/**
 * Created by Administrator on 15-4-17.
 */

/* 打卡 */
PunchClock = function (parent) {
    this.parent = parent;
    this.init();
}

PunchClock.prototype = {
    init: function () {
        this.punchClock = $("<div />").addClass("punch-clock");
        this.arNum = 0;
        this.morning = morning = this.createSpace("morning", "上班", "blue", "white");
        this.afternoon = afternoon = this.createSpace("afternoon", "下班", "orange", "white");
        this.night = night = this.createSpace("night", "加班", "red", "white", true);
        var that = this;
        this.enter = $("<button />").addClass("btn").text("打卡").click(function () {
            var ar = new Array("morning", "afternoon", "night");
            /* 将数据存储 并 设置 实际打卡时间 在按照 flag 进行 按钮重置 */
            var t = timer.getTimer().str;
            that[ar[that.arNum] + "-real-time"].text("实际时间：" + t);
            that.changeEnterLocal(that.arNum);
        });
        this.punchClock.appendTo(this.parent);
        this.initEnterLocal();
    },
    /* 初始化 */
    /* 将按钮通过数据进行确切的位置放置 */
    initEnterLocal: function () {
        this.data = data = {
            specifiedTime: ["08:30:00", "17:00:00"],
            realTime: ["08:30:00"],
            flag: false
        }
        /* 规定时间 */
        var ar = new Array("morning", "afternoon");
        /* 规定时间的赋值是固定的 */
        for (var i = 0; i < data.specifiedTime.length; i++) {
            var stime = data.specifiedTime[i];
            var key = ar[i];
            this[key + "-specified-time"].text("规定时间：" + stime);
        }
        /* 首次打卡 */
        if (!data.realTime) {
            var titleColor = this.morning.titleColor;
            this.arNum = 0;
            this.enter.attr("class", "btn bgcolor-" + titleColor).appendTo(this.morning.body.find(".punch-clock-body"));
            return;
        }

        var stime = data.realTime[0];
        this["morning-real-time"].text("实际时间：" + stime);

        /* 判定是加班的情况 */
        if (data.flag) {
            if (!data.realTime[1]) {
                var titleColor = this.night.titleColor;
                this.arNum = 2;
                this.enter.attr("class", "btn bgcolor-" + titleColor).appendTo(this.night.body.find(".punch-clock-body"));
                this.apply.remove();
            }
        } else {
            if (!data.realTime[1]) {
                var titleColor = this.afternoon.titleColor;
                this.arNum = 1;
                this.enter.attr("class", "btn bgcolor-" + titleColor).appendTo(this.afternoon.body.find(".punch-clock-body"));
            }
        }
    },
    changeEnterLocal: function (arNum) {
        var ar = new Array("morning", "afternoon", "night");
        /* 判定 arNum 的位置 */
        if (this.data.flag){
            if(arNum == 2){
                this.enter.remove();
                this.apply.remove();
                return;
            }
        }else{
            if(arNum == 1){
                this.enter.remove();
                this.apply.remove();
                return;
            }
        }
        this.arNum = arNum + 1;
        if (this.data.flag){
            this.arNum = this.arNum + 1;
            if(this.arNum > 2){
                this.arNum = 2;
            }
        }
        var key = ar[this.arNum];
        var obj = this[key];
        var titleColor = obj.titleColor;
        this.enter.attr("class", "btn bgcolor-" + titleColor).appendTo(obj.body.find(".punch-clock-body"));
    },
    createSpace: function (key, title, titleColor, bodyColor, flag) {
        var portLet = new PortLet(this.punchClock);
        portLet.setTitle(title).setColor(titleColor, bodyColor);
        var div = $("<div />").addClass("punch-clock-body");
        var childDiv = $("<div />").addClass("punch-clock-body-child").appendTo(div);
        /* 加班 是不需要规定时间的 */
        if (!flag) {
            this[key + "-specified-time"] = $("<span />").addClass("specified-time").appendTo(childDiv);
        }
        this[key + "-real-time"] = $("<span />").addClass("real-time").appendTo(childDiv);
        /* 加班是需要申请按钮以及原因的 */
        if (flag){
            this.apply = btn = $("<button />").addClass("btn bgcolor-" + titleColor).text("申请").appendTo(childDiv);
            var that = this;
            btn.click(function(){
                var d = that.createApply();
                conform.setBody(d);
                conform.show("moveFromTopFade",function(){
                    d.find("input:first").focus();
                });
            });
        }
        portLet.setBody(div);
        portLet.titleColor = titleColor;
        return portLet;
    }
}