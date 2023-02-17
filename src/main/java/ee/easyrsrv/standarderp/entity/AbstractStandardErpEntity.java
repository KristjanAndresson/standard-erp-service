package ee.easyrsrv.standarderp.entity;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStandardErpEntity {

    public List<String> messages = new ArrayList<>();

    public abstract String getIdentificatorValue();
    public abstract List<NameValuePair> getErpFindOneParams();
    public abstract List<NameValuePair> getUpdateParams();
    public abstract String getUpdateParamsString();

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String getStringValue(Object obj) {
        return obj != null ? obj.toString() : null;
    }
}
