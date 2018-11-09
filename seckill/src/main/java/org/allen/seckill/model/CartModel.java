package org.allen.seckill.model;

import lombok.Data;

/**
 * @author Zhou Zhengwen
 */
@Data
public class CartModel {
    private Integer stockId;

    private Integer userId;

    private Integer cartNum;
}
