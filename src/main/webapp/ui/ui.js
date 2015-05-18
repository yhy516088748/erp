/**
 * Created by Administrator on 15-4-29.
 */

/* 拥有增删改查功能的table */
Ui_DataTable = function (parent) {
    this.parent = parent;

    /* 消息 延迟 时间 */
    this.msgDelay = 1500;

    this.init();
}

Ui_DataTable.prototype = {
    init: function () {
        this.div = $div = $("<div />").addClass("data-table-div");
        this.controlDiv = $("<div />").addClass("data-table-control").appendTo($div);
        this.createTable();
        this.createThead();
        this.createTbody();
        $div.appendTo(this.parent);
    },
    setThead: function (title) {
        this.theadTitle = title;
        /* 通过 controlFlag 标志 是否在最后新增 控制标签 此标签不会被隐藏 */
        if (this.controlFlag) {
            title.control = "";
        }
        var tr = this.createTr(this.thead);
        var sortMethod = new Array();
        var that = this;
        var num = 0;
        $.each(title, function (k, v) {
            sortMethod.push(k);
            var td = that.createTh(tr);

            if (num > 1 && k != "control") {
                td.addClass("hidden-540");
            }
            if (num > 6 && k != "control") {
                td.addClass("hidden");
            }
            if (k == "control") {
                $("<i />").addClass("icon-tools").appendTo(td);
            } else {
                td.text(v);
            }
            num++;
        });

        this.sortMethod = sortMethod;
        this.setControlDiv();
    },
    setControlDiv: function () {
        if (this.controlFlag) {
            this.createAdd(this.controlDiv);
        }
    },
    setTbody: function (arrData) {
        /* 通过 controlFlag 标志 是否在最后新增 控制按钮 此标签不会被隐藏 */
        /* 数据排序方式 会 按照 thead 中的 方式进行排列 */
        var that = this;
        for (var i = 0; i < arrData.length; i++) {
            var rowData = arrData[i];
            this.addRow(i, rowData);
        }
    },
    /* 添加一行 */
    addRow: function (num, rowData) {
        var rowData = this.addControlToRowData(rowData);
        var that = this;
        var tr = this.createTr(this.tbody);
        if (num % 2 == 0) {
            tr.addClass("light");
        }
        $.each(this.sortMethod, function (i, k) {
            var o = rowData[k];
            if (o == null || o ==""){
                return;
            }
            var td = that.createTd(tr);
            if (o instanceof jQuery) {
                o.appendTo(td);
            }
            if (typeof(o) == "string") {
                if (i > 1) {
                    td.addClass("hidden-540");
                }
                if (i > 6) {
                    td.addClass("hidden");
                }
                td.text(o);
            }
        });
        return tr;
    },
    /* 通过 controlFlag 新增按钮标志 */
    addControlToRowData: function (rowData) {
        /* 先判定 controlFlag 是否已经拥有了 有的情况下 则需要酌情添加 修改 与 删除按钮 */
        if (this.controlFlag) {
            /* 创建 删除按钮 与 修改按钮 */
            var obj = $("<div />").addClass("row-control");
            this.createDelete(obj, rowData);
            this.createQuery(obj, rowData);
            rowData.control = obj;
        }
        return rowData;
    },
    createTable: function () {
        this.table = $('<table />').addClass("data-table").appendTo(this.div);
    },
    createThead: function () {
        this.thead = $("<thead />").addClass("data-table-thead").appendTo(this.table);
    },
    createTbody: function () {
        this.tbody = $("<tbody />").addClass("data-table-tbody").appendTo(this.table);
    },
    createTr: function (parent) {
        var $tr = $("<tr />").appendTo(parent);
        return $tr;
    },
    createTh: function (parent) {
        var $th = $("<th />").appendTo(parent);
        return $th;
    },
    createTd: function (parent) {
        var $td = $("<td />").appendTo(parent);
        return $td;
    },
    /* 创建一个增按钮 */
    /* 增按钮只会被 创建一次 并在 整个table 的外部右上角  并弹出一个 增加信息的框 控件源自于 table 的原始Tbody的数据信息*/
    createAdd: function (parent) {
        var $icon = $("<span />").addClass("date-add icon-add-circle").appendTo(parent);
        var that = this;
        var obj;
        var num = 0;
        $icon.click(function () {
            if (obj) {
                if (num == 0) {
                    obj.show();
                    num = 1;
                } else {
                    obj.hide();
                    num = 0;
                }
            } else {
                obj = that.createAddSpace();
                num = 1;
            }

        });
        return $icon;
    },
    /* 创建一个 查看的 details table */
    createAddSpace: function () {
        var that = this;
        var data = {};
        $.each(this.theadTitle, function (k, v) {
            data[k] = ""
        });
        var d = this.createSubmit(data, function (addData) {
            /* updateData 为新更新后的数据源 */
            if (addData) {
                /* 将数据 提交至后台 进行处理 */

                /* 更新前台数据源 */
                var tr = that.addRow(1, addData);
                util.successMsg("新增数据成功提交", that.msgDelay);
            } else {
                util.errMsg("数据为空", that.msgDelay);
            }
        });
        /* 创建 input 控件 */
        conform.setBody(d);
        conform.show("moveFromTopFade");
    },
    /* 查 会按照 指定 条件并生成块 在 table的上方 */
    createQuery: function (parent, data) {
        var $button = $("<button />").addClass("btn btn-mini bgcolor-blue").appendTo(parent);
        $icon = $("<i />").addClass("icon-search").appendTo($button);
        var that = this;
        var num = 0;
        $button.click(function () {
            that.createQuerySpace(data);
        });
        return $button;
    },
    setQueryAction : function(){

    },
    createQuerySpace : function(data){
        var that = this;
        var d = $("<div />");
        var userinfo = new UserInfo(d);
        userinfo.setData(data.userid);
        /* 创建 input 控件 */
        conformMain.setBody(d);
        conformMain.show("moveFromRight");
    },
    /* 删除按钮 会 包裹数据 */
    createDelete: function (parent, data) {
        var $button = $("<button />").addClass("btn btn-mini bgcolor-red").appendTo(parent);
        $icon = $("<i />").addClass("icon-delete").appendTo($button);
        var that = this;
        $button.click(function () {
            var newData = data;
            newData.control = null;
            var res = util.postJson(that.deleteAction,newData);
            if (!res){
                util.errMsg("数据删除失败", that.msgDelay);
                return;
            }
            util.successMsg("数据删除成功", that.msgDelay);
            var tr = parent.parent().parent();
            tr.addClass("moveToRightFade");
            var animEvent = util.getAnimEndEvent();
            tr.on(animEvent, function () {
                tr.off(animEvent);
                tr.remove();
            })
        })
        return $button;
    },
    setDeleteAction : function(url){
        this.deleteAction = url;
    },
    /* 设置Control标志 */
    setControlFlag: function (flag) {
        this.controlFlag = true;
    }
    ,
    /* 渲染 将 tbody 部分数据清空进行 数据重新整合 */
    reFlash: function () {

    }
}

