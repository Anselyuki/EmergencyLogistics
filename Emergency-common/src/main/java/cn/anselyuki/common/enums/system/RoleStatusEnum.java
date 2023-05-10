package cn.anselyuki.common.enums.system;

/**
 * @author AnselYuki
 * @date 2022/5/29 16:52
 **/

public enum RoleStatusEnum {
    //用户状态
    DISABLE(0),
    AVAILABLE(1);
    private int statusCode;

    RoleStatusEnum(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
