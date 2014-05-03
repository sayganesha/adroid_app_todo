package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends ActionBarActivity {

	// handle to the multi-line edit text input
	private EditText editText;

	// save the position to be returned back to the main activity
	private int pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);

		// Extract the data input for the edit form
		String edit_text = getIntent().getStringExtra("edit_text");
		pos = getIntent().getIntExtra("pos", 0);

		// Setup the edit text input handle
		editText = (EditText) findViewById(R.id.editActEditText);

		// Set the text in the input text field
		editText.setText(edit_text);
		// Set the cursor position at the end
		editText.setSelection(edit_text.length());

	}

	public void onDone(View v) {

		Intent data = new Intent();
		// Pass relevant data back as a result
		data.putExtra("edit_text", editText.getText().toString());
		data.putExtra("pos", pos);

		// Activity finished ok, return the data
		setResult(RESULT_OK, data); // set result code and bundle data for response
		finish(); // closes the activity, pass data to parent
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
