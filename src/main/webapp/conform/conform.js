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