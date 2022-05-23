package com.knoldus.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Employee.Builder.class)
public class Employee {
    String name;
    String designation;
    String empId;

    private Employee(Builder builder) {
        name = builder.name;
        designation = builder.designation;
        empId = builder.empId;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getEmpId() {
        return empId;
    }

    public static final class Builder {
        private final String name;
        private final String designation;
        private final String empId;

        public Builder(@JsonProperty("name") String name, @JsonProperty("designation") String designation, @JsonProperty("empId") String empId) {

            this.name = name;
            this.designation = designation;
            this.empId = empId;
        }

        public Employee build() {
            return new Employee(this);
        }
    }
}
