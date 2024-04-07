import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String expression = sc.nextLine();
        String result = calc(expression);
        System.out.println(result);


    }

    public static String calc(String input) throws Exception {
        String[] s = input.split(" ");
        if (s.length != 3) {
            throw new Exception("Неверный формат выражения");
        }
        boolean hasArabic = input.matches(".*[0-9].*");
        boolean hasRoman = input.matches(".*[IVXLCDM].*");
        if (hasArabic && hasRoman) {
            throw new IllegalArgumentException("Используются одновременно разные системы счисления");
        }
        if (s[0].contains(".") || s[2].contains(".")||s[0].contains(",") || s[2].contains(",")) {
            throw new Exception("Введено десятичное число");
        }

        int a;
        int b;
        boolean isArabic = true;

        try {

            a = Integer.parseInt(s[0]);
            b = Integer.parseInt(s[2]);
        } catch (NumberFormatException ex) {
            isArabic = false;
            a = romanToArabic(s[0]);
            b = romanToArabic(s[2]);
        }





        char op = s[1].charAt(0);
        if ((a < 0 || a > 10) || (b < 0 || b > 10)) {
            throw new Exception("Числа должны быть в диапазоне от 1 до 10");
        }

        int result;
        switch (op) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                if (!isArabic && result <= 0) {
                    throw new Exception("Результат не может быть отрицательным");
                }
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                if (b == 0) {
                    throw new Exception("Деление на ноль");
                }
                result = a / b;
                break;
            default:
                throw new Exception("Недопустимый оператор");
        }

        if (isArabic) {
            return String.valueOf(result);
        } else {
            if (result <= 0) {
                throw new Exception("Результат не может быть отрицательным");
            }
            return arabicToRoman(result);
        }
    }



    public static int romanToArabic(String input) {
        Map<Character, Integer> romanNumerals = new HashMap<>();
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        romanNumerals.put('X', 10);
        romanNumerals.put('L', 50);
        romanNumerals.put('C', 100);
        romanNumerals.put('D', 500);
        romanNumerals.put('M', 1000);

        int result = 0;
        int previousValue = 0;

        for (int i = input.length() - 1; i >= 0; i--) {
            int currentValue = romanNumerals.get(input.charAt(i));
            if (currentValue < previousValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
            previousValue = currentValue;
        }
        return result;
    }


    public static String arabicToRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 3999");
        }

        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder roman = new StringBuilder();
        int remaining = number;

        for (int i = 0; i < arabicValues.length; i++) {
            while (remaining >= arabicValues[i]) {
                roman.append(romanSymbols[i]);
                remaining -= arabicValues[i];
            }
        }
        return roman.toString();
    }
}


