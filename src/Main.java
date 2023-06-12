import java.util.Scanner;

class Main {
    public static void main(String[] args) throws InvalidInputException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражения для подсчета:");
        String input = scanner.nextLine();
        String answer = calc(input);
        System.out.println(answer);
    }

    public static String calc(String input) throws InvalidInputException {
        Character operator;
        operator = getOperator(input);
        String[] operands = input.split("\\+|-|\\*|/");
        int[] operandsInt = new int[operands.length];

        boolean areRoman = areOperandsRoman(operands);
        if (!areRoman) {
            for (int i = 0; i < operands.length; i++) {
                operandsInt[i] = Integer.parseInt(operands[i]);
            }
        } else {
            for (int i = 0; i < operands.length; i++) {
                operandsInt[i] = romanToArabic(operands[i]);
            }
        }

        int answerInt = 0;
        switch (operator) {
            case ('+'):
                answerInt = operandsInt[0] + operandsInt[1];
                break;
            case ('-'):
                answerInt = operandsInt[0] - operandsInt[1];
                break;
            case ('*'):
                answerInt = operandsInt[0] * operandsInt[1];
                break;
            case ('/'):
                answerInt = operandsInt[0] / operandsInt[1];
                break;
        }

        if (areRoman && answerInt < 0) {
             throw new InvalidInputException("В римской системе нет отрицательных чисел");
        }
        if (areRoman && answerInt == 0) {
            throw new InvalidInputException("В римской системе нет нуля");
        }
        if (operandsInt[0] > 10 || operandsInt[1] > 10 || operandsInt[0] < 1 || operandsInt[1] < 1 ) {
             throw new InvalidInputException("Числа должны быть от одного до десяти");
        }

        if (areRoman) {
             return String.valueOf(arabicToRoman(answerInt));
        }

        return String.valueOf(answerInt);
    }
    public static Character getOperator(String input) throws InvalidInputException {
        int count = 0;
        Character foundOperator = null;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                count++;
                foundOperator = c;
            }
        }

        if (count < 1) {
            throw new InvalidInputException("Строка не является математической операцией");
        }

        if (count > 1){
            throw new InvalidInputException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        return foundOperator;
    }

    public static int romanToArabic(String roman) {
        int previousNumber = 0;
        int result = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            char c = roman.charAt(i);
            int currentInt = translateRomanSymbolToArabic(c);
            if (currentInt >= previousNumber) {
                result = result + currentInt;
            } else {
                result = result - currentInt;
            }
            previousNumber = currentInt;
        }
        return result;
    }

    public static boolean isRoman(String inputString) {
        for (int i = 0; i < inputString.length(); i++) {
            String currentCharacter = String.valueOf(inputString.charAt(i));
            try {
                Roman.valueOf(currentCharacter);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return true;
    }

    public static int translateRomanSymbolToArabic(char romanSymbol) {
        Roman roman = Roman.valueOf(String.valueOf(romanSymbol));
        return roman.getTranslation();
    }

    public static boolean isInt(String inputString) {
        try {
            Integer.parseInt(inputString);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String arabicToRoman(int arabic) {
        String answerInRoman = "";
        Roman[] allRomanValues = Roman.values();
        int i = 0;

        while (arabic > 0 && i < allRomanValues.length) {
            int currentNumber = allRomanValues[i].getTranslation();
            if (currentNumber <= arabic){
                answerInRoman += allRomanValues[i];
                arabic -=currentNumber;
            } else {
                i++;
            }
        }
        return answerInRoman;
    }

    public static boolean areOperandsRoman(String[] operands) throws InvalidInputException {
        int countInt = 0;
        int countRoman = 0;

        for (String operand: operands) {
            if (isInt(operand)){
                countInt++;
            } else if (isRoman(operand)) {
                countRoman++;
            }  else {
                throw new InvalidInputException("Строка не является математической операцией");
            }
        }

        if (countInt > 0 && countRoman > 0) {
            throw new InvalidInputException("Используются одновременно разные системы счисления");
        }

        if (countInt < 2 && countRoman < 2) {
            throw new InvalidInputException("Строка не является математической операцией");
        }

        return countRoman > 0;
    }
}
