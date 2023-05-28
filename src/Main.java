import BankAccount.History;
import BankAccount.KontoBankowe;
import BankAccount.OperationType;
import UrzadSkarbowy.UrzadSkarbowy;

import java.util.*;
public class Main {
    public static void main(String[] args) {
        KontoBankowe k1 = new KontoBankowe("Przekorzystne", "PekaoSa", 123563213, "20092226728", 2, 100000, false, "");
        KontoBankowe k2 = new KontoBankowe("Mobilne", "Santander", 124322432, "18092226728", 5, 5000, false, "");
        KontoBankowe k3 = new KontoBankowe("Oszczednosciowe", "pkoBP", 758283835, "18092226728", 10, 2500, false, "");
        KontoBankowe k4 = new KontoBankowe("Walutowe", "mBank", 4325234, "18092226728", 3, 1000, true, "");
        k4.wyplata(1200);
        System.out.println(k4.getHistories());
//        k1.wyplata(200);
        k1.wplata(5000);
        k2.wplata(2000);
        k1.wyplata(5000);
        k2.wyplata(2000);
        k1.wyplata(1000);
        k2.wplata(2000);
        k2.wyplata(2000);
//        k1.przelewWychodzacy(1000, k2);
//        k1.przelewWychodzacy(1000, k2);
//        k1.przelewWychodzacy(1000, k2);
//        k2.wplata(100);
//        k1.wplata(10000);
//        k1.wplata(10000);
//        k1.wplata(10000);
//        k1.wplata(10000);
//        k1.wplata(10000);
//        k1.wplata(10000);
//        k1.wplata(10000);
//        k1.wplata(10000);
//        k1.wplata(10000);
//        k1.wplata(10000);
//        k2.wplata(20000);
//        k2.wyplata(1000);
//        k2.wyplata(1000);
//        k2.wyplata(1000);


        List<KontoBankowe> kontoBankoweList = new ArrayList<>();
        kontoBankoweList.add(k1);
        kontoBankoweList.add(k2);
        kontoBankoweList.add(k3);
        kontoBankoweList.add(k4);

        // ------------------------------------------------------------------------------------------------------------
        //-------------------------------------------------------STREAMS ----------------------------------------------
        System.out.print("Najwięcej operacji: ");
        final String mostOperations = kontoBankoweList
                .stream()
                .max(Comparator.comparingInt(n -> n.getHistories().size()))
                .map(KontoBankowe::getNazwa).orElse(null);
        System.out.println(mostOperations);

        System.out.print("Najwiecej wypłat: ");
        final String mostNumberWyplata = kontoBankoweList.stream()
                .max((n1,n2) -> Integer.compare(n1.getHistories().stream().filter(n->n.getOperationType()==OperationType.WYPLATA).toList().size()
                        ,n2.getHistories().stream().filter(a->a.getOperationType()==OperationType.WYPLATA).toList().size()))
                .map(KontoBankowe::getNazwa).orElse(null);
        System.out.println(mostNumberWyplata);
        System.out.print("Sumarycznie wplacono najwięcej kasy: ");
        final String summaryWplataMax = kontoBankoweList.stream()
                .max((n1, n2) -> Integer.compare(
                        n1.getHistories()
                                .stream()
                                .filter(n -> n.getOperationType() == OperationType.WPLATA)
                                .map(History::getSum).reduce(0, Integer::sum)
                                        ,
                                n2.getHistories()
                                .stream()
                                .filter(operation -> operation.getOperationType() == OperationType.WPLATA).map(History::getSum)
                                .reduce(0, Integer::sum)
                )).map(KontoBankowe::getNazwa).orElse(null);
        System.out.println(summaryWplataMax);
        System.out.print("Sumarycznie wyplacono najwięcej kasy: ");
        final String summaryWyplataMax = kontoBankoweList.stream()
                .max((n1, n2) -> Integer.compare(
                        n1.getHistories()
                                .stream()
                                .filter(n -> n.getOperationType() == OperationType.WYPLATA)
                                .map(History::getSum).reduce(0, Integer::sum)
                                ,
                                n2.getHistories()
                                .stream()
                                .filter(operation -> operation.getOperationType() == OperationType.WYPLATA).map(History::getSum)
                                .reduce(0, Integer::sum))).map(KontoBankowe::getNazwa).orElse(null);
        System.out.println(summaryWyplataMax);

        UrzadSkarbowy urzadSkarbowy = new UrzadSkarbowy();
        urzadSkarbowy.kontroluj(kontoBankoweList, 5, 2023);
        k2.wplata(100);
        urzadSkarbowy.zlozWyjasnienie("dupa",k4);
    }
}