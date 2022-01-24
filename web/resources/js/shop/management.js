/**
 * <h2>负责将店铺编号传递给对应的标签</h2>
 */

$(function (){
   let id = getParameter('id');
   // 验证权限的方法路径
   let checkURL = '/o2o/shop-management/check?id=' + id;
   // 通过执行结果确定是否跳转还是重定向到首页
    $.getJSON(checkURL, function (data) {
        if (data.redirect){
            // 如果没有权限, 那么就会重定向到首页
            window.location.href = data.url;
        }else{
            if (data.id !== undefined){
                id = data.id;
            }
            // 注: 不要忘了加 #
            $('#shop-management').attr('href', '/o2o/shop-admin/edit?id=' + id);
        }
    });
});
