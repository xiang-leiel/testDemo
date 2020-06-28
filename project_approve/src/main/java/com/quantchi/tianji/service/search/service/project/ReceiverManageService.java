package com.quantchi.tianji.service.search.service.project;

import com.quantchi.core.message.ResultInfo;
import com.quantchi.tianji.service.search.model.ReceiverParam;
import com.quantchi.tianji.service.search.model.vo.EvaluateParam;

/**
 * @author leiel
 * @Description
 * @Date 2019/12/18 4:46 PM
 */

public interface ReceiverManageService {

    /**
     * 查询当前行程对应的接待人
     * @param
     * @return
     */
    ResultInfo queryReceiverByVisit(Long visitId);

    /**
     * 查询接待人
     * @param
     * @return
     */
    ResultInfo queryReceiver(Long receiverId);

    /**
     * 编辑接待人（保存或更新）
     * @param
     * @return
     */
    ResultInfo editReceiver(ReceiverParam receiverParam);

    /**
     * 评价接待人（保存）
     * @param
     * @return
     */
    ResultInfo evaluateReceiver(EvaluateParam evaluateParam);

    /**
     * 接待人评价信息查看
     * @param
     * @return
     */
    ResultInfo queryReceiverEvaluate(Long receiverId);

}
