package com.example.RPSbyNFC;

import java.util.ArrayList;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class BeamWeaponActivity extends Activity implements
		CreateNdefMessageCallback, OnNdefPushCompleteCallback {

	ImageButton rockWeapon;
	ImageButton paperWeapon;
	ImageButton scissorWeapon;
	String beamWeapon;
	NfcAdapter mNfcAdapter;
	String payload[];
	String result;
	Intent i;
	int randNum;
	String deviceid;
	Random randomGenerator = new Random();
	ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// StrictMode is most commonly used to catch accidental disk or network
		// access on the application's main thread
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beam_weapon);

		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		mProgressBar.setVisibility(View.GONE);

		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		deviceid = tm.getDeviceId();
		Log.i("device id again", deviceid);
		// Intent intent = getIntent();

		// deviceid = intent.getStringExtra("deviceid");

		beamWeapon = new String();
		beamWeapon = "";

		// rockWeapon = (ImageButton) findViewById(R.id.rock);
		// paperWeapon = (ImageButton) findViewById(R.id.paper);
		// scissorWeapon = (ImageButton) findViewById(R.id.scissors);
		ImageButton ib[] = new ImageButton[3];
		int randImages = randomGenerator.nextInt(100000);
		Log.i("rand number", "" + randImages);

		int resRock = getResources().getIdentifier("rock", "drawable",
				this.getPackageName());
		int resPaper = getResources().getIdentifier("paper", "drawable",
				this.getPackageName());
		int resScissor = getResources().getIdentifier("scissor", "drawable",
				this.getPackageName());

		ib[0] = (ImageButton) findViewById(R.id.rock);
		ib[1] = (ImageButton) findViewById(R.id.paper);
		ib[2] = (ImageButton) findViewById(R.id.scissors);

		ib[randImages % 3].setImageResource(resRock);
		rockWeapon = ib[randImages % 3];
		ib[(randImages + 1) % 3].setImageResource(resPaper);
		paperWeapon = ib[(randImages + 1) % 3];
		ib[(randImages + 2) % 3].setImageResource(resScissor);
		scissorWeapon = ib[(randImages + 2) % 3];

		rockWeapon.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				beamWeapon = "rock";
				Log.i("RPS", "Beaming rock");

			}
		});

		// HOW TO ENSURE other Listeners quit after getting one choice of weapon
		paperWeapon.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				beamWeapon = "paper";
				Log.i("RPS", "Beaming paper");

			}
		});
		scissorWeapon.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				beamWeapon = "scissor";
				Log.i("RPS", "Beaming scissor");

			}
		});

		// Check for available NFC Adapter
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
			Toast.makeText(this, "Sorry, NFC is not available on this device",
					Toast.LENGTH_SHORT).show();
		} else {
			// Register callback to set NDEF message
			mNfcAdapter.setNdefPushMessageCallback(this, this);
			// Register callback to listen for message-sent success
			mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
		}

	}

	private static final String MIME_TYPE = "application/com.tapped.nfc.beam";
	private static final String PACKAGE_NAME = "com.example.RPSbyNFC";

	/**
	 * Implementation for the CreateNdefMessageCallback interface
	 */
	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		randNum = randomGenerator.nextInt();
		String rand = randNum + "";
		String text = beamWeapon + "," + deviceid + "," + rand;
		Log.i("RPS", "NDEF msg created" + text);
		NdefMessage msg = new NdefMessage(new NdefRecord[] {
				NfcUtils.createRecord(MIME_TYPE, text.getBytes()),
				NdefRecord.createApplicationRecord(PACKAGE_NAME) });
		return msg;
	}

	private static final int MESSAGE_SENT = 1;

	/** This handler receives a message from onNdefPushComplete */
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_SENT:
				// Toast.makeText(getApplicationContext(), "Message sent!",
				// Toast.LENGTH_LONG).show();
				Log.i("RPS", "Message sent handler");

				mProgressBar.setVisibility(View.VISIBLE);
				break;
			}
		}
	};

	/**
	 * Implementation for the OnNdefPushCompleteCallback interface
	 */
	@Override
	public void onNdefPushComplete(NfcEvent arg0) {
		// A handler is needed to send messages to the activity when this
		// callback occurs, because it happens from a binder thread

		mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
		new Thread() {
			public void run() {
				try {
					i = new Intent(BeamWeaponActivity.this, Result1.class);
					// Thread.sleep(10000);
					new MakeDBConnection2().execute(randNum + "");
					while (result == null)
						;
					Thread.sleep(3000);
					startActivity(i);
					finish();
				} catch (InterruptedException e) {

				}
			}
		}.start();

		Log.i("RPS", "on ndefpushcomplete");
	}

	@Override
	public void onNewIntent(Intent intent) {
		// onResume gets called after this to handle the intent
		setIntent(intent);
		Log.i("RPS", "new intent created");
	}

	@Override
	public void onResume() {
		super.onResume();
		// Check to see that the Activity started due to an Android Beam
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			try {
				processIntent(getIntent());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("RPS", "resume and process intent");
		}
	}

	/**
	 * Parses the NDEF Message from the intent and toast to the user
	 * 
	 * @throws InterruptedException
	 */
	void processIntent(Intent intent) throws InterruptedException {

		Parcelable[] rawMsgs = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		// in this context, only one message was sent over beam
		NdefMessage msg = (NdefMessage) rawMsgs[0];
		Log.i("recd msg", msg.getRecords()[0].getPayload().toString());
		// record 0 contains the MIME type, record 1 is the AAR, if present
		payload = new String(msg.getRecords()[0].getPayload()).split(",");
		String payload2[];
		payload2 = new String(payload[1]).split(",");
		Log.i("RPS", "Received beam msg" + payload[0] + " opponent "
				+ payload2[0] + " result " + payload + " result " + beamWeapon);

		new Thread() {
			public void run() {
				try {
					i = new Intent(BeamWeaponActivity.this, Result1.class);
					while (beamWeapon.isEmpty())
						;
					if (!beamWeapon.isEmpty() && !payload[1].isEmpty()) {
						new MakeDBConnection().execute(payload[2], payload[0],
								beamWeapon, payload[1]);
						Log.i("sent both to db ", "initiator " + payload[0]
								+ " opponent " + payload[1] + " result "
								+ payload[2] + " result " + beamWeapon);
					}
					while (result == null)
						;
					Thread.sleep(5000);
					startActivity(i);
					finish();
				} catch (InterruptedException e) {

				}
			}
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.beam_weapon, menu);
		return true;
	}

	private class MakeDBConnection extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			Log.i("AsyncTask", "onPreExecute");
		}

		@Override
		protected Void doInBackground(String... pp) {
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

			// define the parameter
			postParameters.add(new BasicNameValuePair("id", pp[0]));
			postParameters.add(new BasicNameValuePair("tag1", pp[1])); //
			postParameters.add(new BasicNameValuePair("tag2", pp[2]));
			postParameters.add(new BasicNameValuePair("oppdeviceid", pp[3]));
			postParameters.add(new BasicNameValuePair("deviceid", deviceid));

			String response = null;

			// call executeHttpPost method passing necessary parameters
			System.out.println("before try http post");
			try {
				System.out.println("in try http post");
				Resources res = getResources();
				String ipaddress = res.getString(R.string.ipaddress);
				Log.i("ipaddress", ipaddress);
				response = CustomHttpClient.executeHttpPost("http://"
						+ ipaddress + "/jsonscriptnfcbeamaccept.php",
						postParameters);
				System.out.println("afer con http post");

				// store the result returned by PHP script that runs MySQL query
				result = response.toString();
				Log.i("update returned", result);
				// result = result.substring(1, result.length() - 2);
				String splitresult[] = result.split(",");
				// parse json data
				String self = "";
				String opponent = "";
				String resultString = "";
				try {
					/*
					 * JSONArray jArray = new JSONArray(result); // for (int i =
					 * 0; i < jArray.length(); i++) { JSONObject json_data =
					 * jArray.getJSONObject(0); self =
					 * json_data.getString("self"); opponent =
					 * json_data.getString("opp"); resultString =
					 * json_data.getString("res");
					 */
					self = splitresult[2];
					opponent = splitresult[1];
					resultString = splitresult[0];
					Log.i("game_result from php", resultString + " " + self
							+ " " + opponent);
					/*
					 * if (resultString.equals("win")) resultString = "lose";
					 * else if (resultString.equals("lose")) resultString =
					 * "win";
					 */
					// }
				} catch (Exception e) {
					Log.e("log_tag", "Error parsing data " + e.toString());
				}

				Log.i("game_result beam accept", resultString + " " + pp[1]
						+ " " + pp[2] + " " + opponent + " " + self);
				i.putExtra("result", resultString);
				i.putExtra("opponent", pp[1]);
				i.putExtra("self", pp[2]);
				i.putExtra("opponentname", opponent);
				i.putExtra("selfname", self);
				i.putExtra("start", "no");

			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection!!" + e.toString());
			}
			return null;
		}

	};

	private class MakeDBConnection2 extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			Log.i("AsyncTask", "onPreExecute");
		}

		@Override
		protected Void doInBackground(String... num) {
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

			// define the parameter
			postParameters.add(new BasicNameValuePair("id", num[0]));
			postParameters.add(new BasicNameValuePair("deviceid", deviceid));

			String response = null;

			// call executeHttpPost method passing necessary parameters
			System.out.println("before try http post2");
			try {
				System.out.println("in try http post2");
				Resources res = getResources();
				String ipaddress = res.getString(R.string.ipaddress);
				Log.i("ipaddress", ipaddress);
				response = CustomHttpClient.executeHttpPost("http://"
						+ ipaddress + "/jsonscriptnfcbeamsent.php",
						postParameters);
				result = response.toString();
				Log.i("result cannot parse= ", result);
				// parse json data
				String self = "";
				String opponent = "";
				String resultString = "";
				String selfname = "";
				String opponentname = "";
				try {
					// JSONArray jArray = new JSONArray(result);
					// for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = new JSONObject(result);// jArray.getJSONObject(0);
					self = json_data.getString("tag1");
					opponent = json_data.getString("tag2");
					resultString = json_data.getString("result");
					selfname = json_data.getString("user1");
					opponentname = json_data.getString("user2");
					if (resultString.equals("win"))
						resultString = "lose";
					else if (resultString.equals("lose"))
						resultString = "win";
					// }
				} catch (JSONException e) {
					Log.e("log_tag", "Error parsing data " + e.toString());
				}

				Log.i("game_result", resultString + " " + opponent + " " + self
						+ " " + opponentname + " " + selfname);
				i.putExtra("result", resultString);
				i.putExtra("opponent", opponent);
				i.putExtra("self", self);
				i.putExtra("opponentname", opponentname);
				i.putExtra("selfname", selfname);
				i.putExtra("start", "no");
			}

			catch (Exception e) {
				Log.e("log_tag", "Error in http connection!!" + e.toString());
			}
			return null;
		}

	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(startMain);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
