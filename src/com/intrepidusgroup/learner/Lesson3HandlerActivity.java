package com.intrepidusgroup.learner;

import com.intrepidusgroup.learner.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Lesson3HandlerActivity extends Activity {
	// This class is the URI handler for iglearner:// links.
	final String unlockkey = "crazyurihandler";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson3_handler);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Handle an incoming iglearner:// URI
		Intent uriReqIntent = getIntent();
		String uri = uriReqIntent.getScheme();
		String path = uriReqIntent.getData().getPath();
		Log.d("LEARNER", "Received request for URI " + uri + " at path: " + path);
		
		TextView lockedStatus = (TextView) this.findViewById(R.id.textViewLocked);
		// if the URI contains the key, challenge is passed
		if (path.contains(unlockkey)) {
			lockedStatus.setText(R.string.unlockedString);
			lockedStatus.setTextColor(Color.GREEN);
		}
		else {
			lockedStatus.setTextColor(Color.RED);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lesson3_handler, menu);
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

}
