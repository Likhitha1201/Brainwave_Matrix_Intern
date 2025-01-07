package atminterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATMInterface {
	private Map<Integer, Account> accounts;
	private Scanner scanner;

	public ATMInterface() {
		this.accounts = new HashMap<>();
		this.scanner = new Scanner(System.in);

		// Initialize some accounts for testing
		accounts.put(623456789, new AccountImplimentation(623456789, "4567", 500.0));
		accounts.put(567832213, new AccountImplimentation(567832213, "2324", 1000.0));
	}

	public void run() {
		System.out.println("Welcome to the ATM!");

		while (true) {
			System.out.println("Enter your account number:");
			int accountNumber = scanner.nextInt();

			if (accounts.containsKey(accountNumber)) {
				Account account = accounts.get(accountNumber);
				System.out.println("Enter your PIN:");
				String pin = scanner.next();

				if (pin.equals(account.getPin())) {
					System.out.println("Welcome, account holder!");

					while (true) {
						System.out.println("Choose an option: \n 1. Check balance \n 2. Withdraw cash \n 3. Deposit money \n 4. Exit");
						
						int option = scanner.nextInt();

						switch (option) {
						case 1:
							System.out.println("Your balance is: " + account.getBalance());
							break;
						case 2:
							System.out.println("Amount available in 100 500");
							System.out.println("Enter the amount to withdraw:");
							double amount = scanner.nextDouble();

							if (amount > account.getBalance()) {
								System.out.println("Insufficient balance!");
							} 
							else if(amount%10!=0 || amount<100) {
								System.out.println("Invalid amount, please enter the amount in the form of 100's & 500's!");
							}
							else {
								account.setBalance(account.getBalance() - amount);
								System.out.println("Withdrawal successful. \nNew balance: " + account.getBalance());
							}
							break;
						case 3:
							System.out.println("Enter the amount to deposit:");
							amount = scanner.nextDouble();

							account.setBalance(account.getBalance() + amount);
							System.out.println("Deposit successful. New balance: " + account.getBalance());
							break;
						case 4:
							System.out.println("Thank you for using the ATM!");
							return;
						default:
							System.out.println("Invalid option. Please choose again.");
						}
					}
				} else {
					System.out.println("Incorrect PIN. Please try again.");
				}
			} else {
				System.out.println("Account not found. Please try again.");
			}
		}
	}

	public static void main(String[] args) {
		ATMInterface atm = new ATMInterface();
		atm.run();
	}
}
