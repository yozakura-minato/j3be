package com.yozakuraMinato.j3be.user.service;

import java.util.Optional;
import java.util.UUID;

public interface UserModuleService {
    Optional<UUID> getHostIdByHostPath(String hostPath);
}
