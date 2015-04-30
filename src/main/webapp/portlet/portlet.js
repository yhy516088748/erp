/**
 * Created by Administrator on 15-4-16.
 */



PortLet = function(parent){
    /* 判定是否为 jquery 对象 */
    if (!util.isJquery(parent,"PortLet's parent is not jquery")){
        return;
    }
    /* 可以选择不返回 对parent对象 进行判定 是否为 DOM对象  可以将其 DOM 对象转换为 jquery */

    this.parent = parent;
    /* 初始化 */
    this.init();
}

PortLet.prototype = {
    init : function(){
        return this.createPortLet();
    },
    createPortLet : function(){
        /* 添加 整个 块 */
        this.portLet = portLet = $("<div />").addClass("portlet");
        /* 添加标签 */
        this.addCaption();

        /* 添加body */
        this.addBody();

        portLet.appendTo(this.parent);
        return portLet;
    },
    addCaption : function(){
        if (this.caption){
            console.log("PortLet's caption already exists");
            return;
        }
        this.caption  = caption = $("<div />").addClass("caption");
        $("<i />").addClass("caption-icon icon-menu").appendTo(caption);
        this.title = $("<span />").addClass("caption-title").appendTo(caption);
        var control = $("<div />").addClass("caption-control").appendTo(caption);

        /* 一些小按钮的功能 */
        var del =  $("<i />").addClass("caption-control-icon icon-delete").appendTo(control);
        var that = this;
        /* 删除功能从页面移除此项 */
        del.click(function(){
            that.portLet.slideUp(300);
        });

        caption.appendTo(this.portLet);
        return this;
    },
    addBody : function(){
        if (util.isExist(this.body,"PortLet's body already exists")){
            return;
        }
        this.body = $("<div />").addClass("body").appendTo(this.portLet);
        return this;
    },
    clearColor : function(){
        this.portLet.attr("class","portlet");
        this.caption.attr("class","caption");
        this.body.attr("class","body");
    },
    setColor : function(titleColor,bodyColor){
        this.clearColor();
        var title_bgcolor = "bgcolor-" + titleColor;
        var title_bdcolor = "bdcolor-" + titleColor;
        var body_bgcolor = "bgcolor-" + bodyColor;
        this.portLet.addClass(title_bdcolor);
        this.caption.addClass(title_bgcolor);
        this.body.addClass(body_bgcolor);
        return this;
    },
    setTitle : function(title){
        this.title.text(title);
        return this;
    },
    setBody : function(obj){
        if (!util.isJquery(obj,"body's childObj is not jquery")){
            return;
        }
        obj.appendTo(this.body);
        return this;
    }
}