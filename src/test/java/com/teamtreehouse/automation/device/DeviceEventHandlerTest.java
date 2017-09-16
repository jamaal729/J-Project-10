package com.teamtreehouse.automation.device;

import com.teamtreehouse.automation.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

public class DeviceEventHandlerTest {
    private User user = new User("someUser", "pass", new String[]{"ROLE_USER"});

    @Mock
    private DeviceRepository deviceRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = AccessDeniedException.class)
    public void nonAdminCannotCreateDevice() throws Exception {
        doAnswer(answer -> {
            if (!Arrays.asList(user.getRoles()).contains("ROLE_ADMIN")) {
                throw new AccessDeniedException("Access denied");
            }
            return null;
        }).when(deviceRepository).save(any(Device.class));
        deviceRepository.save(new Device("device"));
    }
}
