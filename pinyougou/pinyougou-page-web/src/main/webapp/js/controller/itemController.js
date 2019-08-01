var app = new Vue({
    el: "#app",
    data: {
        num:1,//商品的购买数量 
		specificationItems:JSON.parse(JSON.stringify(skuList[0].spec)),
		sku:skuList[0]
    },
    methods: {
		addNum:function(num){
			num = parseInt(num);
			this.num+=num;//加或者减
			if(this.num<=1){
				this.num=1;
			}
		},
        selectSpecification:function(name,value) {
            this.$set(this.specificationItems,name,value);
        },
        isSelected:function(name,value) {
            if(this.specificationItems[name] == value) {
                return true;
            } else {
                return false;
            }
        },
        search:function () {
            for(var i=0;i<skuList.length;i++){
                var object = skuList[i];
                if(JSON.stringify(this.specificationItems)==JSON.stringify(skuList[i].spec)){
                    console.log(object);
                    this.sku=object;
                    break;
                }
            }
        },
        addGoodsToCartList:function () {
            /*axios.get("http://localhost:9105/cart/getCasName.shtml"
            ).then(function (response) {
                alert(response.data)*/
                /*if (response.data.success) {
                    window.location.href = "http://localhost:9107/cart.html"
                } else {
                    alert(response.data.message)
                }*/
            // })
            axios.get("http://localhost:9107/cart/addGoodsToCartList.shtml",{
                params:{
                    itemId:this.sku.id,
                    num:this.num
                },
                withCredentials:true
            }).then(function (response) {
                if (response.data.success) {
                    window.location.href = "http://localhost:9107/cart.html"
                } else {
                    alert(response.data.message)
                }
            })
        },
        addFootmark:function () {
            //console.log(app.sku)
            axios.get('http://localhost:9106/user/addFootmark.shtml',{
                params:{
                    itemId:this.sku.id,
                },
                withCredentials:true
            }).then(function () {
                //console.log(response);/
                if(response.data.success){
                    console.log(response.data.message)
                } else {
                    console.log(response.data.message)
                }
            }).catch(function (error) {
                console.log("1231312131321");
            });
        }

    },

    //钩子函数 初始化了事件和
    created: function () {
        this.addFootmark();
    }

})