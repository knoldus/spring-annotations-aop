package com.knoldus.aspect;

import com.knoldus.helper.AOPHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
public class AOPFilterAspect {
    private final Logger logger = LoggerFactory.getLogger(AOPFilterAspect.class);
    private final AOPHelper aopHelper;

    public AOPFilterAspect(AOPHelper aopHelper) {
        this.aopHelper = aopHelper;
    }

    @Around(value = "@annotation(com.knoldus.aspect.AOPFilter)")
    public Object successfulAOPFilter(ProceedingJoinPoint joinPoint) throws Throwable {

        if (aopHelper.isAppropriateSmokeTestClientId(joinPoint)) {
            return joinPoint.proceed();
        } else {
            logger.trace("Skipping the flow because this type of clientId is for Smoke Tests");
            return new Object();
        }
    }

}
