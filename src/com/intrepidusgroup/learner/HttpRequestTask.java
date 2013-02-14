package com.intrepidusgroup.learner;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//import java.security.KeyManagementException;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;

import org.apache.http.client.ClientProtocolException;

import android.os.AsyncTask;
import android.util.Log;

public class HttpRequestTask extends AsyncTask<String, String, String> {
	protected String jobStatus;
	@Override
    protected String doInBackground(String... uri) {
        // use the HttpURLConnection class for this. It's recommended starting with Gingerbread and above.
		// Passed down is a set of parameters - 
		// uri[0] is the actual resource and uri[1] is the secret value to pass in the header

		URL url = null;

		try {
			url = new URL(uri[0]);
			Log.d("LEARNER", "In HttpRequestTask - URI is " + url.toString());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String secretTokenHeader = "SecretTokenHeader";
		String secretTokenValue = uri[1];
		
		HttpURLConnection urlConnection = null;
		
		try {
			urlConnection = (HttpURLConnection) url.openConnection();
			// add our secret header
			urlConnection.setRequestProperty(secretTokenHeader, secretTokenValue);
			// Uncomment the below if we need to relax cert checking and NOT check certificates. Could be an Easter egg, really, it being another way to solve the challenge.
			//TrustModifier.relaxHostChecking(urlConnection);
			urlConnection.connect();
		} catch (Exception e1) {
			// Important hint here
        	Log.d("LEARNER", "Something happened opening a URL connection:" + e1.getMessage());
        	Log.d("LEARNER", "Hint: Are you trying to man-in-the-middle me?");
		}
		
		
        String responseString = jobStatus;
        try {
        
        	InputStream in = new BufferedInputStream(urlConnection.getInputStream()); 
       
    
        } catch (ClientProtocolException e) {
        	Log.d("LEARNER", "Protocol issue opening a URL connection:" + e.getMessage());
        } catch (IOException e) {
            Log.d("LEARNER", "IO issue opening a URL connection:" + e.getMessage());
        }
        finally {
            urlConnection.disconnect();
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // do nothing, actually. Just silently die. We don't care about the response.
    }
}



