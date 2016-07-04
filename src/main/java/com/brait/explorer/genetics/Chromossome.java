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

    public void move() {
        x += velocity[0];
        y += velocity[1];
    }

}
