package com.brait.explorer.genetics;

import lombok.*;

/**
 * Created by andre on 03/07/16.
 */
@Data
public class Chromossome {

    private int x, y, xVel, yVel;

    public Chromossome(int x, int y, int xVel, int yVel) {
        this.x = x;
        this.y = y;
        this.xVel = xVel;
        this.yVel = yVel;
    }

    public void move(int xLen, int yLen) {
        x += xVel;
        y += yVel;
        if (x < 0) {
            x = 0;
        } else if (x >= xLen) {
            x = xLen - 1;
        }
        if (y < 0) {
            y = 0;
        } else if (y >= yLen) {
            y = yLen - 1;
        }
    }

}
