package eg.iti.programmingforkids.gamekids;

import eg.iti.programmingforkids.gamekids.R;

import eg.iti.programmingforkids.gametutorial.MainActivity;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class SplashActivity extends Activity {

	private static int SPLASH_TIME_OUT = 3000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		new Handler().postDelayed(new Runnable() {
			 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
            	
            	SharedPreferences sharedPref = getSharedPreferences("PFKLog", Context.MODE_PRIVATE);
				String login = sharedPref.getString("username", "");
				if( login.equals("") ){
	                
	                Intent i = new Intent(SplashActivity.this, HomeHActivity.class);
	                startActivity(i);
				}
				else{
					Intent i = new Intent(SplashActivity.this, MainActivity.class);
	                startActivity(i);
				}

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
