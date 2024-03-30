package me.stevemmmmm.thepitremake.utils;

import java.util.HashMap;
import java.util.Map;

public class RomanUtils {
    
    private static final RomanUtils INSTANCE = new RomanUtils();
    private static final Map<Character, Integer> ROMAN_VALUES = new HashMap<>();
    
    static {
        ROMAN_VALUES.put('I', 1);
        ROMAN_VALUES.put('V', 5);
        ROMAN_VALUES.put('X', 10);
        ROMAN_VALUES.put('L', 50);
    }
    
    private RomanUtils() {}
    
    public static RomanUtils getInstance() {
        return INSTANCE;
    }

    public String convertToRomanNumeral(int value) {

        StringBuilder sb = new StringBuilder();
        int[] values = {50, 40, 10, 9, 5, 4, 1};
        String[] numerals = {"L", "XL", "X", "IX", "V", "IV", "I"};
        
        int i = 0;
        while (value > 0) {
            if (value >= values[i]) {
                sb.append(numerals[i]);
                value -= values[i];
            } else {
                i++;
            }
        }
        
        return sb.toString();
    }
    
    public int convertRomanNumeralToInteger(String numeral) {
        int result = 0;
        int prevValue = 0;
        
        for (int i = numeral.length() - 1; i >= 0; i--) {
            int value = ROMAN_VALUES.getOrDefault(numeral.charAt(i), 0);
            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }
            prevValue = value;
        }
        
        return result;
    }
}
