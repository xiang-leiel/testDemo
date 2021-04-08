package com.example.test.algorithm;

import com.example.test.model.Department;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/3/23 3:13 PM
 */

public class DeptTree {

    public static void main(String[] args) {
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(new Department(1, "研发部门", 0));
        departmentList.add(new Department(2, "研发团队1", 1));
        departmentList.add(new Department(3, "研发团队2", 1));
        departmentList.add(new Department(4, "财务部门", 0));
        departmentList.add(new Department(5, "财务A部门", 4));
        departmentList.add(new Department(6, "财务B部门", 4));
        departmentList.add(new Department(7, "财务A部门团队1", 5));
        departmentList.add(new Department(8, "财务A部门团队2", 5));
        departmentList.add(new Department(9, "财务B部门团队1", 6));
        departmentList.add(new Department(10, "财务B部门团队2", 6));

/*        List<Department> listTree = getThree(departmentList,0);

        for(Department department : listTree) {
            System.out.println(department.getName());
        }*/

        List<Integer> list = new ArrayList<>();
        getThree(list, departmentList, 0);
        System.out.println(list);
    }

    private static List<Department> getThree(List<Integer> resultList, List<Department> list,int parentId){
        //获取所有子节点
        List<Department> childTreeList = getChildTree(resultList, list,parentId);

        if(childTreeList.size() == 0) {
            if(!resultList.contains(parentId)) {
                resultList.add(parentId);
            }
        }else {
            for (Department dept : childTreeList) {
                resultList.add(dept.getId());
                dept.setChildren(getThree(resultList, list, dept.getId()));
            }
        }
        return childTreeList;
    }

    private static List<Department> getChildTree(List<Integer> resultList, List<Department> list,int id){
        List<Department> childTree = new ArrayList<>();
        for (Department dept:list) {
            if(dept.getParentId() == id){
                childTree.add(dept);
            }
        }
        return childTree;
    }

}
