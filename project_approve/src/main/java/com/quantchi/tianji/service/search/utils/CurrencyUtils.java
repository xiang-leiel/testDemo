package com.quantchi.tianji.service.search.utils;

import java.math.BigDecimal;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/28 11:06 AM
 */

public class CurrencyUtils {

    /**
     *
     * @param money
     * @return
     */
    public static String getCurrency(BigDecimal money){

        //flag
        Boolean flag = true;
        int zeroCounts = 0;
        Long valueMoney = money.longValue();
        while(flag) {
            if(valueMoney%10 == 0) {
                valueMoney = valueMoney/10;
                zeroCounts++;
            }else {
                flag = false;
            }
        }
        String moneyStr = money.toString();

        int decimalValue = 0;
        if(moneyStr.length() == 9) {
            decimalValue = moneyStr.length()-1 - zeroCounts;

        }else if(moneyStr.length() == 10){

            decimalValue = moneyStr.length()-2 - zeroCounts;

        }else if(moneyStr.length() == 11){

            decimalValue = moneyStr.length()-3 - zeroCounts;

        }else if(moneyStr.length() == 12){

            decimalValue = moneyStr.length()-4 - zeroCounts;
        }else if(moneyStr.length() == 13){
            decimalValue = moneyStr.length()-5 - zeroCounts;
        }else if(moneyStr.length() == 14){
            decimalValue = moneyStr.length()-6 - zeroCounts;
        }
        if(decimalValue < 0) {
            decimalValue = 0;
        }

        String value = null;
        //大于一亿100,000,000
        if(money.compareTo(new BigDecimal("100000000")) >= 0) {
            value =  money.divide(new BigDecimal(100000000), decimalValue, BigDecimal.ROUND_UP) + "亿";
        }else{
            value = moneyStr.substring(0, moneyStr.length()-4) + "万";
        }


        return value;
    }

    public static void main(String[] args) {

        BigDecimal bigDecimal = new BigDecimal("0.000");

        System.out.println(bigDecimal.compareTo(BigDecimal.ZERO) > 0);

        System.out.println(getCurrency(new BigDecimal("2102211300000")));
    }
}
