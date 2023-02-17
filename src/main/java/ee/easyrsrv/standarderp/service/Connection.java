package ee.easyrsrv.standarderp.service;

import lombok.Data;

@Data
public class Connection {

    private String erpUsername;
    private String erpPassword;
    private String erpHost;
    private int erpPort;
    private boolean isEnabled;
    private String path;

    public Connection(){}

    public Connection(String erpUsername, String erpPassword, String erpHost, int erpPort, boolean isEnabled){
        this(erpUsername, erpPassword, erpHost, erpPort, isEnabled, null);
    }

    public Connection(String erpUsername, String erpPassword, String erpHost, int erpPort, boolean isEnabled, String path){
        this.erpUsername = erpUsername;
        this.erpPassword = erpPassword;
        this.erpHost = erpHost;
        this.erpPort = erpPort;
        this.isEnabled = isEnabled;
        this.path = path != null ? path : "api/1/%s";
    }
}
