package com.spand.meme.rest.model.mapper;

import com.spand.meme.model.AppUser;
import com.spand.meme.rest.model.UserDetails;
import com.spand.meme.rest.model.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source="name", target="name"),
            @Mapping(source="login",target="login"),
            @Mapping(source="password",target="password"),
            @Mapping(source="address",target="address")
    })
    AppUser userRequestToUser(UserRequest userrequest);

    @Mappings({
            @Mapping(source="name", target="name"),
            @Mapping(source="login",target="login"),
            @Mapping(source="password",target="password"),
            @Mapping(source="address",target="address")
    })
    UserDetails userToUserDetails(AppUser appUser);
}
