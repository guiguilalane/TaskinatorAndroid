package com.kiwicorporation.taskinator;

import java.util.ArrayList;

import model.ListT;
import model.Task;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {

	private ExpandableListView expandableList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		expandableList = (ExpandableListView) findViewById(R.id.expandableView);

		final ArrayList<ListT> groupes = new ArrayList<ListT>();

		for (int i = 1; i < 5; i++) {

			ListT groupe = new ListT("Liste " + i);

			ArrayList<Task> donnees = new ArrayList<Task>();

			for (int x = 1; x < 4; x++) {
				donnees.add(new Task("Task " + x, true));
			}

			groupe.setTaskList(donnees);

			groupes.add(groupe);
		}

		final ELVAdapter adapter = new ELVAdapter(this, groupes);

		expandableList.setAdapter(adapter);

		Button addListButton = (Button) findViewById(R.id.idAddList);
		addListButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO A configurer pour modifier le nom ...
				groupes.add(new ListT("Bloup a configuer"));
				adapter.notifyDataSetChanged();
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}