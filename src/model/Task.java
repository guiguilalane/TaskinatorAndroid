package model;


/**
 * 
 * @author guillaume
 *
 */
public class Task {
	
	public static final String TASK_ATTRIBUT_NAME = "taskName";
	
	public static final String TASK_ATTRIBUT_CHECKED = "checked";

	private String taskName;
	private boolean checked;
	
	/**
	 * Task Contructor
	 * @param name the name of the task
	 */
	public Task(String name) {
		this(name, false);
	}
	
	public Task(String name, boolean check) {
		taskName = name;
		checked = check;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return taskName + " " + (checked ? "is checked" : "is not checked");
	}
}
