package Services;

import Models.BankAccount;
import Models.History;
import Enums.OperationType;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BankAccountService {


    public static String mostNumberOfOperations(List<BankAccount> bankAccounts) {
        return bankAccounts
                .stream()
                .max(Comparator.comparingInt(n -> n.getHistories()
                        .size()))
                .map(BankAccount::getNameOfAccount).orElse(null);
    }

    public static String mostNumberOfWithdrawn(List<BankAccount> bankAccounts) {
        return bankAccounts.stream()
                .max(Comparator.comparingLong(n -> n.getHistorySize(OperationType.WYPLATA)))
                .map(BankAccount::getNameOfAccount).orElse(null);
    }

    public static String  summaryWplataMax(List<BankAccount> bankAccounts) {
        return bankAccounts.stream()
                .max((n1, n2) -> Integer.compare(
                        n1.getHistories()
                                .stream()
                                .filter(n -> n.getOperationType() == OperationType.WPLATA)
                                .map(History::getAmount).reduce(0, Integer::sum)
                        ,
                        n2.getHistories()
                                .stream()
                                .filter(operation -> operation.getOperationType() == OperationType.WPLATA).map(History::getAmount)
                                .reduce(0, Integer::sum)
                )).map(BankAccount::getNameOfAccount).orElse(null);
    }

    public static String summaryWyplataMax(List<BankAccount> bankAccounts) {
        return bankAccounts.stream()
                .max((n1, n2) -> Integer.compare(
                        n1.getHistories()
                                .stream()
                                .filter(n -> n.getOperationType() == OperationType.WYPLATA)
                                .map(History::getAmount).reduce(0, Integer::sum)
                        ,
                        n2.getHistories()
                                .stream()
                                .filter(operation -> operation.getOperationType() == OperationType.WYPLATA).map(History::getAmount)
                                .reduce(0, Integer::sum)
                )).map(BankAccount::getNameOfAccount).orElse(null);
    }
}
