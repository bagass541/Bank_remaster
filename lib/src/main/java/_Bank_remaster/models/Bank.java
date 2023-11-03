package _Bank_remaster.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Model of a bank.
 */

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Bank {
	private long id;
	private String name;
	
	@Override
	public String toString() {
		return String.format("id: %d, name: %s", id, name);
	}
}
