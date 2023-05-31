package Exceptions;

public class LackOfAccountFundsException extends Exception {
    public LackOfAccountFundsException() {
        super("Brak wystarczających środków na koncie");
    }
}
