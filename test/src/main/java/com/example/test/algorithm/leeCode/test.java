package com.example.test.algorithm.leeCode;

import com.example.test.algorithm.leeCode.link.ListNode;
import com.fasterxml.jackson.databind.ser.std.ArraySerializerBase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/9/17 7:11 PM
 */

public class test {

        //设计一个LRU缓存结构，构造时指定大小k，支持get和set，
        //发生操作则认为是最常使用的，当缓存大小超过k时，需要删除缓存中，移除不经常使用的记录

        class Node {
            int key;
            int value;
            Node pre;
            Node next;
            public Node(int key, int value){
                this.key = key;
                this.value = value;
            }
        }

        public class LRUCache {
            // 容量
            int capacity;
            HashMap<Integer, Node> map = new HashMap<Integer, Node>();
            Node head=null;
            Node end=null;

            LinkedList<Node> list = new LinkedList<>();

            public LRUCache(int capacity) {
                this.capacity = capacity;
                head.next = end;
                end.pre = head;
            }

            // 获取第key个数据
            public int get(int key) {

                if(map.containsKey(key)) {
                    list.remove(map.get(key));
                    list.addFirst(map.get(key));
                    map.put(key,map.get(key));
                    return map.get(key).value;

                }
                return -1;
            }
            // 移除
            public void remove(Node n){

                list.remove(n);

            }
            // 添加
            public void set(int key, int value) {

                Node node = new Node(key, value);

                if(map.containsKey(key)){
                    list.remove((Integer)key);
                    list.addFirst(node);
                    map.put(key,node);
                    return;
                }
                if(list.size()==capacity){
                    map.remove(list.removeFirst());
                    map.put(key,node);
                    list.addFirst(node);
                }
                else{
                    map.put(key,node);
                    list.addFirst(node);
                }
            }

        }



}
