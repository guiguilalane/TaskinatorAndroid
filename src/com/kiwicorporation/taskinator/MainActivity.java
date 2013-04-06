package com.kiwicorporation.taskinator;

import java.io.IOException;

import xmlParsor.ListBackup;
import controleur.ListManager;
import model.ListT;
import model.Task;
import modelException.ListTException;
import modelException.TaskException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;


public class MainActivity extends Activity {

	private ExpandableListView expandableList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		expandableList = (ExpandableListView) findViewById(R.id.expandableView);
		ListManager lManager = ListManager.getInstance();
		try {
			lManager.setListOfList(ListBackup.getInstance().getListFromInpuStream((getAssets().open("test.xml"))));
		} catch (TaskException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ListTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*for (int i = 1; i < 5; i++) {
			ListT groupe = new ListT("Liste " + i, i == 2);
			for (int x = 1; x < 4; x++) {
				groupe.addTask(new Task("Task " + x, true));
			}
			ListManager.getInstance().addList(groupe);
		}*/

		final ELVAdapter adapter = new ELVAdapter(this, expandableList);

		expandableList.setAdapter(adapter);

		Button addListButton = (Button) findViewById(R.id.idAddList);
		addListButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("Add list");
				// builder.setMessage("What is the new name?");

				// Use an EditText view to get user input.
				final EditText input = new EditText(MainActivity.this);
				input.setHint("Name of the list");
				builder.setView(input);

				builder.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String value = input.getText().toString();
								ListManager.getInstance().addList(
										new ListT(value));
								adapter.notifyDataSetChanged();
							}
						}).setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				// Create alert dialog
				AlertDialog modifyDialog = builder.create();

				// Show it
				modifyDialog.show();
			}
		});
	}

	@Override
	public void onPause() {
		ListBackup.getInstance().saveListToFile("save.xml");
		// Sauvegarde dans XML

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}