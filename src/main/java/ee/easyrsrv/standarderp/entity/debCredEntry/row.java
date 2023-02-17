package ee.easyrsrv.standarderp.entity.debCredEntry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class row {

    public Long stp;
    public Long AccNumber;
    public BigDecimal DebVal;
    public BigDecimal CredVal;

    public row(){}

    public row(Long stp, Long AccNumber, BigDecimal DebVal, BigDecimal CredVal){
        this.stp = stp;
        this.AccNumber = AccNumber;
        this.DebVal = DebVal;
        this.CredVal = CredVal;
    }
}
