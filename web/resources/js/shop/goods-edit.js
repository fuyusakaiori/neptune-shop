$(function() {
    // 从URL里获取productId参数的值
    let id = getParameter('id');
    // 根据商品编号获取商品信息 URL
    let initURL = '/o2o/shop-management/goods/find?id=' + id;
    // 获取当前店铺设定的商品类别列表 URL
    let categoryURL = '/o2o/shop-management/goods-category/find';
    // 更新商品信息 URL
    let update = '/o2o/shop-management/goods/update';
    // 新增商品的 URL
    let insert = '/o2o/shop-management/goods/insert';
    let isEdit = !!id;

    if (isEdit) {
        console.log("进入编辑页面");
        // 进入编辑页面
        init();
    } else {
        // 进入新增页面
        console.log("进入新增页面");
        getCategory();
    }

    // 获取需要编辑的商品的商品信息，并赋值给表单
    function init() {
        $.getJSON(initURL, function(data) {
            if (data.success) {
                // 从返回的JSON当中获取product对象的信息，并赋值给表单
                let goods = data.goods;
                console.log(goods);
                $('#goods-name').val(goods.goodsName);
                $('#goods-description').val(goods.description);
                $('#priority').val(goods.priority);
                // TODO $('#point').val(goods.point);
                $('#normal-price').val(goods.normalPrice);
                $('#promotion-price').val(goods.promotionPrice);
                // 获取原本的商品类别以及该店铺的所有商品类别列表

                let categoryHTML = '<option data-value="' + goods.category.goodsCategoryId + '" selected>' + goods.category.goodsCategoryName + '</option>';
                let categories = data.categories;
                // 生成前端的HTML商品类别列表，并默认选择编辑前的商品类别
                categories.map(function(category, index) {
                        categoryHTML += '<option data-value="' + category.productCategoryId + '">'
                            + category.goodsCategoryName
                            + '</option>';
                });
                $('#goods-category').html(categoryHTML);
            }else{
                console.log(data.message);
            }
        });
    }

    // 为商品添加操作提供该店铺下的所有商品类别列表
    function getCategory() {
        $.getJSON(categoryURL, function(data) {
            if (data.success) {
                let categories = data.categories;
                let categoryHTML = '';
                categories.map(function(category, index) {
                    categoryHTML += '<option data-value="' + category.goodsCategoryId + '">' + category.goodsCategoryName + '</option>';
                });
                $('#goods-category').html(categoryHTML);
            }
        });
    }

    // 针对商品详情图控件组，若该控件组的最后一个元素发生变化（即上传了图片），
    // 且控件总数未达到6个，则生成新的一个文件上传控件
    $('.detail-img-div').on('change', '.detail-img:last-child', function() {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    });

    // 提交按钮的事件响应，分别对商品添加和编辑操作做不同响应
    $('#submit').click(
        function() {
            // 创建商品json对象，并从表单里面获取对应的属性值
            let goods = {};
            goods.goodsName = $('#goods-name').val();
            goods.description = $('#goods-description').val();
            goods.priority = $('#priority').val();
            // TODO goods.point = $('#point').val();
            goods.normalPrice = $('#normal-price').val();
            goods.promotionPrice = $('#promotion-price').val();
            // 获取选定的商品类别值
            goods.category = {
                goodsCategoryId : $('#goods-category').find('option').not(
                    function() {return !this.selected;}).data('value')
            };
            if (isEdit){
                goods.goodsId = id;
            }

            // 获取缩略图文件流
            let thumbnail = $('#small-img')[0].files[0];
            // 生成表单对象，用于接收参数并传递给后台
            let formData = new FormData();

            formData.append('thumbnail', thumbnail);
            // 遍历商品详情图控件，获取里面的文件流
            $('.detail-img').map(
                function(index, item) {
                    // 判断该控件是否已选择了文件
                    if ($('.detail-img')[index].files.length > 0) {
                        // 将第i个文件流赋值给key为productImgi的表单键值对里
                        formData.append('detail-' + (index + 1),
                            $('.detail-img')[index].files[0]);
                    }
                });
            formData.append('goods', JSON.stringify(goods));

            let actual = $('#kaptcha').val();
            if (!actual) {
                $.toast('请输入验证码！');
                return;
            }
            formData.append("actual", actual);

            $.ajax({
                url : (isEdit ? update : insert),
                type : 'POST',
                data : formData,
                contentType : false,
                processData : false,
                cache : false,
                success : function(data) {
                    if (data.success) {
                        window.location.href = '/o2o/shop-admin/goods-list';
                        $.toast('提交成功！');
                    } else {
                        $.toast('提交失败！');
                        console.log(data.message);
                        $('#captcha-img').click();
                    }
                }
            });
        });

});