package Banks.Entities;

import Banks.Exceptions.MoneyException;
import Banks.Interfaces.*;
import Banks.Models.BankSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CentralBankImpl implements CentralBankService, Observable {
    private final List<BankImpl> banks = new ArrayList<>();
    private final List<Observer> observers = new ArrayList<>();

    public List<BankImpl> getBanks() {
        return banks;
    }

    /*
     * Add a Bank.
     */
    @Override
    public BankImpl addBank(String name, BankSystem bankSystem) throws Exception {
        if (bankSystem == null) {
            throw new NullPointerException();
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        BankImpl bankImpl = new BankImpl(name, bankSystem);
        banks.add(bankImpl);
        return bankImpl;
    }

    /*
     * Prints all banks.
     */
    public void showBanks() {
        for (BankImpl bankImpl : banks) {
            System.out.println(bankImpl.toString());
        }
    }

    /*
     * Get Bank by name.
     */
    @Override
    public BankImpl getBank(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }

        for (BankImpl bankImpl : banks) {
            if (bankImpl.getName().equals(name)) {
                return bankImpl;
            }
        }

        throw new IllegalArgumentException();
    }

    /*
     * Get Bank by name.
     */
    @Override
    public BankImpl getBank(UUID id) {
        if (id == null) {
            throw new NullPointerException();
        }

        for (BankImpl bankImpl : banks) {
            if (bankImpl.getId().equals(id)) {
                return bankImpl;
            }
        }

        throw new IllegalArgumentException();
    }

    /*
     * Returns the amount of funds in a Bank.
     */
    @Override
    public double transactionBeetweenBanks(BankTransaction transaction, double money, BankImpl oldBankImpl, BankImpl newBankImpl, UUID id) {
        if (transaction == null || oldBankImpl == null || newBankImpl == null || id == null) {
            throw new NullPointerException();
        }

        if (money > 0) {
            throw new MoneyException("Amount of money cannot be negative.");
        }

        return money;
    }

    /*
     * Calculate the commission of the Bank.
     */
    public void speedUpTime(int numberOfMonth) {
        for (int i = 0; i < numberOfMonth; i++) {
            for (BankImpl bankImpl : banks) {
                for (BankAccount account : bankImpl.getAccounts()) {
                    account.commissionPayment();
                    account.commission();
                }
            }
        }
    }

    /*
     * Update the observers.
     */
    public void Notification() {
        for (Observer observer : observers) {
            observer.Update(this);
        }
    }

    /*
     * Adds an observer to the reader.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
}
