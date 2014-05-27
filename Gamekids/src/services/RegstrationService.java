package services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.example.gamekids.AppLogingActivity;
import com.example.gametutorial.MainActivity;
import com.example.manager.SceneManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class RegstrationService extends AsyncTask<String, Void, String> {
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
		
		if(output.trim().equals("true"))
		{
			
			/*SharedPreferences sharedPref = getSharedPreferences("PFKLog", Context.MODE_PRIVATE);
			Editor editor = sharedPref.edit() ;
			editor.putString("username", "sss");
			editor.commit();*/
			
			//userFound
			SceneManager.getInstance().createMenuSceneFromFacebook();
			
		
			
		}
		

		
	}
}
