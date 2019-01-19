package com.java.core.dozer.pojo;

import lombok.Data;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/12/13
 */
@Data
public class UserDo {

    private Long id;
    private String name;
    private String desc;
    private String address;

    private String status;

}
