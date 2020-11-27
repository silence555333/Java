package kw.kd.dss.exception;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-25 10:37
 */
public enum  ExceptionLevel {
    WARN(1, "warn"),
    ERROR(2, "error"),
    FATAL(3, "fatal"),
    RETRY(4, "retry");

    private int level;
    private String name;

    private ExceptionLevel(int level, String name) {
        this.name = name;
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "ExceptionLevel{level=" + this.level + ", name='" + this.name + '\'' + '}';
    }

}
