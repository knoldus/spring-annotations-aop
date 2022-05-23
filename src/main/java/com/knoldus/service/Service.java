package com.knoldus.service;

import com.knoldus.aspect.AOPFilter;
import com.knoldus.events.Employee;
import org.springframework.stereotype.Component;

@Component
public class Service {
    @AOPFilter(filterValue = "developer", fieldToScan = "designation")
    public void run(Employee employee) {
        System.out.println("Developer details:");
        System.out.println(employee.getName() + "\n" + employee.getEmpId());
    }
}
