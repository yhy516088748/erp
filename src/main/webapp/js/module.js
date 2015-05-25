/**
 * Created by Administrator on 15-4-23.
 */
/**
 * Created by Administrator on 15-4-17.
 */

/* 打卡 */
PunchClock = function (parent) {
    this.parent = parent;
    this.init();
}

PunchClock.prototype = {
    init: function () {
        this.punchClock = $("<div />").addClass("punch-clock");
        this.createSpace("考勤", "blue", "white");
        this.punchClock.appendTo(this.parent);
    },
    /* 初始化 */
    /* 将按钮通过数据进行确切的位置放置 */
    createSpace: function (title, titleColor, bodyColor) {
        var portLet = new PortLet(this.punchClock);
        portLet.setTitle(title).setColor(titleColor, bodyColor);
        var div = $("<div />").addClass("punch-clock-body");
        var childDiv = $("<div />").addClass("punch-clock-body-child").appendTo(div);

        var res = util.getJson("erp/getSigninMonth.do");
        var obj;
        if (res) {
            obj = res.List;
        } else {
            return;
        }
        var calendar = new Calendar(childDiv);
        calendar.setData(obj);
        portLet.setBody(div);
        return portLet;
    }
}

UserManager = function (parent) {
    this.parent = parent;
    this.init();
}

UserManager.prototype = {
    init: function () {
        this.punchClock = $("<div />").addClass("user-manager");
        this.createSpace("用户管理", "blue", "white");
        this.punchClock.appendTo(this.parent);
    },
    /* 初始化 */
    /* 将按钮通过数据进行确切的位置放置 */
    createSpace: function (title, titleColor, bodyColor) {
        var portLet = new PortLet(this.punchClock);
        portLet.setTitle(title).setColor(titleColor, bodyColor);
        var div = $("<div />").addClass("user-manager-body");
        var childDiv = $("<div />").addClass("user-manager-body-child").appendTo(div);
        var table = new Ui_DataTable(childDiv);
        table.setControlFlag(true);
        table.setThead(
            {
                name: "姓名",
                idNumber: "证件号",
                gender: "性别",
                birthday: "生日",
                email: "邮箱",
                phone: "联系电话",
                address: "住址",
                zipCode: "邮编",
                nativePlace: "籍贯",
                groups: "部门",
                position: "职务",
                loginName: "登陆账号",
                password: "登陆密码",
                gjjAccount: "公积金账号",
                sbAccount: "社保账号",
                workDate: "参加工作时间",
                isMarry: "是否已婚",
                isInsurance: "是否保险",
                isPOInsurance: "是否配偶保险",
                isClild: "是否子女",
                isClildInsurance: "是否子女保险",
                Education: "学历",
                Professional: "专业",
                workStartTime: "标准上班时间",
                workEndTime: "标准下班时间",
                ContractStartDate: "合同起始日期",
                ContractEndDate: "合同失效日期",
                remark: "备注",
                userid: ""
            }
        );
        var res = util.getJson("erp/getUserList.do");
        //table.setDeleteAction("erp/delUser.do");
        table.setTbody(res.List);
        portLet.setBody(div);
        return portLet;
    }
}

UserInfo = function (parent) {
    this.parent = parent;
    this.init();
}

