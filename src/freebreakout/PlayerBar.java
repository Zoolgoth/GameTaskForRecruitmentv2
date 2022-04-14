/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package freebreakout;

import javax.imageio.ImageIO;
import java.awt.*;
import java.net.URL;


public class PlayerBar {
    private Player p;
    private final int height=15, width=800;
    
    private Image heart;
    
    public PlayerBar(Player a)
    {
        p=a;
        getResource();
    }
    
    private void getResource()
    {
        try
        {
            ClassLoader cl = this.getClass().getClassLoader();
            URL u = cl.getResource("images/heart.png");
            heart = ImageIO.read(u);
        } catch(Exception e) {};
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public void paint(Graphics2D g)
    {
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(0, height, width, height);
        g.drawString("Nazwa gracza: "+p.getName()+"  Punkty: "+Integer.toString(p.getPoints()), 5, 12);
        for(int i=0; i<p.getLives(); i++)
            g.drawImage(heart, 780-(15*i), 3, null);
    }
    
}
