package ee.easyrsrv.standarderp.entity.receipt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.easyrsrv.standarderp.enums.PaymentMode;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class row {

    public String PayDate;
    public String CustCode;
    public String CustName;
    public Long CUPNr;
    public String VATCode;
    public BigDecimal VATVal;
    public BigDecimal InvVal;
    public String InvCurncy;
    public BigDecimal BankVal;
    public String BankCurncy;
    public BigDecimal RecVal;
    public String RecCurncy;
    public Long ARAcc;
    public Long Objects;
    public String PayMode;
    public String Comment;

    public row(){}

    public row(String customerCode, Long prepayNumber, String vatCode, BigDecimal sum, BigDecimal vatSum, String currency, Long objectCode, String comment){
        this(customerCode, prepayNumber, vatCode, sum, vatSum, currency, objectCode, false, comment);
    }

    public row(String customerCode, Long prepayNumber, String vatCode, BigDecimal sum, BigDecimal vatSum, String currency, Long objectCode, boolean giftCardRow, String comment){
        this(null, customerCode, prepayNumber, vatCode, sum, vatSum, currency, objectCode, giftCardRow, null, comment);
    }

    public row(String payDate, String customerCode, Long prepayNumber, String vatCode, BigDecimal sum, BigDecimal vatSum, String currency, Long objectCode, boolean giftCardRow, String comment){
        this(payDate, customerCode, prepayNumber, vatCode, sum, vatSum, currency, objectCode, giftCardRow, null, comment);
    }

    public row(String payDate, String customerCode, Long prepayNumber, String vatCode, BigDecimal sum, BigDecimal vatSum, String currency, Long objectCode, boolean giftCardRow, Long ARAcc, String comment){
        if(payDate != null){
            this.PayDate = payDate;
        }
        this.CustCode = customerCode;
        this.CUPNr = prepayNumber;
        this.VATCode = vatCode;
        this.VATVal = vatSum;
        this.InvVal = sum;
        this.BankVal = sum;
        this.RecVal = sum;
        this.InvCurncy = currency;
        this.BankCurncy = currency;
        this.RecCurncy = currency;
        this.Objects = objectCode;
        this.Comment = comment;

        if(giftCardRow) {
            this.PayMode = PaymentMode.KK.toString();
        }

        if(ARAcc != null) {
            this.ARAcc = ARAcc;
        }
    }
}
