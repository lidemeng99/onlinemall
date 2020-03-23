package com.thesis.onlinemall.sales.domain.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
//@EnableConfigurationProperties(MpConfig.class)
public class WxMaServiceConfiguration {

  @Autowired
  private MpConfig mpConfig;

  @Bean
  public WxMaService wxMaService() {
    WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
   // config.set
    config.setAppid(mpConfig.getAppId());
    config.setSecret(mpConfig.getSecret());
    config.setMsgDataFormat("JSON");
    WxMaService wxMaService = new WxMaServiceImpl();
    wxMaService.setWxMaConfig(config);
    return wxMaService;
  }
}
