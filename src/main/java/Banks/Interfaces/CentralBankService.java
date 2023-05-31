package Banks.Interfaces;

import Banks.Entities.BankImpl;
import Banks.Models.BankSystem;

import java.util.UUID;

public interface CentralBankService {
    BankImpl addBank(String name, BankSystem bankSystem) throws Exception;

    BankImpl getBank(String name);

    BankImpl getBank(UUID id);

    double transactionBeetweenBanks(BankTransaction transaction, double money, BankImpl oldBank,
                                    BankImpl newBank, UUID id);
}
