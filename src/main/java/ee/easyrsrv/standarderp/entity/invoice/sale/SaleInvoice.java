package ee.easyrsrv.standarderp.entity.invoice.sale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.easyrsrv.standarderp.entity.AbstractStandardErpEntity;
import lombok.Data;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleInvoice extends AbstractStandardErpEntity {

    public static String OBJECT_NAME = "IVVc";

    public Long SerNr;              // Unique number for invoice in ERP system
    public String InvDate;          // invoice date
    public String CustCode;         // customer code
    public Long OKFlag = 1L;        // confirmed OK. 0=no, 1=yes
    public Long Prntdf = 1L;        // sent. 0=no, 1=yes
    public Long InvType;            // invoice type (Regular, credit etc. 1=regular)
    public Long InclVAT;            // price with VAT (checbox, 0=no; 1=yes)
    public String CalcFinRef;       // reference
    public String RefStr;           // reference
    public Long RecValue;           // paid (0=no; 1=yes)
    public String InvComment;       // comment
    public Long CredInv;            // Orig. no. /(invoice to credit)
    public Long CredMark = 0L;      // Credit invoice (0=no; 1=yes)
    public String Comment;          // Comment
    public String PayDeal;          // PayDeal
    public Long Objects;
    public String OfficialSerNr;
    public String CredOfficialSerNr;
    public BigDecimal totalSum;
    public BigDecimal totalSumWithoutVat;

    public BigDecimal Sum3 = null;
    public BigDecimal Sum4 = null;       // total sum with VAT

    public List<row> rows = new ArrayList<>();

    public SaleInvoice(){}

    public SaleInvoice(Long erpNumber){
        this.SerNr = erpNumber;
    }

    public SaleInvoice(Long erpNumber, String clientErpCode, String invoiceIsoFormatDate,
            String invoiceFinalNo, String comment, List<row> rows, BigDecimal totalSum, BigDecimal totalSumWithoutVat){

        this.SerNr = erpNumber;
        this.PayDeal = "7";
        this.CustCode = clientErpCode;
        this.InvDate = invoiceIsoFormatDate;
        this.InvComment = comment;
        this.OfficialSerNr = invoiceFinalNo;
        this.CredMark = 0L;
        this.rows = rows;
        this.OKFlag = 1L;
        this.Prntdf = 1L;
        this.totalSum = totalSum;
        this.totalSumWithoutVat = totalSumWithoutVat;
    }

    public SaleInvoice(Long erpNumber, String payDeal, String clientErpCode, String invoiceIsoFormatDate, String invoiceFinalNo,
                       String clientRefNo, Long creditingInvoiceNo, Long credMark, Long objectCode, List<row> rows,
                       BigDecimal totalSum, BigDecimal totalSumWithoutVat){

        this(erpNumber, payDeal, clientErpCode, invoiceIsoFormatDate, invoiceFinalNo, clientRefNo, creditingInvoiceNo,
                credMark, objectCode, rows, 1L, totalSum, totalSumWithoutVat);
    }

    public SaleInvoice(Long erpNumber, String payDeal, String clientErpCode, String invoiceIsoFormatDate, String invoiceFinalNo,
                       String clientRefNo, Long creditingInvoiceNo, Long credMark, Long objectCode, List<row> rows, Long okFlag,
                       BigDecimal totalSum, BigDecimal totalSumWithoutVat){

        boolean isCredit = credMark != null && credMark == 1L;

        this.SerNr = erpNumber;
        this.PayDeal = payDeal;
        this.CustCode = clientErpCode;
        this.InvDate = invoiceIsoFormatDate;
        this.InvComment = invoiceFinalNo;
        if(!isCredit){
            this.OfficialSerNr = invoiceFinalNo;
        } else {
            this.CredOfficialSerNr = invoiceFinalNo;
        }

        this.RefStr = clientRefNo;
        this.CredInv = creditingInvoiceNo;
        this.CredMark = credMark;
        this.Objects = objectCode;
        this.rows = rows;
        this.OKFlag = okFlag;
        this.Prntdf = okFlag;
        this.totalSum = totalSum;
        this.totalSumWithoutVat = totalSumWithoutVat;

        calculatePayVal();
    }

    private void calculatePayVal(){

        if(this.totalSum != null && this.totalSumWithoutVat != null) {
            BigDecimal vatVal =  this.totalSum.subtract(totalSumWithoutVat).setScale(2, RoundingMode.HALF_UP);
            this.Sum4 = this.totalSum;
            this.Sum3 = vatVal;
        }
    }

    public String getIdentificatorValue(){
        return String.valueOf(this.SerNr);
    }

    public static String getObjectName() {
        return OBJECT_NAME;
    }

    public void addRow(row row) {
        this.rows.add(row);
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
        params.add(new BasicNameValuePair("set_field.CustCode", CustCode));
        params.add(new BasicNameValuePair("set_field.InvDate", InvDate));
        params.add(new BasicNameValuePair("set_field.PayDeal", PayDeal));
        params.add(new BasicNameValuePair("set_field.InvComment", InvComment));

        if (OfficialSerNr != null) {
            params.add(new BasicNameValuePair("set_field.OfficialSerNr", OfficialSerNr));
        }
        if (CredOfficialSerNr != null) {
            params.add(new BasicNameValuePair("set_field.CredOfficialSerNr", CredOfficialSerNr));
        }
        if (RefStr != null) {
            params.add(new BasicNameValuePair("set_field.RefStr", RefStr));
        }
        if (Objects != null) {
            params.add(new BasicNameValuePair("set_field.Objects", getStringValue(getObjects())));
        }
        if (CredInv != null) {
            params.add(new BasicNameValuePair("set_field.CredInv", CredInv.toString()));
            params.add(new BasicNameValuePair("set_field.CredMark", CredMark.toString()));
        }

        updateRowParameters(params);

        if(Sum3 != null && Sum4 != null){
            params.add(new BasicNameValuePair("set_field.Sum3", Sum3.toString()));
            params.add(new BasicNameValuePair("set_field.Sum4", Sum4.toString()));
            params.add(new BasicNameValuePair("set_field.BaseSum4", Sum4.toString()));
            params.add(new BasicNameValuePair("set_field.SumIncCom", Sum4.toString()));
        }

        if(OKFlag.equals(1L)) {
            params.add(new BasicNameValuePair("set_field.OKFlag", OKFlag.toString()));
            params.add(new BasicNameValuePair("set_field.Prntdf", Prntdf.toString()));
        }


        return params;
    }

    private void updateRowParameters(List<NameValuePair> params){

        int index = 0;

        if (CredInv != null) {
            addFirstCreditInvoiceParamsRow(params);
            index++;
        }

        for(row row: rows){
            addInvoiceRowParams(params, row, index);
            index++;
        }
    }

    private void addFirstCreditInvoiceParamsRow(List<NameValuePair> params){
        params.add(new BasicNameValuePair(String.format("set_row_field.%s.CredOfficialSerNr", 0), CredOfficialSerNr));
        params.add(new BasicNameValuePair(String.format("set_row_field.%s.stp", 0), "3"));
        params.add(new BasicNameValuePair(String.format("set_row_field.%s.OrdRow", 0), CredInv.toString()));
        params.add(new BasicNameValuePair(String.format("set_row_field.%s.CreditedRow", 0), "1"));
    }

    private void addInvoiceRowParams(List<NameValuePair> params, row row, int index){
        if(row.getCUPNr() != null) {
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.stp", index), row.getStp()));
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.CUPNr", index), row.getCUPNr()));
            if(row.getSum() != null){
                params.add(new BasicNameValuePair(String.format("set_row_field.%s.Sum", index), row.getSum()));
                params.add(new BasicNameValuePair(String.format("set_row_field.%s.BasePrice", index), row.getSum()));
            }
        } else {
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.stp", index), row.getStp()));
            if(row.getArtCode() != null) {
                params.add(new BasicNameValuePair(String.format("set_row_field.%s.ArtCode", index), row.getArtCode()));
            }
            if(row.getObjects() != null) {
                params.add(new BasicNameValuePair(String.format("set_row_field.%s.Objects", index), row.getObjects()));
            }
            if(row.isAddSaleAccountToParams()){
                params.add(new BasicNameValuePair(String.format("set_row_field.%s.SalesAcc", index), row.getSalesAcc()));
            }
            if(row.isAddVatCodeToParams()){
                params.add(new BasicNameValuePair(String.format("set_row_field.%s.VATCode", index), row.getVATCode()));
            }
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.Price", index), row.getPrice()));
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.BasePrice", index), row.getPrice()));
            if(row.getSum() != null){
                params.add(new BasicNameValuePair(String.format("set_row_field.%s.Sum", index), row.getSum()));
            }
            if(row.getCreditedRow() != null){
                params.add(new BasicNameValuePair(String.format("set_row_field.%s.CreditedRow", index), row.getCreditedRow()));
            }
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.Quant", index), row.getQuant(1L)));
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.Quant", index), row.getQuant()));
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
