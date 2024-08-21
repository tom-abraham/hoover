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
    void testNotHittingRightWallH002() {
        HooverRequest request=new HooverRequest(new int[]{15, 5},new int[]{1, 2},new int[][]{{1, 0},{2, 2},{2, 3}},"NNESEEEEEEEEEESWNWW");
        assertEquals(new HooverResponse(new Coordinate(9, 3),1),service.clean(request));
    }
    @Test
    void testNotHittingTopWallH003() {
        HooverRequest request=new HooverRequest(new int[]{5, 15},new int[]{1, 2},new int[][]{{1, 11},{2, 2},{2, 3}},"NNNNNNNNNNESEESWNWW");
        assertEquals(new HooverResponse(new Coordinate(1, 11),1),service.clean(request));
    }

    @Test
    void testPatchAt11H004() {
        HooverRequest request=new HooverRequest(new int[]{1, 1},new int[]{0, 0},new int[][]{{1, 1}},"NE");
        assertEquals(new HooverResponse(new Coordinate(1, 1),1),service.clean(request));
    }

    //patch on 4 corners to be cleaned
    @Test
    void testPatchOnTopRightCornerH005() {
        HooverRequest request=new HooverRequest(new int[]{15, 15},new int[]{0, 0},new int[][]{{15, 15}},"NNNNNNNNNNNNNNNEEEEEEEEEEEEEEE");
        assertEquals(new HooverResponse(new Coordinate(15, 15),1),service.clean(request));
    }



    //patch on corners to be cleaned
    @Test
    void testPatchOnTopLeftCornerH006() {
        HooverRequest request=new HooverRequest(new int[]{15, 15},new int[]{0, 0},new int[][]{{0, 15}},"NNNNNNNNNNNNNNN");
        assertEquals(new HooverResponse(new Coordinate(0, 15),1),service.clean(request));
    }

    @Test
    void testPatchOnBottomLeftCornerH007() {
        HooverRequest request=new HooverRequest(new int[]{15, 15},new int[]{0, 0},new int[][]{{0, 0}},"");
        assertEquals(new HooverResponse(new Coordinate(0, 0),1),service.clean(request));
    }

    @Test
    void testPatchOnBottomRightCornerH008() {
        HooverRequest request=new HooverRequest(new int[]{15, 15},new int[]{0, 0},new int[][]{{15, 00}},"EEEEEEEEEEEEEEE");
        assertEquals(new HooverResponse(new Coordinate(15, 0),1),service.clean(request));
    }

    @Test
    void testPatchCentreH009() {
        HooverRequest request=new HooverRequest(new int[]{16, 16},new int[]{0, 0},new int[][]{{8, 8}},"NNNNNNNNEEEEEEEE");
        assertEquals(new HooverResponse(new Coordinate(8, 8),1),service.clean(request));
    }

    @Test
    void testPatchRandomLocationH010() {
        HooverRequest request=new HooverRequest(new int[]{16, 16},new int[]{4, 7},new int[][]{{6, 9},{4, 7}},"NENE");
        assertEquals(new HooverResponse(new Coordinate(6, 9),2),service.clean(request));
    }
    @Test
    void testPatchRandomLocationH011() {
        HooverRequest request=new HooverRequest(new int[]{16, 16},new int[]{3, 9},new int[][]{{1, 7}},"SSWW");
        assertEquals(new HooverResponse(new Coordinate(1, 7),1),service.clean(request));
    }
    @Test
    void testPatchRandomLocationH012() {
        HooverRequest request=new HooverRequest(new int[]{16, 16},new int[]{13, 11},new int[][]{{13, 11},{12, 11},{13, 10},{13, 12},{14, 11}},"NSWE");
        assertEquals(new HooverResponse(new Coordinate(13, 11),3),service.clean(request));
    }
    @Test
    void testPatchRandomLocationH013() {
        HooverRequest request=new HooverRequest(new int[]{16, 16},new int[]{13, 11},new int[][]{{13, 11},{12, 11},{13, 10},{13, 12},{14, 11}},"NSWESNEW");
        assertEquals(new HooverResponse(new Coordinate(13, 11),5),service.clean(request));
    }

    @Test
    void testInstructionCaseInSensitiveH013() {
        HooverRequest request=new HooverRequest(new int[]{16, 16},new int[]{13, 11},new int[][]{{13, 11},{12, 11},{13, 10},{13, 12},{14, 11}},"NSWESNEw");
        assertEquals(new HooverResponse(new Coordinate(13, 11),5),service.clean(request));
    }


    @Test
    void testHitTheRightWallN001() {
        HooverRequest request=new HooverRequest(new int[]{5, 5},new int[]{1, 2},new int[][]{{1, 0},{2, 2},{5, 2}},"EEEEEEEEEEEEEEEEE");
        assertEquals(new HooverResponse(new Coordinate(5, 2),2),service.clean(request));
    }
    @Test
    void testHitTheTopWallN002() {
        HooverRequest request=new HooverRequest(new int[]{5, 5},new int[]{1, 2},new int[][]{{1, 0},{2, 2},{5, 2},{1, 3}},"NNNNNNNNNNNNNNNN");
        assertEquals(new HooverResponse(new Coordinate(1, 5),1),service.clean(request));
    }
    @Test
    void testHitTheBottomWallN003() {
        HooverRequest request=new HooverRequest(new int[]{5, 5},new int[]{1, 2},new int[][]{{1, 0},{2, 2},{5, 2}},"SSSSSSSSSSSSSSS");
        assertEquals(new HooverResponse(new Coordinate(1, 0),1),service.clean(request));
    }
    @Test
    void testHitTheLeftWallN004() {
        HooverRequest request=new HooverRequest(new int[]{5, 5},new int[]{3, 2},new int[][]{{1, 0},{2, 2},{5, 2},{1,1}},"WWWWWWWWWWWWWWWW");
        assertEquals(new HooverResponse(new Coordinate(0, 2),1),service.clean(request));
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
            assertThat(e.getMessage(), is("Input parameter room size missing"));
        }
    }

    @Test
    void testHitTheLeftWallTurnAndCleanN004() {
        HooverRequest request=new HooverRequest(new int[]{5, 5},new int[]{3, 2},new int[][]{{1, 2},{2, 2},{2, 4},{3,4},{3,5}},"WWWWWWWWWWWWWWWWNNEENE");
        assertEquals(new HooverResponse(new Coordinate(3, 5),4),service.clean(request));
    }

}
