package org.allen.seckill.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zhou Zhengwen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVO<T> {
    private T data;

    private String msg;

    private Integer code;

    public static <T> ResponseVO success(T data) {
        return new ResponseVO<>(data, "success", 0);
    }

    public static ResponseVO fail() {
        return new ResponseVO<>(null, "fail", -1);
    }
}
