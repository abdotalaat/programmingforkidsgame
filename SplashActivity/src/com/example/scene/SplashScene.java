package com.example.scene;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import com.example.base.BaseScene;
import com.example.manager.SceneManager.SceneType;
import org.andengine.engine.camera.Camera;

public class SplashScene extends BaseScene implements IOnMenuItemClickListener
{

	private Sprite splash;
	private Sprite menuBG;
	
	//
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;
	private final int MENU_QUIT = 2;
	private final int MENU_COMPETION = 3;
	private final float     WIDTH  = 400;
	 private final float     HEIGHT  = 240;

	@Override
	public void createScene() 
	{

		
		splash = new Sprite(0, 0, resourcesManager.splash_region, vbom) 
		{
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) 
			{
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};

		splash.setScale(1.5f);
		splash.setPosition(400, 240);
		attachChild(splash);
		
		createMenuChildScene();
	/*
		splash =  new Sprite(0,0, resourcesManager.menu_background_region, vbom)
		{
    		@Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
    		{
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
		};
		
		splash.setScale(1.5f);
		splash.setPosition(400, 240);
		attachChild(splash);
*/

	}
	
	public void createMenuChildScene()
	{
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(WIDTH,HEIGHT);
		
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, vbom), 1.2f, 1);
		final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourcesManager.options_region, vbom), 1.2f, 1);
		final IMenuItem quitMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_QUIT,resourcesManager.quit_region,vbom),1.2f,1);
		final IMenuItem competionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_COMPETION,resourcesManager.competionBtn,vbom),1.2f,1);
		
		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(optionsMenuItem);
		menuChildScene.addMenuItem(quitMenuItem);
		menuChildScene.addMenuItem(competionsMenuItem);
		
		menuChildScene.buildAnimations();  //must setposition after it unless it has no effect
		menuChildScene.setBackgroundEnabled(false);
		
		
//		playMenuItem.setPosition(playMenuItem.getX()-20, playMenuItem.getY() - 150);
//		optionsMenuItem.setPosition(optionsMenuItem.getX()-20, optionsMenuItem.getY() - 110);
		
		playMenuItem.setPosition((WIDTH/2 - playMenuItem.getWidth()/2)-50,(HEIGHT/2- playMenuItem.getHeight()/2)-20);
		optionsMenuItem.setPosition((WIDTH/2 - optionsMenuItem.getWidth()/2)-50,(HEIGHT/2 - optionsMenuItem.getHeight()/2)-120);
		quitMenuItem.setPosition((WIDTH/2 - quitMenuItem.getWidth()/2)-50, (HEIGHT/2 - quitMenuItem.getHeight()/2)-320);
		competionsMenuItem.setPosition((WIDTH/2 - quitMenuItem.getWidth()/2)-50, (HEIGHT/2 - quitMenuItem.getHeight()/2)-220);
		
		menuChildScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuChildScene);
		
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() 
	{
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();

	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		return false;
	}

}
