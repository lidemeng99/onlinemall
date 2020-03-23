package com.thesis.onlinemall.sales.domain.aggregate;

import static org.junit.jupiter.api.Assertions.*;

import com.thesis.onlinemall.sales.domain.command.OrderCommand;
import com.thesis.onlinemall.sales.domain.command.SubmitOrderCommand;
import com.thesis.onlinemall.sales.domain.entity.Order;
import com.thesis.onlinemall.sales.domain.entity.OrderFactory;
import com.thesis.onlinemall.sales.domain.entity.OrderItem;
import com.thesis.onlinemall.sales.domain.entity.Shipping;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.JVM)
class OrderAggregateTest {

  @Autowired
  private CommandGateway commandGateway;

  @Test
  void onEvent() throws InterruptedException {
    String orderid = UUID.randomUUID().toString();
    Shipping shipping = new Shipping();
    shipping.setAddress("望京西园四区417-712");
    shipping.setCity("北京");
    shipping.setProvince("北京");
    shipping.setOpenid("xxxxx123412123");
    shipping.setReceiver("Damon,Li");
    shipping.setCreatetime(new Date());
    shipping.setPhone("+86 13803336287");

    OrderItem item = new OrderItem(UUID.randomUUID().toString(),"",
        "80686675-caa3-418f-9d36-095edfaf91dd","玻璃水",BigDecimal.valueOf(230.00),6);

    Order order = OrderFactory.create(orderid, "125155555555555", BigDecimal.TEN, "NEO,LEE");
    order.addShipping(shipping);
    order.addOrderItem(item);

    commandGateway.sendAndWait(new SubmitOrderCommand(orderid, OrderCommand.SUBMIT,
       order));
    Thread.sleep(10000);
  }
}