package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guillaume
 *
 */
public class ListT {

	private String listName;
	private List<Task> taskList;
	
	
	/**
	 * The List of task constructor
	 * @param name the name of the task list
	 */
	public ListT(String name) {
		listName = name;
		taskList = new ArrayList<Task>();
	}
	
	/**
	 * @return the name of the task list
	 */
	public String getListName() {
		return listName;
	}
	
	/**
	 * @param listName the list name to set
	 */
	public void setListName(String listName) {
		this.listName = listName;
	}
	/**
	 * @return the list af all task in the task list
	 */
	public List<Task> getTaskList() {
		return taskList;
	}
	
	/**
	 * @param pos the position of task needed
	 * @return the task at the specified position
	 */
	public Task getTask(int pos) {
		return taskList.get(pos);
	}
	
	/**
	 * @param taskToAdd the task to add in the task list
	 * @return whether the task was added to the task list
	 */
	public boolean addTask(Task taskToAdd) {
		return taskList.add(taskToAdd);
	}
	
	
	/**
	 * @param taskToRemove the task from the task list
	 * @return whethet the task was removed from the task list
	 */
	public boolean removeTask(Task taskToRemove) {
		return taskList.remove(taskToRemove);
	}
	
	
	/**
	 * @param pos the position of the task to remove from the task list
	 * @return the task which is removed from the task list
	 */
	public Task removeTask(int pos) {
		return taskList.remove(pos);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = listName + " :";
		for(Task t: taskList) {
			s += "\n\t" + t;
		}
		return s;
	}
	
	
	
}
