package com.hometest.restspringbootjpa.payroll.employee;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

  private final EmployeeRepository repository;
  private final EmployeeResourceAssembler assembler;

  public EmployeeController(EmployeeRepository repository, EmployeeResourceAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }

  @GetMapping("/employees")
  public CollectionModel<EntityModel<Employee>> all() {

    List<EntityModel<Employee>> employees = repository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

    return new CollectionModel<>(employees,
        linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
  }

  @PostMapping("/employees")
  public ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) throws URISyntaxException {

    EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));

    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @GetMapping("/employees/{id}")
  public EntityModel<Employee> one(@PathVariable Long id) {

    Employee employee = repository.findById(id)
        .orElseThrow(() -> new EmployeeNotFoundException(id));
    return assembler.toModel(employee);
  }

  @PutMapping("/employees/{id}")
  public ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id)
      throws URISyntaxException {

    Employee updateEmployee = repository.findById(id)
        .map(employee -> {
        employee.setName(newEmployee.getName());
        employee.setRole(newEmployee.getRole());
        return repository.save(employee);
    })
        .orElseGet(() -> {
          newEmployee.setId(id);
          return repository.save(newEmployee);
        });

    EntityModel<Employee> entityModel = assembler.toModel(updateEmployee);

    return  ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @DeleteMapping("/employees/{id}")
  public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
    repository.deleteById(id);

    return ResponseEntity.noContent().build();
  }

}
