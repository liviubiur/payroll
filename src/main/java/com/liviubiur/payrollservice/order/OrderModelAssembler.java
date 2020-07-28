package com.liviubiur.payrollservice.order;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

  @Override
  public EntityModel<Order> toModel(Order entity) {
    EntityModel<Order> orderModel = new EntityModel<>(entity,
        linkTo(methodOn(OrderController.class).one(entity.getId())).withSelfRel(),
        linkTo(methodOn(OrderController.class).all()).withRel("orders"));

    if (entity.getStatus() == Status.IN_PROGRESS) {
      orderModel.add(linkTo(methodOn(OrderController.class)
          .cancel(entity.getId())).withRel("cancel"));
      orderModel.add(
          linkTo(methodOn(OrderController.class).complete(entity.getId())).withRel("complete"));
    }

    return orderModel;
  }
}
