package com.shopplatform.identity_service.user.mapper;



import com.shopplatform.identity_service.auth.dto.request.RegisterRequest;
import com.shopplatform.identity_service.auth.dto.response.RegisterResponse;
import com.shopplatform.identity_service.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //Entity'den dto'ya dönüşüm
    RegisterResponse toDto(User user);

}
