package com.prgrms.shortenurl.service;

import org.springframework.stereotype.Component;

@Component
public class Converter {
    public static String convertToBinary(int number) {
        int quotient = number;
        StringBuilder binaryString = new StringBuilder();

        while (quotient >= 2) {
            binaryString.append(quotient % 2);
            quotient = Math.floorDiv(quotient, 2);
        }
        binaryString.append(quotient);

        return binaryString.reverse().toString();
    }

    public static String convertToHex(int number) {
        int quotient = number;
        StringBuilder hexString = new StringBuilder();
        char[] codes = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        while (quotient >= 16) {
            int remainder = quotient % 16;
            hexString.append(codes[remainder]);
            quotient = Math.floorDiv(quotient, 16);
        }
        hexString.append(codes[quotient]);

        return hexString.reverse().toString();
    }

}
