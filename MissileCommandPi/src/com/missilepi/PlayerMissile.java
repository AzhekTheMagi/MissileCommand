package com.missilepi;

public class PlayerMissile extends Missile
{
    int explosionFrame    = 60;
    int frameBuffer       = 60;
    double acceleration   = .1;
    boolean removeMissile = false;
    boolean atDestination = false;

    public PlayerMissile(int x, int y)
    {
        super(x, y);
        super.newX = this.oldX;
        super.newY = this.oldY;
    }

    // Formula for calculating missile flight update adopted from: https://www.helixsoft.nl/articles/circle/sincos.htm
    //Calculates missile position for each frame during the missile path from beginning to end.
    public void flightPath()
    {
        final double flightX = Math.abs(this.finalX - this.newX);
        final double flightY = Math.abs(this.finalY - this.newY);

        if (flightX <= 10 && flightY <= 10)
        {
            this.atDestination = true;
            super.newX = super.finalX;
            super.newY = super.finalY;
            destinationReached();
        }
        else
        {
            final double xFlightDifference = Math.abs(super.finalX - super.oldX);
            final double yFlightDifference = Math.abs(super.finalY - super.oldY);

            if ( xFlightDifference == 0 || yFlightDifference == 0 ) {
                super.newY -= super.velocity;
            }
            else
            {
                final double angle;
                angle = Math.atan(yFlightDifference / xFlightDifference);

                if ((super.finalX - super.oldX) > 0)
                {
                    super.newX += super.velocity * Math.cos(angle);
                }
                else
                {
                    super.newX -= super.velocity * Math.cos(angle);
                }

                super.newY -= super.velocity * Math.sin(angle);
            }

            super.velocity += this.acceleration;
        }
    }

    public boolean isRemovedMissile() { return removeMissile; }

    public boolean isAtDestination() { return this.atDestination; }

    public int getExplosionFrame()
    {
        return this.explosionFrame;
    }

    public int getFrameRate()
    {
        return this.frameBuffer;
    }

    //Determines explosion animation values from beginning to end, removing missile when explosion completes animation.
    private void destinationReached()
    {
        if (this.frameBuffer == 0)
        {
            if (this.explosionFrame == 0)
            {
                this.removeMissile = true;
            }
            else
            {
                this.explosionFrame--;
            }
        }
        else
        {
            this.frameBuffer--;
        }
    }
}
