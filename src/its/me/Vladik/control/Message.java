package its.me.Vladik.control;

public class Message {
    public enum Type {
        WARNING, ERROR;
    }
    public int code;
    public Type type;
    public String causeBy;
    public int line;

    public Message(Type type, int code, Map map) {
        this.type = type;
        this.code = code;
        this.causeBy = map.figureName;
        this.line = map.line;
    }

    public Message(Type type, int code, String causeBy, int line) {
        this.type = type;
        this.code = code;
        this.causeBy = causeBy;
        this.line = line;
    }
    /*
    WARNINGs code:                    ERRORs code:
    1. Non-existable field      1. non-existable figure
    2. Num of " not odd         2. Invalid value of field
    3. Point1>point2            3. Points have not been initialized
                                4. Field value have not been found
     */


}