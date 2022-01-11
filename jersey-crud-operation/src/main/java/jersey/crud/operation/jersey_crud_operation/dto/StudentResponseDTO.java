package jersey.crud.operation.jersey_crud_operation.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDTO {
	private String stuName;
	private int stuAge;
	private String stuEmail;
	private Date stuCapDate;
}
