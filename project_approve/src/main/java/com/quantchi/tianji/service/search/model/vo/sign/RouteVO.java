package com.quantchi.tianji.service.search.model.vo.sign;
import com.quantchi.tianji.service.search.model.SignRecord;
import lombok.Data;

/**
 * @Description 
 * @author leiel
 * @Date 2019/12/11 4:00 PM
 */
@Data
public class RouteVO {

    /** 行程开始点 */
    private SignRecord startPoint;

    /** 行程结束点 */
    private SignRecord endPoint;

}
