/**
 * This is the class that contains the code for running sound effects and the background music. 
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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Music
{

    public void playWeaponSFX(String fileLocation) //playWeaponSFX only plays once, and has a lower volume that the BGM.
    {
        //try and catch so that it'll work even though there's no music file
        try
        {
            File musicPath = new File(fileLocation);

            if(musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath); //makes a new AudioInputStream
                //clip which houses the BGM
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);

                //adjusts the volume of the bgm a little bit
                FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(-15.0f);

                //this starts the clip
                clip.start();

            }
            else
            {
                System.out.println("File not found.");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void playMusic(String fileLocation) //playMusic plays on a loop, and is louder than the SFX of the game. 
    {
        //try and catch so that it'll work even though there's no music file
        try
        {
            File musicPath = new File(fileLocation);

            if(musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath); //makes a new AudioInputStream
                //clip which houses the BGM
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);

                //adjusts the volume of the bgm a little bit
                FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(-0.0f);

                //this starts the clip
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            }
            else
            {
                System.out.println("File not found.");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}