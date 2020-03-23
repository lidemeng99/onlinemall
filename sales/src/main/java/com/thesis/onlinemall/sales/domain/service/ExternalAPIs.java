package com.thesis.onlinemall.sales.domain.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "api")
public class ExternalAPIs {
  private  String stockDeduction;
}
