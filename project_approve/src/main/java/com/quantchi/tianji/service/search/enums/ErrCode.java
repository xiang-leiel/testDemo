package com.quantchi.tianji.service.search.enums;

/**
 * @author leiel
 * @Description
 * @Date 2019/12/10 10:27 AM
 */

public enum ErrCode {

    SUCCESS(0,"成功"),
    DATA_QUERY_FAIL(1001,"您的信息尚未完善，请先完善个人信息"),
    DATA_INSERT_FAIL(1002,"数据库插入失败"),
    NOT_EXIST(1003,"{}不存在，请重试"),
    NOT_NULL(1004,"{}不能为空，请重试"),
    EXISTED(1005,"数据记录已存在，不支持当前操作"),
    DATA_UPLOADING(1006,"由于您刚刚加入，系统正在拼命同步您的用户数据，请五分钟后再试"),

    WRONG_TOKEN_ERROR(1008,"用户验证失败,请重新登录"),

    USER_DATA_NOEXIST(2001,"用户数据不存在"),
    PARTNER_ERROR(2002,"存在邀请人不和你同组, 请重新选择"),
    NOT_AUTHORITY(2003,"无权限操作, 请重新选择"),
    USER_OVER(2004,"用户个数已超限"),

    LEADER_EXIST(2100,"您所设置的组内已存在组长，请勿重复操作"),

    SIGNED(3001,"当前行程今日已签到"),
    NOT_SIGN(3002,"当前无签到行程"),

    DATA_GET_FAIL(4001,"数据获取失败"),

    NODE_NOT_SET(5001,"用户节点数据未设置"),

    PASSWORD_FAIL(6001,"原密码错误，请重新输入"),
    PASSWORD_SAME(6002,"新密码与旧密码一致，请重新设置新密码"),

    NAME_LONG(6100,"项目名称字数超出，请重新输入"),
    INFO_LONG(6101,"项目内容字数超出，请重新输入"),
    OTHER_LONG(6102,"其他需求字数超出，请重新输入"),
    REMARK_LONG(6103,"备注字数超出，请重新输入"),
    ASSET_INVEST_SMALL(6104,"固定投资资产不可以小于1万，请重新输入"),
    TOTAL_INVEST_SMALL(6105,"总投资资产不可以小于1万，请重新输入"),

    PROJECT_DATA_NOTEXIST(3100, "该项目不存在，请重试")

    ;

    /** 错误码 */
    private Integer code;

    /**  */
    private String desc;

    ErrCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getDesc(Integer code) {
        if (null == code) {
            return null;
        }
        for (ErrCode e : ErrCode.values()) {
            if (e.getCode().equals(code)) {
                return e.getDesc();
            }
        }
        return null;
    }

}
