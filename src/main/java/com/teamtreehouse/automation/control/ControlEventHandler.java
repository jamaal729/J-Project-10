package com.teamtreehouse.automation.control;

import com.teamtreehouse.automation.user.User;
import com.teamtreehouse.automation.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Control.class)
public class ControlEventHandler {
    private final UserRepository users;

    @Autowired
    public ControlEventHandler(UserRepository users) {
        this.users = users;
    }

    @HandleBeforeCreate
    public void setLastModifiedByOnControlCreate(Control control) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = users.findByName(name);

        control.setLastModifiedBy(user);
    }

    @HandleBeforeSave
    public void setLastModifiedByOnSaveControl(Control control) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = users.findByName(name);

        control.setLastModifiedBy(user);
    }
}
