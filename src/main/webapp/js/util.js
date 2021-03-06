/**
 * Created by Administrator on 15-4-8.
 */


var util = {	// actionHost : "http://10.10.10.132:8080/",
	actionHost : "",
	decode : function(data) {
		return $.parseJSON(data);
	},
	encode : JSON.stringify,
	// Ajax 获取数据
	getJson : function(url, data) {
		url = util.actionHost + url;
		if (!data) {
			data = {};
		}
        var index = url.indexOf("addSigninDay");
        if (index > 0){
            /*
            var res = util.postJson("erp/getLocalMacAddress.do");
            */
            getIp();

            //data.macAddress = res.macAddress;
            data.publicip = $("#public-ip").text();
            data.localip = $("#local-ip").text();
        }
		var contentType = "application/x-www-form-urlencoded; charset=utf-8";
		if (window.XDomainRequest)
			contentType = "text/plain";
		var dataStr = "";
		$.each(data, function(k, v) {
			dataStr = dataStr + "&" + k + "=" + v;
		})
		dataStr = dataStr.substr(1, dataStr.length);
		var request = $.ajax({
			url : url,
			async : false,
			data : dataStr,
			type : "get",
			dataType : "json",
			contentType : contentType
		});
		var res = util.decode(request.responseText);
		if (util.cRS(res)) {
			return res;
		}
		return false
	},
	postJson : function(url, data) {
		var url = util.actionHost + url;
		if (!data) {
			data = {};
		}
        var index = url.indexOf("addSigninDay");
        if (index > 0){
            var res = util.postJson("erp/getLocalMacAddress.do");
            getIp();
            data.macAddress = res.macAddress;
            data.publicIp = $("#public-ip").text();
            data.localIp = $("#local-ip").text();
        }
		var contentType = "application/x-www-form-urlencoded; charset=utf-8";
		if (window.XDomainRequest)
			contentType = "text/plain";
		var request = $.ajax({
			url : url,
			async : false,
			data : data,
			type : "post",
			dataType : "json",
			contentType : contentType
		});
		var res = util.decode(request.responseText);
		if (util.cRS(res)) {
			return res;
		}
		return false
	},

	/* 检验返回信息是否正确 */
	cRS : function(res) {
		if (res == null) {
			util.errMsg("response data is empty", 1500);
			return false;
		}
		if (res.Status == "OK") {
			return true;
		}
		if (res.Status == "success") {
			return true;
		}
		if (res.Status == "ERROR") {
            if (res.Reason){
                util.errMsg(res.Reason, 1500);
            }
			return false;
		}
		return false;
	},
	isJquery : function(obj, errMsg) {
		if (obj instanceof jQuery) {
			return true;
		}
		util.errMsg(errMsg, 1500);
		return false;
	},
	isArray : function(obj) {
		return obj.length ? true : false;
	},
	/* 判定对象存在 */
	isExist : function(obj, errMsg) {
		if (obj) {
			console.log(errMsg);
			return true;
		}
		return false;
	},
	/* 给指定字符串补0 */
	addZero : function(str, len) {
		var len = len - ("" + str).length
		var zeroStr = "";
		for (var i = 0; i < len; i++) {
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
		var body = document.body || document.documentElement, style = body.style, vendor = [
				'Webkit', 'Ms', 'O' ], i = 0;

		while (i < vendor.length) {
			if (typeof style[vendor[i] + 'Transition'] === 'string') {
				return vendor[i];
			}
			i++;
		}
	},
	getAnimEndEvent : function() {
		var prefix = this.getVendorPrefix() || "";
		var animEndEventName = this.animEndEventNames[prefix + 'Animation'];
		return animEndEventName;
	},
	useAnim : function(obj, anim, inOrOut, callback) {
		var animEndEvent = util.getAnimEndEvent();
		if (inOrOut == "in") {
			obj.show();
		}
		obj.addClass(anim);
		obj.on(animEndEvent, function() {
			obj.off(animEndEvent);
			if (callback) {
				callback();
			}
			if (inOrOut == "out") {
				obj.hide();
			}
		})
	},
	createMsgSpace : function(title, msg, typeClass, delay) {
		var $dialog = $("<div />").addClass("dialog " + typeClass).appendTo(
				$("body"));
		$("<p />").text(title + msg).appendTo($dialog);
		$dialog.addClass("dialog-show");
		$dialog.data("class", $dialog.attr("class"));
		$dialog.addClass("fadeIn");
		var anEvent = util.getAnimEndEvent();
		$dialog.on(anEvent, function() {
			$dialog.off(anEvent);
			setTimeout(function() {
				$dialog.attr("class", $dialog.data("class") + " fadeOut");
				$dialog.on(anEvent, function() {
					$dialog.remove();
				});
			}, delay);
		});
		return $dialog;
	},
	errMsg : function(msg, delay) {
		/* 弹出一个很快消失的对话框 */
		util.createMsgSpace("", msg, "error", delay);
	},
	successMsg : function(msg, delay) {
		/* 弹出一个 很快消失的 成功的信息框 */
		util.createMsgSpace("", msg, "success", delay);
	},
	fileUpload : function(url, files) {
		var genDataByFiles = function(_files) {
			// 验证_files的每一个对象是否为OFrame对象 或 js 对象 或者 自己扩展的of对象
			if (typeof FormData == "undefined") {

			} else {
				data = new FormData();
				// 判定_files 是否为数组
				if (util.isArray(_files)) {
					$.each(_files, function(i, v) {
						data.append('data' + i, v.files[0]);
					})
				} else {
					data.append('data', files.files[0]);
				}

			}
			return data;
		}
		var data = genDataByFiles(files);
		var request = $.ajax({
			url : url,
			type : 'POST',
			data : data,
			processData : false,
			contentType : false
		});

		var res = util.decode(request.responseText);
		if (util.cRS(res)) {
			return res;
		}

		return false;
	},
	getAttrByUrl : function(attrName) {
		var reg = new RegExp("(^|&)" + attrName + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg); if (r != null)
		return unescape(r[2]); return null;
	},
	setTouchRight : function(obj, callback) {
		if (!obj instanceof jQuery) {
			return;
		}
		obj.on("swiperight", callback);
	},
    isPC :function() {
        var userAgentInfo = navigator.userAgent;
        var Agents = ["Android", "iPhone",
            "SymbianOS", "Windows Phone",
            "iPad", "iPod"];
        var flag = true;
        for (var v = 0; v < Agents.length; v++) {
            if (userAgentInfo.indexOf(Agents[v]) > 0) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}

Timer = function() {
	this.init();
}

Timer.prototype = {
	init : function() {
		this.dom = $("<span />").addClass("timer");
	},
	second : function() {
		if (this.s > 0 && (this.s % 60) == 0) {
			this.m += 1;
			this.s = 0;
		}
		if (this.m > 0 && (this.m % 60) == 0) {
			this.h += 1;
			this.m = 0;
		}
		this.timerStr = timerStr = util.addZero(this.h, 2) + ":"
				+ util.addZero(this.m, 2) + ":" + util.addZero(this.s, 2) + "";
		this.dom.text(timerStr);
		this.s += 1;
	},
	setTime : function(h, m, s) {
		this.h = h;
		this.m = m;
		this.s = s;
	},
	getTimer : function() {
		return {
			"h" : this.h,
			"m" : this.m,
			"s" : this.s,
			str : this.timerStr
		};
	},
	start : function() {
		var that = this;
		that.second();
		this.timer = setInterval(function() {
			that.second();
		}, 1000);
	},
	pause : function() {
		clearInterval(this.timer);
	}
}