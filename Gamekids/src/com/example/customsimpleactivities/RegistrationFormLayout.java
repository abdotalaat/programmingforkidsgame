package com.example.customsimpleactivities;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.SimpleLayoutGameActivity;

import services.RegstrationService;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gamekids.R;


public class RegistrationFormLayout extends SimpleLayoutGameActivity{

	Scene scene;
	public final static int CAMERA_WIDTH = 1280;
	public final static int CAMERA_HEIGHT = 690;
	
	@Override
	protected void onSetContentView() {
		// TODO Auto-generated method stub
		super.onSetContentView();
		Button regBtn=(Button)findViewById(R.id.reg_btn);
		final EditText nameTxt=(EditText)findViewById(R.id.name_txt);
		final EditText ageTxt=(EditText)findViewById(R.id.age_txt);
		EditText emailTxt=(EditText)findViewById(R.id.email_txt);
		regBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RegstrationService task = new RegstrationService();
				task.execute(new String[] { "http://10.145.40.70:8088/ProgramingForKidsWS/rest/user?userName="+nameTxt.getText().toString()+"&birthOfDate="+ageTxt.getText().toString()+""});
			}
		});
	}
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		final Camera camera = new Camera(0, 0, RegistrationFormLayout.CAMERA_WIDTH, RegistrationFormLayout.CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(RegistrationFormLayout.CAMERA_WIDTH, RegistrationFormLayout.CAMERA_HEIGHT), camera);

	}

	@Override
	protected void onCreateResources() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Scene onCreateScene() throws IOException {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());

		scene = new Scene();
		scene.getBackground().setColor(0.09804f, 0.6274f, 0.8784f);
		
		return scene;
	}

	@Override
	protected int getLayoutID() {
		// TODO Auto-generated method stub
		return R.layout.registration_form;
	}

	@Override
	protected int getRenderSurfaceViewID() {
		// TODO Auto-generated method stub
		return R.id.register_surface;
	}

}
