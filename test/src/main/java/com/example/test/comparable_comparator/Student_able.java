package com.example.test.comparable_comparator;
/**
 * @Description 
 * @author leiel
 * @Date 2020/8/16 9:49 PM
 */

public class Student_able implements Comparable<Student_able>{

    String name;
    int record;

    public Student_able(){}
    public Student_able(String name, int record){
        this.name = name;
        this.record = record;
    }

    @Override
    public int compareTo(Student_able stu) {
        // 调用String 类的compareTo方法，返回值 -1，0，1
        return this.name.compareTo(stu.name);
    }
}
