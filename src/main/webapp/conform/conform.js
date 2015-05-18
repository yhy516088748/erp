/**
 * Created by Administrator on 15-4-22.
 */

Conform = function(parent){
    if (!util.isJquery(parent,"Conform's parent is not jQuery")){
        return;
    }
    /* 初始化的参数 */
    this.conform = null;
    this.conformBack = null;
    this.conformBody = null;
    this.parent = parent;

    /* 初始化方法 */
    this.init();
}

Conform.prototype = {
    init : function(){
        this.createConform();
    },
    createConform : function(){
        this.conform = conform = $("<div />").addClass("conform");
        this.conformBack = conformBack =$("<div />").addClass("conform-back").appendTo(conform);
        var that = this;
        conformBack.click(function(){
            that.close("moveToTopFade");
        });
        this.conformBody = conformBody = $("<div />").addClass("conform-body").appendTo(conform);
        conform.appendTo(this.parent);
    },
    setBody : function(obj){
        this.conformBody.empty();
        obj.appendTo(this.conformBody);
        return this;
    },
    show : function(animation,callback){
        this.conform.show();
        var hiddenClass = this.conformBody.attr("class");
        this.conformBody.data("class",hiddenClass);
        this.conformBody.addClass("conform-body-show " + animation);
        var animEndEvent = util.getAnimEndEvent();
        var that = this;
        this.conformBody.on(animEndEvent,function(){
            that.conformBody.off(animEndEvent);
            that.conformBody.removeClass(animation);
            if (callback){
                callback();
            }
        });
        return this;
    },
    close : function(animation,callback){
        this.conformBody.addClass(animation);
        var animEndEvent = util.getAnimEndEvent();
        var that = this;
        this.conformBody.on(animEndEvent,function(){
            that.conformBody.off(animEndEvent);
            that.conformBody.removeClass(animation);
            that.conformBody.attr("class",that.conformBody.data("class"));
            that.conform.hide();
            if (callback){
                callback();
            }
        });
        return this;
    }
}

ConformMain = function(parent){
    if (!util.isJquery(parent,"ConformMain's parent is not jQuery")){
        return;
    }

    /* 初始化的参数 */
    this.conformMain = null;
    this.conformBody = null;
    this.parent = parent;

    /* 初始化方法 */
    this.init();
}

ConformMain.prototype = {
    init : function(){
        this.createConform();
    },
    createConform : function(){
        this.conformMain = conformMain = $("<div />").addClass("conform-main");
        var conformTitle =$("<div />").addClass("conform-title").appendTo(conformMain);

        var btn = $("<button />").addClass("return btn bgcolor-blue").text("return").appendTo(conformTitle);

        var that = this;
        btn.click(function(){
            that.close("moveToRight");
        });
        /* 增加 touch 事件 */

        this.conformBody = conformBody = $("<div />").addClass("conform-body").appendTo(conformMain);
        conformMain.appendTo(this.parent);
    },
    setBody : function(obj){
        this.conformBody.empty();
        obj.appendTo(this.conformBody);
        return this;
    },
    show : function(animation,callback){
        this.conformMain.show();
        var hiddenClass = this.conformMain.attr("class");
        this.conformMain.data("class",hiddenClass);
        this.conformMain.attr("class","conform-main absolute conform-body-show " + animation);
        var animEndEvent = util.getAnimEndEvent();
        var that = this;
        this.conformMain.on(animEndEvent,function(){
            that.conformMain.off(animEndEvent);
            //that.conform.removeClass(animation + " absolute");
            that.conformMain.attr("class","conform-main conform-body-show");
            $("#content-container").hide();
            if (callback){
                callback();
            }
        });
        return this;
    },
    close : function(animation,callback){
        $("#content-container").show();
        this.conformMain.attr("class","conform-main absolute conform-body-show " + animation);
        var animEndEvent = util.getAnimEndEvent();
        var that = this;
        this.conformMain.on(animEndEvent,function(){
            that.conformMain.off(animEndEvent);
            that.conformMain.hide();
            that.conformMain.attr("class","conform-main");
            if (callback){
                callback();
            }
        });
        return this;
    }
}