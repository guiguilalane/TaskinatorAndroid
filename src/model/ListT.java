package model;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author guillaume
 *
 */
public class ListT implements Iterable<Task>{

	public static final String LISTT_ATTRIBUT_NAME = "listName";
	
	public static final String LISTT_ATTRIBUT_OPEN = "open";
	
	private String listName;
	private boolean open;
	private List<Task> taskList;
	
	
	public ListIterator<Task> iterator() {
		return taskList.listIterator();
	}
	
	/**
	 * The List of task constructor
	 * @param name the name of the task list
	 */
	public ListT(String name) {
		this(name, false);
	}
	
	/**
	 * The List of task constructor
	 * @param name the name of the task list
	 * @param state whether the list is open, whether tasks should be displayed
	 */
	public ListT(String name, boolean state) {
		listName = name;
		open = state;
		taskList = new LinkedList<Task>();
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
	 * @return whether tle list is open, whether tasks should be displayed
	 */
	public boolean isOpen() {
		return open;
	}
	
	/**
	 * @param state the new state of the list
	 */
	public void setOpen(boolean state) {
		open = state;
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
	public Task removeTaskFromIndex(int pos) {
		return taskList.remove(pos);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = listName;
		if(open) {
			s += " :";
			for(Task t: taskList) {
				s += "\n\t" + t;
			}
		}
		return s;
	}
}