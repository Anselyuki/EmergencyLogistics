package cn.anselyuki.common.error;

import lombok.Data;

import java.util.Objects;

/**
 * 业务异常
 *
 * @author AnselYuki
 * @date 2022/9/1 14:47
 **/
@Data
public class BusinessException extends Exception implements BaseError {

    // 所有实现了BaseError的ErrorEnum.
    private BaseError baseError;

    // 直接构造错误消息的构造异常
    public BusinessException(BaseError baseError) {
        super(baseError.getErrorMsg());
        this.baseError = baseError;
    }

    // 自定义错误消息的构造异常
    public BusinessException(BaseError baseError, String customErrorMessage) {
        super();
        this.baseError = baseError;
        this.baseError.setErrorMsg(customErrorMessage);
    }

    @Override
    public int getErrorCode() {
        return this.baseError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.baseError.getErrorMsg();
    }

    @Override
    public void setErrorMsg(String message) {
        this.baseError.setErrorMsg(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusinessException that = (BusinessException) o;
        return Objects.equals(baseError, that.baseError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseError);
    }
}
