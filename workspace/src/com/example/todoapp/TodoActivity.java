package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends ActionBarActivity {

	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private final int REQUEST_CODE = 20;

	private ListView lvTodoItems;
	private EditText etNewItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);


		readItems();
		lvTodoItems = (ListView) findViewById(R.id.lvTodo);
		etNewItem = (EditText) findViewById(R.id.editAddItem);
		todoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoItems);
		lvTodoItems.setAdapter(todoAdapter);

		setupListViewListener();
	}

	private void setupListViewListener() {
		// TODO Auto-generated method stub
		lvTodoItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> aview, View item, int pos, long id)
			{
				todoItems.remove(pos);
				todoAdapter.notifyDataSetInvalidated();
				saveItems();
				return true;
			}
		});

		lvTodoItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View item, int pos,
					long id) {

				launchEditActivity(pos, todoItems.get(pos));
			}
		});

	}

	public void launchEditActivity(int pos, String text) {
		Intent i = new Intent(this, EditItemActivity.class);
		i.putExtra("pos", pos);
		i.putExtra("edit_text", text);

		startActivityForResult(i, REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// REQUEST_CODE is defined above
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			// Extract name value from result extras
			String edit_text = data.getExtras().getString("edit_text");
			int pos = data.getExtras().getInt("pos");

			// update the view
			todoItems.set(pos, edit_text);
			todoAdapter.notifyDataSetInvalidated();
			saveItems();
		}
	} 


	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			todoItems = new ArrayList<String>();
			e.printStackTrace();
		}
	}

	private void saveItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, todoItems);
		} catch (IOException e) {
			todoItems = new ArrayList<String>();
			e.printStackTrace();
		}
	}



	public void onAddNewItem(View v) {
		String newItem = etNewItem.getText().toString();
		todoAdapter.add(newItem);
		etNewItem.setText("");
		saveItems();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
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
