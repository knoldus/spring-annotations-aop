package com.knoldus.aspect;

import com.knoldus.helper.AOPHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class SmokeTestClientFilterAspectTests {

    @Mock
    private final AOPHelper aopHelper = Mockito.mock(AOPHelper.class);
    private final AOPFilterAspect aopFilterAspect = new AOPFilterAspect(aopHelper);
    private final ProceedingJoinPoint joinPoint = Mockito.mock(ProceedingJoinPoint.class);

    @Test
    void whenSuccessfulSmokeTestClientFilterPointcutIsCalledThenAnObjectIsReturned() throws Throwable {

        Mockito.when(joinPoint.proceed()).thenReturn(new Object());
        Mockito.when(aopHelper.isAppropriateSmokeTestClientId(joinPoint)).thenReturn(true);

        Object aopFilterObject = aopFilterAspect.successfulAOPFilter(joinPoint);
        Assertions.assertNotNull(aopFilterObject);
    }

    @Test
    void whenSuccessfulSmokeTestClientFilterPointcutIsCalledThenAnyObjectIsReturned() throws Throwable {

        Mockito.when(aopHelper.isAppropriateSmokeTestClientId(joinPoint)).thenReturn(false);

        Object aopFilterObject = aopFilterAspect.successfulAOPFilter(joinPoint);
        Assertions.assertNotNull(aopFilterObject);
    }
}