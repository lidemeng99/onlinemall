package com.thesis.onlinemall.sales.domain.command;

import com.thesis.onlinemall.sales.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderUpdateCommand {
  @TargetAggregateIdentifier
  private String orderid;
  private OrderCommand command;
  private Order order;
}
