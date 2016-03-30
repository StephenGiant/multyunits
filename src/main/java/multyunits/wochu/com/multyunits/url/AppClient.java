package multyunits.wochu.com.multyunits.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import multyunits.wochu.com.multyunits.utils.LogUtil;


public class AppClient {

    //多单为测试环境基础地址
//	public static final String BASEURL="http://116.228.118.218:10098";
//	public static final String BASEURL="http://116.228.118.218:9098";
//	public static final String BASEURL="http://116.228.118.218:9099";
    public static final String BASEURL = "http://10.10.10.105:9098";
    //多单位测试环境基础地址
//	public static final String CESHIBASEURL="http://116.228.118.218:9098";

    //登录地址
//	public static final String LOGINSERVER_TEST = CESHIBASEURL+"/Account/LogOn?";
    public static final String LOGINSERVER = BASEURL + "/Account/LogOn?";
    //获取正常仓商品货位号地址
    public static final String LOCATIONURL_NORMALWAREHOUSE = BASEURL + "/LocationGoods/GetLocationInfo?GoodsCode=";
    //获取检获仓商品货位号地址
    public static final String LOCATIONURL_PICKWAREHOUSE = BASEURL + "/LocationGoods/GetLocationInfo?GoodsCode=";
    public static final String LOCATIONURL = BASEURL + "/LocationGoods/GetPickLocationInfo?GoodsCode=";
    //	public static final String LOCATIONURL_TEST=CESHIBASEURL+"/LocationGoods/GetPickLocationInfo?GoodsCode=";
    public static final String GETLOCATIONINFO_GOODSCODE = BASEURL + "/InventoryAdjustment/GetLocationInventoryByCodeHouse?CodeType=";
    //检查订单状态
    public static final String GETORDERSTATE = BASEURL + "/OrderPick/CheckOrderIsChanged?rationDate=";
    //提交核单（老线）
    public static final String POSTODERSORT = BASEURL + "/WCS_TO_WMS/ConfirmCheckOrder?BoxNO=";
    //分拣仓商品货位关联提交地址
    public static final String FENGJIANCANGCOMMODITYGOODSOCIATEDSUMMIT = BASEURL + "api/LocationGoods/SetPickLocationGoods?";
    //    public static final String FENGJIANCANGCOMMODITYGOODSOCIATEDSUMMIT_TEST =  CESHIBASEURL+"/LocationGoods/SetPickLocationGoods?";
    public static final String GETPUTDOWNINFO = BASEURL + "/OffShelvesOrder/GetOffShelvesInfo?";
    public static final String GETPUTDOWNINFO_TEST = "http://116.228.118.218:9098/OffShelvesOrder/GetOffShelvesInfo?";
    //称重功能获取单个商品信息接口
    public static final String GETWEIGHTGOODSINFORMATION = BASEURL + "/Weigh/GetGoodsByCode?goodsCode=";
    //提交商品称重接口
    public static final String POSTGOODSWEIGHT = BASEURL + "/Weigh/ModifyGoodsWeight";
    //查询功能下根据货位查询
    public static final String getGoodInformationByLocation = BASEURL + "/InventoryAdjustment/GetSUMLocationInventory?LocCode=";
    //    public static final String getGoodInformationByLocation_TEST =CESHIBASEURL+"/InventoryAdjustment/GetSUMLocationInventory?LocCode=";
    //正常仓货位商品关联地址
    public static final String COMMODITYGOODSOCIATED = BASEURL + "/LocationGoods/SetLocationGoods?";
    //	public static final String COMMODITYGOODSOCIATED_TEST = CESHIBASEURL+"/LocationGoods/SetLocationGoods?";
    //分拣仓货位查询功能地址
    public static final String SORTINGGOODSQUERY = BASEURL + "/PickTask/PickTaskQuery?";
    //获取损益环节,损益原因地址
    public static final String GETPOSSITHUANJIE = BASEURL + "/InventoryLost/queryLookUplist?lookupid=";
    //获取损益商品信息地址
    public static final String GETPOSSITINFORMATION = BASEURL + "/InventoryAdjustment/GetInventoryByGoodsBatch?BatchCode=";
    //获取损益所选择仓位地址
    public static final String GETCANGWEI = BASEURL + "/InventoryLost/queryWarehouselist";
    public static final String GETBATCHNUMBER = BASEURL + "/OrderPick/GetBatchNumberByRationDate?rationDate=";
    public static final String GETBATCHNUMBER_TEST = BASEURL + "/OrderPick/GetBatchNumberByRationDate?rationDate=";
    //	public static final String BINDSENDBOX_TEST = "http://116.228.118.218:9098/WCS_TO_WMS/SendWMStoWCStask/";
    public static final String BINDSENDBOX = BASEURL + "/WCS_TO_WMS/SendWMStoWCStasklst";
    public static final String GETODERBYBATCH = BASEURL + "/OrderPick/GetSaleOrderInfo?rationDate=";
    //	public static final String GETODERBYBATCH_TEST = CESHIBASEURL+"/OrderPick/GetSaleOrderInfo?rationDate=";
    public static final String SENDBOXSERVER_TEST = "http://116.228.118.218:9098/OrderPick/GetOrderUserInfo?FBoxNO=";
    public static final String SENDBOXSERVER = BASEURL + "/OrderPick/GetOrderUserInfo?FBoxNO=";
    public static final String GETFROZENITEM = BASEURL + "/Orders/GetFrozenGoodsInfo?rationDate=";
    public static final String BINDZBOX = BASEURL + "/Orders/BindZboxAndGoods?goodsCode=";
    public static final String GOODSCOUNTQUERY = BASEURL + "/Orders/GetGoodsInfo?rationDate=";
    public static final String URL_GET_GOODS_INFO = BASEURL + "/Orders/GetGoodsInfo?";
    public static final String GETORDERDETAIL_BYORDERNUM = BASEURL + "/Orders/GetOrderInfoByOrderNO?orderNo=";

