package ee.easyrsrv.standarderp.entity.invoice.sale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.easyrsrv.standarderp.entity.AbstractStandardErpEntity;
import lombok.Data;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnpaidInvoice extends AbstractStandardErpEntity {

    public static String OBJECT_NAME = "ARVc";

    public Long InvoiceNr;
    public BigDecimal RVal;
    public String DueDate;
    public String CustCode;
    public String ARRebDate;
    public String ARCurncyCode;
    public String CustName;
    public BigDecimal BookRVal;
    public String ARRebDate2;
    public String InvDate;

    public UnpaidInvoice(){}

    public UnpaidInvoice(Long erpInvoiceNumber){
        this.InvoiceNr = erpInvoiceNumber;
    }

    public String getIdentificatorValue(){
        return String.valueOf(this.InvoiceNr);
    }

    public static String getObjectName() {
        return OBJECT_NAME;
    }

    public List<NameValuePair> getErpFindOneParams(){
        if (InvoiceNr == null) {
            return null;
        } else {
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("filter.InvoiceNr", InvoiceNr.toString()));
            return params;
        }
    }

    public List<NameValuePair> getUpdateParams(){
        // TODO unsupported method exception?
        return null;
    }

    public String getUpdateParamsString(){
        // TODO unsupported method exception?
        return null;
    }
}
