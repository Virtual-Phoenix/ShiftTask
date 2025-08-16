package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Main {

    public static Boolean flagShortStatistic = false;
    public static Boolean flagFullStatistic = false;
    public static Boolean flagPrefixToFile = false;
    public static Boolean flagPathToSave = false;
    public static Boolean flagAppendToFile = false;

    public static Integer countInteger = 0;
    public static Integer countString = 0;
    public static Integer countDouble = 0;
    public static String pathToSave = "";
    public static String prefix = "";

    public static List<String> arrayNameFiles = new ArrayList<>();

    public static List<Integer> arrayIntegers = new ArrayList<>();
    public static List<Double> arrayDoubles = new ArrayList<>();
    public static List<String> arrayStrings = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("передано 0 аргументов");
        }

        readArgs(args);
        readAndWriteFiles();
        printStatistic();

    }

    // чтение из файла и запись в выходящий файл
    public static void readAndWriteFiles() {
        for (String nameFile : arrayNameFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader(nameFile))) {

                List<String> intLines = new ArrayList<>();
                List<String> doubleLines = new ArrayList<>();
                List<String> stringLines = new ArrayList<>();

                String s;
                String type;
                while ((s = br.readLine()) != null) {
                    type = typeLine(s);
                    if (type.equals("Integer")) {
                        intLines.add(s);
                        arrayIntegers.add(Integer.parseInt(s));
                        countInteger++;
                    }
                    if (type.equals("Double")) {
                        doubleLines.add(s);
                        arrayDoubles.add(Double.parseDouble(s));
                        countDouble++;
                    }
                    if (type.equals("String")) {
                        stringLines.add(s);
                        countString++;
                    }
                }
                if (!intLines.isEmpty()) {
                    try (BufferedWriter bwI = new BufferedWriter(new FileWriter(pathToSave +
                            prefix + "integer.txt", flagAppendToFile));) {
                        for (String intLine : intLines) bwI.write(intLine + "\n");
                    }
                }
                if (!doubleLines.isEmpty()) {
                    try (BufferedWriter bwD = new BufferedWriter(new FileWriter(pathToSave +
                            prefix + "double.txt", flagAppendToFile))) {
                        for (String doubleLine : doubleLines) bwD.write(doubleLines + "\n");
                    }
                }
                if (!stringLines.isEmpty()) {
                    try (BufferedWriter bwS = new BufferedWriter(new FileWriter(pathToSave +
                            prefix + "string.txt", flagAppendToFile))) {
                        for (String stringLine : stringLines) bwS.write(stringLine + "\n");
                    }
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void printStatistic() {
        if (flagFullStatistic) {

            System.out.println("Количество целых чисел " + countInteger);
            System.out.println("Количество чисел с плавающей точкой " + countDouble);
            System.out.println("Количество строк " + countString);
            System.out.println("########################################");
            System.out.println("Колличество целых чисел " + countInteger);
            System.out.println("Среднее значение целых чисел " +
                    arrayIntegers.stream()
                            .mapToInt(Integer::intValue)
                            .average()
                            .getAsDouble());

            System.out.println("Сумма значений целых чисел " +
                    arrayIntegers.stream()
                            .mapToInt(Integer::intValue)
                            .sum());

            System.out.println("Максимальное значение целых чисел " +
                    arrayIntegers.stream()
                            .max(Integer::compareTo).orElse(0));


            System.out.println("Минимальное значение целых чисел " +
                    arrayIntegers.stream()
                            .min(Integer::compareTo).orElse(0));

            System.out.println("########################################");

            System.out.println("Колличество чисел с плавающей точкой " + countDouble);
            System.out.println("Среднее значение чисел с плавующей точкой " +
                    arrayDoubles.stream()
                            .mapToDouble(Double::doubleValue)
                            .average()
                            .getAsDouble());

            System.out.println("Сумма значений чисел с плавующей точкой " +
                    arrayDoubles.stream()
                            .mapToDouble(Double::doubleValue)
                            .sum());

            System.out.println("Максимальное значение чисел с плавующей точкой " +
                    arrayDoubles.stream()
                            .max(Double::compareTo).orElse(0.0));


            System.out.println("Минимальное значение чисел с плавующей точкой " +
                    arrayDoubles.stream()
                            .min(Double::compareTo).orElse(0.0));
            System.out.println("########################################");
            System.out.println("Количество строк " + countString);
            System.out.println("Максимальная строка " +
                    arrayStrings.stream()
                            .max(Comparator.comparing(String::length))
                            .orElse(" "));
            System.out.println("Минимальная строка " +
                    arrayStrings.stream()
                            .min(Comparator.comparing(String::length))
                            .orElse(" "));

        } else {
            System.out.println("Количество целых чисел " + countInteger);
            System.out.println("Количество чисел с плавающей точкой " + countDouble);
            System.out.println("Количество строк " + countString);
        }
    }
    public static String typeLine(String line) {
        if (line.matches("-?\\d+")) {
            return "Integer";
        } else if (line.matches("-?\\d+(\\.\\d+)?")) {
            return "Double";
        }
        return "String";
    }

    public static void readArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-o")) {
                flagPathToSave = true;
                pathToSave = args[i + 1];
            }
            if (args[i].equals("-p")) {
                flagPrefixToFile = true;
                prefix = args[i + 1];
            }
            if (args[i].equals("-s")) {
                flagShortStatistic = true;
            }
            if (args[i].equals("-f")) {
                flagFullStatistic = true;
            }
            if (args[i].equals("-a")) {
                flagAppendToFile = true;
            }
            if (args[i].endsWith(".txt")) {
                arrayNameFiles.add(args[i]);
            }
        }
        if (flagShortStatistic && flagFullStatistic) {
            throw new RuntimeException("Должен быть выбран только один вид статистики");
        }
    }


}


