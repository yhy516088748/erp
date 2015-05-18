/* jquery 类型的对象 */
Calendar = function (parentObj) {
    /* 验证是否为jquery 对象 */
    if (!parentObj instanceof  jQuery) {
        console.log("calendar's parentObj is not jQuery");
        return;
    }

    this.parentObj = parentObj;

    /* 计算工作日 */
    this.workDays = new Array();

    /* 计算休息日 */
    this.unWorkDays = new Array();

    /* 当日的数据 放置处 */
    this.nowDay = null;

    /* msg 延迟时间 */
    this.msgDelay = 1800;

    /* 单击的是某天 */
    this.clickDay = null;

    this.init();
}

Calendar.prototype = {
    init: function () {
        /* 创建一个日历 */
        this.createCalendar();
    },
    convertData : function(dataObj){
        var newData = {};
        newData.specifiedTime = userObj.specifiedTime;
        var days = new Array();
        for (var i = 0;i < dataObj.length ; i++){
            var o = dataObj[i];
            var num = parseInt(o.signinDate.substr(8, 2)) - 1;
            if (!days[num]){
                days[num] = {};
                days[num].realTime = new Array();
            }
            if (days[num].flag){

            }else{
                days[num].flag = false;
            }
            if (o.signinType == "加班申请"){
                days[num].flag = true;
            }
            if (o.signinTime && o.signinType != "加班申请"){
                days[num].realTime.push(o.signinTime);
            }

        }
        for (var j=0;j<days.length;j++){
            if (!days[j]){
                days[j] = {};
            }
        }
        newData.days = days;
        return newData;
    },
    /* 通过每日数据 对 整个 Calendar 进行上下班 加班 颜色的变化 */
    setData: function (dataObj) {
        this.dataObj = dataObj = this.convertData(dataObj);

        /* 所有日期数据 */
        this.days = days = dataObj.days;

        for (var i = 0; i < days.length; i++) {
            var dayData = days[i];
            var dayNum = i + 1;
            this.setDay(dayData, dayNum);
        }

        this.setNow();
        // 对当日数据 进行初始化

        if (this.nowDay){
            if (this.nowDay.realTime[0]){
                this.calendar.find("#calendar-morning").addClass("bdcolor-bottom-blue").text("M: " + this.nowDay.realTime[0]);
            }
            if (this.nowDay.flag){
                if (this.nowDay.realTime[1]) {
                    this.calendar.find("#calendar-afternoon").addClass("bdcolor-bottom-black").text("N: " + this.nowDay.realTime[1]);
                }
            }else{
                if (this.nowDay.realTime[1]){
                    this.calendar.find("#calendar-afternoon").addClass("bdcolor-bottom-green").text("A: " + this.nowDay.realTime[1]);
                }
            }
        }

        /* 对数据进行统计 去除周末 */
        //var str = "";
        ///* 计算周末加班天数 */
        //var unWorkNum = 0;
        //var newDays = days;
        //for (var i = 0 ; i < this.unWorkDays.length ; i++){
        //    if (newDays[this.unWorkDays[i] - 1].realTime){
        //        unWorkNum ++ ;
        //        newDays[this.unWorkDays[i] - 1] = {};
        //    }
        //}
        //
        //if( unWorkNum > 0){
        //    str = str + "周末上班:" + unWorkNum + "天 ";
        //}
        //
        ///* 计算正常工作天数 */
        //
        //var workNum = 0;
        //for (var i = 0 ; i < this.workDays.length ; i++){
        //    if (newDays[this.workDays[i] - 1]){
        //        if (newDays[this.workDays[i] - 1].realTime){
        //            workNum ++ ;
        //            newDays[this.workDays[i] - 1] = {};
        //        }
        //    }
        //}
        //
        //if (workNum > 0){
        //    if( workNum > 0){
        //        str = str + "正常上班:" + workNum + "天 ";
        //    }
        //}
        ///* 计算剩余工作天数 */
        //
        //str = str + "剩余天数:" + (this.workDays.length - workNum) + "天 ";
        //this.footInfo.text(str);
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
            var str = util.addZero(t.h,2) + ":" + util.addZero(t.m,2) + ":" + util.addZero(t.s,2);
            var res = util.postJson("erp/addSigninDay.do",{signinType : "上班"});
            if (!res){
                util.errMsg("签退失败",that.msgDelay);
                return
            }
            if (!that.nowDay){
                var obj = {};
                obj.realTime = new Array();
                obj.realTime[0] = str;
                that.nowDay = obj;
                that.setDay(that.nowDay);
                that.calendar.find("#calendar-morning").addClass("bdcolor-bottom-blue").text("M: " + str);
                util.successMsg("签到成功",that.msgDelay);
            }else{
                util.errMsg("签到失败",that.msgDelay);
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
                    var res = util.postJson("erp/addSigninDay.do",{signinType : "加班申请",remark : text});
                    if (!res){
                        util.errMsg("申请提交成功",that.msgDelay);
                        return
                    }
                    if (text){
                        that.nowDayFlag = true;
                        util.successMsg("加班申请成功提交",that.msgDelay);
                    }else{
                        util.errMsg("数据为空",that.msgDelay);
                    }
                });
                conform.setBody(d);
                conform.show("moveFromTopFade");
            });
        /* 签退按钮 */
        $("<button />").addClass("btn btn-right-radius bgcolor-green").text("签退").appendTo(control).click(function(){
            var t = timer.getTimer();
            var str = util.addZero(t.h,2) + ":" + util.addZero(t.m,2) + ":" + util.addZero(t.s,2);
            if (that.nowDay){
                if (that.nowDay.realTime.length == 2){
                    util.errMsg("签退失败",that.msgDelay);
                    return;
                }
                var res = util.postJson("erp/addSigninDay.do",{signinType : "下班"});
                if (!res){
                    util.errMsg("签退失败",that.msgDelay);
                    return
                }
                that.nowDay.realTime[1] = str;
                that.nowDay.flag = that.nowDayFlag?true:false;
                that.setDay(that.nowDay);
                /* 弹出一个成功签退的对话框 */
                if (that.nowDay.flag){
                    that.calendar.find("#calendar-night").addClass("bdcolor-bottom-black").text("N: " + str);
                }else{
                    that.calendar.find("#calendar-afternoon").addClass("bdcolor-bottom-green").text("A: " + str);
                }
                util.successMsg("签退成功",that.msgDelay);
            }else{
                util.errMsg("签退失败",that.msgDelay);
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
                $("<span />").addClass("title").text("Now: ").appendTo(span);
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
        this.footInfo = $("<span />").addClass("value").appendTo(li);
        li.appendTo(ul);
    },
    /* 添加 增加备注的按钮 */
    createAdd: function (parent) {
        var add = $("<span class='date-add icon-add-circle'>");
        var that = this;
        add.click(function () {
                var d = that.createSubmit("日程备注：【请如实填写】",function (text) {
                    /* text 为提交的信息 */
                    if (text){
                        that.setDay({dayWhat : text},that.clickDay);
                        util.successMsg("日程备注成功提交",that.msgDelay);
                    }else{
                        util.errMsg("数据为空",that.msgDelay);
                    }

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

        var day = this.calendar.find("[day=" + dayNum + "]");


        /* 设置日程 */
        if (dayData.dayWhat) {
            day.find(".date-what").addClass("show");
        }

        /* 设置 某一天 的数据 */
        if (!dayData.realTime){
            return;
        }

        /* 对数据进行赋值 */
        var lis = day.find("ul li");
        /* 判定是否为加班 */

        $(lis[0]).addClass("bgcolor-blue").find("span").text("M : " + dayData.realTime[0]);  // 上班
        if (dayData.flag) {
            //lis[2]  //加班
            if (dayData.realTime[1]) {
                $(lis[2]).addClass("bgcolor-black").find("span").text("N : " + dayData.realTime[1]);  // 加班
            }
        } else {
            if (dayData.flag != null){
                //lis[1] // 下班
                if (dayData.realTime[1]){
                    $(lis[1]).addClass("bgcolor-green").find("span").text("A : " + dayData.realTime[1]);  // 下班
                }
            }
        }
    },
    /* 创建日历的title 部分 */
    createThead: function () {
        var thead = $("<thead />");
        var tr = this.createTr().attr("class", "week-title").appendTo(thead);
        var weekArray = new Array("日", "一", "二", "三", "四", "五", "六");
        //var weekArray = new Array("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT");
        for (var i in weekArray) {
            var td = this.createTd();
            $("<span />").text(weekArray[i]).appendTo(td);
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
    },
    setHoverEvent: function () {
        /* 增加click 之后的样式CSS */
        var that = this;
        this.calendar.find(".date").click(function () {
            $(".date").removeClass("date-hover bgcolor-red");
            $(this).addClass("date-hover bgcolor-red");
            that.clickDay = $(this).attr("day");
        });
    },
    setNow: function () {
        var month = this.calendar.find(".date");
        var dateNow = this.calendar.find(".date-now");
        var now = new Date();
        var nowDay = now.getDate();
        this.nowDay = this.dataObj.days[nowDay - 1];
        for (var i = 0; i < month.length; i++) {
            var obj = $(month[i]);
            if (obj.attr("day") == nowDay) {
                this.dateNow.data("day",nowDay);
                obj.addClass("date-hover bgcolor-red");
                this.clickDay = nowDay;
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
            this.calWorkDay(i,this.day);
            this.day++;
        }
        tr.appendTo(parent);
    },
    createOtherWeek: function (parent, year, month) {
        this.number = number = 0;//星期数，末尾添加空行用，统一样式。
        for (i = 0; i < this.weeknumber; i++)//其他星期
        {
            var tr = this.createTr();
            var num = 0;
            for (var k = this.daynumber - (7 - this.firstnumber) - (this.weeknumber - 1) * 7; k < this.daynumber - (7 - this.firstnumber) - (this.weeknumber - 1) * 7 + 7; k++) {
                var id = year + "-" + month + "-" + this.day;
                this.createTd(this.createDay(this.day)).attr("id", id).appendTo(tr);
                this.calWorkDay(num,this.day);
                this.day++;
                num ++;
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
            this.calWorkDay(i,this.day);
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
    },
    calWorkDay  : function(num,day){
        if (num == 6 || num == 0){
            this.unWorkDays.push(day);
        }else{
            this.workDays.push(day);
        }
    }
}