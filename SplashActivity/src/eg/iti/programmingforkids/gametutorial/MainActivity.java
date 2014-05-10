package eg.iti.programmingforkids.gametutorial;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;

import eg.iti.programmingforkids.manager.ResourcesManager;
import eg.iti.programmingforkids.manager.SceneManager;

public class MainActivity extends BaseGameActivity 
{

	private BoundCamera camera;
	private ResourcesManager resourcesManager;

	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) 
	{
		// TODO Auto-generated method stub
		return new LimitedFPSEngine(pEngineOptions, 60);
	}

	@Override
	public EngineOptions onCreateEngineOptions() 
	{
		System.out.println("create Engine Option ***************************************************************");
		camera = new BoundCamera(0, 0, 800, 530);
		EngineOptions engineOptions = new EngineOptions(true,ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(800, 530), this.camera);
		// set needs music and sound to true, so we will be able to use some
		// audio for our game
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		System.out.println("Key Down ***************************************************************");
		if (keyCode == KeyEvent.KEYCODE_BACK) 
		{
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
		}
		return false;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)throws IOException 
	{
		System.out.println("create Resources ***************************************************************");
		ResourcesManager.prepareManager(mEngine, this, camera,getVertexBufferObjectManager());
		resourcesManager = ResourcesManager.getInstance();
		pOnCreateResourcesCallback.onCreateResourcesFinished();

	}

	
	//Creating splashScreen
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)throws IOException 
	{
		SceneManager.getInstance().createMenuScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(Scene pScene,OnPopulateSceneCallback pOnPopulateSceneCallback)throws IOException 
	{
		
		System.out.println("Load Menu After Splash Screen********************");
		/*
		mEngine.registerUpdateHandler(new TimerHandler(2f,new ITimerCallback() 
		{
					public void onTimePassed(final TimerHandler pTimerHandler)
					{
						mEngine.unregisterUpdateHandler(pTimerHandler);
						//SceneManager.getInstance().createMenuScene();
					}
		}));
		*/
		pOnPopulateSceneCallback.onPopulateSceneFinished();

	}

}
