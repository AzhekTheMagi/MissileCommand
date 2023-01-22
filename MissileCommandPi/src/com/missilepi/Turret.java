package com.missilepi;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Turret
{
    private BufferedImage turretImage;
    private int turretWidth  = 40;
    private int turretHeight = 40;

    public Turret()
    {
        this.loadTurretImage();
    }

    public void loadTurretImage()
    {
        turretImage = new BufferedImage(20,20,BufferedImage.TYPE_INT_RGB);

        try
        {
            turretImage = ImageIO.read(getClass().getResource("/resource/turret.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public int getTurretWidth()
    {
        return this.turretWidth;
    }

    public int getTurretHeight()
    {
        return this.turretHeight;
    }

    public BufferedImage getTurretImage()
    {
        return this.turretImage;
    }
}
