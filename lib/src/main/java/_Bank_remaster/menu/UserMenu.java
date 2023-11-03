package _Bank_remaster.menu;

import _Bank_remaster.exceptions.UserNotFoundException;
import _Bank_remaster.models.User;
import _Bank_remaster.repositories.UserRepository;
/*
 * The user menu for CRUD-operations with users.
 */
public class UserMenu extends CRUDMenu {
	
	private final UserRepository userRepo;

	private final String MENU = """
			\n----------------------------
			1: Просмотреть всех пользователей
			2: Добавить пользователя
			3: Изменить пользователя
			4: Удалить пользователя
			5: Вернуться назад
			----------------------------""";
	
	
	
	public UserMenu(UserRepository userRepo) {
		this.userRepo = userRepo;
	}



	@Override
	public void start() {
		while(true) {
			printMenu(MENU);
			switch(scanner.nextInt()) {
				case 1 -> {
					userRepo.findAllUsers().forEach(System.out::println);
				}
				case 2 -> {
					User user = new User();				
					askPropeties(user);					
					userRepo.createUser(user);
					
				}
				case 3 -> {
					User user = null;
					System.out.println("\nВведите id пользователя: ");
					try {
						user = userRepo.findById(scanner.nextLong());
					} catch (UserNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println(user);
					
					askPropeties(user);
					
					userRepo.updateUser(user);
				}
				case 4 -> {
					System.out.println("\nВведите id пользователя, которого нужно удалить: ");
					userRepo.deleteUser(scanner.nextLong());
				}
				case 5 -> {
					returnToPreviousMenu();
				}
			}
		}
		
	}
	
	/*
	 * Requests user input and sets properties for an user object.
	 * 
	 * @param user The user object for which properties need to be set.
	 */
	private void askPropeties(User user) {
		System.out.println("\nВведите фамилию пользователя: ");
		user.setSurname(scanner.next());
		
		System.out.println("\nВведите имя пользователя: ");
		user.setName(scanner.next());
		
		System.out.println("\nВведите отчество пользователя: ");
		user.setPatronymic(scanner.next());
	}

}
