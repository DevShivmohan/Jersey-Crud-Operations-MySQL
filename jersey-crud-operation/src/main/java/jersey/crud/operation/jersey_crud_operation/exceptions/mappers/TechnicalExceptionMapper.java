package jersey.crud.operation.jersey_crud_operation.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jersey.crud.operation.jersey_crud_operation.exceptions.TechnicalException;

@Provider
public class TechnicalExceptionMapper implements ExceptionMapper<TechnicalException> {

	@Override
	public Response toResponse(TechnicalException exception) {
		return Response.status(Status.GONE).entity(exception.getMessage()).build();
	}

}
