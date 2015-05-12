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

/*
 this.data = data = {
 specifiedTime: ["08:30:00", "17:00:00"],
 realTime: ["08:30:00"],
 flag: false
 }
 */

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
        var calendar = new Calendar(childDiv);
        var obj = {
            specifiedTime: ["08:30", "17:00"],
            days: [{
                realTime: ["08:30", "17:00"], flag: false
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: true, dayWhat: "字符串"
            }, {}, {}, {
                realTime: ["08:30", "17:00"], flag: true
            }, {
                realTime: ["08:30", "17:00"], flag: false
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: true, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {}, {}, {
                realTime: ["08:30", "17:00"], flag: false
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: true, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {}, {}, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: true, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: false, dayWhat: "字符串"
            }, {
                realTime: ["08:30", "17:00"], flag: true
            }, {
                realTime: ["08:30", "17:00"], flag: false
            }, {}, {
                realTime: ["08:30", "17:00"], flag: false
            }]
        }
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
                name: "员工姓名",
                idNumber: "证件号",
                gender: "性别",
                birthday: "生日",
                Email: "邮箱",
                telephone: "联系电话",
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
                remark: "备注"
            }
        );
        table.setTheadConfig(new Array({
                title: "人员",
                keys: ["name", "idNumber", "gender", "birthday", "Email", "telephone", "address", "zipCode", "nativePlace"]
            },
            {title: "附加", keys: ["isMarry", "isInsurance", "isPOInsurance", "isClild", "isClildInsurance"]},
            {title: "安全", keys: ["loginName", "password"]},
            {title: "岗位", keys: ["groups", "position"]},
            {
                title: "人事",
                keys: ["gjjAccount", "sbAccount", "workDate", "Education", "Professional", "workStartTime", "workEndTime", "workEndTime", "ContractStartDate", "ContractEndDate"]
            }
        ));

        table.setTbody(new Array(
            {
                /*
                name: "虞霆",
                idNumber: "310108198707060556",
                gender: "男",
                birthday: "1987-07-06",
                Email: "yuting@sh-tpa.com",
                telephone: "13764976571",
                address: "上海市闸北区谈家桥路152号901",
                zipCode: "200070",
                nativePlace: "上海",
                */
                groups: "IT部",
                position: "程序猿",
                /*
                loginName: "yuting",
                password: "yuting",
                */
                /*
                gjjAccount: "1234567890",
                sbAccount: "1234567890",
                workDate: "2010年10月",
                isMarry: "是",
                isInsurance: "是",
                isPOInsurance: "是",
                isClild: "是",
                isClildInsurance: "是",
                Education: "本科",
                Professional: "计算机科学与技术",
                */
                workStartTime: "08:30",
                workEndTime: "17:00",
                ContractStartDate: "2010年10月",
                ContractEndDate: "2018年10月"
            }
        ))
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
        var $h3 = $("<h3 />").text("Employee").addClass("page-title").appendTo($title);
        $("<small />").text("information").appendTo($h3);
    },
    createBody: function () {
        var $body = $("<div />").addClass("user-info-body row-fluid profile").appendTo(this.userinfo);

        /* 此地有一个 tab 页*/
        var $tab = new Ui_Tab($body);
        $tab.setMenu(new Array(
            {text: "Personal"},
            {text: "Family"},
            {text: "Work"},
            {text: "Safe Account"}
        ));

        this.$tab = $tab;
    },
    createOne : function(titleData,data){
        var $div = $("<div />").addClass("row-fluid");
        /* 图片 信息 */
        var $imgdiv = $("<div />").addClass("profile-classic span2").appendTo($div);
        $("<img />").attr("src","img/profile-img.png").appendTo($imgdiv);
        $("<a />").attr("href","#").addClass("profile-edit").text("Edit").appendTo($imgdiv);

        /* 个人信息 */
        var $userUl = $("<ul />").addClass("profile-classic unstyle span10").appendTo($div);
        $.each(data,function(k,v){
            $("<li><span>"+ titleData[k] + "</span>" + v + "</li>").appendTo($userUl);
        });
        return  $div;
    },
    createTwo : function(titleData,data){
        var $div = $("<div />").addClass("row-fluid");
        /* 图片 信息 */
        var $imgdiv = $("<div />").addClass("profile-classic span2").appendTo($div);
        $("<img />").attr("src","img/profile-img.png").appendTo($imgdiv);
        $("<a />").attr("href","#").addClass("profile-edit").text("Edit").appendTo($imgdiv);

        /* 个人信息 */
        var $userUl = $("<ul />").addClass("profile-classic unstyle span10").appendTo($div);
        $.each(data,function(k,v){
            $("<li><span>"+ titleData[k] + "</span>" + v + "</li>").appendTo($userUl);
        });
        return  $div;
    },
    createThree : function(titleData,data){
        var $div = $("<div />").addClass("row-fluid");
        /* 图片 信息 */
        var $imgdiv = $("<div />").addClass("profile-classic span2").appendTo($div);
        $("<img />").attr("src","img/profile-img.png").appendTo($imgdiv);
        $("<a />").attr("href","#").addClass("profile-edit").text("Edit").appendTo($imgdiv);

        /* 个人信息 */
        var $userUl = $("<ul />").addClass("profile-classic unstyle span10").appendTo($div);
        $.each(data,function(k,v){
            $("<li><span>"+ titleData[k] + "</span>" + v + "</li>").appendTo($userUl);
        });
        return  $div;
    },
    createFour : function(titleData,data){
        /* 账户密码 信息 以及邮箱 信息*/
        var $div = $("<div />").addClass("row-fluid");
        /* 图片 信息 */
        var $imgdiv = $("<div />").addClass("profile-classic span2").appendTo($div);
        $("<img />").attr("src","img/profile-img.png").appendTo($imgdiv);
        $("<a />").attr("href","#").addClass("profile-edit").text("Edit").appendTo($imgdiv);

        /* 账户安全信息 */
        var $userUl = $("<ul />").addClass("profile-classic unstyle span10").appendTo($div);
        /* 登陆账户 */
        $("<li><span>"+ titleData.loginName + "</span>" + data.loginName + "</li>").appendTo($userUl);

        var password = $("<li />").addClass("controls").appendTo($userUl);
        $("<label>"+ titleData.oldPassword + "</label>").appendTo(password);
        var $oldPassword = $("<input type='password' />").addClass("text span12").appendTo(password);

        var password = $("<li />").addClass("controls").appendTo($userUl);
        $("<label>"+ titleData.newPassword + "</label>").appendTo(password);
        var $newPassword = $("<input type='password' />").addClass("text span12").appendTo(password);

        var password = $("<li />").addClass("controls").appendTo($userUl);
        $("<label>"+ titleData.rePassword + "</label>").appendTo(password);
        var $rePassword = $("<input type='password' />").addClass("text span12").appendTo(password);

        var $controls = $("<li />").addClass("controls").appendTo($userUl);
        $("<button />").addClass("btn bgcolor-gray").text("重置").appendTo($controls);
        $("<button />").addClass("btn bgcolor-blue").text("提交").appendTo($controls);
        return  $div;
    },
    setData : function(userid){
        var data = null;
        if (userid){
            data.userid = userid;
        }
        var res = util.service("/erp/getUserInfo.do",data);
        if (!res){
            //return;
        }
        var $body = $("<div />");
        var $imgdiv = $("<div />").addClass("profile-classic span2").appendTo($body);
        $("<img />").attr("src","img/profile-img.png").appendTo($imgdiv);
        $("<a />").attr("href","#").addClass("profile-edit").text("Edit").appendTo($imgdiv);
        var fileName = $("<input />").attr("type","file").appendTo($imgdiv);
        fileName.change(function(){
            util.fileUpload("/erp/uploadKJQImg.do",fileName[0]);
        })
        this.$tab.setContent(new Array(
            $body
            //this.getOneSpace(res.Info),
            //this.getTwoSpace(res.Info),
            //this.getThreeSpace(res.Info),
            //this.getFourSpace(res.Info)
        ));
    },
    getOneSpace : function(data){
        /* 个人信息 */
        var titleData = {
            name: "User Name :",
            idNumber: "Id Number :",
            gender: "Gender :",
            birthday: "Birthday :",
            email: "Email :",
            phone: "Telephone :",
            workYear: "Work Year :",
            address: "Address :",
            zipCode: "Zip Code :",
            nativePlace: "Native Place :",
            education: "Education :",
            professional: "Professional :",
            bankName : "Bank Name :",
            bankNum : "Bank Number :",
            gjjAccount: "Accumulation Fund :",
            sbAccount: "Social Security Numbers : ",
            remark : "About :"
        }
        var data = {
            name: data.name,
            idNumber: data.idNumber,
            gender: data.gender,
            birthday: data.birthday,
            email: data.email,
            phone: data.phone,
            workYear : data.workYear,
            address: data.address,
            zipCode: data.zipCode,
            nativePlace: data.nativePlace,
            education: data.education,
            professional: data.professional,
            bankName : data.bankName,
            bankNum : data.bankNum,
            gjjAccount : data.gjjAccount,
            sbAccount : data.sbAccount,
            remark : data.remark
        }

        return this.createOne(titleData,data);
    },
    getTwoSpace : function(data){
        /* 家庭信息 */
        var titleData = {
            isMarry: "Whether Married :",
            isInsurance: "Whether Insurance :",
            isPOInsurance: "Whether The Spouse Insurance :",
            isClild: "Whether Children :",
            isClildInsurance: "Whether The Children Insurance :"
        };

        var getYesOrNo = function(flag){
            return flag ? "Yes":"No";
        }

        var data = {
            isMarry: getYesOrNo(data.isMarry),
            isInsurance: getYesOrNo(data.isInsurance),
            isPOInsurance: getYesOrNo(data.isPOInsurance),
            isClild: getYesOrNo(data.isClild),
            isClildInsurance: getYesOrNo(data.isClildInsurance)
        }

        return this.createTwo(titleData,data);
    },
    getThreeSpace : function(data){
        var titleData = {
            department: "Department :",
            position: "Position :",
            workStartTime: "Work Start Time :",
            workEndTime: "Work End Time : ",
            contractStartDate: "Contract Start Date :",
            contractEndDate: "Contract End Date :"
        };
        var data = {
            department: data.department,
            position: data.position,
            workStartTime: data.workStartTime,
            workEndTime: data.workEndTime,
            contractStartDate: data.contractStartDate,
            contractEndDate: data.contractEndDate
        }
        return this.createThree(titleData,data);
    },
    getFourSpace : function(data){
        var titleData = {
            loginName: "登录账户 :",
            oldPassword: "登录密码 :",
            newPassword : "新的密码 :",
            rePassword : "重复密码 :"
        }
        var data = {
            loginName: "yuting",
            oldPassword : "",
            newPassword : "",
            rePassword : ""
        }
        return this.createFour(titleData,data);
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

