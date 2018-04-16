package com.spand.bridgecom.rest.model.mapper;

import com.spand.bridgecom.model.User;
import com.spand.bridgecom.rest.model.UserDetails;
import com.spand.bridgecom.rest.model.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source="name", target="name"),
            @Mapping(source="login",target="login"),
            @Mapping(source="password",target="password"),
            @Mapping(source="address",target="address")
    })
    User userRequestToUser(UserRequest userrequest);

    @Mappings({
            @Mapping(source="name", target="name"),
            @Mapping(source="login",target="login"),
            @Mapping(source="password",target="password"),
            @Mapping(source="address",target="address")
    })
    UserDetails userToUserDetails(User user);
}
