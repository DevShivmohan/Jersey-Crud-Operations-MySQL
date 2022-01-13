package jersey.crud.operation.jersey_crud_operation.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jersey.crud.operation.jersey_crud_operation.exceptions.StudentAlreadyExistsException;

@Provider
public class StudentAlreadyExistExceptionMapper implements ExceptionMapper<StudentAlreadyExistsException> {

	@Override
	public Response toResponse(StudentAlreadyExistsException exception) {
		return Response.status(Status.NOT_ACCEPTABLE).entity(exception.getMessage()).build();
	}
}
