/**
 * This is the class that starts the application. It gets an instance of GameFrame and runs it. It also has the Music class in it and runs it.
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
import java.util.Scanner; 

public class GameStarter
{
    public static void main(String[] args)
    {
        System.out.println("Please enter host IP Address. If you are hosting, type in localhost");
        Scanner scan = new Scanner(System.in);
        String ip = scan.next();
        GameFrame gameFrame = new GameFrame(1280,720,"Ser Gladiators: A Battle Arena Fighting Game",ip);
        gameFrame.connectToServer();
        gameFrame.setUpFrame();
        gameFrame.startInfoUpdates();
        
        Music gunSFX = new Music();
        gunSFX.playMusic("./sfx/bgm.wav"); //this plays the BGM for the whole game.
    }
}