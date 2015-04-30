/**
 * Created by Administrator on 15-4-8.
 */


var util = {
    isJquery : function(obj,errMsg){
        if (obj instanceof  jQuery) {
            return true;
        }
        console.log(errMsg);
        return false;
    },
    /* 判定对象存在 */
    isExist : function(obj,errMsg){
        if (obj){
            console.log(errMsg);
            return true;
        }
        return false;
    },
    /* 给指定字符串补0 */
    addZero : function(str,len){
        var len = len - ("" + str).length
        var zeroStr = "";
        for(var i = 0;i< len;i++){
            zeroStr = zeroStr + "0";
        }
        return zeroStr + str;
    },
    /* 获取动画关于各种不同浏览器的事件 */
    animEndEventNames : {
        'WebkitAnimation' : 'webkitAnimationEnd',
        'OAnimation' : 'oAnimationEnd',
        'MsAnimation' : 'MSAnimationEnd',
        'Animation' : 'animationend'
    },
    getVendorPrefix : function() {
        var body = document.body || document.documentElement,
            style = body.style,
            vendor = ['Webkit', 'Ms', 'O'],
            i = 0;

        while (i < vendor.length) {
            if (typeof style[vendor[i] + 'Transition'] === 'string') {
                return vendor[i];
            }
            i++;
        }
    },
    getAnimEndEvent : function(){
        var prefix = this.getVendorPrefix() || "";
        var animEndEventName = this.animEndEventNames[ prefix + 'Animation'];
        return animEndEventName;
    },
    errMsg : function(msg,delay){
        /* 弹出一个很快消失的对话框 */

    },
    successMsg : function(msg,delay){
        /* 弹出一个 很快消失的 成功的信息框 */

    }
}


Timer = function(){
    this.init();
}

Timer.prototype = {
    init : function(){
       this.dom = $("<span />").addClass("timer");
    },
    second: function () {
        if (this.s > 0 && (this.s % 60) == 0) {
            this.m += 1;
            this.s = 0;
        }
        if (this.m > 0 && (this.m % 60) == 0) {
            this.h += 1;
            this.m = 0;
        }
        this.timerStr = timerStr = util.addZero(this.h,2) + ":" + util.addZero(this.m,2) + ":" + util.addZero(this.s,2) + "";
        this.dom.text(timerStr);
        this.s += 1;
    },
    setTime : function(h,m,s){
        this.h = h;
        this.m = m;
        this.s = s;
    },
    getTimer : function(){
        return {"h":this.h,"m":this.m,"s": this.s, str:this.timerStr};
    },
    start: function () {
        var that = this;
        that.second();
        this.timer = setInterval(function(){
            that.second();
        },1000);
    },
    pause: function () {
        clearInterval(this.timer);
    }
}