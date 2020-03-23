package com.thesis.onlinemall.sales.domain.event;

import com.thesis.onlinemall.sales.domain.entity.Order;
import com.thesis.onlinemall.sales.domain.service.ProductDeductService;
import com.thesis.onlinemall.sales.infrastructure.entity.TOrder;
import com.thesis.onlinemall.sales.infrastructure.repository.OrderItemRepository;
import com.thesis.onlinemall.sales.infrastructure.repository.OrderRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventHandler {

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ProductDeductService productDeductService;
  @Autowired
  private OrderItemRepository orderItemRepository;

  @EventHandler
  public void onEvent(OrderUpdatedEvent orderUpdatedEvent) throws Exception {
//
    Order order = orderUpdatedEvent.getOrder();
    Optional<TOrder> optional = orderRepository.findById(order.getOrderid());
    if (!optional.isPresent()) {
      throw new IllegalArgumentException(
          "can not found order <" + order.getOrderid() + ">");
    }
    TOrder tOrder = optional.get();
    tOrder.setLastmodifiedtime(order.getLastmodifiedtime());
    tOrder.setModifier(order.getModifier());
    tOrder.setStatus(order.getStatus().ordinal());
    switch (orderUpdatedEvent.getCommand()) {
      case PAY:
        tOrder.setPaymenttime(order.getPaymenttime());
        break;
      case DELIVERY:
        tOrder.setSendtime(order.getSendtime());
        break;
      case RECEIVING:
        tOrder.setEndtime(order.getEndtime());
        break;
      case CLOSE:
        tOrder.setClosetime(order.getClosetime());
        break;
      case CANCEL:
        tOrder.setLastmodifiedtime(order.getLastmodifiedtime());
        //增加库存
        orderItemRepository.findAllItemsByOrder(tOrder.getOrderid()).forEach(item -> {
          try {
            productDeductService.deduct(item.getProductid(), item.getQuanlity() * -1);
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
        //orderRepository.
        //productDeductService.deduct(tOrder.getOrderid(),order.get);
      default:
        break;
    }
    orderRepository.save(tOrder);
  }

  @EventHandler
  public void onEvent(StockDeductAppliedEvent orderUpdatedEvent) throws Exception {
    productDeductService.deduct(orderUpdatedEvent.getProductid(), orderUpdatedEvent.getDeduct());
  }
}
