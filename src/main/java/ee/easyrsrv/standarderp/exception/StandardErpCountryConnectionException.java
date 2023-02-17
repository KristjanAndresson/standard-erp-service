package ee.easyrsrv.standarderp.exception;

import ee.easyrsrv.standarderp.enums.Country;
import lombok.Data;

@Data
public class StandardErpCountryConnectionException extends RuntimeException {

    public Country country;
    public String message;

    public StandardErpCountryConnectionException(Country country){
        this.country = country;
        this.message = String.format("There is no ERP connection set for country %s.", country);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
