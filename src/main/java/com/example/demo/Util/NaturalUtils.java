package com.example.demo.Util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;

public class NaturalUtils {
    public static String calculatePoint(double amount){

        BigDecimal amountBd = new BigDecimal(Double.toString(amount));
        BigDecimal p1 = BigDecimal.ZERO;
        BigDecimal p2 = BigDecimal.ZERO;
        DecimalFormat df = new DecimalFormat("#.00");
        if(amountBd.compareTo(BigDecimal.valueOf(100)) == 1){
            p1 = amountBd.subtract(BigDecimal.valueOf(100));
            p2 = BigDecimal.valueOf(50);
        }
        else if(amountBd.compareTo(BigDecimal.valueOf(100)) < 1 && amountBd.compareTo(BigDecimal.valueOf(50)) > -1){
            p2 = amountBd.subtract(BigDecimal.valueOf(50));
        }


        BigDecimal result = p1.multiply(BigDecimal.valueOf(2));
        result = result.add(p2);



        return df.format(result.doubleValue());

    }

    public static String calculateTotalPoint(HashMap<Integer,String> m){
        BigDecimal totalPoint = BigDecimal.ZERO;
        DecimalFormat df = new DecimalFormat("#.00");
        for(Integer month:m.keySet()){
            BigDecimal p1 = new BigDecimal(m.get(month));
            totalPoint = totalPoint.add(p1);
        }

        return df.format(totalPoint.doubleValue());
    }
}
