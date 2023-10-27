package _Bank_remaster.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	private static AtomicLong accountCounter = new AtomicLong(59L);
	
	private long id;
	private String accountNumber;
	private BigDecimal balance;
	private User user;
	private Bank bank;
	private LocalDate openingDate;
	
	
	public void generateAccountNumber() {
		accountNumber = String.format("%010d", accountCounter.incrementAndGet());
	}
	
	
	

	@Override
	public String toString() {
		return String.format("id: %d, number: %s, balance: %.2f, user_id: %d, bank_id: %d, opening date: %s", 
				id, accountNumber, balance, user.getId(), bank.getId(), openingDate);
	}
	
}
