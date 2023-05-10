package cn.anselyuki.common.error;

/**
 * 自定义的错误描述枚举类需实现该接口
 *
 * @author AnselYuki
 * @date 2022/9/1 14:49
 **/
public interface BaseError {

    /**
     * 获取错误码
     *
     */
    int getErrorCode();

    /**
     * 获取错误信息
     *
     */
    String getErrorMsg();

    /**
     * 设置错误信息
     *
     */
    void setErrorMsg(String message);
}
