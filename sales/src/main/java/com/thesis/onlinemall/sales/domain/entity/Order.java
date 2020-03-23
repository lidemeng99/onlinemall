package com.thesis.onlinemall.sales.domain.entity;

import com.thesis.onlinemall.sales.domain.entity.OrderConstants.ORDER_STATUS;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class Order {
  private String orderid;
  private String openid;
  private BigDecimal payment;
  private BigDecimal postage;
  private BigDecimal totalamount;
  private Date paymenttime;
  private Date sendtime;
  private Date endtime;
  private Date closetime;
  private Date createtime;
  private Date lastmodifiedtime;
  private String modifier;
  private Shipping shipping;
  @Getter
  private List<OrderItem> orderItems;
  private ORDER_STATUS status;

  Order(String orderid, String openid, BigDecimal postage, String modifier) {
    this.orderid = orderid;
    this.openid = openid;
    this.postage = postage;
    this.payment = BigDecimal.ZERO;
    this.modifier = modifier;
    this.status = ORDER_STATUS.SUBMITTED;
    this.createtime = new Date();
    this.lastmodifiedtime = new Date();
  }


  /**
   * add shipping
   */
  public void addShipping(Shipping shipping) {
    this.shipping = shipping;
  }

  /**
   * add order item
   */
  public void addOrderItem(OrderItem orderItem) {
    if (orderItem == null) {
      throw new IllegalStateException("mismatch order item");
    }
    orderItem.setOrderid(this.orderid);
    if (this.orderItems == null) {
      this.orderItems = new ArrayList<>();
    }
    this.orderItems.add(orderItem);
    this.payment = this.payment.add(orderItem.getTotalprice());
    this.totalamount = this.payment.add(postage);
  }

  /**
   * 订单支付
   */
  public void pay() {
    this.lastmodifiedtime = new Date();
    this.status = ORDER_STATUS.PAYING;
  }

  /**
   * 订单支付完成
   */
  public void completePayment() {
    this.paymenttime = new Date();
    this.lastmodifiedtime = new Date();
    this.status = ORDER_STATUS.PAID;
  }

  /**
   * 捡货中
   */
  public void allocate() {
    this.lastmodifiedtime = new Date();
    this.status = ORDER_STATUS.ALLOCATING;
  }

  /**
   * 捡货完成
   */
  public void complateAllocation() {
    this.lastmodifiedtime = new Date();
    this.status = ORDER_STATUS.ALLOCATED;
  }

  /**
   * 发货
   */
  public void deliver() {
    this.lastmodifiedtime = new Date();
    this.sendtime = new Date();
    this.status = ORDER_STATUS.DELIVERING;
  }

  /**
   * 收货
   */
  public void receiving() {
    this.lastmodifiedtime = new Date();
    this.endtime = new Date();
    this.status = ORDER_STATUS.DELIVERED;
  }

  /**
   * 7日内订单自动正常关闭
   */
  public void close() {
//    if (this.status != ORDER_STATUS.DELIVERED) {
//      throw new IllegalStateException("order can not be closed due to NO DELIVERED");
//    }
    this.lastmodifiedtime = new Date();
    this.closetime = new Date();
    this.status = ORDER_STATUS.CLOSED;
    this.modifier = "OPERATOR";
  }

  /**
   * 取消订单
   */
  public void cancel() {
    this.lastmodifiedtime = new Date();
    this.status = ORDER_STATUS.CANCELLED;
  }
}
