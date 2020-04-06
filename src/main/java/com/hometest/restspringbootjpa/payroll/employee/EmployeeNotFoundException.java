package com.hometest.restspringbootjpa.payroll.employee;

public class EmployeeNotFoundException extends RuntimeException {

  public EmployeeNotFoundException(Long id) {
    super("Could not find employee " + id);
  }
}
