package Banks.Interfaces;

import java.util.List;

public interface BankTransaction {
    BankTransaction exchangeTransaction(double money, List<BankAccount> accounts);

    BankTransaction cancelTransaction(List<BankAccount> accounts);
}
