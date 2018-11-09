package org.allen.seckill.service;

import org.allen.seckill.dao.CartDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Zhou Zhengwen
 */
@Service
public class CartService {

    @Autowired
    private CartDAO cartDAO;

    public boolean addOneToCart(int stockId, int userId) {
        return cartDAO.addOneStock(stockId, userId) > 0;
    }
}
