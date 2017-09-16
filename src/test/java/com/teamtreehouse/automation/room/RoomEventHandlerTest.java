package com.teamtreehouse.automation.room;

import com.teamtreehouse.automation.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

public class RoomEventHandlerTest {
    private User user = new User("someUser", "pass", new String[]{"ROLE_USER"});

    @Mock
    private RoomRepository roomRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = AccessDeniedException.class)
    public void nonAdminCannotCreateRooms() throws Exception {
        doAnswer(answer -> {
            if (!Arrays.asList(user.getRoles()).contains("ROLE_ADMIN")) {
                throw new AccessDeniedException("Access denied");
            }
            return null;
        }).when(roomRepository).save(any(Room.class));
        roomRepository.save(new Room("Living room", 300));
    }
}
