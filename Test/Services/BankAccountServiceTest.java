package Services;

import Enums.OperationType;
import Models.BankAccount;
import Models.History;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountServiceTest {

    @BeforeEach
    public void setUp(){

    }
    @Test
    public void mostOperations(){
        BankAccount bankAccount1 = new BankAccount("Przekorzystne", "PekaoSa", "1111", "00283100591", 2, 100000, false, "");
        BankAccount bankAccount2 = new BankAccount("Mobilne", "Santander", "11112", "78051175761", 5, 50000, false, "");
        BankAccount bankAccount3 = new BankAccount("Oszczednosciowe", "pkoBP", "11113", "71061129748", 10, 2500, false, "");
        BankAccount bankAccount4 = new BankAccount("Walutowe", "mBank", "11114", "12270771953", 3, 1100, false, "");
        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(bankAccount1);
        bankAccounts.add(bankAccount2);
        bankAccounts.add(bankAccount3);
        bankAccounts.add(bankAccount4);

        // #1 3 Wyplaty, 1 przelew wchodzacy i jeden wychodzacy = 6
        bankAccount1.wyplata(2000);
        bankAccount1.wyplata(2000);
        bankAccount1.wyplata(2000);
        bankAccount1.przelewWychodzacy(200,bankAccount2);
        bankAccount1.wplata(1000);
        // #2 7 operacji
        bankAccount2.przelewWychodzacy(200,bankAccount1);
        bankAccount2.wplata(1000);
        bankAccount2.wplata(1000);
        bankAccount2.wyplata(2000);
        bankAccount2.wyplata(200);
        bankAccount2.wyplata(200);
        // #3 2 operacje
        bankAccount3.wyplata(1000);
        bankAccount3.wyplata(150);
        //#4 1 operacja
        bankAccount4.przelewWychodzacy(100,bankAccount2);
        final String actual = BankAccountService.mostNumberOfOperations(bankAccounts);
        String expected = "Mobilne";
        Assertions.assertEquals(expected,actual);

    }
    @Test
    public void maxWyplata(){
        BankAccount bankAccount1 = new BankAccount("Przekorzystne", "PekaoSa", "1111", "00283100591", 2, 100000, false, "");
        BankAccount bankAccount2 = new BankAccount("Mobilne", "Santander", "11112", "78051175761", 5, 50000, false, "");
        BankAccount bankAccount3 = new BankAccount("Oszczednosciowe", "pkoBP", "11113", "71061129748", 10, 2500, false, "");
        BankAccount bankAccount4 = new BankAccount("Walutowe", "mBank", "11114", "12270771953", 3, 1100, false, "");
        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(bankAccount1);
        bankAccounts.add(bankAccount2);
        bankAccounts.add(bankAccount3);
        bankAccounts.add(bankAccount4);

        bankAccount1.wyplata(300);
        bankAccount2.wyplata(400);
        bankAccount3.wyplata(5000);
        bankAccount4.wyplata(400);

        final String expected = "Walutowe";
        final String actual = BankAccountService.summaryWyplataMax(bankAccounts);
        Assertions.assertEquals(expected,actual);
    }
    @Test
    public void maxWplata(){

        BankAccount bankAccount1 = new BankAccount("Przekorzystne", "PekaoSa", "1111", "00283100591", 2, 100000, false, "");
        BankAccount bankAccount2 = new BankAccount("Mobilne", "Santander", "11112", "78051175761", 5, 50000, false, "");
        BankAccount bankAccount3 = new BankAccount("Oszczednosciowe", "pkoBP", "11113", "71061129748", 10, 2500, false, "");
        BankAccount bankAccount4 = new BankAccount("Walutowe", "mBank", "11114", "12270771953", 3, 1100, false, "");
        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(bankAccount1);
        bankAccounts.add(bankAccount2);
        bankAccounts.add(bankAccount3);
        bankAccounts.add(bankAccount4);

        bankAccount1.wplata(3000);

        bankAccount2.wplata(5000);

        bankAccount3.wplata(105000);
        bankAccount3.wplata(105000);

        bankAccount4.wplata(20000);

        final String expected = "Oszczednosciowe";
        final String actual = BankAccountService.summaryWyplataMax(bankAccounts);
    }
    @Test
    public void mostWithdrawns(){
        BankAccount bankAccount1 = new BankAccount("Przekorzystne", "PekaoSa", "1111", "00283100591", 2, 100000, false, "");
        BankAccount bankAccount2 = new BankAccount("Mobilne", "Santander", "11112", "78051175761", 5, 50000, false, "");
        BankAccount bankAccount3 = new BankAccount("Oszczednosciowe", "pkoBP", "11113", "71061129748", 10, 2500, false, "");
        BankAccount bankAccount4 = new BankAccount("Walutowe", "mBank", "11114", "12270771953", 3, 1100, false, "");
        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(bankAccount1);
        bankAccounts.add(bankAccount2);
        bankAccounts.add(bankAccount3);
        bankAccounts.add(bankAccount4);

        bankAccount1.wyplata(100);
        bankAccount1.wyplata(100);
        bankAccount1.wyplata(100);
        bankAccount1.wyplata(100);


        bankAccount2.wyplata(100);
        bankAccount2.wyplata(100);
        bankAccount2.wyplata(100);
        bankAccount2.wyplata(100);
        bankAccount2.wyplata(100);



        bankAccount3.wyplata(100);
        bankAccount3.wyplata(100);
        bankAccount3.wplata(100);
        bankAccount3.wplata(100);
        bankAccount3.wplata(100);
        bankAccount3.wplata(100);
        bankAccount3.wplata(100);



        bankAccount4.wyplata(100);

        final String expected = "Mobilne";
        final String actual = BankAccountService.mostNumberOfWithdrawn(bankAccounts);

        Assertions.assertEquals(expected,actual);

    }
}