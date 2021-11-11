package com.xiaomeng.dailycost.base;

public enum ReturnCode {
    /**操作成功**/
    RC100(100,"操作成功"),
    /**ID不存在**/
    RC4001(200,"id is not exist"),
    /**操作失败**/
    RC999(999,"操作失败"),
    /**服务异常**/
    RC500(500,"系统异常，请稍后重试"),

    RC_USERNAME_EXIST(400, "用户名已被占用"),
    RC_EMAIL_EXIST(400, "邮箱已使用过"),
    // Todo: catch the Exception in JWTAuthenticationFilter
    RC_USERNAME_OR_PASSWORD_ERROR(400, "用户名或密码错误"),

    RC_CATEGORY_ICON_NOT_EXIST(400, "类别ICON不存在"),
    RC_CATEGORY_NAME_EXIST(400,"类别名称已存在"),
    RC_CATEGORY_NOT_MATCH(400,"记账类别不匹配"),

    RC_ID_NOT_EXIST(400, "ID不存在"),

    RC_NO_DATA_ACCESS_AUTHRITY(401, "无权访问该数据");

    /**自定义状态码**/
    private final int code;
    /**自定义描述**/
    private final String message;

    ReturnCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
