package com.example.RPSbyNFC;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Toast;

import com.example.RPSbyNFC.R;

public class MainActivity extends Activity {

	String imei;
	String start="no";
	NfcAdapter mNfcAdapter;
	String content;
	Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
			Toast.makeText(this, "Sorry, NFC is not available on this device",
					Toast.LENGTH_SHORT).show();
			finish();
		}
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())
				|| NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent()
						.getAction())) {

			processReadIntent(getIntent());
		}

		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		imei = tm.getDeviceId();

		Timer welcometimer = new Timer();
		welcometimer.schedule(new TimerTask() {
			public void run() {
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("deviceid", imei));

				try {
					System.out.println("in try http post");
					Resources res = getResources();
					String ipaddress = res.getString(R.string.ipaddress);
					Log.i("ipaddress", ipaddress);
					String response = CustomHttpClient.executeHttpPost(
							"http://" + ipaddress
									+ "/jsonscriptcheckregister.php",
							postParameters);
					System.out.println("afer con http post");

					String result = response.toString().substring(0,response.length()-1);
					Log.i("log_tag","imei" + imei+" "+result+ " helo");
					
					if (result.equals("found")) {
						Log.i("log_tag"," beam");
						if(start.equals("yes"))
						{
							i = new Intent(MainActivity.this,NFCActivity.class);	
						}
						else
						{
							i = new Intent(MainActivity.this,BeamWeaponActivity.class);
						}
						i.putExtra("deviceid", imei);
						i.putExtra("start",start);
						startActivity(i);
						finish();

					} else {
						Log.i("log_tag"," register");
						i = new Intent(MainActivity.this, Register.class);
						i.putExtra("start",start);
						startActivity(i);
						finish();
					}

				} catch (Exception e) {
					Log.e("log_tag",
							"Error in http connection!!" + e.toString());
				}
			}
		}, 3000);

	}

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

		if (!payloadStrings.isEmpty()) {
			content = TextUtils.join(",", payloadStrings);
			Log.i("start payload",content);
			if(content.contains("start"))
				start="yes";
			else
				start="no";
		}

	}

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


