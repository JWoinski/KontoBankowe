package UrzadSkarbowy;

import BankAccount.History;
import BankAccount.KontoBankowe;
import BankAccount.OperationType;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class UrzadSkarbowy {
    private String nazwa;
    private String adres;

    public void kontroluj(List<KontoBankowe> kontoBankoweList, int month, int year) {
        LocalDate before = LocalDate.of(year, month, 1);
        LocalDate after = LocalDate.of(year, month, before.lengthOfMonth());
        final int comparingWplataAndWyplata =
                kontoBankoweList
                        .stream()
                        .map(
                                n -> n.getHistories()
                                        .stream()
                                        .filter(a -> a.getOperationType() == OperationType.WPLATA && !a.getDateTime().isAfter(after) && !a.getDateTime().isBefore(before))
                                        .map(History::getSum).reduce(0,
                                                Integer::sum))
                        .max(Comparator.comparing(Integer::valueOf))
                        .get()
                        -
                        kontoBankoweList
                                .stream()
                                .map(
                                        n -> n.getHistories()
                                                .stream()
                                                .filter(a -> a.getOperationType() == OperationType.WYPLATA && !a.getDateTime().isAfter(after) && !a.getDateTime().isBefore(before))
                                                .map(History::getSum)
                                                .reduce(0, Integer::sum))
                                .max(Comparator.comparing(Integer::valueOf))
                                .get();

        final Optional<History> anyTransferBiggerThan15K =
                kontoBankoweList
                        .stream()
                        .flatMap(n -> n
                                .getHistories()
                                .stream()
                                .filter(a -> a.getSum() > 15000))
                        .findAny();
        final int listSizeOfIncomingTransfers =
                kontoBankoweList
                        .stream()
                        .map(n -> n.getHistories()
                                .stream()
                                .filter(operation -> operation.getOperationType() == OperationType.PRZELEW_WCHODZACY))
                        .collect(Collectors.toList())
                        .size();

        for (KontoBankowe kontoBankowe : kontoBankoweList) {
            for (History history : kontoBankowe.getHistories()) {
                if (!history.getDateTime().isAfter(after) && !history.getDateTime().isBefore(before)) {
                    if (comparingWplataAndWyplata > 0) {
                        kontoBankowe.setZablokowane(true);
                        kontoBankowe.setOpisZablokowania("Nieznane dochody");
                        System.out.println(kontoBankowe.getNazwa() + " zostało zablokowane, powód: Nieznane dochody");
                        break;
                    }
                    if (anyTransferBiggerThan15K.isPresent()) {
                        kontoBankowe.setZablokowane(true);
                        kontoBankowe.setOpisZablokowania("Niezgłoszona transakcja");
                        System.out.println(kontoBankowe.getNazwa() + " zostało zablokowane, powód: Niezgloszona Transakcja");
                        break;
                    }
                    //
                    if (listSizeOfIncomingTransfers >= 3) {
                        kontoBankowe.setZablokowane(true);
                        kontoBankowe.setOpisZablokowania("Słup małoletni");
                        System.out.println(kontoBankowe.getNazwa() + " zostało zablokowane, powód: Słup małoletni");
                        break;
                    }
                }
            }
        }
//        kontoBankoweList.stream().map(n->n.getHistories().stream().filter(history -> !history.getDateTime().isAfter(after) && !history.getDateTime().isBefore(before)))
    }

    public void zlozWyjasnienie(String tresc, KontoBankowe k) {
        if (k.checkIfBlocked()) {
            Random rand = new Random();
            int n = rand.nextInt(100);
            if ((tresc.length() == 0) || (n > 94)) {
                System.out.println("Konto nadal zablokowane");
            } else {
                k.setZablokowane(false);
                k.setOpisZablokowania("");
                System.out.println("Konto odblokowane");
            }
        } else {
            System.out.println("Konto nie jest zablokowane");
        }
    }
}
