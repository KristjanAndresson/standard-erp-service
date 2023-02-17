package ee.easyrsrv.standarderp.entity.invoice.purchase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.easyrsrv.standarderp.entity.vat.VatCode;
import ee.easyrsrv.standarderp.enums.Country;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class row {

    public Long AccNumber;
    public BigDecimal Sum;
    public Long qty;
    public String VATCode;
    public boolean vatObligated = true;
    public Country country;

    public row(){}

    public row(Long accountCode, BigDecimal sum, Long qty){
        this(accountCode, sum, qty, true);
    }

    public row(Long accountCode, BigDecimal sum, Long qty, boolean vatObligated){

        this.AccNumber = accountCode;
        this.Sum = sum.setScale(2, RoundingMode.HALF_UP);
        this.qty = qty;
        this.vatObligated = vatObligated;
        this.VATCode = vatObligated
                ? PurchaseInvoice.articleAccountToVatCodeMapper.get(accountCode)
                : VatCode.GIFT_CARD_ERP_VAT_CODE;
    }

    public row(Long accountCode, BigDecimal sum, Long qty, boolean vatObligated, Country country){

        this(accountCode, sum, qty, vatObligated, country, null);
    }

    public row(Long accountCode, BigDecimal sum, Long qty, boolean vatObligated, Country country, String vatCode){

        this.AccNumber = accountCode;
        this.Sum = sum.setScale(2, RoundingMode.HALF_UP);
        this.qty = qty;
        this.vatObligated = vatObligated;
        this.country = country;
        if(vatCode != null){
            this.VATCode = vatCode;
        } else {
            this.VATCode = vatObligated
                    ? PurchaseInvoice.countryArticleAccountToVatCodeMapper.get(country).get(accountCode)
                    : VatCode.getGiftCardVatCode(country);
        }
    }
    public String getQty(Long q) {
        Long res = qty != null ? qty + q : null;
        return res != null ? res.toString() : null;
    }
}
