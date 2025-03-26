package com.example.ServerUniApp.common;

import lombok.Data;

@Data
public class JsonResult<T> {
    /**
     * 后端统一定义json的返回格式
     * data: 返回数据
     * code: 0成功 1失败 234等为其他不同异常
     * msg: 返回信息
     * 使用工厂模式创建success 和 error两个创建该类的方法
     */
    private Integer code;
    private String msg;
    private T data;

    /**
     * Success code默认为0
     * 分为：
     *  无参数
     *  不传数据 自定义msg
     *  传数据 自定义msg
     */
    public static <E> JsonResult<E> success () {
        JsonResult<E> res = new JsonResult<>();
        res.setCode(0);
        res.setMsg("操作成功");
        return res;
    }
    public static <E> JsonResult<E> success (String msg) {
        JsonResult<E> res = new JsonResult<>();
        res.setCode(0);
        res.setMsg(msg);
        return res;
    }
    public static <E> JsonResult<E> success (E object, String msg) {
        JsonResult<E> res = new JsonResult<>();
        res.setCode(0);
        res.setMsg(msg);
        res.setData(object);
        return res;
    }

    /**
     * Error code默认为1
     * (异常没必要传数据)
     * 分为：
     * 无参数
     *  code: 1 自定义msg
     *  自定义异常code和msg
     */
    public static <E> JsonResult<E> error() {
        JsonResult<E> res = new JsonResult<>();
        res.setCode(1);
        res.setMsg("操作失败");
        return res;
    }
    public static <E> JsonResult<E> error(String msg) {
        JsonResult<E> res = new JsonResult<>();
        res.setCode(1);
        res.setMsg(msg);
        return res;
    }
    public static <E> JsonResult<E> error(Integer code, String msg) {
        JsonResult<E> res = new JsonResult<>();
        res.setCode(code);
        res.setMsg(msg);
        return res;
    }
}
