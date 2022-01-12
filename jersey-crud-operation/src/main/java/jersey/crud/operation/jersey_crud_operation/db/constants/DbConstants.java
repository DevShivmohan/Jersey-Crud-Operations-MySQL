package jersey.crud.operation.jersey_crud_operation.db.constants;

public class DbConstants {

	/* DB software driver Constants */
	public static final String DB_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

	/* DB Credentials Constants */
	public static final String DB_URL = "jdbc:mysql://localhost:3306/students";
	public static final String DB_USERNAME = "root";
	public static final String DB_PASSWORD = "1010101Shiv@";

	/* DB Query Constants */
	public static final String REST_INSERT_STUDENT = "insert into students (stuId,stuName,stuAge,stuEmail,stuCapDate) values(?,?,?,?,?);";
	public static final String REST_FETCH_STUDENTS = "select * from students;";
	public static final String REST_DELETE_STUDENT = "delete from students where stuId=?;";
	public static final String REST_UPDATE_STUDENT = "update students set stuName=?,stuAge=?,stuEmail=? where stuId=?;";
}
