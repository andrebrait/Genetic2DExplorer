package com.brait.explorer.genetics;

import lombok.*;

/**
 * Created by andre on 03/07/16.
 */
@Data
@Setter(AccessLevel.NONE)
public class Chromossome {

    private int x, y;
    private final int velocity[];

    public Chromossome(int x, int y, int[] velocity) {
        this.velocity = velocity;
        this.x = x;
        this.y = y;
    }

    public void move(int xLen, int yLen) {
        x += velocity[0];
        if (x < 0) {
            x += xLen;
        } else if (x >= xLen) {
            x -= xLen;
        }
        y += velocity[1];
        if (y < 0) {
            y += yLen;
        } else if (y >= yLen) {
            y -= yLen;
        }
    }

}
