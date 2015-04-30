/* jquery 类型的对象 */
Calendar = function (parentObj) {
    /* 验证是否为jquery 对象 */
    if (!parentObj instanceof  jQuery) {
        console.log("calendar's parentObj is not jQuery");
        return;
    }
    this.parentObj = parentObj;

    /* 当日的数据 放置处 */
    this.nowDay = null;

    this.init();
}

Calendar.prototype = {
    init: function () {
        /* 创建一个日历 */
        this.createCalendar();
        this.initData();
    },
    /* 通过每日数据 对 整个 Calendar 进行上下班 加班 颜色的变化 */
    initData: function (dataObj) {
        this.dataObj = dataObj = {
            specifiedTime: ["08:30", "17:00"],
            days: [{
                realTime: ["08:30", "17:00"], flag: false
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: true, dayWhat: "字符串"
            }, {

            }, {

            }, {
                realTime: ["08:30", "17:00"], flag: true
            }, {
                realTime: ["08:30", "17:00"], flag: false
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: true, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {

            }, {

            }, {
                realTime: ["08:30", "17:00"], flag: false
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: true, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {

            }, {

            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: true, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: true
            }, {
                realTime: ["08:30", "17:00"], flag: false
            }, {

            }]
        }
        var days = dataObj.days;
        for (var i = 0; i < days.length; i++) {
            var dayData = days[i];
            var dayNum = i + 1;
            this.setDay(dayData, dayNum);
        }
    },
    /* 创建日历 只有默认为当年当月*/
    createCalendar: function () {
        this.calendar = calendar = $("<div class='calendar'/>");

        /* 添加上部 操作按钮部分 */
        this.createDateControlHeader();

        /* 添加当天的标记 */
        this.createNow();

        var table = $("<table  class='bgcolor-white'/>").appendTo(calendar);

        /* 添加 日历 的第一行 title */
        var thead = this.createThead().appendTo(table);

        /* 创建 日历 的body 部分 */
        this.tbody = this.createTbody().appendTo(table);

        /*将日历 放置进 父容器 */
        calendar.appendTo(this.parentObj);

        /* 产生 整个 日历 的真实数据部分 */
        /* 默认为 今日 */
        var now = new Date();
        var year = now.getFullYear();
        var month = now.getMonth() + 1;
        this.reFlash(year, month);

        this.createDateControlFooter();
    },
    /* 头部分 控制按钮 以及信息 */
    createDateControlHeader: function () {
        var that = this;
        var dateControl = $("<div class='date-control-header'>").appendTo(this.calendar);
        this.createAdd(dateControl);
        var control = $("<div />").addClass("control-button").appendTo(dateControl);

        /* 签到按钮 */
        $("<button />").addClass("btn btn-left-radius bgcolor-blue").text("签到").appendTo(control).click(function () {
            /* 将 当前时间包裹后 传输至后台 以及 userid */
            var t = timer.getTimer();
            var str = util.addZero(t.h,2) + ":" + util.addZero(t.m,2);
            if (!that.nowDay){
                var obj = {};
                obj.realTime = new Array();
                obj.realTime[0] = str;
                that.nowDay = obj;
                that.setDay(that.nowDay);
                that.calendar.find("#calendar-morning").addClass("bdcolor-bottom-blue").text("上班:" + str);
            }
        })
        /* 申请按钮 */
        var that = this;
        $("<button />")
            .addClass("btn bgcolor-black")
            .css({"border-radius": "0px"})
            .text("申请")
            .appendTo(control)
            .click(function () {
                var d = that.createSubmit("申请加班原因：【请如实填写】",function (text) {
                    /* text 为提交的信息 */
                    console.log(text);
                    that.nowDayFlag = true;
                });
                conform.setBody(d);
                conform.show("moveFromTopFade");
            });
        /* 签退按钮 */
        $("<button />").addClass("btn btn-right-radius bgcolor-orange").text("签退").appendTo(control).click(function(){
            var t = timer.getTimer();
            var str = util.addZero(t.h,2) + ":" + util.addZero(t.m,2);
            if (that.nowDay){
                if (that.nowDay.realTime.length == 2){
                    return;
                }
                that.nowDay.realTime[1] = str;
                that.nowDay.flag = that.nowDayFlag?true:false;
                that.setDay(that.nowDay);
                /* 弹出一个成功签退的对话框 */
                if (that.nowDay.flag){
                    that.calendar.find("#calendar-night").addClass("bdcolor-bottom-black").text("加班:" + str);
                }else{
                    that.calendar.find("#calendar-afternoon").addClass("bdcolor-bottom-orange").text("下班:" + str);
                }
            }
        });

        /* 规定时间  左侧 以 span 显示*/
        var control = $("<div />").addClass("control-list").appendTo(dateControl);

        var ul = $("<ul />").appendTo(control);
        var arrId = new Array("calendar-now", "calendar-morning", "calendar-afternoon", "calendar-night");
        for (var i = 0; i < 4; i++) {
            var li = $("<li />");
            if (i == 0) {
                var span = $("<span />").addClass("bdcolor-bottom-black").appendTo(li);
                $("<span />").addClass("title").text("现在:").appendTo(span);
                timer.dom.appendTo(span);
            } else {
                $("<span />").addClass("value").attr("id", arrId[i]).appendTo(li);
            }
            li.appendTo(ul);
        }
    },
    /* 脚部分 信息显示 */
    createDateControlFooter: function () {
        var dateControl = $("<div class='date-control-footer'>").appendTo(this.calendar);
        /* 规定时间  左侧 以 span 显示*/
        var control = $("<div />").addClass("control-list").appendTo(dateControl);

        var ul = $("<ul />").appendTo(control);
        /* 信息统计 */
        var li = $("<li />");
        $("<span />").addClass("value").text("正常上班：19天 剩余天数：4天").appendTo(li);
        li.appendTo(ul);
    },
    /* 添加 增加备注的按钮 */
    createAdd: function (parent) {
        var add = $("<span class='date-add icon-add-circle'>");
        var that = this;
        add.click(function () {
                var d = that.createSubmit("日程备注：",function (text) {
                    /* text 为提交的信息 */
                    console.log(text);
                });
                conform.setBody(d);
                conform.show("moveFromTopFade");
            });

        add.appendTo(parent);
    },
    createNow: function () {
        this.dateNow = $("<span class='date-now bgcolor-red'>今</span>");
    },
    setDay: function (dayData, dayNum) {
        /* 判定 dayNum 是否为空 为空表示当天 */
        if (!dayNum){
            dayNum = this.dateNow.data("day");
        }
        /* 设置 某一天 的数据 */
        if (!dayData.realTime){
            return;
        }
        var day = this.calendar.find("[day=" + dayNum + "]");
        /* 对数据进行赋值 */
        var lis = day.find("ul li");
        /* 判定是否为加班 */

        $(lis[0]).addClass("bgcolor-blue").find("span").text("上:" + dayData.realTime[0]);  // 上班
        if (dayData.flag) {
            //lis[2]  //加班
            $(lis[2]).addClass("bgcolor-black").find("span").text("加:" + dayData.realTime[1]);  // 加班
        } else {
            if (dayData.flag != null){
                //lis[1] // 下班
                $(lis[1]).addClass("bgcolor-orange").find("span").text("下:" + dayData.realTime[1]);  // 下班
            }
        }

        /* 设置日程 */
        if (dayData.dayWhat) {
            day.find(".date-what").addClass("show");
        }
    },
    /* 创建日历的title 部分 */
    createThead: function () {
        var thead = $("<thead />");
        var tr = this.createTr().attr("class", "week-title").appendTo(thead);
        var weekArray = new Array("周日", "周一", "周二", "周三", "周四", "周五", "周六");
        for (var i in weekArray) {
            var td = this.createTd(weekArray[i]);
            td.appendTo(tr);
        }
        return thead;
    },
    /* 创建日历的 身体 部分 */
    createTbody: function () {
        return $("<tbody />");
    },
    /* 对外接口 */
    reFlash: function (year, month) {
        this.tbody.empty();
        this.day = 1;
        this.daynumber = daynumber = this.getMDay(year, month); //当月天数
        this.firstnumber = firstnumber = this.weekNumber(year, month, 1); //当月第一天星期
        this.lastnumber = lastnumber = this.weekNumber(year, month, daynumber); //当月最后一天星期
        this.weeknumber = weeknumber = (daynumber - (7 - firstnumber) - (lastnumber + 1)) / 7; //除去第一个星期和最后一个星期的周数
        this.createFirstWeek(this.tbody, year, month);
        this.createOtherWeek(this.tbody, year, month);
        this.createLastWeek(this.tbody, year, month);
        this.setHoverEvent();
        this.setNow();
    },
    setHoverEvent: function () {
        /* 增加click 之后的样式CSS */
        this.calendar.find(".date").click(function () {
            $(".date").removeClass("date-hover");
            $(this).addClass("date-hover");
        });
    },
    setNow: function () {
        var month = this.calendar.find(".date");
        var dateNow = this.calendar.find(".date-now");
        var now = new Date();
        var nowDay = now.getDate();
        for (var i = 0; i < month.length; i++) {
            var obj = $(month[i]);
            if (obj.attr("day") == nowDay) {
                this.dateNow.data("day",nowDay);
                this.dateNow.appendTo(obj);
                return;
            }
        }
    },
    /* 产生每天的块级元素 */
    createDay: function (day, dataObj) {
        var date = $("<div class='date'/>").attr("day", day);
        var dateNum = $("<div class='date-num'>").appendTo(date);
        var num = $("<span />").text(day).appendTo(dateNum);
        var dateWhat = $("<span class='date-what'/>").appendTo(date);
        var dateTime = $("<div class='date-time'/>").appendTo(date);
        var dateTimeUl = $("<ul />").appendTo(dateTime);
        var createLi = function (parent) {
            var li = $("<li><span></span></li>");
            li.appendTo(parent);
            return li;
        }

        var morning = createLi(dateTimeUl);
        var night = createLi(dateTimeUl);
        var more = createLi(dateTimeUl);

        return date;
    },
    createTr: function () {
        return $("<tr />");
    },
    createTd: function (obj) {
        var td = $("<td />");
        if (typeof(obj) == "string") {
            td.text(obj);
        }
        if (obj instanceof jQuery) {
            obj.appendTo(td);
        }
        return td;
    },
    createFirstWeek: function (parent, year, month) {
        var tr = this.createTr();
        var i = 0;
        for (i = 0; i < this.firstnumber; i++)//第一个星期
        {
            this.createTd("").appendTo(tr);
        }

        for (i = this.firstnumber; i < 7; i++) {
            var id = year + "-" + month + "-" + this.day;
            this.createTd(this.createDay(this.day)).attr("id", id).appendTo(tr);
            this.day++;
        }
        tr.appendTo(parent);
    },
    createOtherWeek: function (parent, year, month) {
        this.number = number = 0;//星期数，末尾添加空行用，统一样式。
        for (i = 0; i < this.weeknumber; i++)//其他星期
        {
            var tr = this.createTr();
            for (var k = this.daynumber - (7 - this.firstnumber) - (this.weeknumber - 1) * 7; k < this.daynumber - (7 - this.firstnumber) - (this.weeknumber - 1) * 7 + 7; k++) {
                var id = year + "-" + month + "-" + this.day;
                this.createTd(this.createDay(this.day)).attr("id", id).appendTo(tr);
                this.day++;
            }
            this.number++;
            tr.appendTo(parent);
        }
    },
    createLastWeek: function (parent, year, month) {
        var tr = this.createTr();
        var i = 0;
        for (i = 0; i < lastnumber + 1; i++)//最后一个星期
        {
            var id = year + "-" + month + "-" + this.day;
            this.createTd(this.createDay(this.day)).attr("id", id).appendTo(tr);
            this.day++;
        }
        for (i = lastnumber + 1; i < 7; i++) {
            this.createTd().appendTo(tr);
        }
        tr.appendTo(parent);
        /*
         if (this.number == 3) {
         var tr = this.createTr();
         for (var i = 0 ;i < 7 ;i ++){
         this.createTd("").appendTo(tr)
         }
         tr.appendTo(parent);
         }
         */
    },
    /* 获取一个月拥有几天 */
    getMDay: function (year, month) {
        var mday = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
        if ((year % 40 == 0 && year % 100 != 0) || year % 400 == 0) { //判断是否是闰月
            mday[1] = 29;
        }
        return mday[month - 1];
    },
    // 获取一个月周数
    weekNumber: function (year, month, day) {
        var wk;
        if (month <= 12 && month >= 1) {
            for (var i = 1; i < month; ++i) {
                day += this.getMDay(year, i);
            }
        }
        wk = (year - 1 + (year - 1) / 4 - (year - 1) / 100 + (year - 1) / 400 + day) % 7;
        return parseInt(wk);
    },
    /* 功能块 */
    createSubmit: function (title,callback) {
        var $div = $("<div />");
        $("<label />").addClass("bgcolor-white").text(title).appendTo($div);
        var text = $("<textarea />").addClass("text").attr("style", "width:100%").appendTo($div);
        var that = this;
        $("<button />").addClass("btn bgcolor-blue").text("提交").attr("style", "width:100%").appendTo($div).click(function () {
            conform.close("moveToTopFade");
            callback(text.val());
        });
        return $div;
    }
}