package com.robotics.hoover;

import com.robotics.hoover.controller.HooverController;
import com.robotics.hoover.model.Coordinate;
import com.robotics.hoover.model.HooverRequest;
import com.robotics.hoover.model.HooverResponse;
import com.robotics.hoover.service.HooverService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
class HooverApplicationTests {
	private final HooverService hooverService = mock(HooverService.class);
	private final  HooverController controller = new HooverController(hooverService);
	@Test
	void testCleanRequestWithProperInputWithSize5x5() {
		HooverRequest request=new HooverRequest(new int[]{5, 5},new int[]{1, 2},new int[][]{{1, 0},{2, 2},{2, 3}},"NNESEESWNWW");
		given(hooverService.clean(request)).willReturn(new HooverResponse(new Coordinate(1, 3),1));
		assertEquals(new HooverResponse(new Coordinate(1, 3),1),controller.cleanRequest(request).getBody());
		assertEquals(HttpStatus.OK,controller.cleanRequest(request).getStatusCode());
	}
}