/* tab 分页功能 */
Ui_Tab = function (parent) {
    this.parent = parent;

    /* 滑动块的动画完成时间 */
    this.bottomDelay = 300;

    this.init();
}

Ui_Tab.prototype = {
    init: function () {
        this.$tab = $("<div />").addClass("tab").appendTo(this.parent);
        this.createMenu();
        this.createContent();
    },
    createMenu: function () {
        this.$tabMenu = $tabMenu = $("<div />").addClass("tab-menu").appendTo(this.$tab);
        this.$tabMenuList = $("<ul />").addClass("tab-menu-list").appendTo($tabMenu);
    },
    initTabMenuBottom: function () {
        this.$tabMenuBottom = $("<span />").addClass("tab-menu-bottom").appendTo(this.$tabMenu);
    },
    setMenu: function (arrObj) {
        /*
         var arrObj = new Array(
         {icon : "icon-home",text : "Home"},
         {icon : "icon-userinfo",text : "userInfo"},
         {icon : "icon-userinfo",text : "userInfo"}
         )
         */
        for (var i = 0; i < arrObj.length; i++) {
            var cssClass;
            if (i == 0) {
                cssClass = "first";
            } else if (i == arrObj.length - 1) {
                cssClass = "last";
            } else {
                cssClass = null;
            }
            this.createMenuList(arrObj[i], i, cssClass);
        }
    },
    createMenuList: function (dataObj, num, cssClass) {
        var $li = $("<li />");
        var $a = $("<a />").appendTo($li);
        var that = this;
        $a.click(function () {
            var tabs = that.$tabMenuList.children("li");
            tabs.removeClass("select");
            $(this).parent().addClass("select");
            var tabContents = that.$tabContent.children(".tab-content-body");
            tabContents.removeClass("active");
            tabContents.hide();
            $(tabContents[num]).addClass("active");
            $(tabContents[num]).show();
            /*
            var width = $(this).outerWidth();
            var o = that.$tabMenu;
            var left = $(this).offset().left - o.offset().left;
            var top = $(this).offset().top - o.offset().top + $(this).height();
            that.$tabMenuBottom.animate({"width": width, "left": left, "top": top}, that.bottomDelay);
            */
        })

        dataObj.icon ? $("<i />").addClass(dataObj.icon).appendTo($a) : false;
        dataObj.text ? $("<span />").text(dataObj.text).appendTo($a) : false;

        cssClass ? $a.addClass(cssClass) : false;
        num == 0 ? $li.addClass("select") : false;

        $li.appendTo(this.$tabMenuList);
        return $li;
    },
    createContent: function () {
        this.$tabContent = $("<div />").addClass("tab-content").appendTo(this.$tab);
    },
    setContent: function (arrObj) {
        /*
         var arrObj = new Array(
         $("<p />").text("首页1"),
         $("<p />").text("首页2"),
         $("<p />").text("首页3")
         )
         */
        for (var i = 0; i < arrObj.length; i++) {
            this.createContentBody(arrObj[i], i);
        }
    },
    createContentBody: function (obj, num) {
        var $div = $("<div />").addClass("tab-content-body");
        if (util.isJquery(obj, "obj's is not jQuery")) {
            obj.appendTo($div);
            $div.hide();
        }
        $div.appendTo(this.$tabContent);
        if (num == 0) {
            $div.addClass("active");
            $div.show();
        }
        return $div;
    }
}