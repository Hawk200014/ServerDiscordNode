package de.hawk200014.serverdiscordnode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private String controllerIP;
    private String controllerSecret;
    private String controllerPort;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    private String serverName;

    public String getControllerIP() {
        return controllerIP;
    }

    public void setControllerIP(String controllerIP) {
        this.controllerIP = controllerIP;
    }

    public String getControllerSecret() {
        return controllerSecret;
    }

    public void setControllerSecret(String controllerSecret) {
        this.controllerSecret = controllerSecret;
    }

    public String getControllerPort() {
        return controllerPort;
    }

    public void setControllerPort(String controllerPort) {
        this.controllerPort = controllerPort;
    }

    public Config(String controllerIP, String controllersecret){
        this.controllerIP = controllerIP;
        this.controllerSecret = controllersecret;
    }

    public Config(){
        this.controllerIP = "SetTheControllerIP";
        this.controllerPort = "SetTheControllerPort(DEFAULT:35565)";
        this.controllerSecret = "SetTheControllerSecret";
        this.serverName = "SetServerName";
    }

    public void load(File configFile){
        FileInputStream fis = null;
        Properties properties = new Properties();

        try {
            fis = new FileInputStream(configFile);
            properties.load(fis);
            this.controllerIP = properties.getProperty("ControllerIP");
            this.controllerPort = properties.getProperty("ControllerPort");
            this.controllerSecret = properties.getProperty("ControllerSecret");
            this.serverName = properties.getProperty("ServerName");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setInitValues(File configFile){
        FileInputStream fis = null;
        Properties properties = new Properties();
        try {
            fis = new FileInputStream(configFile);
            properties.load(fis);
            properties.setProperty("ControllerIP", this.controllerIP);
            properties.setProperty("ControllerPort", this.controllerPort);
            properties.setProperty("ControllerSecret", this.controllerSecret);
            properties.setProperty("ServerName", this.serverName);
            properties.store(new FileWriter(configFile.getAbsolutePath()),"");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
