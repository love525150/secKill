package org.allen.seckill.service;

import org.allen.seckill.dao.GoodsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zhou Zhengwen
 */
@Service
public class GoodsService {
    @Autowired
    private GoodsDAO goodsDAO;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @Transactional
    public boolean buyOne(int stockId) {
        int userId = userService.getUserId();
        int reduceStock = goodsDAO.reduceOneStock(stockId);
        if (reduceStock > 0) {
            cartService.addOneToCart(stockId, userId);
        }
        return reduceStock > 0;
    }
}
