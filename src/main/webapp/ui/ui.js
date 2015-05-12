/**
 * Created by Administrator on 15-4-29.
 */

/* 拥有增删改查功能的table */
Ui_DataTable = function (parent) {
    this.parent = parent;

    /* 消息 延迟 时间 */
    this.msgDelay = 1800;

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
    setTheadConfig: function (titleConfig) {
        this.theadTitleConfig = titleConfig;
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
            this.createUpdate(obj, rowData);
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
        var obj;
        var num = 0;
        $button.click(function () {
            if (obj) {
                if (num == 0) {
                    obj.show();
                    num = 1;
                } else {
                    obj.hide();
                    num = 0;
                }
            } else {
                obj = that.createQuerySpace(data);
                num = 1;
            }

        });
        return $button;
    },
    /* 创建一个 查看的 details table */
    /*
    createQueryDetailsTable: function (data) {
        var tr = $("<tr />");
        var td = $("<td colspan='" + this.sortMethod.length + "'/>").addClass("details").appendTo(tr);
        var that = this;
        var $ul = $("<ul />").appendTo(td);
        $.each(data,function(k,v){
            if (v instanceof  jQuery) {
                return;
            }
            var $li = $("<li />").appendTo($ul);
            $("<span />").text(that.theadTitle[k] + "：").appendTo($li);
            $("<span />").text(v).appendTo($li);
        })
        return tr;
    },
    */
    createQuerySpace : function(data){
        var that = this;
        this.div.data("c", this.div.attr("class"));

        var d = this.createSubmit(data, function () {
            d.remove();
            util.useAnim(that.div, "moveFromLeftFade", "in");
        });

        d.appendTo(that.parent);

        this.div.hide();

        util.useAnim(d, "moveFromRightFade", "in")
    },
    /* 修改数据 会打开 一个 修改数据信息的框 控件数据源自于 table 的原始Tbody的数据信息 */
    createUpdate: function (parent, data) {
        var $button = $("<button />").addClass("btn btn-mini bgcolor-green").appendTo(parent);
        $icon = $("<i />").addClass("icon-pencil").appendTo($button);
        var that = this;
        var obj;
        var num = 0;
        $button.click(function () {
            if (obj) {
                if (num == 0) {
                    obj.show();
                    num = 1;
                } else {
                    obj.hide();
                    num = 0;
                }
            } else {
                obj = that.createUpdateSpace(data);
                num = 1;
            }

        });
        return $button;
    },
    /* 创建一个 查看的 details table */
    createUpdateSpace: function (data) {
        var that = this;
        this.div.data("c", this.div.attr("class"));

        var d = this.createSubmit(data, function () {
            d.remove();
            util.useAnim(that.div, "moveFromLeftFade", "in");
        }, function (updateData) {
            /* updateData 为新更新后的数据源 */
            if (updateData) {
                /* 将数据 提交至后台 进行处理 */

                /* 更新前台数据源 */
                console.log(updateData);
                util.successMsg("修改数据成功", that.msgDelay);
            } else {
                util.errMsg("数据为空", that.msgDelay);
            }
        });

        /* 创建 input 控件 */
        d.appendTo(that.parent);

        this.div.hide();

        util.useAnim(d, "moveFromRightFade", "in")
    },
    /* 删除按钮 会 包裹数据 */
    createDelete: function (parent, data) {
        var $button = $("<button />").addClass("btn btn-mini bgcolor-red").appendTo(parent);
        $icon = $("<i />").addClass("icon-delete").appendTo($button);
        var that = this;
        $button.click(function () {
            console.log(data);
            util.successMsg("数据删除成功", that.msgDelay);
            var tr = parent.parent().parent();
            tr.addClass("moveToRightFade");
            var animEvent = util.getAnimEndEvent();
            tr.on(animEvent, function () {
                tr.off(animEvent);
                tr.remove();
            })

            /* 还需要一同删除 附加 details 信息 */
            var afterTr = tr.next();
            var details = afterTr.find(".details");
            if (details[0]) {
                afterTr.attr("class", "");
                afterTr.addClass("moveToRightFade");
                var animEvent = util.getAnimEndEvent();
                afterTr.on(animEvent, function () {
                    afterTr.off(animEvent);
                    afterTr.remove();
                })
            }
        })
        return $button;
    }
    ,
    /* 获取 数据 方式 选择 类型 以行 或 块进行获取 */
    getSpaceData: function (obj, type) {
        /* type = "tr" or type = "space" */
    }
    ,
    /* 设置Control标志 */
    setControlFlag: function (flag) {
        this.controlFlag = true;
    }
    ,
    /* 渲染 将 tbody 部分数据清空进行 数据重新整合 */
    reFlash: function () {

    },
    /* 功能块 */
    /* old */
    createSubmit: function (data, callback1, callback2) {
        var that = this;
        var $div = $("<div />");
        var dataObj = new Array();

        var $control = $("<div />").appendTo($div);

        if (callback2){
            $("<button />").addClass("btn btn-left-radius bgcolor-gray").text("返回").appendTo($control).click(function () {
                conform.close("moveToTopFade");
                callback1($div);
            });
            $("<button />").addClass("btn btn-right-radius bgcolor-blue").text("提交").appendTo($control).click(function () {                conform.close("moveToTopFade");
                callback2(that.getDataByDataObj(dataObj));
            });
        }else{
            $("<button />").addClass("btn bgcolor-gray").text("返回").appendTo($control).click(function () {
                conform.close("moveToTopFade");
                callback1($div);
            });
        }

        var tab = new Ui_Tab($div);
        /* 整理 数据 */
        var tabMenuList = new Array();
        var tabContents = new Array();
        $.each(this.theadTitleConfig, function (i, v) {
            var obj = {text: v.title};
            var $contentDiv = $("<div />");
            for (var i = 0; i < v.keys.length; i++) {
                var key = v.keys[i];
                $("<label />").addClass("label bgcolor-white").attr("style", "width:100%").text(that.theadTitle[key]).appendTo($contentDiv);
                ;
                var input = $("<input />").addClass("text").attr("style", "width:100%").attr("value", data[key]).appendTo($contentDiv);
                var o = {};
                o[key] = input;
                dataObj.push(o);
            }
            tabMenuList.push(obj);
            tabContents.push($contentDiv);
        })

        tab.setMenu(tabMenuList);
        tab.setContent(tabContents);


        return $div;
    }
    ,
//createSubmit: function (data,callback) {
//    var that = this;
//    var $div = $("<div />");
//    var dataObj = new Array();
//
//    $.each(data,function(k,v){
//        if (k == "control"){
//            return;
//        }
//        var title = that.theadTitle[k] + "：";
//
//        $("<label />").addClass("label bgcolor-white").attr("style","width:100%").text(title).appendTo($div);;
//        var input = $("<input />").addClass("text").attr("style","width:100%").attr("value", v).appendTo($div);
//
//        var obj = {};
//        obj[k] = input;
//        dataObj.push(obj);
//    });
//    var that = this;
//    var $control = $("<div />").appendTo($div);
//    $("<button />").addClass("btn btn-left-radius bgcolor-gray").attr("style","width:100%").text("取消").appendTo($control).click(function () {
//        conform.close("moveToTopFade");
//    });
//    $("<button />").addClass("btn btn-right-radius bgcolor-blue").attr("style","width:100%").text("提交").appendTo($control).click(function () {
//        conform.close("moveToTopFade");
//        callback(that.getDataByDataObj(dataObj));
//    });
//    return $div;
//},
    getDataByDataObj: function (dataObj) {
        var json = null;
        $.each(dataObj, function (_, v) {
            $.each(v, function (k, obj) {
                var value = obj.val();
                if (!value) {
                    return;
                }
                if (!json) {
                    json = {};
                }
                json[k] = value;
            })
        });
        return json;
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
        /*
        if (!this.$tabMenuBottom) {
            this.initTabMenuBottom();
        }
        */
        //var tabs = this.$tabMenuList.find("li a");
        //this.$tabMenuBottom.css("width", $(tabs[0]).outerWidth());
    },
    createMenuList: function (dataObj, num, cssClass) {
        var $li = $("<li />");
        var $a = $("<a />").appendTo($li);
        var that = this;
        $a.click(function () {
            var tabs = that.$tabMenuList.find("li");
            tabs.removeClass("select");
            $(this).parent().addClass("select");
            var tabContents = that.$tabContent.find(".tab-content-body");
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