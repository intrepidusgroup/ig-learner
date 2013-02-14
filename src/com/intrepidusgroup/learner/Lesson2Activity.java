package com.intrepidusgroup.learner;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.intrepidusgroup.learner.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class Lesson2Activity extends Activity {
	
	private String filename;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson2);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// This code hides the bottom layer with all the lesson controls until the drawer is closed
		final SlidingDrawer mDrawer;
		final View mView = this.findViewById(R.id.Lesson2ControlLayout);
		mDrawer = (SlidingDrawer) this.findViewById(R.id.slidingDrawer2);
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
		
		
		try {
			createFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("LEARNER", "Problem in create file:" + e.getMessage());
		}
		// create the submit button
		final Button submitButton = (Button) findViewById(R.id.checkFile);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lesson2, menu);
		Log.d("LEARNER", "I'm in activity 2");
		
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
	
	private void createFile() throws Exception {
		// this method creates the file in the app's directory based on the algorithm defined in getFileName() method
		
		filename = getFileName();
		//String filepath = "";
		//File file = getBaseContext().getFileStreamPath(filename);
		
		// for now we'll keep writing to the same file
	    try {
	    	FileOutputStream fOut = openFileOutput(filename, MODE_WORLD_WRITEABLE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(this.getResources().getString(R.string.testString) + "\n");
			osw.flush();
			osw.close();
			fOut.flush();
			fOut.close();
			
		} catch (Exception e) {
			Log.d("LEARNER", e.getMessage());
		} 		
	}
	
	private String getFileName() {
		return getDate() + getPhoneNumber() + "." + "t" + "x" + "t";
	}
	
	private String getPhoneNumber() {
		// Return default number in case the device does not support telephony
		String defaultNumber = "1234567890";
		String number = "";
		try {
			TelephonyManager tMgr =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
			number = tMgr.getLine1Number();
			if (number.isEmpty()) {
				// Return default 
				number = defaultNumber;
			}
			Log.d("IG_LEARNER", "The phone number is " + number);
			return number;
		} catch (Exception e) {
			// Return default number
			return defaultNumber;
		}
	}
	
	private String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date currDate = new Date();
		// the logging entry is here to help the user in case they can't figure out the date format for the file
		Log.d("LEARNER", "Current date is " + dateFormat.format(currDate));
		return dateFormat.format(currDate);
		
	}
	
	public void lesson2OnSubmitClick(View v) {
		
		// read in the challenge value in the box
		EditText keyedInValueBox = (EditText) findViewById(R.id.lesson2FileNameEditText);
		if (checkFileString(keyedInValueBox.getText().toString())) {
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
	
	private boolean checkFileString (String stringToCheck) {
		if (stringToCheck.equals(filename)) {
			return true;
		}
		return false;
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
