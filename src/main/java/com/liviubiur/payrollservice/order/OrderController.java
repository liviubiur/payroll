package com.liviubiur.payrollservice.order;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors.VndError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.liviubiur.payrollservice.Constant.ID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderRepository orderRepository;
  private final OrderModelAssembler assembler;

  @Autowired
  public OrderController(OrderRepository orderRepository,
      OrderModelAssembler assembler) {
    this.orderRepository = orderRepository;
    this.assembler = assembler;
  }

  @GetMapping
  public CollectionModel<EntityModel<Order>> all() {

    List<EntityModel<Order>> orders = orderRepository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

    return new CollectionModel<>(orders,
        linkTo(methodOn(OrderController.class).all()).withSelfRel());
  }

  @GetMapping(ID)
  public EntityModel<Order> one(@PathVariable Long id) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new OrderNotFoundException(id));

    return assembler.toModel(order);
  }

  @PostMapping
  public ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {
    order.setStatus(Status.IN_PROGRESS);
    Order newOrder = orderRepository.save(order);

    return ResponseEntity
        .created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
        .body(assembler.toModel(newOrder));
  }

  @DeleteMapping(ID + "/cancel")
  public ResponseEntity<?> cancel(@PathVariable Long id) {
    Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

    if (order.getStatus() == Status.IN_PROGRESS) {
      order.setStatus(Status.CANCELLED);
      return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
    }

    return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(new VndError("Method not allowed",
            "You can't cancel an order that is in the " + order.getStatus() + " status"));
  }

  @PutMapping(ID + "/complete")
  public ResponseEntity<?> complete(@PathVariable Long id) {
    Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

    if (order.getStatus() == Status.IN_PROGRESS) {
      order.setStatus(Status.COMPLETED);
      return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
    }

    return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(new VndErrors.VndError("Method not allowed",
            "You can't complete an order that is in the " + order.getStatus() + " status"));
  }
}
