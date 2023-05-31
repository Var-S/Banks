package Banks.Models;

import Banks.Entities.Client;
import Banks.Interfaces.BankAccount;

import java.util.UUID;

public class DebitAccount implements BankAccount {

    private UUID Id;
    private UUID ClientId;
    private double _money;
    private double _percentage;
    private int _daysInYear;
    private double _monthProfit;

    public DebitAccount(Client client, double percentage) {
        ClientId = client.getId();
        _percentage = percentage;
        Id = UUID.randomUUID();
    }

    public UUID getId() {
        return Id;
    }

    public UUID getClientId() {
        return ClientId;
    }

    public double getPercentage() {
        return _percentage;
    }

    public double getMoney() {
        return _money;
    }

    public double depositMoney(double money) {
        _money += money;

        return money;
    }

    public double withdrawMoney(double money) throws Exception {
        if ((_money - money) < 0) {
            throw new Exception();
        }

        _money -= money;

        return money;
    }

    public double commissionPayment() {
        _daysInYear = 365;

        _monthProfit = _money * _percentage / _daysInYear;

        return _monthProfit;
    }

    public void commission() {
        depositMoney(_monthProfit);
        _monthProfit = 0;
    }
}