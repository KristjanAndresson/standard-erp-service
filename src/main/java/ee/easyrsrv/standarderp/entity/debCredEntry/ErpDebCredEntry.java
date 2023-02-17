package ee.easyrsrv.standarderp.entity.debCredEntry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.easyrsrv.standarderp.entity.AbstractStandardErpEntity;
import lombok.Data;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErpDebCredEntry extends AbstractStandardErpEntity {

    public static Long ET_GIFT_CARD_REALIZATION_DEBIT_ACCOUNT_CODE = 2220L;
    public static Long ET_GIFT_CARD_REALIZATION_CREDIT_ACCOUNT_CODE = 3991L;

    public static String OBJECT_NAME = "TRVc";

    public String Number;
    public String RegDate;
    public String Comment;
    public String IntYc;
    public List<row> rows = new ArrayList<>();

    public ErpDebCredEntry(){}

    public ErpDebCredEntry(String Comment, List<row> rows){
        this.Comment = Comment;
        this.rows = rows;
    }

    public static String getObjectName() {
        return OBJECT_NAME;
    }
    public static void setObjectName(String objectName) {
        OBJECT_NAME = objectName;
    }

    public String getNumber() {
        return Number;
    }

    public String getRegDate() {
        return RegDate;
    }

    public String getComment() {
        return Comment;
    }

    public String getIntYc() {
        return IntYc;
    }

    public List<row> getRows() {
        return rows;
    }

    public String getIdentificatorValue(){
        return this.Number;
    }

    public List<NameValuePair> getErpFindOneParams(){
        if (Number == null) {
            return null;
        } else {
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("filter.Number", Number));
            return params;
        }
    }

    public List<NameValuePair> getUpdateParams() {
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("set_field.Comment", Comment));

        updateRowParameters(params);

        return params;
    }

    private void updateRowParameters(List<NameValuePair> params){
        int index = 0;

        for(row row: rows){
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.stp", index), getStringValue(row.getStp())));
            params.add(new BasicNameValuePair(String.format("set_row_field.%s.AccNumber", index), getStringValue(row.getAccNumber())));
            if(row.getDebVal() != null){
                params.add(new BasicNameValuePair(String.format("set_row_field.%s.DebVal", index), getStringValue(row.getDebVal())));
            }
            if(row.getCredVal() != null){
                params.add(new BasicNameValuePair(String.format("set_row_field.%s.CredVal", index), getStringValue(row.getCredVal())));
            }

            index++;
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
