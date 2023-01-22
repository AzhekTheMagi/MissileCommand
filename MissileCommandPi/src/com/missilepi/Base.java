package com.missilepi;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Base
{
    private final boolean isAlive;
    private BufferedImage baseImage;

    public Base()
    {
        this.isAlive = true;
        this.loadBaseImage();
    }

    //Loads static image for cities/bases
    private void loadBaseImage()
    {
        this.baseImage = new BufferedImage(20,20,BufferedImage.TYPE_INT_RGB);

        try
        {
            baseImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resource/city.png")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean getBaseStatus()
    {
        return this.isAlive;
    }

    public int getBaseWidth()
    {
        int baseWidth = 40;
        return baseWidth;
    }

    public int getBaseHeight()

    {
        int baseHeight = 40;
        return baseHeight;
    }

    public BufferedImage getBufferedImage()
    {
        return this.baseImage;
    }
}
