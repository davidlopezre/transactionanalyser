package com.transactionanalyser;

import javafx.util.Pair;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RelativeBalanceCalculatorTest {

    @Test
    public void shouldGetRelativeBalanceZeroWhenListHasPaymentAndItsReversal() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("1",
                                LocalDateTime.of(2019, 3, 29, 18, 30, 00),
                        50.00, TransactionType.PAYMENT, "A","B"),
                 new Transaction("2",
                                LocalDateTime.of(2019, 04, 01, 18, 30, 00),
                                50.00, TransactionType.REVERSAL, "A","B", "1")
        );
        LocalDateTime from = LocalDateTime.of(2019, 3, 25, 18, 30, 00);
        LocalDateTime to = LocalDateTime.of(2019, 4, 29, 18, 30, 00);


        Pair<Integer, Double> result = RelativeBalanceCalculator.getRelativeBalanceForAccountInPeriod("A", from, to, transactions);


        assertEquals(new Pair<>(0, 0.0),result);

    }

    @Test
    public void shouldGetRelativeBalanceZeroWhenListHasNoTransactionsInPeriod() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("1",
                        LocalDateTime.of(2019, 3, 29, 18, 30, 00),
                        50.00, TransactionType.PAYMENT, "A","B"),
                new Transaction("2",
                        LocalDateTime.of(2019, 04, 18, 18, 30, 00),
                        50.00, TransactionType.PAYMENT, "A","B"),
                new Transaction("3",
                    LocalDateTime.of(2019, 05, 18, 18, 30, 00),
                        50.00, TransactionType.PAYMENT, "A","B")
        );
        LocalDateTime from = LocalDateTime.of(2019, 6, 25, 18, 30, 00);
        LocalDateTime to = LocalDateTime.of(2019, 7, 29, 18, 30, 00);

        Pair<Integer, Double> result = RelativeBalanceCalculator.getRelativeBalanceForAccountInPeriod("A", from, to, transactions);

        assertEquals(new Pair<>(0, 0.0),result);

    }

    @Test
    public void shouldReturnCorrectRelativeBalances() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("1",
                        LocalDateTime.of(2019, 3, 29, 18, 30, 00),
                        50.00, TransactionType.PAYMENT, "A","B"),
                new Transaction("2",
                        LocalDateTime.of(2019, 4, 18, 18, 30, 00),
                        50.00, TransactionType.PAYMENT, "A","B"),
                new Transaction("3",
                        LocalDateTime.of(2019, 5, 18, 18, 30, 00),
                        50.00, TransactionType.PAYMENT, "A","B")
        );
        LocalDateTime from = LocalDateTime.of(2019, 1, 25, 18, 30, 00);
        LocalDateTime to = LocalDateTime.of(2019, 7, 29, 18, 30, 00);

        Pair<Integer, Double> result1 = RelativeBalanceCalculator.getRelativeBalanceForAccountInPeriod("A", from, to, transactions);
        Pair<Integer, Double> result2 = RelativeBalanceCalculator.getRelativeBalanceForAccountInPeriod("B", from, to, transactions);

        assertEquals(result1, new Pair<>(3, -150.0));
        assertEquals(result2, new Pair<>(3, 150.0));
    }

    @Test
    public void shouldGetCorrectRelativeBalanceWhenListHasReversalsOutsideOfPeriod() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("1",
                        LocalDateTime.of(2018, 12, 29, 18, 30, 00),
                        50.00, TransactionType.PAYMENT, "A","B"),
                new Transaction("2",
                        LocalDateTime.of(2019, 1, 1, 18, 30, 00),
                        50.00, TransactionType.REVERSAL, "A","B", "1"),
                new Transaction("3",
                        LocalDateTime.of(2019, 3, 29, 18, 30, 00),
                        50.00, TransactionType.PAYMENT, "A","C"),
                new Transaction("4",
                        LocalDateTime.of(2019, 4, 18, 18, 30, 00),
                        200.00, TransactionType.PAYMENT, "B","A"),
                new Transaction("5",
                        LocalDateTime.of(2019, 5, 18, 18, 30, 00),
                        50.00, TransactionType.PAYMENT, "A","B")
        );
        LocalDateTime from = LocalDateTime.of(2019, 1, 1, 18, 30, 00);
        LocalDateTime to = LocalDateTime.of(2019, 5, 18, 18, 30, 00);

        Pair<Integer, Double> result = RelativeBalanceCalculator.getRelativeBalanceForAccountInPeriod("A", from, to, transactions);

        assertEquals(new Pair<>(3, 100.0),result);

    }
}