/**
 * Created by Administrator on 15-4-23.
 */

Message = function (parent) {
    this.parent = parent;
    /* 获取数据的连接 */
    this.action = null;
    /* 计时器 */
    this.interval = null;
    /* 默认延迟时间 */
    this.delay = 1000;

    /* 消息 上限 个数 */
    this.maxNum = 3;
    this.showNum = 1;
    this.init();
}

Message.prototype = {
    init: function () {
        this.$msg = $("<div />").addClass("message").appendTo(this.parent);
    },
    /* 他可以创建多个 message 信息框 但是 他弹出的位置 只有一个位置 消息需要手动关闭 */
    createMessage: function (title, msg, color, control) {
        var $msgbody = $("<div />").addClass("message-body message-body-show moveFromBottomFade bgcolor-" + color);
        var animEndEvent = util.getAnimEndEvent();
        var that = this;
        var $del = $("<span />").addClass("message-icon icon-delete").click(function () {
            $msgbody.attr("class", "message-body message-body-show moveToTopFade");
            $msgbody.on(animEndEvent, function () {
                $msgbody.off(animEndEvent);
                $msgbody.attr("class", "message-body");
                $msgbody.remove();
                that.showNum -= 1;
            })
        }).appendTo($msgbody);
        $("<span />").addClass("message-title").text(title).appendTo($msgbody);
        $("<p />").addClass("message-body-msg").text(msg).appendTo($msgbody);
        if (control) {
            /*对象*/
            var $div = $("<div />").addClass("message-body-control").appendTo($msgbody);
            var that = this;
            $("<button />").addClass("btn bgcolor-gray").text("否").click(function () {
                var d = that.createFalse(function (text) {
                    /* text 为 原因数据 获取后 进行 后台传输进行数据记录 并 关闭 当前 */
                    alert(control.falseAction);
                    console.log(text);
                    $del.click();
                });
                conform.setBody(d);
                conform.show("moveFromTopFade");
            }).appendTo($div);
            $("<button />").addClass("btn bgcolor-blue").text("是").click(function () {
                /* 对指定 action 进行 call 的 true 处理 */
                alert(control.trueAction);
                $del.click();
            }).appendTo($div);
        }
        return $msgbody;
    },
    showMessage: function () {
        /* 通过自己的 action 获取 数据 */
        var actions = new Array()
        actions[0] = {title : "这里是一个title1", msg : "这里是一个msg1这里是一个msg1这里是一个msg1这里是一个msg1这里是一个msg1这里是一个msg1这里是一个msg1这里是一个msg1这里是一个msg1", control: { trueAction : "true", falseAction : "false"}};
        for (var i in actions){
            var action = actions[i];
            if (this.showNum > this.maxNum){
                return;
            }
            this.createMessage(action.title, action.msg, "black", action.control).appendTo(this.$msg);
            this.showNum += 1;
        }

        return this;
    },
    /* 设置了 此项后 他会定时 像指定 action 进行 获取数据 */
    setAction: function (action) {
        this.action = action;
        return this;
    },
    /* 延迟时间以毫秒计算 */
    setDelay: function (delay) {
        this.delay = delay;
        return this;
    },
    setMaxNum : function(maxnum){
        this.maxNum = maxnum;
        return this;
    },
    start: function () {
        var that = this;
        that.showMessage();
        this.interval = setInterval(function () {
            that.showMessage();
        }, this.delay)
    },
    stop: function () {
        clearInterval(this.interval);
    },
    /* 创建一个 选择 否 后的原因 模块 进行 conform */
    createFalse: function (callback) {
        var $div = $("<div />");
        $("<label />").addClass("bgcolor-white").text("原因：（请如实填写）").appendTo($div);
        var text = $("<input />").addClass("text").appendTo($div);
        var that = this;
        $("<button />").addClass("btn bgcolor-blue").text("提交").attr("style", "width:100%").appendTo($div).click(function () {
            conform.close("moveToTopFade");
            callback(text.val());
        });
        return $div;
    }
}