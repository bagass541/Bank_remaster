package _Bank_remaster.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class Transaction {

	private long id;
	private LocalDateTime time;
	private Account senderAccount;
	private Account recieverAccount;
	private BigDecimal amount;
	private TransactionType type;
	
	public Transaction(){
		this.time = LocalDateTime.now();
	}
}
