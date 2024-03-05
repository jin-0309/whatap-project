package exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomExceptionHandler implements ExceptionMapper<CustomException> {

    @Override
    public Response toResponse(CustomException e) {
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
}
