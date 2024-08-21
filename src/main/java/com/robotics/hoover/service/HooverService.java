package com.robotics.hoover.service;

import com.robotics.hoover.model.Coordinate;
import com.robotics.hoover.model.HooverRequest;
import com.robotics.hoover.model.HooverResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class HooverService {
    Logger Log = LoggerFactory.getLogger(this.getClass());
    @Value("${max.room.size}")
    public int ROOM_MAX_SIZE;
    @Value("${min.room.size}")
    public int ROOM_MIN_SIZE ;

    public HooverResponse clean(HooverRequest hooverRequest) {
        if (!validate(hooverRequest)) {
            Log.debug("Input parameter not correct ");
        }

        int[] requestedCoordinates = hooverRequest.getCoords();
        int[] roomSize = hooverRequest.getRoomSize();
        int[][] patches = hooverRequest.getPatches();
        String instructions = hooverRequest.getInstructions();
        Set<Coordinate> traceSet = new HashSet<>();
        Coordinate currentCoordinate = new Coordinate(requestedCoordinates[0], requestedCoordinates[1]);
        traceSet.add(currentCoordinate);//add starting point first to map
        String[] instructionsList = instructions.split("");
        for (String direction : instructionsList) {
            //hitting the wall
            if (direction.equals("E") && currentCoordinate.getX() + 1 > roomSize[0]) {
                Log.debug("Hit the right wall . Unable to proceed");
                continue;
            }
            if (direction.equals("N") && currentCoordinate.getY() + 1 > roomSize[1]) {
                Log.debug("Hit the top wall. Unable to proceed");
                continue;
            }
            if (direction.equals("W") && currentCoordinate.getX() - 1 < 0) {
                Log.debug("Hit the left wall. Unable to proceed");
                continue;
            }
            if (direction.equals("S") && currentCoordinate.getY() - 1 < 0) {
                Log.debug("Hit the bottom wall. Unable to proceed");
                continue;
            }
            currentCoordinate = move(direction, currentCoordinate);
            traceSet.add(currentCoordinate);
        }
        int count = 0;
        for (int[] patch : patches) {
            if (traceSet.contains(new Coordinate(patch[0], patch[1]))) {
                count++;
            }
        }
        return new HooverResponse(currentCoordinate, count);
    }

    private static Coordinate move(String s, Coordinate currentCoordinate) {
        currentCoordinate = switch (s.toUpperCase()) {
            case "N" -> new Coordinate(currentCoordinate.getX(), currentCoordinate.getY() + 1);
            case "S" -> new Coordinate(currentCoordinate.getX(), currentCoordinate.getY() - 1);
            case "E" -> new Coordinate(currentCoordinate.getX() + 1, currentCoordinate.getY());
            case "W" -> new Coordinate(currentCoordinate.getX() - 1, currentCoordinate.getY());
            default -> currentCoordinate;
        };
        return currentCoordinate;
    }

    boolean validate(HooverRequest hooverRequest) {
        int[] requestedCoordinates = hooverRequest.getCoords();
        int[] roomSize = hooverRequest.getRoomSize();
        int[][] patches = hooverRequest.getPatches();
        String instructions = hooverRequest.getInstructions();
        boolean retval=true;
        //input parameters missing
        if (instructions == null) {
            retval=false;
            throw new IllegalArgumentException("Input parameter instructions missing");
        } else if (roomSize == null) {
            retval=false;
            throw new IllegalArgumentException("Input parameter room size missing");
        } else if (patches == null) {
            retval=false;
            throw new IllegalArgumentException("Input parameter patches missing");
        } else if (requestedCoordinates == null) {
            retval=false;
            throw new IllegalArgumentException("Input parameter coords missing");
        }
        if (roomSize[0] <= ROOM_MIN_SIZE) {
            retval=false;
            throw new IllegalArgumentException("Room size should be more than "+ROOM_MIN_SIZE);
        }
        if (roomSize[0] > ROOM_MAX_SIZE) {
            retval=false;
            throw new IllegalArgumentException("Room size should be more than "+ROOM_MAX_SIZE);
        }
        if (roomSize[0] < requestedCoordinates[0] || roomSize[1] < requestedCoordinates[1] ) {
            retval=false;
            throw new IllegalArgumentException("Requested coordinates is more than room size ");
        }

        return retval;
    }
}
