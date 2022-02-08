/**
 * 获取用户的所有店铺信息并显示在首页中
 */

$(function (){
    $.ajax({
        url: '/o2o/shop-management/shop-info/list',
        type: 'GET',
        dataType: 'json',
        success: function (data){
            if (data.success){
                // 将获取到的所有店铺信息全部填充进入表格中
                getShopInfoList(data.shops);
                getUserInfo(data.user);
            }else{
                console.log('店铺信息获取失败!')
            }
        }
    });
    // 将所有店铺信息全部填充进入表单中
    function getShopInfoList(list){
        let listHTML = '';
        list.map(function (shop, index){
           listHTML +=  '<div class="row row-shop"><div class="col-20">'
               + (index + 1) + '</div><div class="col-40">'
               + shop.shopName + '</div><div class="col-20">'
               + getShopStatus(shop.status) + '</div><div class="col-20">'
               + getShopManagementURL(shop.status, shop.shopId)
               + '</div></div>'
        });
        $('#body').html(listHTML);
    }
    // 将用户信息填充进入表格顶部
    function getUserInfo(user){
        $('#username').text(user.username);
    }

    // 根据店铺状态转换成对应的提示信息
    function getShopStatus(status){
        if (status === 0) return '审核中';
        if (status === 1) return '审核通过';
        if (status === -1) return '非法店铺';
    }

    function getShopManagementURL(status, id){
        if (status === 1)
            return '<a href="/o2o/shop-admin/shop-management?id='+ id +'">管理</a>'
        else
            return '';
    }

});