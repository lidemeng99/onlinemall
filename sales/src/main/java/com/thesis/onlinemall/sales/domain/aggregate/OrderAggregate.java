package com.thesis.onlinemall.sales.domain.aggregate;

import com.thesis.onlinemall.sales.domain.command.OrderCommand;
import com.thesis.onlinemall.sales.domain.entity.Order;
import com.thesis.onlinemall.sales.domain.event.OrderCreatedEvent;
import com.thesis.onlinemall.sales.domain.event.OrderUpdatedEvent;
import com.thesis.onlinemall.sales.domain.event.StockDeductAppliedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;

@Slf4j
public class OrderAggregate {

  @AggregateIdentifier
  private String orderid;
  private Order order;


  protected OrderAggregate() {
  }

  public OrderAggregate(String orderid, Order order) {
    this.order = order;
    this.order.setOrderid(orderid);
    AggregateLifecycle.apply(new OrderCreatedEvent(OrderCommand.SUBMIT, this.order));
  }

  public void applyStatus(Order order, OrderCommand command) {
    switch (command) {
      case TO_PAY:
        order.pay();
        break;
      case PAY:
        order.completePayment();
        break;
      case TO_ALLOCATE:
        order.allocate();
        break;
      case ALLOCATE:
        order.complateAllocation();
        break;
      case DELIVERY:
        order.deliver();
        break;
      case RECEIVING:
        order.receiving();
        break;
      case CLOSE:
        order.close();
        break;
      case CANCEL:
        order.cancel();
        break;
      default:
        break;
    }
    AggregateLifecycle.apply(new OrderUpdatedEvent(command, order));
  }


  @EventSourcingHandler
  public void onEvent(OrderCreatedEvent event) {
    this.order = event.getOrder();
    this.orderid = this.order.getOrderid();
    log.info("order<" + this.orderid + "> created, and deduct stock of related product");
    if (CollectionUtils.isNotEmpty(this.order.getOrderItems())) {
      this.order.getOrderItems().forEach(item -> {
        AggregateLifecycle
            .apply(new StockDeductAppliedEvent(item.getProductid(), item.getQuanlity()));
      });
    }

  }
}
