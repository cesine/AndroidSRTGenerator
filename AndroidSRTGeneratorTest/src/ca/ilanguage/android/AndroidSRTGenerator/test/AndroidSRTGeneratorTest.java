package ca.ilanguage.android.AndroidSRTGenerator.test;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import ca.ilanguage.android.AndroidSRTGenerator.*;
/*
 * Test for SRTGenerator Activity 
 * Android built-in testing framework:
 * 	http://www.youtube.com/watch?v=7frRGAPrknE&feature=related
 * Pivotal labs testing framework was evaluated but not chosen
 * 	https://github.com/pivotal/RobolectricSample
 */
public class AndroidSRTGeneratorTest extends ActivityInstrumentationTestCase2<UtteranceFinderDemo>{

	Activity activity;
	ListView UtteranceListView;
	
	
	public AndroidSRTGeneratorTest() {
		super("ca.ilanguage.android.AndroidSRTGenerator",UtteranceFinderDemo.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public UtteranceFinderDemo getActivity() {
		// TODO Auto-generated method stub
		return super.getActivity();
	}

	@Override
	protected void runTest() throws Throwable {
		// TODO Auto-generated method stub
		super.runTest();
	}

	@Override
	public void setActivityIntent(Intent i) {
		// TODO Auto-generated method stub
		super.setActivityIntent(i);
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		activity = this.getActivity();
		
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

	
}
