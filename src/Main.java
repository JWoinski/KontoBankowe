import Services.BankAccountService;
import Models.BankAccount;
import Models.UrzadSkarbowy;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        //---------------------INIT-----------------------------------------------------------------------------------------------------
        BankAccount k1 = new BankAccount("Przekorzystne", "PekaoSa", "123563213", "00283100591", 2, 100000, false, "");
        BankAccount k2 = new BankAccount("Mobilne", "Santander", "124322432", "78051175761", 5, 50000, false, "");
        BankAccount k3 = new BankAccount("Oszczednosciowe", "pkoBP", "758283835", "71061129748", 10, 2500, false, "");
        BankAccount k4 = new BankAccount("Walutowe", "mBank", "4325234", "94101991286", 3, 1100, false, "");
        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(k1);
        bankAccounts.add(k2);
        bankAccounts.add(k3);
        bankAccounts.add(k4);
        k1.wyplata(100);
        k2.wyplata(200);
        k3.wyplata(300);
        k4.wyplata(400);

        // ------------------------------------------------------------------------------------------------------------
        //-------------------------------------------------------STREAMS ----------------------------------------------
        System.out.print("Najwięcej operacji: ");
        System.out.println(BankAccountService.mostNumberOfOperations(bankAccounts));

        System.out.print("Najwiecej wypłat: ");
        System.out.println(BankAccountService.mostNumberOfWithdrawn(bankAccounts));

        System.out.print("Sumarycznie wplacono najwięcej kasy: ");
        System.out.println(BankAccountService.summaryWplataMax(bankAccounts));

        System.out.print("Sumarycznie wyplacono najwięcej kasy: ");
        System.out.println(BankAccountService.summaryWyplataMax(bankAccounts));
//----------------------------------------------------------------------------------------------------------------
        UrzadSkarbowy urzadSkarbowy = new UrzadSkarbowy();
        urzadSkarbowy.bankAccountInspection(bankAccounts, 5, 2023);
//        k2.wplata(100);
//        urzadSkarbowy.zlozWyjasnienie("dupa",k4);
    }
}