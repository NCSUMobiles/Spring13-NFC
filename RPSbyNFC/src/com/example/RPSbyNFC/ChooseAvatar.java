package com.example.RPSbyNFC;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseAvatar extends Activity {
	String result = "";
	String self = "", opponent = "";
	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	String uuname;

	String selfname;
	String opponentname;
	Intent i1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		super.onCreate(savedInstanceState);
		Log.i("result", "reached in result");

		setContentView(R.layout.choose_avatar);

		Log.i("result", "after settign content view");
		Intent intent = getIntent();
		result = intent.getStringExtra("result");
		self = intent.getStringExtra("self");
		opponent = intent.getStringExtra("opponent");
		selfname = intent.getStringExtra("selfname");
		opponentname = intent.getStringExtra("opponentname");

		i1 = new Intent(ChooseAvatar.this, ResultActivity.class);
		i1.putExtra("result", result);
		i1.putExtra("self", self);
		i1.putExtra("opponent", opponent);
		i1.putExtra("opponentname", opponentname);
		i1.putExtra("selfname", selfname);
		i1.putExtra("start", "yes");
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setTextColor(Color.BLACK);

		ImageButton b1 = (ImageButton) findViewById(R.id.imageButton1);
		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Resources res = getResources();
					postParameters.add(new BasicNameValuePair("avatar", "1"));
					String ipaddress = res.getString(R.string.ipaddress);
					String response = CustomHttpClient.executeHttpPost(
							"http://" + ipaddress + "/updateavatar.php",
							postParameters);
					System.out.println("afer con" + "http://" + ipaddress
							+ "/updateavatar.php");

					// store the result returned by PHP script that runs MySQL
					// query
					result = response.toString();
				} catch (Exception e) {
					System.out.println("catch exception" + e);
				}

				// here you can start your Activity B.
				startActivity(i1);
				finish();
			}
		});

		ImageButton b2 = (ImageButton) findViewById(R.id.imageButton2);
		b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Resources res = getResources();
					postParameters.add(new BasicNameValuePair("avatar", "2"));
					String ipaddress = res.getString(R.string.ipaddress);
					String response = CustomHttpClient.executeHttpPost(
							"http://" + ipaddress + "/updateavatar.php",
							postParameters);
					System.out.println("afer con" + "http://" + ipaddress
							+ "/updateavatar.php");

					// store the result returned by PHP script that runs MySQL
					// query
					result = response.toString();
				} catch (Exception e) {
					System.out.println("catch exception" + e);
				}

				// here you can start your Activity B.
				startActivity(i1);
				finish();
			}
		});

		ImageButton b3 = (ImageButton) findViewById(R.id.imageButton3);
		b3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					Resources res = getResources();
					postParameters.add(new BasicNameValuePair("avatar", "3"));
					String ipaddress = res.getString(R.string.ipaddress);
					String response = CustomHttpClient.executeHttpPost(
							"http://" + ipaddress + "/updateavatar.php",
							postParameters);
					System.out.println("afer con" + "http://" + ipaddress
							+ "/updateavatar.php");

					// store the result returned by PHP script that runs MySQL
					// query
					result = response.toString();
				} catch (Exception e) {
					System.out.println("catch exception" + e);
				}
				// here you can start your Activity B.
				startActivity(i1);
				finish();
			}
		});

		ImageButton b4 = (ImageButton) findViewById(R.id.imageButton4);
		b4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					Resources res = getResources();
					postParameters.add(new BasicNameValuePair("avatar", "4"));
					String ipaddress = res.getString(R.string.ipaddress);
					String response = CustomHttpClient.executeHttpPost(
							"http://" + ipaddress + "/updateavatar.php",
							postParameters);
					System.out.println("afer con" + "http://" + ipaddress
							+ "/updateavatar.php");

					// store the result returned by PHP script that runs MySQL
					// query
					result = response.toString();
				} catch (Exception e) {
					System.out.println("catch exception" + e);
				}
				// here you can start your Activity B.
				startActivity(i1);
				finish();
			}
		});

		ImageButton b5 = (ImageButton) findViewById(R.id.imageButton5);
		b5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					Resources res = getResources();
					postParameters.add(new BasicNameValuePair("avatar", "5"));
					String ipaddress = res.getString(R.string.ipaddress);
					String response = CustomHttpClient.executeHttpPost(
							"http://" + ipaddress + "/updateavatar.php",
							postParameters);
					System.out.println("afer con" + "http://" + ipaddress
							+ "/updateavatar.php");

					// store the result returned by PHP script that runs MySQL
					// query
					result = response.toString();
				} catch (Exception e) {
					System.out.println("catch exception" + e);
				}
				// here you can start your Activity B.
				startActivity(i1);
				finish();
			}
		});

		ImageButton b6 = (ImageButton) findViewById(R.id.imageButton6);
		b6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					Resources res = getResources();
					postParameters.add(new BasicNameValuePair("avatar", "6"));
					String ipaddress = res.getString(R.string.ipaddress);
					String response = CustomHttpClient.executeHttpPost(
							"http://" + ipaddress + "/updateavatar.php",
							postParameters);
					System.out.println("afer con" + "http://" + ipaddress
							+ "/updateavatar.php");

					// store the result returned by PHP script that runs MySQL
					// query
					result = response.toString();
				} catch (Exception e) {
					System.out.println("catch exception" + e);
				}
				// here you can start your Activity B.
				startActivity(i1);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

}
