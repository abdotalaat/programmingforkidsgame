package com.example.customsimpleactivities;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.LayoutGameActivity;
import org.andengine.ui.activity.SimpleLayoutGameActivity;
import org.andengine.util.debug.Debug;

import com.example.gamekids.R;
import com.example.manager.ResourcesManager;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


public class MainLevelsSimpleLayout extends SimpleLayoutGameActivity 
{
	
	private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;
	private ITextureRegion mButtonNormalTextureRegion;
	private ITextureRegion mPressedTextureRegion;
	private ITextureRegion mDisabledTextureRegion;

	public Camera camera;
	public final static int CAMERA_WIDTH = 1280;
	public final static int CAMERA_HEIGHT = 690;
	public ListView aListView;
	String[] items;
	Scene scene;
	ButtonSprite buttonSprite ;
	
	

	@Override
	protected void onSetContentView() 
	{
		super.onSetContentView();
		/*
		aListView = (ListView) findViewById(R.id.listView1);
		items = new String[] { "Item 1", "Item 2", "Item 3" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
		aListView.setAdapter(adapter);
		*/
	
		Toast.makeText(getApplicationContext(), "Only the First Level is Active :)", Toast.LENGTH_LONG).show();
		
		Intent intent = getIntent();
		String lvlId = intent.getStringExtra("levelId");
		

		//Toast.makeText(getApplicationContext(), "LvelID: "+lvlId, Toast.LENGTH_LONG).show();
		
		HorizontalScrollView scrolView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		scrolView.setHorizontalScrollBarEnabled(false);
		
		//scrolView.
		
		
		
		scrolView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				//Toast.makeText(getApplicationContext(), "Children Number: "+v.getId(), Toast.LENGTH_LONG).show();
				 //Animation.flipTransition(viewAnimator, FlipDirection.LEFT_RIGHT);
			}
		});
		
	
		
		
		
		ImageView b = (ImageView) findViewById(R.id.imageView1);
		//ImageView b = (ImageView)findViewById(R.id.imageView1);
		
		b.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				System.out.println("ImageBtn Pressed");
				Intent i = new Intent(ResourcesManager.getInstance().activity, SublevelSimpleLayout.class);
				i.putExtra("levelId", "1");
				ResourcesManager.getInstance().activity.startActivity(i);
				finish();
			}
		});
		
	}
	

	@Override
	public EngineOptions onCreateEngineOptions() 
	{
		//Toast.makeText(this, "Touch the screen to move the particlesystem.", Toast.LENGTH_LONG).show();

		final Camera camera = new Camera(0, 0, MainLevelsSimpleLayout.CAMERA_WIDTH, MainLevelsSimpleLayout.CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(MainLevelsSimpleLayout.CAMERA_WIDTH, MainLevelsSimpleLayout.CAMERA_HEIGHT), camera);
	}

	@Override
	protected void onCreateResources() throws IOException 
	{
		loadBtn();
	}

	@Override
	protected Scene onCreateScene() throws IOException 
	{
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		scene.getBackground().setColor(0.09804f, 0.6274f, 0.8784f);
		final ButtonSprite buttonSprite = new ButtonSprite(CAMERA_WIDTH/2, CAMERA_HEIGHT - 40, this.mButtonNormalTextureRegion, this.mPressedTextureRegion, this.mDisabledTextureRegion, this.getVertexBufferObjectManager());
		buttonSprite.setScale(2);
		scene.attachChild(buttonSprite);
		
		
		return scene;
	}


	
	@Override
	protected int getLayoutID() 
	{
		return R.layout.sublevel_simple_layout;
	}

	@Override
	protected int getRenderSurfaceViewID() 
	{
		return R.id.horizental_rendersurfaceviewID;
	}
	
	
	public void loadBtn()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		this.mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 512, 512);
		this.mButtonNormalTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "button_normal.png");
		this.mPressedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "button_normal.png");
		this.mDisabledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "button_normal.png");
		
		try 
		{
			this.mBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			this.mBuildableBitmapTextureAtlas.load();
		} 
		catch (TextureAtlasBuilderException e) 
		{
			Debug.e(e);
		}
	}
	
}