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
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			log.error(e.toString());
		}
		if (connection == null)
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "1010101Shiv@");
		createTable();
		if (studentDao == null)
			studentDao = new StudentDao();
		return studentDao;
	}

	/**
	 * create table initially
	 */
	private static void createTable() {
		try {
			Statement statement = connection.createStatement();
			String query = "CREATE TABLE IF NOT EXISTS students (" + "stuId INT AUTO_INCREMENT NOT NULL,"
					+ "  stuName VARCHAR(255) NULL," + "  stuAge INT NOT NULL," + "  stuEmail VARCHAR(255) NULL,"
					+ "stuCapDate LONG NOT NULL," + "  CONSTRAINT pk_students PRIMARY KEY (stuId)" + ");";
			boolean status = statement.execute(query);
			log.info("Creating table query status " + status);
		} catch (Exception e) {
			log.error(e.toString());
		}
	}

	/**
	 * save data into database
	 * 
	 * @param student
	 * @return true or false
	 */
	public boolean save(Student student) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"insert into students (stuId,stuName,stuAge,stuEmail,stuCapDate) values(?,?,?,?,?);");
			preparedStatement.setLong(1, student.getStuId());
			preparedStatement.setString(2, student.getStuName());
			preparedStatement.setInt(3, student.getStuAge());
			preparedStatement.setString(4, student.getStuEmail());
			preparedStatement.setLong(5, student.getStuCapDate().getTime());
			preparedStatement.executeUpdate();
			return true;
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
		List<Student> students = new ArrayList<Student>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from students;");
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
			Statement statement = connection.createStatement();
			statement.executeUpdate("delete from students where stuId=" + stuId + ";");
			return true;
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
			Statement statement = connection.createStatement();
			statement.executeUpdate(
					"update students set stuName='" + student.getStuName() + "',stuAge=" + student.getStuAge()
							+ ",stuEmail='" + student.getStuEmail() + "' where stuId=" + student.getStuId() + ";");
			return true;
		} catch (Exception e) {
			log.error(e.toString());
			return false;
		}
	}
}
