package com.example.RPSbyNFC;

//import android..R;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.RPSbyNFC.R;

public class Result1 extends Activity {

	String result;
	String self;
	String opponent;
	int count = 0;
	Intent i1;
	String selfname;
	String opponentname;
	String uuname;
	String start;
	
	private void blink() {
		if (count < 10) {
			final Handler handler = new Handler();
			new Thread(new Runnable() {
				@Override
				public void run() {
					int timeToBlink = 600; // in milisseconds
					try {
						Thread.sleep(timeToBlink);
					} catch (Exception e) {
					}
					handler.post(new Runnable() {
						@Override
						public void run() {
							TextView txt = (TextView) findViewById(R.id.finalResult);
							// txt.setVisibility(View.INVISIBLE);
							if (txt.getVisibility() == View.VISIBLE) {
								txt.setVisibility(View.INVISIBLE);
							} else {
								txt.setVisibility(View.VISIBLE);

								txt.setText("You " + result);
							}
							count++;
							blink();
						}
					});
				}
			}).start();
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/*
		 * StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		 * .detectDiskReads().detectDiskWrites().detectNetwork()
		 * .penaltyLog().build());
		 */
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result1);

		Intent intent = getIntent();
		result = intent.getStringExtra("result");
		self = intent.getStringExtra("self");
		opponent = intent.getStringExtra("opponent");
		selfname = intent.getStringExtra("selfname");
		opponentname = intent.getStringExtra("opponentname");
		start = intent.getStringExtra("start");
		
		TextView finalRes=(TextView)findViewById(R.id.finalResult);
		
		finalRes.setTextColor(Color.BLACK);
		finalRes.setText("You "+result);
		
		Log.i("game_result in result", result + " " + opponent + " " + self
				+ " " + opponentname + " " + selfname);

		int res;
		ImageView selfIB = (ImageView) findViewById(R.id.imageView1);
		if ((self.equals("rock") && opponent.equals("paper"))
				|| (self.equals("paper") && opponent.equals("rock"))) {
			res = getResources().getIdentifier("paper_wins", "drawable",
					this.getPackageName());

			selfIB.setImageResource(res);
		} else if ((self.equals("rock") && opponent.equals("scissor"))
				|| (self.equals("scissor") && opponent.equals("rock"))) {
			res = getResources().getIdentifier("rock_wins", "drawable",
					this.getPackageName());

			selfIB.setImageResource(res);
		} else if ((self.equals("paper") && opponent.equals("scissor"))
				|| (self.equals("scissor") && opponent.equals("paper"))) {
			res = getResources().getIdentifier("scissor_win", "drawable",
					this.getPackageName());

			selfIB.setImageResource(res);
		} else {
			Log.i("result", "draw");
			//draw wala image
			res = getResources().getIdentifier("draw", "drawable",this.getPackageName());
			selfIB.setImageResource(res);

		}

		blink();
		try {

			Timer welcometimer1 = new Timer();

			welcometimer1.schedule(new TimerTask() {
				public void run() {
					if(start.equals("yes"))
					{
						i1 = new Intent(Result1.this, ChooseAvatar.class);	
					}
					else
					{
						i1 = new Intent(Result1.this, ResultActivity.class);
					}
					
					//i1.putExtra("result1", "win");
					//i1.putExtra("self1", "rock");
					// here you can start your Activity B.
					i1.putExtra("result", result);
					i1.putExtra("opponent", opponent);
					i1.putExtra("self", self);
					i1.putExtra("opponentname", opponentname);
					i1.putExtra("selfname", selfname);
					i1.putExtra("start",start);
					
					startActivity(i1);
					finish();
				}
			}, 9000);

		} catch (Exception e) {
			System.out.println("Error in Result1 " + e);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

}