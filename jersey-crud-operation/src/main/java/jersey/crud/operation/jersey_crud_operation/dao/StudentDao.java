package jersey.crud.operation.jersey_crud_operation.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.flywaydb.core.Flyway;

import jersey.crud.operation.jersey_crud_operation.db.constants.DbConstants;
import jersey.crud.operation.jersey_crud_operation.entity.Student;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class StudentDao {
	private static StudentDao studentDao;
	private static Connection connection;

	public static StudentDao getInstance() throws SQLException {
		if (connection == null) {
			try {
				Class.forName(DbConstants.DB_DRIVER_CLASS_NAME);
			} catch (ClassNotFoundException e) {
				log.error(e.toString());
			}
			connection = DriverManager.getConnection(DbConstants.DB_URL, DbConstants.DB_USERNAME,
					DbConstants.DB_PASSWORD);
		}
		if (studentDao == null) {
			studentDao = new StudentDao();
			Flyway flyway = Flyway.configure()
					.dataSource(DbConstants.DB_URL, DbConstants.DB_USERNAME, DbConstants.DB_PASSWORD).load();
			flyway.migrate();
		}
		return studentDao;
	}

	/**
	 * save data into database
	 * 
	 * @param student
	 * @return true or false
	 */
	public boolean save(Student student) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(DbConstants.REST_INSERT_STUDENT);
			preparedStatement.setLong(1, student.getStuId());
			preparedStatement.setString(2, student.getStuName());
			preparedStatement.setInt(3, student.getStuAge());
			preparedStatement.setString(4, student.getStuEmail());
			preparedStatement.setLong(5, student.getStuCapDate().getTime());
			if (preparedStatement.executeUpdate() > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			log.error(e.toString());
			return false;
		}
	}

	/**
	 * getting all students
	 * 
	 * @return
	 */
	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(DbConstants.REST_FETCH_STUDENTS);
			while (resultSet.next())
				students.add(new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
						resultSet.getString(4), new Date(resultSet.getLong(5))));
			resultSet.close();
			return students;
		} catch (Exception e) {
			log.error(e.toString());
			return students;
		}
	}

	/**
	 * delete student record
	 * 
	 * @param stuId
	 * @return
	 */
	public boolean remove(long stuId) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(DbConstants.REST_DELETE_STUDENT);
			preparedStatement.setLong(1, stuId);
			if (preparedStatement.executeUpdate() > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			log.error(e.toString());
			return false;
		}
	}

	/**
	 * update student record
	 * 
	 * @param student
	 * @return
	 */
	public boolean update(Student student) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(DbConstants.REST_UPDATE_STUDENT);
			preparedStatement.setString(1, student.getStuName());
			preparedStatement.setInt(2, student.getStuAge());
			preparedStatement.setString(3, student.getStuEmail());
			preparedStatement.setLong(4, student.getStuId());
			if (preparedStatement.executeUpdate() > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			log.error(e.toString());
			return false;
		}
	}
}
