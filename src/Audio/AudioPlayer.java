/* *
 * AudioPlayer.java
 *
 * Adds the ability to put music and Sound Effects into the game
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 * */

package Audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class AudioPlayer {
	
	// Audio Clip Class
	// Used as the variable for mp3 audio functions
	public Clip clip;
	
	public AudioPlayer(String s) {
		
		// This is how the Audio Files in References are called into the game
		try {
			// This is where the mp3 file path is input
			// 's' is variant depending on how the class is created

			//https://stackoverflow.com/questions/5529754/java-io-ioexception-mark-reset-not-supported
			//SOURCE^^ Need to buffer-in some audio files if you want them to work in a JAR file.
			InputStream audioSrc = getClass().getResourceAsStream(s);
			//add buffer for mark/reset support
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);

			
			// determines Audio Format
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels()*2, baseFormat.getSampleRate(), false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			
			// Pulls the clip into the Program and then Opens the File
			clip = AudioSystem.getClip();
			clip.open(dais);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// Call Function for any outside AudioPlayer class declarations
	// This is what starts the Audio
	public void play() {
		if(clip == null)return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	// Call Function that Stops the Audio Clip from running
	public void stop() { if(clip.isRunning()) {clip.stop();} }
	
	// Call Function the closes the Open File
	// Only Occurs when stop() is declared
	public void close() { stop(); clip.close(); }
	
	
	
}
