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
                let parents = data.parents
                // 获取集合长度
                rows = Object.keys(categories).length;
                // 获取所有的父类型
                parents.map(function (parent, index) {
                    set.add(parent);
                })
                categories.map(function (category, index) {
                    categoriesHTML += '<tr><td>' + (index + 1) + '</td>'
                        + '<td>' + category.goodsCategoryName + '</td>'
                        + '<td>' + category.priority + '</td>'
                        + '<td>' + category.goodsCategoryParent.goodsCategoryName +'</td>'
                        + '<td><a href="#" class="btn btn-link first" data-id="'+ category.goodsCategoryId + '">删除</a>'
                });
                $('#category-body').html(categoriesHTML);
            }else{
                alert('查询商品类型信息失败!');
            }
        });
    }

    // 每次点击新增按钮都会新增一个空白行
    $('#new').click(function () {
        let newHTML = $('#category-body').html();
        newHTML += '<tr id="temp"><td>' + (++rows) + '</td>'
            + '<td><input type="text" id="category-name" class="form-control"></td>'
            + '<td><input type="number" id="priority" class="form-control"></td>'
            + '<td><select class="form-control" id="parent">' + findAllParentCategory(set) + '</select></td>'
            + '<td><a href="#" class="btn btn-link second">删除</a></td>'
        $('#category-body').html(newHTML);
    });

    $('#submit').click(function () {
        let list = [];
        let newCategories = $('#temp');
        console.log(newCategories);
        // 注: 为什么要反过来写?
        newCategories.map(function (index, category) {
            let obj = {};
            obj.goodsCategoryName = $(category).find('#category-name').val();
            obj.priority = $(category).find('#priority').val();
            obj.goodsCategoryParent = {
                goodsCategoryId: $(category).find('#parent').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            console.log(obj);
            if (obj.goodsCategoryName && obj.priority){
                list.push(obj);
            }
        });

        $.ajax({
            url: insertURL,
            type: 'POST',
            data: JSON.stringify(list),
            contentType: 'application/json',
            success: function (data) {
                if (data.success){
                    init();
                }else{
                    alert("新增商品类型失败!");
                }
            }
        });
    });

    // 删除还没有添加的商品类型信息
    $('#category-body').on('click', '.second', function () {
        rows--;
        $(this).parent().parent().remove();
    });

    // 删除已经添加的商品类型信息
    $('#category-body').on('click', '.first', function (e) {
        let target = e.currentTarget;
        $.ajax({
            url: deleteURL,
            type: 'POST',
            data: JSON.stringify({
                goodsCategoryId: target.dataset.id
            }),
            contentType: 'application/json',
            success: function (data) {
                if (data.success){
                    alert("删除成功!");
                    init();
                }else{
                    alert("删除失败!");
                }
            }
        });
    })

    function findAllParentCategory(set) {
        let categoriesHTML = '';
        for (let category of set){
            console.log(category);
            categoriesHTML += '<option data-id="' + category.goodsCategoryId + '">' + category.goodsCategoryName + '</option>'
        }
        return categoriesHTML;
    }
});