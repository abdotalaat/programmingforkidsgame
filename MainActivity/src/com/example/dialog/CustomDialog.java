package com.example.dialog;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamekids.R;
import com.example.manager.ResourcesManager;

public class CustomDialog {

	public static void showDialog(final String title,final String msg,final String okBtnMsg,final int senderID)
	{
		ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() 
		{	
			@Override
			public void run() 
			{
				
				final Dialog dialog = new Dialog(ResourcesManager.getInstance().activity);
				dialog.setContentView(R.layout.competiondialog);
				dialog.setTitle(title);
				
	 
				// set the custom dialog components - text, image and button
				TextView text = (TextView) dialog.findViewById(R.id.text);
				text.setText(msg);
				ImageView image = (ImageView) dialog.findViewById(R.id.image);
				image.setImageResource(R.drawable.ic_launcher);
	 
				Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonCancel);
				Button getHintsButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
				//if button is clicked, close the custom dialog
				getHintsButton.setText(okBtnMsg);
				dialogButton.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						dialog.dismiss();
					}
				});
				
				getHintsButton.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						switch (senderID) {
						case 1:     //hints
							//openHintsScene
							//callFunctionofHints
							break;

						default:
							break;
						}
						
					}
				});
	 
				dialog.show();
				
			}
		});
		
	}
}
