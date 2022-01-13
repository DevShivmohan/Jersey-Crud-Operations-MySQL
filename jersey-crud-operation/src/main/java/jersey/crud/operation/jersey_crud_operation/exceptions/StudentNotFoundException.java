package jersey.crud.operation.jersey_crud_operation.exceptions;

public class StudentNotFoundException extends RuntimeException {

	public StudentNotFoundException(String errorMsg) {
		super(errorMsg);
	}
}
