package top.banner.shiro.service;

public class Result {

    private String code;
    private String desc;

    private Result() {
    }

    public static Result create() {
        return new Result();
    }

    public Result code(String code) {
        this.code = code;
        return this;
    }

    public Result desc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}