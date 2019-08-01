let app = new Vue({
    el: "#app",
    data: {
        startDate:"2019-09-09",
        endDate:"2019-09-09",
        brandList:[],
        list:[],
        pageNo:1,
        pages:10
    },
    methods: {
        changeStartDate:function () {
            let startDate = new Date();
            let endDate = new Date();
            endDate.setTime(startDate.getTime() - 3600 * 1000 * 24 * 30);
            app.endDate = startDate.Format("yyyy-MM-dd");
            app.startDate = endDate.Format("yyyy-MM-dd");

        },
        getBrandList:function(){
            axios.get("/goods/getBrandList.shtml").then(function (response) {
                app.brandList = response.data;
                for (let i = 0; i < app.brandList.length; i++) {
                    app.brandList[i] = app.brandList[i].name;
                }
            })
        },
        queryGoodsStatistical:function (pageNo) {
            axios.get("/goods/queryGoodsStatistical.shtml?pageNo=" + pageNo + "&startDate=" + app.startDate + "&endDate=" + app.endDate).then(function (response) {
                app.list = response.data.list;
                app.pageNo=pageNo;
                app.pages=response.data.pages;
            })
        }
    },
    created: function () {
        window.setTimeout(function () {
            app.getBrandList();
            app.changeStartDate();
            app.queryGoodsStatistical(1);
        },1);
    }

});

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };

    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o){
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }

    return fmt;
}