package top.banner.security;

import lombok.Data;

@Data
public class Result {
    private String code;
    private String desc;

    private Result() {

    }

    public static Result build() {
        return new Result();
    }

    public static Result build(String code) {
        return new Result().code(code).desc(code);
    }

    public Result code(String code) {
        this.code = code;
        return this;
    }

    public Result desc(String desc) {
        this.desc = desc;
        return this;
    }
}
