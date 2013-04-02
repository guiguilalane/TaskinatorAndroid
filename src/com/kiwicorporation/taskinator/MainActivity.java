package com.kiwicorporation.taskinator;

import java.util.ArrayList;

import controleur.ListManager;

import model.ListT;
import model.Task;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {

	private ExpandableListView allListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// We take the expandable list of the XML
		allListView = (ExpandableListView) findViewById(R.id.idAllList);

		
		for (int i = 1; i < 4; i++) {
			ListT list = new ListT("Liste " + i);
			ArrayList<Task> donnees = new ArrayList<Task>();
			for (int x = 1; x < 4; x++) {
				list.addTask(new Task("Task "+x));
			}
			ListManager.getInstance().addList(list);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}