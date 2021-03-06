package com.example.manager;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.graphics.Color;

import com.example.gametutorial.MainActivity;


public class ResourcesManager 
{

	private static final ResourcesManager INSTANCE = new ResourcesManager();

	public Engine engine;
	public MainActivity activity;
	public BoundCamera camera;
	public VertexBufferObjectManager vbom;

	    
	
	public ITextureRegion hint_btn;
	public ITextureRegion splash_region;
	public ITextureRegion menu_background_region;
	public ITextureRegion menu_background_facebook;
	public ITextureRegion background_register;
	public ITextureRegion play_region;
	public ITextureRegion options_region;
	public ITextureRegion quit_region;
	public ITextureRegion submenu_background_region;
	public ITextureRegion submenu_item1_region;
	public ITextureRegion reg_bg;
	
	
	// Game Texture Regions
	public ITextureRegion block1_region;
	public ITextureRegion coin_region;
	public ITextureRegion arrow_btn;
	public ITextureRegion submit_btn;
	public ITextureRegion reset_btn;
	public Music music;
	
	private BitmapTextureAtlas splashTextureAtlas;
	private BitmapTextureAtlas menuAymanTextureAtlas;
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	private BuildableBitmapTextureAtlas faceTextureAtlas;
	private BuildableBitmapTextureAtlas subMenuAtlas;
	private BuildableBitmapTextureAtlas registrationAtlas;
	private BuildableBitmapTextureAtlas registerTextureAtlas;
	// Game Texture
	public BuildableBitmapTextureAtlas gameTextureAtlas;

	//submit and reset
	//Submit
	public ITexture submitButtonTexture;
	
	//player
	//public ITexture mPlayerTexture;
	//public TiledTextureRegion mPlayerTextureRegion;
	public ITiledTextureRegion player_region;
	//
	//competions button
	public ITextureRegion competionBtn;
	public Font competionFont;
	//////////////////
	//complete game window

	public ITextureRegion complete_window_region;
	public ITiledTextureRegion complete_stars_region;
	//facebook
	public ITextureRegion faceNormalTextureRegion ;
	public ITextureRegion facePressedTextureRegion;
	public ITextureRegion faceDisabledTextureRegion;
	
	public ITextureRegion registerNormalTextureRegion ;
	
	
	public Font font;

	public void loadRegistrationResources()
	{
		loadRegistrationGraphics();
		loadMenuFonts();
	}

	public void loadMenuResources() //ss
	{
		loadMenuGraphics();
		loadMenuAudio();
		loadMenuFonts();
	}
	
	public void loadSubMenuResources()
	{
		loadSubMenuGraphics();
		loadSubMenuAudio();
		loadSubMenuFonts();
	}

	public void loadGameResources() 
	{
		loadGameGraphics();
		loadGameFonts();
		loadGameAudio();
	}

	public void loadFacebookRegisterSceneResources()
	{
		loadFacebookGraphics();
		
	}

	private void loadMenuAudio() 
	{

	}


	
	private void loadFacebookGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		faceTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        
		menu_background_facebook = BitmapTextureAtlasTextureRegionFactory.createFromAsset(faceTextureAtlas, activity, "menu_bg.gif");
		this.registerNormalTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(faceTextureAtlas , activity, "button_normal.png");
        this.faceNormalTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(faceTextureAtlas , activity, "faceOnbtn.png");
		this.facePressedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(faceTextureAtlas, activity, "faceoffbtn.png");
		this.faceDisabledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(faceTextureAtlas, activity , "faceoffbtn.png");
		

