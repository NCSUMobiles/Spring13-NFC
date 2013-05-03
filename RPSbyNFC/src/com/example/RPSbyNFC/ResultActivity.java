package com.example.RPSbyNFC;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.RPSbyNFC.R;

public class ResultActivity extends Activity {
	String result;
	String self;
	String opponent;
	String selfname;
	String opponentname;
	String oppResult;
	String start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		Intent intent = getIntent();
		result = intent.getStringExtra("result");
		self = intent.getStringExtra("self");
		opponent = intent.getStringExtra("opponent");
		selfname = intent.getStringExtra("selfname");
		opponentname = intent.getStringExtra("opponentname");
		start = intent.getStringExtra("start");
		if(result.equals("win"))
		oppResult = "lose";
		else if(result.equals("lose"))
			oppResult = "win";
		else oppResult = result;

		Log.i("game_result in result", result + " " + opponent + " " + self + " "+ opponentname + " " + selfname);

		int res = getResources().getIdentifier(self+result, "drawable",
				this.getPackageName());
		ImageView selfIB = (ImageView) findViewById(R.id.self);
		selfIB.setImageResource(res);

		int resOpponent = getResources().getIdentifier(opponent+oppResult, "drawable",
				this.getPackageName());
		ImageView opponentIB = (ImageView) findViewById(R.id.opponent);
		opponentIB.setImageResource(resOpponent);
		TextView tv1 = (TextView) findViewById(R.id.selfname);
		tv1.setTextColor(Color.BLACK);

		TextView tv = (TextView) findViewById(R.id.finalResult);
		tv.setTextColor(Color.BLACK);
		tv.setText("You " + result);

		TextView opponame = (TextView) findViewById(R.id.opponentname);
		opponame.setTextColor(Color.BLACK);
		opponame.setText(opponentname);

		Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
		if(start.equals("yes"))
		{
		playAgainButton.setText("Beam mode");	
		}
		else
		{
			playAgainButton.setText("Play Again");
		}
		
		playAgainButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// HARDCODED TO START BEAM ACTIVITY
				Intent playagain=new Intent(ResultActivity.this,BeamWeaponActivity.class);
				playagain.putExtra("start", "no");
				playagain.putExtra("selfname", selfname);
				startActivity(playagain);
				finish();
			}
		});

		Button highScoresButton = (Button) findViewById(R.id.highScoresButton);
		highScoresButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent hs=new Intent(ResultActivity.this,HighScoresActivity.class);
				//hs.putExtra("selfname", selfname);
				
				startActivity(hs);
				finish();
			}
		});

		Button viewStatsButton = (Button) findViewById(R.id.viewStatsButton);
		viewStatsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent vs=new Intent(ResultActivity.this,ViewStatsActivity.class);
				//vs.putExtra("selfname", selfname);
				startActivity(vs);
				finish();
			}
		});

		Button quitButton = (Button) findViewById(R.id.quitButton);
		quitButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(ResultActivity.this)
		       .setIcon(android.R.drawable.ic_dialog_alert)
		       .setTitle("Closing RockPaperScissor")
		       .setMessage("Are you sure you want to quit?")
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener()
		   {
		       @Override
		       public void onClick(DialogInterface dialog, int which) {
		    	   Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();    
		       }

		   })
		   .setNegativeButton("No", null)
		   .show();
				
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this)
		       .setIcon(android.R.drawable.ic_dialog_alert)
		       .setTitle("Closing RockPaperScissor")
		       .setMessage("Are you sure you want to quit?")
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener()
		   {
		       @Override
		       public void onClick(DialogInterface dialog, int which) {
		    	   Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();    
		       }

		   })
		   .setNegativeButton("No", null)
		   .show();
			
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

}
