package BankAccount;

import java.time.LocalDate;
import java.util.ArrayList;

class LackOfAccountFundsException extends Exception {
    public LackOfAccountFundsException() {
        super("Brak wystarczających środków na koncie");
    }
}

class BlockedAccountException extends Exception {
    public BlockedAccountException() {
        super("Konto zablokowane");
    }
}

public class KontoBankowe {
    private final String nazwa;
    private final String nazwaBanku;
    private final int numer;
    private final String peselWlasciciela;
    private final int oprocentowanie;
    private int saldo;

    private boolean zablokowane;

    private String opisZablokowania;
    private ArrayList<History> histories = new ArrayList<>();

    public KontoBankowe(String nazwa, String nazwaBanku, int numer, String peselWlasciciela, int oprocentowanie, int saldo, boolean zablokowane, String opisZablokowania) {
        this.nazwa = nazwa;
        this.nazwaBanku = nazwaBanku;
        this.numer = numer;
        this.peselWlasciciela = peselWlasciciela;
        this.oprocentowanie = oprocentowanie;
        this.saldo = saldo;
        this.zablokowane = zablokowane;
        this.opisZablokowania = opisZablokowania;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getNazwaBanku() {
        return nazwaBanku;
    }

    public int getNumer() {
        return numer;
    }

    public String getPeselWlasciciela() {
        return peselWlasciciela;
    }

    public int getOprocentowanie() {
        return oprocentowanie;
    }

    public int getSaldo() {
        return saldo;
    }

    public boolean checkIfBlocked() {
        return zablokowane;
    }

    private void addHistory(History history) {
        histories.add(history);
    }

    public ArrayList<History> getHistories() {
        return histories;
    }

    public void setZablokowane(boolean zablokowane) {
        this.zablokowane = zablokowane;
    }

    public void setOpisZablokowania(String opisZablokowania) {
        this.opisZablokowania = opisZablokowania;
    }

    LocalDate now = LocalDate.now();
//    List <History> histories = new ArrayList<>();

    public void wyplata(int withDraw) {
        try {
            if (checkIfBlocked()) {
                throw new BlockedAccountException();
            }
            if (getSaldo() < withDraw) {
                throw new LackOfAccountFundsException();
            }
            this.saldo -= withDraw;
            addHistory(new History(this, now, OperationType.WYPLATA, withDraw));
        } catch (BlockedAccountException | LackOfAccountFundsException e) {
            System.out.println(e.getMessage());
        }
    }

    public void przelewWychodzacy(int sum, KontoBankowe k) {
        try {
            if (checkIfBlocked()) {
                throw new BlockedAccountException();
            }
            if (getSaldo() < sum) {
                throw new LackOfAccountFundsException();
            }
            this.saldo -= sum;
            przelewWchodzacy(sum, k);
            addHistory(new History(this, now, OperationType.PRZELEW_WYCHODZACY, sum));
        } catch (BlockedAccountException | LackOfAccountFundsException e) {
            System.out.println(e.getMessage());
        }
    }

    private void przelewWchodzacy(int sum, KontoBankowe k) {
        try {
            if (checkIfBlocked()) {
                throw new BlockedAccountException();
            }
            k.saldo += sum;
            k.addHistory(new History(k, now, OperationType.PRZELEW_WCHODZACY, sum));
        } catch (BlockedAccountException e) {
            System.out.println(e.getMessage());
        }
    }

    public void wplata(int sum) {
        try {
            if (checkIfBlocked()) {
                throw new BlockedAccountException();
            }
            this.saldo += sum;
            addHistory(new History(this, now, OperationType.WPLATA, sum));
        } catch (BlockedAccountException e) {
            System.out.println(e.getMessage());
        }
    }


}

