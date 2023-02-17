package ee.easyrsrv.standarderp.exception;

import lombok.Data;

@Data
public class StandardErpApiException extends RuntimeException {

    public String code;
    public String description;
    public String row;
    public String field;
    public String message;

    public StandardErpApiException(String code, String description, String row, String field){
        this.code = code;
        this.description = description;
        this.row = row;
        this.field = field;
        this.message = String.format("StandardErpApiException. Code: %s, Description: %s, Row: %s, Field: %s", code, description, row, field);
    }

    public StandardErpApiException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
