package com.robotics.hoover.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HooverRequest {
    int[] roomSize;
    int[] coords;
    int[][] patches;
    String instructions;
}
