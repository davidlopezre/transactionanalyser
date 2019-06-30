package com.transactionanalyser;


import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class CSVTransactionParserTest {

    @Test
    public void parseShouldReturnActualWithRelatedTransaction() throws IOException {
        BufferedReader bufferedReaderMock = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReaderMock.readLine())
                .thenReturn("TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, REVERSAL, TX10002")
                .thenReturn(null);

        Map<String, List<Transaction>> result = CSVTransactionParser.readCSV(bufferedReaderMock);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        Transaction expectedTransaction = new Transaction(
                            "TX10001",
                            LocalDateTime.parse("20/10/2018 12:47:55", formatter),
                            25.00,
                            TransactionType.REVERSAL,
                            "ACC334455",
                            "ACC778899",
                            "TX10002");
        Map<String, List<Transaction>> expected = new HashMap<>();
        expected.put("ACC334455", Arrays.asList(expectedTransaction));
        expected.put("ACC778899", Arrays.asList(expectedTransaction));
        assertEquals(result, expected);
    }

    @Test
    public void parseShouldReturnActualWithoutRelatedTransaction() throws IOException {
        BufferedReader bufferedReaderMock = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReaderMock.readLine())
                .thenReturn("1, 1, 2, 01/07/2019 12:47:55, 50.00, PAYMENT")
                .thenReturn(null);

        Map<String, List<Transaction>> result = CSVTransactionParser.readCSV(bufferedReaderMock);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        Transaction expectedTransaction = new Transaction(
                "1",
                LocalDateTime.parse("01/07/2019 12:47:55", formatter),
                50.00,
                TransactionType.PAYMENT,
                "1",
                "2"
                );
        Map<String, List<Transaction>> expected = new HashMap<>();
        expected.put("1", Arrays.asList(expectedTransaction));
        expected.put("2", Arrays.asList(expectedTransaction));

        assertEquals(result, expected);
    }

}