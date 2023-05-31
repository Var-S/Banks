package Banks.Models;

import Banks.Exceptions.MoneyException;
import Banks.Exceptions.PercentException;
import Banks.Interfaces.BankAccount;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class DepositAccount implements BankAccount {

    private static final double MoneyLowPercent = 50000;
    private static final double MoneyHighPercent = 100000;
    private final LocalDate unlockDepositDateTime;
    private final UUID id;
    private final UUID clientId;
    private double money;
    private double lowPercent;
    private double middlePercent;
    private double highPercent;
    private double monthCommission;

    public DepositAccount(UUID clientId, double highPercent, double middlePercent, double lowPercent,
                          LocalDate unlockDepositDateTime) {
        Objects.requireNonNull(clientId);
        Objects.requireNonNull(unlockDepositDateTime);

        if (highPercent < 0 || middlePercent < 0 || lowPercent < 0) {
            throw new PercentException("Percent can't be less then 0");
        }

        if (highPercent < middlePercent || middlePercent < lowPercent) {
            throw new PercentException("Incorrect Percent");
        }

        id = UUID.randomUUID();
        this.clientId = clientId;
        this.highPercent = highPercent;
        this.middlePercent = middlePercent;
        this.lowPercent = lowPercent;
        this.unlockDepositDateTime = unlockDepositDateTime;
    }

    public LocalDate getUnlockDepositDateTime() {
        return unlockDepositDateTime;
    }

    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public double getMoney() {
        return money;
    }

    public double getHighPercent() {
        return highPercent;
    }

    public double getMiddlePercent() {
        return middlePercent;
    }

    public double getLowPercent() {
        return lowPercent;
    }

    public double depositMoney(double money) throws Exception {
        if (money < 0) {
            throw new Exception();
        }

        this.money += money;

        return this.money;
    }

    public double withdrawMoney(double money) {
        if (this.money > money) {
            throw new MoneyException("Not enough money");
        }
        if (money < 0) {
            throw new MoneyException("Negative amount of money");
        }

        this.money -= money;

        return this.money;
    }

    public void commission() {
        withdrawMoney(monthCommission);
        monthCommission = 0;
    }

    public double commissionPayment() {
        int daysInYear = 365;

        if (money < MoneyLowPercent) {
            monthCommission = (money * lowPercent) / daysInYear;
        }
        if (money > MoneyLowPercent && money < MoneyHighPercent) {
            monthCommission = (money * middlePercent) / daysInYear;
        }
        if (money > MoneyHighPercent) {
            monthCommission = (money * highPercent) / daysInYear;
        }

        return monthCommission;
    }

    public double changeLowPercent(double newPercent) {
        if (newPercent < 0) {
            throw new PercentException("Percent can't be less then 0");
        }
        if (newPercent > middlePercent) {
            throw new PercentException("Incorrect percent");
        }

        lowPercent = newPercent;

        return lowPercent;
    }

    public double changeMiddlePercent(double newPercent) {
        if (newPercent < 0) {
            throw new PercentException("Percent can't be less then 0");
        }
        if (newPercent > highPercent) {
            throw new PercentException("Incorrect percent");
        }

        middlePercent = newPercent;

        return middlePercent;
    }

    public double changeHighPercent(double newPercent) {
        if (newPercent < 0) {
            throw new PercentException("Percent can't be less then 0");
        }
        if (newPercent < middlePercent) {
            throw new PercentException("Incorrect percent");
        }

        highPercent = newPercent;

        return highPercent;
    }
}