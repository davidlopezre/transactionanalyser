package com.transactionanalyser;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {

    private String id;

    private LocalDateTime createdAt;

    private double amount;

    private TransactionType type;

    private String fromAccountId;

    private String toAccountId;

    private String relatedTransactionId;

    public Transaction(String id,
                       LocalDateTime createdAt,
                       double amount,
                       TransactionType type,
                       String fromAccountId,
                       String toAccountId) {
        this.id = id;
        this.createdAt = createdAt;
        this.amount = amount;
        this.type = type;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
    }

    public Transaction(String id,
                       LocalDateTime createdAt,
                       double amount, TransactionType type,
                       String fromAccountId,
                       String toAccountId,
                       String relatedTransactionId) {
        this(id, createdAt, amount, type, fromAccountId, toAccountId);
        this.relatedTransactionId = relatedTransactionId;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public String getRelatedTransactionId() {
        return relatedTransactionId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", amount=" + amount +
                ", type=" + type +
                ", fromAccountId='" + fromAccountId + '\'' +
                ", toAccountId='" + toAccountId + '\'' +
                ", relatedTransactionId=" + relatedTransactionId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(createdAt, that.createdAt) &&
                type == that.type &&
                Objects.equals(fromAccountId, that.fromAccountId) &&
                Objects.equals(toAccountId, that.toAccountId) &&
                Objects.equals(relatedTransactionId, that.relatedTransactionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, createdAt, amount, type, fromAccountId, toAccountId, relatedTransactionId);
    }
}
