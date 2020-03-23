package com.thesis.onlinemall.product.domain.aggregate;

import com.thesis.onlinemall.product.domain.command.CommandConstants.PRODUCT_COMMAND;
import com.thesis.onlinemall.product.domain.entity.CatalogEntity;
import com.thesis.onlinemall.product.domain.entity.ProductEntity;
import com.thesis.onlinemall.product.domain.entity.ProductStock;
import com.thesis.onlinemall.product.domain.entity.StatusConstants.STATUS_PRODUCT;
import com.thesis.onlinemall.product.domain.event.ProductCreatedEvent;
import com.thesis.onlinemall.product.domain.event.ProductLifecycleEvent;
import com.thesis.onlinemall.product.domain.event.StockDeductEvent;
import com.thesis.onlinemall.product.domain.event.StockEnabledEvent;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;

public class ProductAggregate {

  @AggregateIdentifier
  private String productId;
  private ProductEntity product;
  private CatalogEntity catalog;

  protected ProductAggregate() {
  }

  public ProductAggregate(ProductEntity productEntity) {
    this.productId = productEntity.getProductId();
    this.product = productEntity;
    AggregateLifecycle.apply(new ProductCreatedEvent(this.productId, product));
  }

  public void deduct(String productId,int deduct){
    AggregateLifecycle.apply(new StockDeductEvent(productId,deduct));
  }

  //domain logic
  public void apply(ProductEntity product, PRODUCT_COMMAND command) {
    this.product = product;
    switch (command) {
      case SUBMIT:
        product.submit();
        break;
      case STOCK_APPLY:
        product.applyStock();
        break;
      case STOCK_APPROVE:
        product.stock();
        break;
      case PRICE_APPLY:
        product.applyPrice();
        break;
      case PRICE_APPROVE:
        product.price();
        break;
      case LAUNCH_APPLY:
        product.applyLaunch();
        break;
      case LAUNCH_APPROVE:
        product.launch();
        break;
      case CANCEL_APPLY:
        product.applyCancel();
        break;
      case CANCEL_APPROVE:
        product.cancel();
        break;
      case DELETE:
        product.delete();
        break;
      default:
        break;
    }
    AggregateLifecycle.apply(new ProductLifecycleEvent(this.product));
    if (product.getStatus() == STATUS_PRODUCT.LAUNCHED) {
      System.out.println(product);
      AggregateLifecycle.apply(new StockEnabledEvent(
          new ProductStock(product.getProductId(), product.getStock(), product.getUnit())));
    }
  }


  @EventSourcingHandler
  public void onEvent(ProductCreatedEvent event) {
    this.productId = event.getProductId();
    System.out.println(event.getProductId() + "创建成功");

  }

}
