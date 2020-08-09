package com.hand.app.myorm.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 封装了java属性和set/get源码信息
 * @Author zifu.li@hand-china.com
 * @Date 2020/8/8 12:34
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JavaFieldGetSet {

    // 属性的源码信息
    private String fieldInfo;
    // get的源码信息
    private String getInfo;
    // set的源码信息
    private String setInfo;
}
