package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.utils.SearchTracker;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ApiTrackerAspect {
	@Autowired
	private  SearchTracker tracker;
	
	@Around("@annotation(com.example.demo.utils.Tracker) && anyRestAPICall()") 
	public Object track(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
	{	
		long start = System.currentTimeMillis();
		Object value;

        try {
            value = proceedingJoinPoint.proceed();  
        } catch (Throwable throwable) {
            throw throwable;
        }finally {
            long duration = System.currentTimeMillis() - start;

            log.info(
                    "{}.{} took {} ms",
                    proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName(),
                    proceedingJoinPoint.getSignature().getName(),
                    duration);
            tracker.setApiTracker(proceedingJoinPoint.getSignature().getName());
            
        }

        return value;
	}

	
	@Pointcut("execution(* com.example.demo.api.*.*(..))")
	public void anyRestAPICall()
	{
		
	}


}
