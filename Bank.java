import java.util.Scanner;
import java.util.ArrayList;

// Base class representing a bank account
class Account {
    private int accountNumber;      // Unique identifier for the account
    private String accountOwner;    // The Owner of the account
    private double balance;         // Current balance of the account
    private String accountType;     // Type of account (savings or checking)
    private boolean active;         // Indicates whether the account is active or not

    // Constructor for creating an Account object
    Account(int accountNumber, String accountOwner, double balance, String accountType, boolean active) {
        this.accountNumber = accountNumber;
        this.accountOwner = accountOwner;
        this.balance = balance;
        this.accountType = accountType;
        this.active = active;
    }

    // Getter methods for retrieving account information
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean isActive() {
        return active;
    }

    // Methods for depositing funds into the account
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit successful. (Balance: " + balance + " OMR)");
    }

    // Methods for withdrawing funds from the account
    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds.");
        } else {
            balance -= amount;
            System.out.println("Withdrawal successful. (Balance: " + balance + " OMR)");
        }
    }
}

// Subclass representing a savings account, inheriting from Account
class SavingsAccount extends Account {
    // Constructor for creating a SavingsAccount object
    SavingsAccount(int accountNumber, String accountOwner, double balance, String accountType, boolean active) {
        super(accountNumber, accountOwner, balance, accountType, active);
    }

    // Constructor overloading to use without parameters
    SavingsAccount() {
        super(100, "Defualt", 5000, "Basic", true);
    }

    // Method Overriding - Provides specific implementation for savings account withdrawals
    public void withdraw(double amount) {
        if (amount > getBalance()) {
            System.out.println("Withdrawal amount exceeds balance for Savings Account.");
        } else {
            super.withdraw(amount);
        }
    }
}

// ATM class for user interactions
class ATM {
    private Account currentAccount;  // The currently selected account for transactions

    // Setter for the current account
    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    // Display the balance of the current account
    public void checkBalance() {
        System.out.println("Balance: " + currentAccount.getBalance() + " OMR");
    }

    // Deposit funds into the current account
    public void deposit(double amount) {
        currentAccount.deposit(amount);
    }

    // Withdraw funds from the current account
    public void withdraw(double amount) {
        currentAccount.withdraw(amount);
    }
}

// Main class for running the Bank System
public class Bank {
    public static void main(String[] args) {
        ArrayList<SavingsAccount> accounts = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        accounts.add(new SavingsAccount());
        accounts.add(new SavingsAccount(200, "Ali", 1000.10, "Basic", true));
        accounts.add(new SavingsAccount(300, "Salim", 1500.50, "Premium", true));
        accounts.add(new SavingsAccount(400, "Ahmed", 300.60, "Basic", false));
        accounts.add(new SavingsAccount(500, "Mohammed", 650.80, "Premium", true));

        System.out.println("===========================");
        System.out.println("Available Accounts:");
        double[] balances = new double[accounts.size()];
        double maxBalance = 0;
        double minBalance = 999999999;
        int i = 0;
        for (SavingsAccount ac : accounts) {
            System.out.println("ID:" + ac.getAccountNumber() + "\tOwner:" + ac.getAccountOwner() + "\tBalance:" + ac.getBalance() + "\tType:" + ac.getAccountType() + "\tActive:" + ac.isActive());
            balances[i] = ac.getBalance();
            maxBalance = Math.max(maxBalance, balances[i]);
            minBalance = Math.min(minBalance, balances[i]);
            i++;
        }
        System.out.println("------------------");
        System.out.println("Highest account balance: " + maxBalance);
        System.out.println("Lowest account balance: " + minBalance);
        System.out.println("===========================");

        ATM atm = new ATM();
        boolean choosed = false;
        while (choosed == false) {
            System.out.print("Enter Account ID to operate: ");
            int accountChoice = scanner.nextInt();
            for (SavingsAccount ac : accounts) {
                if (accountChoice == ac.getAccountNumber()) {
                    if (ac.isActive() == true) {
                        atm.setCurrentAccount(ac);
                        System.out.println("Account Choosed: " + ac.getAccountOwner().toUpperCase());
                        choosed = true;
                        break;
                    } else {
                    System.out.println("Wrong ID or account is not Active.");
                    System.out.println("===========================");
                    break;
                    }
                }
            }
        }
        
        // Display menu
        System.out.println("===========================");
        System.out.println("ATM Options:");
        System.out.println("1  : Change Account");
        System.out.println("2  : Check Balance");
        System.out.println("3  : Deposit");
        System.out.println("4  : Withdraw");
        System.out.println("5  : Create Account");
        System.out.println("6  : Show Accounts");
        System.out.println("Any: Exit");
        System.out.println("===========================");

        while (true) {
            System.out.print("Enter your choice: ");
            String choice = scanner.next();

            // Perform user-selected action
            switch (choice) {
                case "1":
                    System.out.print("Enter Account ID to operate: ");
                    int accountChoice = scanner.nextInt();
                    for (SavingsAccount ac : accounts) {
                        if (accountChoice == ac.getAccountNumber()) {
                            if (ac.isActive() == true) {
                                atm.setCurrentAccount(ac);
                                System.out.println("Account Choosed: " + ac.getAccountOwner().toUpperCase());
                                choosed = true;
                                break;
                            } else {
                            System.out.println("Wrong ID or account is not Active.");
                            break;
                            }
                        }
                    }
                    break;

                case "2":
                    atm.checkBalance();
                    break;

                case "3":
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    atm.deposit(depositAmount);
                    break;

                case "4":
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    atm.withdraw(withdrawalAmount);
                    break;

                case "5":
                    System.out.print("Enter account id: ");
                    int id = scanner.nextInt();

                    boolean idExist = false;
                    for (SavingsAccount ac : accounts) {
                        if (ac.getAccountNumber() == id) {
                            System.out.println("ID already exists, try again..");
                            idExist = true;
                        }
                    }

                    if (idExist) {break;}

                    System.out.print("Enter account owner: ");
                    String name = scanner.next();

                    System.out.print("Enter account balance: ");
                    double balance = scanner.nextDouble();

                    System.out.println("Enter account type: ");
                    System.out.println("1: Basic");
                    System.out.println("2: Premium");
                    System.out.print("Choice: ");
                    int type = scanner.nextInt();

                    System.out.print("Enter account active: ");
                    boolean active = scanner.nextBoolean();

                    String accountType = "";
                    if (type == 1) {accountType = "Basic";}
                    else if (type == 2) {accountType = "Premium";}

                    accounts.add(new SavingsAccount(id, name, balance, accountType, active));
                    break;

                case "6":
                    for (SavingsAccount ac : accounts) {
                        System.out.println("ID:" + ac.getAccountNumber() + "\tOwner:" + ac.getAccountOwner() + "\tBalance:" + ac.getBalance() + "\tType:" + ac.getAccountType() + "\tActive:" + ac.isActive());
                    }
                    break;

                default:
                    System.out.println("Exiting ATM...");
                    scanner.close();
                    System.exit(0);
            }
            System.out.println("===========================");
        }
    }
}