package com.example.gamekids;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.example.gametutorial.MainActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AppLogingActivity extends Activity  implements OnClickListener{
Button button;
TextView outputText;
EditText fName;
EditText lName;
EditText userName;
EditText birthOfDate;
String firstName;
String lastName;
String userNameValue;
String dateOfBirth;


@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_app_loging);

	findViewsById();
	button.setOnClickListener(this);
	

}

private void findViewsById() {
	button = (Button) findViewById(R.id.button);
	outputText = (TextView) findViewById(R.id.outputTxt);
	fName = (EditText) findViewById(R.id.editText1);
	lName = (EditText) findViewById(R.id.editText2);
	userName = (EditText) findViewById(R.id.editText3);
	birthOfDate = (EditText) findViewById(R.id.editText4);
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {

	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.activity_app_loging, menu);
	return true;
}



/**
 * A placeholder fragment containing a simple view.
 */

@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	
	firstName = fName.getText().toString().trim();
	lastName = lName.getText().toString().trim();
	userNameValue = userName.getText().toString().trim();
	dateOfBirth = birthOfDate.getText().toString().trim();
	
	GetXMLTask task = new GetXMLTask();
	task.execute(new String[] { "http://10.145.40.70:8088/ProgramingForKidsWS/rest/user?fName="+firstName+"&lName="+lastName+"&userName="+userNameValue+"&birthOfDate="+dateOfBirth+""});

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
		Toast.makeText(AppLogingActivity.this,output, Toast.LENGTH_LONG).show();
		if(output.trim().equals("true"))
		{
			
			/*SharedPreferences sharedPref = getSharedPreferences("PFKLog", Context.MODE_PRIVATE);
			Editor editor = sharedPref.edit() ;
			editor.putString("username", "sss");
			editor.commit();*/
			
			Intent intent = new Intent(AppLogingActivity.this, MainActivity.class);
			startActivity(intent);
		
			
		}
		

		
	}
}

}
