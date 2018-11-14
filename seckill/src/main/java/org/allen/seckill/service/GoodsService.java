package org.allen.seckill.service;

import org.allen.seckill.common.CommonSettings;
import org.allen.seckill.config.StockConfig;
import org.allen.seckill.dao.CartDAO;
import org.allen.seckill.dao.GoodsDAO;
import org.allen.seckill.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Zhou Zhengwen
 */
@Service
public class GoodsService {
    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsRedisService goodsRedisService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public boolean buyOne(int stockId) {
        Boolean hasRemain = StockConfig.STOCK_REMAIN.getOrDefault(stockId, true);
        if (!hasRemain) {
            return false;
        }
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
                StockConfig.STOCK_REMAIN.put(stockId, false);
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

    public void prepareForSecKill(int stockId, int stockNum) {
        createStockInRedis(stockId, stockNum);
        String num = stringRedisTemplate.opsForValue().get(RedisUtils.keyGenarator(CommonSettings.REDIS_PREFIX, stockId));
        cartDAO.deleteStock(stockId);
        stringRedisTemplate.delete(RedisUtils.keyGenarator(CommonSettings.REDIS_PREFIX, CommonSettings.USER_SET_KEY));
        System.out.println("秒杀商品初始化成功");
    }

    @PostConstruct
    public void postConstruct() {
        prepareForSecKill(1, 10);
    }
}
