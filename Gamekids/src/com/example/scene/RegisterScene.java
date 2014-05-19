package com.example.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.debug.Debug;

import com.example.base.BaseScene;
import com.example.manager.ResourcesManager;
import com.example.manager.SceneManager;
import com.example.manager.SceneManager.SceneType;
import com.facebook.*;
import com.facebook.model.*;

public class RegisterScene extends BaseScene  implements OnClickListener
{

	private final float WIDTH  = 400;
	private final float HEIGHT = 240;
	

	
	@Override
	public void createScene() 
	{
		createBackground();
	}

	@Override
	public void onBackKeyPressed() 
	{
				
	}

	@Override
	public SceneType getSceneType() 
	{
		return null;
	}

	@Override
	public void disposeScene() 
	{
		
	}
	
	private void createBackground()
	{
		attachChild(new Sprite(WIDTH , HEIGHT, resourcesManager.menu_background_facebook , vbom)
		{
    		@Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
    		{
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
		});
		
		loadBtn();	
	}
	
	public void loadBtn()
	{
		ButtonSprite facebookBtn = new ButtonSprite(WIDTH , HEIGHT, resourcesManager.faceNormalTextureRegion ,resourcesManager.facePressedTextureRegion , vbom , RegisterScene.this);
		attachChild(facebookBtn);
		registerTouchArea(facebookBtn);
	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,float pTouchAreaLocalY) 
	{
		//loginFacebook();
		SceneManager.getInstance().createMenuSceneFromFacebook();
	}
	
	
	public void loginFacebook()
	{
		Session.openActiveSession(resourcesManager.activity, true, new Session.StatusCallback()
		{

			// callback when session changes state
			@SuppressWarnings("deprecation")
			@Override
			public void call(Session session, SessionState state,
					Exception exception)
			{
				
				if (session.isOpened())
				{
					System.out.println("sssssssssssssssssssssss");

					// make request to the /me API
					Request.executeMeRequestAsync(session,
							new Request.GraphUserCallback()
							{

								// callback after Graph API response with user
								// object
								@Override
								public void onCompleted(GraphUser user, Response response)
								{
									if (user != null)
									{
										System.out.println("--->>> "+user.getFirstName());
										
									} 
									else 
									{
										System.out.println("--->>> ");
									}
								}
							});
				}
			}
		});
	}
	
	

}
