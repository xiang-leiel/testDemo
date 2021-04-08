package com.example.test.comparable_comparator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/8/16 9:49 PM
 */

public class ComparableTest {

    public static void main(String[] args) {
        List<Student_able> studentAbleList = Arrays.asList(new Student_able("liming", 90),
                new Student_able("xiaohong", 95),
                new Student_able("zhoubin", 88),
                new Student_able("xiaoli", 94)
        );
        // 排序前
        for(Student_able st : studentAbleList) {
            System.out.println("排序前"+st.name+"---"+st.record);
        }
        Collections.sort(studentAbleList);
        System.out.println("");
        // 排序后
        for(Student_able st : studentAbleList) {
            System.out.println("排序后"+st.name+"---"+st.record);
        }

    }

}
