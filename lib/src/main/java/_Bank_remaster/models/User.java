package _Bank_remaster.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {
	
	private long id;
	private String name;
	private String patronymic;
	private String surname;
	
}
