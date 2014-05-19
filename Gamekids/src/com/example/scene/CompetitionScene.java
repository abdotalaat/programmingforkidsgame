package com.example.scene;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.animator.AlphaMenuSceneAnimator;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.base.BaseScene;
import com.example.gamekids.R;
import com.example.manager.ResourcesManager;
import com.example.manager.SceneManager;
import com.example.manager.SceneManager.SceneType;

public class CompetitionScene extends BaseScene implements IOnMenuItemClickListener
{

	
	///////////////////////////////
	//variables
	private MenuScene competionMenuScene;
	private final int MENU_OPTIONS = 0;
	//private final int MENU_QUIT = 1;
	private final float     WIDTH  = 400;
	 private final float     HEIGHT  = 240;
	////////////////////
	 //Menu
	protected static final int MENU_RESET = 0;
	protected static final int MENU_QUIT = MENU_RESET + 1;
	private Font mFont;
	private MenuScene mMenuScene;
	 
	
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,float pMenuItemLocalX, float pMenuItemLocalY) 
	{
		
		ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() 
		{	
			@Override
			public void run() 
			{
				
				final Dialog dialog = new Dialog(ResourcesManager.getInstance().activity);
				dialog.setContentView(R.layout.competiondialog);
				dialog.setTitle("Title...");
				
	 
				// set the custom dialog components - text, image and button
				TextView text = (TextView) dialog.findViewById(R.id.text);
				text.setText("Android custom dialog example!");
				ImageView image = (ImageView) dialog.findViewById(R.id.image);
				image.setImageResource(R.drawable.ic_launcher);
	 
				
				Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
				//if button is clicked, close the custom dialog
				
				dialogButton.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						dialog.dismiss();
					}
				});
				
	 
				dialog.show();
				
			}
		});
		
		switch(pMenuItem.getID())
		{
			case MENU_OPTIONS:

			   return true;
		 default:
		return false;
		}
	}

	@Override
	public void createScene() 
	{
		ResourcesManager.getInstance().loadCompetitionMenuResources();
		createBackground();
		createMenuChildScene();
		
	}

	@Override
	public void onBackKeyPressed() 
	{
		SceneManager.getInstance().getMenuSceneBackFromCompt(engine);
		
	}

	@Override
	public SceneType getSceneType() 
	{
		return SceneType.SCENE_SUBMENU;
	}

	@Override
	public void disposeScene() 
	{
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
		final MenuScene competionMenuScene = new MenuScene(ResourcesManager.getInstance().camera, new AlphaMenuSceneAnimator());
		//competionMenuScene = new MenuScene(camera);
		//competionMenuScene.setPosition(WIDTH,HEIGHT);
		
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("Item 1");
		arr.add("Item 2");
		arr.add("Item 3");
		arr.add("Item 4");
 		
		for (int i = 0; i < 4 ; i++) 
		{
			final IMenuItem item = new ColorMenuItemDecorator(new TextMenuItem(MENU_QUIT, ResourcesManager.getInstance().competionFont, arr.get(i), ResourcesManager.getInstance().vbom), new Color(1,0,0), new Color(0,0,0));
			competionMenuScene.addMenuItem(item);
		}
		
		/*
		final IMenuItem optionsMenuItem =  new ColorMenuItemDecorator(new TextMenuItem(MENU_RESET, ResourcesManager.getInstance().competionFont, "RESET", ResourcesManager.getInstance().vbom), new Color(1,0,0), new Color(0,0,0));
		final IMenuItem quitMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_QUIT, ResourcesManager.getInstance().competionFont, "QUIT", ResourcesManager.getInstance().vbom), new Color(1,0,0), new Color(0,0,0));
		
		
		competionMenuScene.addMenuItem(optionsMenuItem);
		competionMenuScene.addMenuItem(quitMenuItem);
		
		*/
		competionMenuScene.buildAnimations();  //must setposition after it unless it has no effect
		competionMenuScene.setBackgroundEnabled(false);
		
//		playMenuItem.setPosition(playMenuItem.getX()-20, playMenuItem.getY() - 150);
//		optionsMenuItem.setPosition(optionsMenuItem.getX()-20, optionsMenuItem.getY() - 110);
		
//		optionsMenuItem.setPosition((WIDTH/2 - optionsMenuItem.getWidth()/2)-50,(HEIGHT/2 - optionsMenuItem.getHeight()/2)-50);
		
		
		competionMenuScene.setOnMenuItemClickListener(this);
		
		setChildScene(competionMenuScene);
		
	}

}
