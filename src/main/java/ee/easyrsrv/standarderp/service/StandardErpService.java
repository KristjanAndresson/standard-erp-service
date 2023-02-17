package ee.easyrsrv.standarderp.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ee.easyrsrv.standarderp.entity.AbstractStandardErpEntity;
import ee.easyrsrv.standarderp.enums.Country;
import ee.easyrsrv.standarderp.exception.StandardErpApiException;
import ee.easyrsrv.standarderp.exception.StandardErpCountryConnectionException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StandardErpService {


    private List<String> messageDescriptions = new ArrayList<>();
    private Map<Country, Connection> connections = new HashMap<>();

    private final String plainErrorRegex = "<error(.*?)error>";
    private final String errorRegex = "code=\"(.*?)\" description=\"(.*?)\" row=\"(.*?)\" field=\"(.*?)\"";
    private final String errorRegexSingleQuote = "code='(.*?)' description='(.*?)' row='(.*?)' field='(.*?)'";
    private final String messageTagRegex = "<message(.*?)message>";
    private final String messageDescriptionRegex = "description=\"(.*?)\"";
    private final String messageDescriptionRegexSingleQuote = "description='(.*?)'";
    protected final Logger logger = LoggerFactory.getLogger(getClass());

   public StandardErpService(Map<Country, Connection> connections){
        this.connections = connections;
    }

    private final CloseableHttpClient httpClientHandler = HttpClients.createDefault();

    private Matcher getPlainErrorMatcher(String response){
        Pattern pattern = Pattern.compile(plainErrorRegex);
        return pattern.matcher(response);
    }

    private Matcher getErrorMatcher(String response){
        Pattern pattern = Pattern.compile(errorRegex);
        return pattern.matcher(response);
    }

    private Matcher getSingleQuoteErrorMatcher(String response){
        Pattern pattern = Pattern.compile(errorRegex);
        return pattern.matcher(response);
    }

    private Matcher getMessageTagMatcher(String response){
        Pattern pattern = Pattern.compile(messageTagRegex);
        return pattern.matcher(response);
    }

    private Matcher getMessageDescriptionMatcher(String response){
        Pattern pattern = Pattern.compile(messageDescriptionRegex);
        return pattern.matcher(response);
    }

    private Matcher getSingleQuoteMessageDescriptionMatcher(String response){
        Pattern pattern = Pattern.compile(messageDescriptionRegexSingleQuote);
        return pattern.matcher(response);
    }

    public boolean isEnabled(Country country) {
        return connections.get(country).isEnabled();
    }

    public <T> T addRegisterObject(String objectName, AbstractStandardErpEntity standardErpEntity, Class<T> clazz, Country country) throws
            AuthenticationException, IOException, TransformerException, StandardErpApiException, StandardErpCountryConnectionException,
            URISyntaxException, ParserConfigurationException, SAXException, Exception {

        validateCountry(country);

        List<T> result = makeGetRequestGetList(objectName, standardErpEntity, clazz, country);

        if(result.size() == 0){
            List<T> postResult = makePostRequest(objectName, standardErpEntity, clazz, country);
            return postResult.size() > 0 ? postResult.get(0) : null;
        } else {
            AbstractStandardErpEntity erpEntity = ((AbstractStandardErpEntity) result.get(0));
            List<T> putResult = makePatchRequest(objectName, erpEntity.getIdentificatorValue(), standardErpEntity, clazz, country);
            return putResult.size() > 0 ? putResult.get(0) : null;
        }
    }

    public <T> T updateRegisterObject(String objectName, String identityValue, AbstractStandardErpEntity standardErpEntity,
                                      Class<T> clazz, Country country) throws
            AuthenticationException, IOException, TransformerException, StandardErpApiException, URISyntaxException,
            ParserConfigurationException, SAXException, StandardErpCountryConnectionException, Exception {

        validateCountry(country);
        List<T> putResult = makePatchRequest(objectName, identityValue, standardErpEntity, clazz, country);
        return putResult.size() > 0 ? putResult.get(0) : null;
    }

    public <T> List<T> makeGetRequestGetList(String objectName, AbstractStandardErpEntity standardErpEntity,
                                             Class<T> clazz, Country country) throws
            AuthenticationException, UnsupportedOperationException, IOException, TransformerException, StandardErpApiException,
            URISyntaxException, ParserConfigurationException, SAXException, StandardErpCountryConnectionException, Exception {

        validateCountry(country);

        List<NameValuePair> findByParams = standardErpEntity.getErpFindOneParams();

        if(findByParams == null) {
            return new ArrayList<>();
        }

        URIBuilder builder = buildURI(objectName, findByParams, country);

        UsernamePasswordCredentials creds = getCredentals(country);
        HttpGet get = new HttpGet(builder.build());
        get.addHeader(new BasicScheme().authenticate(creds, get, null));
        CloseableHttpResponse response = this.httpClientHandler.execute(get);

        String body = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);

        int statusCode = response.getStatusLine() != null ? response.getStatusLine().getStatusCode() : 0;

        response.close();

        checkErrors(body);
        body = checkMessages(body);

        return getObjectsFromXmlString(body, objectName, clazz, statusCode, standardErpEntity.getIdentificatorValue());
    }

    private UsernamePasswordCredentials getCredentals(Country country){
        validateCountry(country);

        Connection connection = connections.get(country);

        return new UsernamePasswordCredentials(connection.getErpUsername(), connection.getErpPassword());
    }

    private <T> List<T> makeInsertOrUpdateRequest(HttpEntityEnclosingRequestBase requestBase, String objectName,
                                                  AbstractStandardErpEntity standardErpEntity, Class<T> clazz, Country country)
            throws AuthenticationException, UnsupportedOperationException, IOException, TransformerException, StandardErpApiException,
            ParserConfigurationException, SAXException, StandardErpCountryConnectionException, Exception {

        validateCountry(country);

        String updateParams = standardErpEntity.getUpdateParamsString();
        StringEntity entity = new StringEntity(updateParams, "utf-8");
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8"));
        entity.setContentEncoding("utf-8");
        requestBase.addHeader(new BasicScheme().authenticate(getCredentals(country), requestBase, null));
        requestBase.setEntity(entity);

        CloseableHttpResponse response = this.httpClientHandler.execute(requestBase);

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            System.out.println("Got HTTP " + response.getStatusLine().getStatusCode());
        }

        String body = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);

        int statusCode = response.getStatusLine() != null ? response.getStatusLine().getStatusCode() : 0;

        response.close();

        checkErrors(body);
        body = checkMessages(body);

        return getObjectsFromXmlString(body, objectName, clazz, statusCode, updateParams);
    }

    public <T> List<T> makePatchRequest(String objectName, String identityValue, AbstractStandardErpEntity standardErpEntity,
                                        Class<T> clazz, Country country) throws
            AuthenticationException, IOException, TransformerException, StandardErpApiException, URISyntaxException,
            ParserConfigurationException, SAXException, StandardErpCountryConnectionException, Exception {

        validateCountry(country);

        URIBuilder builder = buildURI(objectName + "/" + identityValue, country);

        HttpPatch patch = new HttpPatch(builder.build());
        return makeInsertOrUpdateRequest(patch, objectName, standardErpEntity, clazz, country);
    }

    public <T> List<T> makePostRequest(String objectName, AbstractStandardErpEntity standardErpEntity, Class<T> clazz, Country country) throws
            AuthenticationException, IOException, TransformerException, StandardErpApiException, URISyntaxException,
            ParserConfigurationException, SAXException, StandardErpCountryConnectionException, Exception {

        validateCountry(country);

        URIBuilder builder = buildURI(objectName, country);

        HttpPost post = new HttpPost(builder.build());
        return makeInsertOrUpdateRequest(post, objectName, standardErpEntity, clazz, country);
    }

    public URIBuilder buildURI(String objectName, Country country) {
        return buildURI(objectName, null, country);
    }

    public URIBuilder buildURI(String objectName, List<NameValuePair> params, Country country) {

        Connection connection = connections.get(country);

        URIBuilder builder = new URIBuilder()
                .setScheme("https")
                .setHost(connection.getErpHost())
                .setPort(connection.getErpPort())
                .setPath(String.format(connection.getPath(), objectName));

        if(params != null && params.size() > 0){
            builder.setParameters(params);
        }

        return builder;
    }

    private String checkMessages(String responseBody){

        List<String> messages = new ArrayList<>();

        Matcher messageTagMatcher = getMessageTagMatcher(responseBody);

        while (messageTagMatcher.find()) {
            String messageTag = messageTagMatcher.group(0);
            responseBody = responseBody.replace(messageTag, "");

            Matcher descriptionMatcher = getMessageDescriptionMatcher(messageTag);
            Matcher descriptionMatcher2 = getSingleQuoteMessageDescriptionMatcher(messageTag);

            while (descriptionMatcher.find()) {
                String description = descriptionMatcher.group(1);

                if(!messages.contains(description))
                    messages.add(description);
            }

            while (descriptionMatcher2.find()) {
                String description = descriptionMatcher2.group(1);

                if(!messages.contains(description))
                    messages.add(description);
            }
        }

        this.messageDescriptions = messages;

        return responseBody;
    }

    private void checkErrors(String responseBody) throws StandardErpApiException {
        Matcher matcher = getErrorMatcher(responseBody);

        while (matcher.find()) {
            throw new StandardErpApiException(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
        }

        Matcher matcher2 = getSingleQuoteErrorMatcher(responseBody);

        while (matcher2.find()) {
            throw new StandardErpApiException(matcher2.group(1), matcher2.group(2), matcher2.group(3), matcher2.group(4));
        }

        Matcher matcher3 = getPlainErrorMatcher(responseBody);

        while (matcher3.find()) {
            throw new StandardErpApiException(matcher3.group(1));
        }
    }

    private void validateCountry(Country country) throws StandardErpCountryConnectionException {
        if(this.connections == null || this.connections.get(country) == null) {
            throw new StandardErpCountryConnectionException(country);
        }
    }

    private <T> List<T> getObjectsFromXmlString(String responseBody, String objectName, Class<T> clazz, int statusCode, String getOrUpdateParms) throws
            TransformerException, ParserConfigurationException, SAXException, IOException, Exception {

        try {
            Document doc = getFromResponseBody(responseBody);
            List<T> res = new ArrayList<T>();
            XmlMapper xmlMapper = new XmlMapper();

            for(int i = 0; i < doc.getElementsByTagName(objectName).getLength(); i++){
                String xmlString = xmlDocNodeToString(doc.getElementsByTagName(objectName).item(i));
                T obj = xmlMapper.readValue(xmlString, clazz);
                if(obj instanceof AbstractStandardErpEntity && this.messageDescriptions.size() > 0) {
                    ((AbstractStandardErpEntity) obj).setMessages(this.messageDescriptions);
                }
                res.add(obj);
            }

            return res;
        } catch (Exception e) {
            logger.error(String.format("BOOKS_RESPONSE_BODY: %s, STATUS CODE: %s, PARAMS: %s", (ObjectUtils.isEmpty(responseBody) ? "EMPTY BODY" : responseBody), statusCode, getOrUpdateParms));
            throw new Exception(e.getMessage());
        }
    }

    private Document getFromResponseBody(String responseBody) throws
            ParserConfigurationException, SAXException, IOException {
        return getFromResponseBody(responseBody, true);
    }

    private Document getFromResponseBody(String responseBody, boolean normalize) throws
            ParserConfigurationException, SAXException, IOException {
        Document doc = convertStringToXMLDocument(responseBody);
        if(normalize) {
            doc.getDocumentElement().normalize();
        }

        return doc;
    }

    private static Document convertStringToXMLDocument(String xmlString) throws
            ParserConfigurationException, SAXException, IOException
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        //Create DocumentBuilder with default configuration
        builder = factory.newDocumentBuilder();

        //Parse the content to Document object
        return builder.parse(new InputSource(new StringReader(xmlString)));
    }

    private static String xmlDocNodeToString(Node elem) throws TransformerException {
        StringWriter buf = new StringWriter();
        Transformer xform = TransformerFactory.newInstance().newTransformer();
        xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); // optional
        xform.setOutputProperty(OutputKeys.INDENT, "yes"); // optional
        xform.transform(new DOMSource(elem), new StreamResult(buf));

        return buf.toString();
    }
}
