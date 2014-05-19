package com.example.manager;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.activity.SimpleLayoutGameActivity;


import android.app.Activity;
import android.content.Intent;

import com.example.base.BaseScene;
import com.example.customsimpleactivities.MainLevelsSimpleLayout;
import com.example.scene.CompetitionScene;

import com.example.scene.LoadingScene;
import com.example.scene.MainMenuScene;
import com.example.scene.MazeScene;
import com.example.scene.RegisterScene;
import com.example.scene.SplashScene;


public class SceneManager 
{

	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene levelMenuScene;
	private BaseScene loadingScene;
	private BaseScene gameScene;
	private BaseScene competitionScene;
	private BaseScene registeScene;
	
	private static int levelID ;

	private static final SceneManager INSTANCE = new SceneManager();

	private SceneType currentSceneType = SceneType.SCENE_SPLASH;

	private BaseScene currentScene;

	private Engine engine = ResourcesManager.getInstance().engine;

	public enum SceneType 
	{
		SCENE_SPLASH, SCENE_MENU, SCENE_GAME, SCENE_LOADING,SCENE_SUBMENU,
	}

	public void setScene(BaseScene scene) 
	{
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}

	public void setScene(SceneType sceneType) 
	{
		switch (sceneType) 
		{
		case SCENE_MENU:
			setScene(menuScene);
			break;

		case SCENE_SPLASH:
			setScene(splashScene);
			break;
		case SCENE_SUBMENU:
			setScene(levelMenuScene);
		case SCENE_GAME:
			setScene(gameScene);
			break;
		case SCENE_LOADING:
			setScene(loadingScene);
			break;
		default:
			break;
		}
	}

	public static SceneManager getInstance() 
	{
		return INSTANCE;
	}

	public SceneType getCurrentSceneType() {
		return currentSceneType;
	}

	public BaseScene getCurrentScene() {
		return currentScene;
	}

	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) //ss 
	{
		/*
		ResourcesManager.getInstance().loadSplashScreen();
		//ResourcesManager.getInstance().loadMenuResources();
		splashScene = new SplashScene();
		currentScene = splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
		*/
		ResourcesManager.getInstance().loadSplashScreen();
		splashScene = new SplashScene();
		currentScene = splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}

	private void disposeSplashScene() 
	{
		ResourcesManager.getInstance().unloadSplashScreen();
		splashScene.disposeScene();
		splashScene = null;
	}

	public void createMenuScene(OnCreateSceneCallback pOnCreateSceneCallback) //ss
	{
		/*
		//ResourcesManager.getInstance().loadSplashScreen();
		ResourcesManager.getInstance().loadMenuResources();
		menuScene = new MainMenuScene();
		currentScene = menuScene;
		setScene(menuScene);
		disposeSplashScene();
		//pOnCreateSceneCallback.onCreateSceneFinished(menuScene);
		 * 
		 */
		
		ResourcesManager.getInstance().loadMenuResources();
		menuScene = new MainMenuScene();
		setScene(menuScene);
		disposeSplashScene();
	}
	
	public void createMenuSceneFromFacebook() //ss
	{
		ResourcesManager.getInstance().loadMenuResources();
		menuScene = new MainMenuScene();
		setScene(menuScene);
		disposeFacebookScene();
	}
	
	
	public void createFacebookRegisterScene() //ss
	{
		ResourcesManager.getInstance().loadFacebookRegisterSceneResources();
		registeScene = new RegisterScene();
		setScene(registeScene);
		disposeSplashScene();
	}
	
	private void disposeFacebookScene() 
	{
		ResourcesManager.getInstance().unLoadFacebookGraphics();
	}
	
	public void createSubMenuScene()
	{
		/*
		ResourcesManager.getInstance().loadSubMenuResources();
		levelMenuScene = new LevelsMenuScene();
		loadingScene = new LoadingScene();
		SceneManager.getInstance().setScene(levelMenuScene);
		ResourcesManager.getInstance().unloadMenuTextures();
		*/
		
		
		//SublevelSimpleLayout subLayout = new SublevelSimpleLayout();
		ResourcesManager.getInstance().loadSubMenuResources();
		
		Intent i = new Intent(ResourcesManager.getInstance().activity, MainLevelsSimpleLayout.class);
		i.putExtra("levelId", "1");
		ResourcesManager.getInstance().activity.startActivity(i);
		//ResourcesManager.getInstance().unloadMenuTextures();
	}
	
	public void createCompetitionScene()
	{
		ResourcesManager.getInstance().loadSubMenuResources();
		competitionScene = new CompetitionScene();
		loadingScene = new LoadingScene();
		SceneManager.getInstance().setScene(competitionScene);
		
		ResourcesManager.getInstance().unloadMenuTextures();
		
		
		/*
		Intent i = new Intent(ResourcesManager.getInstance().activity , TextMenuExample.class);
		ResourcesManager.getInstance().activity.startActivity(i);
		*/
	}
	
	
	public void getMenuSceneBack(final Engine mEngine)
	{
		ResourcesManager.getInstance().loadMenuTextures();
		setScene(menuScene);
		levelMenuScene.disposeScene();
		ResourcesManager.getInstance().unloadSubMenuTextures();
	}
	
	public void getMenuSceneBackFromCompt(final Engine mEngine)
	{
		ResourcesManager.getInstance().loadMenuTextures();
		setScene(menuScene);
		competitionScene.disposeScene();
		ResourcesManager.getInstance().unloadComptTextures();
	}
	
	
	public void loadGameScene(final Engine mEngine)
	{
	    setScene(loadingScene);
	    ResourcesManager.getInstance().unloadSubMenuTextures();
	    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
	    {
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	            mEngine.unregisterUpdateHandler(pTimerHandler);
	            ResourcesManager.getInstance().loadGameResources();
	            gameScene = new MazeScene();
	            setScene(gameScene);
	        }
	    }));
	}
	
	public void loadGameSceneA()
	{
		loadingScene = new LoadingScene();
	    setScene(loadingScene);
	    
	    
	    
	    engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback()
	    {
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	            engine.unregisterUpdateHandler(pTimerHandler);
	            ResourcesManager.getInstance().loadGameResources();
	            gameScene = new MazeScene();
	            setScene(gameScene);
	        }
	    }));
	    
	    
	 //   a.finish();
	    //
	    ResourcesManager.getInstance().loadGameResources();
        gameScene = new MazeScene();
        setScene(gameScene);
	}
	
	
	public void loadSubMenuScene(final Engine mEngine)
	{
		loadingScene = new LoadingScene();
	    setScene(loadingScene);
	    gameScene.disposeScene();
	    ResourcesManager.getInstance().unloadGameTextures();
	    mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
	    {
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	            mEngine.unregisterUpdateHandler(pTimerHandler);
	            //ResourcesManager.getInstance().loadSubMenuTextures();
	            setScene(menuScene);
	        }
	    }));
	}

	public static void setLevelID(int levelID) {
		SceneManager.levelID = levelID;
	}
	public static int getLevelID() {
		return levelID;
	}
}
