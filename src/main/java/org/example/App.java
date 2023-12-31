package org.example;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Locale swedishLocale = new Locale("sv", "SE");
        Locale.setDefault(swedishLocale);
        Scanner scanner = new Scanner(System.in);
        int[] prices = new int[24];
        while (true) {
            System.out.print("""
                    Elpriser
                    ========
                    1. Inmatning
                    2. Min, Max och Medel
                    3. Sortera
                    4. Bästa Laddningstid (4h)
                    e. Avsluta
                    """);
            String val = scanner.next();

            if (val.equals("e") || val.equals("E")) {
                System.out.print("Avsluta");
                break;
            } else if (val.equals("1")) {
                input(prices, scanner);
            } else {
                chooseOption(val, prices);
            }
        }
    }

    public static void chooseOption(String val, int[] prices) {

        switch (val) {
            case "2" -> minMaxMediumValue(prices);
            case "3" -> sortValues(prices);
            case "4" -> cheapest4Hours(prices);
            default -> System.out.print("Ogiltigt val");
        }

    }
    public static void input(int[] prices, Scanner scanner) {
        for (int i = 0; i < prices.length; i++) {
            String time = String.format("%02d-%02d", i, i + 1);
            System.out.print("Pris för klockan " + time + ": ");
            int val = Integer.parseInt(scanner.next());
            prices[i] = val;
        }
    }

    public static void minMaxMediumValue(int[] prices) {
        int min = prices[0];
        int max = prices[0];
        int sum = 0;
        for (int price : prices) {
            sum += price;
            min = Math.min(min, price);
            max = Math.max(max, price);
        }
        String highTime = null;
        String time = null;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] == min) {
                highTime = String.format("%02d-%02d", i, i + 1);
            }
            if (prices[i] == max) {
                time = String.format("%02d-%02d", i, i + 1);
            }
        }
        float average = (float) sum / prices.length;

        DecimalFormat df = new DecimalFormat("0.00");
        String averageString = df.format(average).replace(".", ",");
        System.out.print("Lägsta pris: " + highTime + ", " + min + " öre/kWh\n" +
                "Högsta pris: " + time + ", " + max + " öre/kWh\n" +
                "Medelpris: " + averageString + " öre/kWh\n");
    }


    public static void sortValues(int[] arr) {
        int[] sortedArr = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sortedArr);
        boolean[] printed = new boolean[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            String time = null;
            int index = -1;

            for (int j = 0; j < arr.length; j++) {
                if (arr[j] == sortedArr[i] && !printed[j]) {
                    time = String.format("%02d-%02d", j, j + 1);
                    index = j;
                    break;
                }
            }
            if (time != null) {
                System.out.println(time + " " + sortedArr[i] + " öre");
                printed[index] = true;
            }
        }
    }






    public static void cheapest4Hours(int[] arr) {
        int minSum = Integer.MAX_VALUE;
        int startTime = 0;
        for (int i = 0; i < arr.length - 3; i++) {
            int sum = 0;
            for (int j = i; j < i + 4; j++) {
                sum += arr[j];
            }
            if (sum < minSum) {
                minSum = sum;
                startTime = i;
            }
        }
        float averagePrice = minSum / 4f;
        DecimalFormat df = new DecimalFormat("0.0");
        df.setDecimalSeparatorAlwaysShown(true);
        String averageString = df.format(averagePrice).replace(".", ",");

        System.out.print("Påbörja laddning klockan " + startTime + "\nMedelpris 4h: " + averageString + " öre/kWh\n");
    }

}
