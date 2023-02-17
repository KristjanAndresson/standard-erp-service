package ee.easyrsrv.standarderp.entity.invoice.sale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class row {

    public Long stp;
    public Long SalesAcc;
    public String ArtCode;
    public Long Quant = 1L;
    public BigDecimal Price;
    public BigDecimal Sum;
    public BigDecimal rowGP;
    public String VATCode;
    public Long CreditedRow;
    public Long OrdRow;                 // set for credit invoice first row. Reference to credited invoice SerNo
    public Long CUPNr;                  // prepayment number
    public Long Objects;
    public boolean addVatCodeToParams = false;
    public boolean addSaleAccountToParams = false;

    public row(){}

    public row(String articleCode, BigDecimal price, String vatCode, Long objectCode, boolean setSum){
        price = checkPrice(price);
        BigDecimal multipliedSum = price.multiply(getBigDecimalQuant());
        this.ArtCode = articleCode;
        this.Price = price;
        if(setSum) {
            this.Sum = multipliedSum;
        }
        this.VATCode = vatCode;
        this.stp = 1L;
        this.Objects = objectCode;
    }

    public row(String articleCode, BigDecimal price, String vatCode, Long objectCode){
        price = checkPrice(price);
        BigDecimal multipliedSum = price.multiply(getBigDecimalQuant());
        this.ArtCode = articleCode;
        this.Price = price;
        this.VATCode = vatCode;
        this.stp = 1L;
        this.Objects = objectCode;
    }

    public row(Long salesAcc, BigDecimal price, String vatCode, Long stp, Long prepaymentNumber, Long objectCode){
        price = checkPrice(price);
        BigDecimal multipliedSum = price.multiply(getBigDecimalQuant());
        this.SalesAcc = salesAcc;
        this.Price = price;
        this.Sum = multipliedSum;
        this.VATCode = vatCode;
        this.stp = stp;
        this.CUPNr = prepaymentNumber;
        this.Objects = objectCode;
    }

    private BigDecimal checkPrice(BigDecimal price){
        price = price == null ? BigDecimal.ZERO : price;

        if(price.compareTo(BigDecimal.ZERO) < 0) {
            this.Quant = -1L;
            price = price.abs();
        }

        return price;
    }

    public String getQuant(Long q) {
        Long res = Quant != null ? Quant + q : null;
        return res != null ? res.toString() : null;
    }

    public void setQuantity(BigDecimal qty){
        this.Quant = qty != null ? qty.longValue() : 1L;
    }

    public BigDecimal getBigDecimalQuant() {
        return Quant != null ? new BigDecimal(Quant) : BigDecimal.ZERO;
    }

}
