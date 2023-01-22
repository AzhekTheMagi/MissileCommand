package com.missilepi;

import java.util.ArrayList;

public class GameState
{
    private final ArrayList<Explosion> explosion   = new ArrayList<>();
    private final ArrayList<Missile> playerMissile = new ArrayList<>();
    private final ArrayList<Missile> enemyMissile  = new ArrayList<>();
    private final ArrayList<Missile> updateMissile = new ArrayList<>();
    private final ArrayList<Base>    baseList      = new ArrayList<>();
    private final ArrayList<Integer> baseLocation  = new ArrayList<>();
    private final Turret turret                    = new Turret();
    private final int baseTotal = 5;

    public GameState()
    {
        initialize();
    }

    private void initialize()
    {
        for (int i = 0; i < baseTotal; i++)
        {
            baseList.add(new Base());
        }

        baseLocation.add(50);
        baseLocation.add(150);
        baseLocation.add(300);
        baseLocation.add(500);
        baseLocation.add(700);
    }

    public int getBaseTotal()
    {
        return this.baseTotal;
    }

    /**
     * @param playerMissile the playerMissile to set
     */
    public void addPlayerMissile(Missile playerMissile) {
        this.playerMissile.add(playerMissile);
    }

    /**
     * @param explosion the explosion to set
     */
    public void addExplosion(Explosion explosion) {
        this.explosion.add(explosion);
    }

    /**
     * @return the explosion
     */
    public ArrayList<Explosion> getExplosion()
    {
        return this.explosion;
    }

    /**
     * @return the baseList
     */
    public ArrayList<Base> getBaseList()
    {
        return this.baseList;
    }

    /**
     * @return the enemyMissile
     */
    public ArrayList<Missile> getEnemyMissile()
    {
        return this.enemyMissile;
    }

    /**
     * @return the playerMissile
     */
    public ArrayList<Missile> getPlayerMissile()
    {
        return this.playerMissile;
    }

    public ArrayList<Integer> getBaseLocation()
    {
        return this.baseLocation;
    }

    public ArrayList<Missile> getUpdateMissile() { return this.updateMissile; }

    public Turret getTurret()
    {
        return this.turret;
    }
}
