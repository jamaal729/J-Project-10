package com.teamtreehouse.automation.room;

import com.teamtreehouse.automation.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)

public class RoomRepositoryTest {
    @Mock
    private RoomRepository roomRepository;

    private List<Room> rooms = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(roomRepository.save(any(Room.class))).then(answer ->
                rooms.add(new Room("room1", 500))
        );
        roomRepository.save(new Room("room1", 500));
    }

    @Test
    public void findByNameReturnsRoom() throws Exception {
        when(roomRepository.findByNameStartsWith(eq("room"), any(PageRequest.class))).thenReturn(new PageImpl<>(
                rooms.stream().filter(room -> room.getName().contains("room")).collect(Collectors.toList()))
        );
        Page<Room> roomPage = roomRepository.findByNameStartsWith("room", new PageRequest(1, 10));

        assertEquals(roomPage.getTotalElements(), 1);
    }

    @Test
    public void findBySizeLessThanReturnsRoom() throws Exception {
        when(roomRepository.findByAreaLessThan(eq(600), any(PageRequest.class))).thenReturn(new PageImpl<Room>(
                rooms.stream().filter(room -> room.getArea() < 600).collect(Collectors.toList())
        ));
        Page<Room> roomPage = roomRepository.findByAreaLessThan(600, new PageRequest(1, 10));

        assertEquals(roomPage.getTotalElements(), 1);
    }
}
