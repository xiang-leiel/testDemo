package com.example.test.comparable_comparator;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/8/16 10:21 PM
 */

public class ComparatorTest {

    public static void main(String[] args) {

        List<Student_ator> studentAbleList = Arrays.asList(new Student_ator("liming", 90),
                new Student_ator("xiaohong", 95),
                new Student_ator("zhoubin", 88),
                new Student_ator("xiaoli", 94)
        );

        // 排序前
        for(Student_ator st : studentAbleList) {
            System.out.println("排序前"+st.name+"---"+st.record);
        }

        Collections.sort(studentAbleList, new Student_ator());

/*        Collections.sort(studentAbleList, new Comparator<Student_able>() {

            @Override
            public int compare(Student_able o1, Student_able o2) {
                return o1.name.compareTo(o2.name);
            }
        });*/

        for(Student_ator st : studentAbleList) {
            System.out.println("排序前"+st.name+"---"+st.record);
        }

    }

}
