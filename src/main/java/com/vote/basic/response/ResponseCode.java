package com.vote.basic.response;

/**
 * 异常代码
 * http 协议规范为主
 *
 * @author
 */
public enum ResponseCode {

    /**
     * 请求成功
     */
    CODE_200(200, "请求成功"),
    /**
     * 系统未捕获异常
     */
    CODE_400(400, "请求错误,请稍后重试"),
    /**
     * 并发异常 并发时可能出现此类异常，其下有若干子类异常来标识乐观锁和获取锁失败这两类异常信息
     */
    CODE_900(900, "请求过于频繁,请稍后再试"),
    CODE_901(901, "方法请求错误"),
    /**
     * 参数错误
     */
    CODE_1000(1000, "参数错误：{0}：{1}"),
    CODE_1001(1001, "参数错误"),
    CODE_1002(1002, "非法请求"),
    CODE_1003(1003, "请求失败，请重新操作"),
    CODE_1004(1004, "找不到对应的url请仔细核对,或者参数错误"),
    CODE_1005(1005, "操作失败，请稍后重试"),
    CODE_1006(1006, "方法执行异常"),
    CODE_1007(1007, "已存在同名称的选举会议"),
    CODE_1008(1008, "该选举不存在"),
    CODE_1009(1009, "该选举已是当前状态，无需更新"),
    CODE_1010(1010, "该选举候选人已存在"),
    CODE_1011(1011, "该选举已结束"),
    CODE_1012(1012, "该选举候选人数量不足，请添加足够的候选人"),
    CODE_1013(1013, "该候选人不存在"),
    CODE_1014(1014, "该选举还未结束"),
    CODE_1015(1015, "身份证已登记"),
    CODE_1016(1016, "用户不存在"),
    CODE_1017(1017, "您已投票");


    private int code;
    private String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public static ResponseCode changeMethod(int code) {
        return Enum.valueOf(ResponseCode.class, "CODE_" + code);
    }
}
