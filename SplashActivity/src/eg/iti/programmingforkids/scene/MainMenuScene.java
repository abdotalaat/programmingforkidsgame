package eg.iti.programmingforkids.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;


import eg.iti.programmingforkids.base.*;
import eg.iti.programmingforkids.gametutorial.MainActivity;
import eg.iti.programmingforkids.manager.ResourcesManager;
import eg.iti.programmingforkids.manager.SceneManager;
import eg.iti.programmingforkids.manager.SceneManager.SceneType;
import eg.iti.programmingforkids.scene.*;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener
{
	//---------------------------------------------
	// VARIABLES
	//---------------------------------------------
	
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;
	private final int MENU_QUIT = 2;
	private final int MENU_COMPETION = 3;
	private final float     WIDTH  = 400;
	 private final float     HEIGHT  = 240;
	
	//---------------------------------------------
	// METHODS FROM SUPERCLASS
	//---------------------------------------------

	 private Sprite menuBG;
	 
	@Override
	public void createScene()
	{
		createBackground();
		//createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed()
	{
		System.exit(0);
	}

	@Override
	public SceneType getSceneType()
	{
		return SceneType.SCENE_MENU;
	}
	

	@Override
	public void disposeScene()
	{
		// TODO Auto-generated method stub
	}
	
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
	{
		
		switch(pMenuItem.getID())
		{
			case MENU_PLAY:
				//Load Game Scene!
//				SceneManager.getInstance().loadGameScene(engine);
				return true;
			case MENU_OPTIONS:
				SceneManager.getInstance().createSubMenuScene();
				return true;
				
				
			case MENU_COMPETION:
				SceneManager.getInstance().createCompetitionScene();
				return true;
					
				
			case MENU_QUIT:
				
				ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() 
				{
			        public void run() 
			        {
			         
			        	
			        	 AlertDialog.Builder alert = new AlertDialog.Builder(ResourcesManager.getInstance().activity);
			             alert.setTitle("");
			             alert.setMessage("Are you sure to exit this game?");
			             alert.setPositiveButton("Yes", new OnClickListener() {
			                     
			                     public void onClick(DialogInterface arg0, int arg1) {

			                    	 	System.exit(0);
			                     }
			             	});
			             alert.setNegativeButton("No",new OnClickListener() {
							
							public void onClick(DialogInterface dialog, int which) 
							{
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
			             alert.show();
			        }
				});
				return true;
			default:
				return false;
		}
	}
	
	//---------------------------------------------
	// CLASS LOGIC
	//---------------------------------------------
	
	
	private void createBackground()
	{
		attachChild(new Sprite(WIDTH,HEIGHT, resourcesManager.menu_background_region, vbom)
		{
    		@Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
    		{
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
		});
		
		createMenuChildScene();	
		
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
	


}