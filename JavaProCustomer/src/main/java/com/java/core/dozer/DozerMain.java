package com.java.core.dozer;

import com.java.core.dozer.pojo.UserDo;
import com.java.core.dozer.pojo.UserDto;
import org.apache.commons.beanutils.BeanUtils;
import org.dozer.DozerBeanMapper;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/12/12
 */
public class DozerMain {

    public static void main(String[] args) throws Exception {


//        int count = 8000 * 1000;
        int count = 1;
        {
            DozerBeanMapper beanMapper = new DozerBeanMapper();
            long start = System.currentTimeMillis();
            for (int i = 0 ; i < count ; i++) {
                UserDo userDo = new UserDo();
                userDo.setId(1L);
                userDo.setName("userDo_name");
                userDo.setAddress("my_address");
                userDo.setDesc("my_desc");
                userDo.setStatus("1hehehe");
                UserDto userDto = new UserDto();
                beanMapper.map(userDo, userDto);
                System.out.println();
            }
            long time = System.currentTimeMillis() - start;
            System.out.println("dozer time : " + time);
        }
        {
            long start = System.currentTimeMillis();
            for (int i = 0 ; i < count ; i++) {
                UserDo userDo = new UserDo();
                userDo.setId(1L);
                userDo.setName("userDo_name");
                userDo.setAddress("my_address");
                userDo.setDesc("my_desc");
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(userDto, userDo);
            }
            long time = System.currentTimeMillis() - start;
            System.out.println("BeanUtils time : " + time);
        }

    }

}
