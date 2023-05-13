package Handle;
import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

public class Audio
{
    private Clip clip;
    private int currentFrame;
    public Audio(String fileName)
    {
        try {
            URL url=this.getClass().getResource(fileName);
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (LineUnavailableException ex) {
            Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void PlayFromBegin()
    {
        if(clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void Play()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void PlayOnce()
    {
        if(clip.isRunning()==false)
        {
            clip.setFramePosition(0);
            clip.start();
        }
        if(clip.getFramePosition()==clip.getFrameLength())
            clip.stop();
    }

    public void Resume()
    {
        clip.start();
        clip.setFramePosition(currentFrame);
    }
    public void Pause()
    {
        currentFrame=clip.getFramePosition();
        if(clip.isRunning())
            clip.stop();
    }
    public void SetLoopTime(int time)
    {
        clip.loop(time);
    }
    public boolean IsPlaying()
    {
        return clip.isRunning();
    }
}
