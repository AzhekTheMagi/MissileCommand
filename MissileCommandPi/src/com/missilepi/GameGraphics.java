package com.missilepi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import java.util.concurrent.*;

public class GameGraphics extends JPanel implements ActionListener
{
    private final int frameWidth  = 800;
    private int playerPoints = 0;
    private final GameState gameState;
    private final JLabel scoreLabel;

    public GameGraphics(GameState gameState)
    {
        this.gameState = gameState;
        scoreLabel = new JLabel(String.valueOf(playerPoints));
        add(scoreLabel, BorderLayout.NORTH);
        ScheduledExecutorService enemyMissileExecutor = Executors.newScheduledThreadPool(1);

        TimerTask enemyMissileTask = new TimerTask() {
            @Override
            public void run() {
                int randomIndex = ThreadLocalRandom.current().nextInt(0, 5);
                gameState.getEnemyMissile().add(new EnemyMissile(gameState.getBaseLocation().get(randomIndex), 720,
                        ThreadLocalRandom.current().nextInt(10, frameWidth), 5));
            }
        };

        Timer timer = new Timer(8, this);
        timer.start();

        //Launches enemy missiles at predetermined times. Time is kept short for testing/presentation purposes.
        ScheduledFuture<?> firstRound = enemyMissileExecutor.scheduleAtFixedRate(enemyMissileTask, 0, 1000, TimeUnit.MILLISECONDS);
        ScheduledFuture<?> secondRound = enemyMissileExecutor.scheduleAtFixedRate(enemyMissileTask, 15, 1, TimeUnit.SECONDS);
        ScheduledFuture<?> thirdRound = enemyMissileExecutor.scheduleAtFixedRate(enemyMissileTask, 30, 1, TimeUnit.SECONDS);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.GREEN);
        graphics2D.fillRect(0,730, frameWidth, 70);

        // Draw bases in play area
        int groundCoordinate = 700;
        for (int i = 0; i < gameState.getBaseTotal(); i++)
        {
            if (gameState.getBaseList().get(i).getBaseStatus())
            {
                g.drawImage(gameState.getBaseList().get(i).getBufferedImage(),
                            gameState.getBaseLocation().get(i), groundCoordinate,
                            gameState.getBaseList().get(i).getBaseWidth(),
                            gameState.getBaseList().get(i).getBaseHeight(), null);
            }
        }

        // Draw turret on play area
        int turretX_Coordinate = 350;
        g.drawImage(gameState.getTurret().getTurretImage(), turretX_Coordinate, groundCoordinate,
                    gameState.getTurret().getTurretWidth(),
                    gameState.getTurret().getTurretHeight(), null);

        scoreLabel.setText(String.valueOf(playerPoints));

        // Launches enemy missiles
        int frameBuffer = 60;
        int minExplosionSize = 2;
        int maxExplosionSize = 20;
        for (int i = 0; i < gameState.getEnemyMissile().size(); i++)
        {
            final EnemyMissile em = (EnemyMissile) gameState.getEnemyMissile().get(i);
            boolean missileDestroyed = false;

            //Determines if enemy missile collided with player explosion.
            for (int j = 0; j < gameState.getExplosion().size(); j++)
            {
                if (Explosion.areColliding(gameState.getEnemyMissile().get(i).newX,
                        gameState.getExplosion().get(j).getXExplosion(),
                        gameState.getEnemyMissile().get(i).newY,
                        gameState.getExplosion().get(j).getYExplosion()))
                {
                    missileDestroyed = true;
                    em.atDestination = true;
                    playerPoints += 10;
                    gameState.getExplosion().clear();
                    break;
                }
            }

            //Initiate enemy missile explosion if enemy missile destroyed or if reached target city.
            if (em.isAtDestination())
            {
                int centerX;
                int centerY;
                int radius;

                setExplosionFrameColor(graphics2D, minExplosionSize, maxExplosionSize, em.getExplosionFrame());

                if (em.getFrameRate() != 0) {
                    centerX = (int) gameState.getEnemyMissile().get(i).newX - ((frameBuffer - em.getFrameRate()) / 2);
                    centerY = (int) gameState.getEnemyMissile().get(i).newY - ((frameBuffer - em.getFrameRate()) / 2);
                    radius = frameBuffer - em.getFrameRate();
                }
                else
                {
                    centerX = (int) gameState.getEnemyMissile().get(i).newX - (em.getExplosionFrame() / 2);
                    centerY = (int) gameState.getEnemyMissile().get(i).newY - (em.getExplosionFrame() / 2);
                    radius = em.getExplosionFrame();
                }

                // Points taken away if player city hit
                if (!missileDestroyed)
                {
                    playerPoints -= 5;
                }

                Explosion.drawExplosion(graphics2D, centerX, centerY, radius);

                //Required since enemyMissile class function destinationReached not functioning as intended.
                gameState.getEnemyMissile().remove(i);
            }
            else
            {
                graphics2D.setColor(Color.WHITE);
                graphics2D.fillOval((int) gameState.getEnemyMissile().get(i).newX + 15,
                                    (int) gameState.getEnemyMissile().get(i).newY, 6, 6);
            }
        }

