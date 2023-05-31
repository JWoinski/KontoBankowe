package Exceptions;

public class BlockedAccountException extends Exception {
    public BlockedAccountException() {
        super("Konto zablokowane");
    }
}
