package jersey.crud.operation.jersey_crud_operation.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	private long stuId;
	private String stuName;
	private int stuAge;
	private String stuEmail;
	private Date stuCapDate;
}
