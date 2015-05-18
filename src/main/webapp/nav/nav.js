/* sample :

 * 设置菜单的放置位置
 var navBar = new NavBar($("#content-nav"));

 * 设置数据 并直接进行渲染
 * dataObj  = {
     root: [
         {code: "rcgl", title: "日常管理", icon: "product"},
         {code: "gsgl", title: "公司管理", icon: "policy"},
         {code: "xmgl", title: "项目管理", icon: "claim"},
         {code: "rygl", title: "人员管理", icon: "users"},
         {code: "bxgl", title: "报销管理", icon: "hospital"}
     ],
     child: {
         rcgl: [
             {title: "打卡", module: "dk"},
             {title: "考勤", module: "kq"},
             {title: "会议", module: "hy"}
         ],
         gsgl: [
             {title: "打卡", module: "dk"},
             {title: "考勤", module: "kq"},
             {title: "会议", module: "hy"}
         ]
     }
 }

 navBar.setData(dataObj);

 * 设置响应后的菜单按钮放置位置
 navBar.setMenuControl($("#header-nav"));

 */

NavBar = function (parent) {
    /* 判定是否为 jquery 对象 */
    if (!parent instanceof  jQuery) {
        console.log("NavBar's parent is not jquery");
        return;
    }
    /* 可以选择不返回 对parent对象 进行判定 是否为 DOM对象  可以将其 DOM 对象转换为 jquery */

    this.parent = parent;
    /* 初始化 */
    this.init();
}

NavBar.prototype = {
    init: function () {
        return this.createNav();
    },
    /* 创建一个菜单的父节点 */
    createNav: function () {
        this.navBar = navBar = $("<div />").addClass("nav");
        var navMenu = $("<div />").addClass("nav-menu").appendTo(navBar);
        this.selectSpan = $("<span />").addClass("selected").appendTo(navMenu);
        this.rootUl = ul = this.createUl().appendTo(navMenu);
        navBar.appendTo(this.parent);
        var that = this;
        $(window).resize(function(){
           if ($(this).innerWidth() >= 768){
               if (that.navBar[0].style.display == "block"){

               }else{
                   that.navBar.show();
               }
           }
        });
        return navBar;
    },
    setData: function (dataObj) {
        this.rootUl.empty();
        var root = dataObj.root;
        var child = dataObj.child;
        var that = this;
         var rootLiArray = new Array();
        function findChildObj(child,code){
            var obj;
            for (var i = 0 ; i < child.length;i ++ ){
                var cObj = child[i];
                $.each(cObj,function(k,v){
                    if (k == code){
                        obj = v;
                    }
                })
                if (obj){
                    return obj;
                }
            }
            return obj;
        }
         $.each(root, function (i, v) {
             var icon = v.icon;
             var title = v.title;
             var childObj = findChildObj(child,v.code);
             var rootLi = that.createRootLi(icon, title, childObj).appendTo(that.rootUl);
             rootLiArray.push(rootLi);
         });
         this.rootLiArray = rootLiArray;
    },
    setMenuControl: function (parentObj, speed) {
        /* 判定 menuControl 是否已经被创建 */
        /* 默认速度 300 */
        /* 默认曲线 linear */
        if (!this.menuControl) {
            this.menuControl = menuControl = this.createMenuControl();
            var num = 0;
            menuControl.click({self: this}, function (e) {
                var self = e.data.self;
               self.navBar.slideToggle(speed || 300);
            });
        }
        this.menuControl.appendTo(parentObj);
    },
    createMenuControl: function () {
        var menuControl = $("<div />");
        var a = $("<a />").addClass("nav-menu-control").appendTo(menuControl);
        var icon = $("<i />").addClass("icon-menu").appendTo(a);
        return menuControl;
    },
    createUl: function () {
        return $("<ul />");
    },
    createRootLi: function (icon, title, arrow) {
        var li = this.createLi();
        var a = $("<a />").appendTo(li);
        var icon = $("<i />").addClass("icon-" + icon).appendTo(a);
        var title = $("<span />").addClass("title").appendTo(a).text(title);
        if (arrow) {
            this.createArrow(li, a, arrow);
        }
        return li
    },
    createChildLi: function (parent, title, event) {
        var li = this.createLi();
        var a = $("<a />").appendTo(li);
        var title = $("<span />").appendTo(a).text(title);
        var that = this;
        li.click(function (e) {
            e.stopPropagation();
            that.clearLiClass("active");
            parent.addClass("active");
            that.selectSpan.appendTo(parent.find("a:first"));
            $(this).addClass("active");
            event();
        })
        return li;
    },
    clearLiClass: function (classStr) {
        var arrs = this.navBar.find("li");
        $.each(arrs,function (_, v) {
            $(v).removeClass(classStr);
        });
    },
    /* 只有 允许 下拉的 li 才会出现arrow 并允许被下拉 */
    createArrow: function (eventLi, parent, childData) {
        /* childData : {title: "打卡", module: "dk" , event : function(){ new window["dk"]()}} */
        var ul = this.createUl().appendTo(eventLi);
        var that = this;
        $.each(childData, function (i, v) {
            var title = v.title;
            var event = function(){
                $("#content-container").empty();
                new window[v.code]($("#content-container"));
            };
            var li = that.createChildLi(eventLi, title, event);
            li.appendTo(ul);
        });

        /* 初始默认 childUl 是隐藏的 通过 arrowSpan 进行toggle 出现 */
        ul.hide();

        var arrowSpan = $("<span />").addClass("arrow icon-keyboard-arrow-left").appendTo(parent);
        /* 更改 arrowSpan 的箭头状态 */
        /* 可以封装成 toggle */
        var num = 0;
        eventLi.click(function () {
            if (num == 0) {
                arrowSpan.removeClass("icon-keyboard-arrow-left");
                arrowSpan.addClass("icon-list-down");
                num = 1;
            } else {
                arrowSpan.removeClass("icon-list-down");
                arrowSpan.addClass("icon-keyboard-arrow-left");
                num = 0;
            }
            ul.slideToggle(300);
        });
    },
    createLi: function () {
        var li = $("<li />");
        return li;
    }
    /* 预留 颜色 修改接口 */
}