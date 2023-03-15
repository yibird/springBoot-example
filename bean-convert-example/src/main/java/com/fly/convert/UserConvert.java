package com.fly.convert;

import com.fly.model.domain.UserDO;
import com.fly.model.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);
    // bean转换方法,用于将UserDO对象转为UserVO对象
    UserVO convert(UserDO userDO);
}
