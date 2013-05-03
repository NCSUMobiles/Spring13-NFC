package com.example.RPSbyNFC;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class HighScoresActivity extends Activity {

	TextView tv;

	String returnString;
	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
String selfname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_scores);
//		Intent intent = getIntent();
//		selfname = intent.getStringExtra("selfname");
		
		tv = (TextView) findViewById(R.id.selfname);
		
		tv.setTextColor(Color.BLACK);
		TextView tv2 = (TextView) findViewById(R.id.finalResult);
		
		tv2.setTextColor(Color.BLACK);
		//postParameters.add(new BasicNameValuePair("username", selfname));

		String response = null;

		// call executeHttpPost method passing necessary parameters
		System.out.println("before try");
		try {
			System.out.println("in try");
			Resources resrc = getResources();
			String ipaddress= resrc.getString(R.string.ipaddress);
			response = CustomHttpClient.executeHttpPost(
					"http://"+ipaddress+"/highscores.php", postParameters);

			System.out.println();
			System.out.println("afer con");

			// store the result returned by PHP script that runs MySQL query
			String result = response.toString();

			System.out.println("Returned by php" + result);
			String sb = new String(result);
			String sb1 = sb.substring(1, sb.length() - 2);
			String sbarr[] = sb1.split("\"\"  \"\"");

			String res = "Rank \t Player \t  Score\n";

			for (int k = 0; k < 3; k = k + 1) {
				String twoo[] = sbarr[k].split("\"\" \"\"");
				res += (k + 1) + ".\t\t\t" + twoo[0] + "\t\t\t\t\t" + twoo[1]
						+ "\n";
			}

			tv.setText("\n" + res);
		}

		catch (Exception e) {
			Log.e("log_tag", "Error in http connection!!" + e.toString());
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_stats, menu);
		return true;
	}

}
