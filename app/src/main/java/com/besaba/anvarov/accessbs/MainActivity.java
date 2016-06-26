package com.besaba.anvarov.accessbs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends FragmentActivity {

	private static final String msStart = "s";
	private static final String msEnd = "e";
	private static final String msWork = " w ";
	private static final String SITE = "SITE_�";
	private static final String SENT = "sent";
	private static final String operatorMTS = "25001";
	private String phoneNumber = "+79104127358";
	private static final String server = "1 ";
	static final String APP_PREFERENCES = "mysettings"; // ���������
	static final String APP_PREFERENCES_COUNTER = "counter"; // ���������
	SharedPreferences mSettings;
	//final String LOG_TAG = "myLogs";
	final String DIR_SD = "AccessBS";
	final String FILENAME_SD = "Log_SMS";
	InputStream oStream;
	Button btBuf;
	ToggleButton btStart, btEnd; // ��� �������� � ���������� Button
	DataWork BufWork = new DataWork();
	DbAdapter dbAdapter;
	Cursor cursor;
	private int[] resButtons = new int[] { R.id.btDeleteLog, R.id.button2,
			R.id.button3, R.id.button4, R.id.button5, R.id.button6,
			R.id.button7, R.id.button8, R.id.button9, R.id.button10,
			R.id.button11, R.id.button12 };
	private int[] startB = new int[] { R.id.Button101, R.id.Button102,
			R.id.Button103, R.id.Button104, R.id.Button105, R.id.Button106,
			R.id.Button107, R.id.Button108, R.id.Button109, R.id.Button110,
			R.id.Button111, R.id.Button112 };
	private int[] endB = new int[] { R.id.Button201, R.id.Button202,
			R.id.Button203, R.id.Button204, R.id.Button205, R.id.Button206,
			R.id.Button207, R.id.Button208, R.id.Button209, R.id.Button210,
			R.id.Button211, R.id.Button212 };
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final TelephonyManager manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String OperatorSIM = manager.getSimOperator();
		if (OperatorSIM == operatorMTS) phoneNumber = "2868"; 
		
		mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE); // ���������
		dbAdapter = new DbAdapter(this);
		dbAdapter.open();
		int Rez;
		for (int i = 0; i < 12; i++) {
			Rez = 0;
			btBuf = (Button) findViewById(resButtons[i]);
			btStart = (ToggleButton) findViewById(startB[i]);
			btEnd = (ToggleButton) findViewById(endB[i]);
			cursor = dbAdapter.getWork(i + 1);
			// BufWork.NUMBER = cursor.getInt(cursor
			// .getColumnIndex(DatabaseHelper.ROWID));
			BufWork.SITE = cursor.getInt(cursor
					.getColumnIndex(DatabaseHelper.SITE));
			BufWork.WORK = cursor.getInt(cursor
					.getColumnIndex(DatabaseHelper.WORK));
			BufWork.START = cursor.getInt(cursor
					.getColumnIndex(DatabaseHelper.START));
			BufWork.STOP = cursor.getInt(cursor
					.getColumnIndex(DatabaseHelper.STOP));
			cursor.close();
			// Log.d(TAG,
			// "NUMBER-" + String.valueOf(BufWork.NUMBER) + " SITE-"
			// + String.valueOf(BufWork.SITE) + " WORK-"
			// + String.valueOf(BufWork.WORK) + " START-"
			// + String.valueOf(BufWork.START) + " STOP-"
			// + String.valueOf(BufWork.STOP));'
			Rez = BufWork.START * 10 + BufWork.STOP;
			if (BufWork.SITE == 0) {
				// Log.d(TAG,
				// "SITE == 0 " + "NUMBER-"
				// + String.valueOf(BufWork.NUMBER));
				btBuf.setText(SITE);
				btStart.setEnabled(false);
				btStart.setChecked(false);
				btEnd.setEnabled(false);
				btEnd.setChecked(false);
			} else {
				// Log.d(TAG,
				// "Rez " + String.valueOf(Rez) + " NUMBER-"
				// + String.valueOf(BufWork.NUMBER));
				btBuf.setText(String.valueOf(BufWork.SITE));
				switch (Rez) {
				case 0:
					btStart.setEnabled(true);
					btStart.setChecked(false);
					btEnd.setEnabled(false);
					btEnd.setChecked(false);
					break;
				case 10:
					btStart.setEnabled(false);
					btStart.setChecked(true);
					btBuf.setEnabled(false);
					btEnd.setEnabled(true);
					btEnd.setChecked(false);
					break;
				case 11:
					btStart.setEnabled(false);
					btStart.setChecked(true);
					btEnd.setEnabled(false);
					btEnd.setChecked(true);
					btBuf.setText(SITE);
					BufWork.SITE = 0;
					BufWork.WORK = 0;
					BufWork.START = 0;
					BufWork.STOP = 0;
					// Log.d(TAG,
					// "--- updateWork ---"
					// + String.valueOf(BufWork.NUMBER));
					dbAdapter.updateWork(i + 1, BufWork);
					break;
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbAdapter.close(); // ��������� ����������� ��� ������
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// ��������� ������� �� ������ ����
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.it_About:
			// � ���������
			Intent intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.it_Log: // ����� �� ����������
			Intent intentLog = new Intent(this, LogActivity.class);
			startActivity(intentLog);

			break;
		case R.id.it_Quit: // ����� �� ����������
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void butSiteClick(View v) {
		// ���� ������ ������
		btBuf = (Button) v;
		// ����� ������
		int i = Integer.parseInt((String) btBuf.getTag());
		btStart = (ToggleButton) findViewById(startB[i - 1]);
		btEnd = (ToggleButton) findViewById(endB[i - 1]);
		cursor = dbAdapter.getWork(i);
		BufWork.NUMBER = cursor.getInt(cursor
				.getColumnIndex(DatabaseHelper.ROWID));
		BufWork.SITE = cursor
				.getInt(cursor.getColumnIndex(DatabaseHelper.SITE));
		BufWork.WORK = cursor
				.getInt(cursor.getColumnIndex(DatabaseHelper.WORK));
		BufWork.START = cursor.getInt(cursor
				.getColumnIndex(DatabaseHelper.START));
		BufWork.STOP = cursor
				.getInt(cursor.getColumnIndex(DatabaseHelper.STOP));
		cursor.close();
		Intent intent = new Intent(this, InputActivity.class);
		intent.putExtra("SITE", String.valueOf(BufWork.SITE));
		intent.putExtra("WORK", String.valueOf(BufWork.WORK));
		startActivityForResult(intent, 1);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		if (data.getStringExtra("SITE").length() == 0) {
			BufWork.SITE = 0;
			btBuf.setText(SITE);
			btStart.setEnabled(false);
			btStart.setChecked(false);
			btEnd.setEnabled(false);
			btEnd.setChecked(false);
		} else {
			BufWork.SITE = Integer.parseInt(data.getStringExtra("SITE"));
			btBuf.setText(String.valueOf(BufWork.SITE));
			btStart.setEnabled(true);
			btStart.setChecked(false);
			btEnd.setEnabled(false);
			btEnd.setChecked(false);
		}
		if (data.getStringExtra("WORK").length() == 0)
			BufWork.WORK = 0;
		else
			BufWork.WORK = Integer.parseInt(data.getStringExtra("WORK"));
		// Log.d(TAG, String.valueOf(BufWork.WORK));
		// Log.d(TAG, "--- updateWork ---" + String.valueOf(BufWork.NUMBER));
		dbAdapter
				.updateWork(Integer.parseInt((String) btBuf.getTag()), BufWork);
	}

	public void butStartClick(View v) {
		// ������� ��� - ������ ������
		btStart = (ToggleButton) v;
		// ����� ������
		int i = Integer.parseInt((String) btStart.getTag());
		cursor = dbAdapter.getWork(i);
		BufWork.NUMBER = cursor.getInt(cursor
				.getColumnIndex(DatabaseHelper.ROWID));
		BufWork.SITE = cursor
				.getInt(cursor.getColumnIndex(DatabaseHelper.SITE));
		BufWork.WORK = cursor
				.getInt(cursor.getColumnIndex(DatabaseHelper.WORK));
		BufWork.START = 1;
		BufWork.STOP = cursor
				.getInt(cursor.getColumnIndex(DatabaseHelper.STOP));
		cursor.close();
		dbAdapter.updateWork(i, BufWork);
		sendSMS(String.valueOf(BufWork.WORK), msStart);
		btEnd = (ToggleButton) findViewById(endB[i - 1]);
		btStart.setEnabled(false);
		// btBuf = (Button) findViewById(resButtons[i]);
		// btBuf.setEnabled(false);
		btEnd.setEnabled(true);
	}

	public void butEndClick(View v) {
		// ������� ��� - ����� ������
		btEnd = (ToggleButton) v;
		// ����� ������
		int i = Integer.parseInt((String) btEnd.getTag());
		cursor = dbAdapter.getWork(i);
		BufWork.NUMBER = cursor.getInt(cursor
				.getColumnIndex(DatabaseHelper.ROWID));
		BufWork.SITE = cursor
				.getInt(cursor.getColumnIndex(DatabaseHelper.SITE));
		BufWork.WORK = cursor
				.getInt(cursor.getColumnIndex(DatabaseHelper.WORK));
		BufWork.START = cursor.getInt(cursor
				.getColumnIndex(DatabaseHelper.START));
		BufWork.STOP = 1;
		cursor.close();
		dbAdapter.updateWork(i, BufWork);
		sendSMS(String.valueOf(BufWork.WORK), msEnd);
		btStart = (ToggleButton) findViewById(startB[i - 1]);
		btStart.setEnabled(false);
		// btBuf = (Button) findViewById(resButtons[i]);
		// btBuf.setEnabled(true);
		btEnd.setEnabled(false);
	}

	/*
	 * public void trySendSMS() { showDialog(IDD_SENT); }
	 */

	private void sendSMS(String numberWork, String msType) {

		String textSMS;
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
				SENT), 0);

		// ---����� SMS ����������---
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS sent",
							Toast.LENGTH_SHORT).show();

					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Generic failure",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No service",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "Null PDU",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "Radio off",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(SENT));

		SmsManager sms = SmsManager.getDefault();
		textSMS = server + numberWork + msWork + msType;
		sms.sendTextMessage(phoneNumber, null, textSMS, sentPI, null);
		writeLog(textSMS);
	}

	private void writeLog(String text) {
		SimpleDateFormat mSDF = new SimpleDateFormat("d.M.yyyy HH:mm:ss");
		text = mSDF.format(new Date()) + " --> " + text + "\n";
		oStream = new ByteArrayInputStream(text.getBytes());
		FileUtils.writeExternal(oStream, FILENAME_SD, true);
		CommonIO.close(oStream);
	}
	/*
	 * ��� ��������� SMS � ������� ��������� ��������� 
	 * ContentValues values = new ContentValues(); 
	 * values.put("address", "123456789"); //����� �������� ���� ��������� ��� 
	 * values.put("body", "test"); //����� ���������
	 * values.put("date", System.currentTimeMillis()); 
	 * values.put("read", 1); //��������� ��������� 
	 * values.put("seen", 1); //��������� �����������
	 * values.put("type", 2); //��� ��������� ��������� 
	 * values.put("person"),
	 * contactId); //������������� �������� � �� ���������
	 * values.put("thread_id", conversationId); //������������� ������
	 * getContentResolver().insert(Uri.parse("content://sms/sent"), values); 
	 * ��� ����� ������������� ������? � ��������� ��������� �� �� ��������� -
	 * ������� ���� ����������� ������, � ��������� ��� �������� ���� �������
	 * (������ �����), � ��������� ��������� ������ �������� � �� ������������.
	 * � ����� ������ ������������� ������ ��� �������� ���� ������������
	 * ����������� ����� 
	 * content://mms-sms/conversations/ (����� �� �� ��� ���� ���������).
	 * 
	 * ������������� �������� ����� ����� �� ��������� - �������� � ��� ����
	 * ���������. ������, ���� ����� 2 �������� � ����������� �������� - ��
	 * ����� ���� ��������.
	 * 
	 * ���� read/seen/type ����� ������������� (� ����������� �������).
	 */
}
