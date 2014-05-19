package com.example.customsimpleactivities;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleLayoutGameActivity;
import org.andengine.util.debug.Debug;

import com.example.gamekids.R;
import com.example.manager.SceneManager;

import adapters.CustomGridViewAdapter;
import adapters.Item;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.AdapterView.OnItemClickListener;
public class SublevelSimpleLayout extends SimpleLayoutGameActivity 
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
	
	//Grid
	GridView gridView;
	ArrayList<Item> gridArray = new ArrayList<Item>();
	CustomGridViewAdapter customGridAdapter;

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
		//loadBtn();
		/*
		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		
		aListView = (ListView) findViewById(R.id.listView1);
		
		items = new String[] { "Item 1", "Item 2", "Item 3" , "Item 1", "Item 2", "Item 3" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
		aListView.setAdapter(adapter);
		
		
		HorizontalScrollView scrolView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		scrolView.setHorizontalScrollBarEnabled(false);
		
		//ImageButton b = (ImageButton) findViewById(R.id.imageButton1);
		ImageView b = (ImageView)findViewById(R.id.imageView1);
		
		b.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				System.out.println("ImageBtn Pressed");
			}
		});
		
		*/
		
		
		 //set grid view item
		  Bitmap homeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.menu3);
		  Bitmap userIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.menu3);

		  gridArray.add(new Item(homeIcon,"Level 1"));
		  gridArray.add(new Item(userIcon,"Level 2"));


		  gridView = (GridView) findViewById(R.id.gridView1);
		  customGridAdapter = new CustomGridViewAdapter(this, R.layout.grid_row, gridArray);
		  gridView.setAdapter(customGridAdapter);
		  
		  gridView.setOnItemClickListener(new OnItemClickListener() 
		  {
				@Override
				public void onItemClick(AdapterView<?> parent, View view ,int position, long id) 
				{
					Toast.makeText(SublevelSimpleLayout.this, "Loading Game...", Toast.LENGTH_LONG).show();
					//finishActivity(0);
					SceneManager.setLevelID(position+1);
					SceneManager.getInstance().loadGameSceneA();
					new Handler().postDelayed(new Runnable() 
					{	
						@Override
						public void run() 
						{
							SublevelSimpleLayout.this.finish();							
						}
					},3000);
				}
	        });
	}

	@Override
	public EngineOptions onCreateEngineOptions() 
	{
		//Toast.makeText(this, "Touch the screen to move the particlesystem.", Toast.LENGTH_LONG).show();

		final Camera camera = new Camera(0, 0, SublevelSimpleLayout.CAMERA_WIDTH, SublevelSimpleLayout.CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(SublevelSimpleLayout.CAMERA_WIDTH, SublevelSimpleLayout.CAMERA_HEIGHT), camera);
	}

	@Override
	protected void onCreateResources() throws IOException 
	{
		/*
		this.mParticleTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/badge.png", TextureOptions.BILINEAR);
		this.mParticleTextureRegion = TextureRegionFactory.extractFromTexture(this.mParticleTexture);
		this.mParticleTexture.load();
		*/
		//loadBtn();  *******
	}

	@Override
	protected Scene onCreateScene() throws IOException 
	{
		this.mEngine.registerUpdateHandler(new FPSLogger());

		scene = new Scene();
		scene.getBackground().setColor(0.09804f, 0.6274f, 0.8784f);
		
		
		//final Sprite badgeSprite = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, mParticleTextureRegion, this.getVertexBufferObjectManager());
        //scene.attachChild(badgeSprite);
		//final ButtonSprite buttonSprite = new ButtonSprite(CAMERA_WIDTH/2, CAMERA_HEIGHT - 40, this.mButtonNormalTextureRegion, this.mPressedTextureRegion, this.mDisabledTextureRegion, this.getVertexBufferObjectManager());
		//buttonSprite.setScale(2);
		//scene.registerTouchArea(buttonSprite);
		//scene.attachChild(buttonSprite);
		
		return scene;
	}


	
	@Override
	protected int getLayoutID() 
	{
		return R.layout.custom_grid;
	}

	@Override
	protected int getRenderSurfaceViewID() 
	{
		return R.id.customGrid_rendersurfaceviewID;
	}
	
	
	public void loadBtn()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		this.mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 512, 512);
		this.mButtonNormalTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "button_normal.png");
		this.mPressedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "button_pressed.png");
		this.mDisabledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "button_disabled.png");
		
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