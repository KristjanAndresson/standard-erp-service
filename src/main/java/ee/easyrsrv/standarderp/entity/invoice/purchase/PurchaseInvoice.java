package ee.easyrsrv.standarderp.entity.invoice.purchase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.easyrsrv.standarderp.entity.AbstractStandardErpEntity;
import ee.easyrsrv.standarderp.entity.vat.VatCode;
import ee.easyrsrv.standarderp.enums.Country;
import lombok.Data;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseInvoice extends AbstractStandardErpEntity {

    public static String OBJECT_NAME = "VIVc";

    public static Long ACCOMMODATION_ACCOUNT_CODE = 7111L;
    public static Long SERVICE_ACCOUNT_CODE = 7110L;
    public static Long TRANSLATION_JOB_ACCOUNT_CODE = 7115L;
    public static Long CONTRACTUAL_PENALTY_ACCOUNT_CODE = 8260L;

    public static Long ET_ACCOMMODATION_ACCOUNT_CODE = 4330L;
    public static Long ET_SERVICE_ACCOUNT_CODE = 4320L;
    public static Long ET_TRANSLATION_JOB_ACCOUNT_CODE = 5210L;
    public static Long ET_CONTRACTUAL_PENALTY_ACCOUNT_CODE = 7610L;

    public static Long LT_ACCOMMODATION_ACCOUNT_CODE = 4341L;
    public static Long LT_SERVICE_ACCOUNT_CODE = 4342L;
    public static Long LT_TRANSLATION_JOB_ACCOUNT_CODE = 5210L;
    public static Long LT_CONTRACTUAL_PENALTY_ACCOUNT_CODE = 7610L;

    public static Map<Long, BigDecimal> articleAccountToVatPercentMapper = new HashMap<>(){{
        put(ACCOMMODATION_ACCOUNT_CODE, VatCode.ACCOMMODATION_VAT_PERCENT);
        put(SERVICE_ACCOUNT_CODE, VatCode.SERVICE_VAT_PERCENT);
        put(TRANSLATION_JOB_ACCOUNT_CODE, VatCode.SERVICE_VAT_PERCENT);
        put(CONTRACTUAL_PENALTY_ACCOUNT_CODE, VatCode.OTHER_INCOME_VAT_PERCENT);
    }};

    public static Map<Long, BigDecimal> etArticleAccountToVatPercentMapper = new HashMap<>(){{
        put(ET_ACCOMMODATION_ACCOUNT_CODE, VatCode.ET_ACCOMMODATION_VAT_PERCENT);
        put(ET_SERVICE_ACCOUNT_CODE, VatCode.ET_SERVICE_VAT_PERCENT);
        put(ET_TRANSLATION_JOB_ACCOUNT_CODE, VatCode.ET_SERVICE_VAT_PERCENT);
        put(ET_CONTRACTUAL_PENALTY_ACCOUNT_CODE, VatCode.ET_OTHER_INCOME_VAT_PERCENT);
    }};

    public static Map<Long, BigDecimal> ltArticleAccountToVatPercentMapper = new HashMap<>(){{
        put(LT_ACCOMMODATION_ACCOUNT_CODE, VatCode.LT_ZERO_ACCOMMODATION_VAT_PERCENT);
        put(LT_SERVICE_ACCOUNT_CODE, VatCode.LT_ZERO_SERVICE_VAT_PERCENT);
        put(LT_TRANSLATION_JOB_ACCOUNT_CODE, VatCode.LT_ZERO_SERVICE_VAT_PERCENT);
        put(LT_CONTRACTUAL_PENALTY_ACCOUNT_CODE, VatCode.LT_OTHER_INCOME_VAT_PERCENT);
    }};

    public static Map<Long, String> articleAccountToVatCodeMapper = new HashMap<>(){{
        put(ACCOMMODATION_ACCOUNT_CODE, VatCode.ET_PURCHASE_ACCOMMODATION_ERP_VAT_CODE);
        put(SERVICE_ACCOUNT_CODE, VatCode.PURCHASE_SERVICE_ERP_VAT_CODE);
        put(TRANSLATION_JOB_ACCOUNT_CODE, VatCode.PURCHASE_SERVICE_ERP_VAT_CODE);
    }};

    public static Map<Long, String> etArticleAccountToVatCodeMapper = new HashMap<>(){{
        put(ET_ACCOMMODATION_ACCOUNT_CODE, VatCode.ET_PURCHASE_ACCOMMODATION_ERP_VAT_CODE);
        put(ET_SERVICE_ACCOUNT_CODE, VatCode.ET_PURCHASE_SERVICE_ERP_VAT_CODE);
        put(ET_TRANSLATION_JOB_ACCOUNT_CODE, VatCode.ET_PURCHASE_SERVICE_ERP_VAT_CODE);
    }};

    public static Map<Long, String> ltArticleAccountToVatCodeMapper = new HashMap<>(){{
        put(LT_ACCOMMODATION_ACCOUNT_CODE, VatCode.LT_PURCHASE_ACCOMMODATION_ERP_VAT_CODE);
        put(LT_SERVICE_ACCOUNT_CODE, VatCode.LT_PURCHASE_SERVICE_ERP_VAT_CODE);
        put(LT_TRANSLATION_JOB_ACCOUNT_CODE, VatCode.LT_PURCHASE_SERVICE_ERP_VAT_CODE);
    }};

    public static Map<Country, Map<Long, BigDecimal>> countryArticleAccountToVatPercentMapper = new HashMap<>(){{
        put(Country.ee, etArticleAccountToVatPercentMapper);
        put(Country.lv, articleAccountToVatPercentMapper);
        put(Country.lt, ltArticleAccountToVatPercentMapper);
    }};

    public static Map<Country, Map<Long, String>> countryArticleAccountToVatCodeMapper = new HashMap<>(){{
        put(Country.ee, etArticleAccountToVatCodeMapper);
        put(Country.lv, articleAccountToVatCodeMapper);
        put(Country.lt, ltArticleAccountToVatCodeMapper);
    }};

    public static Map<Country, Long> accommodationAccountCodeMapper = new HashMap<>(){{
        put(Country.ee, ET_ACCOMMODATION_ACCOUNT_CODE);
        put(Country.lv, ACCOMMODATION_ACCOUNT_CODE);
        put(Country.lt, LT_ACCOMMODATION_ACCOUNT_CODE);
    }};

    public static Map<Country, Long> serviceAccountCodeMapper = new HashMap<>(){{
        put(Country.ee, ET_SERVICE_ACCOUNT_CODE);
        put(Country.lv, SERVICE_ACCOUNT_CODE);
        put(Country.lt, LT_SERVICE_ACCOUNT_CODE);
    }};

    public static Map<Country, Long> translationJobAccountCodeMapper = new HashMap<>(){{
        put(Country.ee, ET_TRANSLATION_JOB_ACCOUNT_CODE);
        put(Country.lv, TRANSLATION_JOB_ACCOUNT_CODE);
        put(Country.lt, LT_TRANSLATION_JOB_ACCOUNT_CODE);
    }};

    public static Map<Country, Long> contractualPenaltyAccountCodeMapper = new HashMap<>(){{
        put(Country.ee, ET_CONTRACTUAL_PENALTY_ACCOUNT_CODE);
        put(Country.lv, CONTRACTUAL_PENALTY_ACCOUNT_CODE);
        put(Country.lt, LT_CONTRACTUAL_PENALTY_ACCOUNT_CODE);
    }};

    public Long SerNr;              // Unique number for invoice in ERP system
    public String InvDate;          // invoice date
    public BigDecimal PayVal;       // total sum with VAT
    public BigDecimal VATVal;
    public BigDecimal CalcVATVal;
    public String InvoiceNr;        // Supplier invoice number
    public String VECode;           // Supplier code in ERP contact register
    public Long OKFlag = 1L;        // confirmed OK. 0=no, 1=yes
    public String PayDeal;          // PayDeal
    public String RefStr;             // reference

    public boolean vatObligated = true;

    public BigDecimal accommodationTotal;
    public BigDecimal serviceTotal;

    public List<row> rows = new ArrayList<>();

    public PurchaseInvoice(){}

    public PurchaseInvoice(Long erpNumber, String payDeal, String supplierErpCode, String invoiceIsoFormatDate, String invoiceFinalNo,
                           String clientRefNo, List<row> rows){

        this(erpNumber, payDeal, supplierErpCode, invoiceIsoFormatDate, invoiceFinalNo, clientRefNo, rows, true, null, null);
    }

    public PurchaseInvoice(Long erpNumber, String payDeal, String supplierErpCode, String invoiceIsoFormatDate, String invoiceFinalNo,
                           String clientRefNo, List<row> rows, boolean vatObligated){

        this(erpNumber, payDeal, supplierErpCode, invoiceIsoFormatDate, invoiceFinalNo, clientRefNo, rows, vatObligated, null, null);
    }

    public PurchaseInvoice(Long erpNumber, String payDeal, String supplierErpCode, String invoiceIsoFormatDate, String invoiceFinalNo,
                           String clientRefNo, List<row> rows, boolean vatObligated, BigDecimal accommodationTotal,
                           BigDecimal serviceTotal){

        this.SerNr = erpNumber;
        this.PayDeal = payDeal;
        this.VECode = supplierErpCode;
        this.InvDate = invoiceIsoFormatDate;
        this.InvoiceNr = invoiceFinalNo;
        this.RefStr = clientRefNo;
        this.rows = rows;
        this.vatObligated = vatObligated;
        this.accommodationTotal = accommodationTotal;
        this.serviceTotal = serviceTotal;

        this.calculatePayVal();
    }

    public String getIdentificatorValue(){
        return String.valueOf(this.SerNr);
    }

    public static String getObjectName() {
        return OBJECT_NAME;
    }

    public Long getSerNr() {
        return SerNr;
    }

    public String getInvDate() {
        return InvDate;
    }

    public BigDecimal getPayVal() {
        return PayVal;
    }

    public String getInvoiceNr() {
        return InvoiceNr;
    }

    public String getVECode() {
        return VECode;
    }

    public Long getOKFlag() {
        return OKFlag;
    }

    public String getPayDeal() {
        return PayDeal;
    }

    public String getRefStr() {
        return RefStr;
    }

    public List<row> getRows() {
        return rows;
    }

    private void calculatePayVal(){
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal sumWithoutVat = BigDecimal.ZERO;
        BigDecimal vatVal = BigDecimal.ZERO;

        for(row row: this.rows){
            BigDecimal vatPercent = vatObligated
                    ? countryArticleAccountToVatPercentMapper.get(row.getCountry()).get(row.getAccNumber())
                    : BigDecimal.ZERO;

            sumWithoutVat = sumWithoutVat.add(row.getSum().setScale(2, RoundingMode.HALF_UP));
            sum = sum.add(row.getSum().multiply(BigDecimal.ONE.add(vatPercent)));
            vatVal = vatVal.add(row.getSum().multiply(vatPercent));
        }

        sum = (this.accommodationTotal == null || this.serviceTotal == null)
                ? sum.setScale(2, RoundingMode.HALF_UP)
                : this.accommodationTotal.add(this.serviceTotal);

        vatVal = sum.subtract(sumWithoutVat);

        this.PayVal = (this.accommodationTotal == null || this.serviceTotal == null) ? sum : this.accommodationTotal.add(this.serviceTotal);
        this.VATVal = vatObligated ? vatVal.setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.CalcVATVal = this.VATVal;
    }

    public List<NameValuePair> getErpFindOneParams(){
        if (SerNr == null) {
            return null;
        } else {
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("filter.SerNr", SerNr.toString()));
            return params;
        }
    }

    public List<NameValuePair> getUpdateParams(){
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("set_field.VECode", VECode));
        params.add(new BasicNameValuePair("set_field.InvDate", InvDate));
        params.add(new BasicNameValuePair("set_field.PayDeal", PayDeal));
        params.add(new BasicNameValuePair("set_field.InvoiceNr", InvoiceNr));
        params.add(new BasicNameValuePair("set_field.PayVal", PayVal.toString()));

        updateRowParameters(params);

        params.add(new BasicNameValuePair("set_field.CalcVATVal", CalcVATVal.toString()));
        params.add(new BasicNameValuePair("set_field.VATVal", VATVal.toString()));
        params.add(new BasicNameValuePair("set_field.OKFlag", OKFlag.toString()));

        return params;
    }

    private void updateRowParameters(List<NameValuePair> params){

        int index = 0;

        for(row row: rows){
            addInvoiceRowParams(params, row, index);
            index++;
        }
    }

    private void addInvoiceRowParams(List<NameValuePair> params, row row, int index){
        params.add(new BasicNameValuePair(String.format("set_row_field.%s.AccNumber", index), getStringValue(row.getAccNumber())));
        params.add(new BasicNameValuePair(String.format("set_row_field.%s.Sum", index), getStringValue(row.getSum())));
        params.add(new BasicNameValuePair(String.format("set_row_field.%s.VATCode", index), row.getVATCode()));
        params.add(new BasicNameValuePair(String.format("set_row_field.%s.qty", index), getStringValue(row.getQty())));
    }

    public String getUpdateParamsString(){
        List<String> res = new ArrayList<>();

        for(NameValuePair nameValuePair: getUpdateParams()){
            res.add(String.format("%s=%s", nameValuePair.getName(), nameValuePair.getValue()));
        }

        return String.join("&", res);
    }
}
