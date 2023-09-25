package _Bank_remaster.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	private long id;
	private String accountNumber;
	private BigDecimal balance;
	private User user;
	private Bank bank;
	private LocalDate openingDate;
	
	
}
