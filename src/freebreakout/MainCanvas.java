/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package freebreakout;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;
import java.net.URL;


public class MainCanvas extends Canvas {
    Main m;
    VolatileImage vi;
    Graphics2D gr;
    Image background;
    boolean nowPainting = false;
    boolean loading = false;
    
    Platform platform = new Platform(50,500);
    Ball ball = new Ball();
    Player player;
    PlayerBar pb;
    
    boolean gameOver = false;
    
    Brick[] bricks;
    //DEBUG
    long t=0, t1=0, fps=0;
    
    MainCanvas(Main main)
    {
        setIgnoreRepaint(true);
        
        m = main;
        
        vi = m.createVolatileImage(800, 600);
        gr = vi.createGraphics();
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        getResource();
        newGame();
        
        t = System.currentTimeMillis();
    }
    
    public void setSpeed(int s)
    {
        ball.setSpeedModifier(s);
    }
    
    private void fpsMeter()
    {
        if(System.currentTimeMillis()>=t+1000) //Upłynęła sekunda
        {
            t = System.currentTimeMillis();
            fps = t1;
            t1=0;
        } else t1++;
    }
    
    private void newGame()
    {
        player = new Player(3);
        pb = new PlayerBar(player);
        ball.stop();
        Thread lvlrdr = new Thread(new LevelReader(this, 3), "lr");
        lvlrdr.start();
        loading = true;
        gameOver = false;
    }
    
    private void getResource()
    {
        try
        {
            ClassLoader cl=this.getClass().getClassLoader();
            URL url = cl.getResource("images/background.png");
            background = ImageIO.read(url);
        } catch(Exception ex) {};
    }
    
    private void updateGame()
    {
        boolean bricksPainted = false;
        gr.drawImage(background, 0,0, m);
        pb.paint(gr);
        /*Ściany*/
        int b_rad = ball.getRadius();
        if(!ball.isStopped()) //Inaczej gdy piłka jest zatrzymana może wpaść w pętlę nieskończoną
        {
            if(ball.getX()>=(780-b_rad)||ball.getX()<=(0+b_rad)) {//Uderzenie ściany pionowej
                ball.hitWall(0);
                while(ball.getX()>=(780-b_rad)||ball.getX()<=(0+b_rad)) ball.update();
            } 
            if(ball.getY()<=(0+pb.getHeight()+b_rad)) {//Uderzenie ściany poziomej
                ball.hitWall(1);
                while(ball.getY()<=(0+pb.getHeight()+b_rad)) ball.update();
            } 
            if(ball.getY()>=(600-b_rad)) {
                ball.stop();
                player.lostHisLive();
                player.addPoints(-10000);
                if(player.getLives()==0) gameOver = true;
            }
        }
        /*Paletka*/
        platform.setX(m.getMouseX(), ball);
        platform.paint(gr);
        if(platform.isLeftHit(ball.getX(), ball.getY(), b_rad)) {
            ball.hitWall(2);
            while(platform.contains(ball.getX(), ball.getY())) ball.moveX(-1); //Odsuwa piłkę od paletki
        }
        else if(platform.isRightHit(ball.getX(), ball.getY(), b_rad)) {
            ball.hitWall(3);
            while(platform.contains(ball.getX(), ball.getY())) ball.moveX(1);
        }
        else if(platform.isTopHit(ball.getX(), ball.getY(), b_rad)) {
            ball.hit();
            while(platform.contains(ball.getX(), ball.getY())) ball.moveY(-1);
        }
        
        /*Cegiełki*/
        for(int i=0;i<bricks.length;i++) {
            if(bricks[i].isHit(ball.getX(), ball.getY(), b_rad)){
                if(bricks[i].isVerticalHit(ball)) ball.hitWall(0); //Odbicie od lewej ścianki
               // else if(bricks[i].isRightHit(ball)) ball.hitWall(0); //Odbicie od prawej ścianki
                else ball.hit(); //Odbicie od góry/dołu
                while(bricks[i].contains(ball.getX(), ball.getY())) ball.update(); //Zabezpiecza przed wielokrotnym zbiciem
                player.addPoints(ball.getSpeedModifier()*1000);
            }
            bricks[i].paint(gr);
            if(bricks[i].brickExists()) bricksPainted = true;
        }
        
        /*Piłka*/
        if(m.Clicked()) ball.start();
        if(ball.isStopped()) ball.setXY(m.getMouseX()-b_rad, platform.getY()-10-b_rad-1);
        ball.update();
        ball.paint(gr);
        
        if(!bricksPainted&&!loading) //Załaduj nowy level
        {
            ball.stop();
            //Poniższa linia jest b. ważna. Jeżeli nie zresetujemy położenia piłki to przy generowaniu nowej "mapy" się zapętlimy
            ball.setXY(m.getMouseX()-b_rad, platform.getY()-10-b_rad-1);
            player.giveNewLive();
            Thread lvlrdr = new Thread(new LevelReader(this, 3), "lr");
            lvlrdr.start();
            loading = true;
        }
        //FPS METER
        fpsMeter();
        gr.setColor(Color.WHITE);
        gr.drawString("FPS: "+Long.toString(fps), 5, 570);
    }
    
    public void setBricks(Brick[] b)
    {
        bricks = b;
        loading = false;
    }
    
    private void finalScreen()
    {
        gr.drawImage(background, 0,0, m);
        Font f1 = gr.getFont();
        Font f = new Font("Arial", Font.BOLD, 36);
        gr.setFont(f);
        gr.drawString("Koniec gry", 300, 100);
        f = new Font("Arial", Font.PLAIN, 24);
        gr.setFont(f);
        gr.drawString("Zdobyłeś/aś "+Integer.toString(player.getPoints())+
                " punktów.", 240, 150);
        f = new Font("Arial", Font.PLAIN, 16);
        gr.setFont(f);
        gr.drawString("Kliknij, aby rozpocząć nową grę.", 280, 190);
        gr.setFont(f1);
        
        if(m.Clicked()) newGame();
        m.clickRelease(); //Trzeba zwolnić kliknięcie, inaczej gra sama się rozpocznie
    }
    
    private void loadingScreen()
    {
        gr.drawImage(background, 0,0, m);
        Font f1 = gr.getFont();
        Font f = new Font("Arial", Font.BOLD, 36);
        gr.setFont(f);
        gr.drawString("Wczytywanie", 300, 100);
        gr.setFont(f1);
    }
    
    public void refresh()
    {     
        if(nowPainting) return;
        nowPainting = true;
        if(!loading)
        {
            if(!gameOver) updateGame();
                else finalScreen();
        } else loadingScreen();
        BufferStrategy bs=getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.drawImage(vi,0,0,this);
        g.dispose();
        bs.show();
        Toolkit.getDefaultToolkit().sync();
        nowPainting = false;
    }
}
