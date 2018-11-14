package org.allen.seckill.service;

import org.allen.seckill.common.CommonSettings;
import org.allen.seckill.dao.CartDAO;
import org.allen.seckill.util.RedisUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodsServiceTest {
    @Autowired
    private GoodsService goodsService;
    @Resource
    private CartDAO cartDAO;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void prepareForSecKill() throws Exception {
        int stockId = 1;
        goodsService.createStockInRedis(stockId, 100);
        String num = stringRedisTemplate.opsForValue().get(RedisUtils.keyGenarator(CommonSettings.REDIS_PREFIX, stockId));
        System.out.println(num);
        Assert.assertTrue("100".equals(num));
        cartDAO.deleteStock(stockId);
        stringRedisTemplate.delete(RedisUtils.keyGenarator(CommonSettings.REDIS_PREFIX, CommonSettings.USER_SET_KEY));
    }

    @Test
    public void test() {
        /*String key = RedisUtils.keyGenarator(CommonSettings.REDIS_PREFIX, 1);
        Long value = stringRedisTemplate.opsForValue().decrement(key);*/
        /*long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Long value = stringRedisTemplate.opsForSet().add(RedisUtils.keyGenarator(CommonSettings.REDIS_PREFIX, CommonSettings.USER_SET_KEY), String.valueOf(99999));
        }
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String num = stringRedisTemplate.opsForValue().get(RedisUtils.keyGenarator(CommonSettings.REDIS_PREFIX, 1));
        }
        System.out.println(System.currentTimeMillis() - start);*/

    }

}