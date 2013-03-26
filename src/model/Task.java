package model;


/**
 * 
 * @author guillaume
 *
 */
public class Task {

	private String taskName;
	private boolean checked;
	
	/**
	 * Task Contructor
	 * @param name the name of the task
	 */
	public Task(String name) {
		taskName = name;
		checked = false;
	}
	
	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @return whether the task is checked or not
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @param checked the new task state
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
