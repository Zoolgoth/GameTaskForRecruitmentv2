/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package freebreakout;

import javax.swing.JOptionPane;


public class Player {
    private String name;
    private int points=0;
    private int lives;
    
    Player(int l)
    {
        name = JOptionPane.showInputDialog(null,"Podaj nazwę gracza","Pytanie",JOptionPane.QUESTION_MESSAGE);
        if(name==null) name = "Bez nazwy";
        lives = l;
    }
    
    public void lostHisLive()
    {
        if(lives>0) lives--;
    }
    
    public void giveNewLive()
    {
        if(lives<9) lives++;
    }
    
    public int getLives()
    {
        return lives;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void addPoints(int p)
    {
        points+=p;
    }
    
    public int getPoints()
    {
        return points;
    }
    
}
