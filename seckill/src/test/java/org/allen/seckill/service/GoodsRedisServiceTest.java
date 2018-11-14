package org.allen.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodsRedisServiceTest {
    @Autowired
    private GoodsRedisService goodsRedisService;

    @Test
    public void reduceGoodsStockByOne() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            long l = goodsRedisService.reduceGoodsStockByOne(1);
            System.out.println(l);
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 10000);
    }

    @Test
    public void testRedisTPS() {
        int concurrency = 1;
        int loop = 10000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < concurrency; i++) {
            new Thread(() -> {
                for (int j = 0; j < loop; j++) {
                    long l = goodsRedisService.reduceGoodsStockByOne(1);
                    System.out.println(l);
                }
            }).start();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}