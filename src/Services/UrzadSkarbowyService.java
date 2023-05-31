package Services;

import Models.BankAccount;
import Models.History;
import Enums.OperationType;

import java.time.LocalDate;
import java.time.Year;
import java.util.Random;

public class UrzadSkarbowyService {
    public static boolean dateBetweenPeriod(History history, int month, int year) {
        LocalDate beginingOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = LocalDate.of(year, month, beginingOfMonth.lengthOfMonth());

        return history.getDateTime().isBefore(endOfMonth)
                && history.getDateTime().isAfter(beginingOfMonth);

    }

    public static int getAmountOfOperations(BankAccount bankAccount, OperationType operationType, int month, int year) {

        return bankAccount
                .getHistories()
                .stream()
                .filter(a ->
                        a.getOperationType() == operationType
                                && dateBetweenPeriod(a, month, year))
                .map(History::getAmount)
                .reduce(0, Integer::sum);

    }

    public static int compareWplataAndWyplata(BankAccount bankAccount, int month, int year) {
        return Integer
                .compare(getAmountOfOperations(bankAccount, OperationType.WPLATA, month, year),
                        getAmountOfOperations(bankAccount, OperationType.WYPLATA, month, year));
    }

    public static long getSizeOfOperations(BankAccount bankAccount, OperationType operationType, int month, int year) {
        return bankAccount.getHistories().stream().filter(operation -> operation.getOperationType() == operationType && dateBetweenPeriod(operation, month, year)).count();
    }

    public static boolean getAnyTransferBiggerThan15K(BankAccount bankAccount, int month, int year) {
        return bankAccount.getHistories().stream().anyMatch(a -> a.getAmount() > 15000 && dateBetweenPeriod(a, month, year));
    }

    public static int getYearsOld(String pesel) {
        int yearsOld = 0;
        final int getMonth = Integer.parseInt(pesel.substring(2, 4));
        final int getYear = Integer.parseInt(pesel.substring(0, 2));
        if (getMonth <= 32 && getMonth >= 21) {
            yearsOld = 2000 + getYear;
        }
        if (getMonth <= 12 && getMonth >= 1) {
            if (getMonth < 10) {
                return 1900 + Integer.parseInt(pesel.substring(1, 2)); // special case for 2000
            }
            yearsOld = 1900 + getYear;
        }
        if (getMonth <= 52 && getMonth >= 41) {
            yearsOld = 2100 + getYear;
        }
        if (getMonth <= 72 && getMonth >= 61) {
            yearsOld = 2200 + getYear;
        }
        if (getMonth <= 92 && getMonth >= 81) {
            yearsOld = 1800 + getYear;
        }
        return Year.now().getValue() - yearsOld;
    }
    public static boolean isUnmatureOwner(BankAccount bankAccount){
        return getYearsOld(bankAccount.getPeselOfOwnerAccount()) < 18;
    }
    public static boolean moreThan3TransfersIn(BankAccount bankAccount,int month, int year){
        return getSizeOfOperations(bankAccount, OperationType.WPLATA, month, year) >= 3;
    }
    public static boolean tooMuchPaymentOnAccount(BankAccount bankAccount,int month, int year){
        return compareWplataAndWyplata(bankAccount, month, year) > 0;
    }
    public void zlozWyjasnienie(String tresc, BankAccount k) {
        if (k.getIsBlocked()) {
            Random rand = new Random();
            int n = rand.nextInt(100);
            if ((tresc.length() == 0) || (n > 94)) {
                System.out.println("Konto nadal zablokowane");
            } else {
                k.setBlock(false);
                k.setReasonOfBlock("");
                System.out.println("Konto odblokowane");
            }
        } else {
            System.out.println("Konto nie jest zablokowane");
        }
    }

    public static void blockAccount(BankAccount bankAccount, String reason) {
        bankAccount.setBlock(true);
        bankAccount.setReasonOfBlock(reason);
        System.out.println(bankAccount.getNameOfAccount() + " zostało zablokowane, powód: " + reason);
    }
}
