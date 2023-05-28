package UrzadSkarbowy;
import BankAccount.History;
import BankAccount.KontoBankowe;
import BankAccount.OperationType;
import java.util.*;
import java.time.LocalDate;

public class UrzadSkarbowy {
    private String nazwa;
    private String adres;

    public void kontroluj(List<KontoBankowe> kontoBankoweList, int month, int year) {
        final Integer wyplataSummary = kontoBankoweList
                .stream().map(
                        n -> n.getHistories()
                                .stream()
                                .filter(a -> a.getOperationType() == OperationType.WYPLATA)
                                .map(History::getSum)
                                .reduce(0, Integer::sum))
                .max(Comparator.comparing(Integer::valueOf)).get();
        final Integer wplataSummary = kontoBankoweList
                .stream()
                .map(
                        n -> n.getHistories()
                                .stream()
                                .filter(a -> a.getOperationType() == OperationType.WPLATA)
                                .map(History::getSum).reduce(0,
                                        Integer::sum))
                .max(Comparator.comparing(Integer::valueOf)).get();
        final Optional<History> any = kontoBankoweList.stream().flatMap(n -> n.getHistories().stream().filter(a -> a.getSum() > 15000)).findAny();

        LocalDate before = LocalDate.of(year, month, 1);
        LocalDate after = LocalDate.of(year, month, before.lengthOfMonth());
        int counterPrzelew = 0;
        for (KontoBankowe kontoBankowe : kontoBankoweList) {
            for (History history : kontoBankowe.getHistories()) {
                if (!history.getDateTime().isAfter(after) && !history.getDateTime().isBefore(before)) {
                    if (wyplataSummary < wplataSummary) {
                        kontoBankowe.setZablokowane(true);
                        kontoBankowe.setOpisZablokowania("Nieznane dochody");
                        System.out.println(kontoBankowe.getNazwa() + " zostało zablokowane, powód: Nieznane dochody");
                        break;
                    }
                    if (any.isPresent()) {
                        kontoBankowe.setZablokowane(true);
                        kontoBankowe.setOpisZablokowania("Niezgłoszona transakcja");
                        System.out.println(kontoBankowe.getNazwa() + " zostało zablokowane, powód: Niezgloszona Transakcja");
                        break;
                    }
                    // zakładam na sztywno wiek za pomocą rocznika by nie skupiać się na walidowaniu peselu
                    if (history.getOperationType() == OperationType.PRZELEW_WCHODZACY) {
                        counterPrzelew += 1;
                        if (counterPrzelew >= 3) {
                            kontoBankowe.setZablokowane(true);
                            kontoBankowe.setOpisZablokowania("Słup małoletni");
                            System.out.println(kontoBankowe.getNazwa() + " zostało zablokowane, powód: Słup małoletni");
                            break;
                        }
                    }
                }
            }
        }
    }

    public void zlozWyjasnienie(String tresc, KontoBankowe k) {
        if (k.checkIfBlocked()) {
            Random rand = new Random();
            int n = rand.nextInt(100);
            if (n > 94) {
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
