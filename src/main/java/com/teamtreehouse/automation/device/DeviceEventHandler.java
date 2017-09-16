package com.teamtreehouse.automation.device;

import com.teamtreehouse.automation.user.User;
import com.teamtreehouse.automation.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RepositoryEventHandler(Device.class)
public class DeviceEventHandler {
    private final UserRepository users;

    @Autowired
    public DeviceEventHandler(UserRepository users) {
        this.users = users;
    }

    // Only Room Administrators can add and modify Devices
    @HandleBeforeCreate
    @HandleBeforeSave
    public void authorizeDeviceAddOrModify(Device device) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = users.findByName(name);
        if (!Arrays.asList(user.getRoles()).contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Must be a room administrator to add or modify devices");
        }
    }
}
