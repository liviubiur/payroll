package com.liviubiur.payrollservice.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CUSTOMER_ORDER")
public class Order {

  @Id
  @GeneratedValue
  private Long id;
  private String description;
  private Status status;

  public Order(String description, Status status) {
    this.description = description;
    this.status = status;
  }

}
