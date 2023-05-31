package Banks.Entities;

import Banks.Exceptions.AccountNotFoundException;
import Banks.Exceptions.MoneyException;
import Banks.Interfaces.BankAccount;
import Banks.Interfaces.BankService;
import Banks.Interfaces.BankTransaction;
import Banks.Models.BankSystem;
import Banks.Models.CreditAccount;
import Banks.Models.DebitAccount;
import Banks.Models.DepositAccount;

import java.time.LocalDate;
import java.util.*;

public class BankImpl implements BankService {

    private final List<Client> clients = new ArrayList<>();
    private final List<BankTransaction> transactions = new ArrayList<>();
    private final List<BankAccount> accounts = new ArrayList<>();
    public String name;
    private final BankSystem bankSystem;
    public UUID id;

    /*
     * Create a Bank.
     */
    public BankImpl(String name, BankSystem bankSystem) throws Exception {
        Objects.requireNonNull(bankSystem);

        if (name == null || name.trim().isEmpty()) {
            throw new Exception();
        }

        this.name = name;
        this.bankSystem = bankSystem;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public BankSystem getBankSystem() {
        return bankSystem;
    }

    public String getName() {
        return name;
    }

    public List<BankTransaction> getBankTransaction() {
        return Collections.unmodifiableList(transactions);
    }

    public List<BankAccount> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

    /*
     * Add a client to the client.
     */
    public Client addPassport(UUID id, int passport) {
        Objects.requireNonNull(id);

        return clients.stream().filter(person -> person.getId().equals(id)).findFirst()
                .orElse(null);
    }

    /*
     * Add an address to the client.
     */
    public Client addAddress(UUID id, String address) throws Exception {
        Objects.requireNonNull(id);

        if (address == null || address.trim().isEmpty()) {
            throw new Exception();
        }

        Client client = clients.stream().filter(person -> person.getId().equals(id)).findFirst()
                .orElse(null);
        Objects.requireNonNull(client).changeAddressInformation(address);

        return client;
    }

    /*
     * Add a client to the client.
     */
    public Client addClient(Client client) {
        if (clients.contains(client)) {
            throw new IllegalArgumentException();
        }

        clients.add(client);
        return client;
    }

    /*
     * Get client by id.
     */
    public Client getClient(UUID id) {
        Objects.requireNonNull(id);

        return clients.stream().filter(person -> person.getId().equals(id)).findFirst().orElse(null);
    }

    /*
     * Get client by id.
     */
    public Client getClient(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        Client client = clients.stream().filter(person -> person.getFirstName().equals(name))
                .findFirst().orElse(null);

        if (client == null) {
            throw new IllegalArgumentException();
        }

        return client;
    }

    /*
     * Add a new wallet.
     */
    public UUID addDebit(Client client) {
        Objects.requireNonNull(client);

        if (!clients.contains(client)) {
            clients.add(client);
        }

        DebitAccount account = new DebitAccount(client, bankSystem.getYearPercent());
        accounts.add(account);

        return account.getId();
    }

    /*
     * Add a deposit to the client.
     */
    public UUID addDeposit(Client client, LocalDate depositEnd) {
        Objects.requireNonNull(client);

        if (!clients.contains(client)) {
            clients.add(client);
        }

        DepositAccount account = new DepositAccount(client.getId(), bankSystem.getHighPercent(),
                bankSystem.getMediumPercent(), bankSystem.getLowPercent(), depositEnd);
        accounts.add(account);

        return account.getId();
    }

    /*
     * Add a credit to the account.
     */
    public UUID addCredit(Client client) {
        Objects.requireNonNull(client);

        if (!clients.contains(client)) {
            clients.add(client);
        }

        CreditAccount account = new CreditAccount(bankSystem.getCreditLimit(),
                bankSystem.getYearPercent(), client.getId());
        accounts.add(account);

        return account.getId();
    }

    /*
     * Change credit limit.
     */
    public double changeCreditLimit(double newLimit) {
        if (newLimit < 0) {
            throw new MoneyException("Money < 0");
        }

        bankSystem.changeCreditLimit(newLimit);

        return newLimit;
    }

    /*
     * Change the creditful client.
     */
    public double changeDoubtfulClientLimit(double newLimit) {
        if (newLimit < 0) {
            throw new MoneyException("Negative credit limit is not allowed.");
        }
        bankSystem.changeDoubtfulClientLimit(newLimit);
        return newLimit;
    }

    /*
     * Deposit.
     */
    public void depositTransaction(UUID accountId, double money) {
        Objects.requireNonNull(accountId, "Account ID cannot be null.");
        if (money < 0) {
            throw new MoneyException("Negative money value is not allowed.");
        }
        accounts.stream()
                .filter(account -> account.getId().equals(accountId))
                .findFirst()
                .ifPresent(account -> {
                    try {
                        account.depositMoney(money);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    /*
     * Invoke a transaction.
     */
    public void withdrawTransaction(UUID accountId, double money) throws Exception {
        Objects.requireNonNull(accountId, "Account ID cannot be null.");
        BankAccount account = accounts.stream()
                .filter(a -> a.getId().equals(accountId))
                .findFirst()
                .orElseThrow(
                        () -> new AccountNotFoundException("Account with ID " + accountId + " not found."));
        if (account.getMoney() - money < 0) {
            throw new MoneyException("Insufficient funds in account " + accountId);
        }
        account.withdrawMoney(money);
    }

    /*
     * Create a transfer transaction.
     */
    public void transferTransaction(UUID withdraw, UUID deposit, double money) throws Exception {
        Objects.requireNonNull(withdraw, "Withdraw account ID cannot be null.");
        Objects.requireNonNull(deposit, "Deposit account ID cannot be null.");
        if (money < 0) {
            throw new MoneyException("Negative money value is not allowed.");
        }
        BankAccount withdrawAccount = accounts.stream()
                .filter(a -> a.getId().equals(withdraw))
                .findFirst()
                .orElseThrow(
                        () -> new AccountNotFoundException("Account with ID " + withdraw + " not found."));
        BankAccount depositAccount = accounts.stream()
                .filter(a -> a.getId().equals(deposit))
                .findFirst()
                .orElseThrow(
                        () -> new AccountNotFoundException("Account with ID " + deposit + " not found."));
        withdrawAccount.withdrawMoney(money);
        depositAccount.depositMoney(money);
    }

    /*
     * Print the list of clients.
     */
    public void showClients() {
        for (Client client : clients) {
            System.out.println(client.getFirstName());
        }
    }

    /*
     * Return a string representation of the Bank.
     */
    @Override
    public String toString() {
        return new StringBuilder()
                .append("Bank: \n")
                .append("name: ")
                .append(name)
                .append("\nSettings: \n")
                .append("Commission: ")
                .append(bankSystem.getCommission())
                .append("\nCredit Limit: ")
                .append(bankSystem.getCreditLimit())
                .append("\nYear percent: ")
                .append(bankSystem.getYearPercent())
                .toString();
    }
}
