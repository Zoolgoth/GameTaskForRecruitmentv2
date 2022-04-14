/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package freebreakout;

import java.awt.Color;
import java.awt.Graphics2D;


public class Ball {
    private final int radius=5;
    
    private int bx=300, by=300;
    private int movex=-1, movey=-2;
    private int speed=1;
    private boolean stopped = true;
    
    public void update()
    {
        if(!stopped)
        {
            bx+=(speed*movex);
            by+=(speed*movey);
        }
    }
    
    public int getMinStepX() {return speed*movex;}
    
    public int getMinStepY() {return speed*movey;}
    
    public void moveX(int x)
    {
        bx+=x;
    }
    
    public void moveY(int y)
    {
        by+=y;
    }
    
    public void setSpeedModifier(int s)
    {
        speed=s;
    }
    
    public int getSpeedModifier()
    {
        return speed;
    }
    
    public void setXY(int x, int y)
    {
        if(stopped)
        {
            bx=x;
            by=y;
        }
    }
    
    public boolean isStopped() {return stopped;};
    
    public int getRadius() {return radius;};
    
    public int getX() {return bx;}
    
    public int getY() {return by;}
    
    public void start()
    {
        stopped = false;
    }
    
    public void stop()
    {
        stopped = true;
    }
    
    public void hitWall(int mode)
    {
        if(mode==0) movex*=-1; //Uderzono ścianę pionową
        else if(mode==1) movey*=-1; //Uderzono ścianę poziomą
        else if(mode==2) if(movex>0)movex*=-1; //Odbij w lewo
        else if(mode==3) if(movex<0)movex*=-1; //Odbij w prawo
    }
    
    public void hit()
    {
        movey*=-1;
    }
    
    public void paint(Graphics2D g)
    {
        g.setColor(Color.WHITE);
        g.fillOval(bx, by, 12, 12);
    }
    
}