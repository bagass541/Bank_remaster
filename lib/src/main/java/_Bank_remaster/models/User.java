package _Bank_remaster.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class User {
	
	private long id;
	private String name;
	private String patronymic;
	private String surname;
	
	@Override
	public String toString() {
		return String.format("id: %d, surname: %s, name: %s, patronymic: %s", id,
				surname, name, patronymic);
	}
}
