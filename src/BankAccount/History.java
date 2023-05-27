package BankAccount;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class History {
    private KontoBankowe kontoBankowe;
    private LocalDate dateTime;
    private OperationType operationType;
    private int sum;

    public History(KontoBankowe kontoBankowe, LocalDate dateTime, OperationType operationType, int sum) {
        this.kontoBankowe = kontoBankowe;
        this.dateTime = dateTime;
        this.operationType = operationType;
        this.sum = sum;
    }

    public KontoBankowe getKontoBankowe() {
        return kontoBankowe;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public int getSum() {
        return sum;
    }

    public int compareTo(History h2) {

        return 1;
    }

    @Override
    public String toString() {
        return "Konto: " +
                getKontoBankowe().getNazwa() +
                " data: " + getDateTime() +
                " operacja: " + getOperationType() +
                " suma: " + getSum() + "\n";
    }

}
