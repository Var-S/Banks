package Banks.Interfaces;

import java.util.UUID;

public interface BankAccount {
    UUID getId();
    double getMoney();
    double depositMoney(double money) throws Exception;
    double withdrawMoney(double money) throws Exception;
    double commissionPayment();
    void commission();
}
