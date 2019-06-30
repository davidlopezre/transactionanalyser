package com.transactionanalyser;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.transactionanalyser.RelativeBalanceCalculator.*;

public class Startup {

    public static void main(String[] args){
        Map<String, List<Transaction>> transactionMap;
        try {
            transactionMap = CSVTransactionParser.parseTransactions(args[0]);
            mainLoop(transactionMap);
        } catch (FileNotFoundException e) {
            System.out.println("File " + args[0] + " could not be found");
        } catch (IOException e) {
            System.out.println("There was a problem loading the file, please try again");
        }
    }

    public static void mainLoop(Map<String, List<Transaction>> transactionMap) throws IOException {
        while (true) {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter account ID: ");
            String accountId = in.readLine();

            System.out.println("Please enter time frame in format dd/MM/yyyy HH:mm:s");
            System.out.print("FROM: ");
            LocalDateTime from = LocalDateTime.parse(in.readLine(), DateTimeFormatters.DATE_TIME_FORMATTER);

            System.out.print("TO: ");
            LocalDateTime to = LocalDateTime.parse(in.readLine(), DateTimeFormatters.DATE_TIME_FORMATTER);

            Pair<Integer, Double> relativeBalance = calculateRelativeBalance(accountId, from, to, transactionMap);

            if (relativeBalance == null) {
                System.out.println("The given account ID " + "\"" +accountId + "\"" + " was not found");
            } else {
                System.out.println("=================================================================");
                System.out.println("Account ID: " + accountId);
                System.out.println("FROM: " + from);
                System.out.println("TO: " + to);
                System.out.println("Relative balance for the period is: " + relativeBalance.getValue());
                System.out.println("Number of transactions included is: " + relativeBalance.getKey());
                System.out.println("=================================================================\n");
                System.out.println("Ctrl^C to exit");
            }

        }
    }

    private static Pair<Integer, Double> calculateRelativeBalance(String accountId,
                                                 LocalDateTime from,
                                                 LocalDateTime to,
                                                 Map<String, List<Transaction>> transactionMap) {

        if (!transactionMap.containsKey(accountId)) {
            return null;
        }

        List<Transaction> relevantTransactions = transactionMap.get(accountId);

        return getRelativeBalanceForAccountInPeriod(accountId, from, to, relevantTransactions);
    }
}
