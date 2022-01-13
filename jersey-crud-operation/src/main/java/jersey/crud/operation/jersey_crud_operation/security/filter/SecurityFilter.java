package jersey.crud.operation.jersey_crud_operation.security.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;
import jersey.crud.operation.jersey_crud_operation.db.constants.DbConstants;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class SecurityFilter implements ContainerRequestFilter {

	/**
	 * protect URL prefix based
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		log.info(requestContext.getUriInfo().getPath());
		/**
		 * protect URL only start with students
		 */
		if (requestContext.getUriInfo().getPath().contains(DbConstants.SECURED_URI_PREFIX)) {
			List<String> outHeaders = requestContext.getHeaders().get(DbConstants.AUTHORIZATION_HEADER_KEY);
			if (outHeaders != null && outHeaders.size() > 0) {
				String authToken = outHeaders.get(0);
				authToken = authToken.replaceFirst(DbConstants.AUTHORIZATION_HEADER_PREFIX, "");
				String decodeString = new String(Base64.getDecoder().decode(authToken.getBytes()));
				StringTokenizer stringTokenizer = new StringTokenizer(decodeString, ":");
				String username = null;
				String password = null;
				if (stringTokenizer.hasMoreTokens())
					username = stringTokenizer.nextToken();
				if (stringTokenizer.hasMoreTokens())
					password = stringTokenizer.nextToken();
				if (username != null && password != null)
					if (username.equals("Shiv") && password.equals("12345"))
						return;
			}
			Response unAuthorizedResponse = Response.status(Status.UNAUTHORIZED)
					.entity("User cannot access the resource").build();
			requestContext.abortWith(unAuthorizedResponse);
		}
	}
}
