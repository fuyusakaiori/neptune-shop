/**
 * 显示所有商品
 */
$(function() {
    // 获取此店铺下的商品列表的URL
    let initURL = '/o2o/shop-management/goods/list?pageIndex=1&pageSize=999';
    // 商品下架URL
    let updateURL = '/o2o/shop-management/goods/update';
    getList();

    function getList() {
        // 从后台获取此店铺的商品列表
        $.getJSON(initURL, function(data) {
            if (data.success) {
                let goodsList = data.goodsList;
                let listHTML = '';
                goodsList.map(function(good, index) {
                    let status = good.status === 0 ? '上架' : '下架';
                    let contrary = 1 - good.status;
                    console.log(good.status);
                    listHTML += '' + '<div class="row row-product"><div class="col-10">' + (index + 1) + '</div>'
                        + '<div class="col-20">' + good.goodsName + '</div>'
                        + '<div class="col-20">' + good.category.goodsCategoryName + '</div>'
                        + '<div class="col-10">' + good.normalPrice + '</div>'
                        + '<div class="col-10">' + good.promotionPrice + '</div>'
                        + '<div class="col-10">0</div>'
                        + '<div class="col-20">'
                        + '<a href="#" class="edit" data-id="' + good.goodsId + '" data-status="' + good.status + '">编辑</a>'
                        + '<a href="#" class="status" data-id="' + good.goodsId + '" data-status="' + contrary + '">' + status + '</a>'
                        + '<a href="#" class="preview" data-id="' + good.goodsId + '" data-status="' + good.status + '">预览</a>'
                        + '</div>'
                        + '</div>';
                });
                // 将拼接好的信息赋值进html控件中
                $('.product-wrap').html(listHTML);
            }
        });
    }

    // 给每个按钮绑定相应的事件
    $('.product-wrap').on('click', 'a', function(e) {
        var target = $(e.currentTarget);
        if (target.hasClass('edit')) {
            window.location.href = '/o2o/shop-admin/goods-edit?id=' + e.currentTarget.dataset.id;
        } else if (target.hasClass('status')) {
            changeItemStatus(e.currentTarget.dataset.id, e.currentTarget.dataset.status);
        } else if (target.hasClass('preview')) {
            // 如果有class preview则去前台展示系统该商品详情页预览商品情况
            window.location.href = '#';
        }
    });

    // 改变商品状态
    function changeItemStatus(id, status) {
        let goods = {};
        goods.goodsId = id;
        goods.status = status;
        $.confirm('确定么?', function() {
            $.ajax({
                url : updateURL,
                type : 'POST',
                data : {
                    goods : JSON.stringify(goods),
                    status : true
                },
                dataType : 'json',
                success : function(data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getList();
                    } else {
                        $.toast('操作失败！');
                    }
                }
            });
        });
    }
});