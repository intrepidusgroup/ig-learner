package com.intrepidusgroup.learner;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.client.ClientProtocolException;

import android.os.AsyncTask;
import android.util.Log;

public class HttpRequestTaskWithPinning extends AsyncTask<String, String, String> {
	protected String jobStatus;
	@Override
    protected String doInBackground(String... uri) {
        // use the HttpURLConnection class for this. It's recommended starting with Gingerbread and above.
		// Passed down is a set of parameters - 
		// uri[0] is the actual resource and uri[1] is the secret value to pass in the header

		URL url = null;

		try {
			url = new URL(uri[0]);
			Log.d("LEARNER", "In HttpRequestTaskWithPinning - URI is " + url.toString());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String secretTokenHeader = "SecretTokenHeader";
		String secretTokenValue = uri[1];
		
		// This now has to be an HttpsURLConnection to be able to set a custom SSL Socket Factory
		HttpsURLConnection urlConnection = null;
		
		try {
			TrustManager[] trustManagers = new TrustManager[1];
			// PIN value for intrepidusgroup.com
			trustManagers[0]             = new PinningTrustManager(new String[] {"22be4c99907cf04f336da9e82cf0608fa79cc42e"}); 
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustManagers, null);
			urlConnection = (HttpsURLConnection) url.openConnection();
			// add our secret header
			urlConnection.setRequestProperty(secretTokenHeader, secretTokenValue);
			// Here we go, enabling certificate pinning
			urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());
			urlConnection.connect();
		} catch (Exception e1) {
			// Important hint here
        	Log.d("LEARNER", "Something happened opening a URL connection (w/Pinning):" + e1.getMessage());
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



