package comp3011.assignment1.exception;

public class ShutdownException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ShutdownException() {
		super("Graceful shutdown is already in progress.");
	}

}
