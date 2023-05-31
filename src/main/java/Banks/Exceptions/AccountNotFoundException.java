package Banks.Exceptions;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}