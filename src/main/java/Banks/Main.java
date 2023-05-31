package Banks;

import Banks.Entities.BankImpl;
import Banks.Entities.CentralBankImpl;
import Banks.Entities.Client;
import Banks.Models.BankSystem;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    /**
     * Main entry point.
     */
    public static void main(String[] args) throws Exception {
        CentralBankImpl centralBank = new CentralBankImpl();
        boolean work = true;
        while (work) {
            System.out.println("Чтобы посмотреть помощь нажмите - 0 ");
            Scanner scanner = new Scanner(System.in);
            int command = scanner.nextInt();

            switch (command) {
                case 0 -> help();
                case 1 -> addBank(centralBank);
                case 2 -> addClient(centralBank);
                case 3 -> addDebitAccount(centralBank);
                case 4 -> addCreditAccount(centralBank);
                case 5 -> addDepositAccount(centralBank);
                case 6 -> addDepositTransaction(centralBank);
                case 7 -> addWithdrawTransaction(centralBank);
                case 8 -> speedUpTime(centralBank);
                case 9 -> clientInfo(centralBank);
                case 10 -> bankInfo(centralBank);
                case 11 -> work = false;
            }
        }
    }

    static Scanner scanner = new Scanner(System.in);

    private static void help() {
        System.out.println("Список доступных команд ");
        System.out.println("Добавление банка - 1 ");
        System.out.println("Добавление клиента - 2 ");
        System.out.println("Добавление дебитового счёта - 3 ");
        System.out.println("Добавление кредитного счёта - 4 ");
        System.out.println("Добавление депозитного счёта - 5 ");
        System.out.println("Положить деньги на счёт - 6 ");
        System.out.println("Снять деньги со счёта - 7 ");
        System.out.println("Ускорить время - 8 ");
        System.out.println("Информация о клиенте - 9 ");
        System.out.println("Информация о банке - 10 ");
        System.out.println("Завершить работу - 11 ");
    }

    private static void addBank(CentralBankImpl centralBank) throws Exception {
        System.out.println("Введите название вашего банка : ");
        String bankName = scanner.nextLine();
        System.out.println("Перейдём к настройкам банка : ");
        System.out.println("Укажите кредитный лимит: : ");
        double creditLimit = Double.parseDouble(scanner.nextLine());
        System.out.println("Укажите комиссию : ");
        double comission = Double.parseDouble(scanner.nextLine());
        System.out.println("Укажите годовой процент : ");
        double yearPercent = Double.parseDouble(scanner.nextLine());
        System.out.println("Укажите процент для минимальной ставке : ");
        double lowPercent = Double.parseDouble(scanner.nextLine());
        System.out.println("Введите процент по средней ставке : ");
        double mediumPercent = Double.parseDouble(scanner.nextLine());
        System.out.println("Введите процент по высокой ставке : ");
        double highPercent = Double.parseDouble(scanner.nextLine());
        System.out.println("Введите максимальный лимит для подозрительного клиента");
        double doubtfulClientLimit = Double.parseDouble(scanner.nextLine());

        centralBank.addBank(bankName,
                new BankSystem(creditLimit, comission, yearPercent, doubtfulClientLimit, lowPercent,
                        mediumPercent, highPercent));

        System.out.println(Objects.requireNonNull(
                centralBank.getBanks().stream().skip(centralBank.getBanks().size() - 1).findFirst()
                        .orElse(null)).id);
    }

    private static void addClient(CentralBankImpl centralBank) {
        System.out.println("В какой банк вы хотите добавить клиента? ");
        centralBank.showBanks();
        BankImpl bank = centralBank.getBank(scanner.nextLine());
        System.out.println("Введите имя : ");
        String firstName = scanner.nextLine();
        System.out.println("Введите фамилию : ");
        String lastName = scanner.nextLine();
        System.out.println("Введите номер паспорта : ");
        int passport = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите адрес : ");
        String address = scanner.nextLine();
        Client client = new Client.Builder(firstName, lastName).addPassportNumber(passport)
                .addAddress(address).build();
        bank.addClient(client);
        System.out.println(client.toString());
    }

    private static void addDebitAccount(CentralBankImpl centralBank) {
        System.out.println("В каком банке вы хотите добавить счёт?");
        centralBank.showBanks();
        BankImpl bank = centralBank.getBank(scanner.nextLine());
        System.out.println("Введите имя клиента : ");
        Client client = bank.getClient(scanner.nextLine());
        UUID id = bank.addDebit(client);
        System.out.println("Успешно! id = " + id);
    }

    private static void addCreditAccount(CentralBankImpl centralBank) {
        System.out.println("В каком банке вы хотите добавить счёт?");
        centralBank.showBanks();
        BankImpl bank = centralBank.getBank(scanner.nextLine());
        System.out.println("Введите id клиента : ");
        System.out.println("Введите имя клиента : ");
        Client client = bank.getClient(scanner.nextLine());
        UUID id = bank.addCredit(client);
        System.out.printf("Успешно! id = %s\n", id);
    }

    private static void addDepositAccount(CentralBankImpl centralBank) {
        System.out.println("В каком банке вы хотите добавить счёт?");
        centralBank.showBanks();
        BankImpl bank = centralBank.getBank(scanner.nextLine());
        System.out.println("Введите имя клиента : ");
        Client client = bank.getClient(scanner.nextLine());
        System.out.println("Введите год разблокировки счёта : ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите месяц разблокировки счёта : ");
        int month = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите день разблокировки счёта : ");
        int day = Integer.parseInt(scanner.nextLine());
        LocalDate end = LocalDate.of(year, month, day);
        UUID id = bank.addDeposit(client, end);
        System.out.printf("Успешно! id - %s\n", id);
    }

    private static void addDepositTransaction(CentralBankImpl centralBank) {
        System.out.println("В каком банке вы хотите сделать депозит?");
        centralBank.showBanks();
        BankImpl bank = centralBank.getBank(scanner.nextLine());
        System.out.println("Введите id аккаунта : ");
        UUID id = UUID.fromString(scanner.nextLine());
        System.out.println("Введите количество денег : ");
        double money = Double.parseDouble(scanner.nextLine());
        bank.depositTransaction(id, money);
    }

    private static void addWithdrawTransaction(CentralBankImpl centralBank) throws Exception {
        System.out.println("В каком банке вы хотите сделать депозит?");
        centralBank.showBanks();
        BankImpl bank = centralBank.getBank(scanner.nextLine());
        System.out.println("Введите id аккаунта : ");
        UUID id = UUID.fromString(scanner.nextLine());
        System.out.println("Введите количество денег : ");
        double money = Double.parseDouble(scanner.nextLine());
        bank.withdrawTransaction(id, money);
    }

    private static void speedUpTime(CentralBankImpl centralBank) {
        System.out.println("Введите количество пропускаемых месяцев : ");
        int months = Integer.parseInt(scanner.nextLine());
        centralBank.speedUpTime(months);
    }

    private static void clientInfo(CentralBankImpl centralBank) {
        System.out.println("В каком банке вы хотите посмотреть клиентов?");
        centralBank.showBanks();
        BankImpl bank = centralBank.getBank(scanner.nextLine());
        System.out.println("Клиенты этого банка :");
        bank.showClients();
        System.out.println("Выбор клиента :");
        Client client = bank.getClient(scanner.nextLine());
        System.out.println(client.toString());
    }

    private static void bankInfo(CentralBankImpl centralBank) {
        System.out.println("Выбор банка : ");
        centralBank.showBanks();
    }
}