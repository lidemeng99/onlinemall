package com.thesis.onlinemall.sales.service;

import com.thesis.onlinemall.sales.domain.command.OrderCommand;
import com.thesis.onlinemall.sales.domain.command.OrderUpdateCommand;
import com.thesis.onlinemall.sales.domain.entity.Order;
import com.thesis.onlinemall.sales.domain.entity.OrderConstants.ORDER_STATUS;
import com.thesis.onlinemall.sales.infrastructure.entity.TOrder;
import com.thesis.onlinemall.sales.infrastructure.repository.OrderRepository;
import java.util.List;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class OrderStateChangeScheduleTask {

  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private OrderRepository orderRepository;

  /**
   * 自动将订单状态由支付变化待发货
   */
  @Scheduled(fixedRate = 10000)
  private void confirmPayment() {
    List<TOrder> orders = orderRepository.findByStates(ORDER_STATUS.PAID.ordinal());
    orders.forEach(order -> {
      OrderUpdateCommand orderUpdateCommand = new OrderUpdateCommand();
      Order orderEntity = new Order();
      BeanUtils.copyProperties(order, orderEntity);
      orderEntity.setStatus(ORDER_STATUS.values()[order.getStatus()]);
      orderUpdateCommand.setOrderid(order.getOrderid());
      orderUpdateCommand.setOrder(orderEntity);
      orderUpdateCommand.setCommand(OrderCommand.TO_ALLOCATE);
      commandGateway.sendAndWait(orderUpdateCommand);//发送状态更新命令 支付完成后自动变成捡货
    });
  }

  /**
   * 收货完成后自动完成订单
   */
  @Scheduled(fixedRate = 10000)
  private void completeOrder() {
    List<TOrder> orders = orderRepository.findByStates(ORDER_STATUS.DELIVERED.ordinal());
    orders.forEach(order -> {
      OrderUpdateCommand orderUpdateCommand = new OrderUpdateCommand();
      Order orderEntity = new Order();
      BeanUtils.copyProperties(order, orderEntity);
      orderEntity.setStatus(ORDER_STATUS.values()[order.getStatus()]);
      orderUpdateCommand.setOrderid(order.getOrderid());
      orderUpdateCommand.setOrder(orderEntity);
      orderUpdateCommand.setCommand(OrderCommand.CLOSE);
      commandGateway.sendAndWait(orderUpdateCommand);//发送状态更新命令 支付完成后自动变成捡货
    });
  }

}
