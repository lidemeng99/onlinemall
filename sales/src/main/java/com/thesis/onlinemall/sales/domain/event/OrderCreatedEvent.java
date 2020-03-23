package com.thesis.onlinemall.sales.domain.event;

import com.thesis.onlinemall.sales.domain.command.OrderCommand;
import com.thesis.onlinemall.sales.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
  private OrderCommand command;
  private Order order;


}
