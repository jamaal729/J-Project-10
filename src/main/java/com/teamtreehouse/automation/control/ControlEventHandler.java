package com.teamtreehouse.automation.control;

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
@RepositoryEventHandler(Control.class)
public class ControlEventHandler {
    private final UserRepository users;

    @Autowired
    public ControlEventHandler(UserRepository users) {
        this.users = users;
    }

    // Only Room Administrators can add and modify Controls
    @HandleBeforeCreate
    @HandleBeforeSave
    public void setLastModifiedByOnControlAddOrModify(Control control) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = users.findByName(name);
        if (!Arrays.asList(user.getRoles()).contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Must be a room administrator to add or modify controls");
        }
        control.setLastModifiedBy(user);
    }
}
