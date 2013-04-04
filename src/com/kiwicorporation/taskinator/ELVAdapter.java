package com.kiwicorporation.taskinator;

import java.util.ArrayList;

import model.ListT;
import model.Task;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/*
 * TODO:
 * Save wich list is expanded or not
 * Save data when app was quit
 * Modify a list (menu or EditText)
 * Add List and task
 * Drag and drop pour déplacer une tâche
 * Quand on déroule une liste se mettre à la position ou on déroule et non à la fin quand la liste est plus grande que l'écran
 * Supprimer tous les todo et sysout
 * Ajouter le plus dans les child (Exception retournée quand on déroule la première après la deuxième)
 * Liste vide pas de flèche expandable
 */

public class ELVAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<ListT> groupes;
	private LayoutInflater inflater;

	public ELVAdapter(Context context, ArrayList<ListT> groupes) {
		this.context = context;
		this.groupes = groupes;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public Object getChild(int gPosition, int cPosition) {
		return groupes.get(gPosition).getTaskList().get(cPosition);
	}

	@Override
	public long getChildId(int gPosition, int cPosition) {
		return cPosition;
	}

	@Override
	public View getChildView(final int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		// if (!isLastChild) {
		System.out.println("lastChild: " + isLastChild);
		System.out.println("on passe autre");
		final Task objet = (Task) getChild(groupPosition, childPosition);
		ChildViewHolder childViewHolder;

		if (convertView == null) {
			childViewHolder = new ChildViewHolder();

			convertView = inflater.inflate(R.layout.task, null);

			childViewHolder.checkboxChild = (CheckBox) convertView
					.findViewById(R.id.idCheckbox);
			childViewHolder.textViewChild = (TextView) convertView
					.findViewById(R.id.idNameTask);
			childViewHolder.deleteButtonChild = (ImageButton) convertView
					.findViewById(R.id.idDeleteTask);

			convertView.setTag(childViewHolder);
		} else {
			childViewHolder = (ChildViewHolder) convertView.getTag();
		}

		childViewHolder.checkboxChild.setChecked(objet.isChecked());
		childViewHolder.checkboxChild
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						objet.setChecked(isChecked);
					}
				});
		childViewHolder.textViewChild.setText(objet.getTaskName());

		childViewHolder.deleteButtonChild.setFocusable(false);
		childViewHolder.deleteButtonChild.setFocusableInTouchMode(false);
		childViewHolder.deleteButtonChild
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						((ListT) getGroup(groupPosition)).removeTask(objet);
						notifyDataSetChanged();
						Toast.makeText(context,
								"Delete Task : " + objet.getTaskName(),
								Toast.LENGTH_SHORT).show();
					}
				});

		return convertView;
		// } else {
		// System.out.println("dernier");
		// ChildViewAdd childViewAdd;
		//
		// if (convertView == null) {
		// System.out.println("on passe view null !");
		// childViewAdd = new ChildViewAdd();
		//
		// convertView = inflater.inflate(R.layout.addtask, null);
		//
		// childViewAdd.addButtonChild = (ImageButton) convertView
		// .findViewById(R.id.idAddTask);
		//
		// convertView.setTag(childViewAdd);
		// } else {
		// childViewAdd = (ChildViewAdd) convertView.getTag();
		// }
		//
		// childViewAdd.addButtonChild.setFocusable(false);
		// childViewAdd.addButtonChild.setFocusableInTouchMode(false);
		// childViewAdd.addButtonChild
		// .setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Toast.makeText(context, "Ajout Task : ",
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		//
		// return convertView;
		// }

	}

	@Override
	public int getChildrenCount(int gPosition) {
		return groupes.get(gPosition).getTaskList().size()/* + 1 */;
	}

	@Override
	public Object getGroup(int gPosition) {
		return groupes.get(gPosition);
	}

	@Override
	public int getGroupCount() {
		return groupes.size();
	}

	@Override
	public long getGroupId(int gPosition) {
		return gPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupViewHolder gholder;

		final ListT group = (ListT) getGroup(groupPosition);

		if (convertView == null) {
			gholder = new GroupViewHolder();

			convertView = inflater.inflate(R.layout.list, null);

			gholder.textViewGroup = (TextView) convertView
					.findViewById(R.id.idNameList);
			gholder.deleteButtonGroup = (ImageButton) convertView
					.findViewById(R.id.idDeleteList);

			convertView.setTag(gholder);
		} else {
			gholder = (GroupViewHolder) convertView.getTag();
		}

		gholder.textViewGroup.setText(group.getListName());
		gholder.deleteButtonGroup.setFocusable(false);
		gholder.deleteButtonGroup.setFocusableInTouchMode(false);
		gholder.deleteButtonGroup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				groupes.remove(groupPosition);
				notifyDataSetChanged();
				Toast.makeText(context,
						"Delete Groupe : " + group.getListName(),
						Toast.LENGTH_SHORT).show();
			}
		});

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

	class GroupViewHolder {
		public TextView textViewGroup;
		public ImageButton deleteButtonGroup;
	}

	class ChildViewHolder {
		public CheckBox checkboxChild;
		public TextView textViewChild;
		public ImageButton deleteButtonChild;
	}

	class ChildViewAdd {
		public ImageButton addButtonChild;
	}
}
