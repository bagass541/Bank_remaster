package _Bank_remaster.menu;


import _Bank_remaster.exceptions.BankNotFoundException;
import _Bank_remaster.models.Bank;
import _Bank_remaster.repositories.BankRepository;

public class BankMenu extends CRUDMenu{

	private final BankRepository bankRepo;
	
	private final String MENU = """
			\n----------------------------
			1: Просмотреть все банки
			2: Добавить банк
			3: Изменить банк
			4: Удалить банк
			5: Вернуться назад
			----------------------------""";
	
	public BankMenu(BankRepository bankRepo) {
		this.bankRepo = bankRepo;
	}


	@Override
	public void start() {
		while(true) {
			printMenu(MENU);
			switch(scanner.nextInt()) {
				case 1 -> {
					bankRepo.findAllBanks().forEach(System.out::println);
				}
				case 2 -> {
					Bank bank = new Bank();
					System.out.println("\nВведите название банка: ");
					bank.setName(scanner.next());
					
					bankRepo.createBank(bank);
				}
				case 3 -> {
					Bank bank = null;
					System.out.println("\nВведите id банка, который желаете изменить: ");
					try {
						bank = bankRepo.findById(scanner.nextLong());
					} catch (BankNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("\nВведите новое имя банка: ");
					bank.setName(scanner.next());
					
					bankRepo.updateBank(bank);
				}
				case 4 -> {
					System.out.println("\nВведите id банка, который нужно удалить: ");
					bankRepo.deleteBank(scanner.nextLong());
				}
				case 5 ->{
					returnToPreviousMenu();
				}
			}
		}
		
	}

}
