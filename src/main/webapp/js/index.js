/**
 * Created by Administrator on 15-4-14.
 */

/* 各种初始化 */
$(function () {
    /* 通过ajax 获取 直观数据 */
    userObj = {
        /* 用户信息 */
        user : {
            specifiedTime : ["08:30:00","17:00:00"],
            realTime : ["08:30:00","17:00:00"],
            flag : true
        },
        /* 计时器的必要参数 对应计时器标签 */
        timer: {h: 17, m: 00, s: 00},
        /* 菜单的必要参数 对应菜单标签 */
        navbar: {
            root: [
                {code: "rcgl", title: "日常管理", icon: "product"},
                {code: "gsgl", title: "公司管理", icon: "policy"},
                {code: "xmgl", title: "项目管理", icon: "claim"},
                {code: "rygl", title: "人员管理", icon: "users"},
                {code: "bxgl", title: "报销管理", icon: "hospital"}
            ],
            child: {
                rcgl: [
                    {title: "打卡", module: "PunchClock"},
                    {title: "请假", module: "qj"},
                    {title: "会议", module: "hy"}
                ],
                gsgl: [
                    {title: "部门", module: "bm"},
                ]
            }
        },
        defaultModule : "PunchClock"
    }
    /* 放置各种 全局物品 */

    /* conform 窗口的布置 */
    conform = new Conform($("body"));

    /* 计时器 */
    /* 通过获取到的时间 进行timer初始化 */
    timer = new Timer();
    timer.setTime(userObj.timer.h, userObj.timer.m, userObj.timer.s);
    timer.start();

    /* 菜单 */
    var navBar = new NavBar($("#content-nav"));
    navBar.setData(userObj.navbar);
    navBar.setMenuControl($("#header-nav"));

    /* 消息弹出框 */
    var message = new Message($("body"));
    //message.setAction("1").setDelay(60000);
    //message.start();

    /* 默认模块 */
    new window[userObj.defaultModule]($("#content-container"));
})