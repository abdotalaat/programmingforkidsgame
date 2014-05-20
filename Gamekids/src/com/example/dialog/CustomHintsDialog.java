package com.example.dialog;

import android.app.Dialog;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customsimpleactivities.NewHighestScore;
import com.example.gamekids.R;
import com.example.manager.ResourcesManager;
import com.example.manager.SceneManager;
import com.example.scene.MazeScene;

public class CustomHintsDialog implements OnClickListener
{
	Dialog dialog;

	public void showDialog()
	{
		ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() 
		{	
			@Override
			public void run() 
			{
				
				dialog = new Dialog(ResourcesManager.getInstance().activity);
				dialog.setContentView(R.layout.buy_hints_layout);
				dialog.setTitle("Buy Hints");
	
				ImageView image1 = (ImageView) dialog.findViewById(R.id.image1);
				ImageView image2 = (ImageView) dialog.findViewById(R.id.image2);
				ImageView image3 = (ImageView) dialog.findViewById(R.id.image3);
				image1.setOnClickListener(CustomHintsDialog.this);
				image2.setOnClickListener(CustomHintsDialog.this);
				image3.setOnClickListener(CustomHintsDialog.this);
				dialog.show();
				
				
				
			}
		});
		
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image1:  // 1 hint=100 unit of score 
			System.out.println("Image1");
			if(MazeScene.highestScore >= 100)
			{	 new NewHighestScore().showDialog("You got a new hint, your score= "+String.valueOf(MazeScene.highestScore-100)) ;
					MazeScene.highestScore-=100;
			}
				
			else 
			new NewHighestScore().showDialog("Ops!you haven't enough score ");
			dialog.dismiss();
			break;
		case R.id.image2:
			System.out.println("Image2");
			if(MazeScene.highestScore >= 200)
					{
						 new NewHighestScore().showDialog("You got two new hint, your score= "+String.valueOf(MazeScene.highestScore-200)) ;
						 MazeScene.highestScore-=200;
					}
			else 
				new NewHighestScore().showDialog("Ops!you haven't enough score ");
			
			dialog.dismiss();
			break;
		case R.id.image3:
			System.out.println("Image3");
			if(MazeScene.highestScore >= 300)
			{
				 new NewHighestScore().showDialog("You got three new hint, your score= "+String.valueOf(MazeScene.highestScore-300)) ;
				 MazeScene.highestScore-=300;
			}
	else 
		new NewHighestScore().showDialog("Ops!you haven't enough score ");
	
	dialog.dismiss();
			break;

		default:
			break;
		}
	}
}
