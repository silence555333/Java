package kw.kd.dss.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-25 10:36
 */
public abstract class DWCException extends Exception{
    static String applicationName;
    static String hostname;
    static int hostPort;
    private int errCode;
    private String desc;
    private String ip;
    private int port;
    private String serviceKind;

    public static void setApplicationName(String applicationName) {
        applicationName = applicationName;
    }

    public static void setHostname(String hostname) {
        hostname = hostname;
    }

    public static void setHostPort(int hostPort) {
        hostPort = hostPort;
    }

    public DWCException(int errCode, String desc) {
        this(errCode, desc, hostname, hostPort, applicationName);
    }

    public DWCException(int errCode, String desc, String ip, int port, String serviceKind) {
        super("errCode: " + errCode + " ,desc: " + desc + " ,ip: " + ip + " ,port: " + port + " ,serviceKind: " + serviceKind);
        this.errCode = errCode;
        this.desc = desc;
        this.ip = ip;
        this.port = port;
        this.serviceKind = serviceKind;
    }

    public int getErrCode() {
        return this.errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServiceKind() {
        return this.serviceKind;
    }

    public void setServiceKind(String serviceKind) {
        this.serviceKind = serviceKind;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> retMap = new HashMap();
        retMap.put("level", this.getLevel().getLevel());
        retMap.put("errCode", this.getErrCode());
        retMap.put("desc", this.getDesc());
        retMap.put("ip", this.getIp());
        retMap.put("port", this.getPort());
        retMap.put("serviceKind", this.getServiceKind());
        return retMap;
    }

    abstract ExceptionLevel getLevel();

    public String toString() {
        return "DWCException{errCode=" + this.errCode + ", desc='" + this.desc + '\'' + ", ip='" + this.ip + '\'' + ", port=" + this.port + ", serviceKind='" + this.serviceKind + '\'' + '}';
    }
}
