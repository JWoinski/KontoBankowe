package Models;

import Enums.OperationType;

import java.time.LocalDate;

public class History {
    private LocalDate dateTime;
    private OperationType operationType;
    private int amount;

    public History(OperationType operationType, int amount) {
        this.dateTime = LocalDate.now();
        this.operationType = operationType;
        this.amount = amount;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return
                " data: " + getDateTime() +
                        " operacja: " + getOperationType() +
                        " suma: " + getAmount() + "\n";
    }
}
