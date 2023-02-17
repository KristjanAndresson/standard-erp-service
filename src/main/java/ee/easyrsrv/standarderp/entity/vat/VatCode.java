package ee.easyrsrv.standarderp.entity.vat;


import ee.easyrsrv.standarderp.enums.Country;
import ee.easyrsrv.standarderp.enums.StandardErpArticleCode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class VatCode {

    public static final String SERVICE_VAT_TEXT = "SERVICE";
    public static final String ACCOMMODATION_VAT_TEXT = "ACCOMMODATION";
    public static final String GIFT_CARD_VAT_TEXT = "GIFT CARD";

    public static final String SERVICE_ERP_VAT_CODE = "1";
    public static final String ET_SERVICE_ERP_VAT_CODE = "1";
    public static final String LT_SERVICE_ERP_VAT_CODE = "5";

    public static final String ACCOMMODATION_ERP_VAT_CODE = "2";
    public static final String ET_ACCOMMODATION_ERP_VAT_CODE = "4";
    public static final String LT_ACCOMMODATION_ERP_VAT_CODE = "5";

    public static final String GIFT_CARD_ERP_VAT_CODE = "9";
    public static final String ET_GIFT_CARD_ERP_VAT_CODE = "2";
    public static final String LT_GIFT_CARD_ERP_VAT_CODE = "2";

    public static final String OTHER_INCOME_ERP_VAT_CODE = "8";
    public static final String ET_OTHER_INCOMED_ERP_VAT_CODE = "MV";
    public static final String LT_OTHER_INCOME_ERP_VAT_CODE = "MV";

    public static final String LT_COMMISSION_ERP_VAT_CODE = "3";

    public static final String PURCHASE_SERVICE_ERP_VAT_CODE = "5";
    public static final String PURCHASE_ACCOMMODATION_ERP_VAT_CODE = "6";
    // TODO CHANGE ET VALUES
    public static final String ET_PURCHASE_SERVICE_ERP_VAT_CODE = "1";
    public static final String ET_PURCHASE_ACCOMMODATION_ERP_VAT_CODE = "4";

    public static final String LT_PURCHASE_SERVICE_ERP_VAT_CODE = "2";
    public static final String LT_PURCHASE_ACCOMMODATION_ERP_VAT_CODE = "2";

    public static final BigDecimal SERVICE_VAT_PERCENT = BigDecimal.valueOf(0.21);
    public static final BigDecimal ET_SERVICE_VAT_PERCENT = BigDecimal.valueOf(0.20);
    public static final BigDecimal LT_SERVICE_VAT_PERCENT = BigDecimal.valueOf(0.21);
    public static final BigDecimal LT_ZERO_SERVICE_VAT_PERCENT = BigDecimal.valueOf(0.00);

    public static final BigDecimal ACCOMMODATION_VAT_PERCENT = BigDecimal.valueOf(0.12);
    public static final BigDecimal ET_ACCOMMODATION_VAT_PERCENT = BigDecimal.valueOf(0.09);
    public static final BigDecimal LT_ACCOMMODATION_VAT_PERCENT = BigDecimal.valueOf(0.09);
    public static final BigDecimal LT_ZERO_ACCOMMODATION_VAT_PERCENT = BigDecimal.valueOf(0.00);

    public static final String RECEIPT_SERVICE_ERP_VAT_CODE = "1";
    public static final String ET_RECEIPT_SERVICE_ERP_VAT_CODE = "1";
    public static final String LT_RECEIPT_SERVICE_ERP_VAT_CODE = "5";
    public static final String RECEIPT_ACCOMMODATION_ERP_VAT_CODE = "2";
    public static final String ET_RECEIPT_ACCOMMODATION_ERP_VAT_CODE = "4";
    public static final String LT_RECEIPT_ACCOMMODATION_ERP_VAT_CODE = "5";

    public static final BigDecimal RECEIPT_SERVICE_VAT_PERCENT = BigDecimal.valueOf(0.21);
    public static final BigDecimal ET_RECEIPT_SERVICE_VAT_PERCENT = BigDecimal.valueOf(0.20);
    public static final BigDecimal LT_RECEIPT_SERVICE_VAT_PERCENT = BigDecimal.valueOf(0.00);

    public static final BigDecimal RECEIPT_ACCOMMODATION_VAT_PERCENT = BigDecimal.valueOf(0.12);
    public static final BigDecimal ET_RECEIPT_ACCOMMODATION_VAT_PERCENT = BigDecimal.valueOf(0.09);
    public static final BigDecimal LT_RECEIPT_ACCOMMODATION_VAT_PERCENT = BigDecimal.valueOf(0.00);

    public static final BigDecimal OTHER_INCOME_VAT_PERCENT = BigDecimal.valueOf(0.00);
    public static final BigDecimal ET_OTHER_INCOME_VAT_PERCENT = BigDecimal.valueOf(0.00);
    public static final BigDecimal LT_OTHER_INCOME_VAT_PERCENT = BigDecimal.valueOf(0.00);

    public static Map<StandardErpArticleCode, String> erpArticleCodeVatCodeMapper = new HashMap<>(){{
        put(StandardErpArticleCode.MAJUTUS, VatCode.ACCOMMODATION_ERP_VAT_CODE);
        put(StandardErpArticleCode.TEENUS, VatCode.SERVICE_ERP_VAT_CODE);
        put(StandardErpArticleCode.KOMISJON, VatCode.SERVICE_ERP_VAT_CODE);
        put(StandardErpArticleCode.KINKEK, VatCode.GIFT_CARD_ERP_VAT_CODE);
        put(StandardErpArticleCode.MAJUTUSEM, VatCode.ACCOMMODATION_ERP_VAT_CODE);
        put(StandardErpArticleCode.TEENUSEM, VatCode.SERVICE_ERP_VAT_CODE);
        put(StandardErpArticleCode.TRAHV, VatCode.OTHER_INCOME_ERP_VAT_CODE);
    }};

    public static Map<StandardErpArticleCode, String> etErpArticleCodeVatCodeMapper = new HashMap<>(){{
        put(StandardErpArticleCode.MAJUTUS, VatCode.ET_ACCOMMODATION_ERP_VAT_CODE);
        put(StandardErpArticleCode.TEENUS, VatCode.ET_SERVICE_ERP_VAT_CODE);
        put(StandardErpArticleCode.KOMISJON, VatCode.ET_SERVICE_ERP_VAT_CODE);
        put(StandardErpArticleCode.KINKEK, VatCode.ET_GIFT_CARD_ERP_VAT_CODE);
        put(StandardErpArticleCode.MAJUTUSEM, VatCode.ET_ACCOMMODATION_ERP_VAT_CODE);
        put(StandardErpArticleCode.TEENUSEM, VatCode.ET_SERVICE_ERP_VAT_CODE);
        put(StandardErpArticleCode.TRAHV, VatCode.ET_OTHER_INCOMED_ERP_VAT_CODE);
    }};

    public static Map<StandardErpArticleCode, String> ltErpArticleCodeVatCodeMapper = new HashMap<>(){{
        put(StandardErpArticleCode.MAJUTUS, VatCode.LT_ACCOMMODATION_ERP_VAT_CODE);
        put(StandardErpArticleCode.TEENUS, VatCode.LT_SERVICE_ERP_VAT_CODE);
        put(StandardErpArticleCode.KOMISJON, VatCode.LT_COMMISSION_ERP_VAT_CODE);
        put(StandardErpArticleCode.KINKEK, VatCode.LT_GIFT_CARD_ERP_VAT_CODE);
        put(StandardErpArticleCode.MAJUTUSEM, VatCode.LT_ACCOMMODATION_ERP_VAT_CODE);
        put(StandardErpArticleCode.TEENUSEM, VatCode.LT_SERVICE_ERP_VAT_CODE);
        put(StandardErpArticleCode.TRAHV, VatCode.LT_OTHER_INCOME_ERP_VAT_CODE);
    }};

    public static Map<Country, Map<StandardErpArticleCode, String>> countryErpArticleCodeVatCodeMapper = new HashMap<>(){{
        put(Country.ee, etErpArticleCodeVatCodeMapper);
        put(Country.lv, erpArticleCodeVatCodeMapper);
        put(Country.lt, ltErpArticleCodeVatCodeMapper);
    }};

    public static Map<Country, String> serviceVatCodeMapper = new HashMap<>(){{
        put(Country.ee, VatCode.ET_SERVICE_ERP_VAT_CODE);
        put(Country.lv, VatCode.SERVICE_ERP_VAT_CODE);
        put(Country.lt, VatCode.LT_SERVICE_ERP_VAT_CODE);
    }};

    public static Map<Country, String> receiptServiceVatCodeMapper = new HashMap<>(){{
        put(Country.ee, VatCode.ET_RECEIPT_SERVICE_ERP_VAT_CODE);
        put(Country.lv, VatCode.RECEIPT_SERVICE_ERP_VAT_CODE);
        put(Country.lt, VatCode.LT_RECEIPT_SERVICE_ERP_VAT_CODE);
    }};

    public static Map<Country, String> accommodationVatCodeMapper = new HashMap<>(){{
        put(Country.ee, VatCode.ET_ACCOMMODATION_ERP_VAT_CODE);
        put(Country.lv, VatCode.ACCOMMODATION_ERP_VAT_CODE);
        put(Country.lt, VatCode.LT_ACCOMMODATION_ERP_VAT_CODE);
    }};

    public static Map<Country, String> receiptAccommodationVatCodeMapper = new HashMap<>(){{
        put(Country.ee, VatCode.ET_RECEIPT_ACCOMMODATION_ERP_VAT_CODE);
        put(Country.lv, VatCode.RECEIPT_ACCOMMODATION_ERP_VAT_CODE);
        put(Country.lt, VatCode.LT_RECEIPT_ACCOMMODATION_ERP_VAT_CODE);
    }};

    public static Map<Country, String> giftCardVatCodeMapper = new HashMap<>(){{
        put(Country.ee, VatCode.ET_GIFT_CARD_ERP_VAT_CODE);
        put(Country.lv, VatCode.GIFT_CARD_ERP_VAT_CODE);
        put(Country.lt, VatCode.LT_GIFT_CARD_ERP_VAT_CODE);
    }};

    public static Map<Country, String> otherIncomeVatCodeMapper = new HashMap<>(){{
        put(Country.ee, VatCode.ET_OTHER_INCOMED_ERP_VAT_CODE);
        put(Country.lv, VatCode.OTHER_INCOME_ERP_VAT_CODE);
        put(Country.lt, VatCode.LT_OTHER_INCOME_ERP_VAT_CODE);
    }};

    public static Map<Country, BigDecimal> serviceVatPercentMapper = new HashMap<>(){{
        put(Country.ee, VatCode.ET_SERVICE_VAT_PERCENT);
        put(Country.lv, VatCode.SERVICE_VAT_PERCENT);
        put(Country.lt, VatCode.LT_SERVICE_VAT_PERCENT);
    }};


    public static Map<Country, BigDecimal> accommodationVatPercentMapper = new HashMap<>(){{
        put(Country.ee, VatCode.ET_ACCOMMODATION_VAT_PERCENT);
        put(Country.lv, VatCode.ACCOMMODATION_VAT_PERCENT);
        put(Country.lt, VatCode.LT_ACCOMMODATION_VAT_PERCENT);
    }};

    public static Map<Country, BigDecimal> receiptServiceVatPercentMapper = new HashMap<>(){{
        put(Country.ee, VatCode.ET_RECEIPT_SERVICE_VAT_PERCENT);
        put(Country.lv, VatCode.RECEIPT_SERVICE_VAT_PERCENT);
        put(Country.lt, VatCode.LT_RECEIPT_SERVICE_VAT_PERCENT);
    }};

    public static Map<Country, BigDecimal> receiptAccommodationVatPercentMapper = new HashMap<>(){{
        put(Country.ee, VatCode.ET_RECEIPT_ACCOMMODATION_VAT_PERCENT);
        put(Country.lv, VatCode.RECEIPT_ACCOMMODATION_VAT_PERCENT);
        put(Country.lt, VatCode.LT_RECEIPT_ACCOMMODATION_VAT_PERCENT);
    }};

    public static final String getServiceVatCode(Country country){
        return serviceVatCodeMapper.get(country);
    }

    public static final String getReceiptServiceVatCode(Country country){
        return receiptServiceVatCodeMapper.get(country);
    }

    public static final String getAccommodationVatCode(Country country){
        return accommodationVatCodeMapper.get(country);
    }

    public static final String getReceiptAccommodationVatCode(Country country){
        return receiptAccommodationVatCodeMapper.get(country);
    }

    public static final String getGiftCardVatCode(Country country){
        return giftCardVatCodeMapper.get(country);
    }

    public static final BigDecimal getServiceVatPercent(Country country){
        return serviceVatPercentMapper.get(country);
    }

    public static final BigDecimal getReceiptServiceVatPercent(Country country){
        return receiptServiceVatPercentMapper.get(country);
    }

    public static final BigDecimal getAccommodationVatPercent(Country country){
        return accommodationVatPercentMapper.get(country);
    }

    public static final BigDecimal getReceiptAccommodationVatPercent(Country country){
        return receiptAccommodationVatPercentMapper.get(country);
    }
}
