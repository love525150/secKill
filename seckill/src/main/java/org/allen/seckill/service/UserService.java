package org.allen.seckill.service;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author Zhou Zhengwen
 */
@Service
public class UserService {

    public int getUserId() {
        Random random = new Random();
        int userId = random.nextInt(100);
        return userId;
    }
}
