/**
 * <h2>负责商品类型页面的管理</h2>
 */

$(function () {
    let queryURL = '/o2o/shop-management/goods-category/find';
    let insertURL = '/o2o/shop-management/goods-category/insert';
    let deleteURL = '/o2o/shop-management/goods-category/delete';
    let rows = 0;
    let set = new Set();

    // 执行初始化
    init();

    function init(){
        $.getJSON(queryURL, function (data) {
            if (data.success){
                let categoriesHTML = '';
                let categories = data.categories;
                // 获取集合长度
                rows = Object.keys(categories).length;
                categories.map(function (category, index) {
                    categoriesHTML += '<div class="row row-product-category now">'
                        + '<div class="col-20">' + (index + 1) + '</div>'
                        + '<div class="col-20">' + category.goodsCategoryName + '</div>'
                        + '<div class="col-20">' + category.priority + '</div>'
                        + '<div class="col-20"><a href="#" class="button first" data-id="' + category.goodsCategoryId + '">删除</a></div>'
                        + '</div>';
                });
                $('#category-body').html(categoriesHTML);
            }else{
                $.toast('查询商品类型信息失败!');
            }
        });
    }

    // 每次点击新增按钮都会新增一个空白行
    $('#new').click(function () {
        let newHTML = '';
         newHTML += '<div class="row row-product-category temp"><div class="col-20">' + (++rows) + '</div>'
            + '<div class="col-20"><input class="category-input category" id="category-name" type="text" placeholder="分类名"></div>'
            + '<div class="col-20"><input class="category-input priority" id="priority" type="number" placeholder="优先级"></div>'
            + '<div class="col-20"><a href="#" class="button second">删除</a></div>'
            + '</div>';
        $('#category-body').append(newHTML);
    });

    $('#submit').click(function () {
        let categories = [];
        let newCategories = $('.temp');
        console.log(newCategories);
        // 注: 为什么要反过来写?
        newCategories.map(function (index, category) {
            let obj = {};
            obj.goodsCategoryName = $(category).find('.category').val();
            obj.priority = $(category).find('.priority').val();
            console.log(obj);
            if (obj.goodsCategoryName && obj.priority){
                categories.push(obj);
            }
        });

        $.ajax({
            url: insertURL,
            type: 'POST',
            data: JSON.stringify(categories),
            contentType: 'application/json',
            success: function (data) {
                if (data.success){
                    init();
                }else{
                    $.toast("新增商品类型失败!");
                }
            }
        });
    });

    // 删除还没有添加的商品类型信息
    $('#category-body').on('click', '.second', function () {
        rows--;
        $(this).parent().parent().remove();
    });

    // TODO 暂时还存在问题 删除已经添加的商品类型信息
    $('#category-body').on('click', '.first', function (e) {
        let target = e.currentTarget;
        $.confirm("确定删除吗?", function () {
            $.ajax({
                url: deleteURL,
                type: 'POST',
                data: {categoryId : target.dataset.id},
                dataType: 'json',
                success: function (data) {
                    if (data.success){
                        $.toast("删除成功!");
                        init();
                    }else{
                        $.toast(data.message);
                    }
                }
            });
        })
    })

});