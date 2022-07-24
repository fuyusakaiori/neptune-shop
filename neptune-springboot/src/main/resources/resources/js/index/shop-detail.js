$(function() {
    let loading = false;
    // 分页允许返回的最大条数，超过此数则禁止访问后台
    let maxItems = 20;
    // 默认一页返回的商品数
    let pageSize = 3;
    // 列出商品列表的URL
    let listUrl = '/o2o/index/search-goods-init';
    // 默认的页码
    let pageNum = 1;
    // 从地址栏里获取ShopId
    let shopId = getParameter('id');
    let categoryId = '';
    let goodsName = '';
    // 获取本店铺信息以及商品类别信息列表的URL
    let searchDivUrl = '/o2o/index/init-shop-detail?id=' + shopId;
    // 渲染出店铺基本信息以及商品类别列表以供搜索
    getSearchDivData();
    // 预先加载10条商品信息
    addItems(pageSize, pageNum);

    // 给兑换礼品的a标签赋值兑换礼品的URL，2.0讲解
    // $('#exchangelist').attr('href', '/o2o/frontend/awardlist?shopId=' +
    // shopId);
    // 获取本店铺信息以及商品类别信息列表
    function getSearchDivData() {
        let url = searchDivUrl;
        $.getJSON(url, function(data) {
            if (data.success) {
                let shop = data.shop;
                $('#shop-cover-pic').attr('src', shop.imageURL);
                $('#shop-update-time').html(new Date(shop.updateTime));
                $('#shop-name').html(shop.shopName);
                $('#shop-desc').html(shop.description);
                $('#shop-addr').html(shop.address);
                $('#shop-phone').html(shop.phone);
                // 获取后台返回的该店铺的商品类别列表
                let categories = data.categories;
                let html = '';
                // 遍历商品列表，生成可以点击搜索相应商品类别下的商品的a标签
                categories.map(function(category, index) {
                        html += '<a href="#" class="button" data-product-search-id=' + category.goodsCategoryId + '>' + category.goodsCategoryName + '</a>';
                    });
                // 将商品类别a标签绑定到相应的HTML组件中
                $('#shopdetail-button-div').html(html);
            }
        });
    }
    /**
     * 获取分页展示的商品列表信息
     *
     * @param pageSize
     * @param pageIndex
     * @returns
     */
    function addItems(pageSize, pageIndex) {
        // 拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
        let url = listUrl + '?pageIndex=' + pageIndex
            + '&pageSize=' + pageSize
            + '&categoryId=' + categoryId
            + '&goodsName=' + goodsName
            + '&id=' + shopId;
        // 设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
        loading = true;
        // 访问后台获取相应查询条件下的商品列表
        $.getJSON(url, function(data) {
            if (data.success) {
                // 获取当前查询条件下商品的总数
                maxItems = data.count;
                let html = '';
                // 遍历商品列表，拼接出卡片集合
                data.goodsList.map(function(goods, index) {
                    html += '' + '<div class="card" data-product-id='
                        + goods.goodsId + '>'
                        + '<div class="card-header">' + goods.goodsName
                        + '</div>' + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + goods.imageURL + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + goods.description
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(goods.updateTime)
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                // 将卡片集合添加到目标HTML组件里
                $('.list-div').append(html);
                // 获取目前为止已显示的卡片总数，包含之前已经加载的
                let total = $('.list-div .card').length;
                // 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
                if (total >= maxItems) {
                    // 隐藏提示符
                    $('.infinite-scroll-preloader').hide();
                } else {
                    $('.infinite-scroll-preloader').show();
                }
                // 否则页码加1，继续load出新的店铺
                pageNum += 1;
                // 加载结束，可以再次加载了
                loading = false;
                // 刷新页面，显示新加载的店铺
                $.refreshScroller();
            }
        });
    }

    // 下滑屏幕自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function() {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });
    // 选择新的商品类别之后，重置页码，清空原先的商品列表，按照新的类别去查询
    $('#shopdetail-button-div').on('click', '.button',
        function(e) {
            // 获取商品类别Id
            categoryId = e.target.dataset.productSearchId;
            if (categoryId) {
                // 若之前已选定了别的category,则移除其选定效果，改成选定新的
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    categoryId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                $('.list-div').empty();
                pageNum = 1;
                addItems(pageSize, pageNum);
            }
        });
    // 点击商品的卡片进入该商品的详情页
    $('.list-div').on('click', '.card',
        function(e) {
            window.location.href = '/o2o/index/goods-detail?id=' + e.currentTarget.dataset.productId;
        });
    // 需要查询的商品名字发生变化后，重置页码，清空原先的商品列表，按照新的名字去查询
    $('#search').on('change', function(e) {
        goodsName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });
    // 点击后打开右侧栏
    $('#me').click(function() {
        $.openPanel('#panel-right-demo');
    });
    $.init();
});
