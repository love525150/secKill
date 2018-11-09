package org.allen.seckill.dao;

import org.allen.seckill.model.CartModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface CartDAO {

    @Insert("insert into t_cart (stockId, userId, cart_num) values (#{stockId}, #{userId}, #{cartNum})")
    int addToCart(CartModel cartModel);

    @Insert("insert into t_cart (stockId, userId, cart_num) values (#{stockId}, #{userId}, 1)")
    int addOneStock(@Param("stockId") int stockId, @Param("userId") int userId);
}
