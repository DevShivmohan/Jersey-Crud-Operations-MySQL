package jersey.crud.operation.jersey_crud_operation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDTO {
	private String stuName;
	private int stuAge;
	private String stuEmail;
}
