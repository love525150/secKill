package org.allen.seckill.service;

import org.allen.seckill.common.CommonSettings;
import org.allen.seckill.dao.GoodsDAO;
import org.allen.seckill.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    @Autowired
    private GoodsRedisService goodsRedisService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public boolean buyOne(int stockId) {
        int userId = userService.getUserId();
        long start = System.currentTimeMillis();
        boolean newAdded = goodsRedisService.addToAlreadyBuy(userId);
        System.out.println("加入已购买耗时：" + (System.currentTimeMillis() - start));
        if (!newAdded) {
            return false;
        }
        try {
            start = System.currentTimeMillis();
            long remainNum = goodsRedisService.reduceGoodsStockByOne(stockId);
            System.out.println("扣减库存耗时：" + (System.currentTimeMillis() - start));
            if (remainNum < 0) {
                return false;
            } else {
                start = System.currentTimeMillis();
                cartService.addOneToCart(stockId, userId);
                System.out.println("记录购买记录耗时：" + (System.currentTimeMillis() - start));
            }
        } catch (Exception e) {
            goodsRedisService.removeFromAlreadyBuy(userId);
            throw e;
        }
        return true;
    }

    public void createStockInRedis(int stockId, int stockNum) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String key = RedisUtils.keyGenarator(CommonSettings.REDIS_PREFIX, stockId);
        valueOperations.set(key, String.valueOf(stockNum));
    }
}
