/**
 * This is the class that contains the elements of the drawing. This is also where most of the methods are located related to changing the elements on the screen.
 *
 * @author Castro, Butch Adrian A. & Go, Gerick Jeremiah Niño N.
 * @version 05/19/19
 */

/*
I have not discussed the Java language code
in my program with anyone other than my instructor
or the teaching assistants assigned to this course.

I have not used the Java language code obtained
from another student, or any other unauthorized
source, either modified or unmodified.

If any Java language code or documentation
used in my program was obtained from another source,
such as a text book or webpage, those have been clearly noted with a proper citation in the comments of my code.
*/

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.geom.*;

public class GameCanvas extends JComponent implements ActionListener
{
    private int width, height, playerID, otherPlayer, winLoseMsgReceived;
    private Player player1, player2;
    private Timer timer,updatePosTimer;
    private Image wallpaper, dead;
    private Mouse mouse;
    private GameFrame.ClientSideConnection csc;
    private Graphics2D g2d;

    public GameCanvas(int w, int h, Color c, Container contentPane, int ID, int otherPlayer, GameFrame.ClientSideConnection csc)
    {
        width = w;
        height = h;
        playerID = ID;
        this.otherPlayer = otherPlayer;
        this.csc = csc;
        setPreferredSize(new Dimension(width,height));
        player1 = new Player(30,360,32,csc, contentPane, playerID, "red");
        player2 = new Player(1210,360,32,csc, contentPane, playerID, "blue");
        wallpaper = Toolkit.getDefaultToolkit().createImage("./assets/map.png");
        dead = Toolkit.getDefaultToolkit().createImage("./assets/dead.png");
        timer = new Timer(5, this);
        timer.start();
        updatePosTimer = new Timer(5000, new updatePositionTimer());
        updatePosTimer.start();
        winLoseMsgReceived = 0;

    }
    /**
     * This is the updatePositionTimer inner class.
     * @author Castro, Butch Adrian A.
     * this is a timer that sends the exact location of the player, in order to compensate for any lag via connection or thread. The player's position is sent every 5 seconds
     *
     */
    public class updatePositionTimer implements ActionListener
    {
        public void actionPerformed( ActionEvent ae)
        {
            csc.sendAction(8,getPlayer().getX());
            csc.sendAction(9,getPlayer().getY());
        }
    }
    public void resetGame() //resets values of players and their weapons, locations, health, and other things in the canvas. 
    {
        player1.setHealth(100);
        player2.setHealth(100);
        player1.setX(30);
        player1.setY(360);
        player2.setX(1210);
        player2.setY(360);
        player1.setPlayerWeapon("none");
        player2.setPlayerWeapon("none");
        repaint();
        player1.setResetDecision(false);
        player2.setResetDecision(false);
        setWinLoseMsgReceived(0);
    }
    public void actionPerformed(ActionEvent ae) //everytime an action is picked up by this program, these update their values, and updates the canvas indirectly.
    {
    	player1.updatePosition();
    	player2.updatePosition();
        player1.updateHitbox();
        player2.updateHitbox();
		repaint();
    }

    public void paintComponent(Graphics g)
    {
        Music WinLoseMsg = new Music();
        g.drawImage(wallpaper,0,0,null);
        g2d = (Graphics2D) g;
        Rectangle2D.Double hpRed = new Rectangle2D.Double(player1.getX()-50,player1.getY()-40,player1.getHealth(),10); //HP BAR PLAYER 1
        g2d.setColor(Color.RED);
        g2d.fill(hpRed);
        Rectangle2D.Double hpBlue = new Rectangle2D.Double(player2.getX()-50,player2.getY()-40,player2.getHealth(),10); //HP BAR PLAYER 2
        g2d.setColor(Color.BLUE);
        g2d.fill(hpBlue);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        player1.draw(g2d);
        player2.draw(g2d);


        /*
            this code below draws the HP of each player and puts it on top of their HP Bar.
        */
        g.setColor(Color.BLACK);
        g.setFont(new Font(null,Font.BOLD,10));

        g.drawString(String.valueOf(player1.getHealth()),(int)(player1.getX()-40),(int)(player1.getY()-32));
        g.drawString(String.valueOf(player2.getHealth()),(int)(player2.getX()-40),(int)(player2.getY()-32));
        
        // to give congratulations to winning player and prompt them if they want to have a rematch or not.
        if (getOpponent().getHealth() <= 0)
        {
            g.drawImage(dead,(int)(getOpponent().getX()-124),(int)(getOpponent().getY()-124),null);
            g.setColor(Color.BLUE);
            g.setFont(new Font(null,Font.BOLD,110));
            g.drawString("YOU ARE VICTORIOUS",20,300);
            g.setFont(new Font(null,Font.BOLD,50));
            g.drawString("Press 0 to challenge your enemy to a rematch!",80,700);
            if (winLoseMsgReceived == 0){
            WinLoseMsg.playWeaponSFX("./sfx/win.wav");
            setWinLoseMsgReceived(1);
            }   
            
            if (getPlayer().getResetDecision() == true && getOpponent().getResetDecision() == false)
            {
                g.drawString("You have challenged your enemy to a rematch!", 80,500);
            }
            if (getPlayer().getResetDecision() == false && getOpponent().getResetDecision() == true)
            {
                g.drawString("Enemy has challenged you to a rematch!", 140,500);
            }
            if (getPlayer().getResetDecision() == true && getOpponent().getResetDecision() == true)
            {
                resetGame();
            }
        }
        if (getPlayer().getHealth() <= 0)
        {
            g.drawImage(dead,(int)(getPlayer().getX()-124),(int)(getPlayer().getY()-124),null);
            g.setColor(Color.RED);
            g.setFont(new Font(null,Font.BOLD,110));
            g.drawString("YOU SUFFER DEFEAT",40,300);
            g.setFont(new Font(null,Font.BOLD,50));
            g.drawString("Press 0 to challenge your enemy to a rematch!",80,700);
            if (winLoseMsgReceived == 0){
            WinLoseMsg.playWeaponSFX("./sfx/lose.wav");
            setWinLoseMsgReceived(1);
            }   

            if (getPlayer().getResetDecision() == true && getOpponent().getResetDecision() == false)
            {
                g.drawString("You have challenged your enemy to a rematch!", 80,500);
            }
            if (getPlayer().getResetDecision() == false && getOpponent().getResetDecision() == true)
            {
                g.drawString("Enemy has challenged you to a rematch!", 140,500);
            }
            if (getPlayer().getResetDecision() == true && getOpponent().getResetDecision() == true)
            {
                resetGame();
            }
        }
    }
    public Player getPlayer() {
    	if(playerID==1) {
    		return player1;
    	} else {
    		return player2;
    	}
    }
    
    public Player getOpponent() {
    	if(playerID==1) {
    		return player2;
    	} else {
    		return player1;
    	}
    }
    public int getPlayerID() {
    	return playerID;
    }
    public int winLoseMsgReceived() 
    {
        return winLoseMsgReceived;
    }
    public void setWinLoseMsgReceived(int i) //if winLoseMsgReceived == 1, then the message has already been played. the resetGame() method will set this back to 0.
    {
        winLoseMsgReceived = i;
    }
}