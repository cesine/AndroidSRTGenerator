package ca.ilanguage.android.AndroidSRTGenerator;

import com.openlanguage.android.AndroidSRTGenerator.R;

import android.app.Activity;
import android.media.AudioTrack;
import android.os.Bundle;
import android.widget.Toast;
/**
 * Input Bundle: String of audio file path 
 * Output Bundle: String of srt formated time codes corresponding to silences
 * 
 * Optional Input: 
 * 		Type of split prefered expecting SPLIT_ON_SILENCE or SPLIT_ON_ANY_PROSODIC_CUE, 
 * 		but more fine grained choices are availible through the constants listed below
 * 
 * See also: http://www.zdnet.com/blog/burnette/google-io-mastering-the-android-media-framework/1133
 * 
 * @author gina
 *
 */
public class SRTGeneratorActivity extends Activity {
	private AudioTrack mAudioTrack;
	private String mAudioFilePath;
	private int mSplitType;
	private String[] mTimeCodes;
	
	public static final String EXTRA_AUDIOFILE_FULL_PATH = "audioFilePath";
	public static final String EXTRA_SPLIT_TYPE = "splitOn";
	
	/**
	 * Splitting on Silence is relatively quick it only requires mathematic calculation on the audio sample, 
	 * this is used by default by all other split types.
	 */
	public static final int SPLIT_ON_SILENCE = 1;
	/**
	 * Subtitles should not exceed a certain length to fit on the screen, use this if you actually have the goal of generating subtitles
	 */
	public static final int SPLIT_ON_TOO_MANY_CHARS = 9;
	/**
	 * Warning: using prosodic cues requires more audio analysis (fourier transforms FFT) and so they will slow the service down substantially
	 */
	public static final int SPLIT_ON_ANY_PROSODIC_CUE = 2;

	/*
	 * List of Optional Prosodic cues
	 * 
	 * For more info: 
	 * 		http://en.wikipedia.org/wiki/Prosody_(linguistics)
	 * For more bleeding-edge info: 
	 * 		Experimental and Theoretical Advances in Prosody Conference http://prosodylab.org/etap/
	 */
	public static final int SPLIT_ON_LENGTHENED_VOWEL = 3;
	public static final int SPLIT_ON_LENGHTENED_CONSONANT = 4;
	public static final int SPLIT_ON_LENGTHENED_ANYTHING = 5;
	public static final int SPLIT_ON_GLOTTALIZATION = 6;
	public static final int SPLIT_ON_BREATH = 7;
	/**
	 * For those speakers of "valley-girl" English prosodic phrases can be 
	 * discovered using upspeak also known as: {high rising terminal (HRT), uptalk, upspeak, rising inflection or high rising intonation }
	 * 
	 * For more info: http://en.wikipedia.org/wiki/High_rising_terminal
	 */
	public static final int SPLIT_ON_UPSPEAK = 8;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*
         * TODO get data from bundle, store it in the member variables
         */
        
        
        String message = generateSRT();
        Toast.makeText(SRTGeneratorActivity.this,
				message,
				Toast.LENGTH_LONG).show();
    }
    public String generateSRT(){
    	String messageToReturn = "";
    	/*
    	 * TODO 
    	 * make sure the audioTrack is open
    	 */

    	/* 
    	 * TODO 
    	 * implement switch on mSplitType
    	 */
    	messageToReturn = splitOnSilence();
    	
    	return messageToReturn;
    }
    public String splitOnSilence(){
    	mTimeCodes = new String[4];
    	mTimeCodes[0] = "0:00:02.350,0:00:06.690";
    	mTimeCodes[1] = "0:00:07.980,0:00:12.780";
    	mTimeCodes[2] = "0:00:14.529,0:00:17.970";
    	mTimeCodes[3] = "0:00:17.970,0:00:20.599";
    	return "right now, these are fake timecodes";
    }
    public String testSplitOnSilence(){
    	/*
    	 * TODO
    	 * open raw/res/...wav sample
    	 * set the audioTrack to that file
    	 * run splitOnSilence on it
    	 * compare the output against the raw/res...corrected.sbv time stamps
    	 * return test failed/passed
    	 */
    	return "fail";
    }
}