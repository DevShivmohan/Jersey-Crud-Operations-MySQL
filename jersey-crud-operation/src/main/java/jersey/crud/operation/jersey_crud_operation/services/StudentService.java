package jersey.crud.operation.jersey_crud_operation.services;

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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	 */
	public Response addStudent(StudentRequestDTO studentRequestDTO) {
		try {
			Student student = new Student();
			student.setStuName(studentRequestDTO.getStuName());
			student.setStuAge(studentRequestDTO.getStuAge());
			student.setStuEmail(studentRequestDTO.getStuEmail());
			student.setStuCapDate(new Date());
			for (Student student2 : StudentDao.getInstance().getAllStudents())
				if (student2.getStuEmail().equalsIgnoreCase(student.getStuEmail()))
					return Response.status(Status.NOT_ACCEPTABLE)
							.entity("Student Email " + student.getStuEmail() + " is already exist in our database")
							.build();
			if (StudentDao.getInstance().save(student))
				return Response.status(Status.OK).entity(new StudentResponseDTO(student.getStuName(),
						student.getStuAge(), student.getStuEmail(), student.getStuCapDate())).build();
			else
				return Response.status(Status.EXPECTATION_FAILED)
						.entity("Due to technical problem your request could not processed").build();
		} catch (Exception e) {
			log.error(e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Technical issue please try again").build();
		}
	}

	/**
	 * update a student record
	 * 
	 * @param studentRequestDTO
	 * @param stuEmail
	 * @return
	 */
	public Response updateStudent(StudentRequestDTO studentRequestDTO, String stuEmail) {
		try {
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
				return Response.status(Status.NOT_FOUND).entity("Given Email " + stuEmail + " not found").build();
			if (isEmailExists && !stuEmail.equalsIgnoreCase(studentRequestDTO.getStuEmail()))
				return Response.status(Status.NOT_ACCEPTABLE)
						.entity("Email " + student.getStuEmail() + " is already exists in our database").build();
			if (StudentDao.getInstance().update(student))
				return Response.status(Status.OK).entity(new StudentResponseDTO(student.getStuName(),
						student.getStuAge(), student.getStuEmail(), student.getStuCapDate())).build();
			else
				return Response.status(Status.EXPECTATION_FAILED)
						.entity("Due to some technical problem request could not be processed").build();
		} catch (Exception e) {
			log.error(e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Technical issue please try again").build();
		}
	}

	/**
	 * delete a student record
	 * 
	 * @param stuEmail
	 * @return
	 */
	public Response deleteStudent(String stuEmail) {
		try {
			for (Student student : StudentDao.getInstance().getAllStudents())
				if (student.getStuEmail().equalsIgnoreCase(stuEmail))
					if (StudentDao.getInstance().remove(student.getStuId()))
						return Response.status(Status.OK).entity(student).build();
					else
						return Response.status(Status.EXPECTATION_FAILED).entity("Expectation failed please try again")
								.build();
			return Response.status(Status.NOT_FOUND).entity("Record not found via email " + stuEmail).build();
		} catch (Exception e) {
			log.error(e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Technical issue please try again").build();
		}
	}

	/**
	 * getting all students
	 * 
	 * @return
	 */
	public Response getAllStudents() {
		try {
			List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();
			for (Student student : StudentDao.getInstance().getAllStudents())
				studentResponseDTOS.add(new StudentResponseDTO(student.getStuName(), student.getStuAge(),
						student.getStuEmail(), student.getStuCapDate()));
			return Response.status(Status.OK).entity(studentResponseDTOS).build();
		} catch (Exception e) {
			log.error(e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Technical issue please try again").build();
		}
	}

	/**
	 * getting student by name
	 * 
	 * @param stuName
	 * @return
	 */
	public Response getStudentsByName(String stuName) {
		try {
			List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();
			for (Student student : StudentDao.getInstance().getAllStudents())
				if (student.getStuName().equalsIgnoreCase(stuName))
					studentResponseDTOS.add(new StudentResponseDTO(student.getStuName(), student.getStuAge(),
							student.getStuEmail(), student.getStuCapDate()));
			return Response.status(Status.OK).entity(studentResponseDTOS).build();
		} catch (Exception e) {
			log.error(e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Technical issue please try again").build();
		}
	}

	/**
	 * getting students by email
	 * 
	 * @param stuEmail
	 * @return
	 */
	public Response getStudentsByEmail(String stuEmail) {
		try {
			List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();
			for (Student student : StudentDao.getInstance().getAllStudents())
				if (student.getStuEmail().equalsIgnoreCase(stuEmail))
					studentResponseDTOS.add(new StudentResponseDTO(student.getStuName(), student.getStuAge(),
							student.getStuEmail(), student.getStuCapDate()));
			return Response.status(Status.OK).entity(studentResponseDTOS).build();
		} catch (Exception e) {
			log.error(e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Technical issue please try again").build();
		}
	}

	/**
	 * getting students by age
	 * 
	 * @param stuAge
	 * @return
	 */
	public Response getStudentsByAge(int stuAge) {
		try {
			List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();
			for (Student student : StudentDao.getInstance().getAllStudents())
				if (student.getStuAge() == stuAge)
					studentResponseDTOS.add(new StudentResponseDTO(student.getStuName(), student.getStuAge(),
							student.getStuEmail(), student.getStuCapDate()));
			return Response.status(Status.OK).entity(studentResponseDTOS).build();
		} catch (Exception e) {
			log.error(e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Technical issue please try again").build();
		}
	}

	/**
	 * getting students by date ascending order
	 * 
	 * @return
	 */
	public Response getStudentsByAscDate() {
		try {
			List<Date> dates = new ArrayList<>();
			List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();
			for (Student student : StudentDao.getInstance().getAllStudents())
				studentResponseDTOS.add(new StudentResponseDTO(student.getStuName(), student.getStuAge(),
						student.getStuEmail(), student.getStuCapDate()));
			Map<Date, StudentResponseDTO> studentResponseDTOMap = new HashMap<>();
			for (StudentResponseDTO studentResponseDTO : studentResponseDTOS) {
				studentResponseDTOMap.put(studentResponseDTO.getStuCapDate(), studentResponseDTO);
				dates.add(studentResponseDTO.getStuCapDate());
			}
			dates.sort((before, after) -> before.compareTo(after));
			studentResponseDTOS = new ArrayList<>();
			for (Date date : dates)
				studentResponseDTOS.add(studentResponseDTOMap.get(date));
			return Response.status(Status.OK).entity(studentResponseDTOS).build();
		} catch (Exception e) {
			log.error(e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Technical issue please try again").build();
		}
	}

	/**
	 * getting students by descending order
	 * 
	 * @return
	 */
	public Response getStudentsByDesDate() {
		try {
			List<Date> dates = new ArrayList<>();
			List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();
			for (Student student : StudentDao.getInstance().getAllStudents())
				studentResponseDTOS.add(new StudentResponseDTO(student.getStuName(), student.getStuAge(),
						student.getStuEmail(), student.getStuCapDate()));
			Map<Date, StudentResponseDTO> studentResponseDTOMap = new HashMap<>();
			for (StudentResponseDTO studentResponseDTO : studentResponseDTOS) {
				studentResponseDTOMap.put(studentResponseDTO.getStuCapDate(), studentResponseDTO);
				dates.add(studentResponseDTO.getStuCapDate());
			}
			dates.sort((before, after) -> after.compareTo(before));
			studentResponseDTOS = new ArrayList<>();
			for (Date date : dates)
				studentResponseDTOS.add(studentResponseDTOMap.get(date));
			return Response.status(Status.OK).entity(studentResponseDTOS).build();
		} catch (Exception e) {
			log.error(e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Technical issue please try again").build();
		}
	}
}
