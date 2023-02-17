package ee.easyrsrv.standarderp.exception;

import lombok.Data;

@Data
public class StandardErpXmlParsingException extends RuntimeException {

    public String responseBody;
    public String message;

    public StandardErpXmlParsingException(String responseBody, String message){
        this.responseBody = responseBody;
        this.message = String.format("StandardErpXmlParsingException. Body: %s, Message: %s", responseBody, message);
    }
}
