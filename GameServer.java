/**
 * This is the class that starts the server client. It seeks for clients to connect.
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
import java.net.*;
import java.io.*;

public class GameServer {
	private ServerSocket ss;
	private int numPlayers, actionID;
	private double action;
	private ServerSideConnection player1, player2;
	private boolean gameStart;
	
	public GameServer() {
		System.out.println("---- Game Server----");
		numPlayers = 0;
		gameStart = true;
		try {
			ss = new ServerSocket(18222);
		} catch (IOException ex){
			System.out.println("IOException from GameServer Constructor");
		}
	}
	public void acceptConnections() {
		try {
			System.out.println("Waiting for connections...");
			while (numPlayers<2) {
				Socket s = ss.accept();
				numPlayers++;
				ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
				System.out.println("Connected: Player # " + numPlayers);

                if (numPlayers == 1) {
                    player1 = ssc;
                } else if (numPlayers == 2) {
                    player2 = ssc;
                    player1.startGame();
                }
                Thread t = new Thread(ssc);
                t.start();
			} 
			} catch (IOException ex) {
				System.out.println("IOException at acceptConnections()");
		}
	}
	
	private class ServerSideConnection implements Runnable {
		private Socket socket;
		private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private int playerID;
        
        public ServerSideConnection(Socket s, int id) {
        	socket = s;
        	playerID = id;
        	try {
        		dataIn = new DataInputStream(socket.getInputStream());
        		dataOut = new DataOutputStream(socket.getOutputStream());
        	} catch (IOException ex) {
                System.out.println("IOException from run() SSC constructor");
            }
        }
        
        public void run() {
    		try {
    			dataOut.writeInt(playerID);
    			dataOut.flush();
    			
    			while (gameStart) {
    				actionID = dataIn.readInt();
					
					action = dataIn.readDouble();
    				
    				if (playerID==1) {
    					player2.sendAction(actionID,action);
    				} else {
						player1.sendAction(actionID,action);
    				}
    			}
    		} catch (IOException ex) {
                System.out.println("IOException from run() SSC");
            }
    	}
        
		public void startGame() {
			try {
				dataOut.writeBoolean(true);
				dataOut.flush();
			} catch (IOException ex) {
				System.out.println("IOException from startGame() SSC.");
			}
		}
        
        public void sendAction(int actionID, double action) {
        	try {
        		dataOut.writeInt(actionID);
            	dataOut.writeDouble(action);
            	dataOut.flush();
        	} catch (IOException ex) {
				System.out.println("IOException from sendOpponentPosition() SSC.");
			}	
        }
	}
	
	public static void main(String[] args) 
	{
        GameServer s = new GameServer();
        s.acceptConnections();
    }
}

