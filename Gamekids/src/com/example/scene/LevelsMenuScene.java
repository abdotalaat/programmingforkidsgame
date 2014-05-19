package com.example.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import android.widget.Toast;

import com.example.base.BaseScene;
import com.example.manager.ResourcesManager;
import com.example.manager.SceneManager;
import com.example.manager.SceneManager.SceneType;

public class LevelsMenuScene extends BaseScene implements IOnMenuItemClickListener{

	
	///////////////////////////////
	//variables
	private MenuScene submenuChildScene;
	private final int MENU_OPTIONS = 0;
	private final int MENU_QUIT = 1;
	private final float     WIDTH  = 400;
	 private final float     HEIGHT  = 240;
	////////////////////
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,float pMenuItemLocalX, float pMenuItemLocalY) 
	{
		
		switch(pMenuItem.getID())
		{
			case MENU_OPTIONS:
				SceneManager.getInstance().loadGameScene(engine);
				
			   return true;
		 default:
			 return false;
		}
	}

	@Override
	public void createScene() 
	{
		createBackground();
		createMenuChildScene();	
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().getMenuSceneBack(engine);
	}

	@Override
	public SceneType getSceneType() 
	{
		return SceneType.SCENE_SUBMENU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

	
	
	///////////////////////////////////////////////////////////
	private void createBackground()
	{
		attachChild(new Sprite(WIDTH,HEIGHT, resourcesManager.submenu_background_region, vbom)
		{
    		@Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
    		{
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
		});
	}
	
	private void createMenuChildScene()
	{
		submenuChildScene = new MenuScene(camera);
		submenuChildScene.setPosition(WIDTH,HEIGHT);
		
		final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourcesManager.submenu_item1_region, vbom), 1.2f, 1);
		//final IMenuItem quitMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_QUIT,resourcesManager.quit_region,vbom),1.2f,1);
		
		submenuChildScene.addMenuItem(optionsMenuItem);
		
		
		submenuChildScene.buildAnimations();  //must setposition after it unless it has no effect
		submenuChildScene.setBackgroundEnabled(false);
		
		
//		playMenuItem.setPosition(playMenuItem.getX()-20, playMenuItem.getY() - 150);
//		optionsMenuItem.setPosition(optionsMenuItem.getX()-20, optionsMenuItem.getY() - 110);
		
		optionsMenuItem.setPosition((WIDTH/2 - optionsMenuItem.getWidth()/2)-50,(HEIGHT/2 - optionsMenuItem.getHeight()/2)-50);
		
		
		submenuChildScene.setOnMenuItemClickListener(this);
		
		setChildScene(submenuChildScene);
		
	}

}
