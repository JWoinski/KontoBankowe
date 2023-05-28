package BankAccount;

import java.time.LocalDate;

public class History {
    private final KontoBankowe kontoBankowe;
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

    @Override
    public String toString() {
        return "Konto: " +
                getKontoBankowe().getNazwa() +
                " data: " + getDateTime() +
                " operacja: " + getOperationType() +
                " suma: " + getSum() + "\n";
    }

}
