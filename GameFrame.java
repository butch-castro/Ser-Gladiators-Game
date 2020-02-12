/**
 * This is the class that contains the JFrame. This is also where the the canvas, the transferring of information between two clients are found and initialized.
 * @author Castro, Butch Adrian A. & Go, Gerick Jeremiah Ni�o N.
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class GameFrame extends JFrame
{
    private int width,height,playerID,otherPlayer,actionID;
    private double action;
    private String title, ipAddress;
    private Image icon;
    private ClientSideConnection csc;
    private GameCanvas gameCanvas;
    private Container contentPane;
    private Mouse mouse;
    private boolean gameStart;
    
    /**
     * This is the constructor for the GameFrame class.
     * @param w - width of the frame
     * @param h - height of the frame
     * @param t - title of the frame
     */
    public GameFrame(int w, int h, String t, String ip)
    {
        width = w;
        height = h;
        title = getTitle();
        title = t;
        icon = Toolkit.getDefaultToolkit().getImage("./assets/icon.png");
        gameStart = false;
        ipAddress = ip;
    }
    
    /**
     * Method that connects the game to the Game Server
     */
    public void connectToServer() {
		csc = new ClientSideConnection();
	}
    
    public void startInfoUpdates() {
    	Thread t1 = new Thread(new Runnable() {
			public void run() {
				while (true) {
					csc.receiveAction();
			    	if (actionID==0) {
                        if (action == 1)
                        {
			    		   gameCanvas.getOpponent().movingYneg(true); // moving up
                        }
                        else
                        {
                            gameCanvas.getOpponent().movingYneg(false);
                        }
			    	} else if (actionID==1) {
			    		if (action == 1)
                        {
                           gameCanvas.getOpponent().movingYpos(true); // moving down
                        }
                        else
                        {
                            gameCanvas.getOpponent().movingYpos(false);
                        }
			    	} else if (actionID==2) {
			    		gameCanvas.getOpponent().setRotation(action);
			    	} else if (actionID==3) {
			    		gameCanvas.getOpponent().setPlayerWeaponThroughIndex(action);
			    	} else if (actionID==4) {
			    		gameCanvas.getOpponent().setPlayerShotThroughIndex(action);
			    	} else if (actionID==5) {
			    		gameCanvas.getPlayer().damageTaken(action);
			    	} else if (actionID==6) {
                        if (action == 1)
                        {
                           gameCanvas.getOpponent().movingXneg(true); // moving left
                        }
                        else
                        {
                            gameCanvas.getOpponent().movingXneg(false);
                        }
                    } else if (actionID==7) {
                        if (action == 1)
                        {
                           gameCanvas.getOpponent().movingXpos(true); // moving right
                        }
                        else
                        {
                            gameCanvas.getOpponent().movingXpos(false);
                        }
                    } else if (actionID==8) {
                        gameCanvas.getOpponent().setX(action);
                    } else if (actionID==9) {
                        gameCanvas.getOpponent().setY(action);
                    } else if (actionID==10) {
                        gameCanvas.getOpponent().setResetDecision(true);
                    }
			    	gameCanvas.repaint();
				}
			}
		});
		t1.start();
    }
    
    /**
     * Method that sets the title for the frame with the corresponding Player ID.
     */
    public String getTitle() {
    	String t = new String("Ser Gladiators: A Battle Arena Fighting Game - Player #"+playerID);
    	return t;
    }
    
    /**
     * Sets up the frame of the class.
     */
    public void setUpFrame()
    {
        contentPane = getContentPane();
        gameCanvas = new GameCanvas(width, height, new Color(178, 150, 37), contentPane, playerID, otherPlayer, csc);
        mouse = new Mouse(gameCanvas, csc);
        
        if (playerID==1) {
        	contentPane.addKeyListener(gameCanvas.getPlayer());
        	otherPlayer = 2;
        } else {
        	contentPane.addKeyListener(gameCanvas.getPlayer());
        	otherPlayer = 1;
        }
        
        contentPane.add(gameCanvas, BorderLayout.CENTER);
        contentPane.addMouseListener(mouse);
        contentPane.addMouseMotionListener(mouse);
        contentPane.setFocusable(true);
        contentPane.setFocusTraversalKeysEnabled(false);
        
        setTitle(title);
        setIconImage(icon);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);
        
        if (playerID==1) {
			csc.startGame();
			System.out.println("Start the Game!");
		}
    }
    
    /**
     * This is the ClientSideConnection inner class.
     * @author Go, Gerick Jeremiah Ni�o N.
     *
     */
    public class ClientSideConnection {
    	private Socket socket;
    	private DataInputStream dataIn;
    	private DataOutputStream dataOut;
    	
    	/**
    	 * Constructor for the ClientSideConnection. Finds the host and assigns the Player ID.
    	 */
    	public ClientSideConnection() {
    		System.out.println("--- Client ---");
    		try {
    			socket = new Socket(ipAddress, 18222); //input IP address here
    			dataIn = new DataInputStream(socket.getInputStream());
    			dataOut = new DataOutputStream(socket.getOutputStream());
    			playerID = dataIn.readInt();
    			
    			if (playerID==2 && !gameStart) {
    				gameStart = true;
    				System.out.println("Connected to Server.");
    				System.out.println("Start the Game!");
    			}
    		} catch (IOException ex){
    			System.out.println("IOException from CSC constructor.");
    		}
    	}
    	
    	/**
    	 * Method that receives a boolean value from the server to allow it to send data to the server.
    	 */
		public void startGame() {
			try {
				gameStart = dataIn.readBoolean();
				System.out.println("Connected to Server.");
			} catch (IOException ex) {
				System.out.println("IOException from startGame() CSC.");
			}
		}
    	
		/**
		 * Method that sends what action needs to be done and the value of the action that needs to be changed
		 * @param actionID
		 * @param action
		 */
    	public void sendAction(int actionID, double action) {
    		if (gameStart) {
    			try {
        			dataOut.writeInt(actionID);
        			dataOut.writeDouble(action);
        			dataOut.flush();
        		} catch (IOException ex) {
        			System.out.println("IOException from sendMovement() CSC.");
        		}
    		}
    	}
    	
    	/**
    	 * Method that receives the information of the opponent send by the server. Returns an double array.
    	 */
    	public void receiveAction() {
    		try {
    			actionID = dataIn.readInt();
    			action = dataIn.readDouble();
    		} catch (IOException ex) {
    			System.out.println("IOException from receiveMovement() CSC.");
    		}
    	}
    }
}