package com.transactionanalyser;

public enum TransactionType {
    PAYMENT, REVERSAL;

    public static TransactionType fromString(String s) {
        if (s.equals("PAYMENT")) {
            return PAYMENT;
        } else if (s.equals("REVERSAL")) {
            return REVERSAL;
        } else {
            System.err.println("Could not parse string: " + s + " as a transaction type");
            throw new IllegalArgumentException();
        }
    }
}
