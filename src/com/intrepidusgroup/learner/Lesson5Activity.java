package com.intrepidusgroup.learner;

import com.intrepidusgroup.learner.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.support.v4.app.NavUtils;

public class Lesson5Activity extends Activity {
	private String token;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson5);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// This code hides the bottom layer with all the lesson controls until the drawer is closed
		final SlidingDrawer mDrawer;
		final View mView = this.findViewById(R.id.Lesson5ControlLayout);
		mDrawer = (SlidingDrawer) this.findViewById(R.id.slidingDrawer5);
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
		
		
		// generate a secret token
		token = generateChallenge();
		// Invoke an asynchronous HTTP request - need to make sure the app doesn't lock up if there's no connection
		String [] httpRequestParams = {this.getResources().getString(R.string.sslTestURL2), token};
		// This time, the SSL uses certificate pinning
		new HttpRequestTaskWithPinning().execute(httpRequestParams);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lesson5, menu);
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
		
		// I want to be reasonable and not have the user switch between upper- and lowercase all day long.
		char alphabet[] =  "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();

        StringBuffer out = new StringBuffer(len);
        for (int i = 0; i < len; i++)
        {
            out.append(alphabet[random.nextInt(alphabet.length)]);
        }
        return out.toString();
		
	}
	
	public void lesson5OnResendClick(View v) {
		// Invoke an asynchronous HTTP request - need to make sure the app doesn't lock up if there's no connection
		String [] httpRequestParams = {this.getResources().getString(R.string.sslTestURL), token};
		new HttpRequestTaskWithPinning().execute(httpRequestParams);
	}
	
	public void lesson5OnSubmitClick(View v) {
		
		// read in the challenge value in the box
		EditText keyedInValueBox = (EditText) findViewById(R.id.lesson5SecretTokenEditText);
		if (checkSecretToken(keyedInValueBox.getText().toString())) {
			// success
			displayToastMessage(true);
		}
		else {
			// failure
			displayToastMessage(false);
		}
	
	}
	
	private boolean checkSecretToken (String enteredToken) {
		if (enteredToken.equals(token)) {
			return true;
		}
		return false;
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
