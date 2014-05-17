package com.example.gamekids;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gametutorial.MainActivity;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook.DialogListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HomeHActivity extends Activity  implements OnClickListener{
	
	
	
	
	Button facButton;
	private static String APP_ID = "530325873743587"; // Replace your App ID
														// here
	
	
	// Instance of Facebook Class
		private Facebook facebook;
		private AsyncFacebookRunner mAsyncRunner;
		String FILENAME = "AndroidSSO_data";
		private SharedPreferences mPrefs;

	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_h);		
		Button button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(this);
		
		
		facButton = (Button) findViewById(R.id.button2);
		facButton.setOnClickListener(this);

		facebook = new Facebook(APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(facebook);
		
	}

	
	
	
	
	public void loginToFacebook() {
		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) {
			facebook.setAccessToken(access_token);
		}

		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {
			facebook.authorize(this,
					new String[] { "email", "publish_stream" },
					new DialogListener() {

						@Override
						public void onCancel() {
							// Function to handle cancel event
						}

						@Override
						public void onComplete(Bundle values) {
							// Function to handle complete event
							// Edit Preferences and update facebook acess_token

							//SharedPreferences.Editor editor = mPrefs.edit();
							//editor.putString("access_token",
								//	facebook.getAccessToken());
							//editor.putLong("access_expires",
									//facebook.getAccessExpires());
							

							//editor.commit();
							getProfileInformation();
						}

						@Override
						public void onError(DialogError error) {
							// Function to handle error

						}

						@Override
						public void onFacebookError(FacebookError fberror) {
							// Function to handle Facebook errors

						}

					});
		}
	}


	
	
	public void getProfileInformation() {
		mAsyncRunner.request("me", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Profile", response);
				final String json = response;
				try {
					JSONObject profile = new JSONObject(json);
					// getting name of the user
					
					final String userName = profile.getString("username");
					final String fName = profile.getString("first_name");
					final String lName = profile.getString("last_name");
					
					// getting email of the user
					final String email = profile.getString("email");
					Log.e("name", json);
					

					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							
							GetXMLTask task = new GetXMLTask();
							task.execute(new String[] { "http://10.145.40.70:8088/ProgramingForKidsWS/rest/user?fName="+fName+"&lName="+lName+"&userName="+userName+"&birthOfDate="+"sd"+""});
							
							
							
						}

					});

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}

	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home_h, menu);
		return true;
	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId() != facButton.getId())
		{
		Intent intent = new Intent(this,AppLogingActivity.class);
		startActivity(intent);
		}
		else
		{
			loginToFacebook();
		}
		
	}
	
	
	
	
	
	private class GetXMLTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String output = null;
			for (String url : urls) {
				output = getOutputFromUrl(url);
			}
			return output;
		}

		private String getOutputFromUrl(String url) {
			String output = null;
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);

				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				output = EntityUtils.toString(httpEntity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return output;
		}

		@Override
		protected void onPostExecute(String output) {
			Toast.makeText(HomeHActivity.this,output, Toast.LENGTH_LONG).show();
			if(output.trim().equals("true"))
			{
				Intent intent = new Intent(HomeHActivity.this, MainActivity.class);
				startActivity(intent);
				
			}
			

			
		}
	}
	

	/**
	 * A placeholder fragment containing a simple view.
	 */
	

}
