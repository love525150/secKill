package org.allen.seckill.dao;

import org.allen.seckill.model.CartModel;
import org.apache.ibatis.annotations.*;

public interface CartDAO {

    @Insert("insert into t_cart (stockId, userId, cart_num) values (#{stockId}, #{userId}, #{cartNum})")
    int addToCart(CartModel cartModel);

    @Insert("insert into t_cart (stockId, userId, cart_num) values (#{stockId}, #{userId}, 1)")
    int addOneStock(@Param("stockId") int stockId, @Param("userId") int userId);

    @Select("select count(1) from t_cart where stockId = #{stockId} and userId = #{userId}")
    int selectCount(@Param("stockId") int stockId, @Param("userId") int userId);

    @Delete("delete from t_cart where stockId = #{stockId}")
    int deleteStock(@Param("stockId") int stockId);
}
