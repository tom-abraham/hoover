package com.robotics.hoover.service;

import com.robotics.hoover.model.Coordinate;
import com.robotics.hoover.model.HooverRequest;
import com.robotics.hoover.model.HooverResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HooverService {
    Logger Log = LoggerFactory.getLogger(this.getClass());
    public final int ROOM_MAX_LENGTH=10000;
    public final int ROOM_MAX_WIDTH=10000;
    public final int ROOM_MIN_LENGTH=0 ;
    public final int ROOM_MIN_WIDTH=0 ;

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
        int patchCount=0;
        Set<Coordinate> set = Arrays.stream(patches).map(patch->new Coordinate(patch[0], patch[1])).collect(Collectors.toSet());
        for (Coordinate patch :set) {
            if(patch.getX()>roomSize[0] || patch.getY()>roomSize[1]){
                Log.error("Patch is outside room with length: "+roomSize[0]+"and width: "+roomSize[1]);
                continue;
            }
            if (traceSet.contains(patch)) {
                patchCount++;
            }
        }
        return new HooverResponse(currentCoordinate, patchCount);
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
        if (roomSize[0] <= ROOM_MIN_LENGTH || roomSize[1] <= ROOM_MIN_WIDTH ) {
            retval=false;
            throw new IllegalArgumentException("Room length should be more than "+ROOM_MIN_LENGTH+" or width: "+ROOM_MIN_WIDTH);
        }
        if (roomSize[0] > ROOM_MAX_LENGTH || roomSize[1] > ROOM_MAX_WIDTH) {
            retval=false;
            throw new IllegalArgumentException("Room length should not be more than "+ROOM_MAX_LENGTH+" or width: "+ROOM_MAX_WIDTH);
        }
        if (roomSize[0] < requestedCoordinates[0] || roomSize[1] < requestedCoordinates[1] ) {
            retval=false;
            throw new IllegalArgumentException("Requested coordinates is more than room size ");
        }

        return retval;
    }
}
