package com.intrepidusgroup.learner;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.intrepidusgroup.learner.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class Lesson6Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson6);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// This code hides the bottom layer with all the lesson controls until the drawer is closed
		final SlidingDrawer mDrawer;
		final View mView = this.findViewById(R.id.Lesson6ControlLayout);
		mDrawer = (SlidingDrawer) this.findViewById(R.id.slidingDrawer6);
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

		// Create a view to populate the field with the new phone number
		TextView newPhoneNumberTextView = (TextView) this.findViewById(R.id.Lesson6NewNumberTextView);

		// Set the random number for the challenge
		String randomPhoneNumber = generateRandomPhoneNumber();
		newPhoneNumberTextView.setText(randomPhoneNumber);
		
		try {
			// Log out the own phone number's encrypted value
			Log.d("LEARNER", this.getResources().getString(R.string.Lesson6Hint2String) + encryptNumberWithAES(getPhoneNumber()));
			//Log.d("LEARNER", "Encrypted value. The number is: " + AESHelper.encryptNumberWithAES(randomPhoneNumber));
			//Log.d("LEARNER", "Sanity check if we can really decrypt our encrypted string. The number is: " + AESHelper.decryptNumberWithAES(encryptNumberWithAES(randomPhoneNumber)));
		}
		catch (Exception e) {
			// do nothing, move on
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lesson6, menu);
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
	
	private String generateRandomPhoneNumber() {
		int digit = 0;
		String number = "";
		for (int i = 0; i < 10; i++) {
			digit = (int) Math.floor(Math.random() * 10);
			number += Integer.toString(digit);
		}
		
		return number;
	}
	
	private String encryptNumberWithAES(String number) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// This method will return a base64 encoded value of the AES-encrypted string

		byte[] numberToEncryptBytes = number.getBytes();
		
		String keyString = "intrepidlearner1"; // The key is exactly 16 bytes long
		byte[] key = keyString.getBytes();
		
		
		Cipher c = Cipher.getInstance("AES");
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		c.init(Cipher.ENCRYPT_MODE, keySpec);
		
		byte[] encryptedData = c.doFinal(numberToEncryptBytes);
		// encode the encrypted array of bytes as a Base64 encoded string
		String encryptedNumber = Base64.encodeToString(encryptedData, Base64.DEFAULT);
		
		return encryptedNumber;
	}
	
		
	public void lesson6OnSubmitClick(View v) {
		
		// read in the challenge value in the box
		TextView keyedInValueBox = (TextView) findViewById(R.id.Lesson6EditText1);
		if (checkSecretToken(keyedInValueBox.getText().toString())) {
			// success
			displayToastMessage(true);
		}
		else {
			// failure
			displayToastMessage(false);
		}
	
	}
	
	@SuppressLint("DefaultLocale")
	private boolean checkSecretToken (String enteredToken) {
		// Get the phone number from the view
		TextView newPhoneNumberTextView = (TextView) this.findViewById(R.id.Lesson6NewNumberTextView);
		String phoneNumber = (String) newPhoneNumberTextView.getText();
		String correctEncryptedValue = null;
		try {
			correctEncryptedValue = encryptNumberWithAES(phoneNumber);
			//Log.d("LEARNER", "In checkSecretToken, token is " + correctEncryptedValue + " and phone number is " + phoneNumber);
		}
		catch (Exception e) {
			Log.d("LEARNER", "In checkSecretToken, couldn't encrypt the number" + e.getMessage());
		}
		// encrypt the token
		
		// This will check for at least 8 sequential characters of the token
		if (enteredToken.length() >= 8 && correctEncryptedValue.toLowerCase(Locale.US).contains(enteredToken.toLowerCase(Locale.US))) {
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
