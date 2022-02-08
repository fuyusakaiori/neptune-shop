/**
 * 首页
 */

$(function () {
    let indexURL = '/o2o/index/init';
    // 获取头条列表和父类型店铺
    $.getJSON(indexURL, function (data) {
        if (data.success){
            // 轮播图信息
            let lines = data.lines;
            let headHTML = '';
            lines.map(function (line, index) {
                headHTML += '' + '<div class="swiper-slide img-wrap">'
                    + '<a href="' + line.link + '"><img class="banner-img" src="' + line.imageURL + '" alt="' + line.headlineName + '"></a>'
                    + '</div>';
            });
            console.log(headHTML);
            //将轮播图组赋值给前端HTML控件
            $('.swiper-wrapper').html(headHTML);
            //设定轮播图轮换时间为3秒
            $(".swiper-container").swiper({
                autoplay : 3000,
                //用户对轮播图进行操作时，是否自动停止autoplay
                autoplayDisableOnInteraction : false
            });

            // 店铺类型信息
            let categories = data.categories;
            let categoryHTML = '';
            categories.map(function (category, index) {
                console.log(category);
                categoryHTML += '<div class="col-50 shop-classify" data-category=' + category.shopCategoryId + '>' + '<div class="word">'
                    + '<p class="shop-title">' + category.categoryName + '</p>'
                    + '<p class="shop-desc">' + category.description + '</p>'
                    + '</div><div class="shop-classify-img-warp">'
                    + '<img class="shop-img" src="' + category.imageURL + '"></div>'
                    + '</div>';
            });
            console.log(categoryHTML);
            $('.row').html(categoryHTML);
        }else{
            console.log("获取信息失败! 错误信息: ");
        }

    });

    // 点击显示侧边栏
    $('#me').click(function() {
        $.openPanel('#panel-right-demo');
    });

    // 给每个图标添加点击事件
    $('.row').on('click', '.shop-classify', function(e) {
        // 父类型店铺的编号
        let id = e.currentTarget.dataset.category;
        window.location.href = '/o2o/index/shop-list?id=' + id;
    });
});
