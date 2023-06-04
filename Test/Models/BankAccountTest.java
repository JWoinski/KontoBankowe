package Models;

import Enums.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    @Test
    void getHistorySizeTest() {
        BankAccount k1 = new BankAccount("Przekorzystne", "PekaoSa", "123563213", "00283100591", 2, 100000, false, "");
        k1.wyplata(200);
        k1.wyplata(300);
        final long expected = 2;
        final long actual = k1.getHistorySize(OperationType.WYPLATA);
        Assertions.assertEquals(expected,actual);
    }
}