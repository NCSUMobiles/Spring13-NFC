package com.example.RPSbyNFC;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity {
	EditText uname;

	String start;
	Intent i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		Button registerButton = (Button) findViewById(R.id.playAgainButton);
		registerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				String imei = tm.getDeviceId();

				Intent intent = getIntent();
				start= intent.getStringExtra("start");
				
				uname = (EditText) findViewById(R.id.editText2);
				String u = uname.getText().toString();

				reg(u,imei);
				System.out.println("registered hopefully");

				System.out.println("In register" + u);

				if(start.equals("yes"))
				{
					i = new Intent(Register.this,NFCActivity.class);	
				}
				else
				{
					i = new Intent(Register.this,BeamWeaponActivity.class);
				}
				i.putExtra("deviceid", imei);
				i.putExtra("start",start);
				startActivity(i);
				finish();
				
			}
		});
		

	}


	private static final String MIME_TYPE = "application/com.tapped.nfc.tag";

	protected Void reg(String... pp) {
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		// define the parameter
		postParameters.add(new BasicNameValuePair("username", pp[0]));
		postParameters.add(new BasicNameValuePair("deviceid", pp[1]));
		String response = null;

		// call executeHttpPost method passing necessary parameters
		System.out.println("before try");
		try {
			System.out.println("in try");
			Resources res = getResources();
			String ipaddress= res.getString(R.string.ipaddress);
			response = CustomHttpClient.executeHttpPost(
					"http://"+ipaddress+"/jsonscriptnfc2.php",
					postParameters);
			System.out.println("afer con");

			// store the result returned by PHP script that runs MySQL query
			String result = response.toString();
			System.out.println(result);
		}

		catch (Exception e) {
			Log.e("log_tag", "Error in http connection!!" + e.toString());
		}
		return null;
	}

	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			Intent intent=new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
