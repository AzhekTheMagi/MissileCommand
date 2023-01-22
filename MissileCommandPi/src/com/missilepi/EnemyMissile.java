package com.missilepi;

public class EnemyMissile extends Missile
{
    int explosionFrame    = 60;
    int frameBuffer       = 60;
    double acceleration   = .01;
    boolean removeMissile = false;
    boolean atDestination = false;

    public EnemyMissile(int x, int y, int launchX, int launchY) {
        super(x, y);
        super.oldX = launchX;
        super.oldY = launchY;
        super.newX = launchX;
        super.newY = launchY;
    }

    //Formula for calculating missile flight update adopted from: https://www.helixsoft.nl/articles/circle/sincos.htm
    //Calculates missile position for each frame during the missile path from beginning to end.
    @Override
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

                super.newY += super.velocity * Math.sin(Math.atan(yFlightDifference / xFlightDifference));
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
    //Currently, non-functional for the enemyMissile class. Requires further attention.
    //Formula for calculating missile collision adapted from:
    //https://flatredball.com/documentation/tutorials/math/circle-collision/
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
