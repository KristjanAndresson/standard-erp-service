package ee.easyrsrv.standarderp.entity.receipt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.easyrsrv.standarderp.entity.AbstractStandardErpEntity;
import ee.easyrsrv.standarderp.enums.BankCode;
import ee.easyrsrv.standarderp.enums.Country;
import ee.easyrsrv.standarderp.enums.PaymentMode;
import lombok.Data;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Receipt extends AbstractStandardErpEntity {

    public static String OBJECT_NAME = "IPVc";

    public static Map<BankCode, PaymentMode> erpBankPaymentCodeMapper = new HashMap<>(){{
        put(BankCode.LUMINOR, PaymentMode.B);
        put(BankCode.SEB, PaymentMode.B1);
        put(BankCode.SWED, PaymentMode.B2);
    }};

    public static Map<BankCode, Long> receiptRowBankARAccMapper = new HashMap<>(){{
        put(BankCode.SEB, 2620L);
        put(BankCode.SWED, 2621L);
        put(BankCode.LUMINOR, 2622L);
    }};

    public static Map<BankCode, Long> etReceiptRowBankARAccMapper = new HashMap<>(){{
        put(BankCode.SEB, 1030L);
        put(BankCode.SWED, 1020L);
        put(BankCode.LUMINOR, 1040L);
    }};

    public static Map<Country, Map<BankCode, Long>> countryReceiptRowBankARAccMapper = new HashMap<>(){{
        put(Country.ee, etReceiptRowBankARAccMapper);
        put(Country.lv, receiptRowBankARAccMapper);
        put(Country.lt, etReceiptRowBankARAccMapper);
    }};

    public Long SerNr;
    public String Comment;
    public String PayMode;
    public Long OKFlag = 1L;
    public Long OKMark = 1L;
    public String RegDate;
    public String TransDate;
    public String Sign;
    public BigDecimal CurPayVal;

    public List<row> rows = new ArrayList<>();

    public Receipt(){}

    public Receipt(BigDecimal curPayVal, PaymentMode paymentMode, List<row> rows){
        this(null, null, curPayVal, paymentMode, rows);
    }

    public Receipt(String regDate, String transDate, BigDecimal curPayVal, PaymentMode paymentMode, List<row> rows){
        if(regDate != null){
            this.RegDate = regDate;
        }
        if(transDate != null){
            this.TransDate = transDate;
        }
        this.CurPayVal = curPayVal;
        this.rows = rows;
        if(paymentMode != null) {
            this.PayMode = paymentMode.toString();
        }
    }

    public String getIdentificatorValue(){
        return String.valueOf(this.SerNr);
    }

    public static String getObjectName() {
        return OBJECT_NAME;
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

        if(PayMode != null) {
            params.add(new BasicNameValuePair("set_field.PayMode", PayMode));
        }
        if(RegDate != null) {
            params.add(new BasicNameValuePair("set_field.RegDate", RegDate));
        }
        if(TransDate != null) {
            params.add(new BasicNameValuePair("set_field.TransDate", TransDate));
        }
        params.add(new BasicNameValuePair("set_field.CurPayVal", CurPayVal.toString()));
        updateRowParameters(params);
        params.add(new BasicNameValuePair("set_field.OKFlag", OKFlag.toString()));
        params.add(new BasicNameValuePair("set_field.OKMark", OKMark.toString()));

        return params;
    }

    private void updateRowParameters(List<NameValuePair> params){
        int index = 0;
        for(row row: rows){
            addRowParams(params, row, index);
            index++;
        }
    }

    private void addRowParams(List<NameValuePair> params, row row, int index){

        params.add(new BasicNameValuePair(String.format("set_row_field.%s.RecVal", index), getStringValue(row.getRecVal())));
        params.add(new BasicNameValuePair(String.format("set_row_field.%s.InvVal", index), getStringValue(row.getInvVal())));
        params.add(new BasicNameValuePair(String.format("set_row_field.%s.BankVal", index), getStringValue(row.getBankVal())));
        params.add(new BasicNameValuePair(String.format("set_row_field.%s.CUPNr", index), getStringValue(row.getCUPNr())));
        params.add(new BasicNameValuePair(String.format("set_row_field.%s.VATCode", index), row.getVATCode()));
        params.add(new BasicNameValuePair(String.format("set_row_field.%s.VATVal", index), getStringValue(row.getVATVal())));

        if(row.getPayDate() != null){
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.PayDate", index), row.getPayDate()));
        }

        if(row.getCustCode() != null){
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.CustCode", index), row.getCustCode()));
        }
        if(row.getObjects() != null){
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.Objects", index), getStringValue(row.getObjects())));
        }
        if(row.getPayMode() != null) {
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.PayMode", index), row.getPayMode()));
        }
        if(row.getARAcc() != null) {
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.ARAcc", index), row.getARAcc().toString()));
        }
        if(row.getComment() != null) {
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.Comment", index), row.getComment()));
        }
    }

    public String getUpdateParamsString(){
        List<String> res = new ArrayList<>();

        for(NameValuePair nameValuePair: getUpdateParams()){
            res.add(String.format("%s=%s", nameValuePair.getName(), nameValuePair.getValue()));
        }

        return String.join("&", res);
    }
}
