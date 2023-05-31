package Models;

import Services.UrzadSkarbowyService;

import java.util.*;

import static Services.UrzadSkarbowyService.*;

public class UrzadSkarbowy {
    private String nameOfUrzadSkarbowy;
    private String address;

    public void bankAccountInspection(List<BankAccount> BankAccounts, int month, int year) {
        for (BankAccount bankAccount : BankAccounts) {
            if (tooMuchPaymentOnAccount(bankAccount, month, year)) {
                blockAccount(bankAccount, "Nieznane dochody");
            }
            if (getAnyTransferBiggerThan15K(bankAccount, month, year)) {
                UrzadSkarbowyService.blockAccount(bankAccount, "Niezgłoszona transakcja");
            }
            if (moreThan3TransfersIn(bankAccount, month, year) && isUnmatureOwner(bankAccount)) {
                blockAccount(bankAccount, "Słup małoletni");
            }
        }
    }
}
