package com.kiwicorporation.taskinator;

import java.util.ArrayList;

import model.ListT;
import model.Task;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/*
 * TODO:
 * Save wich list is expanded or not
 * Save data when app was quit
 * Drag and drop pour d�placer une t�che
 * Quand on d�roule une liste se mettre � la position ou on d�roule et non � la fin quand la liste est plus grande que l'�cran
 * 
 * Est-ce qu'on laisse les toast si oui pour lesquel add modify supp ?????
 * 
 * Voir pour le style / padding / taille bouton ... 
 * Menu contextuel (� voir)
 * 
 * NOTE:
 * Revoir pour la fa�on dont est corriger le bug du bouton plus 
 * Revoir pour Add/Delete/Modify un moyen de cr�er les boites de dialog � part (dans une autre classe) Refractor
 * 
 * FIXME:
 * Supprimer tous les todo et sysout
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
		if (!isLastChild) {
			final Task objet = (Task) getChild(groupPosition, childPosition);
			ChildViewHolder childViewHolder;

			// if (convertView == null) {
			childViewHolder = new ChildViewHolder();

			convertView = inflater.inflate(R.layout.task, null);

			childViewHolder.checkboxChild = (CheckBox) convertView
					.findViewById(R.id.idCheckbox);
			childViewHolder.textViewChild = (TextView) convertView
					.findViewById(R.id.idNameTask);
			childViewHolder.modifyButtonChild = (ImageButton) convertView
					.findViewById(R.id.idModifyTask);
			childViewHolder.deleteButtonChild = (ImageButton) convertView
					.findViewById(R.id.idDeleteTask);

			convertView.setTag(childViewHolder);
			// } else {
			// childViewHolder = (ChildViewHolder) convertView.getTag();
			// }

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

			childViewHolder.modifyButtonChild.setFocusable(false);
			childViewHolder.modifyButtonChild.setFocusableInTouchMode(false);
			childViewHolder.modifyButtonChild
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									context);
							builder.setTitle("Modify task");
							builder.setMessage("What is the new name of the task?");

							// Use an EditText view to get user input.
							final EditText input = new EditText(context);
							input.setText(objet.getTaskName());
							builder.setView(input);

							builder.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											String value = input.getText()
													.toString();
											objet.setTaskName(value);
											notifyDataSetChanged();
										}
									}).setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
							// Create alert dialog
							AlertDialog modifyDialog = builder.create();

							// Show it
							modifyDialog.show();
							Toast.makeText(context,
									"Modify " + objet.getTaskName(),
									Toast.LENGTH_SHORT).show();
						}
					});

			childViewHolder.deleteButtonChild.setFocusable(false);
			childViewHolder.deleteButtonChild.setFocusableInTouchMode(false);
			childViewHolder.deleteButtonChild
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							((ListT) getGroup(groupPosition)).removeTask(objet);
							notifyDataSetChanged();
							Toast.makeText(context,
									"You delete " + objet.getTaskName(),
									Toast.LENGTH_SHORT).show();
						}
					});

			return convertView;
		} else {
			ChildViewAdd childViewAdd;

			// if (convertView == null) {
			childViewAdd = new ChildViewAdd();

			convertView = inflater.inflate(R.layout.addtask, null);

			childViewAdd.addButtonChild = (ImageButton) convertView
					.findViewById(R.id.idAddTask);

			convertView.setTag(childViewAdd);
			// } else {
			// childViewAdd = (ChildViewAdd) convertView.getTag();
			// }

			childViewAdd.addButtonChild.setFocusable(false);
			childViewAdd.addButtonChild.setFocusableInTouchMode(false);
			childViewAdd.addButtonChild
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									context);
							builder.setTitle("Add task");
							// builder.setMessage("What is the new name?");

							// Use an EditText view to get user input.
							final EditText input = new EditText(context);
							input.setHint("Name of the task");
							builder.setView(input);

							builder.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											String value = input.getText()
													.toString();
											((ListT) getGroup(groupPosition))
													.addTask(new Task(value));
											notifyDataSetChanged();
										}
									}).setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
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

			return convertView;
		}

	}

	@Override
	public int getChildrenCount(int gPosition) {
		return groupes.get(gPosition).getTaskList().size() + 1;
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
			gholder.modifyButtonGroup = (ImageButton) convertView
					.findViewById(R.id.idModifyList);
			gholder.deleteButtonGroup = (ImageButton) convertView
					.findViewById(R.id.idDeleteList);

			convertView.setTag(gholder);
		} else {
			gholder = (GroupViewHolder) convertView.getTag();
		}

		gholder.textViewGroup.setText(group.getListName());
		gholder.modifyButtonGroup.setFocusable(false);
		gholder.modifyButtonGroup.setFocusableInTouchMode(false);
		gholder.modifyButtonGroup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Modify list");
				builder.setMessage("What is the new name of the new list?");

				// Use an EditText view to get user input.
				final EditText input = new EditText(context);
				input.setText(group.getListName());
				builder.setView(input);

				builder.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String value = input.getText().toString();
								group.setListName(value);
								notifyDataSetChanged();
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
		gholder.deleteButtonGroup.setFocusable(false);
		gholder.deleteButtonGroup.setFocusableInTouchMode(false);
		gholder.deleteButtonGroup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// Set title
				alertDialogBuilder.setTitle("Delete List ?");

				// Set dialog message
				alertDialogBuilder
						.setMessage("You'll lose all tasks in this list!")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										groupes.remove(groupPosition);
										notifyDataSetChanged();
										Toast.makeText(
												context,
												"You delete "
														+ group.getListName(),
												Toast.LENGTH_SHORT).show();
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// Create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// Show it
				alertDialog.show();
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

	@Override
	public void onGroupCollapsed(int groupPosition) {
		groupes.get(groupPosition).setOpen(false);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		groupes.get(groupPosition).setOpen(true);
	}

	class GroupViewHolder {
		public TextView textViewGroup;
		public ImageButton modifyButtonGroup;
		public ImageButton deleteButtonGroup;
	}

	class ChildViewHolder {
		public CheckBox checkboxChild;
		public TextView textViewChild;
		public ImageButton modifyButtonChild;
		public ImageButton deleteButtonChild;
	}

	class ChildViewAdd {
		public ImageButton addButtonChild;
	}
}
