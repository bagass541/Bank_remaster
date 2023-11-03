package _Bank_remaster.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
 * Enum of transaction types.
 */

@RequiredArgsConstructor
@Getter
public enum TransactionType {
	DEPOSIT("Пополнение"),
	WITHDRAW("Cнятие средств"),
	TRANSFER("Перевод");
	
	private final String translate;
	
}
