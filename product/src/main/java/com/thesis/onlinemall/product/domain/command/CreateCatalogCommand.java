package com.thesis.onlinemall.product.domain.command;

import com.thesis.onlinemall.product.domain.command.CommandConstants.COMMAND_TYPE;
import com.thesis.onlinemall.product.domain.entity.CatalogEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateCatalogCommand {
  @TargetAggregateIdentifier
  private String catalogId;
  private COMMAND_TYPE commandType;
  private String catalogName;
  private String description;
  private int orderNum;
  private String parentId;
  private String modifier;
//  private CatalogEntity catalog;
//
//  public CreateCatalogCommand(CatalogEntity catalog) {
//    this.catalogId = catalog.getCatalogId();
////    this.sender = catalog.getModifier();
//    this.catalog = catalog;
////    this.sendTime = new Date();
//  }
}
