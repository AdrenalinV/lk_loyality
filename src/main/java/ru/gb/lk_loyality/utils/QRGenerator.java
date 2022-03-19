package ru.gb.lk_loyality.utils;

public class QRGenerator {

    public static String generateQR(String value) {
        int sumchet = 0;
        int sumnechet = 0;
        int sum = 0;
        String res;
        for (int i = 0; i < value.length(); i++) {
            if (i % 2 == 0) sumchet += Integer.parseInt(value.substring(i, i + 1));
            else sumnechet += Integer.parseInt(value.substring(i, i + 1));
        }
        sum = sumchet + sumnechet * 3;
        res = String.valueOf((int)(10 - (sum - Math.floor(sum/10) * 10)));

        if (res.equals("10"))
        {
            res = "0";
        }
        return  value + res;
    }
}
