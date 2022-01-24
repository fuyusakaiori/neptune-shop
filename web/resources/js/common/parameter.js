/**
 * <h2>获取 URL 地址中携带的参数</h2>
 * @param name 参数的名字
 */
function getParameter(name) {
    // 1. 获取参数的正则表达式
    let regExp = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    // 2. 获取搜索框中的 url 地址（从 ? 号开始的部分）
    let url = window.location.search;
    console.log("查询部分: " + url);
    // 3. 选取参数并且使用正则表示进行匹配
    let array = url.substr(1).match(regExp);
    console.log("参数部分: " + array);
    // 4. 解析参数后返回
    return array == null ? '': decodeURIComponent(array[2]);
}