package com.thesis.onlinemall.sales.domain.service;

import lombok.Data;

@Data
public class WxMaLogin {
  //用户登录凭证
  String code;

  //原始数据字符串
  String signature;

  //校验用户信息字符串
  String rawData;

  //加密用户数据
  String encryptedData;

  //加密算法的初始向量
  String iv;

}
