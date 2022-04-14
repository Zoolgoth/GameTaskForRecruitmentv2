/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package freebreakout;

import javax.imageio.ImageIO;
import java.awt.*;
import java.net.URL;


public class Brick {
    
    private int posx, posy, life;
    private boolean exists = true;
    
    Image[] bricks;
    
    Brick(int x, int y, int l)
    {
        posx = x;
        posy = y;
        life = l;
        if(life<0) life = 0;
        else if(life>4) life = 4;
        if(life == 0) exists = false;
        getResource();
    }
    
    private void getResource()
    {
        try
        {
            ClassLoader cl=this.getClass().getClassLoader();
            bricks = new Image[4];
            URL url = cl.getResource("images/b_green.png");
            bricks[0] = ImageIO.read(url);
            url = cl.getResource("images/b_yellow.png");
            bricks[1] = ImageIO.read(url);
            url = cl.getResource("images/b_red.png");
            bricks[2] = ImageIO.read(url);
            url = cl.getResource("images/b_brown.png");
            bricks[3] = ImageIO.read(url);
        } catch(Exception ex) {};
    }
    
    public boolean contains(int x, int y)
    {
        if(x<=posx+35&&x>=posx-35)
            if(y<=posy+20&&y>=posy-20) return true;
        return false;
    }
    
    public boolean brickExists() {return exists;};
    
    public boolean isHit(int x, int y, int radius)
    {
        if(exists)
        { //X,Y to środek piłki, poprawić współrzędne trochę...
            if((x>=(posx-30-radius))&&(x<=(posx+30+radius)))
                if((y>=(posy-15-radius))&&(y<=(posy+15+radius)))
                {
                    if((--life)==0) exists = false;
                    return true;
                }
        }
        return false;
    }
    
    public boolean isLeftHit(Ball b)
    {
        int x = b.getX();
        int y = b.getY();
        int radius = b.getRadius();
        int miny = b.getMinStepY();
        if((x>=(posx-30-radius))&&(x<=(posx)))
            if((y>=(posy-15-radius+miny))&&(y<=(posy+15+radius+miny)))
                return true;
        return false;
    }
    
    public boolean isVerticalHit(Ball b)
    {
        int x = b.getX();
        int y = b.getY();
        int radius = b.getRadius();
        int miny = b.getMinStepY();
        if((x>=(posx-30-radius))&&(x<=(posx+30+radius)))
            if((y>=(posy-15-radius+miny))&&(y<=(posy+15+radius+miny)))
                return true;
        return false;
    }
    
    public void paint(Graphics2D g)
    {
        if(exists)
            g.drawImage(bricks[life-1], posx-30, posy-15, null);
    }
    
    public int getColor()
    {
        return 0;
    }
}
