package com.thesis.onlinemall.product.domain.command;

import com.thesis.onlinemall.product.domain.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductLifecycleCommand {
  @TargetAggregateIdentifier
  private String productId;
  private CommandConstants.PRODUCT_COMMAND command;
  private ProductEntity product;
  private String operator;


}
