package Services;

import Enums.OperationType;
import Models.BankAccount;
import Models.History;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static Services.UrzadSkarbowyService.*;
import static org.junit.jupiter.api.Assertions.*;

class UrzadSkarbowyServiceTest {

    @Test
    void getAmountOfOperationsTest() {
        BankAccount k4 = new BankAccount("Walutowe", "mBank", "4325234", "94101991286", 3, 1100, false, "");
        k4.wplata(300);
        k4.wplata(800);
        final int expected = 1100;
        final int actual = getAmountOfOperations(k4, OperationType.WPLATA,6,2023);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void compareWplataAndWyplataTest() {
        BankAccount k4 = new BankAccount("Walutowe", "mBank", "4325234", "94101991286", 3, 1100, false, "");
        k4.wplata(300);
        k4.wplata(800);
        k4.wyplata(1101);
        final int expected = -1 ; //second value in compare
        final int actual = compareWplataAndWyplata(k4,6,2023);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void getSizeOfOperationsTest() {
        BankAccount k4 = new BankAccount("Walutowe", "mBank", "4325234", "94101991286", 3, 1100, false, "");
        k4.wplata(300);
        k4.wplata(800);
        k4.wyplata(1101);
        final long expeccted = 2;
        final long actual =  getSizeOfOperations(k4,OperationType.WPLATA,6,2023);
        Assertions.assertEquals(expeccted,actual);
    }

    @Test
    void getAnyTransferBiggerThan15KTest() {
        BankAccount k4 = new BankAccount("Walutowe", "mBank", "4325234", "94101991286", 3, 1100, false, "");
        k4.wplata(300);
        k4.wplata(800);
        k4.wyplata(1101);
        k4.wplata(15001);
        final boolean expected = true;
        final boolean actual = getAnyTransferBiggerThan15K(k4,6,2023);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void getYearsOldTest() {
        BankAccount k4 = new BankAccount("Walutowe", "mBank", "4325234", "94101991286", 3, 1100, false, "");
        final String pesel =k4.getPeselOfOwnerAccount();
        final int expected = 29;
        final int actual = getYearsOld(pesel);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void isUnmatureOwnerTest() {
        BankAccount k4 = new BankAccount("Walutowe", "mBank", "4325234", "12210333256", 3, 1100, false, "");
        final boolean expected = true;
        final boolean actual = isUnmatureOwner(k4);
        Assertions.assertEquals(expected,actual);


    }

    @Test
    void moreThan3TransfersInTest() {
        BankAccount k4 = new BankAccount("Walutowe", "mBank", "4325234", "12210333256", 3, 1100, false, "");
        k4.wplata(200);
        k4.wplata(200);
        k4.wplata(200);
        final boolean expected = true;
        final boolean actual = moreThan3TransfersIn(k4,6,2023);
        Assertions.assertEquals(expected,actual);

    }

    @Test
    void tooMuchPaymentOnAccountTest() {
        BankAccount k4 = new BankAccount("Walutowe", "mBank", "4325234", "12210333256", 3, 1100, false, "");
        k4.wplata(200);
        k4.wplata(200);
        k4.wplata(200);
        k4.wyplata(200);
        final boolean expected = true;
        final boolean actual = tooMuchPaymentOnAccount(k4,6,2023);
        Assertions.assertEquals(expected,actual);
    }
}