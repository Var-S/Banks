package Banks.Exceptions;

public class MoneyException extends RuntimeException {
    public MoneyException(String errorMessage) {
        super(errorMessage);
    }
}