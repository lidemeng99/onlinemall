package com.thesis.onlinemall.product.domain.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StockDeductCommand {
  @TargetAggregateIdentifier
  private String productId;
  private int deduct;

}
