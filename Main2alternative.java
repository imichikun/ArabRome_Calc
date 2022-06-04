package Calc;

import java.io.BufferedReader;      // альтернатива с более простой реализацией перевода начального значения римской
import java.io.InputStreamReader;   // цифры в арабскую. С двумя массивами (римский, арабский) и другим методом
import java.util.TreeMap;           // romeGetEntryValue()

public class Main2alternative {
    static String[] userLineArray;
    static Integer a;
    static Integer b;

    static final String[] romeDigits = {"X", "IX", "V", "IV", "I"}; // Римская система счисления строится по убыванию
    static final int[] arabDigits = {10, 9, 5, 4, 1};

    static final TreeMap<Integer, String> mapRoman = new TreeMap<>() {{
        put(1, "I");
        put(4, "IV");
        put(5, "V");
        put(9, "IX");
        put(10, "X");
        put(40, "XL");
        put(50, "L");
        put(90, "XC");
        put(100, "C");
    }};

    public static String calc(String input) {
        return input;
    }

    static String readUserLine() throws Exception{
        String result;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
        {
            String line = reader.readLine().trim();

            if ( line.isEmpty())
                throw new Exception("Строка пустая!");

            userLineArray = line.split(" "); // разбиваем строку на части

            if ( userLineArray.length != 3)
                throw new Exception("Строка не является математической операцией!");

            for (String arrayValue: romeDigits) {
                if (userLineArray[0].contains(arrayValue)) {
                    a = romeToArabicValue(userLineArray[0]);
                    checkNumberOfRomanDigits(a);
                }
            }

            for (String arrayValue : romeDigits) {
                if (userLineArray[2].contains(arrayValue)) {
                    b = romeToArabicValue(userLineArray[2]);
                    checkNumberOfRomanDigits(b);
                }
            }

            if ( a != null & b != null ){
                result = arabicToRoman(arithmeticOp(userLineArray[1]));
            }
            else if ( (a == null & b != null) || (a != null & b == null) )
                throw new Exception("Используются разные системы счисления!");
            else {
                a = Integer.valueOf(userLineArray[0]);
                b = Integer.valueOf(userLineArray[2]);
                checkNumberOfArabicDigits(a, b);

                result = Integer.toString(arabCalc(userLineArray[1]));
            }
        } catch (NumberFormatException nfe){
            throw new Exception("Введено вещественное число !");
        }
        return result;
    }

    static int romeToArabicValue(String value) {    // переводим полученное римское значение в арабское
        int rome2ArabValue = switch(value){
            case "I" -> 1;
            case "II" -> 2;
            case "III" -> 3;
            case "IV" -> 4;
            case "V" -> 5;
            case "VI" -> 6;
            case "VII" -> 7;
            case "VIII" -> 8;
            case "IX" -> 9;
            case "X" -> 10;
            default -> romeGetEntryValue(value);
        };

        return rome2ArabValue;
    }

    static int romeGetEntryValue(String romanString) {
        int arabicValue = 0;
        for (int i = 0; i < romeDigits.length; i++) {
            while(romanString.startsWith(romeDigits[i])){
                arabicValue += arabDigits[i];
                romanString = romanString.substring(romeDigits[i].length()); // возвращаем новую строку, которая теперь
            } //начинается ОТ уже использованного знака romeDigits[i].length и до конца предыдущей romanString.length()
        }
        return arabicValue;
    }

    static int arabCalc(String value) throws Exception{
        a = Integer.valueOf(userLineArray[0]);
        b = Integer.valueOf(userLineArray[2]);

        checkNumberOfArabicDigits(a, b);
        return arithmeticOp(value);
    }

    static String arabicToRoman(int arabicNumber) throws Exception{
        if (arabicNumber <= 0)
            throw new Exception("В римской системе счисления нет отрицательных чисел или ноля");

        StringBuilder builder = new StringBuilder();
        while (arabicNumber != 0){                      // floorkey возвращает ключ - (у нас число) равное или меньшее чем
            int num = mapRoman.floorKey(arabicNumber);  // существующий ключ в мапе
            arabicNumber = arabicNumber - num;
            builder.append(mapRoman.get(num));
        }

        return builder.toString();
    }

    static int arithmeticOp(String sign){
        int mathOperationResult = switch(sign){
            case "+" -> a + b;
            case "-" -> a - b;
            case "/" -> a / b;
            case "*" -> a * b;
            default -> throw new IllegalArgumentException(sign + " - Недопустимая математическая операция!");
        };
        return mathOperationResult;
    }

    static void checkNumberOfArabicDigits(int a, int b) throws Exception{
        if ( a <= 0 || a > 10 || b <= 0 || b > 10)
            throw new Exception("Арабское число должно быть в диапазоне от 1 до 10 !");
    }

    static void checkNumberOfRomanDigits(int a) throws Exception {
        if (a <= 0 || a > 10)
            throw new Exception("Римское число должно быть в диапазоне от I до X");
    }

    public static void main(String[] args) throws Exception{
        System.out.println("Введите строку для вычисления в формате: a + b (числа от 1 до 10)");
        System.out.println(calc(readUserLine()));
    }
}