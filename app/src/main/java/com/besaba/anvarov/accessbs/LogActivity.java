package com.besaba.anvarov.accessbs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogActivity extends Activity {
	TextView textLog;
	Button btDeleteLog;
	// final String LOG_TAG = "myLogs";
	InputStream iStream, oStream;
	final String FILENAME_SD = "Log_SMS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		textLog = (TextView) findViewById(R.id.textLog);
		btDeleteLog = (Button) findViewById(R.id.btDeleteLog);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Log.d(LOG_TAG, "MainActivity: onStart()");
		readLog();
	}

	private void readLog() {
		iStream = FileUtils.readExternal(FILENAME_SD);
		if (iStream != null) {
			textLog.setText(CommonIO.convertStreamToString(iStream));
		} else {
			textLog.setText("Нет лога");
			btDeleteLog.setEnabled(false);
		}
		CommonIO.close(iStream);
	}

	public static void delete(String path) {
		File file = new File(path); // Создаем файловую переменную
		if (file.exists()) { // Если файл или директория существует
			String deleteCmd = "rm -r " + path; // Создаем текстовую командную
												// строку
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec(deleteCmd); // Выполняем системные команды
			} catch (IOException e) {
			}
		}
	}

	public void butDeleteLogClick(View v) {
		textLog.setText("Нет лога");
		delete(Environment.getExternalStorageDirectory().getPath() + "/"
				+ FILENAME_SD); // delete("/mnt/sdcard/Log_SMS");
	}

}
