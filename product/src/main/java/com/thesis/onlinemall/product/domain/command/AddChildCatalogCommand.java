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
public class AddChildCatalogCommand {
  private String catalogName;
  private String description;
  private int orderNum;
  @TargetAggregateIdentifier
  private String parentId;
  private String modifier;
  private String catalogId;

}
