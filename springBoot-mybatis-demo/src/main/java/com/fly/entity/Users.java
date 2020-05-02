package com.fly.entity;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
/**
 * Accessors的意思是存取器,它有3个属性
 *    fluent:fluent的中文含义是流畅的,设置为true,则getter和setter方法的方法名都是基础属性名,例如下面的代码
 *    private Integer id;
 *    private String name;
 *    //生成的getter、setter方法如下
 *    public Integer id(){}
 *
 *    chain:chain的含义是链式的,设置为true,则setter方法返回当前对象
 *
 *    prefix:prefix的含义是前缀,用于生成setter和getter方法的字段名忽略某些前缀,例如设置为 @Accessors(prefix = "p"),
 *    假设类中有 private Integer pId这个字段,最后生成的setter和getter方法分别为 public void setId(Interger id){}和
 *    public Integer getId(){}
 */
@Accessors(chain = true)
public class Users {

    private Integer id;

    private String uname;

    private String uno;

    private Integer sex;

    private Integer age;

    private String idCard;

    private String address;

    private String des;

}
