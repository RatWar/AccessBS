package com.besaba.anvarov.accessbs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class InputActivity extends Activity {

	EditText editWork, editSite;
	String BufWork, BufSite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
		editWork = (EditText) findViewById(R.id.etWork);
		editSite = (EditText) findViewById(R.id.etSite);
		// editWork.setText(getIntent().getStringExtra("WORK"));
		// editSite.setText(getIntent().getStringExtra("SITE"));
		BufWork = getIntent().getStringExtra("WORK");
		if (BufWork.equals("0"))
			editWork.setText("");
		else
			editWork.setText(BufWork);
		BufSite = getIntent().getStringExtra("SITE");
		if (BufSite.equals("0"))
			editSite.setText("");
		else
			editSite.setText(BufSite);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.input, menu);
		return true;
	}

	public void butCancelClick(View v) {
		// Отмена
		finish();
	}

	public void butClearSClick(View v) {
		editSite.setText("");
	}

	public void butClearWClick(View v) {
		editWork.setText("");
	}

	public void butInputClick(View v) {
		// Ввод
		Intent intent = new Intent();
		intent.putExtra("SITE", editSite.getText().toString());
		intent.putExtra("WORK", editWork.getText().toString());
		setResult(RESULT_OK, intent);
		finish();
	}

}
