package com.kiwicorporation.taskinator;

import java.util.List;

import model.ListT;
import model.Task;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import controleur.ListManager;

public class ELVAdapter extends BaseExpandableListAdapter {
	private final Context context;
	// private ArrayList<Groupe> groupes;
	private final LayoutInflater inflater;

	public ELVAdapter(Context context/* , ArrayList<Groupe> groupes */,
			List<ListT> lists) {
		this.context = context;
		// this.groupes = groupes;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public Object getChild(int gPosition, int cPosition) {
		return ListManager.getInstance().getList(gPosition).getTask(cPosition);
	}

	@Override
	public long getChildId(int gPosition, int cPosition) {
		return cPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		System.out.println("on passe dans child");
		final Task task = (Task) getChild(groupPosition, childPosition);

		ChildViewHolder childViewHolder;

		if (convertView == null) {
			childViewHolder = new ChildViewHolder();

			convertView = inflater.inflate(R.layout.task, null);

			childViewHolder.editTextChild = (EditText) convertView
					.findViewById(R.id.idNameTask);
			childViewHolder.checkboxChild = (CheckBox) convertView
					.findViewById(R.id.idCheckbox);
			childViewHolder.imageButtonChild = (ImageButton) convertView
					.findViewById(R.id.idDeleteTask);
			System.out.println("on passe childview");
			convertView.setTag(childViewHolder);
		} else {
			childViewHolder = (ChildViewHolder) convertView.getTag();
		}

		childViewHolder.editTextChild.setText(task.getTaskName());
		childViewHolder.checkboxChild.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Task : " + task.getTaskName(),
						Toast.LENGTH_SHORT).show();
			}
		});
		childViewHolder.imageButtonChild
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(context, "DeleteTask",
								Toast.LENGTH_SHORT).show();
					}
				});

		return convertView;
	}

	@Override
	public int getChildrenCount(int gPosition) {
		return ListManager.getInstance().getList(gPosition).getTaskList()
				.size();
	}

	@Override
	public Object getGroup(int gPosition) {
		return ListManager.getInstance().getList(gPosition);
	}

	@Override
	public int getGroupCount() {
		return ListManager.getInstance().getLists().size();
	}

	@Override
	public long getGroupId(int gPosition) {
		return gPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupViewHolder gholder;

		ListT list = (ListT) getGroup(groupPosition);

		if (convertView == null) {
			gholder = new GroupViewHolder();

			convertView = inflater.inflate(R.layout.list, null);

			gholder.editTextGroup = (EditText) convertView
					.findViewById(R.id.idNameList);
			gholder.imageButtonGroup = (ImageButton) convertView
					.findViewById(R.id.idDeleteList);

			convertView.setTag(gholder);
		} else {
			gholder = (GroupViewHolder) convertView.getTag();
		}

		gholder.editTextGroup.setText(list.getListName());
		gholder.imageButtonGroup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Button deleteList : ",
						Toast.LENGTH_SHORT).show();
			}
		});
		System.out.println("avant return");
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
		public EditText editTextGroup;
		public ImageButton imageButtonGroup;
	}

	class ChildViewHolder {
		public EditText editTextChild;
		public CheckBox checkboxChild;
		public ImageButton imageButtonChild;
	}

}
