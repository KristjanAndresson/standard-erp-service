package ee.easyrsrv.standarderp.entity.contact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.easyrsrv.standarderp.entity.AbstractStandardErpEntity;
import lombok.Data;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact extends AbstractStandardErpEntity {

    public static String OBJECT_NAME = "CUVc";

    public String UUID;
    public String Code;                     // unique contact code in ERP
    public String Name;                     // contact name
    public String InvAddr0;                 // invoice addressline 1
    public String CountryCode;              // EE, LV, LT, RU, FI jne
    public String RegNr1;                   // Reg no
    public String RegNr2;                   // Reference no
    public String VATNr;                    // VAT Reg no
    public String VEType;                      // supplier 0=no, 1=yes
    public String CUType;                      // customer 0=no, 1=yes
    public String Comment;                  // Comment
    public boolean isInsert = true;         // not erp specific field. Used to get update params
    public String IBANCode;
    public String Department;

    public Contact(){}

    public Contact(String registerNumber, String referenceNumber, String name, String address, String comment, String Department){
        this(registerNumber, referenceNumber, name, address, comment, null, Department);
    }

    public Contact(String registerNumber, String referenceNumber, String name, String address, String comment,
                   String IBANCode, String Department){
        this.RegNr1 = registerNumber;
        this.RegNr2 = referenceNumber;
        this.Name = name;
        this.InvAddr0 = address;
        this.Comment = comment;
        this.CUType = "1";
        this.IBANCode = IBANCode;
        this.Department = Department;
    }

    public Contact(String countryCode, String registerNumber, String referenceNumber, String vatNumber, String name,
                   String address, String comment, boolean isInsert, String Department){
        this(countryCode, registerNumber, referenceNumber, vatNumber, name, address, comment, isInsert, null, Department);
    }

    public Contact(String countryCode, String registerNumber, String referenceNumber, String vatNumber, String name,
                   String address, String comment, boolean isInsert, String IBANCode, String Department){
        this.CountryCode = countryCode;
        this.RegNr1 = registerNumber;
        this.RegNr2 = referenceNumber;
        this.Name = name;
        this.InvAddr0 = address;
        this.Comment = comment;
        this.VATNr = vatNumber;
        this.CUType = "1";
        this.VEType = "1";
        this.isInsert = isInsert;
        this.IBANCode = IBANCode;
        this.Department = Department;
    }


    public String getIdentificatorValue(){
        return this.Code;
    }

    public static String getObjectName() {
        return OBJECT_NAME;
    }

    public List<NameValuePair> getErpFindOneParams(){
        if (RegNr1 == null) {
            return null;
        } else {
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("filter.RegNr1", RegNr1));
            return params;
        }
    }

    public List<NameValuePair> getUpdateParams(){
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("set_field.Name", Name));
        params.add(new BasicNameValuePair("set_field.InvAddr0", InvAddr0));
        params.add(new BasicNameValuePair("set_field.Department", Department));
        if(VATNr != null){
            params.add(new BasicNameValuePair("set_field.VATNr", VATNr));
        }

        if(CountryCode != null) {
            params.add(new BasicNameValuePair("set_field.CountryCode", CountryCode));
        }

        if(IBANCode != null){
            params.add(new BasicNameValuePair("set_field.IBANCode", IBANCode));
        }

        params.add(new BasicNameValuePair("set_field.RegNr1", RegNr1));

        if(RegNr2 != null) {
            params.add(new BasicNameValuePair("set_field.RegNr2", RegNr2));
        }

        if(isInsert) {
            if(VEType != null){
                params.add(new BasicNameValuePair("set_field.VEType", VEType));
            }
            if(CUType != null){
                params.add(new BasicNameValuePair("set_field.CUType", CUType));
            }
            if(Comment != null){
                params.add(new BasicNameValuePair("set_field.Comment", Comment));
            }
        }

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
