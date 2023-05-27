package BankAccount;

import java.time.LocalDate;
import java.util.ArrayList;

class LackOfAccountFundsException extends Exception {
    public LackOfAccountFundsException(String s) {
        // Call constructor of parent Exception
        super(s);
    }
}

class BlockedAccountException extends Exception {
    public BlockedAccountException(String s) {
        super(s);
    }
}

public class KontoBankowe {
    private final String nazwa;
    private final String nazwaBanku;
    private final int numer;
    private final long peselWlasciciela;
    private final int oprocentowanie;
    private int saldo;

    private boolean zablokowane;
    private ArrayList<History> histories = new ArrayList<>();

    public KontoBankowe(String nazwa, String nazwaBanku, int numer, long peselWlasciciela, int oprocentowanie, int saldo, boolean zablokowane) {
        this.nazwa = nazwa;
        this.nazwaBanku = nazwaBanku;
        this.numer = numer;
        this.peselWlasciciela = peselWlasciciela;
        this.oprocentowanie = oprocentowanie;
        this.saldo = saldo;
        this.zablokowane = zablokowane;
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

    public long getPeselWlasciciela() {
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
    public void setZablokowane(boolean zablokowane){
        this.zablokowane = zablokowane;
    }
    LocalDate now = LocalDate.now();
//    List <History> histories = new ArrayList<>();

    public void wyplata(int withDraw) {
        try {
            if (checkIfBlocked()) {
                throw new BlockedAccountException("Konto zablokowane");
            }

        } catch (BlockedAccountException e) {
            System.out.println(e.getMessage());
        }
        try {
            if (getSaldo() < withDraw) {
                throw new LackOfAccountFundsException("Brak wystarczających środków na koncie");
            }
        } catch (LackOfAccountFundsException e) {
            System.out.println(e.getMessage());
        }
        if (!checkIfBlocked() && getSaldo() >= withDraw) {
            this.saldo -= withDraw;
            addHistory(new History(this, now, OperationType.WYPLATA, withDraw));
        }
    }

    public void przelewWychodzacy(int sum, KontoBankowe k) {
        try {
            if (checkIfBlocked()) {
                throw new BlockedAccountException("Konto zablokowane");
            }

        } catch (BlockedAccountException e) {
            System.out.println(e.getMessage());
        }
        try {
            if (getSaldo() < sum) {
                throw new LackOfAccountFundsException("Brak wystarczających środków na koncie");
            }
        } catch (LackOfAccountFundsException e) {
            System.out.println(e.getMessage());
        }
        if (!checkIfBlocked() && getSaldo() >= sum) {
            this.saldo -= sum;
            przelewWchodzacy(sum, k);
            addHistory(new History(this, now, OperationType.PRZELEW_WYCHODZACY, sum));
        }
    }

    private void przelewWchodzacy(int sum, KontoBankowe k) {
        try {
            if (checkIfBlocked()) {
                throw new BlockedAccountException("Konto zablokowane");
            }

        } catch (BlockedAccountException e) {
            System.out.println(e.getMessage());
        }
        if(!checkIfBlocked()) {
            k.saldo += sum;
            k.addHistory(new History(k, now, OperationType.PRZELEW_WCHODZACY, sum));
        }
    }

    public void wplata(int sum) {
        try {
            if (checkIfBlocked()) {
                throw new BlockedAccountException("Konto zablokowane");
            }

        } catch (BlockedAccountException e) {
            System.out.println(e.getMessage());
        }
        if(!checkIfBlocked()) {
            this.saldo += sum;
            addHistory(new History(this, now, OperationType.WPLATA, sum));
        }
    }

}

