package com.thesis.onlinemall.product.domain.command;

import com.thesis.onlinemall.product.domain.command.CommandConstants.COMMAND_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCommand {
  @TargetAggregateIdentifier
  private String productId;
  private String productName;
  private String shortDesc;
  private String fullDesc;
  private String thumbnail;
  private String catalogId;
  private String catalogName;
  private String modifier;
  private COMMAND_TYPE commandType;
}
