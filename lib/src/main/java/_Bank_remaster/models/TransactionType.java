package _Bank_remaster.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TransactionType {
	DEPOSIT("Пополнение"),
	WITHDRAW("Cнятие средств"),
	TRANSFER("Перевод");
	
	private final String translate;
	
}
