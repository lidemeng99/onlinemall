package com.thesis.onlinemall.product.domain.command;

import com.thesis.onlinemall.product.domain.entity.CatalogEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateCatalogCommand {
  @TargetAggregateIdentifier
  private String catalogId;
  private CommandConstants.COMMAND_TYPE commandType;
  private String modifier;

}
