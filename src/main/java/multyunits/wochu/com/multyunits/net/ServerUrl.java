package multyunits.wochu.com.multyunits.net;

public class ServerUrl {
    //testgit测试提交
    //yyyyy

    public static final String REPLESERVER = "http://116.228.118.218:10098/PickLocationReplenish/GetPickLocationGoods?";
    public static final String COMITREPLESERVER = "http://116.228.118.218:10098/PickLocationReplenish/UpdateReplenishStatus?userID=";
    //获取货位
    public static final String GETPICKLOCATION = "http://116.228.118.218:10098/PickLocationReplenish/GetPickLocationInfo?GoodsCode=";
    public static final String GETPICKLOCATION_TEST = "http://116.228.118.218:9098/PickLocationReplenish/GetPickLocationInfo?GoodsCode=";
    //登录地址
    public static final String LOGINSERVER = "http://116.228.118.218:10098/Account/LogOn?";
    //订单详情地址
    public static final String GETORDERSERVER = "http://116.228.118.218:10098/OrderPick/GetReviewInfo?BoxNO=";
    public static final String GETORDERSERVER_TEST = "http://116.228.118.218:9098/OrderPick/GetReviewInfo?BoxNO=";

    //发货箱状态地址
    public static final String SENDBOXSERVER = "http://116.228.118.218:10098/OrderPick/GetOrderUserInfo?FBoxNO=";
    public static final String SENDBOXSERVER_TEST = "http://116.228.118.218:9098/OrderPick/GetOrderUserInfo?FBoxNO=";
    //	public static final String GETORDERSERVER_TEST = "http://58.247.11.228:8082/api/OrderPick/GetReviewInfo?BoxNO=";
    //提交发货地址
    public static final String BINDSENDBOX = "http://116.228.118.218:10098/WCS_TO_WMS/SendWMStoWCStask/";
    public static final String BINDSENDBOX_TEST = "http://116.228.118.218:9098/WCS_TO_WMS/SendWMStoWCStask/";
    //提交错误订单地址
    public static final String COMITERRORORDER = "http://116.228.118.218:10098/WCS_TO_WMS/SendReCheckExcep/";
    public static final String COMITERRORORDER_TEST = "http://116.228.118.218:9098/WCS_TO_WMS/SendReCheckExcep/";
    //称重数据的地址
    public static final String DEBINDFBOX = "http://116.228.118.218:10098/WCS_TO_WMS/DebindFBOX?";
    public static final String WINTINGURL = "http://58.247.11.228:8099/weigh/getgoods";
    public static final String SEND_RECHECK = "http://116.228.118.218:10098/WCS_TO_WMS/SendReCheckExcep";
    public static final String SEND_RECHECK_TEST = "http://116.228.118.218:9098/WCS_TO_WMS/SendReCheckExcep";
    //获取订单中的商品信息的地址
    public static final String ORDERDETAILS = "http://116.228.118.218:10098/WCS_TO_WMS/GetAPPPickOrderInfoByOrderNO?";
    //未拣区访问网络
    public static final String WEIJIANQU = "http://58.247.11.228:8088/WCS_TO_WMS/GetUserAppPickInfo?";
    //成品拣货保存的地址
    public static final String BAOCHUN = "http://58.247.11.228:8088/WCS_TO_WMS/AppSaveWMStoWCStask";
    //成品拣货提交的地址
    public static final String TIJIAO = "http://58.247.11.228:8088/WCS_TO_WMS/AppSendWMStoWCStask";

    public static String loginUrl(String userName, String password) {
        StringBuilder sb = new StringBuilder();
        sb.append(LOGINSERVER);
        sb.append("userName=");
        sb.append(userName + "&");
        sb.append("password=");
        sb.append(password);
        return sb.toString();
    }

    public static String WeiJianQu(String userID, String time) {
        StringBuilder sb = new StringBuilder();
        sb.append(WEIJIANQU);
        sb.append("UserId=");
        sb.append(userID + "&");
        sb.append("Time=");
        sb.append(time);
        return sb.toString();

    }

    public static String pickingfinishedUrl(String orderNumber, String userID, String userCode) {
        StringBuilder sb = new StringBuilder();
        sb.append(ORDERDETAILS);
        sb.append("OrderNO=");
        sb.append(orderNumber + "&");
        sb.append("UserID=");
        sb.append(userID + "&");
        sb.append("UserName=");
        sb.append(userCode);
        return sb.toString();
    }

    //我加一句

    public static final String GETINFOBYORDERNUM = "http://116.228.118.218:10098/WCS_TO_WMS/checkWMS_TO_WCStask?OrderNO=";
    public static final String GETINFOBYORDERNUM_TEST = "http://116.228.118.218:9098/WCS_TO_WMS/checkWMS_TO_WCStask?OrderNO=";
    public static final String UPDATESENDBOX = "http://116.228.118.218:9098/WCS_TO_WMS/UpdateWMS_TO_WCSTASKfboxs?";
    public static final String UPDATESENDBOX_TEST = "http://116.228.118.218:9098/WCS_TO_WMS/UpdateWMS_TO_WCSTASKfboxs?";
    public static final String REPLESERVER_TEST = "http://116.228.118.218:9098/PickLocationReplenish/GetPickLocationGoods?";
    public static final String COMITREPLESERVER_TEST = "http://116.228.118.218:9098/PickLocationReplenish/UpdateReplenishStatus?userID=";
    public static final String DEBINDFBOX_TEST = "http://116.228.118.218:9098/WCS_TO_WMS/DebindFBOX?";
}