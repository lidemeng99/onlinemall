package com.thesis.onlinemall.sales;

import com.thesis.onlinemall.sales.domain.command.OrderCommand;
import com.thesis.onlinemall.sales.domain.command.SubmitOrderCommand;
import com.thesis.onlinemall.sales.domain.entity.OrderFactory;
import java.math.BigDecimal;
import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class SalesApplication {



  public static void main(String[] args) {
    SpringApplication.run(SalesApplication.class, args);

//
  }

}
