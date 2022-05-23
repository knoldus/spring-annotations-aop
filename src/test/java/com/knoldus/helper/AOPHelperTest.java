package com.knoldus.helper;

import com.knoldus.util.ObjectCreator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AOPHelperTest {
    AOPHelper aopHelper = new AOPHelper();
    ProceedingJoinPoint joinPoint = Mockito.mock(ProceedingJoinPoint.class);
    MethodSignature signature = Mockito.mock(MethodSignature.class);

    @BeforeEach
    void init() throws NoSuchMethodException {
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getMethod()).thenReturn(ObjectCreator.dummyMethod());
    }

    @Test
    void whenCorrectClientIdIsPassedThenSmokeTestHelperReturnTrue() throws IllegalAccessException {
        Mockito.when(joinPoint.getArgs()).thenReturn(ObjectCreator.successfulEmployeeObject());
        Assertions.assertTrue(aopHelper.isAppropriateSmokeTestClientId(joinPoint));
    }

    @Test
    void whenWrongClientIdIsPassedThenSmokeTestHelperReturnFalse() throws IllegalAccessException {
        Mockito.when(joinPoint.getArgs()).thenReturn(ObjectCreator.failingEmployeeObject());
        Assertions.assertFalse(aopHelper.isAppropriateSmokeTestClientId(joinPoint));
    }
}
