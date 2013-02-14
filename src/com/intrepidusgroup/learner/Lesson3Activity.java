package com.intrepidusgroup.learner;

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
import android.webkit.WebView;

public class Lesson3Activity extends Activity {
// This class creates a URI embedded in a WebView that is later handled by a registered URI handler. 
// The user's goal is to find a URI such that the URI handler will unlock hidden functionality.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson3);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// This code hides the bottom layer with all the lesson controls until the drawer is closed
		final SlidingDrawer mDrawer;
		final View mView = this.findViewById(R.id.Lesson3ControlLayout);
		mDrawer = (SlidingDrawer) this.findViewById(R.id.slidingDrawer3);
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
		
		// create the submit button
		final Button submitButton = (Button) findViewById(R.id.lesson3SubmitButton);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lesson3, menu);
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
	
	private boolean requestURI(String uri) {
		return true;
	}
	
	public void lesson3OnSubmitClick(View v) {
		
		// get the URI box to read info from
		EditText keyedInValueBox = (EditText) findViewById(R.id.uriToTest);
		
		// create a WebView to dump the resulting URI into
		WebView webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		
		// get the URI from the input window
		String strURI = keyedInValueBox.getText().toString();
		
		
		// send the current value of the URI into the constructor of the JSInterface
		JsInterface jsInterface = new JsInterface(strURI);
		
		// add the JSInterface to the webview
		webview.addJavascriptInterface(jsInterface, "android"); // this will be referenced as "android" in JS code
		
		try {
			 //load file - the file will query the getURI method to create a dynamic link through Document.Write
	        webview.loadUrl("file:///android_asset/test.html");
		} catch (Exception e) {
			Log.d("LEARNER", "Problem loading URL: " + e.getMessage());
		}
//		if (requestURI(keyedInValueBox.getText().toString())) {
//			// success
//			displayToastMessage(true);
//		}
//		else {
//			// failure
//			displayToastMessage(false);
//		}
	
	}
	
	
    //javascript interface
    private class JsInterface {
    	// the constructor for the class takes the URI to form the link on the page with
    	private String resource;
    	JsInterface(String uri) {
    		resource = uri;
    	}
    	//function that will be called from assets/test.js
    	//js example: android.log('my message');
    	public String getURI() {
    		Log.d("LEARNER", "Outputting URI: " + resource);
    		return resource;
    	}
    }
    


}
