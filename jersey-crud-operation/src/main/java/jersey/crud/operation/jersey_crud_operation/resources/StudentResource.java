package jersey.crud.operation.jersey_crud_operation.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jersey.crud.operation.jersey_crud_operation.dto.StudentRequestDTO;
import jersey.crud.operation.jersey_crud_operation.exceptions.StudentAlreadyExistsException;
import jersey.crud.operation.jersey_crud_operation.services.StudentService;

@Path(value = "/students")
public class StudentResource {

	@GET
	@Path(value = "/test")
	@Produces(MediaType.TEXT_PLAIN)
	public Response test() {
		int i = 0;
		if (i == 0)
			throw new StudentAlreadyExistsException("Exception Mapper executed shiv");
		return Response.status(Status.ACCEPTED).entity("Welcome in jersey framework").build();
	}

	/**
	 * add a student record
	 * 
	 * @param studentRequestDTO
	 * @return
	 */
	@POST
	@Path(value = "/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addStudent(StudentRequestDTO studentRequestDTO) {
		return StudentService.getInstance().addStudent(studentRequestDTO);
	}

	/**
	 * update student record
	 * 
	 * @param studentRequestDTO
	 * @param stuEmail
	 * @return
	 */
	@PUT
	@Path(value = "/update/{email}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStudent(StudentRequestDTO studentRequestDTO, @PathParam("email") String stuEmail) {
		return StudentService.getInstance().updateStudent(studentRequestDTO, stuEmail);
	}

	/**
	 * delete student record
	 * 
	 * @param stuEmail
	 * @return
	 */
	@DELETE
	@Path(value = "/delete/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteStudent(@PathParam("email") String stuEmail) {
		return StudentService.getInstance().deleteStudent(stuEmail);
	}

	/**
	 * getting all students
	 * 
	 * @return
	 */
	@GET
	@Path(value = "/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStudents() {
		return StudentService.getInstance().getAllStudents();
	}

	/**
	 * getting students by name
	 * 
	 * @param stuName
	 * @return
	 */
	@GET
	@Path(value = "/getByName/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsByName(@PathParam("name") String stuName) {
		return StudentService.getInstance().getStudentsByName(stuName);
	}

	/**
	 * getting students by age
	 * 
	 * @param stuAge
	 * @return
	 */
	@GET
	@Path(value = "/getByAge/{age}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsByAge(@PathParam("age") int stuAge) {
		return StudentService.getInstance().getStudentsByAge(stuAge);
	}

	/**
	 * getting students by email
	 * 
	 * @param stuEmail
	 * @return
	 */
	@GET
	@Path(value = "/getByEmail/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsByEmail(@PathParam("email") String stuEmail) {
		return StudentService.getInstance().getStudentsByEmail(stuEmail);
	}

	/**
	 * getting students by date ascending order
	 * 
	 * @return
	 */
	@GET
	@Path(value = "/getByAscDate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsByAscDate() {
		return StudentService.getInstance().getStudentsByAscDate();
	}

	/**
	 * getting students by date descending order
	 * 
	 * @return
	 */
	@GET
	@Path(value = "/getByDscDate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsByDscDate() {
		return StudentService.getInstance().getStudentsByDesDate();
	}
}
