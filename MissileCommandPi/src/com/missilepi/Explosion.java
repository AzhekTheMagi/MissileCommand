package com.missilepi;

import java.awt.*;

public class Explosion
{
    private final int xExplosion;
    private final int yExplosion;

    public Explosion(int x, int y)
    {
        this.xExplosion = x;
        this.yExplosion = y;
    }

    public int getXExplosion()
    {
        return this.xExplosion;
    }

    public int getYExplosion()
    {
        return this.yExplosion;
    }

    //Determines whether enemy missile caught within blast range of player missile explosion, returning true if
    //distance within predetermined limits.
    //Formula for calculating missile collision adapted from:
    //https://flatredball.com/documentation/tutorials/math/circle-collision/
    public static boolean areColliding(double C1x, double C2x, double C1y, double C2y)
    {
        double centerDistance = Math.sqrt((Math.pow(C2x - C1x, 2) + Math.pow(C2y - C1y, 2)));

        return (int) centerDistance < 25;
    }

    public static void drawExplosion(Graphics2D g2D, int x, int y, int r)
    {
        g2D.fillOval(x, y, r, r);
    }
}
