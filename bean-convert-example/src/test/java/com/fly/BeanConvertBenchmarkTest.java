package com.fly;

import com.fly.convert.UserConvert;
import com.fly.model.domain.UserDO;
import com.fly.model.vo.UserVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

@SpringBootTest
public class BeanConvertBenchmarkTest {
    private UserDO userDO;

    private final static long COUNT = 1000000;

    @BeforeEach
    public void init() {
        userDO = new UserDO();
        userDO.setId(1);
        userDO.setUsername("zchengfeng");
        userDO.setPassword("123456");
        userDO.setNickname("zchengfeng");
        userDO.setPassword("123456");
        userDO.setNickname("zchengfeng");
        userDO.setSex(1);
        userDO.setAge(24);
        userDO.setRoleId(1);
        userDO.setAddress("宇宙");
        userDO.setBirthday(new Date());
        userDO.setAvatar("http://xxxxx/xxx,png");
    }

    @Test
    public void commonsBeanUtilsConvertTest() throws InvocationTargetException, IllegalAccessException {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            UserVO userVO = new UserVO();
            org.apache.commons.beanutils.BeanUtils.copyProperties(userVO, userDO);
        }
        System.out.println("time:" + (System.currentTimeMillis() - startTime));
    }

    @Test
    public void springBeanUtilsConvertTest() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            UserVO userVO = new UserVO();
            org.springframework.beans.BeanUtils.copyProperties(userDO, userVO);
        }
        System.out.println("time:" + (System.currentTimeMillis() - startTime));
    }

    @Test
    public void huToolBeanUtilsConvertTest() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            UserVO userVO = new UserVO();
            cn.hutool.core.bean.BeanUtil.copyProperties(userDO, userVO);
        }
        System.out.println("time:" + (System.currentTimeMillis() - startTime));
    }

    @Test
    public void beanCopierConvertTest() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            net.sf.cglib.beans.BeanCopier beanCopier = net.sf.cglib.beans.BeanCopier
                    .create(UserDO.class, UserVO.class, false);
            UserVO userVO = new UserVO();
            beanCopier.copy(userDO, userVO, null);
        }
        System.out.println("time:" + (System.currentTimeMillis() - startTime));
    }

    @Test
    public void dozerMapperConvertTest() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            org.dozer.Mapper mapper = new org.dozer.DozerBeanMapper();
            UserVO userVO = mapper.map(userDO, UserVO.class);
        }
        System.out.println("time:" + (System.currentTimeMillis() - startTime));
    }

    @Test
    public void orikaMapperConvertTest() {
        long startTime = System.currentTimeMillis();
        // 使用默认映射工厂创建映射工厂
        ma.glasnost.orika.MapperFactory mapperFactory = new ma.glasnost.orika.impl
                .DefaultMapperFactory.Builder().build();
        for (int i = 0; i < COUNT; i++) {
            UserVO userVO = mapperFactory.getMapperFacade().map(userDO, UserVO.class);
        }
        System.out.println("time:" + (System.currentTimeMillis() - startTime));
    }

    @Test
    public void mapStructConvertTest() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            UserVO userVO = UserConvert.INSTANCE.convert(userDO);
        }
        System.out.println("time:" + (System.currentTimeMillis() - startTime));
    }
}
