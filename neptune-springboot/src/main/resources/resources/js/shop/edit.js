/**
 * <h2>用于获取新增店铺页面数据</h2>
 * <h2>编辑店铺信息页面复用</h2>
 */
// 1. js 文件被加载的时候会自动执行这个方法

$(function () {
    // 2.1 获取店铺类型和所属区域等等相关信息的方法的对应地址
    let init = '/o2o/shop-management/shop-info/init';
    // 2.2 注册店铺的方法地址
    let register = '/o2o/shop-management/shop-info/insert';
    // 2.3 获取店铺对应编号的方法地址
    let id = getParameter('id');
    let getShopId = '/o2o/shop-management/shop-info/find?id=' + id;
    // 2.4 更新店铺信息的对应地址
    let update = '/o2o/shop-management/shop-info/update';
    // 2.5 判断接收请求是新增店铺信息还是更新店铺信息
    let isEdit = !!id;
    // 3. 初始化店铺类型和区域类型信息
    if (isEdit){
        fillShopInfo(id);
    }else{
        getShopInfo();
    }
    function getShopInfo(){
        $.getJSON(init, function (data) {
            // 3.1 如果成功返回信息, 那么就开是填充数据
            if (data.success){
                let categoryHTML = '';
                let areaHTML = '';
                data.categoryList.map(function (category, index){
                    // 注: 因为没有提示, 所以属性名称千万不要写错了
                    categoryHTML += '<option data-id="' + category.shopCategoryId + '">' + category.categoryName + '</option>'
                });
                data.areaList.map(function (area, index) {
                    areaHTML += '<option data-id="' + area.campusAreaId + '">' + area.campusAreaName + '</option>';
                });
                // 3.2 获取到对应的标签并填充获取的内容
                $('#shop-category').html(categoryHTML);
                $('#campus-area').html(areaHTML);
            }else{
                // TODO 3.3. 如果返回信息失败, 完成相应的处理
                console.log('信息初始化失败...');
            }
        });
    }

    // 4. 填充表单中的信息
    function fillShopInfo(id){
        console.log("获取店铺信息的方法地址: " + getShopId);
        $.getJSON(getShopId, function (data) {
            if (data.success){
                // 取出店铺信息
                let shop = data.shop;
                // 填充表单
                $('#shop-name').val(shop.shopName);
                $('#shop-phone').val(shop.phone);
                $('#shop-address').val(shop.address);
                $('#shop-description').val(shop.description);
                let categoryHTML = '<option data-id="' + shop.category.shopCategoryId + '" selected>' +
                                        shop.category.categoryName + '</option>';
                let areaHTML = '';
                data.areas.map(function (area, index) {
                    areaHTML += '<option data-id="' + area.campusAreaId + '">' + area.campusAreaName + '</option>';
                })
                // 店铺类型是不可以修改的, 所以直接选定
                $('#shop-category').html(categoryHTML);
                // 不可修改店铺类型
                $('#shop-category').attr('disabled', 'disabled');
                $('#campus-area').html(areaHTML);
                // 将当前店铺的区域设置为选中
                $("#campus-area option[data-id='" + shop.campusArea.campusAreaId + "']").
                        attr('selected', 'selected');

            }else{
                alert(data.message);
            }
        });
    }

    // 5. 提交表单中的信息
    $('#submit').click(function () {
        let shopinfo = {};
        if (isEdit){
            shopinfo.shopId = id;
        }
        shopinfo.shopName = $('#shop-name').val();
        shopinfo.address = $('#shop-address').val();
        shopinfo.phone = $('#shop-phone').val();
        shopinfo.description = $('#shop-description').val();
        shopinfo.category = {
            shopCategoryId : $('#shop-category').find('option').not(function () {
                    return !this.selected;
            }).data('id'),
            categoryName : $('#shop-category').find('option').not(function () {
                return !this.selected;
            }).val()
        }
        shopinfo.campusArea = {
            campusAreaId : $('#campus-area').find('option').not(function () {
                return !this.selected;
            }).data('id'),
            campusAreaName : $('#campus-area').find('option').not(function () {
                return !this.selected;
            }).val()
        }
        // 注: 从 files 属性中获取图片而不是 file
        let image = $('#shop-img')[0].files[0];
        let form = new FormData();
        let actual = $('#kaptcha').val();
        if (!actual){
            alert('请填写验证码!')
            return;
        }
        form.append('actual', actual);
        // 注: 记得传递对象之前转换成 JSON 格式
        form.append("shopinfo", JSON.stringify(shopinfo));
        form.append("shopimage", image);
        console.log(shopinfo);
        // 利用 AJAX 提交请求
        $.ajax({
            // 注: 这个地方一定要加括号
            url: (isEdit ? update : register),
            type: 'POST',
            data: form,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success){
                    window.location.href = '/o2o/shop-admin/shop-edit?id=' + id;
                    $.toast('操作成功!')
                }else{
                    $.toast('操作失败!');
                    console.log(data.message);
                }
                // 无论是否提交成功, 更新验证码
                $('#kaptcha-img').click();
            }
        })
    })
})