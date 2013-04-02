package com.kiwicorporation.taskinator;

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
import android.widget.Toast;
import controleur.ListManager;

public class ELVAdapter extends BaseExpandableListAdapter {
    private Context context;
//    private ArrayList<Groupe> groupes;
    private LayoutInflater inflater;
    
    public ELVAdapter(Context context/*, ArrayList<Groupe> groupes*/) {
        this.context = context;
//        this.groupes = groupes;
        inflater = LayoutInflater.from(context);
    }
 
    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }
 
    public Object getChild(int gPosition, int cPosition) {
        return ListManager.getInstance().getList(gPosition).getTask(cPosition);
    }
 
    public long getChildId(int gPosition, int cPosition) {
        return cPosition;
    }
 
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Task task = (Task) getChild(groupPosition, childPosition);
 
        ChildViewHolder childViewHolder;
 
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
 
            convertView = inflater.inflate(R.layout.task, null);
 
            childViewHolder.editTextChild = (EditText) convertView.findViewById(R.id.idNameTask);
//            childViewHolder.buttonChild = (Button) convertView.findViewById(R.id.BTChild);
 
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
 
        childViewHolder.editTextChild.setText(task.getTaskName());
 
        childViewHolder.checkboxChild.setOnClickListener(new OnClickListener() {
 
            public void onClick(View v) {
                Toast.makeText(context, "Task : " + task.getTaskName(), Toast.LENGTH_SHORT).show();
            }
        });
 
        return convertView;
    }
 
    public int getChildrenCount(int gPosition) {
        return ListManager.getInstance().getList(gPosition).getTaskList().size();
    }
 
    public Object getGroup(int gPosition) {
        return ListManager.getInstance().getList(gPosition);
    }
 
    public int getGroupCount() {
        return ListManager.getInstance().getLists().size();
    }
 
    public long getGroupId(int gPosition) {
        return gPosition;
    }
 
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder gholder;
 
        ListT list = (ListT) getGroup(groupPosition);
 
        if (convertView == null) {
            gholder = new GroupViewHolder();
 
            convertView = inflater.inflate(R.layout.list, null);
 
            gholder.editTextGroup = (EditText) convertView.findViewById(R.id.idNameList);
 
            convertView.setTag(gholder);
        } else {
            gholder = (GroupViewHolder) convertView.getTag();
        }
 
        gholder.editTextGroup.setText(list.getListName());
 
        return convertView;
    }
 
    public boolean hasStableIds() {
        return true;
    }
 
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }
 
    class GroupViewHolder {
        public EditText editTextGroup;
    }
 
    class ChildViewHolder {
        public EditText editTextChild;
        public CheckBox checkboxChild;
    }
 
}
