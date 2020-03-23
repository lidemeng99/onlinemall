package com.thesis.onlinemall.product.domain.command;

public interface CommandConstants {

  enum COMMAND_TYPE {
    SUBMIT, APPROVE, CREATE, CANCEL
  }

  /**
   * 商品生命周期状态
   */
  enum PRODUCT_COMMAND {
    INITIAL, SUBMIT,
    STOCK_APPLY, STOCK_APPROVE,
    PRICE_APPLY, PRICE_APPROVE,
    LAUNCH_APPLY, LAUNCH_APPROVE,
    CANCEL_APPLY, CANCEL_APPROVE,
    DELETE
  }
}