        //Determines if player missile reached its terminus location, if true initiates explosion or draws missile
        //otherwise.
        for (int i = 0; i < gameState.getPlayerMissile().size(); i++)
        {
            final PlayerMissile pm = (PlayerMissile) gameState.getPlayerMissile().get(i);
            if (pm.isAtDestination())
            {
                int centerX;
                int centerY;
                int radius;

                setExplosionFrameColor(graphics2D, minExplosionSize, maxExplosionSize, pm.getExplosionFrame());

                if (pm.getFrameRate() != 0)
                {
                    centerX = (int) gameState.getPlayerMissile().get(i).newX - ((frameBuffer - pm.getFrameRate()) / 2);
                    centerY = (int) gameState.getPlayerMissile().get(i).newY - ((frameBuffer - pm.getFrameRate()) / 2);
                    radius = frameBuffer - pm.getFrameRate();
                }
                else
                {
                    centerX = (int) gameState.getPlayerMissile().get(i).newX - (pm.getExplosionFrame() / 2);
                    centerY = (int) gameState.getPlayerMissile().get(i).newY - (pm.getExplosionFrame() / 2);
                    radius = pm.getExplosionFrame();
                }

                Explosion.drawExplosion(graphics2D, centerX, centerY, radius);
            }
            else
            {
                graphics2D.setColor(Color.WHITE);
                graphics2D.fillOval((int) gameState.getPlayerMissile().get(i).newX - 6,
                                    (int) gameState.getPlayerMissile().get(i).newY - 6, 6, 6);
            }
        }
    }

    //Colors explosion for each cycle/frame of blast radius.
    private void setExplosionFrameColor(Graphics2D g2D, int minExplosionSize, int maxExplosionSize, int explosionFrame)
    {
        if (explosionFrame % minExplosionSize == 0 && explosionFrame % maxExplosionSize == 0)
        {
            g2D.setColor(Color.RED);
        }
        else
        {
            g2D.setColor(Color.ORANGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.intervalUpdate();
        repaint();
    }

    //Updates enemy and player missile lists coordinate values, replacing each with new coordinates calculated from
    //respective enemyMissile and playerMissile flightPath() functions.
    public void intervalUpdate()
    {
        for (int i = 0; i < gameState.getUpdateMissile().size(); i++)
        {
            if (gameState.getUpdateMissile().get(i) instanceof PlayerMissile)
            {
                gameState.getPlayerMissile().remove(gameState.getUpdateMissile().get(i));
            }
            else
            {
                gameState.getEnemyMissile().remove(gameState.getUpdateMissile().get(i));
            }
        }

        gameState.getUpdateMissile().clear();

        for (int i = 0; i < gameState.getPlayerMissile().size(); i++)
        {
            final PlayerMissile intervalPM = (PlayerMissile) gameState.getPlayerMissile().get(i);

            if (intervalPM.isRemovedMissile())
            {
                gameState.getUpdateMissile().add(gameState.getPlayerMissile().get(i));
            }

            gameState.getPlayerMissile().get(i).flightPath();
        }

        for (int i = 0; i < gameState.getEnemyMissile().size(); i++)
        {
            final EnemyMissile intervalPM = (EnemyMissile) gameState.getEnemyMissile().get(i);

            if (intervalPM.isRemovedMissile())
            {
                gameState.getUpdateMissile().add(gameState.getEnemyMissile().get(i));
            }

            gameState.getEnemyMissile().get(i).flightPath();
        }
    }

    public int getFrameWidth()
    {
        return this.frameWidth;
    }

    public int getFrameHeight()
    {
        int frameHeight = 800;
        return frameHeight;
    }
}
