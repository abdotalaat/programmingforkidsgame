package com.example.scene;


import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.animator.AlphaMenuSceneAnimator;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import android.view.KeyEvent;


public class TextMenuExample extends SimpleBaseGameActivity implements IOnMenuItemClickListener 
{
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	protected static final int MENU_RESET = 0;
	protected static final int MENU_QUIT = MENU_RESET + 1;

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;

	private Scene mMainScene;

	private ITexture mFaceTexture;
	private ITextureRegion mFaceTextureRegion;

	private Font mFont;

	private MenuScene mMenuScene;



	@Override
	public EngineOptions onCreateEngineOptions() 
	{
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
	}

	@Override
	public void onCreateResources() throws IOException 
	{
		//System.out.println("create Resources ***************************************************************");
		FontFactory.setAssetBasePath("font/");
		
		this.mFont = FontFactory.createFromAsset(this.getFontManager(), this.getTextureManager(), 256, 256, TextureOptions.BILINEAR, this.getAssets(), "Plok.ttf", 48, true, android.graphics.Color.WHITE);
		this.mFont.load();

	}

	@Override
	public Scene onCreateScene() 
	{
		//System.out.println("Create Scceneeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		this.mEngine.registerUpdateHandler(new FPSLogger());

		this.mMenuScene = this.createMenuScene();

		/* Just a simple scene with an sprite flying around. */
		this.mMainScene = new Scene();
		this.mMainScene.getBackground().setColor(0.09804f, 0.6274f, 0.8784f);

		this.mMainScene.setChildScene(this.mMenuScene, false, true, true);
		return this.mMainScene;
	}

	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) 
	{
		if(pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) 
		{
			if(this.mMainScene.hasChildScene()) 
			{
				/* Remove the menu and reset it. */
				this.mMenuScene.back();
			}
			else {
				/* Attach the menu. */
				this.mMainScene.setChildScene(this.mMenuScene, false, true, true);
			}
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}

	@Override
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
		switch(pMenuItem.getID()) 
		{
			case MENU_RESET:
				/* Restart the animation. */
				this.mMainScene.reset();

				/* Remove the menu and reset it. */
				this.mMainScene.clearChildScene();
				this.mMenuScene.reset();
				return true;
			case MENU_QUIT:
				/* End Activity. */
				this.finish();
				return true;
			default:
				return false;
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public MenuScene createMenuScene() 
	{
		final MenuScene menuScene = new MenuScene(this.mCamera, new AlphaMenuSceneAnimator());

		final IMenuItem resetMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_RESET, this.mFont, "RESET", this.getVertexBufferObjectManager()), new Color(1,0,0), new Color(0,0,0));
		menuScene.addMenuItem(resetMenuItem);

		final IMenuItem quitMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_QUIT, this.mFont, "QUIT", this.getVertexBufferObjectManager()), new Color(1,0,0), new Color(0,0,0));
		menuScene.addMenuItem(quitMenuItem);

		menuScene.buildAnimations();

		menuScene.setBackgroundEnabled(false);

		menuScene.setOnMenuItemClickListener(this);
		return menuScene;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}