$(function() {
    let loading = false;
    // 分页允许返回的最大条数，超过此数则禁止访问后台
    let maxItems = 999;
    // 页大小 和 页号
    let pageSize = 3, pageIndex = 1;
    // 根据查询条件获取相应的店铺列表
    let searchURL = '/o2o/index/search';
    // 获取店铺类别列表以及区域列表的URL
    let initURL = '/o2o/index/search-init';

    // 是否存在父类型编号: 如果没有, 就会显示全部子类型, 如果有, 就只显示父类型下的所有子类型
    let parentId = getParameter('id');
    // 是否选择了子类
    let selectedParent = false;

    if (parentId){
        selectedParent = true;
    }
    // 其余查询店铺的条件
    let areaId = '';
    let id = '';
    let name = '';

    // 渲染出店铺类别列表以及区域列表以供搜索
    initSearchCondition();
    // 显示店铺信息
    setShopInfo(pageSize, pageIndex);

    function initSearchCondition() {
        let url = initURL + '?parentId=' + parentId;
        $.getJSON(url, function(data) {
            if (data.success) {
                // 获取后台返回过来的店铺类别列表
                let categories = data.categories;
                let categoryHTML = '';
                // 店铺类型
                categoryHTML += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
                categories.map(function(category, index) {
                        categoryHTML += '<a href="#" class="button" data-category-id=' + category.shopCategoryId + '>'
                            + category.categoryName + '</a>';
                });
                $('#shoplist-search-div').html(categoryHTML);

                // 区域类型
                let areaHTML = '<option value="">全部街道</option>';
                let areaList = data.areas;
                areaList.map(function(area, index) {
                    areaHTML += '<option value="' + area.campusAreaId + '">' + area.campusAreaName + '</option>';
                });
                $('#area-search').html(areaHTML);
            }
        });
    }

    /**
     * 获取分页展示的店铺列表信息
     *
     * @param pageSize
     * @param pageIndex
     * @returns
     */
    function setShopInfo(pageSize, pageIndex) {
        // 拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
        let url = searchURL + '?pageIndex=' + pageIndex
            + '&pageSize=' + pageSize
            + '&parentId=' + parentId
            + '&areaId=' + areaId
            + '&id=' + id
            + '&name=' + name;
        // 设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
        loading = true;
        // 访问后台获取相应查询条件下的店铺列表
        $.getJSON(url, function(data) {
            if (data.success) {
                // 获取当前查询条件下店铺的总数
                maxItems = data.count;
                let html = '';
                // 遍历店铺列表，拼接出卡片集合
                data.shops.map(function(shop, index) {
                    html += '' + '<div class="card" data-shop-id="'
                        + shop.shopId + '">' + '<div class="card-header">'
                        + shop.shopName + '</div>'
                        + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + shop.imageURL + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + shop.description
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(shop.updateTime)
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
                    return;
                } else {
                    $('.infinite-scroll-preloader').show();
                }
                // 否则页码加1，继续load出新的店铺
                pageIndex += 1;
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
        setShopInfo(pageSize, pageIndex);
    });

    // 点击店铺的卡片进入该店铺的详情页
    $('.shop-list').on('click', '.card', function(e) {
        window.location.href = '/o2o/index/shop-detail?id=' + e.currentTarget.dataset.shopId;
    });

    // 选择新的店铺类别之后，重置页码，清空原先的店铺列表，按照新的类别去查询
    $('#shoplist-search-div').on('click', '.button',
        function(e) {
            if (parentId && selectedParent) {// 如果传递过来的是一个父类下的子类
                id = e.target.dataset.categoryId;
                // 若之前已选定了别的category,则移除其选定效果，改成选定新的
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    id = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                // 由于查询条件改变，清空店铺列表再进行查询
                $('.list-div').empty();
                // 重置页码
                pageIndex = 1;
                setShopInfo(pageSize, pageIndex);
            } else {// 如果传递过来的父类为空，则按照父类查询
                parentId = e.target.dataset.categoryId;
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    parentId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                // 由于查询条件改变，清空店铺列表再进行查询
                $('.list-div').empty();
                // 重置页码
                pageIndex = 1;
                setShopInfo(pageSize, pageIndex);
            }

        });

    // 需要查询的店铺名字发生变化后，重置页码，清空原先的店铺列表，按照新的名字去查询
    $('#search').on('change', function(e) {
        name = e.target.value;
        $('.list-div').empty();
        pageIndex = 1;
        setShopInfo(pageSize, pageIndex);
    });

    // 区域信息发生变化后，重置页码，清空原先的店铺列表，按照新的区域去查询
    $('#area-search').on('change', function() {
        areaId = $('#area-search').val();
        $('.list-div').empty();
        pageIndex = 1;
        setShopInfo(pageSize, pageIndex);
    });

    // 点击后打开右侧栏
    $('#me').click(function() {
        $.openPanel('#panel-right-demo');
    });

    // 初始化页面
    $.init();
});
