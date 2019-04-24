package com.mcgj.aop;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.mcgj.entity.Operation;
import com.mcgj.entity.User;
import com.mcgj.service.IOperationService;
import com.mcgj.utils.SpringUtil;
import com.mcgj.utils.UserUtil;

@Aspect
@Service
public class ServiceHelper {
	
	//定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.mcgj.aop.Log)")
    public void logPoinCut() {
    	
    }
	
    /**
     * 保存用户的操作
     * @param joinPoint
     */
    @AfterReturning("logPoinCut()")
    public void saveOperation(JoinPoint joinPoint){
    	this.addOperation(new Operation(), joinPoint);
    }
    
	@Pointcut("execution(public * com.mcgj.service.*Service.*(..))")
	public void mapperPoint() {
		
	}
	
	@Around("mapperPoint()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object proceed = pjp.proceed();
		//记录所有的查询操作
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		//获取方面名称
		String methodName = method.getName();
		if(!methodName.startsWith("select")){
			return proceed;
		}
		//获取类名
		this.addOperation(new Operation(), pjp);
		return proceed;
	}
	
	/**
	 * 保存操作数据
	 */
	private void addOperation(Operation operation,JoinPoint joinPoint){
    	//从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method =signature.getMethod();
        //获取方法的注解
        Log log = method.getAnnotation(Log.class);
        if(log != null){
        	operation.setOperation(log.value());
        }
        //获取request对象
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        //获取请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        for(String key:parameterMap.keySet()){
        	if(key != null && "token".equals(key)){
        		//根据token获取用户数据
        		User user = UserUtil.getCurrentUser(parameterMap.get(key)[0]);
        		if(user != null){
        			operation.setUserId(user.getId());
        		}
        	}
        }
        Object json = JSONObject.toJSON(parameterMap);
        if(json != null){
        	operation.setParams(json.toString());
        }
        //获取ip
        operation.setIp(request.getLocalAddr());
        //获取请求的类名称
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        operation.setMethod(className + "." + methodName);
        if(methodName.startsWith("select")){
        	operation.setOperation(OperationType.SELECT);
        }
    	IOperationService operationService = (IOperationService)SpringUtil.getBean(IOperationService.class);
    	operationService.insert(operation);
	}
	
}
