package _Bank_remaster.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Model of a user.
 */

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class User {
	
	private long id;
	private String name;
	private String patronymic;
	private String surname;
	
	/*
	 * Concatenates surname, name and patronymic.
	 * 
	 * @return full name of the user.
	 */
	public String getFullName() {
		return surname + " " + name + " " + patronymic;
	}
	
	@Override
	public String toString() {
		return String.format("id: %d, surname: %s, name: %s, patronymic: %s", id,
				surname, name, patronymic);
	}
}
