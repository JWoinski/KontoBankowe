import BankAccount.History;
import BankAccount.KontoBankowe;
import BankAccount.OperationType;
import UrzadSkarbowy.UrzadSkarbowy;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        KontoBankowe k1 = new KontoBankowe("Przekorzystne", "PekaoSa", 123563213, 59092226728L, 2, 100000, false);
        KontoBankowe k2 = new KontoBankowe("Mobilne", "Santander", 124322432, 59092226728L, 5, 5000, false);
        KontoBankowe k3 = new KontoBankowe("Oszczednosciowe", "pkoBP", 758283835, 59092226728L, 10, 2500, false);
        KontoBankowe k4 = new KontoBankowe("Walutowe", "mBank", 4325234, 59092226728L, 3, 1000, true);
        k4.wyplata(1200);
        System.out.println(k4.getHistories());
        k1.wyplata(200);
        k1.wplata(5000);
        k1.przelewWychodzacy(1000, k2);
        k1.wplata(800);
        k1.wplata(10000);
        k2.wplata(5000);
        k2.wplata(10000);
        k2.wyplata(12000);
        k2.wplata(5000);
        k2.wyplata(3500);
        k2.wyplata(1500);
        List<KontoBankowe> kontoBankoweList = new ArrayList<>();
        kontoBankoweList.add(k1);
        kontoBankoweList.add(k2);

        // ------------------------------------------------------------------------------------------------------------
        //-------------------------------------------------------STREAMS ----------------------------------------------
        //  - bylo najwiecej operacji
        final Integer integer = kontoBankoweList
                .stream()
                .map(
                        n -> n.getHistories().size())
                .max(Comparator.comparing(Integer::valueOf)).get();
        System.out.println(integer);
        //  - bylo najwiecej wyplat
        final Integer integer1 = kontoBankoweList.stream()
                .map(
                        n -> n.getHistories()
                                .stream()
                                .filter(a -> a.getOperationType() == OperationType.WYPLATA)
                                .toList().size())
                .max(Comparator.comparing(Integer::valueOf)).get();
        System.out.println(integer1);
        //- na ktore wplacono sumarycznie najwiecej kasy
        final Integer integer2 = kontoBankoweList
                .stream()
                .map(
                        n -> n.getHistories()
                                .stream()
                                .filter(a -> a.getOperationType() == OperationType.WPLATA)
                                .map(History::getSum).reduce(0,
                                        (a, b) -> a + b))
                .max(Comparator.comparing(Integer::valueOf))
                .get();
        System.out.println(integer2);
        //- z ktorego sumarycznie wyplacono najwiecej kasy
        final Integer integer3 = kontoBankoweList
                .stream().map(
                        n -> n.getHistories()
                                .stream()
                                .filter(a -> a.getOperationType() == OperationType.WYPLATA)
                                .map(History::getSum)
                                .reduce(0, (a, b) -> a + b))
                .max(Comparator.comparing(Integer::valueOf)).get();
        System.out.println(integer3);
        UrzadSkarbowy urzadSkarbowy = new UrzadSkarbowy();
        urzadSkarbowy.zlozWyjasnienie("Sraka",k4);

    }
//    Zadanie 02.
//
//    Stworz klase UrzadSkarbowy (nazwa, adres)
//
//    ktory kontroluje konta bankowe.
//
//    kontrola odbywa sie co miesiac tzn:
//
//    List<KontoBankowe> zablokowane = urzad.kontroluj(listaKont, Month.JANUARY, 2020)
//
//    mowi ze bedziemy kontrolowac tylko operacje wykonane w styczniu 2020.
//
//    i tak:
//
//            - jesli w danym okresie kontrolnym suma wplat na konto jest mniejsza niz suma wyplat - to blokujemy konto z opisem "nieznane dochody"
//
//            - jesli w danym okresie kontrolnym pojawi sie transakcja na kwote wieksza niz 15.000 pln - to blokujemy konto z opisem "niezglosozna transakcja"
//
//            - jesli wiek klienta jest mniejsz niz 18 lat i wykazuje on conajmniej 3 przelewy wchodzace na kwote min 1500 pln - to blokujemy konto z opisem "słup małoletni"
//
}