/**
 * Created by Administrator on 15-4-3.
 */

$(function(){
    function getNewArray(objs,typeClassName){
        var num = 0;
        var newObjs = new Array;

        for (var i = 0 ;i < objs.length ; i ++){
            if (objs[i].className == typeClassName) {
                if ( num == 1){
                    return $(newObjs);
                }
                num = 1
            }
            newObjs.push(objs[i])
        }
        return $(newObjs);
    }

    function getTabAs(tab){
        var as = tab.find(".tab-menu-list li a");
        return getNewArray(as,"select");
    }

    function getTabContents(tab){
        var tabContents = tab.find(".tab-content .tab-content-body");
        return getNewArray(tabContents,"active");
    }

    function getTabMenu(tab){
        return tab.find(".tab-menu");
    }

    function initTab(tab){
        var tabs = getTabAs(tab);
        var tabContents = getTabContents(tab);
        var bottom = tab.find(".tab-menu-bottom:first").css("width",$(tabs[0]).outerWidth());
        for (var i = 0;i < tabs.length ;i ++){
            (function(i){
                var num = i;
                $(tabs[num]).click(function(){
                    /* 清除所有 menu 选中效果 */
                    tabs.removeClass("select");
                    $(this).addClass("select");
                    /* 清除所有 tab-content 激活效果 */
                    tabContents.removeClass("active");
                    tabContents.hide();
                    $(tabContents[num]).addClass("active");
                    $(tabContents[num]).show();
                    var width = $(this).outerWidth();
                    var o = getTabMenu(tab);
                    var left = $(this).offset().left - o.offset().left;
                    bottom.animate({"width":width,"left":left},300);
                });
            })(i);
        }
    }
    function initTabHide(tab){
        var tabContents = getTabContents(tab);
        tabContents.hide();
        $(tabContents[0]).show();
    }
    /* 对所有menu 进行 事件加入 */
    var tabs = $(".tab");

    for (var i = 0 ; i < tabs.length ; i++){
        initTab($(tabs[i]));
    }
    for (var i = 0 ; i < tabs.length ; i++){
        initTabHide($(tabs[i]));
    }
});