package Banks.Interfaces;

import Banks.Entities.Client;

import java.time.LocalDate;
import java.util.UUID;

public interface BankService {
    Client addPassport(UUID id, int passport);

    Client addAddress(UUID id, String address) throws Exception;

    Client getClient(UUID id);

    UUID addDebit(Client client);

    UUID addDeposit(Client client, LocalDate depositEnd);

    UUID addCredit(Client client);

    double changeCreditLimit(double newLimit);

    double changeDoubtfulClientLimit(double newLimit);

    void depositTransaction(UUID accountId, double money);

    void withdrawTransaction(UUID accountId, double money) throws Exception;

    void transferTransaction(UUID withdraw, UUID deposit, double money) throws Exception;
}
