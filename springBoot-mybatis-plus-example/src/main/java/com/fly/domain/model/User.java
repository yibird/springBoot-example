package com.fly.domain.model;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly.annotation.Sensitive;
import com.fly.common.model.BaseModel;
import com.fly.enums.SensitiveStrategy;
import com.fly.serializer.DesensitizationSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


//
//@Data
//@Accessors(chain = true)
//@TableName("sys_user")
//@AllArgsConstructor
//@NoArgsConstructor
//public class User extends BaseModel {
//    /**
//     * 表主键,type用于指定主键策略,支持如下策略:
//     * IdType.AUTO: 数据库自增。
//     * IdType.NONE: 无状态(默认策略),该类型为未设置主键类型(注解里等于跟随全局,全局里约等于 INPUT)。
//     * IdType.INPUT: insert 前自行 set 主键值。
//     * IdType.ASSIGN_ID: 分配 ID(主键类型为 Number(Long 和 Integer)或 String)(since 3.3.0),
//     * 使用接口IdentifierGenerator的方法nextId(默认实现类为DefaultIdentifierGenerator雪花算法)。
//     * IdType.ASSIGN_UUID: 分配 UUID,主键类型为 String(since 3.3.0),使用接口IdentifierGenerator
//     * 的方法nextUUID(默认 default 方法)
//     */
//    @TableId(type = IdType.AUTO)
//    private Long id;
//    private String username;
//    @Sensitive(strategy = SensitiveStrategy.PASSWORD)
//    @JSONField(serializeUsing = DesensitizationSerializer.class)
//    private String password;
//    @Sensitive(strategy = SensitiveStrategy.CHINESE_NAME)
//    private String nickname;
//    private Integer age;
//    private Integer sex;
//
//    @Sensitive(strategy = SensitiveStrategy.PHONE)
//    private String phone;
//    @Sensitive(strategy = SensitiveStrategy.EMAIL)
//    private String email;
//    // @TableField用于指定表字段,exist表示是否是数据表字段
//    @TableField(value = "idcard", exist = true)
//    @Sensitive(strategy = SensitiveStrategy.ID_CARD)
//    private String idCard;
//    private String avatar;
//    @Sensitive(strategy = SensitiveStrategy.ADDRESS)
//    private String address;
//    private Integer status;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date birthday;
//    @Sensitive(strategy = SensitiveStrategy.BANK_CARD)
//    private String bankCard;
//}



@Data
@Accessors(chain = true)
@TableName("sys_user")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    @JSONField(serializeUsing = DesensitizationSerializer.class)
    private String password;
    @JSONField(serializeUsing = DesensitizationSerializer.class)
    private String nickname;
    private Integer age;
    private Integer sex;

    @JSONField(serializeUsing = DesensitizationSerializer.class)
    private String phone;
    @JSONField(serializeUsing = DesensitizationSerializer.class)
    private String email;
    // @TableField用于指定表字段,exist表示是否是数据表字段
    @TableField(value = "idcard", exist = true)
    @JSONField(serializeUsing = DesensitizationSerializer.class)
    private String idCard;
    private String avatar;
    @JSONField(serializeUsing = DesensitizationSerializer.class)
    private String address;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    @JSONField(serializeUsing = DesensitizationSerializer.class)
    private String bankCard;
}
