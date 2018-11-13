package org.allen.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodsDAOTest {
    @Autowired
    private GoodsDAO goodsDAO;

    @Test
    public void testReduceOneStock() {
        int loopNum = 100;
        long durationSum = 0;
        for (int i = 0; i < loopNum; i++) {
            long start = System.currentTimeMillis();
            goodsDAO.reduceOneStock(1);
            long duration = System.currentTimeMillis() - start;
            durationSum += duration;
        }
        System.out.println(durationSum / loopNum);
    }

}