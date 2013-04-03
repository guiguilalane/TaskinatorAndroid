package com.kiwicorporation.taskinator;

import model.ListT;
import model.Task;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ExpandableListView;
import controleur.ListManager;

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
			for (int x = 1; x < 4; x++) {
				list.addTask(new Task("Task " + x));
			}
			System.out.println("taille liste: " + list.getTaskList().size());
			ListManager.getInstance().addList(list);
		}

		ELVAdapter adapter = new ELVAdapter(this/* , groupes */, ListManager
				.getInstance().getLists());
		allListView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}