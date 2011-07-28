package ca.ilanguage.android.AndroidSRTGenerator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import ca.ilanguage.android.AndroidSRTGenerator.R;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
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
 * 			 http://developer.android.com/reference/android/media/AudioTrack.html
 * 
 * @author gina
 *
 */
public class SRTGeneratorActivity extends Activity {
	private AudioTrack mAudioTrack;
	private String mAudioFilePath;
	private int mSplitType;
	private ArrayList<String> mTimeCodes;
	String file_path;
	
	public static final String EXTRA_AUDIOFILE_FULL_PATH = "/res/raw/sample_recorded_in_praat_using_laptop_mic_outdoors_wav.wav";
	public static final String EXTRA_RESULTS = "splitUpResults";
	public static final String EXTRA_SPLIT_TYPE = "splitOn";
	
	//CodRev-SK:these should be refactored into enums
	//http://download.oracle.com/javase/1,5.0/docs/guide/language/enums.html
	
	/**
	 * Splitting on Silence is relatively quick it only requires a mathematical calculation on the audio sample, 
	 * this is used by default by all other split types.
	 */
	public static final int SPLIT_ON_SILENCE = 1;
	/**
	 * Subtitles should not exceed a certain length to fit on the screen, use this if you want to generate subtitles
	 */
	public static final int SPLIT_ON_TOO_MANY_CHARS = 9;
	/**
	 * Warning: using prosodic cues requires more audio analysis (Fourier transforms FFT and such) and so they will slow the service down substantially
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
        try {
            mAudioFilePath = getIntent().getExtras().getString(EXTRA_AUDIOFILE_FULL_PATH);
            if (mAudioFilePath.length() > 0) {
                this.setTitle(mAudioFilePath);
            }else{
            	this.setTitle("default");
            }
        } catch (Exception e) {
        	Toast.makeText(SRTGeneratorActivity.this, "Error "+e,Toast.LENGTH_LONG).show();
        }
            
        /*
         * Generate the SRT
         * http://www.matroska.org/technical/specs/subtitles/srt.html
         */
        String message = generateSRT();
        Toast.makeText(SRTGeneratorActivity.this, message,Toast.LENGTH_LONG).show();
        
        /*
         * Return results
         */
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_RESULTS, mTimeCodes);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
    public String generateSRT(){
    	String messageToReturn = "";
    	/*
    	 * TODO 
    	 * make sure the audioTrack is open
    	 */

    	file_path = "/res/raw/sample_recorded_in_praat_using_laptop_mic_outdoors_wav.wav";
    	
    	/* 
    	 * TODO 
    	 * implement switch on mSplitType
    	 */

		messageToReturn = splitOnSilence(file_path);
   	
    	return messageToReturn;
    }
    public String splitOnSilence(String file_path){
    	
    	mTimeCodes = new ArrayList<String>();
    	
    	//open raw/res/wavsample
    	byte[] byteArray = null;
    	
    	FileInputStream file = null;
   	
    	try {
			file = new FileInputStream(file_path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		short hibyte;
		short lobyte;
		int volume;
		int powersum = 0;
		int poweravg = 0;
		int offset = 0;
		int timestamp;
				
		try {
		/* read 400ms */
		while(file.read(byteArray, offset, 35280)>0)
		{
			/* keep track of where we are in the file/byteArray */
			offset += 35280;
			
			/* calculate time stamp based on where we are in the file
			(assuming 16bits per sample and 44100Khz sample rate) */
			timestamp = 1-(offset/byteArray.length);
			
			for(int i = 0; i < 35200; i++)
			{
				lobyte = byteArray[i];
				hibyte = byteArray[i+1];
				volume = (lobyte << 8) | hibyte;
				powersum += (int) Math.log10(volume);
			}
			
			poweravg = powersum / 35280;
			
			if(poweravg < 25)
			{
				Log.w("splitonsilence", "power is less than 25db");
				Log.w("splitonsilence", Integer.toString(timestamp));
				//save timestamp and avgpower for the 400ms sample
				mTimeCodes.add(Integer.toString(timestamp));
			}
				 
		}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "message done";
		
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
    
    	splitOnSilence("/res/raw/sample_recorded_in_praat_using_laptop_mic_outdoors_wav.wav");
    	
    	
    	return "fail";
    }
}