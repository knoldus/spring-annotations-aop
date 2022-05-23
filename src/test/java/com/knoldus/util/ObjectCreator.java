package com.knoldus.util;

import com.knoldus.aspect.AOPFilter;
import com.knoldus.events.Employee;

import java.lang.reflect.Method;

public class ObjectCreator {

    private final static Object[] args = new Object[1];
    AOPFilter aopFilter;

    public static Method dummyMethod() throws NoSuchMethodException {
        return ObjectCreator.class.getDeclaredMethod("getAnnotation");
    }

    public static Object[] successfulEmployeeObject() {
        Employee employee = new Employee.Builder(Constants.NAME.getName(), "developer", Constants.EMP_ID.getName()).build();
        args[0] = employee;
        return args;
    }

    public static Object[] failingEmployeeObject() {
        Employee employee = new Employee.Builder(Constants.NAME.getName(), "qa", Constants.EMP_ID.getName()).build();
        args[0] = employee;
        return args;
    }

    @AOPFilter(filterValue = "developer", fieldToScan = "designation")
    public AOPFilter getAnnotation() {
        return aopFilter;
    }
}
