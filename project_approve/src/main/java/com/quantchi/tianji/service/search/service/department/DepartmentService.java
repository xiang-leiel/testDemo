package com.quantchi.tianji.service.search.service.department;

import com.quantchi.tianji.service.search.dao.DmCsDeptMapper;
import com.quantchi.tianji.service.search.dao.DmWfRygwdzbMapper;
import com.quantchi.tianji.service.search.model.DmCsDept;
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
    private DmCsDeptMapper dmCsDeptMapper;

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

            List<DmCsDept> dmCsDepts = dmCsDeptMapper.selectDeptByType(3);

            for(DmCsDept dmCsDept : dmCsDepts){

                //度假区类型
                if(dmCsDept.getDeptDm() != 2001005 && dmCsDept.getDeptDm() != 2001007) {
                    departmentIds.add(dmCsDept.getDeptDm());
                }else {
                    List<Integer> groups = getGroupIds(dmCsDept.getDeptDm());
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
            List<Integer> xjdatas = dmCsDeptMapper.selectOneBySjDeptDm(integer);
            xjDeptDms.addAll(xjdatas);
        }
        return xjDeptDms;
    }

    private Integer getSjdepartment(Integer department) {
        DmCsDept dmCsDept = dmCsDeptMapper.selectByPrimaryKey(department);
        return dmCsDept.getDeptSjDm();
    }

    public DmCsDept selectDeptInfo(Integer deptDm) {
        if(deptDm == null) {
            return null;
        }

        DmCsDept dmCsDept = dmCsDeptMapper.selectByPrimaryKey(deptDm);

        if(dmCsDept.getQybj() != 1) {
            return null;
        }
        return dmCsDept;

    }

    public String selectDeptByUserDm(Integer userDm) {

        String bmmc = dmCsDeptMapper.selectOneByUserDm(userDm);

        return bmmc;

    }

    public Integer getDeptByDeptName(String deptName) {

        Integer deptDm = dmCsDeptMapper.selectOneBydeptName(deptName);

        return deptDm;

    }

    public String selectDeptByDeptDm(Integer deptDm) {

        DmCsDept dmCsDept = dmCsDeptMapper.selectByPrimaryKey(deptDm);

        return dmCsDept.getBmmc();

    }

    /**
     * 获取招商组片区
     * @return
     */
    public Map<String, DmCsDept> getArea(){

        Map<String, DmCsDept> areaMap = new HashMap<>();
        List<DmCsDept> dmCsDepts = dmCsDeptMapper.selectByType();

        for(DmCsDept dmCsDept : dmCsDepts) {
            areaMap.put(dmCsDept.getBmmc(), dmCsDept);
        }

        return areaMap;
    }

    /**
     * 获取片区下面的组
     * @return
     */
    public Map<String, DmCsDept> getGroup(Integer area){

        Map<String, DmCsDept> groupMap = new HashMap<>();

        List<DmCsDept> dmCsDepts = dmCsDeptMapper.selectByArea(area);

        for(DmCsDept dmCsDept : dmCsDepts) {
            groupMap.put(dmCsDept.getBmmc(), dmCsDept);
        }

        return groupMap;
    }

    /**
     * 获取片区下面的组
     * @return
     */
    public List<Integer> getGroupIds(Integer area){

        List<Integer> groupIds = new ArrayList<>();
        List<DmCsDept> dmCsDepts = dmCsDeptMapper.selectByArea(area);

        for(DmCsDept dmCsDept : dmCsDepts) {
            if(dmCsDept.getDeptType() == 1) {
                groupIds.add(dmCsDept.getDeptDm());
            }else {
                groupIds.addAll(getGroupIds(dmCsDept.getDeptDm()));
            }
        }

        return groupIds;
    }

    @Resource
    private DmWfRygwdzbMapper dmWfRygwdzbMapper;

    /**
     * 判断当前用户是否为组长
     * @param userDm
     * @return
     */
    public Boolean checkLeader(Integer userDm) {

        Integer value = dmWfRygwdzbMapper.checkLeader(userDm);
        if(value != null) {
            return true;
        }
        return false;
    }

}
