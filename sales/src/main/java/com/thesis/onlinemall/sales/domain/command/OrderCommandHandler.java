package com.thesis.onlinemall.sales.domain.command;

import com.thesis.onlinemall.sales.domain.aggregate.OrderAggregate;
import com.thesis.onlinemall.sales.domain.entity.Order;
import com.thesis.onlinemall.sales.domain.entity.OrderConstants.ORDER_STATUS;
import com.thesis.onlinemall.sales.domain.entity.Shipping;
import com.thesis.onlinemall.sales.infrastructure.entity.TOrder;
import com.thesis.onlinemall.sales.infrastructure.entity.TOrderProduct;
import com.thesis.onlinemall.sales.infrastructure.entity.TShipping;
import com.thesis.onlinemall.sales.infrastructure.repository.OrderItemRepository;
import com.thesis.onlinemall.sales.infrastructure.repository.OrderRepository;
import com.thesis.onlinemall.sales.infrastructure.repository.ShippingRepository;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.Repository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCommandHandler {

  @Autowired
  private Repository<OrderAggregate> repository;
  @Autowired
  private ShippingRepository shippingRepository;
  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @CommandHandler
  public void handle(SubmitOrderCommand submitOrderCommand) throws Exception {
    Order order = submitOrderCommand.getOrder();

    //收货地址
    Shipping shipping = order.getShipping();
    if (StringUtils.isEmpty(shipping.getId())) {
      TShipping tShipping = new TShipping();
      BeanUtils.copyProperties(shipping, tShipping);
      tShipping.setId(UUID.randomUUID().toString());
      shippingRepository.save(tShipping);
      shipping.setId(tShipping.getId());
    }

    //订单商品
    order.getOrderItems().forEach(orderItem -> {
      orderItem.setId(UUID.randomUUID().toString());
      TOrderProduct product = new TOrderProduct();
      BeanUtils.copyProperties(orderItem, product);
      orderItemRepository.save(product);
    });

    TOrder tOrder = new TOrder();
    BeanUtils.copyProperties(order, tOrder);
    tOrder.setShippingid(shipping.getId());
    tOrder.setStatus(order.getStatus().ordinal());
    orderRepository.save(tOrder);


    repository.newInstance(
        () -> new OrderAggregate(
            submitOrderCommand.getOrderid(), submitOrderCommand.getOrder()));
  }

  /**
   * 订单处理命令
   *
   * @param orderUpdateCommand OrderUpdateCommand
   */
  @CommandHandler
  public void handle(OrderUpdateCommand orderUpdateCommand) {
    Order order = orderUpdateCommand.getOrder();
    order.setOrderid(orderUpdateCommand.getOrderid());
    Aggregate<OrderAggregate> aggregate = repository.load(orderUpdateCommand.getOrderid());
    aggregate.execute(aggregateRoot -> aggregateRoot
        .applyStatus(order, orderUpdateCommand.getCommand()));
  }

}
