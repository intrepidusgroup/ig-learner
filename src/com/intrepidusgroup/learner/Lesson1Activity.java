package com.intrepidusgroup.learner;

import java.util.Random;

import com.intrepidusgroup.learner.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Toast;

public class Lesson1Activity extends Activity {

	private String challenge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson1);
			
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// This code hides the bottom layer with all the lesson controls until the drawer is closed
		final SlidingDrawer mDrawer;
		final View mView = this.findViewById(R.id.Lesson1ControlLayout);
		mDrawer = (SlidingDrawer) this.findViewById(R.id.slidingDrawer1);
		mDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener()
		{
			@Override
			public void onDrawerOpened() {
				mView.setVisibility(mView.GONE);
			}
		});
		mDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				mView.setVisibility(mView.VISIBLE);
			}
		});
		// End drawer control code
		
		
		// create the challenge string
		challenge = generateChallenge();
		
		// call the function to do the actual logging
		fillLogWithGarbage();
		
	}

	public void lesson1OnSubmitClick(View v) {
	
		// read in the challenge value in the box
		EditText keyedInValueBox = (EditText) findViewById(R.id.editText2);
		if (checkLoggedString(keyedInValueBox.getText().toString())) {
			// success
			displayToastMessage(true);
		}
		else {
			// failure
			displayToastMessage(false);
		}
	
	}
	
	private void displayToastMessage(boolean success) {
		// come back to this if invocations tend to produce view/activity weirdness	
		
		// Also - this really needs to be in its own class

		String yay = getResources().getString(R.string.popupChallengeSuccess);
		String nay = getResources().getString(R.string.popupChallengeFailure);
		if (success) {
			Toast.makeText(getApplicationContext(), yay, Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(getApplicationContext(), nay, Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private boolean checkLoggedString(String stringToTest) {
		if (stringToTest.equals(challenge)) { 
			return true;
		}
		else return false;		
	}
	
	private String generateChallenge() {
		// this method generates a 10-char long challenge
		int len = 10;
		java.security.SecureRandom random = new java.security.SecureRandom();
		
		// I want to be reasonable and not have the user switch between upper- and lowercase all day long.
		char alphabet[] =  "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();

        StringBuffer out = new StringBuffer(len);
        for (int i = 0; i < len; i++)
        {
            out.append(alphabet[random.nextInt(alphabet.length)]);
        }
        return out.toString();
		
	}
	
	private void fillLogWithGarbage() {
		// the purpose of this function is to create 99 bogus entries, and insert the actual challenge at a pseudo-random location 
		// with a slightly modified intro text to help differentiate the records
		final int maxRecords = 100;
		Random nonSecureRandomGenerator = new Random();
		int nonSecureRandomInt = nonSecureRandomGenerator.nextInt(maxRecords);
		for (int i = 0; i < maxRecords; i++) {
			if (i == nonSecureRandomInt) {
				// // insert the actual challenge at a random position
				Log.d("LEARNER", getResources().getString(R.string.challengeString) + challenge);
			}
			else {
				// generate a whole bunch of bogus entries
				Log.d("LEARNER", getResources().getString(R.string.garbageLogString) + generateChallenge());
			}
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lesson1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			
			// NavUtils.navigateUpFromSameTask(this);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}	
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.do_nothing_animation,R.anim.slide_right_out);
	}

	@Override
	public void finish() {
	    super.finish();
	    overridePendingTransition(R.anim.do_nothing_animation,R.anim.slide_right_out);
	}
	
}


