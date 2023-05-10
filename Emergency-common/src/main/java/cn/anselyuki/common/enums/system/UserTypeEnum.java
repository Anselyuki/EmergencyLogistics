package cn.anselyuki.common.enums.system;

/**
 * 用户类型
 *
 * @author AnselYuki
 * @date 2022/5/29 12:25
 **/
public enum UserTypeEnum {
    // 系统管理员admin
    SYSTEM_ADMIN(0),
    // 系统的普通用户
    SYSTEM_USER(1);

    private int typeCode;

    UserTypeEnum(int typeCode) {
        this.typeCode = typeCode;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }
}
