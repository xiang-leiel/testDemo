package com.quantchi.tianji.service.search.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.quantchi.tianji.service.search.dao.ExportDao;
import com.quantchi.tianji.service.search.model.PageBean;
import com.quantchi.tianji.service.search.service.ExportService;

import com.quantchi.tianji.service.search.utils.JsonUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.quantchi.tianji.service.search.enums.ProjectSpecialTagsEnum.GEOGRAPHIC_INFORMATION;
import static com.quantchi.tianji.service.search.enums.ProjectSpecialTagsEnum.INDUSTRIAL_PROJECT;
import static com.quantchi.tianji.service.search.enums.ProjectSpecialTagsEnum.TALENT_PROJECT;
import static java.util.stream.Collectors.toList;

@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportDao exportDao;

    private static String shanghaiCode = "021";

    private static List<String> sunanCode = new ArrayList<>();
    static {
        sunanCode.add("025");//南京
        sunanCode.add("0512");//苏州
        sunanCode.add("0510");//无锡
        sunanCode.add("0519");//常州
        sunanCode.add("0511");//镇江
    }
    @Override
    public void getProjectInfo(Map<String, Object> condition, String email) {
        String startDate = condition.get("startDate").toString();
        int year = Integer.parseInt(startDate.substring(0,4));
        condition.put("industry", INDUSTRIAL_PROJECT.getCode());
        condition.put("talent", TALENT_PROJECT.getCode());
        condition.put("geo", GEOGRAPHIC_INFORMATION.getCode());
        HSSFWorkbook workbook=new HSSFWorkbook();
        for (String group : Arrays.asList((String[]) condition.get("groupList"))){
            condition.put("group", group);
            Map<String, Object> head = exportDao.getProjectHeadInfo(condition);
            List<Map<String, Object>> list = exportDao.getProjectList(condition);
            HSSFSheet sheet=workbook.createSheet(group);

            HSSFCellStyle style=workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            //第一行
            HSSFRow row = sheet.createRow( 0);
            HSSFCell cell=row.createCell(0);
            CellRangeAddress region=new CellRangeAddress(0, 0, 0, 12);
            cell.setCellStyle(style);
            cell.setCellValue(year+"德清县驻点招商项目信息采集表");
            sheet.addMergedRegion(region);
            //第二行
            row = sheet.createRow( 1);
            cell=row.createCell(0);
            region=new CellRangeAddress(1, 1, 0, 12);
            cell.setCellStyle(style);
            String projectValue = "共上报在谈项目"+head.get("allProject")+"个（新增"+head.get("newProject")
                    +"个），（其中产业项目"+head.get("industryNum")+"个，人才项目"+head.get("talentNum")
                    +"个，地理信息项目"+head.get("geoNum")+"个，上海资5亿元以上项目"+head.get("fiveNum")+"个";
            cell.setCellValue(projectValue);
            sheet.addMergedRegion(region);

            row = sheet.createRow( 2);
            cell=row.createCell(0);
            region=new CellRangeAddress(2, 2, 0, 12);
            cell.setCellStyle(style);
            String groupValue = "所属片组："+group+"    填报人："+head.get("name").toString()
                    +"    联系电话："+head.get("mobile").toString()+"    填报时间："+head.get("newTime").toString();
            cell.setCellValue(groupValue);
            sheet.addMergedRegion(region);

            HSSFCellStyle listStyle=workbook.createCellStyle();
            listStyle.setAlignment(HorizontalAlignment.CENTER);
            listStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            listStyle.setBorderBottom(BorderStyle.THIN);
            listStyle.setBorderLeft(BorderStyle.THIN);
            listStyle.setBorderRight(BorderStyle.THIN);
            listStyle.setBorderTop(BorderStyle.THIN);
            String[] headArry = new String[]{"序号", "项目名称", "投资方", "固定资产投资", "投资方简介", "项目内容",
                    "需用土地", "企业联系人", "联系方式", "片区", "进度、备注", "产业类型", "研判结果"};
            List<String> headList = new LinkedList<>(Arrays.asList(headArry));
            row = sheet.createRow( 3);
            for (int i = 0 ;i <headList.size(); i++) {
                cell = row.createCell(i);
                cell.setCellValue(headList.get(i));
                cell.setCellStyle(listStyle);
            }
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow( i + 4);
                Map<String, Object> map = list.get(i);
                for (int j = 0 ;j <headArry.length; j++) {
                    cell = row.createCell(j);
                    if (j == 0 ){
                        cell.setCellValue(i + 1);
                    }else {
                        cell.setCellValue(map.get(headArry[j]).toString());
                    }
                    cell.setCellStyle(listStyle);
                }
            }
            row = sheet.createRow( 3+list.size()+1);
            cell=row.createCell(0);
            region=new CellRangeAddress(4+list.size(), 4+list.size(), 0, 12);
            String value = "备注：1.本表由各招商组组长（部长）认真审核后统一上报至招商科；";
            cell.setCellValue(value);
            sheet.addMergedRegion(region);

            row = sheet.createRow( 5+list.size());
            cell=row.createCell(0);
            region=new CellRangeAddress(5+list.size(), 5+list.size(), 0, 12);
            value = "        2.有新增项目或者项目有进度更新时都需要填写本报表，如无不需填写；";
            cell.setCellValue(value);
            sheet.addMergedRegion(region);

            row = sheet.createRow( 6+list.size());
            cell=row.createCell(0);
            region=new CellRangeAddress(6+list.size(), 6+list.size(), 0, 12);
            value = "        3.若项目一月内无进度更新，请说明并备注原因；";
            cell.setCellValue(value);
            sheet.addMergedRegion(region);

            row = sheet.createRow( 7+list.size());
            cell=row.createCell(0);
            region=new CellRangeAddress(7+list.size(), 7+list.size(), 0, 12);
            value = "        4.此报表每周五上午十点报送至商务局招商科严秋燕（13754209545，县府网短号649545）处，节假日顺延。";
            cell.setCellValue(value);
            sheet.addMergedRegion(region);

        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMail(new ByteArrayInputStream(bos.toByteArray()), "驻点招商项目信息采集表", email);
    }

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public void getPerformanceInfo(Map<String, Object> condition, String email) {
        String startDate = condition.get("startDate").toString();
        System.out.println(startDate+":startDate:"+startDate.substring(0,4)+":date:"+startDate.substring(5,7));
        int year = Integer.parseInt(startDate.substring(0,4));
        int month = Integer.parseInt(startDate.substring(5, startDate.indexOf("-",startDate.indexOf("-")+1 )));
        double maxVisitNum = 0;
        double maxReceptionNum = 0;
        double maxProjectNum = 0;
        double maxExcellentNum = 0;
        double maxLeaderNum = 0;
        HSSFWorkbook workbook=new HSSFWorkbook();
        for(String group : Arrays.asList((String[]) condition.get("groupList"))){
            condition.put("group", group);
            List<Map<String, Object>> list = exportDao.getPerformanceList(condition);
            maxVisitNum = getMaxNum(list, "visitNum");
            maxReceptionNum = getMaxNum(list, "receptionNum");
            maxProjectNum = getMaxNum(list, "projectNum");
            maxExcellentNum = getMaxNum(list, "excellentNum");
            maxLeaderNum = getMaxNum(list, "leaderNum");
            double total = 0;
            int sort = 1;
            for (Map<String, Object> performanceMap : list) {
                Double longitude = (Double) performanceMap.get("longitude");
                Double latitude = (Double) performanceMap.get("latitude");
                List<String> cityList = getProvinceCity(longitude+","+latitude);
                String city = "";
                if (cityList != null &&cityList.size() >0 ) {
                    city = cityList.get(0);
                }
                condition.put("performanceStaffId", performanceMap.get("id"));
                List<Map<String, Object>> signList = exportDao.getSignTudeList(condition);
                int i = 0;
                List<String> signDateList = new ArrayList<>();
                if (signList.size()>0) {
                    for (Map<String, Object> map : signList) {
                        List<String> signCity = getProvinceCity(map.get("tude").toString());
                        if (signCity.contains(city)){
                            i++;
                            signDateList.add(map.get("date").toString());
                        }
                    }
                }
                List<String> workList = getWorkDates(year, month);
                workList = workList.stream().filter(item -> !signDateList.contains(item)).collect(toList());
                performanceMap.put("signNum", i);
                performanceMap.put("outNum", workList.size()*10.0);
                if (i>=20){
                    performanceMap.put("signGrade", 10.0);
                }else if (i>=15){
                    performanceMap.put("signGrade", 9.0);
                }else if (i>=10){
                    performanceMap.put("signGrade", 8.0);
                }else {
                    if (i -1<0){
                        performanceMap.put("signGrade", 0.0);
                    }else {
                        performanceMap.put("signGrade", (i - 1)*1.0);
                    }
                }
                DecimalFormat df = new DecimalFormat("#.0");
                performanceMap.put("visitGrade", maxVisitNum ==0? 0.0:df.format((Double.valueOf(performanceMap.get("visitNum").toString())/maxVisitNum)*10));
                performanceMap.put("receptionGrade", maxReceptionNum ==0? 0.0:df.format((Double.valueOf(performanceMap.get("receptionNum").toString())/maxReceptionNum)*10));
                performanceMap.put("projectGrade", maxProjectNum ==0? 0.0:df.format((Double.valueOf(performanceMap.get("projectNum").toString())/maxProjectNum)*15));
                performanceMap.put("excellentGrade",maxExcellentNum ==0? 0.0:df.format( (Double.valueOf(performanceMap.get("excellentNum").toString())/maxExcellentNum)*15));
                performanceMap.put("leaderGrade", maxLeaderNum ==0? 0.0:df.format((Double.valueOf(performanceMap.get("leaderNum").toString())/maxLeaderNum)*20));
                List<String> foreignList = exportDao.getForeignList(condition);
                double foreignCount = 0;
                for (String key : foreignList) {
                    if (key == null) {
                        foreignCount = foreignCount + 20;
                    }else {
                        String[] keyArry = key.split(",");
                        foreignCount = foreignCount + 20.0/keyArry.length;
                    }
                }
                performanceMap.put("foreignGrade", df.format(foreignCount));
                total = (Double)performanceMap.get("signGrade") - (Double)performanceMap.get("outNum")
                        + Double.valueOf(performanceMap.get("visitGrade").toString()) + Double.valueOf(performanceMap.get("receptionGrade").toString())+ Double.valueOf(performanceMap.get("projectGrade").toString())
                        + Double.valueOf(performanceMap.get("excellentGrade").toString()) + Double.valueOf(performanceMap.get("leaderGrade").toString())  + Double.valueOf(performanceMap.get("foreignGrade").toString()) ;
                if (total <0 ){
                    performanceMap.put("total", 0.0);
                }else {
                    performanceMap.put("total", total);
                }
                performanceMap.put("signProjectNum", "-");
                performanceMap.put("signProjectGrade", "-");
                performanceMap.put("sort", sort);
                sort++;
            }
            for (Map<String, Object> performanceMap : list) {
                Double totalSort = (Double) performanceMap.get("total");
                int sortNum = (int) performanceMap.get("sort");
                for (Map<String, Object> performance : list){
                    Double totalSortOther = (Double) performance.get("total");
                    int sortNumOther = (int) performance.get("sort");
                    if (totalSort < totalSortOther && sortNum < sortNumOther) {
                        int sub = sortNum;
                        sortNum = sortNumOther;
                        sortNumOther = sub;
                    }
                }
            }
            //导出excel
            HSSFSheet sheet=workbook.createSheet(group);

            HSSFCellStyle style=workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            //第一行
            HSSFRow row = sheet.createRow( 0);
            HSSFCell cell=row.createCell(0);
            CellRangeAddress region=new CellRangeAddress(0, 0, 0, 19);
            cell.setCellStyle(style);
            cell.setCellValue("德清县驻点招商员月度绩效比拼表");
            sheet.addMergedRegion(region);
            //第二行
            row = sheet.createRow( 1);
            cell=row.createCell(0);
            region=new CellRangeAddress(1, 1, 0, 19);
            String projectValue = "招商组名称："+group+"                                     "+month+"月";
            cell.setCellValue(projectValue);
            sheet.addMergedRegion(region);

            HSSFCellStyle listStyle=workbook.createCellStyle();
            listStyle.setAlignment(HorizontalAlignment.CENTER);
            listStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            listStyle.setBorderBottom(BorderStyle.THIN);
            listStyle.setBorderLeft(BorderStyle.THIN);
            listStyle.setBorderRight(BorderStyle.THIN);
            listStyle.setBorderTop(BorderStyle.THIN);
            net.sf.json.JSONObject schemaInfo  = JsonUtil.getJsonDataToList("static/json/export");
            Map<String, Object> headMap = (Map<String, Object>) schemaInfo.get("招商员月度绩效表");
            row = sheet.createRow( 2);
            HSSFRow subRow = sheet.createRow(3);
            int headNum = 0;
            for (String key : headMap.keySet()) {
                Object value = headMap.get(key);
                cell = row.createCell(headNum);
                if (value instanceof String) {
                    region=new CellRangeAddress(2, 3, headNum, headNum);
                    cell.setCellValue(key);
                    cell.setCellStyle(listStyle);
                    sheet.addMergedRegion(region);
                }else {
                    region=new CellRangeAddress(2, 2, headNum, headNum+1);
                    cell.setCellValue(key);
                    sheet.addMergedRegion(region);
                    cell.setCellStyle(listStyle);
                    Map<String, Object> subHead = (Map<String, Object>) value;
                    for (String subKey : subHead.keySet()) {

                        cell = subRow.createCell(headNum);
                        cell.setCellValue(subKey);
                        cell.setCellStyle(listStyle);
                        headNum++;

                    }
                    headNum--;
                }
                headNum++;
            }

            for (int i = 0; i <list.size(); i ++) {
                row = sheet.createRow(4+i);
                Map<String, Object> map = list.get(i);
                int j = 0;
                for (String key : headMap.keySet()){
                    cell = row.createCell(j);
                    if (key.equals("序号")){
                        cell.setCellValue(i + 1);
                        cell.setCellStyle(listStyle);
                    }else {
                        if (headMap.get(key) instanceof String){
                            cell.setCellValue(map.get(headMap.get(key).toString()).toString());
                            cell.setCellStyle(listStyle);
                        }else {
                            Map<String, Object> subHead = (Map<String, Object>) headMap.get(key);
                            for (String subKey : subHead.keySet()) {
                                cell = row.createCell(j);
                                cell.setCellValue(map.get(subHead.get(subKey).toString()).toString());
                                cell.setCellStyle(listStyle);
                                j++;
                            }
                            j--;
                        }
                    }
                    j++;
                }
            }
            HSSFCellStyle endStyle=workbook.createCellStyle();
            endStyle.setWrapText(true);
            row = sheet.createRow(4+list.size());
            cell=row.createCell(0);
            region=new CellRangeAddress(4+list.size(), 9+list.size(), 0, 19);
            projectValue = "备注：1、驻点天数基数20天/月，基础分10分（最高分），19-15天得9分，14-10天得8分，9天以下每少一天就从8分里减一分，\n" +
                    "         直至归零；\n" +
                    "      2、督查离岗扣分，每发现一次扣10分，直至当月分值扣完；\n" +
                    "      3、外资项目得分，每一个签约外资项目加20分（两人介绍的平分分值）；\n" +
                    "      4、其他得分都按照绝对值计算，计算公式为：本人得分=本人绝对值/本组最高绝对值*本项得分；\n" +
                    "      请各组组长认真组织，严格把关，根据分数高低排出名次。并且每月3号前请将上月比拼表发送于杨延诚（18605722188/550220)";
            cell.setCellValue(projectValue);
            cell.setCellStyle(endStyle);
            sheet.addMergedRegion(region);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMail(new ByteArrayInputStream(bos.toByteArray()), "驻点招商员月度绩效比拼表", email);
    }

    @Override
    public void getImportInfo(Map<String, Object> condition, String email) {
        List<Map<String, Object>> list = exportDao.getImportList(condition);
        int talentTotalNum = 0;
        int talentShanghaiTotalNum = 0;
        int talentSuNanTotalNum = 0;
        int studentShanghaiTotalNum = 0;
        int studentSuNanTotalNum = 0;
        HSSFWorkbook workbook=new HSSFWorkbook();
        for (Map<String, Object> groupMap : list) {
            List<String> talentCityList = getProvinceCity(groupMap.get("tude").toString());
            if (talentCityList != null && talentCityList.size() > 0) {
                groupMap.put("talentNum", talentCityList.size());
                int talentShanghaiNum = 0;
                int talentSuNanNum = 0;
                for (String key : talentCityList) {
                    if (key.equals(shanghaiCode)){
                        talentShanghaiNum++;
                    }else if (sunanCode.contains(key)){
                        talentSuNanNum++;
                    }
                }
                groupMap.put("talentShanghaiNum", talentShanghaiNum);
                groupMap.put("talentSuNanNum", talentSuNanNum);
            }else {
                groupMap.put("talentNum", 0);
                groupMap.put("talentShanghaiNum", 0);
                groupMap.put("talentSuNanNum", 0);
            }
            condition.put("group", groupMap.get("group"));
            List<Map<String, Object>> studentList = exportDao.getStudentList(condition);
            if (studentList != null && studentList.size() > 0) {
                int studentShanghaiNum = 0;
                int studentSuNanNum = 0;
                for (Map<String, Object> map : studentList) {
                    List<String> studentCityList = getProvinceCity(map.get("longitude").toString()+","+map.get("latitude").toString());
                    String studentCity = studentCityList.get(0);
                    if (studentCity.equals(shanghaiCode)){
                        studentShanghaiNum = studentShanghaiNum + Integer.parseInt(map.get("students").toString());
                    }else if (sunanCode.contains(studentCity)){
                        studentSuNanNum = studentSuNanNum + Integer.parseInt(map.get("students").toString());
                    }
                }
                groupMap.put("studentShanghaiNum", studentShanghaiNum);
                groupMap.put("studentSuNanNum", studentSuNanNum);
            }else {
                groupMap.put("studentShanghaiNum", 0);
                groupMap.put("studentSuNanNum", 0);
            }
            talentTotalNum = talentTotalNum + Integer.parseInt(groupMap.get("talentNum").toString());
            talentShanghaiTotalNum = talentShanghaiTotalNum + Integer.parseInt(groupMap.get("talentShanghaiNum").toString());
            talentSuNanTotalNum = talentSuNanTotalNum + Integer.parseInt(groupMap.get("talentSuNanNum").toString());
            studentShanghaiTotalNum = studentShanghaiTotalNum + Integer.parseInt(groupMap.get("studentShanghaiNum").toString());
            studentSuNanTotalNum = studentSuNanTotalNum + Integer.parseInt(groupMap.get("studentSuNanNum").toString());
        }
        Map<String, Object> totalMap = exportDao.getTotalImportList(condition);
        totalMap.put("talentNum", talentTotalNum);
        totalMap.put("talentShanghaiNum", talentShanghaiTotalNum);
        totalMap.put("talentSuNanNum", talentSuNanTotalNum);
        totalMap.put("studentShanghaiNum", studentShanghaiTotalNum);
        totalMap.put("studentSuNanNum", studentSuNanTotalNum);
        list.add(totalMap);


        HSSFSheet sheet=workbook.createSheet();

        HSSFCellStyle style=workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //第一行


        HSSFRow row = sheet.createRow( 0);
        HSSFCell cell=row.createCell(0);
        CellRangeAddress region=new CellRangeAddress(0, 0, 0, 29);
        cell.setCellStyle(style);
        StringBuilder startDate = new StringBuilder(condition.get("startDate").toString());
        StringBuilder endDate = new StringBuilder(condition.get("endDate").toString());
        cell.setCellValue("驻沪苏招商引资引技引才进展情况汇总表  "
                +startDate.replace(4,5, "年").replace(7,8, "月").append("日")
                +"-"+endDate.replace(4,5, "年").replace(7,8, "月").append("日"));

        sheet.addMergedRegion(region);

        HSSFCellStyle listStyle=workbook.createCellStyle();
        listStyle.setAlignment(HorizontalAlignment.CENTER);
        listStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        listStyle.setBorderBottom(BorderStyle.THIN);
        listStyle.setBorderLeft(BorderStyle.THIN);
        listStyle.setBorderRight(BorderStyle.THIN);
        listStyle.setBorderTop(BorderStyle.THIN);
        net.sf.json.JSONObject schemaInfo  = JsonUtil.getJsonDataToList("static/json/export");
        Map<String, Object> headMap = (Map<String, Object>) schemaInfo.get("招商引资引技引才进展情况汇总表");
        row = sheet.createRow( 1);
        HSSFRow subRow = sheet.createRow(2);
        HSSFRow otherRow = sheet.createRow(3);
        int headNum = 0;
        for (String key : headMap.keySet()) {
            Object value = headMap.get(key);
            cell = row.createCell(headNum);
            int cellNum = 0;
            boolean flag = false;
            if (value instanceof String) {
                region=new CellRangeAddress(1, 2, headNum, headNum); //第一层
                cell.setCellValue(key);
                cell.setCellStyle(listStyle);
                sheet.addMergedRegion(region);
                headNum++;
            }else {
                cell.setCellValue(key);
                cell.setCellStyle(listStyle);
                Map<String, Object> subHead = (Map<String, Object>) value;
                for (String subKey : subHead.keySet()) {
                    HSSFCell subCell = subRow.createCell(headNum+cellNum);//第二层
                    subCell.setCellValue(subKey);
                    subCell.setCellStyle(listStyle);
                    HSSFCell otherCell = otherRow.createCell(headNum+cellNum);
                    otherCell.setCellStyle(listStyle);
                    if (subHead.get(subKey) instanceof Map) {
                        Map<String, Object> otherMap = (Map<String, Object>) subHead.get(subKey);
                        region=new CellRangeAddress(2, 2, headNum+cellNum, headNum+cellNum+otherMap.size()-1);
                        sheet.addMergedRegion(region);
                        for (String otherKey : otherMap.keySet()){
                            otherCell = otherRow.createCell(headNum+cellNum);
                            otherCell.setCellValue(otherKey);
                            otherCell.setCellStyle(listStyle);
                            cellNum++;
                        }
                        flag = true;
                    }else {
                        cellNum++;
                    }
                    if (!flag && !"洽谈数".equals(subKey)){
                        otherCell.setCellValue(subKey);
                    }
                }
                if (flag){
                    region=new CellRangeAddress(1, 1, headNum, headNum+cellNum-1);//第一层
                }else {
                    region=new CellRangeAddress(1, 2, headNum, headNum+cellNum-1);
                }
                sheet.addMergedRegion(region);
                headNum = headNum +cellNum;
            }
        }
        for (int i = 0; i < list.size();i++){
            row = sheet.createRow(4+i);
            Map<String, Object> groupMap = list.get(i);
            groupMap.put("visitInfo", groupMap.get("visitAllNum").toString()+"批"+groupMap.get("receptionistAllNum").toString()
                    +"，新增"+groupMap.get("visitNum")+"批"+groupMap.get("receptionistNum").toString()+"人次");
            groupMap.put("goodInfo", groupMap.get("wantAllNum").toString()+"，新增"+groupMap.get("wantNum"));
            groupMap.put("signNum", "-");
            groupMap.put("signShangHaiNum", "-");
            groupMap.put("signSuNanNum", "-");
            groupMap.put("signOtherNum", "-");
            groupMap.put("billionNum", "-");
            groupMap.put("billionShanghaiNum", "-");
            groupMap.put("billionSuNanNum", "-");
            groupMap.put("billionOtherNum", "-");
            groupMap.put("fiveBillionProjectNum", "-");
            groupMap.put("fiveBillionProjectShangHaiNum", "-");
            groupMap.put("fiveBillionProjectSuNanNum", "-");
            groupMap.put("fiveBillionProjectOtherNum", "-");
            groupMap.put("talkNum", "-");
            groupMap.put("signScientific", "-");
            groupMap.put("signScientificShanghai", "-");
            groupMap.put("signScientificSuNan", "-");
            groupMap.put("reportNum",groupMap.get("reportAllNum").toString()+"，新增"+groupMap.get("reportNum"));
            groupMap.put("employNum", "-");
            int j = 0;
            for (String key : headMap.keySet()){
                cell = row.createCell(j);
                if (key.equals("序号")){
                    cell.setCellValue(i + 1);
                    cell.setCellStyle(listStyle);
                }else {
                    if (headMap.get(key) instanceof String){
                        cell.setCellValue(groupMap.get(headMap.get(key).toString()).toString());
                        cell.setCellStyle(listStyle);
                    }else {
                        Map<String, Object> subHead = (Map<String, Object>) headMap.get(key);
                        for (String subKey : subHead.keySet()) {
                            if (subHead.get(subKey) instanceof String){
                                cell = row.createCell(j);
                                cell.setCellValue(groupMap.get(subHead.get(subKey).toString()).toString());
                                cell.setCellStyle(listStyle);
                                j++;
                            }else {
                                Map<String, Object> otherHead = (Map<String, Object>) subHead.get(subKey);
                                for (String otherKey : otherHead.keySet()){
                                    cell = row.createCell(j);
                                    cell.setCellValue(groupMap.get(otherHead.get(otherKey).toString()).toString());
                                    cell.setCellStyle(listStyle);
                                    j++;
                                }
                            }
                        }
                        j--;
                    }
                }
                j++;
            }
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMail(new ByteArrayInputStream(bos.toByteArray()), "驻沪苏招商引资引技引才进展情况汇总表", email);
    }

    @Override
    public void getVisitInfo(Map<String, Object> condition, String email) {
        HSSFWorkbook workbook=new HSSFWorkbook();

        for(String group : Arrays.asList((String[]) condition.get("groupList"))){
            List<Map<String, Object>> list = exportDao.getVisitInfo(condition);
            HSSFSheet sheet=workbook.createSheet(group);

            HSSFCellStyle style=workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            //第一行
            HSSFRow row = sheet.createRow( 0);
            HSSFCell cell=row.createCell(0);
            CellRangeAddress region=new CellRangeAddress(0, 0, 0, 8);
            cell.setCellStyle(style);
            cell.setCellValue("长三角片区拜访客商周报表");
            sheet.addMergedRegion(region);
            //第二行
            row = sheet.createRow( 1);
            cell=row.createCell(0);
            region=new CellRangeAddress(1, 1, 0, 8);
            String projectValue = "组别："+group;
            StringBuilder startDate = new StringBuilder(condition.get("startDate").toString());
            StringBuilder endDate = new StringBuilder(condition.get("endDate").toString());
            cell.setCellValue(projectValue+"                                      "
                        +startDate.replace(4,5, "年").replace(7,8, "月").append("日")
                        +"-"+endDate.replace(4,5, "年").replace(7,8, "月").append("日"));
            sheet.addMergedRegion(region);

            HSSFCellStyle listStyle=workbook.createCellStyle();
            listStyle.setAlignment(HorizontalAlignment.CENTER);
            listStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            listStyle.setBorderBottom(BorderStyle.THIN);
            listStyle.setBorderLeft(BorderStyle.THIN);
            listStyle.setBorderRight(BorderStyle.THIN);
            listStyle.setBorderTop(BorderStyle.THIN);

            net.sf.json.JSONObject schemaInfo  = JsonUtil.getJsonDataToList("static/json/export");
            Map<String, Object> headMap = (Map<String, Object>) schemaInfo.get("长三角片区拜访客商周报表");
            row = sheet.createRow( 2);
            int headNum = 0;
            for (String key : headMap.keySet()) {
                cell = row.createCell(headNum);
                cell.setCellValue(key);
                cell.setCellStyle(listStyle);
                headNum++;
            }

            for (int i = 0; i< list.size();i++){
                row = sheet.createRow( 3+i);
                Map<String, Object> map = list.get(i);
                int j =0;
                for (String key : headMap.keySet()) {
                    cell = row.createCell(j);
                    if (key.equals("序号")){
                        cell.setCellValue(i + 1);
                    }else {
                        if (map.get(headMap.get(key).toString()) != null){
                            cell.setCellValue(map.get(headMap.get(key).toString()).toString());
                        }else {
                            cell.setCellValue("");
                        }
                    }
                    cell.setCellStyle(listStyle);
                    j++;
                }
            }
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMail(new ByteArrayInputStream(bos.toByteArray()), "长三角片区拜访客商周报表", email);
    }

    @Override
    public List<Map<String, String>> getExportGroupList() {
        List<Map<String, String>> list = exportDao.getExportGroupList();
        return list;
    }

    @Override
    public PageBean<Map<String, Object>> getExportHistory(String userId, Integer from, Integer size) {
        from = from == null ? 1 : from;
        size = size == null ? 10 : size;
        PageHelper.startPage(from, size);
        List<Map<String, Object>> list = exportDao.getExportHistory(userId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        PageBean<Map<String, Object>> pageBean = new PageBean<>();
        BeanUtils.copyProperties(pageInfo, pageBean);
        return pageBean;
    }

    @Override
    public void addExportHistory(String staffId, int model) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("userId", staffId);
        String name = "";
        switch (model) {
            case 0:
                name = "招商项目信息采集表";
                break;
            case 1:
                name = "月度绩效比拼表";
                break;
            case 2:
                name = "引资引技引才进展情况汇总表";
                break;
            case 3:
                name = "拜访客商表";
                break;
        }
        condition.put("name", name);
        condition.put("date", new Date());
        exportDao.addExportHistory(condition);
    }

    private List<String> getProvinceCity(String tude){
        List<String> result = new ArrayList<>();
        String url = new StringBuilder().append("https://restapi.amap.com/v3/geocode/regeo")
                .append("?output=json&extensions=base&batch=true")
                .append("&location="+tude)
                .append("&key="+"4cb6b220b9be5b24aabbf51997885ece")
                .toString();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Map> resultMap= restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);
        List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.getBody().get("regeocodes");
        if (list != null && list.size()>0) {
            for (Map<String, Object> map : list){
                Map<String, Object> addressComponentMap = (Map<String, Object>) map.get("addressComponent");
                String city = addressComponentMap.get("citycode").toString();
                result.add(city);
            }
        }
        return result;
    }

    private static List<String> getWorkDates(int year, int month) {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        while (cal.get(Calendar.YEAR) == year &&
                cal.get(Calendar.MONTH) < month) {
            int day = cal.get(Calendar.DAY_OF_WEEK);

            if (!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)) {
                dates.add(bartDateFormat.format(cal.getTime().clone()));
            }
            cal.add(Calendar.DATE, 1);
        }
        return dates;
    }

    private double getMaxNum(List<Map<String, Object>> list, String colunm) {
        double max = 0;
        for (Map<String, Object> map : list) {
            if ( Double.valueOf(map.get(colunm).toString()) >max){
                max = Double.valueOf(map.get(colunm).toString());
            }
        }
        return max;
    }


    private static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        /*InputStream in = DockingServiceImpl.class.getClassLoader().getResourceAsStream(fileName);
        byte[] bytes = new byte[0];
        try {
            bytes = new byte[in.available()];
            in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String  input = new String(bytes);
        JSONObject jsonObject = JSON.parseObject(input);
        return jsonObject.toString();*/
    }


    public boolean sendMail(InputStream is, String fileName, String email){
        //log.info("[ 开始发送邮件... ]");
        Transport transport = null;
        try{
            System.setProperty("mail.mime.splitlongparameters","false");
            Properties props = new Properties();
            // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
            props.put("mail.smtp.host","smtp.qq.com");
            // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.user", "838728895@qq.com");
            props.put("mail.password", "rlicsueubvfvbdgi");
            Authenticator authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
            // 用（你可以在控制台（console)上看到发送邮件的过程）
            mailSession.setDebug(false);
            // 用session为参数定义消息对象
            MimeMessage message = new MimeMessage(mailSession);
            // 加载发件人地址
            InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
            message.setFrom(form);
            // 加载收件人地址
            InternetAddress to = new InternetAddress(email);
            message.setRecipient(MimeMessage.RecipientType.TO, to);
            // 加载标题
            message.setSubject(fileName);
            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();
            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText("请查看附件");
            multipart.addBodyPart(contentPart);
            // 添加附件
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new ByteArrayDataSource(is, "application/msexcel");
            // 添加附件的内容
            messageBodyPart.setDataHandler(new DataHandler(source));
            // 添加附件的标题
            // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            messageBodyPart.setFileName(MimeUtility.encodeText(fileName+".xls"));
            multipart.addBodyPart(messageBodyPart);
            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();
            // 发送邮件
            transport = mailSession.getTransport("smtp");
            // 把邮件发送出去
            Transport.send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            try {
                transport.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
