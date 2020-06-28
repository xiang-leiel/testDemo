package com.quantchi.tianji.service.search.model.vo.project;

import com.quantchi.tianji.service.search.model.InvestParams;
import com.quantchi.tianji.service.search.model.TalentParams;
import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @author leiel
 * @Date 2020/2/4 8:35 PM
 */
@Data
public class ProjectReportVO {

    /**
     * 关联项目id
     */
    private String xmId;

    /**
     * 项目状态 1001为项目终止
     */
    private Integer status;

    /**
     * 产业类型
     */
    private Integer industryType;

    /**
     * 项目标签
     */
    private List<Integer> projectLabels;

    /**
     * 高层次人才
     */
    private List<Integer> talentsLabels;

    /**
     * 人才引进项目
     */
    private List<TalentParams> talentImportLabels;

    /**
     * 行业领域
     */
    private List<Integer> fields;

    /**
     *项目名称
     */
    private String projectName;

    /**
     * 项目内容
     */
    private String projectInfo;

    /**
     * 需用地
     */
    private BigDecimal needLand;

    /**
     * 需用地单位  1：亩  2：平方米
     */
    private Integer needLandUnit;

    /**
     * 需用地新
     */
    private String needLandNew;

    /**
     * 固定资产投资
     */
    private BigDecimal assetInvest;

    /**
     * 货币单位
     */
    private Integer currencyUnit;

    /**
     * 总投资货币单位
     */
    private Integer currencyUnitTotal;

    /**
     * 货币单位
     */
    private String assetInvestNew;

    /**
     * 初步研判建议待落地平台和预评价建议待落地平台
     */
    private Integer suggestLand;

    /**
     * 初步研判建议待落地平台和预评价建议待落地平台
     */
    private String suggestLandNew;

    /**
     * 审核人
     */
    private Integer userId;

    /**
     * 用户用词id
     */
    private Integer yhycDm;

    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 审核操作 1通过  2暂缓  3终止
     */
    private Integer auditStatus;

    /**
     * 当前流程节点
     */
    private List<String> workFlowId;

    /**
     * 审核时间
     */
    private String auditTime;

    /**
     * 项目所在地区
     */
    private String region;

    /**
     * 具体投资额
     */
    private Integer investmentUnit;

    /**
     * 招商员
     */
    private String staffName;

    /**
     * 招商员所属组
     */
    private String group;

    /**
     * 招商员所属部门
     */
    private String department;

    /**
     * 总投资
     */
    private BigDecimal investTotal;

    /**
     * 总投资new
     */
    private String investTotalNew;

    /**
     * 北京 上海 深圳标记
     */
    private Integer regionFlag;

    /**
     * 是否显示终止标记
     */
    private Integer overFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 项目是否已隐藏
     */
    private Integer hide;

    /**
     * 需用地类型  0:待定；1:存量；2:新征
     */
    private Integer needLandType;

    /**
     * 投资方名称
     */
    private String investName;

    /**
     * 办公面积
     */
    private BigDecimal offerArea;

    /**
     * 项目其他需求
     */
    private String otherRequire;

    /**
     * 意向迁出时间
     */
    private String moveTime;

    /**
     * 项目首报人
     */
    private Integer masterUserId;

    /**
     * 项目首报人
     */
    private String masterName;

    /**
     * 投资方相关信息
     */
    private List<InvestParams> investParamsList;

}
