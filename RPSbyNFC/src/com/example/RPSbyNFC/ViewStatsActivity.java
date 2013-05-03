package com.example.RPSbyNFC;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

public class ViewStatsActivity extends Activity {

	TextView tv;
	TextView tv1;
	TextView tv2;
	TextView tv3;
	TextView tv4;
	String returnString;
	String deviceid;
	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_stats);
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		deviceid = tm.getDeviceId();
		tv = (TextView) findViewById(R.id.selfname);
		tv.setTextColor(Color.BLACK);
		tv1 = (TextView) findViewById(R.id.textView3);
		tv1.setTextColor(Color.BLACK);
		tv2 = (TextView) findViewById(R.id.textView6);
		tv2.setTextColor(Color.BLACK);
		tv3 = (TextView) findViewById(R.id.textView5);
		tv3.setTextColor(Color.BLACK);
		tv4 = (TextView) findViewById(R.id.textView2);
		tv4.setTextColor(Color.BLACK);
		postParameters.add(new BasicNameValuePair("deviceid", deviceid));

		/* connectvity code */

		String response = null;

		// call executeHttpPost method passing necessary parameters
		System.out.println("before try");
		try {
			System.out.println("in try");
			Resources res = getResources();
			String ipaddress= res.getString(R.string.ipaddress);
			response = CustomHttpClient.executeHttpPost(
					"http://"+ipaddress+"/jsonscriptnfcviewstats.php",
					postParameters);

			System.out.println();
			System.out.println("afer con");

			// store the result returned by PHP script that runs MySQL query
			String result = response.toString();

			System.out.println("Returned by php" + result);
			String sb = new String(result);
			String sb1 = sb.substring(1, sb.length() - 2);
			String sbarr[] = sb1.split("\"\" \"\"");

			for (int k = 0; k < sbarr.length; k++)
				System.out.println(sbarr[k]);

			int i = Integer.parseInt(sbarr[0]);
			int j = Integer.parseInt(sbarr[1]);
			
			tv4.setText("Hi "+ sbarr[3]);
			tv.setText("Total Games Played :\t" + i);
			tv1.setText("Total Games Won : \t" + j);
			tv3.setText("Lucky Weapon  :\t" + sbarr[2]);
			tv2.setText("Favorite Weapon :\t" + sbarr[4]);

		}

		catch (Exception e) {
			Log.e("log_tag", "Error in http connection!!" + e.toString());
		}
	}

}
