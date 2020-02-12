/**
 * This is the class that contains the Player object. Anything related to the player's movement, attacks, and change of weapons are found here.
 *
 * @author Castro, Butch Adrian A. & Go, Gerick Jeremiah Niño N.
 * @version 05/19/2019
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
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Timer.*;


public class Player implements KeyListener
{
    private double rotation,x,y,size,velocityY,velocityX,hypotenuse,hypotenuse2,hypotenuse3,hitboxX,hitboxY,hitboxX2,hitboxY2,hitboxX3,hitboxY3,hitboxX4,hitboxY4,hitboxY5,hitboxX5,weaponIndex;
    private Image playerSprite,playerAttackSprite;
    private Boolean playerShot,showHitbox,movingXpos,movingXneg,movingYpos,movingYneg,wantToResetGame;
    private String weapon, color;
    private GameFrame.ClientSideConnection csc;
    private int playerID, health;

    /**
     * Constructor for the class. Initializes the 
     * @param x
     * @param y
     * @param size
     * @param color
     * @param csc
     * @param contentPane
     */
    public Player(double x, double y, double size, GameFrame.ClientSideConnection csc, Container contentPane, int playerID, String color)
    {
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;
        this.csc = csc;
        this.playerID = playerID;
        health = 100;
        weaponIndex = 0;
        setPlayerShot(false);
        setPlayerWeapon("none");
        showHitbox = false;
        wantToResetGame = false;
    }

    public void updatePosition()
    {
        // this updates the position of the player and also makes sure that the
        // player doesn't go through walls and borders
        // it also denies movement for players that have 0 or less health
        if (x - 16 < 0)
        {
            velocityX = 0;
            x = 16;
        }

        if (x + size - 16 > 1280)
        {
            velocityX = 0;
            x = 1280-size+16;
        }

        if (y - 16< 0)
        {
            velocityY = 0;
            y = 16;
        }

        if (y + size - 16 > 720)
        {
            velocityY = 0;
            y = 720-size+16;
        }

        // UNIT COLLISION WITH WALLS 
        // this code makes unit collision zones for each wall, and shifts you back depending on which wall you are colliding with.
        // WALL STRUCTURE 1
        if (y + size - 16> 131 && y - 16< 329 && x + size- 16 > 131 && x + size- 16 < 160)
        {
            velocityX = 0;
            x += -3;
        }
        if (y + size- 16 > 131 && y + size - 16< 160 && x + size- 16 > 131 && x - 16< 395)
        {
            velocityY = 0;
            y += -3;
        }
        if (y - 16 < 197 && y - 16 > 170 && x - 16> 197 && x + 16 < 400)
        {
            velocityY = 0;
            y += 3;
        }
        if (y + size - 16> 132 && y- 16 < 329 && x - 16< 198 && x- 16 + size > 171)
        {
            velocityX = 0;
            x += 3;
        }
        if (y- 16 < 329 && y - 16> 310 && x + size - 16> 132 && x - 16< 197)
        {
            velocityY = 0;
            y += 3;
        }
        if (y - 16< 198 && y + size - 16> 131 && x- 16 < 396 && x - 16> 370)
        {
            velocityX = 0;
            x += 3;
        }

        //WALL STRUCTURE 2

        if (y - 16< 593 && y - 16> 570 && x + size - 16> 132 && x - 16< 528)
        {
            velocityY = 0;
            y += 3;
        }
        if (y - 16< 593 && y + size- 16 > 462 && x + size - 16> 131 && x + size- 16 < 160)
        {
            velocityX = 0;
            x += -3;
        }
        if (y + 16< 596 && y + size - 16> 329 && x - 16< 528 && x - 16> 505)
        {
            velocityX = 0;
            x += 3;
        }
        if (y + size - 16 > 527 && y + size - 16 < 550 && x -16 > 197 && x - 16 < 462)
        {
            velocityY = 0;
            y += -3;
        }
        if (y + size- 16 > 329 && y + size - 16< 528 && x + size- 16 > 461 && x + size - 16 < 489)
        {
            velocityX = 0;
            x += -3;
        }
        if (y + size - 16> 462 && y + size - 16< 528 && x - 16< 198 && x - 16> 170)
        {
            velocityX = 0;
            x += 3;
        }
        if (y + size - 16> 462 && y + size - 16< 490 && x + size - 16> 131 && x- 16 < 198)
        {
            velocityY = 0;
            y += -3;
        }
        if (y  > 329 - 16 && y  < 355- 16 && x + size- 16 > 462 && x- 16 < 528)
        {
            velocityY = 0;
            y += -3;
        }

        //WALL STRUCTURE 3

        if (y - 16< 329 && y - 16> 305 && x + size - 16> 1081 && x - 16< 1148)
        {
            velocityY = 0;
            y += 3;
        }
        if (y + size- 16 > 131 && y - 16< 329 && x - 16< 1148 && x - 16> 1120)
        {
            velocityX = 0;
            x += 3;
        }
        if (y + size - 16> 131 && y + size - 16< 160 && x + size - 16> 752 && x - 16< 1148)
        {
            velocityY = 0;
            y += -3;
        }
        if (y + size - 16> 131 && y - 16< 395 && x + size- 16 > 751 && x + size - 16< 779)
        {
            velocityX = 0;
            x += -3;
        }
        if (y - 16< 395 && y - 16> 365 && x + size - 16> 751 && x - 16< 818)
        {
            velocityY = 0;
            y += 3;
        }
        if (y - 16> 195 && y - 16< 395 && x - 16< 818 && x - 16 > 790)
        {
            velocityX = 0;
            x += 3;
        }
        if (y - 16< 198 && y - 16> 170 && x- 16 > 817 && x + size - 16< 1083)
        {
            velocityY = 0;
            y += 3;
        }
        if (y- 16 > 195 && y - 16< 329 && x + size - 16> 1082 && x - 16< 1109)
        {
            velocityX = 0;
            x += -3;
        }

        //WALL STRUCTURE 4
        if (y + size - 16> 527 && y + size- 16 < 550 && x + size - 16> 884 && x + size - 16< 1084)
        {
            velocityY = 0;
            y += -3;
        }
        if (y + size - 16> 461 && y + size- 16 < 528 && x + size - 16> 1082 && x + size - 16< 1105)
        {
            velocityX = 0;
            x += -3;
        }
        if (y + size - 16> 461 && y + size - 16< 485 && x + size - 16> 1082 && x- 16 < 1148)
        {
            velocityY = 0;
            y += -3;
        }
        if (y + size - 16> 461 && y - 16< 595 && x - 16< 1148 && x- 16 > 1125)
        {
            velocityX = 0;
            x += 3;
        }
        if (y - 16< 593 && y - 16> 570 && x + size - 16> 884 && x- 16< 1148)
        {
            velocityY = 0;
            y += 3;
        }
        if (y + size - 16> 528 && y - 16< 594 && x + size - 16> 884 && x + size - 16< 906)
        {
            velocityX = 0;
            x += -3;
        }

        if (health > 0)
        {
            y += velocityY;
            x += velocityX;
        }
        else
        {
            y += 0;
            x += 0;
        }
    }
    public void updateHitbox()
    {
        //this code gets the X and Y values of the hitbox of the weapon used. The hitbox determines the area wherein enemy player will get hit if mousePressed at the same time he's inside the hitbox.
        //this runs everytime an action is performed by the player
        if (weapon == "sword") //sword has 2 small hitboxes
        {
            hypotenuse = 32;
            hypotenuse2 = 64;
            hitboxX = x + hypotenuse*Math.cos(rotation);
            hitboxY = y + hypotenuse*Math.sin(rotation);
            hitboxX2 = x + hypotenuse2*Math.cos(rotation);
            hitboxY2 = y + hypotenuse2*Math.sin(rotation);
        }
        else if (weapon == "spear") //spear has 3 boxes worth of hitbox, longer range than sword
        {
            hypotenuse = 32;
            hypotenuse2 = 64;
            hypotenuse3 = 96;
            hitboxX = x + hypotenuse*Math.cos(rotation);
            hitboxY = y + hypotenuse*Math.sin(rotation);
            hitboxX2 = x + hypotenuse2*Math.cos(rotation);
            hitboxY2 = y + hypotenuse2*Math.sin(rotation);
            hitboxX3 = x + hypotenuse3*Math.cos(rotation);
            hitboxY3 = y + hypotenuse3*Math.sin(rotation);
        }
        else if (weapon == "axe") //axe has 5 hitboxes surrounding 180deg in front of ur sprite when mousePressed
        {
            hypotenuse = 64;
            hitboxX = x + hypotenuse*Math.cos(rotation);
            hitboxY = y + hypotenuse*Math.sin(rotation);
            hitboxX2 = x + hypotenuse*Math.cos(rotation + Math.toRadians(-90));
            hitboxY2 = y + hypotenuse*Math.sin(rotation + Math.toRadians(-90));
            hitboxX3 = x + hypotenuse*Math.cos(rotation + Math.toRadians(90));
            hitboxY3 = y + hypotenuse*Math.sin(rotation + Math.toRadians(90));
            hitboxX4 = x + hypotenuse*Math.cos(rotation + Math.toRadians(45));
            hitboxY4 = y + hypotenuse*Math.sin(rotation + Math.toRadians(45));
            hitboxX5 = x + hypotenuse*Math.cos(rotation + Math.toRadians(-45));
            hitboxY5 = y + hypotenuse*Math.sin(rotation + Math.toRadians(-45));
        }
        else if (weapon == "gun") //gun has one small long-range hitbox, however fixed range.
        {
            hypotenuse = 512;
            hitboxX = x + hypotenuse*Math.cos(rotation);
            hitboxY = y + hypotenuse*Math.sin(rotation);
        }
    }

    public void draw(Graphics2D g2d) 
    {
        AffineTransform reset = g2d.getTransform();
        Ellipse2D.Double e1 = new Ellipse2D.Double(x-16,y-16,32,32);
        g2d.setColor(Color.BLACK);
        g2d.fill(e1);
        g2d.rotate(rotation,x,y);

        if (playerShot == false) // this code changes the animation of the sprite depending on whether it's attacking or not.
        {
            g2d.drawImage(playerSprite,(int)(x-124),(int)(y-124),null);
        }
        else if (playerShot == true)
        {
            g2d.drawImage(playerAttackSprite,(int)(x-124),(int)(y-124),null);
        }
        g2d.setTransform(reset);

        //these are the codes that enables the player to see the hitbox regions of each weapon. If the enemy collides with the weapon hitbox just as player clicks, he takes damage.
        if(weapon == "sword")
        {
            Rectangle2D.Double hitbox = new Rectangle2D.Double(hitboxX-16,hitboxY-16,32,32);
            Rectangle2D.Double hitbox2 = new Rectangle2D.Double(hitboxX2-16,hitboxY2-16,32,32);

            if(showHitbox == true)
            {
                g2d.setColor(Color.GREEN);
                g2d.fill(hitbox);
                g2d.fill(hitbox2);
            }

        }
        else if(weapon == "spear")
        {
            Rectangle2D.Double hitbox = new Rectangle2D.Double(hitboxX-16,hitboxY-32,32,64);
            Rectangle.Double hitbox2 = new Rectangle2D.Double(hitboxX2-16,hitboxY2-32,32,64);
            Rectangle.Double hitbox3 = new Rectangle2D.Double(hitboxX3-16,hitboxY3-32,32,64);

            if(showHitbox == true)
            {
                g2d.setColor(Color.YELLOW);
                g2d.fill(hitbox);
                g2d.fill(hitbox2);
                g2d.fill(hitbox3);
            }

        }
        else if(weapon == "axe")
        {
            Rectangle2D.Double hitbox = new Rectangle2D.Double(hitboxX-32,hitboxY-16,64,32);
            Rectangle2D.Double hitbox2 = new Rectangle2D.Double(hitboxX2-32,hitboxY2-16,64,32);
            Rectangle2D.Double hitbox3 = new Rectangle2D.Double(hitboxX3-32,hitboxY3-16,64,32);
            Rectangle2D.Double hitbox4 = new Rectangle2D.Double(hitboxX4-32,hitboxY4-16,64,32);
            Rectangle2D.Double hitbox5 = new Rectangle2D.Double(hitboxX5-32,hitboxY5-16,64,32);
            g2d.setColor(Color.PINK);
            if (showHitbox == true)
            {
                g2d.fill(hitbox);
                g2d.fill(hitbox2);
                g2d.fill(hitbox3);
                g2d.fill(hitbox4);
                g2d.fill(hitbox5);
            }
        }
        else if(weapon == "gun")
        {
            Ellipse2D.Double hitbox = new Ellipse2D.Double(hitboxX-16,hitboxY-16,32,32);
            g2d.setColor(Color.GRAY);
            g2d.fill(hitbox);
            Line2D.Double l1 = new Line2D.Double(hitboxX,hitboxY-16,hitboxX,hitboxY+16);
            Line2D.Double l2 = new Line2D.Double(hitboxX-16,hitboxY,hitboxX+16,hitboxY);
            g2d.setColor(Color.BLACK);
            g2d.draw(l1);
            g2d.draw(l2);
        }
    }

    // this increases velocity of the Player depending on WASD
    public void keyPressed(KeyEvent e)
    {
        int pressedKey = e.getKeyCode();

        if (pressedKey == KeyEvent.VK_0) // this is responsible for sending to opponent that player wants to have a rematch.
        {
            wantToResetGame = true;
            csc.sendAction(10,0);
        }
        if (health > 0) // just so player cannot move if he is dead. All of these codes involve movement via WASD keys and changing of weapons by 1,2,3,4 keys.
        {
            if (pressedKey == KeyEvent.VK_O) {
                    showHitbox = true;
                }
            if (pressedKey == KeyEvent.VK_P) {
                    showHitbox = false;
                }
            if (pressedKey == KeyEvent.VK_1 && x > 530 && x < 750 && y > 133 && y < 592) { //change of weapons are only allowed while in this space indicated in this code.
                setPlayerWeapon("sword");
                csc.sendAction(3,getWeaponIndex());
                System.out.println("You pressed 1 for SWORD");
            } else if (pressedKey == KeyEvent.VK_2 && x > 530 && x < 750 && y > 133 && y < 592) {
                setPlayerWeapon("spear");
                csc.sendAction(3,getWeaponIndex());
                System.out.println("You pressed 2 for SPEAR");
            } else if (pressedKey == KeyEvent.VK_3 && x > 530 && x < 750 && y > 133 && y < 592) {
                setPlayerWeapon("axe");
                csc.sendAction(3,getWeaponIndex());
                System.out.println("You pressed 3 for AXE");
            } else if (pressedKey == KeyEvent.VK_4 && x > 530 && x < 750 && y > 133 && y < 592) {
                setPlayerWeapon("gun");
                csc.sendAction(3,getWeaponIndex());
                System.out.println("You pressed 4 for GUN");
                
            } if (pressedKey == KeyEvent.VK_W) {
                velocityY = -3;
                csc.sendAction(0,1);
               
            } else if (pressedKey == KeyEvent.VK_S) {
                velocityY = 3;
                csc.sendAction(1,1);
            
            } else if (pressedKey == KeyEvent.VK_A) {
                velocityX = -3;
                csc.sendAction(6,1);
               
            } else if (pressedKey == KeyEvent.VK_D) {
                velocityX = 3;
                csc.sendAction(7,1);  
            }
        }
    }
    // this allows so that the player velocity resets to 0 when key is released
    public void keyReleased(KeyEvent e)
    {
        int pressedKey = e.getKeyCode();
        if (pressedKey == KeyEvent.VK_W) {
            velocityY = 0;
            csc.sendAction(0,0);
        } else if (pressedKey == KeyEvent.VK_S) {
            velocityY = 0;
            csc.sendAction(1,0);
        } else if (pressedKey == KeyEvent.VK_A) {
            velocityX = 0;
            csc.sendAction(6,0);
        } else if (pressedKey == KeyEvent.VK_D) {
            velocityX = 0;
            csc.sendAction(7,0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e){
    	
    }
    public void setResetDecision(Boolean b) //this method sets if player wants to have rematch.
    {
        wantToResetGame = b;
    }
    public Boolean getResetDecision() //gets value if player wants to reset the game
    {
        return wantToResetGame;
    }

    // the next four methods are allow the enemy client to send information to this player's client. If true, he goes in that certain direction.
    public void movingXpos(Boolean b) 
    {
        if(b == true)
        {
            velocityX = +3;
        }
        else
        {
            velocityX = 0;
        }
    }
    public void movingXneg(Boolean b)
    {
        if(b == true)
        {
            velocityX = -3;
        } 
        else
        {
            velocityX = 0;
        }  
    }
    public void movingYpos(Boolean b)
    {
        if(b == true)
        {
            velocityY = +3;
        }
        else
        {
            velocityY = 0;
        }
    }
    public void movingYneg(Boolean b)
    {
        if(b == true)
        {
            velocityY = -3;
        }
        else
        {
            velocityY = 0;
        }
    }
    public void setRotation(double deg) { //rotation of the sprite depending on the where the mouse cursor is relative to the player's position. This deg comes from mouseMoved in the Mouse class.
        rotation = deg;
    }
    
    public double getRotation() { 
    	return rotation;
    }
    
    public void setX(double x) {
    	this.x = x;
    }
    
    public void setY(double y) {
    	this.y = y;
    }

    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setPlayerShot(Boolean b) { // if true, player has shot/attacked.
        playerShot = b;
    }
    
    public void setPlayerWeapon(String s) { //allows the client to differentiate between player1 and player2 by usage of different sprites. 
        weapon = s;
        if (color=="red")
        {
            if (weapon == "none") {
            	playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/r.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/r.png");	
            } else if(weapon == "sword") {
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/rsword.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/rswordattack.png");
            } else if(weapon == "spear") {
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/rspear.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/rspearattack.png");
            } else if(weapon == "axe") {
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/raxe.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/raxeattack.png");
            } else if(weapon == "gun") {
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/rgun.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/rgunattack.png");
            }
        } 
        else
        {
            if (weapon == "none") {
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/b.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/b.png"); 
            } else if(weapon == "sword") {
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/bsword.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/bswordattack.png");
            } else if(weapon == "spear") {
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/bspear.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/bspearattack.png");
            } else if(weapon == "axe") {
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/baxe.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/baxeattack.png");
            } else if(weapon == "gun") {
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/bgun.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/bgunattack.png");
            }
        }
    }
    public String getPlayerWeapon() {
        return weapon;
    }
    
    public void setHealth(int h)
    {
        health = h;
    }

    public int getHealth() {
        return health;
    }
    
    public void damageTaken(double i) {
        health -= i;
    }
    
    //codes that all give the hitbox regions to the draw method.
    public double getHitboxX() {return hitboxX;}
    public double getHitboxY(){return hitboxY;}
    public double getHitboxX2(){return hitboxX2;}
    public double getHitboxY2(){return hitboxY2;}
    public double getHitboxX3(){return hitboxX3;}
    public double getHitboxY3(){return hitboxY3;}
    public double getHitboxX4(){return hitboxX4;}
    public double getHitboxY4(){return hitboxY4;}
    public double getHitboxX5(){return hitboxX5;}
    public double getHitboxY5(){return hitboxY5;}
    
    public double getWeaponIndex() {
    	weaponIndex = -1;
    	if (weapon == "none") {
            weaponIndex = 0;
        } else if(weapon == "sword") {
            weaponIndex = 1;
        } else if(weapon == "spear") {
            weaponIndex = 2;
        } else if(weapon == "axe") {
            weaponIndex = 3;
        } else if(weapon == "gun") {
            weaponIndex = 4;
        }
    	return weaponIndex;
    }
    
    public void setPlayerWeaponThroughIndex(double i) //this sends the proper image for the sprite to the enemy's client. Depends on whether you're player 1 or player 2.
    {   if (playerID == 2)
        {
              if(i==1) {
                //setPlayerWeapon("sword");
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/rsword.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/rswordattack.png");
            } else if(i==2) {
                //setPlayerWeapon("spear");
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/rspear.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/rspearattack.png");
            } else if(i==3) {
                //setPlayerWeapon("axe");
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/raxe.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/raxeattack.png");
            } else if(i==4) {
                //setPlayerWeapon("gun");
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/rgun.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/rgunattack.png");
            }
        }
        else
        {
              if(i==1) {
                //setPlayerWeapon("sword");
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/bsword.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/bswordattack.png");
            } else if(i==2) {
                //setPlayerWeapon("spear");
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/bspear.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/bspearattack.png");
            } else if(i==3) {
                //setPlayerWeapon("axe");
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/baxe.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/baxeattack.png");
            } else if(i==4) {
                //setPlayerWeapon("gun");
                playerSprite = Toolkit.getDefaultToolkit().createImage("./assets/bgun.png");
                playerAttackSprite = Toolkit.getDefaultToolkit().createImage("./assets/bgunattack.png");
            }
        }
    }
    public double getPlayerShotIndex() {
    	if (playerShot) {
    		return 1;
    	} else {
    		return 0;
    	}
    }
    
    public void setPlayerShotThroughIndex(double i) {
    	if (i==1) {
    		playerShot = true;
    	} else {
    		playerShot = false;
    	}
    }
}
