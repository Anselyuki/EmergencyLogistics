package cn.anselyuki.common.enums.buisiness;

/**
 * 业务用户类型
 *
 * @author AnselYuki
 * @date 2022/9/15 18:37
 **/
public enum BizUserTypeEnum {
    //用户类型枚举
    DEAN();

    private String val;

    BizUserTypeEnum() {
        this.val = "部门主任";
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
