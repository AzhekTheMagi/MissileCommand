package com.missilepi;

public abstract class Missile
{
    double oldX = 350; // Turret static x coordinate position
    double oldY = 700; // Turret static y coordinate position
    double newX;
    double newY;
    double finalX;
    double finalY;
    double velocity = 2;

    public Missile(int x, int y)
    {
        this.finalX = x;
        this.finalY = y;
    }

    public abstract void flightPath();
}
