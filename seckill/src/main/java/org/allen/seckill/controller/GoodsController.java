package org.allen.seckill.controller;

import org.allen.seckill.common.ResponseVO;
import org.allen.seckill.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zhou Zhengwen
 */
@RequestMapping("goods")
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("buyOne")
    public ResponseVO buyOne(Integer goodsId) {
        Boolean success = goodsService.buyOne(goodsId);
        if (success) {
            return ResponseVO.success(success);
        }
        return ResponseVO.fail();
    }

}
