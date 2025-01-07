package atminterface;

public class AccountImplimentation implements Account{
	private double balance;
	private int accountNumber;
	private String pin;

    public AccountImplimentation(int accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    @Override
    public int getAccountNum() {
        return accountNumber;
    }

    @Override
    public String getPin() {
        return pin;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
