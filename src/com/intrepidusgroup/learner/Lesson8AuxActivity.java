package com.intrepidusgroup.learner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.intrepidusgroup.learner.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class Lesson8AuxActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson8_aux);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		TextView lockedStatus = (TextView) this.findViewById(R.id.lesson8TextViewLocked);

		// Get the intent
		Intent intent = getIntent();
		String action = intent.getAction();
						
		if (action.equals(this.getResources().getString(R.string.lesson8IntentActionString)) && intent.getExtras().containsKey(getSecret())) {
			lockedStatus.setText(R.string.debugString);
			lockedStatus.setTextColor(Color.GREEN);
		}
		else {
			lockedStatus.setText(R.string.wrongIntentString);
			lockedStatus.setTextColor(Color.RED);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lesson8_aux, menu);
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
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private String getSecret() {
		return getDate();
	}
	
	private String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date currDate = new Date();
		// the logging entry is here to help the user in case they can't figure out the date format for the file
		Log.d("LEARNER", "Current date is " + dateFormat.format(currDate));
		return dateFormat.format(currDate);
		
	}


}
