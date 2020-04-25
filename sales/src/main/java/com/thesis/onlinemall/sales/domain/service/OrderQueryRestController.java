package com.thesis.onlinemall.sales.domain.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.thesis.onlinemall.sales.domain.entity.Order;
import com.thesis.onlinemall.sales.domain.entity.OrderConstants.ORDER_STATUS;
import com.thesis.onlinemall.sales.domain.entity.OrderItem;
import com.thesis.onlinemall.sales.domain.entity.Shipping;
import com.thesis.onlinemall.sales.infrastructure.entity.TOrder;
import com.thesis.onlinemall.sales.infrastructure.entity.TOrderProduct;
import com.thesis.onlinemall.sales.infrastructure.entity.TShipping;
import com.thesis.onlinemall.sales.infrastructure.repository.OrderItemRepository;
import com.thesis.onlinemall.sales.infrastructure.repository.OrderRepository;
import com.thesis.onlinemall.sales.infrastructure.repository.ShippingRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class OrderQueryRestController {

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private OrderItemRepository orderItemRepository;
  @Autowired
  private ShippingRepository shippingRepository;


  @Autowired
  private WxMaService wxMaService;

  @GetMapping("/orders")
  public @ResponseBody
  List<Order> findOrdersByCustomer(@RequestParam(name = "openid") String openid) {
    List<Order> orders = new ArrayList<>();
    List<TOrder> tOrders = orderRepository.findByCustomer(openid);
    tOrders.forEach(item -> {
      Order order = new Order();
      System.out.println(item.toString());

      BeanUtils.copyProperties(item, order);
      order.setStatus(ORDER_STATUS.values()[item.getStatus()]);

      //load order items
      List<TOrderProduct> itemPersistents = orderItemRepository
          .findAllItemsByOrder(item.getOrderid());
      BigDecimal payment = order.getPayment();
      itemPersistents.forEach(subitem -> {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(subitem, orderItem);
        order.addOrderItem(orderItem);
      });
      order.setPayment(payment);
      order.setTotalamount(payment.add(order.getPostage()));
      System.out.println(order.toString());
      orders.add(order);
    });
    return orders;
  }

  @GetMapping("/shipping")
  public @ResponseBody
  List<Shipping> findShippingsByCustomer(@RequestParam(name = "openid") String openid) {
    List<Shipping> shippingList = new ArrayList<>();
    List<TShipping> tShippings = shippingRepository.findByCustomer(openid);
    tShippings.forEach(item -> {
      Shipping shipping = new Shipping();
      BeanUtils.copyProperties(item, shipping);
      shippingList.add(shipping);

    });
    return shippingList;
  }

  @GetMapping("/orders/allocating")
  public @ResponseBody
  List<Order> findAllocatingOrders() {
    List<Order> orders = new ArrayList<>();
    List<TOrder> tOrders = orderRepository.findByStates(ORDER_STATUS.ALLOCATING.ordinal());
    tOrders.forEach(item -> {
      orders.add(findOne(item.getOrderid()));
    });
    return orders;
  }


  @GetMapping("/order/{id}")
  public @ResponseBody
  Order findOne(@PathVariable String id) {
    Optional<TOrder> tOrder = orderRepository.findById(id);
    if (!tOrder.isPresent()) {
      throw new IllegalArgumentException("can't find order <" + id + ">");
    }
    Order order = new Order();
    BeanUtils.copyProperties(tOrder.get(), order);
    order.setStatus(ORDER_STATUS.values()[tOrder.get().getStatus()]);
    Optional<TShipping> tShippingOptional = shippingRepository
        .findById(tOrder.get().getShippingid());
    Shipping shipping = new Shipping();
    if (tShippingOptional.isPresent()) {
      BeanUtils.copyProperties(tShippingOptional.get(), shipping);
      order.setShipping(shipping);
    }
    BigDecimal payment = order.getPayment();
    List<TOrderProduct> itemPersistents = orderItemRepository
        .findAllItemsByOrder(order.getOrderid());
    itemPersistents.forEach(subitem -> {
      OrderItem orderItem = new OrderItem();
      BeanUtils.copyProperties(subitem, orderItem);
      order.addOrderItem(orderItem);
    });
    order.setPayment(payment);
    order.setTotalamount(payment.add(order.getPostage()));

    return order;
  }

  @PostMapping("/opendid")
  public String getOpenID(@RequestBody WxMaLogin login) throws WxErrorException {
    WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(login.code);
    if (null == session) {
      throw new RuntimeException("login handler error");
    }
    WxMaUserInfo wxUserInfo = wxMaService.getUserService()
        .getUserInfo(session.getSessionKey(), login.encryptedData, login.iv);
    return wxUserInfo.getOpenId();
  }
}
