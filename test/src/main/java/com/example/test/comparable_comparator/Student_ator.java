package com.example.test.comparable_comparator;

import lombok.Data;

import java.util.Comparator;

/**
 * @Description 
 * @author leiel
 * @Date 2020/8/16 10:20 PM
 */
@Data
public class Student_ator implements Comparator<Student_ator> {

    String name;
    int record;

    public Student_ator(){}
    public Student_ator(String name, int record){
        this.name = name;
        this.record = record;
    }

    @Override
    public int compare(Student_ator o1, Student_ator o2) {
        return o1.record - o2.record;
    }

}
