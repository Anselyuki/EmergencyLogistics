package cn.anselyuki.system.aspect;

import cn.anselyuki.common.annotation.ControllerEndpoint;
import cn.anselyuki.common.model.system.Log;
import cn.anselyuki.common.response.ActiveUser;
import cn.anselyuki.common.utils.AddressUtil;
import cn.anselyuki.common.utils.IPUtil;
import cn.anselyuki.system.service.LogService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 系统日志切面
 *
 * @author AnselYuki
 */
@Slf4j
@Aspect
@Component
public class ControllerEndpointAspect extends AbstractAspectSupport {
    private final Log sysLog = new Log();
    private final LogService logService;
    @Autowired
    public ControllerEndpointAspect(LogService logService) {
        this.logService = logService;
    }

    @Pointcut("@annotation(cn.anselyuki.common.annotation.ControllerEndpoint)")
    public void pointcut() {
    }

    /**
     * 环绕通知
     *
     */
    @Around("pointcut()")
    public Object saveSysLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        // 开始时间
        long startTime = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取注解
        ControllerEndpoint controllerEndpoint = method.getAnnotation(ControllerEndpoint.class);
        if (controllerEndpoint != null) {
            String operation = controllerEndpoint.operation();
            // 注解上的操作描述
            sysLog.setOperation(operation);
        }

        // 请求的参数
        Object[] args = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        sysLog.setParams("paramName:" + Arrays.toString(paramNames) + ",args:" + Arrays.toString(args));

        // 请求的IP
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        String ipAddr = IPUtil.getIpAddr(request);
        sysLog.setIp(ipAddr);
        // 地理位置
        sysLog.setLocation(AddressUtil.getCityInfo(ipAddr));
        // 操作人
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        sysLog.setUsername(activeUser.getUser().getUsername());
        // 添加时间
        sysLog.setCreateTime(new Date());
        // 执行目标方法
        result = joinPoint.proceed();
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()\n"
                + "\nresponse:" + postHandle(result));
        // 执行耗时
        sysLog.setTime(System.currentTimeMillis() - startTime);
        // 保存系统日志
        logService.saveLog(sysLog);

        return result;
    }

    /**
     * 返回数据
     *
     */
    private String postHandle(Object retVal) {
        if (null == retVal) {
            return "";
        }
        return JSON.toJSONString(retVal);
    }
}
