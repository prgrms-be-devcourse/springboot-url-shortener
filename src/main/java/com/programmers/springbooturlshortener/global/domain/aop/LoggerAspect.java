package com.programmers.springbooturlshortener.global.domain.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.programmers.springbooturlshortener.global.domain.exception.UrlApplicationException;

@Aspect
@Component
public class LoggerAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

	@Around("execution(* com.programmers.springbooturlshortener..*Controller.*(..))")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();

		long startTime = System.currentTimeMillis();
		Object proceed = joinPoint.proceed();
		long finishTime = System.currentTimeMillis();

		double invokeTime = (finishTime - startTime) / 1000.0;

		logger.info("invoke method : {} - {} - / timeTaken : {} s", className, methodName, invokeTime);
		return proceed;
	}

	@AfterThrowing(pointcut = "execution (* com.programmers.springbooturlshortener..*(..))", throwing = "urlApplicationException")
	public void uniCloudException(JoinPoint joinPoint, UrlApplicationException urlApplicationException) {
		logger.warn("\n", urlApplicationException);
	}
}