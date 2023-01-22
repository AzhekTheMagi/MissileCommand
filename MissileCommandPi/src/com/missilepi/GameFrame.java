package com.missilepi;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameFrame extends JFrame
{
    private final GameState gameState;

    public GameFrame()
    {
        this.gameState = new GameState();
        GameGraphics gameGraphics = new GameGraphics(this.gameState);
        this.setSize(gameGraphics.getFrameWidth(), gameGraphics.getFrameHeight());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(gameGraphics);
        this.setVisible(true);
        gameGraphics.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        gameGraphics.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked ( final MouseEvent e ) {
                // TODO Auto-generated method stub
                // Do nothing
            }

            @Override
            public void mousePressed ( final MouseEvent e )
            {
                gameState.addPlayerMissile(new PlayerMissile(e.getX(), e.getY()));
                gameState.addExplosion(new Explosion(e.getX(), e.getY()));
            }

            @Override
            public void mouseReleased ( final MouseEvent e ) {
                // TODO Auto-generated method stub
                // Do nothing
            }

            @Override
            public void mouseEntered ( final MouseEvent e ) {
                // TODO Auto-generated method stub
                // Do nothing
            }

            @Override
            public void mouseExited ( final MouseEvent e ) {
                // TODO Auto-generated method stub
                // Do nothing
            }
        });
    }
}
