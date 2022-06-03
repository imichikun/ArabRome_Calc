package Calc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TreeMap;

public class Main {
    static String[] userLineArray;
    static Integer a;
    static Integer b;
    static final String[] romeDigits = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
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

            for (String check: romeDigits) {
                if ( userLineArray[0].equals(check) )
                    a = romeEntryValue(userLineArray[0]);
            }

            for (String check: romeDigits) {
                if ( userLineArray[2].equals(check) ) {
                    b = romeEntryValue(userLineArray[2]);
                }
            }

            if ( a != null & b != null ){
                result = arabicToRoman(arithmeticOp(userLineArray[1]));
            }
            else if ( (a == null & b != null) || (a != null & b == null) )
                throw new Exception("Используются разные системы счисления!");
            else
                result = Integer.toString(arabCalc(userLineArray[1]));
            }

            return result;
        }

    static int romeEntryValue(String value) throws Exception{

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
            default -> throw new Exception("Римские числа должны быть в диапазоне от I до X !");
        };
        return rome2ArabValue;
    }

    static int arabCalc(String value) throws Exception{
        a = Integer.valueOf(userLineArray[0]);
        b = Integer.valueOf(userLineArray[2]);

        checkNumberOfDigits(a, b);
        return arithmeticOp(value);
    }

    static String arabicToRoman(int arabicNumber) {
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

    static void checkNumberOfDigits(int a, int b) throws Exception{
        if ( a <= 0 || a > 10 || b <= 0 || b > 10)
            throw new Exception("Число должно быть в диапазоне от 1 до 10 !");
    }

    public static void main(String[] args) throws Exception{
        System.out.println("Введите строку для вычисления в формате: a + b (числа от 1 до 10)");
        System.out.println(calc(readUserLine()));
    }
}