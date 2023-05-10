package cn.anselyuki.common.enums.system;

/**
 * 用户状态
 *
 * @author AnselYuki
 * @date 2022/5/29 12:29
 **/
public enum UserStatusEnum {
    //用户状态枚举
    DISABLE(0),
    AVAILABLE(1);

    private int statusCode;

    UserStatusEnum(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
