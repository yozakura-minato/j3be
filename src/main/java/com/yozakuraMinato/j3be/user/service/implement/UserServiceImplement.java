package com.yozakuraMinato.j3be.user.service.implement;

import com.yozakuraMinato.j3be.user.service.UserModuleService;
import com.yozakuraMinato.j3be.user.util.UserConstant;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImplement implements UserModuleService {
    @Override
    public Optional<UUID> getHostIdByHostPath(String hostPath) {
        return UserConstant.Temporary.HOST_PATH.equals(hostPath)
                ? Optional.of(UserConstant.Temporary.HOST_ID)
                : Optional.empty();
    }
}