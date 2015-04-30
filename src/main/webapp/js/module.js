/**
 * Created by Administrator on 15-4-23.
 */
/**
 * Created by Administrator on 15-4-17.
 */

/* 打卡 */
PunchClock = function (parent) {
    this.parent = parent;
    this.init();
}

/*
this.data = data = {
    specifiedTime: ["08:30:00", "17:00:00"],
    realTime: ["08:30:00"],
    flag: false
}
*/

PunchClock.prototype = {
    init: function () {
        this.punchClock = $("<div />").addClass("punch-clock");
        this.createSpace("考勤","blue","white");
        this.punchClock.appendTo(this.parent);
    },
    /* 初始化 */
    /* 将按钮通过数据进行确切的位置放置 */
    createSpace: function (title, titleColor, bodyColor) {
        var portLet = new PortLet(this.punchClock);
        portLet.setTitle(title).setColor(titleColor, bodyColor);
        var div = $("<div />").addClass("punch-clock-body");
        var childDiv = $("<div />").addClass("punch-clock-body-child").appendTo(div);
        new Calendar(childDiv);
        portLet.setBody(div);
        return portLet;
    }
}