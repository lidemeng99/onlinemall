package com.thesis.onlinemall.sales.domain.service;

import com.thesis.onlinemall.sales.domain.command.OrderUpdateCommand;
import com.thesis.onlinemall.sales.domain.command.SubmitOrderCommand;
import com.thesis.onlinemall.sales.domain.entity.Order;
import com.thesis.onlinemall.sales.domain.entity.OrderFactory;
import com.thesis.onlinemall.sales.domain.entity.OrderItem;
import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class OrderCommandRestController {

  @Autowired
  private CommandGateway commandGateway;

  @PostMapping("/order")
  public String createOrder(@RequestBody SubmitOrderCommand submitOrderCommand) {
    String id = UUID.randomUUID().toString();
    Order eventOrder = submitOrderCommand.getOrder();
    Order order = OrderFactory
        .create(id, eventOrder.getOpenid(), eventOrder.getPostage(), eventOrder.getModifier());
    order.addShipping(eventOrder.getShipping());

    eventOrder.getOrderItems().forEach(item -> {
      order.addOrderItem(new OrderItem(item.getId(), id, item.getProductid(), item.getProductname(),
          item.getUnitprice(), item.getQuanlity()));
    });
    submitOrderCommand.setOrder(order);
    submitOrderCommand.setOrderid(id);
    commandGateway.sendAndWait(submitOrderCommand);
    return id;
  }

  @PutMapping("/order/{id}/status")
  public void updateState(@PathVariable String id,
      @RequestBody OrderUpdateCommand orderUpdateCommand) {
    orderUpdateCommand.setOrderid(id);
    orderUpdateCommand.getOrder().setOrderid(id);
    commandGateway.sendAndWait(orderUpdateCommand);
  }

}
