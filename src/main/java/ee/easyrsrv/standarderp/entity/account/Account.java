package ee.easyrsrv.standarderp.entity.account;


import ee.easyrsrv.standarderp.enums.Country;
import ee.easyrsrv.standarderp.enums.StandardErpArticleCode;

import java.util.HashMap;
import java.util.Map;

public class Account {

    public static Long PREPAYMENT_SALES_ACCOUNT = 5210L;
    public static Long ET_PREPAYMENT_SALES_ACCOUNT = 2210L;
    public static Long LT_PREPAYMENT_SALES_ACCOUNT = 2210L;

    public static Long GIFT_CARD_SALES_ACCOUNT = 5910L;

    public static Long ACCOMMODATION_SALES_ACCOUNT = 6111L;
    public static Long ET_ACCOMMODATION_SALES_ACCOUNT = 3300L;
    public static Long LT_ACCOMMODATION_SALES_ACCOUNT = 3520L;

    public static Long SERVICE_SALES_ACCOUNT = 6110L;
    public static Long ET_SERVICE_SALES_ACCOUNT = 3200L;
    public static Long LT_SERVICE_SALES_ACCOUNT = 3510L;

    public static Long COMMISSION_SALES_ACCOUNT = 6310L;
    public static Long ET_COMMISSION_SALES_ACCOUNT = 3100L;
    public static Long LT_COMMISSION_SALES_ACCOUNT = 3510L;

    public static Long OTHER_INCOME_ACCOUNT = 8160L;
    public static Long ET_OTHER_INCOME_ACCOUNT = 3990L;
    public static Long LT_OTHER_INCOME_ACCOUNT = 3990L;

    public static Map<Country, Long> prepaymentAccountMapper = new HashMap<>(){{
        put(Country.ee, Account.ET_PREPAYMENT_SALES_ACCOUNT);
        put(Country.lv, Account.PREPAYMENT_SALES_ACCOUNT);
        put(Country.lt, Account.LT_PREPAYMENT_SALES_ACCOUNT);
    }};

    public static Map<Country, Long> accommodationAccountMapper = new HashMap<>(){{
        put(Country.ee, Account.ET_ACCOMMODATION_SALES_ACCOUNT);
        put(Country.lv, Account.ACCOMMODATION_SALES_ACCOUNT);
        put(Country.lt, Account.LT_ACCOMMODATION_SALES_ACCOUNT);
    }};

    public static Map<Country, Long> serviceAccountMapper = new HashMap<>(){{
        put(Country.ee, Account.ET_SERVICE_SALES_ACCOUNT);
        put(Country.lv, Account.SERVICE_SALES_ACCOUNT);
        put(Country.lt, Account.LT_SERVICE_SALES_ACCOUNT);
    }};

    public static Map<Country, Long> commissionAccountMapper = new HashMap<>(){{
        put(Country.ee, Account.ET_COMMISSION_SALES_ACCOUNT);
        put(Country.lv, Account.COMMISSION_SALES_ACCOUNT);
        put(Country.lt, Account.LT_COMMISSION_SALES_ACCOUNT);
    }};

    public static Map<Country, Long> otherIncomeAccountMapper = new HashMap<>(){{
        put(Country.ee, Account.ET_OTHER_INCOME_ACCOUNT);
        put(Country.lv, Account.OTHER_INCOME_ACCOUNT);
        put(Country.lt, Account.LT_OTHER_INCOME_ACCOUNT);
    }};

    public static Map<StandardErpArticleCode,Map<Country, Long>> articleToAccountMapper = new HashMap<>(){{
        put(StandardErpArticleCode.MAJUTUS, accommodationAccountMapper);
        put(StandardErpArticleCode.TEENUS, serviceAccountMapper);
        put(StandardErpArticleCode.KOMISJON, commissionAccountMapper);
        put(StandardErpArticleCode.KINKEK, serviceAccountMapper);
        put(StandardErpArticleCode.MAJUTUSEM, prepaymentAccountMapper);
        put(StandardErpArticleCode.TEENUSEM, prepaymentAccountMapper);
        put(StandardErpArticleCode.TRAHV, otherIncomeAccountMapper);
    }};
}
