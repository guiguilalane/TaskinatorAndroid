package modelException;

public class TaskException extends Exception {

	private static final long serialVersionUID = 1L;

	public TaskException() {
		// TODO Auto-generated constructor stub
	}

	public TaskException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	public TaskException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}

	public TaskException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

}
