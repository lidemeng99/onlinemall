package com.thesis.onlinemall.product.domain.aggregate;

import com.thesis.onlinemall.product.domain.command.CommandConstants;
import com.thesis.onlinemall.product.domain.entity.CatalogEntity;
import com.thesis.onlinemall.product.domain.entity.EntityFactory;
import com.thesis.onlinemall.product.domain.event.CatalogCreatedEvent;
import com.thesis.onlinemall.product.domain.event.CatalogUpdatedEvent;
import com.thesis.onlinemall.product.domain.event.ChildCatalogAddedEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;

public class CatalogAggregate {

  @AggregateIdentifier
  private String catalogId;
  private CatalogEntity catalogEntity;
  private List<CatalogEntity> children = new ArrayList<>();

  protected CatalogAggregate() {
  }

  public CatalogAggregate(final CatalogEntity catalogEntity) {
    this.catalogId = catalogEntity.getCatalogId();
    this.catalogEntity = catalogEntity;
    //publish event
    CatalogCreatedEvent catalogCreatedEvent = new CatalogCreatedEvent();
    catalogCreatedEvent.setCatalogId(this.catalogId);
    catalogCreatedEvent.setCatalog(this.catalogEntity);
    catalogCreatedEvent.setReceiveTime(new Date());
    catalogCreatedEvent.setStatus(this.catalogEntity.getStatus());
    AggregateLifecycle.apply(catalogCreatedEvent);
  }

  public void addChild(String catalogId, String catalogName,
      String description,
      int orderNum,
      String parentId,
      String modifier) {
    CatalogEntity entity = EntityFactory
        .create(catalogId, catalogName, description,
            orderNum, parentId, modifier);
    this.children.add(entity);
    AggregateLifecycle.apply(new ChildCatalogAddedEvent(entity));
  }

  public void update(CatalogEntity catalogEntity, CommandConstants.COMMAND_TYPE command_type) {
    switch (command_type) {
      case SUBMIT:
        catalogEntity.submit();
        break;
      case APPROVE:
        catalogEntity.approve();
        break;
      case CANCEL:
        catalogEntity.cancel();
        break;
      default:
        System.out.println("no match command type");
        break;
    }
    AggregateLifecycle.apply(new CatalogUpdatedEvent(catalogEntity));

  }

  @EventSourcingHandler
  public void onEvent(CatalogCreatedEvent event) {
    this.catalogId = event.getCatalogId();
    System.out.println(event.getCatalogId() + "创建成功");

  }
}
