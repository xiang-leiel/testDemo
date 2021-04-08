package com.example.test.basis.arrayList;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/23 4:36 PM
 */

public class ArrayListDemo {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add(0,"dasda");


        String a = "ab";

        String b = "cd";

        String c = a + b;

        String d = "abcd";

        System.out.println(c == d);

        System.out.println(c.equals(d));

    }

}
