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
public class BeanConvertTest {

    private UserDO userDO;

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

    /**
     * 手动get/set转换Bean
     */
    @Test
    public void manualConvertTest() {
        UserVO userVO = new UserVO();
        userVO.setId(userDO.getId());
        userVO.setUsername(userDO.getUsername());
        userVO.setNickname(userDO.getNickname());
        userVO.setSex(String.valueOf(userDO.getSex()));
        userVO.setAddress(userDO.getAddress());
        userVO.setBirthday(userDO.getBirthday());
        userVO.setAvatar(userDO.getAvatar());
        System.out.println("userVO:" + userVO);
    }

    /**
     * 通过commons-beanutils的BeanUtils.copyProperties()进行Bean对象的转换,
     * BeanUtils.copyProperties(final Object dest, final Object orig):
     * 对于属性名称相同的所有情况,将属性值从源对象(orig)复制到目标对象(dest)。
     */
    @Test
    public void commonsBeanUtilsConvertTest() throws InvocationTargetException, IllegalAccessException {
        UserVO userVO = new UserVO();
        org.apache.commons.beanutils.BeanUtils.copyProperties(userVO, userDO);
        System.out.println("userVO:"+userVO);
    }

    /**
     * 通过spring-beans的BeanUtils.copyProperties()转换Bean。spring-beans提供了
     * 多个copyProperties()重载方法:
     * - copyProperties(Object source, Object target):允许源对象(source)与目标对象(target)有相同的属性时,
     * 将源对象的属性拷贝到目标对象的属性上。
     * - copyProperties(Object source, Object target, String... ignoreProperties):
     * 允许源对象(source)与目标对象(target)有相同的属性时,将源对象的属性拷贝到目标对象的属性上,
     * ignoreProperties表示忽略转换的属性。
     *
     * spring-beans BeanUtils.copyProperties()的缺点:
     * - 对于不同类型会转换失败,例如Integer类型转String类型,转换后的值为null。
     */
    @Test
    public void springBeanUtilsConvertTest() {
        UserVO userVO = new UserVO();
        org.springframework.beans.BeanUtils.copyProperties(userDO, userVO);
        System.out.println("userVO:" + userVO);
    }

    /**
     * 通过hutool的BeanUtil.copyProperties转换Bean,用于与
     * Spring的BeanUtils.copyProperties()类似。
     */
    @Test
    public void huToolBeanUtilsConvertTest(){
        UserVO userVO = new UserVO();
        cn.hutool.core.bean.BeanUtil.copyProperties(userDO, userVO);
        System.out.println("userVO:" + userVO);
    }

    /**
     * 通过BeanCopier进行bean对象的转换。BeanCopier支持两种方式:
     * - 不使用转换器(Convert)。不使用转换器情况下仅对两个bean间属性名和类型完全相同的变量进行拷贝。
     * - 使用转换器(Convert)。使用转换器情况下可以对某些特定属性值进行特殊操作。
     * BeanCopier方法如下:
     * - BeanCopier.create(Class source, Class target, boolean useConverter):创建一个BeanCopier实例。
     * 该方法需要指定转换的源对象Class(source)、转换目标对象的Class(target)及是否使用转换器(useConverter)三个参数。
     * 自定义转换器需要实现Convert接口并重写convert()方法,convert()方法中用于处理转换逻辑。
     * - beanCopier.copy(Object var1, Object var2, Converter var3):从源对象(var1)拷贝属性到目标对象(var2),
     * 拷贝对象时可以指定转换器(var3)。
     */
    @Test
    public void beanCopierConvertTest() {
        net.sf.cglib.beans.BeanCopier beanCopier = net.sf.cglib.beans.BeanCopier
                .create(UserDO.class, UserVO.class, false);
        UserVO userVO = new UserVO();
        beanCopier.copy(userDO, userVO, null);
        System.out.println("userVO:" + userVO);
    }

    /**
     * 通过DozerBeanMapper实现Bean转换。由于DozerBeanMapper内部通过属性映射方式,
     * 递归性的复制对象,性能相对较差。
     */
    @Test
    public void dozerMapperConvertTest() {
        org.dozer.Mapper mapper = new org.dozer.DozerBeanMapper();
        UserVO userVO = mapper.map(userDO, UserVO.class);
        System.out.println("userVO:" + userVO);
    }

    /**
     * 基于Orika实现Bean转换
     */
    @Test
    public void orikaMapperConvertTest() {
        // 使用默认映射工厂创建映射工厂
        ma.glasnost.orika.MapperFactory mapperFactory = new ma.glasnost.orika.impl
                .DefaultMapperFactory.Builder().build();
        UserVO userVO = mapperFactory.getMapperFacade().map(userDO, UserVO.class);
        System.out.println("userVO:" + userVO);
    }

    @Test
    public void mapStructConvertTest(){
        UserVO userVO = UserConvert.INSTANCE.convert(userDO);
        System.out.println("userVO:"+userVO);
    }
}
