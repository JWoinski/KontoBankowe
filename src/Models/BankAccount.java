package Models;

import Enums.OperationType;
import Exceptions.BlockedAccountException;
import Exceptions.LackOfAccountFundsException;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private final String nameOfAccount;
    private final String nameOfBank;
    private final String numberOfAccount;
    private final String peselOfOwnerAccount;
    private final int InterestRateOfAccount;
    private int balance;
    private boolean isBlocked;
    private String blockDescription;

    private List<History> histories;

    public BankAccount(String nameOfAccount, String nameOfBank, String numberOfAccount, String peselOfOwnerAccount, int InterestRateOfAccount, int balance, boolean isBlocked, String blockDescription) {
        this.nameOfAccount = nameOfAccount;
        this.nameOfBank = nameOfBank;
        this.numberOfAccount = numberOfAccount;
        this.peselOfOwnerAccount = peselOfOwnerAccount;
        this.InterestRateOfAccount = InterestRateOfAccount;
        this.balance = balance;
        this.isBlocked = isBlocked;
        this.blockDescription = blockDescription;
        histories = new ArrayList<>();
    }

    public String getNameOfAccount() {
        return nameOfAccount;
    }

    public String getNameOfBank() {
        return nameOfBank;
    }

    public String getNumberOfAccount() {
        return numberOfAccount;
    }

    public String getPeselOfOwnerAccount() {
        return peselOfOwnerAccount;
    }

    public int getInterestRateOfAccount() {
        return InterestRateOfAccount;
    }

    public int getBalance() {
        return balance;
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public String getReasonOfBlock( String blockDescription){
        return blockDescription;
    }

    public void addHistory(History history) {
        histories.add(history);
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setBlock(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public void setReasonOfBlock(String blockDescription) {
        this.blockDescription = blockDescription;
    }

    public void wyplata(int amount) {
        try {
            if (getIsBlocked()) {
                throw new BlockedAccountException();
            }
            if (getBalance() < amount) {
                throw new LackOfAccountFundsException();
            }
            balance -= amount;
            this.addHistory(new History(OperationType.WYPLATA, amount));
        } catch (BlockedAccountException | LackOfAccountFundsException e) {
            System.out.println(e.getMessage());
        }
    }

    public void przelewWychodzacy(int sum, BankAccount destinationAccount) {
        try {
            if (getIsBlocked()) {
                throw new BlockedAccountException();
            }
            if (getBalance() < sum) {
                throw new LackOfAccountFundsException();
            }
            this.balance -= sum;
            destinationAccount.przelewWchodzacy(sum);
            this.addHistory(new History(OperationType.PRZELEW_WYCHODZACY, sum));
        } catch (BlockedAccountException | LackOfAccountFundsException e) {
            System.out.println(e.getMessage());
        }
    }

    private void przelewWchodzacy(int amount) {
        try {
            if (getIsBlocked()) {
                throw new BlockedAccountException();
            }
            balance += amount;
            this.addHistory(new History(OperationType.PRZELEW_WCHODZACY, amount));
        } catch (BlockedAccountException e) {
            System.out.println(e.getMessage());
        }
    }

    public void wplata(int amount) {
        try {
            if (getIsBlocked()) {
                throw new BlockedAccountException();
            }
            this.balance += amount;
            this.addHistory(new History(OperationType.WPLATA, amount));
        } catch (BlockedAccountException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public long getHistorySize(OperationType operationType) {
        return histories
                .stream()
                .filter(n -> n.getOperationType() == operationType)
                .toList().size();
    }
}
