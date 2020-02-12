/**
 * This is the class that houses everything that has to do with moving and pressing the mouse. Things such as rotation of sprite, changing of animation of sprite, applying damage depending on weapons, all reside here.
 *
 * @author Castro, Butch Adrian A.
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
import java.awt.*;
import java.awt.event.*;

// Mouse class contains both MouseMotionListener and MouseListener in order to look for any action involving the mouse. 
public class Mouse implements MouseMotionListener, MouseListener
{
    private double mouseX,mouseY,rotationDeg,player1X,player1Y,player2X,player2Y,distanceY,distanceX,hitboxX,hitboxY,hitboxX2,hitboxY2,hitboxX3,hitboxY3,hitboxX4,hitboxY4,hitboxX5,hitboxY5;
    private GameCanvas gameCanvas;
    private GameFrame.ClientSideConnection csc;
    private String swordAttackSound,spearAttackSound,axeAttackSound,gunAttackSound,playerWeapon;


    public Mouse(GameCanvas gc, GameFrame.ClientSideConnection csc)
    {
        gameCanvas = gc;
        this.csc = csc;
        swordAttackSound = "./sfx/swordattack.wav"; //file locations of each of the weapons
        spearAttackSound = "./sfx/spearattack.wav";
        axeAttackSound = "./sfx/axeattack.wav";
        gunAttackSound = "./sfx/gunattack.wav";
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) //whenever the mouse is moved, this method gets the angle between the player and cursor and sends back to Player class for its uses. It is also here where these information are sent to the other client.
    {
    	if (gameCanvas.getPlayer().getHealth() > 0)
    	{
	    	if (gameCanvas.getPlayerID()==1) {
		        player1X = gameCanvas.getPlayer().getX() + 10;
		        player1Y = gameCanvas.getPlayer().getY() + 35;
		        mouseX = MouseInfo.getPointerInfo().getLocation().x;
		        mouseY = MouseInfo.getPointerInfo().getLocation().y;
		
		        distanceY = mouseY - player1Y;
		        distanceX = mouseX - player1X;
		
		        rotationDeg = Math.atan2(distanceY,distanceX); // this is the formula that allows to get the proper angle of rotation of the sprite, depending on its location and the cursor.
		        gameCanvas.getPlayer().setRotation(rotationDeg);
		        csc.sendAction(2,rotationDeg);
	    	} else {
	    		player2X = gameCanvas.getPlayer().getX() + 10;
	            player2Y = gameCanvas.getPlayer().getY() + 35;
	            mouseX = MouseInfo.getPointerInfo().getLocation().x;
	            mouseY = MouseInfo.getPointerInfo().getLocation().y;

	            distanceY = mouseY - player2Y;
	            distanceX = mouseX - player2X;

	            rotationDeg = Math.atan2(distanceY,distanceX);
	            gameCanvas.getPlayer().setRotation(rotationDeg);
	            csc.sendAction(2,rotationDeg);
	    	}	
	    }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) { //whenever mouse is pressed, there is an attempt to attack. This method makes hitboxes and checks if enemy takes damage or not, depending if they are in the hitbox or not.
    	if(gameCanvas.getPlayer().getHealth() > 0)
    	{
	    	if (gameCanvas.getPlayerID()==1) {
		        playerWeapon = gameCanvas.getPlayer().getPlayerWeapon();
		        player1X = gameCanvas.getPlayer().getX() + 10;
		        player1Y = gameCanvas.getPlayer().getY() + 35;
		        player2X = gameCanvas.getOpponent().getX();
		        player2Y = gameCanvas.getOpponent().getY();
		        mouseX = MouseInfo.getPointerInfo().getLocation().x;
		        mouseY = MouseInfo.getPointerInfo().getLocation().y;
		
		        distanceY = mouseY - player1Y;
		        distanceX = mouseX - player1X;
		
		        rotationDeg = Math.atan2(distanceY,distanceX);
		        gameCanvas.getPlayer().setRotation(rotationDeg);
		        Music gunSFX = new Music();

		        /**	
					The code below forms the hitbox, and sees when the player has pressed his mouse if the enemy is within the hitbox. If yes, then enemy takes damage, and is sent to the enemy's client as well.
				**/
		        if(playerWeapon == "sword") { //hitbox for SWORD
		            gunSFX.playWeaponSFX(swordAttackSound);
					hitboxX = gameCanvas.getPlayer().getHitboxX();
					hitboxY = gameCanvas.getPlayer().getHitboxY();
					hitboxX2 = gameCanvas.getPlayer().getHitboxX2();
					hitboxY2 = gameCanvas.getPlayer().getHitboxY2();
					if ((player2X + 16 > hitboxX - 16 && player2X - 16 < hitboxX + 16 && player2Y - 16 < hitboxY + 16 && player2Y + 16 > hitboxY - 16) || (player2X + 16 > hitboxX2 - 16 && player2X - 16 < hitboxX2 + 16 && player2Y - 16 < hitboxY2 + 16 && player2Y + 16 > hitboxY2 - 16)) {
						gameCanvas.getOpponent().damageTaken(33);
						csc.sendAction(5,33);
						System.out.println("Player 2 HIT");
					} else {
						System.out.println("NOT HIT");
					}
		        } else if (playerWeapon == "spear") { //hitbox for SPEAR
		            gunSFX.playWeaponSFX(spearAttackSound);
					hitboxX = gameCanvas.getPlayer().getHitboxX();
					hitboxY = gameCanvas.getPlayer().getHitboxY();
					hitboxX2 = gameCanvas.getPlayer().getHitboxX2();
					hitboxY2 = gameCanvas.getPlayer().getHitboxY2();
					hitboxX3 = gameCanvas.getPlayer().getHitboxX3();
					hitboxY3 = gameCanvas.getPlayer().getHitboxY3();

					if ((player2X + 16 > hitboxX - 16 && player2X - 16 < hitboxX + 16 && player2Y - 16 < hitboxY + 32 && player2Y + 16 > hitboxY - 32) || (player2X + 16 > hitboxX2 - 16 && player2X - 16 < hitboxX2 + 16 && player2Y - 16 < hitboxY2 + 32 && player2Y + 16 > hitboxY2 - 32) || (player2X + 16 > hitboxX3 - 16 && player2X - 16 < hitboxX3 + 16 && player2Y - 16 < hitboxY3 + 32 && player2Y + 16 > hitboxY3 - 32)) {
						gameCanvas.getOpponent().damageTaken(10);
						csc.sendAction(5,10);
						System.out.println("Player 2 HIT");
					}
		        } else if (playerWeapon == "axe") { //hitbox for AXE
		            gunSFX.playWeaponSFX(axeAttackSound);
					hitboxX = gameCanvas.getPlayer().getHitboxX();
					hitboxY = gameCanvas.getPlayer().getHitboxY();
					hitboxX2 = gameCanvas.getPlayer().getHitboxX2();
					hitboxY2 = gameCanvas.getPlayer().getHitboxY2();
					hitboxX3 = gameCanvas.getPlayer().getHitboxX3();
					hitboxY3 = gameCanvas.getPlayer().getHitboxY3();
					hitboxX4 = gameCanvas.getPlayer().getHitboxX4();
					hitboxY4 = gameCanvas.getPlayer().getHitboxY4();
					hitboxX5 = gameCanvas.getPlayer().getHitboxX5();
					hitboxY5 = gameCanvas.getPlayer().getHitboxY5();
					if ((player2X + 16 > hitboxX - 32 && player2X - 16 < hitboxX + 32 && player2Y - 16 < hitboxY + 16 && player2Y + 16 > hitboxY - 16) || (player2X + 16 > hitboxX2 - 32 && player2X - 16 < hitboxX2 + 32 && player2Y - 16 < hitboxY2 + 16 && player2Y + 16 > hitboxY2 - 16) || (player2X + 16 > hitboxX3 - 32 && player2X - 16 < hitboxX3 + 32 && player2Y - 16 < hitboxY3 + 16 && player2Y + 16 > hitboxY3 - 16) || (player2X + 16 > hitboxX4 - 32 && player2X - 16 < hitboxX4 + 32 && player2Y - 16 < hitboxY4 + 16 && player2Y + 16 > hitboxY4 - 16) || (player2X + 16 > hitboxX5 - 32 && player2X - 16 < hitboxX5 + 32 && player2Y - 16 < hitboxY5 + 16 && player2Y + 16 > hitboxY5 - 16)) {
						gameCanvas.getOpponent().damageTaken(10);
						csc.sendAction(5,10);
						System.out.println("Player 2 HIT");
					}
		        } else if (playerWeapon == "gun") { // hitbox for GUN
		            gunSFX.playWeaponSFX(gunAttackSound);
					hitboxX = gameCanvas.getPlayer().getHitboxX();
					hitboxY = gameCanvas.getPlayer().getHitboxY();
					if (player2X + 16 > hitboxX - 16 && player2X - 16 < hitboxX + 16 && player2Y - 16 < hitboxY + 16 && player2Y + 16 > hitboxY - 16) {
						gameCanvas.getOpponent().damageTaken(10);
						csc.sendAction(5,10);
						System.out.println("Player 2 HIT");
					}
		        }
		        gameCanvas.getPlayer().setPlayerShot(true); //this sets playerShot to true, which changes the sprite image to display an animation of attacking.
		        csc.sendAction(4,1); 
		        
	        } else {
				playerWeapon = gameCanvas.getPlayer().getPlayerWeapon();
				player1X = gameCanvas.getOpponent().getX();
				player1Y = gameCanvas.getOpponent().getY();
				player2X = gameCanvas.getPlayer().getX() + 10;
				player2Y = gameCanvas.getPlayer().getY() + 35;
				mouseX = MouseInfo.getPointerInfo().getLocation().x;
				mouseY = MouseInfo.getPointerInfo().getLocation().y;

				distanceY = mouseY - player2Y;
				distanceX = mouseX - player2X;

				rotationDeg = Math.atan2(distanceY,distanceX);
				gameCanvas.getPlayer().setRotation(rotationDeg);
		        Music gunSFX = new Music();

		        /**	
					The code below forms the hitbox, and sees when the player has pressed his mouse if the enemy is within the hitbox. If yes, then enemy takes damage, and is sent to the enemy's client as well.
					But this time for player 2.
				**/

		        if(playerWeapon == "sword") { //hitbox for SWORD
		            gunSFX.playWeaponSFX(swordAttackSound);
					hitboxX = gameCanvas.getPlayer().getHitboxX();
					hitboxY = gameCanvas.getPlayer().getHitboxY();
					hitboxX2 = gameCanvas.getPlayer().getHitboxX2();
					hitboxY2 = gameCanvas.getPlayer().getHitboxY2();
					if ((player1X + 16 > hitboxX - 16 && player1X - 16 < hitboxX + 16 && player1Y - 16 < hitboxY + 16 && player1Y + 16 > hitboxY - 16) || (player1X + 16 > hitboxX2 - 16 && player1X - 16 < hitboxX2 + 16 && player1Y - 16 < hitboxY2 + 16 && player1Y + 16 > hitboxY2 - 16)) {
						gameCanvas.getOpponent().damageTaken(33);
						csc.sendAction(5,33);
						System.out.println("Player 1 HIT");
					} else {
						System.out.println("NOT HIT");
					}
		        } else if (playerWeapon == "spear") { //hitbox for SPEAR
		            gunSFX.playWeaponSFX(spearAttackSound);
					hitboxX = gameCanvas.getPlayer().getHitboxX();
					hitboxY = gameCanvas.getPlayer().getHitboxY();
					hitboxX2 = gameCanvas.getPlayer().getHitboxX2();
					hitboxY2 = gameCanvas.getPlayer().getHitboxY2();
					hitboxX3 = gameCanvas.getPlayer().getHitboxX3();
					hitboxY3 = gameCanvas.getPlayer().getHitboxY3();

					if ((player1X + 16 > hitboxX - 16 && player1X - 16 < hitboxX + 16 && player1Y - 16 < hitboxY + 32 && player1Y + 16 > hitboxY - 32) || (player1X + 16 > hitboxX2 - 16 && player1X - 16 < hitboxX2 + 16 && player1Y - 16 < hitboxY2 + 32 && player1Y + 16 > hitboxY2 - 32) || (player1X + 16 > hitboxX3 - 16 && player1X - 16 < hitboxX3 + 16 && player1Y - 16 < hitboxY3 + 32 && player1Y + 16 > hitboxY3 - 32)) {
						gameCanvas.getOpponent().damageTaken(10);
						csc.sendAction(5,10);
						System.out.println("Player 1 HIT");
					}
		        } else if (playerWeapon == "axe") { //hitbox for AXE
		            gunSFX.playWeaponSFX(axeAttackSound);
					hitboxX = gameCanvas.getPlayer().getHitboxX();
					hitboxY = gameCanvas.getPlayer().getHitboxY();
					hitboxX2 = gameCanvas.getPlayer().getHitboxX2();
					hitboxY2 = gameCanvas.getPlayer().getHitboxY2();
					hitboxX3 = gameCanvas.getPlayer().getHitboxX3();
					hitboxY3 = gameCanvas.getPlayer().getHitboxY3();
					hitboxX4 = gameCanvas.getPlayer().getHitboxX4();
					hitboxY4 = gameCanvas.getPlayer().getHitboxY4();
					hitboxX5 = gameCanvas.getPlayer().getHitboxX5();
					hitboxY5 = gameCanvas.getPlayer().getHitboxY5();
					if ((player1X + 16 > hitboxX - 32 && player1X - 16 < hitboxX + 32 && player1Y - 16 < hitboxY + 16 && player1Y + 16 > hitboxY - 16) || (player1X + 16 > hitboxX2 - 32 && player1X - 16 < hitboxX2 + 32 && player1Y - 16 < hitboxY2 + 16 && player1Y + 16 > hitboxY2 - 16) || (player1X + 16 > hitboxX3 - 32 && player1X - 16 < hitboxX3 + 32 && player1Y - 16 < hitboxY3 + 16 && player1Y + 16 > hitboxY3 - 16) || (player1X + 16 > hitboxX4 - 32 && player1X - 16 < hitboxX4 + 32 && player1Y - 16 < hitboxY4 + 16 && player1Y + 16 > hitboxY4 - 16) || (player1X + 16 > hitboxX5 - 32 && player1X - 16 < hitboxX5 + 32 && player1Y - 16 < hitboxY5 + 16 && player1Y + 16 > hitboxY5 - 16)) {
						gameCanvas.getOpponent().damageTaken(10);
						csc.sendAction(5,10);
						System.out.println("Player 1 HIT");
					}
		        } else if (playerWeapon == "gun") { //hitbox for GUN
		            gunSFX.playWeaponSFX(gunAttackSound);
					hitboxX = gameCanvas.getPlayer().getHitboxX();
					hitboxY = gameCanvas.getPlayer().getHitboxY();
					if (player1X + 16 > hitboxX - 16 && player1X - 16 < hitboxX + 16 && player1Y - 16 < hitboxY + 16 && player1Y + 16 > hitboxY - 16) {
						gameCanvas.getOpponent().damageTaken(10);
						csc.sendAction(5,10);
						System.out.println("Player 1 HIT");
					}
		        }
		        gameCanvas.getPlayer().setPlayerShot(true);
		        csc.sendAction(4,1);
	        }
	    }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    	gameCanvas.getPlayer().setPlayerShot(false); // when mouse is released, the sprite image file is reverted back to non-attack form.
		csc.sendAction(4,0);
    	}
    }

