/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package freebreakout;

import java.util.Random;


public class LevelReader implements Runnable {
    
    private Brick[] br;
    private MainCanvas m;
    private int lvl;
    
    LevelReader(MainCanvas main, int level)
    {
        m=main;
        lvl = level;
    }
    
    private void generateSimpleLevel(int level)
    {
        br = new Brick[10*level];
        Random ra = new Random();
        for (int i=0;i<10;i++)
        {
            for (int j=0;j<level;j++)
            {
                br[i+(j*10)] = new Brick(40+(i*78), 50+(j*40),ra.nextInt(4));
            }
        }
    }

    @Override
    public void run() {
        generateSimpleLevel(lvl);
        m.setBricks(br);
    }
    
}
