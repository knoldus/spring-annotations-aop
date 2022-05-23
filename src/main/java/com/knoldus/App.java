package com.knoldus;

import com.knoldus.config.AppConfig;
import com.knoldus.events.Employee;
import com.knoldus.service.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        Service service = applicationContext.getBean(Service.class);
        Employee employee = new Employee.Builder("Nikunj", "developer", "K123").build();

        service.run(employee);
    }
}
