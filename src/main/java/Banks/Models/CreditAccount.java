package Banks.Models;

import Banks.Interfaces.BankAccount;

import java.util.UUID;

public class CreditAccount implements BankAccount {

    private double _creditLimit;
    private double _money;
    private double _percentage;
    private UUID Id;
    private UUID ClientId;

    public CreditAccount(double creditLimit, double percentage, UUID clientId) {
        _creditLimit = creditLimit;
        _percentage = percentage;
        ClientId = clientId;
        Id = UUID.randomUUID();
    }

    public UUID getId() {
        return Id;
    }

    public UUID getClientId() {
        return ClientId;
    }

    public double getMoney() {
        return _money;
    }

    public double getPercentage() {
        return _percentage;
    }

    public double depositMoney(double money) {
        _money += money;

        return money;
    }

    public double withdrawMoney(double money) throws Exception {
        if (_money < _creditLimit) {
            throw new Exception();
        }

        _money -= money;

        return money;
    }

    @Override
    public double commissionPayment() {
        return 0;
    }

    @Override
    public void commission() {
    }
}
