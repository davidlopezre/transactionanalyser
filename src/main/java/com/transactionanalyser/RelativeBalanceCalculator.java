package com.transactionanalyser;

import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class RelativeBalanceCalculator {
    public static Pair<Integer, Double> getRelativeBalanceForAccountInPeriod(String accountId,
                                                                             LocalDateTime from,
                                                                             LocalDateTime to,
                                                                             List<Transaction> transactions) {

        List<Double> transactionsIncluded = removeReversalTransactions(transactions)
                                                .stream()
                                                .filter(a -> a.getCreatedAt().isAfter(from)
                                                        && a.getCreatedAt().isBefore(to.plusSeconds(1)))
                                                .map(a -> calculationHelper(accountId, a))
                                                .collect(Collectors.toList());

        double relativeBalance = transactionsIncluded.stream().reduce(0.0, (n1,n2) -> n1 + n2);
        return new Pair<>(transactionsIncluded.size(), relativeBalance);
    }

    private static List<Transaction> removeReversalTransactions(List<Transaction> transactions) {
        List<String> reversalTransactions = transactions
                                                .stream()
                                                .filter(a -> a.getType().equals(TransactionType.REVERSAL))
                                                .map(a -> a.getRelatedTransactionId())
                                                .collect(Collectors.toList());

        return transactions
                .stream()
                .filter(a ->
                    !a.getType().equals(TransactionType.REVERSAL) && !reversalTransactions.contains(a.getId()))
                .collect(Collectors.toList());

    }

    private static double calculationHelper(String accountId, Transaction transaction) {
        if (transaction.getFromAccountId().equals(accountId)) {
            return transaction.getAmount() * (-1);
        }
        return transaction.getAmount();
    }
}
