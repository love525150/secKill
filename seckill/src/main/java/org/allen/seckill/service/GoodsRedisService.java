package org.allen.seckill.service;

import org.allen.seckill.common.CommonSettings;
import org.allen.seckill.util.RedisUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author Zhou Zhengwen
 */
@Service
public class GoodsRedisService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public long getGoodsStock(int stockId) {
        String value = stringRedisTemplate.opsForValue().get(RedisUtils.keyGenarator(CommonSettings.REDIS_PREFIX, stockId));
        return Optional.ofNullable(value).map(Long::parseLong).orElse(0L);
    }

    public long reduceGoodsStockByOne(int stockId) {
        Long decrement = stringRedisTemplate.opsForValue().decrement(RedisUtils.keyGenarator(CommonSettings.REDIS_PREFIX, stockId));
        return Optional.ofNullable(decrement).orElse(0L);
    }

    public boolean addToAlreadyBuy(int userId) {
        Long addNum = stringRedisTemplate.opsForSet().add(RedisUtils.keyGenarator(CommonSettings.REDIS_PREFIX, CommonSettings.USER_SET_KEY), String.valueOf(userId));
        return Optional.ofNullable(addNum).map(n -> n > 0).orElse(false);
    }

    public boolean removeFromAlreadyBuy(int userId) {
        Long addNum = stringRedisTemplate.opsForSet().remove(RedisUtils.keyGenarator(CommonSettings.REDIS_PREFIX, CommonSettings.USER_SET_KEY), String.valueOf(userId));
        return Optional.ofNullable(addNum).map(n -> n > 0).orElse(false);
    }
}
