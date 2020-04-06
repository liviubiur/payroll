package com.hometest.restspringbootjpa.payroll;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hometest.restspringbootjpa.payroll.employee.Employee;
import com.hometest.restspringbootjpa.payroll.employee.EmployeeRepository;
import com.hometest.restspringbootjpa.payroll.order.Order;
import com.hometest.restspringbootjpa.payroll.order.OrderRepository;
import com.hometest.restspringbootjpa.payroll.order.Status;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(EmployeeRepository repository, OrderRepository orderRepository) {

    return args -> {
      log.info("Preloading " + repository.save(new Employee("Bilbo", "Todos", "burglar")));
      log.info("Preloading " + repository.save(new Employee("Frodo", "Blabod","thief")));

      orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
      orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

      orderRepository.findAll().forEach(order -> {
        log.info("Preload " + order);
      });
    };
  }
}
