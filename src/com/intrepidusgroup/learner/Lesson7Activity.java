package com.intrepidusgroup.learner;

import com.intrepidusgroup.learner.R;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class Lesson7Activity extends Activity {
	private Uri usersUri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson7);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		String username = "johndoe";
		String password = "you found the password:" + generateChallenge();
		String description = "John Doe's description";
			
		ContentValues values = new ContentValues();
		values.put(Lesson7DatabaseTable.COLUMN_USER, username);
		values.put(Lesson7DatabaseTable.COLUMN_PASSWORD, password);
		values.put(Lesson7DatabaseTable.COLUMN_DESCRIPTION, description);
		
	    if (usersUri == null) {
	        // Remove all records to keep things clean
	    	getContentResolver().delete(Lesson7ContentProvider.CONTENT_URI, Lesson7DatabaseTable.COLUMN_USER + " IS NOT NULL", null);
	    	// Create a single row
	    	usersUri = getContentResolver().insert(Lesson7ContentProvider.CONTENT_URI, values);
	        
	    } else {
	        // Update todo
	        getContentResolver().update(usersUri, values, null, null);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lesson7, menu);
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

	private String generateChallenge() {
		// this method generates a 10-char long challenge
		int len = 10;
		java.security.SecureRandom random = new java.security.SecureRandom();
		
		char alphabet[] =  "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();

        StringBuffer out = new StringBuffer(len);
        for (int i = 0; i < len; i++)
        {
            out.append(alphabet[random.nextInt(alphabet.length)]);
        }
        return out.toString();
		
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
