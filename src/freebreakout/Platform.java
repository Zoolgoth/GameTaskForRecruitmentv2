/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package freebreakout;

import java.awt.Color;
import java.awt.Graphics2D;


public class Platform {
    
    private int px=0, py=0;
    
    Platform(int x, int y)
    {
        px=x;
        py=y;
    }
    
    public void setX(int a, Ball ball)
    {
        px=a;
    }
    
    public void setY(int a)
    {
        py=a;
    }
    
    public int getY()
    {
        return py;
    }
    
    public int getX()
    {
        return px;
    }
    
    public boolean contains(int x, int y)
    {
        if(x<=px+55&&x>=px-55)
            if(y<=py+15&&y>=py-15) return true;
        return false;
    }
    
    public boolean isTopHit(int x, int y, int radius)
    {
        if((x>=(px-50-radius))&&(x<=(px+50+radius)))
            if((y>=(py-10-radius))&&(y<=(py-10+radius))) //5 + ball radius
            {
                return true;
            }
        return false;
    }
    
    public boolean isLeftHit(int x, int y, int radius)
    {
        if((x>=(px-50-radius))&&(x<=(px-50)))
            if((y>=(py-10-radius))&&(y<=(py+10+radius))) //5 + ball radius
            {
                return true;
            }
        return false;
    }
    
    public boolean isRightHit(int x, int y, int radius)
    {
        if((x>=(px+50+radius))&&(x<=(px+50)))
            if((y>=(py-10-radius))&&(y<=(py+10+radius))) //5 + ball radius
            {
                return true;
            }
        return false;
    }
    
    public void paint(Graphics2D g)
    {
        g.setColor(Color.RED);
        g.fillRect(px-50, py-5, 100, 10);
    }
    
}
