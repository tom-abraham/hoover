package com.robotics.hoover;

import com.robotics.hoover.model.Coordinate;
import com.robotics.hoover.model.HooverRequest;
import com.robotics.hoover.model.HooverResponse;
import com.robotics.hoover.service.HooverService;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HooverServiceTests {
    HooverService service = new HooverService();
    @Test
    void testCleanWithProperInputWithSize5x5H001() {
        HooverRequest request=new HooverRequest(new int[]{5, 5},new int[]{1, 2},new int[][]{{1, 0},{2, 2},{2, 3}},"NNESEESWNWW");
        assertEquals(new HooverResponse(new Coordinate(1, 3),1),service.clean(request));
    }
    @Test
    void testHitTheRightWallShouldThrowExceptionN001() {
        HooverRequest request=new HooverRequest(new int[]{5, 5},new int[]{1, 2},new int[][]{{1, 0},{2, 2},{2, 3}},"NNESEEEEEEEEEESWNWW");
        try {
            service.clean(request);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("robot has hit the right wall"));
        }
    }
    @Test
    void testHitTheLeftWallShouldThrowExceptionN002() {
        HooverRequest request=new HooverRequest(new int[]{5, 5},new int[]{1, 2},new int[][]{{1, 0},{2, 2},{2, 3}},"NNESEESWNWWWWWWWW");
        try {
            service.clean(request);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("robot has hit the left wall"));
        }
    }
    @Test
    void testHitTheTopWallShouldThrowExceptionN003() {
        HooverRequest request=new HooverRequest(new int[]{5, 5},new int[]{1, 2},new int[][]{{1, 0},{2, 2},{2, 3}},"NNNNNNNNNNESEESWNWW");
        try {
            service.clean(request);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("robot has hit the top wall"));
        }
    }
    @Test
    void testHitTheBottomWallShouldThrowExceptionN004() {
        HooverRequest request=new HooverRequest(new int[]{5, 5},new int[]{1, 2},new int[][]{{1, 0},{2, 2},{2, 3}},"NNESSSSSSSSSSSSSEESWNWW");
        try {
            service.clean(request);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("robot has hit the bottom wall"));
        }
    }
    @Test
    void testRoomSizeZeroShouldThrowExceptionN005() {
        HooverRequest request=new HooverRequest(new int[]{0, 0},new int[]{1, 2},new int[][]{{1, 0},{2, 2},{2, 3}},"NNESSEESWNWW");
        try {
            service.clean(request);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Room size should be more than 0"));
        }
    }

    @Test
    void testInstructionsNullShouldThrowExceptionN006() {
        HooverRequest request=new HooverRequest(new int[]{5, 5},new int[]{1, 2},new int[][]{{1, 0},{2, 2},{2, 3}},null);
        try {
            service.clean(request);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Input parameter instructions missing"));
        }
    }

    @Test
    void testPatchesNullShouldThrowExceptionN007() {
        HooverRequest request=new HooverRequest(new int[]{5, 5},new int[]{1, 2},null,"NNESSEESWNWW");
        try {
            service.clean(request);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Input parameter patches missing"));
        }
    }

    @Test
    void testCoordsNullShouldThrowExceptionN008() {
        HooverRequest request=new HooverRequest(new int[]{5, 5},null,new int[][]{{1, 0},{2, 2},{2, 3}},"NNESSEESWNWW");
        try {
            service.clean(request);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Input parameter coords missing"));
        }
    }

    @Test
    void testRoomSizeNullShouldThrowExceptionN009() {
        HooverRequest request=new HooverRequest(null,new int[]{1, 2},new int[][]{{1, 0},{2, 2},{2, 3}},"NNESSEESWNWW");
        try {
            service.clean(request);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Input parameter roomsize missing"));
        }
    }
}
