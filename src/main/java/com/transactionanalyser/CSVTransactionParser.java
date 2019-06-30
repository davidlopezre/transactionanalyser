package com.transactionanalyser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVTransactionParser {

    private static final String COMMA_DELIMITER = ",";
    public static final int TRANSACTION_ID_INDEX = 0;
    public static final int FROM_ACCOUNT_ID_INDEX = 1;
    public static final int TO_ACCOUNT_ID_INDEX = 2;
    public static final int CREATED_AT_INDEX = 3;
    public static final int AMOUNT_INDEX = 4;
    public static final int TRANSACTION_TYPE_INDEX = 5;

    /**
     * Returns a map with account ids mapped to the list of transactions involving the accounts
     * @param filename
     * @return the map with accountIds mapped to relevant transactions
     */
    public static  Map<String, List<Transaction>> parseTransactions(String filename) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        return readCSV(bufferedReader);
    }

    /**
     * Given a BufferedReader with the CSV file data, return the map with accountIds -> transactionLists.
     * This method was created to enable unit testing of potential problems with the reader.
     * @param bufferedReader
     * @return the map with accountIds mapped to relevant transactions
     */
    public static Map<String, List<Transaction>> readCSV(BufferedReader bufferedReader) throws IOException {
        Map<String, List<Transaction>> transactionMap = new HashMap<>();
        String line;
            while ((line = bufferedReader.readLine()) != null) {

                if (line.isEmpty()) {
                    return transactionMap;
                }

                Transaction newTransaction;

                String[] values = line.split(COMMA_DELIMITER);

                String transactionId = values[TRANSACTION_ID_INDEX].trim();
                String fromAccountId = values[FROM_ACCOUNT_ID_INDEX].trim();
                String toAccountId = values[TO_ACCOUNT_ID_INDEX].trim();
                LocalDateTime createdAt = LocalDateTime.parse(values[CREATED_AT_INDEX].trim(), DateTimeFormatters.DATE_TIME_FORMATTER);
                double amount = Double.parseDouble(values[AMOUNT_INDEX].trim());
                TransactionType transactionType = TransactionType.fromString(values[TRANSACTION_TYPE_INDEX].trim());

                // Transaction is REVERSAL
                if (values.length > 6) {
                    String relatedTransaction = values[6].trim();
                    newTransaction = new Transaction(
                            transactionId,
                            createdAt,
                            amount,
                            transactionType,
                            fromAccountId,
                            toAccountId,
                            relatedTransaction
                    );
                } else {
                    newTransaction = new Transaction(
                            transactionId,
                            createdAt,
                            amount,
                            transactionType,
                            fromAccountId,
                            toAccountId
                    );
                }

                addToTransactionList(fromAccountId, newTransaction, transactionMap);
                addToTransactionList(toAccountId, newTransaction, transactionMap);
            }
        return transactionMap;
    }

    private static void addToTransactionList(String accountId, Transaction t, Map<String, List<Transaction>> map) {
        if (!map.containsKey(accountId)) {
            map.put(accountId, new ArrayList<>());
        }
        map.get(accountId).add(t);
    }
}