UserInfo.prototype = {
    init: function () {
        this.userinfo = $("<div />").addClass("user-info").appendTo(this.parent);
        this.createTitle();
        this.createBody();
    },
    createTitle: function () {
        var $title = $("<div />").addClass("user-info-title").appendTo(this.userinfo);
        this.$titleH3 = $h3 = $("<h3 />").addClass("page-title").appendTo($title);
    },
    createBody: function () {
        var $body = $("<div />").addClass("user-info-body row-fluid profile").appendTo(this.userinfo);

        /* 此地有一个 tab 页*/
        var $tab = new Ui_Tab($body);
        $tab.setMenu(new Array(
            {text: "Employee"},
            {text: "Account"}
        ));

        this.$tab = $tab;
    },
    createOne: function (titleData, data, allData) {
        var $div = $("<div />").addClass("row-fluid");
        /* 图片 信息 */
        var $imgUl = $("<ul />").addClass("profile-classic unstyle span3").appendTo($div);
        var $imgLi = $("<li />").appendTo($imgUl);
        $("<img />").attr("src", "img/profile-img.png").appendTo($imgLi);
        $("<a />").attr("href", "#").addClass("profile-edit").text("Edit").appendTo($imgLi);

        var $cdiv = $("<div />").addClass("row-fluid span9").appendTo($div);

        this.changeDataObj = {};
        this.changeDataObj.userid = allData.userid;

        /* 个人信息 */
        var $tab = new Ui_Tab($cdiv);

        $tab.setMenu(new Array(
            {text: "Personal"},
            {text: "Other"},
            {text: "Family"},
            {text: "Work"}
        ));

        $tab.setContent(new Array(
            this.createTitleSpace(titleData, data),
            this.getOneTwoSpace(allData),
            this.getTwoSpace(allData),
            this.getThreeSpace(allData)
        ));

        var $editLi = $("<div />").appendTo($cdiv);
        var $a = $("<a><i class='icon-pencil'></i>Save Changes</a>").addClass("btn bgcolor-green").appendTo($editLi);

        var that = this;
        $a.click(function () {
            var res = util.postJson("addUser.do", that.getChangeDatas());
            $("#scroll").click();
        });

        return $div;
    },
    getChangeDatas: function () {
        var newObj = {};
        $.each(this.changeDataObj, function (k, o) {
            if (o instanceof  jQuery) {
                var value = o.val();
                if (value == "Yes") {
                    value = true;
                } else if (value == "No") {
                    value = false;
                }
                newObj[k] = value;
            } else {
                newObj[k] = o;
            }
        })
        return newObj;
    },
    createTitleSpace: function (titleData, data) {
        var $userUl = $("<ul />").addClass("profile-classic unstyle");
        this.$titleH3.text(data.name);
        $("<small />").text("information").appendTo(this.$titleH3);
        var that = this;
        $.each(data, function (k, v) {
            var $li = $("<li />").addClass("controls").appendTo($userUl);
            $("<label>" + titleData[k] + "</label>").appendTo($li);
            var text = $("<input type='text' />").addClass("text span12").attr("value", v).appendTo($li);
            //$("<li><span>"+ titleData[k] + "</span>" + v + "</li>").appendTo($userUl);
            that.changeDataObj[k] = text;
        });
        return $userUl;
    },
    createSpace: function (titleData, data) {
        var $div = $("<div />").addClass("row-fluid");
        /* 个人信息 */
        var $userUl = $("<ul />").addClass("profile-classic unstyle span12").appendTo($div);
        var that = this;
        $.each(data, function (k, v) {
            var $li = $("<li />").addClass("controls").appendTo($userUl);
            $("<label>" + titleData[k] + "</label>").appendTo($li);
            var text = $("<input type='text' />").addClass("text span12").attr("value", v).appendTo($li);
            that.changeDataObj[k] = text;
        });
        return $div;
    },
    createAccountSpace: function (titleData, data) {
        /* 账户密码 信息 以及邮箱 信息*/
        var $div = $("<div />").addClass("row-fluid");

        /* 账户安全信息 */
        var $userUl = $("<ul />").addClass("profile-classic unstyle span12").appendTo($div);
        /* 登陆账户 */
        $("<li><span>" + titleData.loginName + "</span>" + data.loginName + "</li>").appendTo($userUl);

        var password = $("<li />").addClass("controls").appendTo($userUl);
        $("<label>" + titleData.oldPassword + "</label>").appendTo(password);
        var $oldPassword = $("<input type='password' />").addClass("text span12").appendTo(password);

        var password = $("<li />").addClass("controls").appendTo($userUl);
        $("<label>" + titleData.newPassword + "</label>").appendTo(password);
        var $newPassword = $("<input type='password' />").addClass("text span12").appendTo(password);

        var password = $("<li />").addClass("controls").appendTo($userUl);
        $("<label>" + titleData.rePassword + "</label>").appendTo(password);
        var $rePassword = $("<input type='password' />").addClass("text span12").appendTo(password);

        var $controls = $("<li />").addClass("controls").appendTo($userUl);
        var $a = $("<a><i class='icon-pencil'></i>Save Changes</a>").addClass("btn bgcolor-green").appendTo($controls);
        return $div;
    },
    setData: function (userid) {
        var data = {};
        if (userid) {
            data.userid = userid;
        }
        var res = util.postJson("erp/getUserInfo.do", data);
        if (!res) {
            return;
        }
        /*
         var fileName = $("<input />").attr("type","file").appendTo($imgdiv);
         fileName.change(function(){
         util.fileUpload("http://10.10.10.132:8080/erp/getUserInfo.do",fileName[0]);
         })
         */
        this.$tab.setContent(new Array(
            this.getOneSpace(res.Info),
            this.getFourSpace(res.Info)
        ));
    },
    getOneSpace: function (data) {
        /* 个人信息 */
        var titleData = {
            name: "User Name :",
            idNumber: "Id Number :",
            gender: "Gender :",
            birthday: "Birthday :",
            workYear: "Work Year :",
            nativePlace: "Native Place :",
            education: "Education :",
            professional: "Professional :",
            remark: "About :"
        }

        var newData = {
            name: data.name,
            idNumber: data.idNumber,
            gender: data.gender,
            birthday: data.birthday,
            workYear: data.workYear,
            nativePlace: data.nativePlace,
            education: data.education,
            professional: data.professional,
            remark: data.remark
        }

        return this.createOne(titleData, newData, data);
    },
    getOneTwoSpace: function (data) {
        var titleData = {
            email: "Email :",
            phone: "Telephone :",
            address: "Address :",
            zipCode: "Zip Code :",
            bankName: "Bank Name :",
            bankNumber: "Bank Number :",
            gjjAccount: "Accumulation Fund :",
            sbAccount: "Social Security Numbers : "
        }
        var data = {
            email: data.email,
            phone: data.phone,
            address: data.address,
            zipCode: data.zipCode,
            bankName: data.bankName,
            bankNumber: data.bankNumber,
            gjjAccount: data.gjjAccount,
            sbAccount: data.sbAccount
        }
        return this.createSpace(titleData, data);
    },
    getTwoSpace: function (data) {
        /* 家庭信息 */
        var titleData = {
            isMarry: "Whether Married :",
            isInsurance: "Whether Insurance :",
            isPOInsurance: "Whether The Spouse Insurance :",
            isChild: "Whether Children :",
            isChildInsurance: "Whether The Children Insurance :"
        };

        var getYesOrNo = function (flag) {
            return flag ? "Yes" : "No";
        }

        var data = {
            isMarry: getYesOrNo(data.isMarry),
            isInsurance: getYesOrNo(data.isInsurance),
            isPOInsurance: getYesOrNo(data.isPOInsurance),
            isChild: getYesOrNo(data.isChild),
            isChildInsurance: getYesOrNo(data.isChildInsurance)
        }

        return this.createSpace(titleData, data);
    },
    getThreeSpace: function (data) {
        var titleData = {
            departments: "Departments :",
            positions: "Positions :",
            workStartTime: "Work Start Time :",
            workEndTime: "Work End Time : ",
            contractStartDate: "Contract Start Date :",
            contractEndDate: "Contract End Date :"
        };

        var data = {
            departments: data.departments,
            positions: data.positions,
            workStartTime: data.workStartTime,
            workEndTime: data.workEndTime,
            contractStartDate: data.contractStartDate,
            contractEndDate: data.contractEndDate
        }

        return this.createSpace(titleData, data);
    },
    getFourSpace: function (data) {
        var titleData = {
            loginName: "Login Name :",
            oldPassword: "Pass Word :",
            newPassword: "New Pass Word :",
            rePassword: "Re Pass Word :"
        }
        var data = {
            loginName: data.loginName,
            oldPassword: "",
            newPassword: "",
            rePassword: ""
        }
        return this.createAccountSpace(titleData, data);
    }
}

GroupManager = function () {

}

GroupManager.prototype = {}

RoleManager = function () {

}

RoleManager.prototype = {}

MenuManager = function () {

}

MenuManager.prototype = {}

