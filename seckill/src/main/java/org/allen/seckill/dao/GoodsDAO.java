package org.allen.seckill.dao;

import org.apache.ibatis.annotations.Update;

public interface GoodsDAO {

    @Update("update t_stock set stock = stock - 1 where id = #{id} and stock > 0")
    int reduceOneStock(int id);
}
