package com.tankwar;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    @Test
    void getImage() {
        for (Direction direction : Direction.values()) {
            //Tank tank = new Tank(0, 0, false, Direction.RIGHT);
            assertNotNull(new Tank(0, 0, false, Direction.RIGHT).getImage());
        }
    }
}