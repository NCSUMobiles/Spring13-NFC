package com.example.RPSbyNFC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class NFCActivity extends Activity {

	String name = "";
	String uuname = new String();
	NfcAdapter mNfcAdapter;
	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	ArrayList<NameValuePair> postParameters1 = new ArrayList<NameValuePair>();
	String response;
	String s1[] = new String[2];
	String result;
	Intent i;
	String content;
	PendingIntent pendingIntent;
	String deviceid = null;
	String start;
	String opponentName;
	ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		super.onCreate(savedInstanceState);
		// Utility.context = this;
		setContentView(R.layout.activity_nfc);

		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		mProgressBar.setVisibility(View.GONE);

		pendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this.getApplicationContext(), this.getClass()), 0);

		Intent intent = getIntent();
		start = intent.getStringExtra("start");

		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		deviceid = tm.getDeviceId();
		// TextView tv = (TextView) findViewById(R.id.finalResult);
		// tv.setTextColor(Color.BLACK);
		try {
			System.out.println("in try of onCreate");
			Resources res = getResources();
			postParameters1.add(new BasicNameValuePair("deviceid", deviceid));
			String ipaddress = res.getString(R.string.ipaddress);
			response = CustomHttpClient.executeHttpPost("http://" + ipaddress
					+ "/getvibe.php", postParameters1);
			System.out.println("afer con" + "http://" + ipaddress
					+ "/getvibe.php");

			// store the result returned by PHP script that runs MySQL query
			result = response.toString();
			Log.i("log_tag", "result is parsed data" + result);
			// String result1 = result.substring(1, result.length() - 2);
			String splitresult[] = result.split(",");
			// parse json data

			String avatar = "";
			avatar = splitresult[2].substring(0, splitresult[2].length() - 1);
			uuname = splitresult[0];
			opponentName = splitresult[1];

			Log.i("game_result from php", uuname + " " + avatar + " "
					+ opponentName);
			System.out.println("uuname in NFCActivity " + uuname);

			TextView finalres = (TextView) findViewById(R.id.finalResult);
			finalres.setTextColor(Color.BLACK);
			finalres.setText("Select weapon to attack.\nVibe sent by your Opponent "
					+ opponentName + " :");
			int ress = 0;
			ImageView selfIB;
			selfIB = (ImageView) findViewById(R.id.imageView1);
			int casesw = Integer.parseInt(avatar);
			System.out.println("value of swiching var " + casesw);
			switch (casesw) {
			case 4:
				ress = getResources().getIdentifier("boylast1", "drawable",
						this.getPackageName());
				// selfIB=(ImageView)findViewById(R.id.imageView1);
				selfIB.setImageResource(ress);
				break;

			case 5:
				ress = getResources().getIdentifier("boylast2", "drawable",
						this.getPackageName());
				System.out.println("1st stmt img ress " + ress);

				System.out.println("2nd stmt" + selfIB);
				selfIB.setImageResource(ress);

				System.out.println("3rd stmt");
				break;

			case 6:
				ress = getResources().getIdentifier("boylast3", "drawable",
						this.getPackageName());
				// selfIB=(ImageView)findViewById(R.id.imageView1);
				selfIB.setImageResource(ress);
				break;

			case 1:
				ress = getResources().getIdentifier("g1", "drawable",
						this.getPackageName());
				// selfIB=(ImageView)findViewById(R.id.imageView1);
				selfIB.setImageResource(ress);
				break;

			case 2:
				ress = getResources().getIdentifier("g2", "drawable",
						this.getPackageName());
				// selfIB=(ImageView)findViewById(R.id.imageView1);
				selfIB.setImageResource(ress);
				break;

			case 3:
				ress = getResources().getIdentifier("g3", "drawable",
						this.getPackageName());
				// selfIB=(ImageView)findViewById(R.id.imageView1);
				selfIB.setImageResource(ress);
				break;
			}

		} catch (Exception e) {
			Log.e("log_tag", "in 2nd catch " + e.toString());
		}
		// setContentView(R.layout.activity_nfc);

		Bundle extras = getIntent().getExtras();
		Log.i("nfcact ", "reached nfc act");
		if (extras != null) {
			if (name != null)
				postParameters.add(new BasicNameValuePair("username", uuname));
		}

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
			Toast.makeText(this, "Sorry, NFC is not available on this device",
					Toast.LENGTH_SHORT).show();
			finish();
		}
		resolveIntent(getIntent());
	}

	@Override
	public void onNewIntent(Intent intent) {
		// onResume gets called after this to handle the intent
		setIntent(intent);
		resolveIntent(intent);
	}

	void resolveIntent(Intent intent) {

		HashMap<String, String> data = null;
		String action = intent.getAction();
		if ((action != null)
				&& (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED))) {
			Log.i("new", "Foreground task dispatch");

			processReadIntent(intent);
		} else {
			Log.i("NEW", "Background task dispatch");
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				Set<String> keys = extras.keySet();
				data = new HashMap<String, String>();
				for (String key : keys) {
					data.put(key, extras.getString(key));
				}
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		// if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())
		// || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent()
		// .getAction())) {
		//
		// processReadIntent(getIntent());
		// }
		mNfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);

	}

	@Override
	public void onPause() {
		super.onPause();
		mNfcAdapter.disableForegroundDispatch(this);
		// Utility.LogInfo("DrinkOrdersActivity.onPause");
	}

	private static final String MIME_TYPE = "application/com.tapped.nfc.tag";

	private class MakeDBConnection extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			Log.i("AsyncTask", "onPreExecute");
		}

		@Override
		protected Void doInBackground(String... pp) {
			// define the parameter
			postParameters.add(new BasicNameValuePair("readtag", pp[0]));

			for (int i = 0; i < postParameters.size(); i++) {
				System.out.println(postParameters.get(i));
			}

			String response = null;
			// call executeHttpPost method passing necessary parameters
			System.out.println("before try");
			try {
				System.out.println("in try");
				Resources res = getResources();
				String ipaddress = res.getString(R.string.ipaddress);
				response = CustomHttpClient.executeHttpPost("http://"
						+ ipaddress + "/jsonscriptnfc3.php", postParameters);
				System.out.println("afer con" + "http://" + ipaddress
						+ "/jsonscriptnfc3.php");

				// store the result returned by PHP script that runs MySQL query
				result = response.toString();
				Log.i("log_tag", "parsed data" + result);
				String[] splitResult = result.split("\"\"");
				// parse json data
				String self = splitResult[2].substring(0,
						splitResult[2].length() - 2);
				String opponent = splitResult[1];
				String resultString = splitResult[0].substring(1);

				Log.i("log_tag", "parsed data" + self + " " + opponent + " "
						+ resultString);

				Log.i("game_result nfcact", resultString + " " + opponent + " "
						+ self + " " + opponentName + " " + uuname);
				i.putExtra("result", resultString);
				i.putExtra("opponent", opponent);
				i.putExtra("self", self);
				i.putExtra("selfname", uuname);
				i.putExtra("opponentname", opponentName);
				i.putExtra("start", "yes");
			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection!!" + e.toString());
			}
			return null;
		}

	};

	public void processReadIntent(Intent intent) {
		List<NdefMessage> intentMessages = NfcUtils
				.getMessagesFromIntent(intent);
		List<String> payloadStrings = new ArrayList<String>(
				intentMessages.size());

		for (NdefMessage message : intentMessages) {
			for (NdefRecord record : message.getRecords()) {
				byte[] payload = record.getPayload();
				String payloadString = new String(payload);

				if (!TextUtils.isEmpty(payloadString))
					payloadStrings.add(payloadString);
			}
		}
		mProgressBar.setVisibility(View.VISIBLE);
		
		if (!payloadStrings.isEmpty()) {
			content = TextUtils.join(",", payloadStrings);
			new Thread() {
				public void run() {
					try {

						i = new Intent(NFCActivity.this, Result1.class);
						//mProgressBar.setVisibility(View.VISIBLE);
						
						new MakeDBConnection().execute(content);
						Thread.sleep(5000);
						
						startActivity(i);
						finish();
					} catch (InterruptedException e) {

					}
				}
			}.start();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nfc, menu);
		return true;
	}

}
