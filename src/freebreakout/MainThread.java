/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package freebreakout;


public class MainThread implements Runnable {
    
    Main m;

    public MainThread(Main a)
    {
        m=a;
    }
    
    @Override
    public void run() {
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS; 
        long lastLoopTime = System.nanoTime();
        
        while(true)
        {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            m.canvas.refresh();
            try{
                Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
            } catch(Exception e) {};
        }
        
    }
    
}