    public static final String URL_GET_ORDER_INFO_BY_ORDER_NO = BASEURL + "/Orders/GetOrderInfoByOrderNO?orderNo=";

    public static final String GETBATCHORDER_DPS = BASEURL + "/Orders/GetAllOrderInfo?rationDate=";
    public static final String BINDFBOX_DPS = BASEURL + "/Orders/BindFBoxAndOrder";
    public static final String GET_CONFIRMCHECKORDER = BASEURL + "/Orders/ConfirmCheckOrder?RationDate=";


		/*
     * 登陆地址集成方法
	 * @param userName
	 * @param password
	 * @return
	 */

    public static String loginUrl(String userName, String password) {
        StringBuilder sb = new StringBuilder();
        sb.append(LOGINSERVER);
        sb.append("userName=");
        sb.append(userName + "&");
        sb.append("password=");
        sb.append(password);
        return sb.toString();
    }

    /**
     * userID是登录的用户名,此方法为拼接获取下架信息的url
     *
     * @param cellNo
     * @param batchNo
     * @param userID
     * @param shelvesDate
     * @return
     */
    public static String putDownInfoUrl(String cellNo, String batchNo, String userID, String shelvesDate) {
        StringBuilder sb = new StringBuilder();
//		sb.append(GETPUTDOWNINFO);
        sb.append(GETPUTDOWNINFO_TEST);
        try {
            cellNo = URLEncoder.encode(cellNo, "UTF-8");
            System.out.println("编码" + cellNo);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        sb.append("cellNo=" + cellNo + "&");
        sb.append("batchNo=" + batchNo + "&");
        sb.append("userID=" + userID + "&");
        sb.append("offShelvesDate=" + shelvesDate);

        System.out.println("get请求" + sb.toString());
        return sb.toString();
    }

    //获取货位商品信息地址
    public static final String GetGoodInformations = BASEURL + "/InventoryAdjustment/GetLocationInventory?LocCode=";
//	public static final String GetGoodInformations_TEST = CESHIBASEURL+"/InventoryAdjustment/GetLocationInventory?LocCode=";

    //库存盘点商品信息提交地址
    public static final String PostGoodInformations = BASEURL + "/InventoryAdjustment/UpdateLocationInventory?UserId=";
//	public static final String PostGoodInformations_TEST = CESHIBASEURL+"/InventoryAdjustment/UpdateLocationInventory?UserId=";

    //库存盘点提交作废商品地址
    public static final String PostInvalidGood = BASEURL + "/InventoryAdjustment/UpdateLocationInventoryStatus?";
    //	public static final String PostInvalidGood_TEST = CESHIBASEURL+"/InventoryAdjustment/UpdateLocationInventoryStatus?";
    //库存盘点作废区地址
    public static final String InvalidGoodInformations = BASEURL + "/InventoryAdjustment/GetLocationInventory?";
//	public static final String InvalidGoodInformations_TEST = CESHIBASEURL+"/InventoryAdjustment/GetLocationInventory?";

    //分拣仓商品货位关联检查地址
    public static final String FengJianCangCOMMODITYGOODSOCIATED = BASEURL + "/LocationGoods/CheckPickLocationGoods?";
    public static final String ZBOX_POST_TEST = "http://116.228.118.218:9098/ZboxSku/SetZboxSkuRela?UserId={UserId}&GoodsCode={GoodsCode}&ZboxCode={ZboxCode}";
    public static final String GETITEMCOUNT = BASEURL + "/OrderPick/GetOrderItemSumQTYByFboxNO?FBOXNO=";
    public static final String GETORDERDETAIL_BYFBOX = BASEURL + "/OrderPick/GetOrderItemByFboxNO?FBOXNO=";
    public static final String GETORDERDETAIL_BYFBOX_DPS = BASEURL + "/Orders/GetOrderInfoByFboxNO?FboxNo=";
    public static final String DEBINDODERFBOX = BASEURL + "/WCS_TO_WMS/deleteMarkToFBOX?FBoxNO=";
//	public static final String DEBINDODERFBOX_TEST = CESHIBASEURL+"/WCS_TO_WMS/deleteMarkToFBOX?FBoxNO=";

    /**
     * 获取作废商品集成地址的方法
     *
     * @param locatonCode
     * @param status
     * @return
     */
    public static String getInvalidGoodInformations(String locatonCode, int status) {
        StringBuilder sb = new StringBuilder();
//		sb.append(InvalidGoodInformations);
        sb.append(InvalidGoodInformations);
        sb.append("LocCode=");
        sb.append(locatonCode + "&");
        sb.append("status=");
        sb.append(status);
        return sb.toString();
    }

    /**
     * 提交作废商品集成地址的方法
     *
     * @param LocCode
     * @param BatchCode
     * @param status
     * @return
     */
    public static String getPostInvalidGood(String LocCode, String BatchCode, int status) {
        StringBuilder sb = new StringBuilder();
//		sb.append(PostInvalidGood);
        sb.append(PostInvalidGood);
        sb.append("LocCode=");
        sb.append(LocCode + "&");
        sb.append("BatchCode=");
        sb.append(BatchCode + "&");
        sb.append("status=");
        sb.append(status);
        return sb.toString();
    }

    public static String GuanLianSummit(int userID, String LocationCode, String GoodsCode) {
        StringBuilder sb = new StringBuilder();
        sb.append(FENGJIANCANGCOMMODITYGOODSOCIATEDSUMMIT);
        sb.append("userID=");
        sb.append(userID + "&");
        sb.append("LocationCode=");
        sb.append(LocationCode + "&");
        sb.append("GoodsCode=");
        sb.append(GoodsCode);
        return sb.toString();
    }

    public static String GuanLian(int userID, String LocationCode, String GoodsCode) {
        StringBuilder sb = new StringBuilder();
        sb.append(FengJianCangCOMMODITYGOODSOCIATED);
        sb.append("userID=");
        sb.append(userID + "&");
        sb.append("LocationCode=");
        sb.append(LocationCode + "&");
        sb.append("GoodsCode=");
        sb.append(GoodsCode);
        return sb.toString();
    }


    /**
     * 分拣仓货位查询功能集成地址
     *
     * @param locationcode
     * @param batchNo
     * @param rationDate
     * @return
     */
    public static String GETSORTINGGOODSQUERY(String locationcode, String batchNo, String rationDate) {
        StringBuilder sb = new StringBuilder();
        sb.append(SORTINGGOODSQUERY);
        sb.append("locationcode=");
        sb.append(locationcode + "&");
        sb.append("batchNo=");
        sb.append(batchNo + "&");
        sb.append("rationDate=");
        sb.append(rationDate);
        return sb.toString();
    }

    /**
     * 正常仓商品货位关联集成地址
     *
     * @param userID
     * @param LocationCode
     * @param GoodsCode
     * @param status
     * @return
     */
    public static String GuanLian(int userID, String LocationCode, String GoodsCode, int status) {
        StringBuilder sb = new StringBuilder();
        sb.append(COMMODITYGOODSOCIATED);
        sb.append("userID=");
        sb.append(userID + "&");
        sb.append("LocationCode=");
        sb.append(LocationCode + "&");
        sb.append("GoodsCode=");
        sb.append(GoodsCode + "&");
        sb.append("status=");
        sb.append(status);
        return sb.toString();
    }

    public static String BindGoodsLocation(int userID, String goodsCode, String zboxCode) {
        String url = null;
        url = ZBOX_POST_TEST.replace("{UserId}", userID + "");
        url = url.replace("{GoodsCode}", goodsCode);
        url = url.replace("{ZboxCode}", zboxCode);
        LogUtil.i("看看url", url);
        return url;
    }
}
