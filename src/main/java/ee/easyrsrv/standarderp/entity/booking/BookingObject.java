package ee.easyrsrv.standarderp.entity.booking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.easyrsrv.standarderp.entity.AbstractStandardErpEntity;
import lombok.Data;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingObject extends AbstractStandardErpEntity {

    public static String OBJECT_NAME = "ObjVc";

    public String UUID;
    public String Code;
    public String Comment;

    public BookingObject(){}

    public BookingObject(String code, String comment){
        this.Code = code;
        this.Comment = comment;
    }

    public static String getObjectName() {
        return OBJECT_NAME;
    }

    public String getIdentificatorValue(){
        return this.Code;
    }

    public List<NameValuePair> getErpFindOneParams(){
        if (Code == null) {
            return null;
        } else {
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("filter.Code", Code));
            return params;
        }
    }

    public List<NameValuePair> getUpdateParams(){
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("set_field.Code", Code));
        params.add(new BasicNameValuePair("set_field.Comment", Comment));

        return params;
    }

    public String getUpdateParamsString(){
        List<String> res = new ArrayList<>();

        for(NameValuePair nameValuePair: getUpdateParams()){
            res.add(String.format("%s=%s", nameValuePair.getName(), nameValuePair.getValue()));
        }

        return String.join("&", res);
    }
}
