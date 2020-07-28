package com.liviubiur.payroll;

import com.liviubiur.payroll.employee.Employee;
import com.liviubiur.payroll.employee.EmployeeRepository;
import com.liviubiur.payroll.order.Order;
import com.liviubiur.payroll.order.OrderRepository;
import com.liviubiur.payroll.order.Status;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(EmployeeRepository repository, OrderRepository orderRepository) {

    return args -> {
      log.info("Preloading " + repository.save(new Employee("Marco", "Rossi", "engineer")));
      log.info("Preloading " + repository.save(new Employee("Mark", "Foo","designer")));

      orderRepository.save(new Order("Asus", Status.COMPLETED));
      orderRepository.save(new Order("Kindle", Status.IN_PROGRESS));

      orderRepository.findAll().forEach(order -> {
        log.info("Preload " + order);
      });
    };
  }
}
