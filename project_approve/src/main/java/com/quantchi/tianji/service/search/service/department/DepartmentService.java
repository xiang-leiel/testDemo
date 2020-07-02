package com.quantchi.tianji.service.search.service.department;

import com.quantchi.tianji.service.search.dao.CodeDeptmentMapper;
import com.quantchi.tianji.service.search.entity.CodeDeptment;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 部门服务类
 * @author leiel
 * @Date 2020/2/9 11:34 AM
 */
@Service
public class DepartmentService {

    @Resource
    private CodeDeptmentMapper codeDeptmentMapper;

    /**
     * 校验项目所属部门的上级部门是否有当前登录用户的部门
     * @param
     * @return
     */
    public Boolean checkDepartment(Integer department, Integer projectDepartmentId) {

        if(projectDepartmentId == null) {
            return false;
        }

        List<Integer> departmentIds = new ArrayList<>();

        if(department == 2001006) {

            departmentIds.add(2001006);

            List<CodeDeptment> dmCsDepts = codeDeptmentMapper.selectDeptByType(3);

            for(CodeDeptment codeDeptment : dmCsDepts){

                //度假区类型
                if(codeDeptment.getId() != 2001005 && codeDeptment.getId() != 2001007) {
                    departmentIds.add(codeDeptment.getId());
                }else {
                    List<Integer> groups = getGroupIds(codeDeptment.getId());
                    departmentIds.addAll(groups);
                }

            }

        } else {
            //依据projectDepartmentId获取该项目部门的上级有哪些
            departmentIds = getSjDeptDms(projectDepartmentId);
        }

        //依据部门id查看是否有上级，并将每次查询的上级部门id放入List<Integer>中
        if(CollectionUtils.isNotEmpty(departmentIds) && departmentIds.contains(department)) {
            return true;
        }

        return false;

    }

    public List<Integer> getSjDeptDms(Integer department) {
        List<Integer> departmentIds = new ArrayList<>();
        departmentIds.add(department);
        //依据projectDepartmentId获取该项目部门的上级有哪些
        Boolean deptFalg = true;
        while(deptFalg){
            Integer departmentNew = getSjdepartment(department);
            if(departmentNew == null) {
                deptFalg = false;
            }
            if(departmentNew != null) {
                department = departmentNew;
                departmentIds.add(departmentNew);
            }
        }
        return departmentIds;
    }

    public List<Integer> getXjDeptDms(Integer department) {

        List<Integer> departmentIds = new ArrayList<>();

        List<Integer> xjdepts = new ArrayList<>();
        xjdepts.add(department);

        departmentIds.add(department);
        //依据projectDepartmentId获取该部门的下级部门有哪些
        Boolean deptFalg = true;
        while(deptFalg){
            List<Integer> deptDms = getXjdepartment(departmentIds);

            if(CollectionUtils.isEmpty(deptDms)) {
                deptFalg = false;
            } else {
                departmentIds = deptDms;
                xjdepts.addAll(deptDms);
            }

        }
        return xjdepts;
    }

    private List<Integer> getXjdepartment(List<Integer> departments) {

        List<Integer> xjDeptDms = new ArrayList<>();

        for(Integer integer : departments) {
            List<Integer> xjdatas = codeDeptmentMapper.selectOneBySjDeptDm(integer);
            xjDeptDms.addAll(xjdatas);
        }
        return xjDeptDms;
    }

    private Integer getSjdepartment(Integer department) {
        CodeDeptment codeDeptment = codeDeptmentMapper.selectById(department);
        return codeDeptment.getPid();
    }

    public CodeDeptment selectDeptInfo(Integer deptDm) {
        if(deptDm == null) {
            return null;
        }

        CodeDeptment codeDeptment = codeDeptmentMapper.selectById(deptDm);

        if(codeDeptment.getIsValid() != 1) {
            return null;
        }
        return codeDeptment;

    }

    public String selectDeptByUserDm(Integer userDm) {

        String name = codeDeptmentMapper.selectOneByUserDm(userDm);

        return name;

    }

    public Integer getDeptByDeptName(String deptName) {

        Integer deptDm = codeDeptmentMapper.selectOneBydeptName(deptName);

        return deptDm;

    }

    public String selectDeptByDeptDm(Integer deptDm) {

        CodeDeptment codeDeptment = codeDeptmentMapper.selectById(deptDm);

        return codeDeptment.getName();

    }

    /**
     * 获取招商组片区
     * @return
     */
    public Map<String, CodeDeptment> getArea(){

        Map<String, CodeDeptment> areaMap = new HashMap<>();
        List<CodeDeptment> dmCsDepts = codeDeptmentMapper.selectByType();

        for(CodeDeptment dmCsDept : dmCsDepts) {
            areaMap.put(dmCsDept.getName(), dmCsDept);
        }

        return areaMap;
    }

    /**
     * 获取片区下面的组
     * @return
     */
    public Map<String, CodeDeptment> getGroup(Integer area){

        Map<String, CodeDeptment> groupMap = new HashMap<>();

        List<CodeDeptment> dmCsDepts = codeDeptmentMapper.selectByArea(area);

        for(CodeDeptment dmCsDept : dmCsDepts) {
            groupMap.put(dmCsDept.getName(), dmCsDept);
        }

        return groupMap;
    }

    /**
     * 获取片区下面的组
     * @return
     */
    public List<Integer> getGroupIds(Integer area){

        List<Integer> groupIds = new ArrayList<>();
        List<CodeDeptment> dmCsDepts = codeDeptmentMapper.selectByArea(area);

        for(CodeDeptment dmCsDept : dmCsDepts) {
            if(dmCsDept.getType() == 1) {
                groupIds.add(dmCsDept.getId());
            }else {
                groupIds.addAll(getGroupIds(dmCsDept.getId()));
            }
        }

        return groupIds;
    }


}
