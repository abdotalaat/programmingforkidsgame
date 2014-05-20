package com.example.customsimpleactivities;

import android.app.Dialog;

import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamekids.R;
import com.example.manager.ResourcesManager;
import com.example.manager.SceneManager;
import com.example.scene.MazeScene;

public class NewHighestScore 
{
	Dialog dialog;

	public  void showDialog(final String text)
	{
		ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() 
		{	
			@Override
			public void run() 
			{
				
				dialog = new Dialog(ResourcesManager.getInstance().activity);
				dialog.setContentView(R.layout.new_score_layout);
				TextView txt= (TextView)dialog.findViewById(R.id.scoreTxt);
				txt.setText(text);
				Button okBtn=(Button)dialog.findViewById(R.id.okBtnId);
				okBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.show();
				
				
				
			}
		});
		
	}
}
