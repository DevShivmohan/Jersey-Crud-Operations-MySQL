package jersey.crud.operation.jersey_crud_operation.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jersey.crud.operation.jersey_crud_operation.dao.StudentDao;
import jersey.crud.operation.jersey_crud_operation.dto.StudentRequestDTO;
import jersey.crud.operation.jersey_crud_operation.dto.StudentResponseDTO;
import jersey.crud.operation.jersey_crud_operation.entity.Student;
import jersey.crud.operation.jersey_crud_operation.exceptions.StudentAlreadyExistsException;
import jersey.crud.operation.jersey_crud_operation.exceptions.StudentNotFoundException;
import jersey.crud.operation.jersey_crud_operation.exceptions.TechnicalException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentService {

	private static StudentService studentService;

	public static StudentService getInstance() {
		if (studentService == null)
			studentService = new StudentService();
		return studentService;
	}

	/**
	 * add student
	 * 
	 * @param studentRequestDTO
	 * @return
	 * @throws SQLException
	 */
	public Response addStudent(StudentRequestDTO studentRequestDTO) {
		Student student = new Student();
		student.setStuName(studentRequestDTO.getStuName());
		student.setStuAge(studentRequestDTO.getStuAge());
		student.setStuEmail(studentRequestDTO.getStuEmail());
		student.setStuCapDate(new Date());
		for (Student student2 : StudentDao.getInstance().getAllStudents())
			if (student2.getStuEmail().equalsIgnoreCase(student.getStuEmail()))
				throw new StudentAlreadyExistsException(
						"Student Email " + student.getStuEmail() + " is already exist in our database");
		if (StudentDao.getInstance().save(student))
			return Response.status(Status.OK).entity(new StudentResponseDTO(student.getStuName(), student.getStuAge(),
					student.getStuEmail(), student.getStuCapDate())).build();
		else
			throw new TechnicalException("Due to technical problem your request could not processed");
	}

	/**
	 * update a student record
	 * 
	 * @param studentRequestDTO
	 * @param stuEmail
	 * @return
	 */
	public Response updateStudent(StudentRequestDTO studentRequestDTO, String stuEmail) {
		boolean isEmailExists = false, isEmailMatched = false;
		Student student = new Student();
		student.setStuName(studentRequestDTO.getStuName());
		student.setStuAge(studentRequestDTO.getStuAge());
		student.setStuEmail(studentRequestDTO.getStuEmail());
		for (Student student2 : StudentDao.getInstance().getAllStudents()) {
			if (student2.getStuEmail().equalsIgnoreCase(stuEmail)) {
				isEmailMatched = true;
				student.setStuId(student2.getStuId());
				student.setStuCapDate(student2.getStuCapDate());
			}
			if (student2.getStuEmail().equalsIgnoreCase(studentRequestDTO.getStuEmail()))
				isEmailExists = true;
		}
		if (!isEmailMatched)
			throw new StudentNotFoundException("Given Email " + stuEmail + " not found");
		if (isEmailExists && !stuEmail.equalsIgnoreCase(studentRequestDTO.getStuEmail()))
			throw new StudentAlreadyExistsException(
					"Email " + student.getStuEmail() + " is already exists in our database");
		if (StudentDao.getInstance().update(student))
			return Response.status(Status.OK).entity(new StudentResponseDTO(student.getStuName(), student.getStuAge(),
					student.getStuEmail(), student.getStuCapDate())).build();
		else
			throw new TechnicalException("Due to some technical problem request could not be processed");
	}

	/**
	 * delete a student record
	 * 
	 * @param stuEmail
	 * @return
	 */
	public Response deleteStudent(String stuEmail) {
		for (Student student : StudentDao.getInstance().getAllStudents())
			if (student.getStuEmail().equalsIgnoreCase(stuEmail))
				if (StudentDao.getInstance().remove(student.getStuId()))
					return Response.status(Status.OK).entity(student).build();
				else
					throw new TechnicalException("Expectation failed please try again");
		throw new StudentNotFoundException("Record not found via email " + stuEmail);
	}

	/**
	 * getting all students
	 * 
	 * @return
	 */
	public Response getAllStudents() {
		List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();
		for (Student student : StudentDao.getInstance().getAllStudents())
			studentResponseDTOS.add(new StudentResponseDTO(student.getStuName(), student.getStuAge(),
					student.getStuEmail(), student.getStuCapDate()));
		return Response.status(Status.OK).entity(studentResponseDTOS).build();
	}

	/**
	 * getting student by name
	 * 
	 * @param stuName
	 * @return
	 */
	public Response getStudentsByName(String stuName) {
		List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();
		for (Student student : StudentDao.getInstance().getAllStudents())
			if (student.getStuName().equalsIgnoreCase(stuName))
				studentResponseDTOS.add(new StudentResponseDTO(student.getStuName(), student.getStuAge(),
						student.getStuEmail(), student.getStuCapDate()));
		return Response.status(Status.OK).entity(studentResponseDTOS).build();
	}

	/**
	 * getting students by email
	 * 
	 * @param stuEmail
	 * @return
	 */
	public Response getStudentsByEmail(String stuEmail) {
		List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();
		for (Student student : StudentDao.getInstance().getAllStudents())
			if (student.getStuEmail().equalsIgnoreCase(stuEmail))
				studentResponseDTOS.add(new StudentResponseDTO(student.getStuName(), student.getStuAge(),
						student.getStuEmail(), student.getStuCapDate()));
		return Response.status(Status.OK).entity(studentResponseDTOS).build();
	}

	/**
	 * getting students by age
	 * 
	 * @param stuAge
	 * @return
	 */
	public Response getStudentsByAge(int stuAge) {
		List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();
		for (Student student : StudentDao.getInstance().getAllStudents())
			if (student.getStuAge() == stuAge)
				studentResponseDTOS.add(new StudentResponseDTO(student.getStuName(), student.getStuAge(),
						student.getStuEmail(), student.getStuCapDate()));
		return Response.status(Status.OK).entity(studentResponseDTOS).build();
	}

	/**
	 * getting students by date ascending order
	 * 
	 * @return
	 */
	public Response getStudentsByAscDate() {
		Map<Date, StudentResponseDTO> studentResponseDTOMap = new HashMap<>();
		List<Date> dates = new ArrayList<>();
		List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();
		for (Student student : StudentDao.getInstance().getAllStudents()) {
			studentResponseDTOS.add(new StudentResponseDTO(student.getStuName(), student.getStuAge(),
					student.getStuEmail(), student.getStuCapDate()));
			studentResponseDTOMap.put(student.getStuCapDate(), new StudentResponseDTO(student.getStuName(),
					student.getStuAge(), student.getStuEmail(), student.getStuCapDate()));
			dates.add(student.getStuCapDate());
		}
		dates.sort((before, after) -> before.compareTo(after));
		studentResponseDTOS = new ArrayList<>();
		for (Date date : dates)
			studentResponseDTOS.add(studentResponseDTOMap.get(date));
		return Response.status(Status.OK).entity(studentResponseDTOS).build();
	}

	/**
	 * getting students by descending order
	 * 
	 * @return
	 */
	public Response getStudentsByDesDate() {
		Map<Date, StudentResponseDTO> studentResponseDTOMap = new HashMap<>();
		List<Date> dates = new ArrayList<>();
		List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();
		for (Student student : StudentDao.getInstance().getAllStudents()) {
			studentResponseDTOS.add(new StudentResponseDTO(student.getStuName(), student.getStuAge(),
					student.getStuEmail(), student.getStuCapDate()));
			studentResponseDTOMap.put(student.getStuCapDate(), new StudentResponseDTO(student.getStuName(),
					student.getStuAge(), student.getStuEmail(), student.getStuCapDate()));
			dates.add(student.getStuCapDate());
		}
		dates.sort((before, after) -> after.compareTo(before));
		studentResponseDTOS = new ArrayList<>();
		for (Date date : dates)
			studentResponseDTOS.add(studentResponseDTOMap.get(date));
		return Response.status(Status.OK).entity(studentResponseDTOS).build();
	}
}
