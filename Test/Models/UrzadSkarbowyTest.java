package Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrzadSkarbowyTest {

    @Test
    void bankAccountInspection() {
        BankAccount k1 = new BankAccount("Przekorzystne", "PekaoSa", "123563213", "00283100591", 2, 100000, false, "");
        k1.wplata(300);
        k1.wplata(300);
        k1.wplata(300);

    }
}