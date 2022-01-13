package jersey.crud.operation.jersey_crud_operation.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jersey.crud.operation.jersey_crud_operation.exceptions.StudentNotFoundException;

@Provider
public class StudentNotFoundExceptionMapper implements ExceptionMapper<StudentNotFoundException> {

	@Override
	public Response toResponse(StudentNotFoundException exception) {
		return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).build();
	}

}