        try 
	    {
	        this.faceTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
	        this.faceTextureAtlas.load();
	    } 
	    catch (final TextureAtlasBuilderException e)
	    {
	        Debug.e(e);
	    }
	}
	
	public void unLoadFacebookGraphics()
	{
		faceTextureAtlas.unload();
	}
	
	private void loadGameGraphics() 
	{
		System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVV**********************VVVVVVVVVVVVVVVVVV*********************VVVV");
		
		 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
		    gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		    
		    block1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "block1.png");
		    coin_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "coin.png");
		    player_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "player.png", 3, 4);// 3 cols,4 rows
		    arrow_btn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "next.png");
		    submit_btn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "menu_ok.png");
		    reset_btn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "menu_reset.png");
		    complete_window_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "levelCompleteWindow.png");
		    complete_stars_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "star.png", 2, 1);//2 cols ,1 row
		    hint_btn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "hintPic.jpg");
		    try 
		    {
		        this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
		        this.gameTextureAtlas.load();
		    } 
		    catch (final TextureAtlasBuilderException e)
		    {
		        Debug.e(e);
		    }
	}

	private void loadGameFonts() 
	{
		FontFactory.setAssetBasePath("font/");
		final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
		font.load();
	}
	

	private void loadGameAudio() {
		try {
			music=MusicFactory.createMusicFromAsset(getInstance().engine.getMusicManager(),activity,"mfx/music.ogg");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void unloadMenuTextures()
	{
		menuTextureAtlas.unload();
	}
	
	public void loadMenuTextures()
	{
		menuTextureAtlas.load();
	}
	
	public void unloadSubMenuTextures()
	{
		subMenuAtlas.unload();
	}
	
	public void unloadComptTextures()
	{
		subMenuAtlas.unload();
	}
	
	public void loadSubMenuTextures()
	{
		subMenuAtlas.load();
	}
	
	public void loadCompetitionMenuResources()
	{
		loadCompetionMenuResources();
	}
	
	public void loadSplashScreen() 
	{
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 800, 600, TextureOptions.BILINEAR);
		splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splashPic.png", 0, 0);
		splashTextureAtlas.load();
	}

	public void unloadSplashScreen() 
	{

		splashTextureAtlas.unload();
		splash_region = null;
	}
	
	private void loadMenuFonts()
	{
	    FontFactory.setAssetBasePath("font/");
	    final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	    competionFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
	    competionFont.load();
	}

	public static ResourcesManager getInstance() 
	{
		return INSTANCE;
	}
	
	private void loadCompetionMenuResources()
	{
		FontFactory.setAssetBasePath("font/");
		
		this.competionFont = FontFactory.createFromAsset(activity.getFontManager(), activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR, activity.getAssets(), "Plok.ttf", 48, true, android.graphics.Color.WHITE);
		this.competionFont.load();
		
		 
	}

	private void loadMenuGraphics()
	{
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024,1024, TextureOptions.BILINEAR);
        menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "bg.jpg");
        play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.jpg");
        options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.jpg");
        quit_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity,"quitbtn.jpg");
        competionBtn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity,"level.jpg");
       
    	try 
    	{
			this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.menuTextureAtlas.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}
	}
	
	private void loadSubMenuGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/subMenu/");
		subMenuAtlas= new BuildableBitmapTextureAtlas(activity.getTextureManager(),1024,1024,TextureOptions.BILINEAR);
		submenu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(subMenuAtlas, activity,"menu_bg.gif");
		submenu_item1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(subMenuAtlas, activity, "level.jpg");
		
		try 
    	{
			this.subMenuAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.subMenuAtlas.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}

		
	}
	private void loadSubMenuAudio()
	{
		
	}
	private void loadRegistrationGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/subMenu/");
		registerTextureAtlas= new BuildableBitmapTextureAtlas(activity.getTextureManager(),2048,2048,TextureOptions.BILINEAR);
		reg_bg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(subMenuAtlas, activity,"menu_bg.gif");
		//submenu_item1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(subMenuAtlas, activity, "level.jpg");
		
		try 
    	{
			this.registerTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.registerTextureAtlas.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}

	}
	
	private void loadSubMenuFonts()
	{
		FontFactory.setAssetBasePath("font/");
		final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
		font.load();
	}
	
	
	public static void prepareManager(Engine engine, MainActivity activity,BoundCamera camera, VertexBufferObjectManager vbom) 
	{
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
		
	}
	public void clearPhysics()
	{
		engine.runOnUpdateThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void unloadGameTextures()
	{
	    // TODO (Since we did not create any textures for game scene yet)
	
	
		gameTextureAtlas.unload();
		block1_region=null;
		coin_region=null;
		arrow_btn=null;
		submit_btn=null;
		reset_btn=null;
		hint_btn=null;
		
	}
	
	
}
