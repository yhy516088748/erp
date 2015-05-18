/**
 * Created by Administrator on 15-4-14.
 */

/* 各种初始化 */
$(function() {
	/* 基础数据初始化 */
	var
	/* 重要的参数userid */
	// userid = util.getAttrByUrl("userid"),
	userid = "8a8a8a044d4b26b9014d4b3464800000",
	/* 用户基础信息的初始化参数 */
	userInfoObj,

	/* 计时器的初始化 数据 */
	timerObj,

	/* 菜单的初始化数据 */
	navBarObj;

	var res = util.postJson("erp/getUserInfo.do", {
		userid : userid
	});

	if (res) {
		userInfoObj = res.Info;
	}

	var res = util.postJson("erp/getToday.do");

	if (res) {
		timerObj = res.Timer;
	}

	var res = util.postJson("erp/getMenuList.do", {});
	if (res) {
		navBarObj = res.navbar;
	}

	/* 用户信息 */
	userObj = {

		userid : userid,

		// 正规上班时间
		specifiedTime : [ userInfoObj.workStartTime, userInfoObj.workEndTime ],

		// 计时器的必要参数 对应计时器标签
		timer : {
			h : timerObj.hour,
			m : timerObj.minute,
			s : timerObj.second
		},

		// 菜单的必要参数 对应菜单标签
		navbar : navBarObj,

		defaultModule : "PunchClock"
	}

	// 放置各种 全局物品

	// conform 窗口的布置 带背景黑灰色 的conform
	conform = new Conform($("body"));

	// 右边直接覆盖 Main的conform
	conformMain = new ConformMain($("#content-main"));

	// 计时器
	// 通过获取到的时间 进行timer初始化
	timer = new Timer();
	timer.setTime(userObj.timer.h, userObj.timer.m, userObj.timer.s);
	timer.start();

	/* 菜单 */

	var navBar = new NavBar($("#content-nav"));
	navBar.setData(userObj.navbar);
	navBar.setMenuControl($("#header-nav"));

	/* 消息弹出框 */
	// var message = new Message($("body"));
	// message.setAction("1").setDelay(60000);
	// message.start();
	/* 默认模块 */
	new window[userObj.defaultModule]($("#content-container"));
	// var userInfo = new UserInfo($("#content-container"));
	// userInfo.setData();
})