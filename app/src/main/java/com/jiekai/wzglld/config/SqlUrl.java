package com.jiekai.wzglld.config;

/**
 * Created by LaoWu on 2017/12/6.
 */

public class SqlUrl {
    /**
     * 登录数据库操作
     */
    public static final String LoginSql = "SELECT * FROM userinfo where USERID = ? AND PASSWORD = ?";
    /**
     * 获取登录用户的权限
     */
    public static final String LoginRule = "SELECT * FROM userrole WHERE USERID = ?";
    /**
     * 获取设备类型
     */
    public static final String GetDeviceType = "SELECT * FROM devicesort";
    /**
     * 查询全部的设备类别
     */
    public static final String GetAllLeiBie = "SELECT * FROM devicesort WHERE PARENTCOOD = \"0\"";
    /**
     * 查询对应类别的设备型号
     */
    public static final String GetXingHaoByLeiBie = "SELECT * FROM devicesort WHERE PARENTCOOD = ?";
    /**
     * 查询对应设备型号的规格
     */
    public static final String GetGuiGeByXingHao = "SELECT * FROM devicesort WHERE PARENTCOOD = ?";
    /**
     * 通过类别，型号，规格获取设备信息
     */
    public static final String GetBHByLeiBieXinghaoGuige = "SELECT device.BH FROM device where LB = ? AND XH = ? AND GG = ?";
    /**
     * 获取设备名称（通过第一级 设备类别）
     */
    public static final String GetDeviceMCByLB = "SELECT device.MC FROM device where LB = ?";
    /**
     * 获取设备名称（通过第二级 设备型号）
     */
    public static final String GetDeviceMCByXh = "SELECT device.MC FROM device where XH = ?";
    /**
     * 获取设备名称（通过第三级 设备规格）
     */
    public static final String GetDeviceMCByGG = "SELECT device.MC FROM device where GG = ?";
    /**
     * 获取设备自编号 通过设备名称
     */
    public static final String GetDeviceBHByMC = "SELECT device.BH FROM device where MC = ?";
    /**
     * 根据电子码编号获取设备信息
     */
    public static final String GetDeviceByID = "SELECT * FROM device where IDDZMBH1 = ? OR IDDZMBH2 = ? OR IDDZMBH3 = ?";
    /**
     * 根据自编码获取设备信息
     */
    public static final String GetDeviceByBH = "SELECT * FROM device where BH = ?";
    /**
     * 根据二维码获取设备信息
     */
    public static final String GetDeviceBySAOMA = "SELECT * FROM device where EWMBH = ?";
    /**
     * 往一个设备中添加配件
     */
    public static final String AddDepart = "UPDATE device SET SFPJ = ? , SSSBBH = ? WHERE BH = ?";
    /**
     * 根据设备id查询其配件的列表
     */
    public static final String GetPartListByDeviceId = "SELECT BH, MC, IDDZMBH1 FROM device WHERE SFPJ = 1 AND SSSBBH = ?";
    /**
     * 绑定设备
     */
    public static final String BIND_DEVICE = "UPDATE device SET IDDZMBH1 = ?, IDDZMBH2 = ?, IDDZMBH3 = ? WHERE BH = ?";
    /**
     * 插入设备文档表（绑定图片）
     */
    public static final String SaveDoc = "INSERT INTO devicedoc (SBBH, WJMC, WJDX, WJDZ, WDLX, LB) VALUES (?, ?, ?, ?, ?, ?)";
    /**
     * 获取已经审核通过，的结果
     */
    public static final String GetShenHeList = "SELECT * FROM deviceapply WHERE SPZT = \"1\"";
    /**
     * 执行设备出库操作 (设备自编码，操作时间，操作人，类别， 井号)
     */
    public static final String OUT_DEVICE = "INSERT INTO devicestore (SBBH, CZSJ, CZR, LB, JH, LYDW) VALUES (?, ?, ?, ?, ?, ?);";
    /**
     * 执行设备入库操作 (设备自编码，操作时间，操作人，类别)
     */
    public static final String IN_DEVICE = "INSERT INTO devicestore (SBBH, CZSJ, CZR, LB) VALUES (?, ?, ?, ?);";
    /**
     * 执行设备维修操作 (设备自编码，操作时间，操作人，类别)
     */
    public static final String REPAIR_DEVICE = "INSERT INTO devicestore (SBBH, CZSJ, CZR, LB) VALUES (?, ?, ?, ?);";
    /**
     * 查找设备出库表
     */
    public static final String GetDeviceOut = "SELECT devicestore.*, userinfo.USERNAME as czrname FROM devicestore, userinfo WHERE SBBH = ? AND LB = 0 AND userinfo.USERID = devicestore.CZR";
    /**
     * 查找设备入库表
     */
    public static final String GetDeviceIN = "SELECT devicestore.*, userinfo.USERNAME as czrname FROM devicestore, userinfo WHERE SBBH = ? AND LB = 1 AND userinfo.USERID = devicestore.CZR";
    /**
     * 查找设备入库表
     */
    public static final String GetDeviceRepair = "SELECT devicestore.*, userinfo.USERNAME as czrname FROM devicestore, userinfo WHERE SBBH = ? AND (LB = 3 OR LB = 4 OR LB = 5) AND userinfo.USERID = devicestore.CZR";
    /**
     * 根据盘库的需求查询数据库
     */
    public static final String GetPanKuDataByID = "SELECT " +
            "dv.BH, dv.MC, dv.LB, dv.XH, dv.GG, leibie.TEXT AS leibie,xinghao.TEXT AS xinghao,guige.TEXT AS guige" +
            " FROM " +
            "devicesort AS leibie, devicesort AS xinghao, devicesort AS guige, device as dv" +
            " WHERE " +
            "(dv.IDDZMBH1 = ? OR dv.IDDZMBH2 = ? OR dv.IDDZMBH3 = ?)" +
            "AND leibie.COOD = dv.LB " +
            "AND xinghao.COOD = dv.XH " +
            "AND guige.COOD = dv.GG";
    /**
     * 通过扫码所获取的二维码号找到设备的信息
     */
    public static final String GetPanKuDataBySAOMA = "SELECT " +
            "dv.BH, dv.MC, dv.LB, dv.XH, dv.GG, leibie.TEXT AS leibie,xinghao.TEXT AS xinghao,guige.TEXT AS guige" +
            " FROM " +
            "devicesort AS leibie, devicesort AS xinghao, devicesort AS guige, device as dv" +
            " WHERE " +
            "(dv.EWMBH = ?)" +
            "AND leibie.COOD = dv.LB " +
            "AND xinghao.COOD = dv.XH " +
            "AND guige.COOD = dv.GG";
    /**
     * 插入图片到服务器中（ID, 文件名称， 文件大小， 文件地址，文件类型，类别）
     */
    public static final String INSERT_IAMGE = "INSERT INTO devicedoc (SBBH, WJMC, WJDX, WJDZ, WDLX, LB) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_IMAGE = "UPDATE devicedoc SET WJMC = ?, WJDX = ?, WJDZ = ?, WDLX = ? WHERE SBBH = ? AND LB = ?";
    /**
     * 查找上次插入数据所返回的ID
     */
    public static final String SELECT_INSERT_ID = "SELECT LAST_INSERT_ID() AS last_insert_id";
    /**
     * 插入设备报废信息
     */
    public static final String ADD_DEVICE_SCRAP = "INSERT INTO devicescrap (SBBH, BFSJ, BFR) VALUES (?, ?, ?)";
    /**
     * 查找报废设备
     */
    public static final String GET_SCRAP_DEVICE = "SELECT devicescrap.*, userinfo.USERNAME as bfrname FROM devicescrap, userinfo WHERE SBBH = ? AND userinfo.USERID = devicescrap.BFR";
    /**
     * 更改设备状态
     */
    public static final String CHANGE_DEVICE_STATE = "UPDATE device SET SBZT = ? WHERE BH = ?";
    /**
     * 根据时间段 获取设备出库历史
     */
    public static final String GET_OUT_HISTORY = "SELECT " +
            "device.MC, " +
            "devicestore.SBBH, devicestore.CZSJ, devicestore.JH, devicestore.LYDW, " +
            "userinfo.USERNAME " +
            "FROM devicestore, device, userinfo WHERE " +
            "devicestore.CZSJ >=?  AND devicestore.CZSJ <=? AND devicestore.LB = 0 " +
            "AND device.BH = devicestore.SBBH AND userinfo.USERID = devicestore.CZR";
    /**
     * 获取设备详情 通过设备标签id查询
     */
    public static final String GET_DEVICE_DETAIL = "SELECT " +
            "*, lb.TEXT AS leibie, xh.TEXT AS xinghao, gg.TEXT AS guige " +
            "FROM device, devicesort AS lb, devicesort as xh, devicesort as gg " +
            "WHERE (device.IDDZMBH1 = ? OR device.IDDZMBH2 = ? OR device.IDDZMBH3 = ?) " +
            "AND lb.COOD = device.LB AND xh.COOD = device.XH AND gg.COOD = device.GG";
    /**
     * 获取设备详情 通过设备编号
     */
    public static final String GET_DEVICE_DETAIL_BY_BH = "SELECT " +
            "*, lb.TEXT AS leibie, xh.TEXT AS xinghao, gg.TEXT AS guige " +
            "FROM device, devicesort AS lb, devicesort as xh, devicesort as gg " +
            "WHERE (device.BH = ?) " +
            "AND lb.COOD = device.LB AND xh.COOD = device.XH AND gg.COOD = device.GG";
    /**
     * 获取设备详情 通过二维码
     */
    public static final String GET_DEVICE_DETAIL_BY_SAOMA = "SELECT " +
            "*, lb.TEXT AS leibie, xh.TEXT AS xinghao, gg.TEXT AS guige " +
            "FROM device, devicesort AS lb, devicesort as xh, devicesort as gg " +
            "WHERE (device.EWMBH = ?) " +
            "AND lb.COOD = device.LB AND xh.COOD = device.XH AND gg.COOD = device.GG";
    /**
     * 查询数据库中上次是否有盘库的数据,返回上次盘库的全部数据
     */
    public static final String Get_Old_Panku = "SELECT user.USERNAME as CZR, devicepanku.*, tableibie.TEXT as leibie, " +
            "tabxinghao.TEXT AS xinghao, tabguige.TEXT AS guige FROM devicepanku, userinfo as user, " +
            "devicesort as tableibie, devicesort as tabxinghao, devicesort as tabguige " +
            "WHERE devicepanku.SFQD = 0 AND user.USERID = devicepanku.CZR " +
            "AND tableibie.COOD = devicepanku.LB AND tabxinghao.COOD = devicepanku.XH " +
            "AND tabguige.COOD = devicepanku.GG";
    /**
     * 插入盘库信息
     */
    public static final String INSERT_PANKU = "INSERT INTO " +
            "devicepanku (BH, MC, LB, XH, GG, CZR, CZSJ, SFQD) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    /**
     * 删除上次用户盘库的信息
     */
    public static final String DELET_OLD_PANKU = "DELETE FROM devicepanku WHERE SFQD = ?";
    /**
     * 更新盘库信息
     */
    public static final String UPLOAD_PANKU_DATE = "UPDATE devicepanku SET devicepanku.SFQD = 1 WHERE SFQD = 0";
    /**
     * 获取盘库列表的所有分类信息，每个类别的个数
     */
    public static final String GET_PANKU_GROUP_LIST = "SELECT lb.TEXT as LB, xh.TEXT as XH, gg.TEXT as GG, COUNT(*) as NUM FROM " +
            "devicesort AS lb, devicesort as xh, devicesort as gg, " +
            "devicepanku WHERE devicepanku.SFQD = 0 AND lb.COOD = devicepanku.LB " +
            "AND xh.COOD = devicepanku.XH AND gg.COOD = devicepanku.GG " +
            "GROUP BY LB, XH, GG";
    /**
     * 获取该设备是否盘库
     */
    public static final String DEVICE_IS_PANKU = "SELECT * FROM devicepanku WHERE SFQD = 0 AND BH = ?";
    /**
     * 获取照片的地址
     */
    public static final String Get_Image_Path = "SELECT * FROM devicedoc WHERE SBBH = ? AND LB = ?";
    /**
     * 获取记录列表的内容（有哪些内容需要记录）
     * 通过标签获取
     */
    public static final String Get_Record_List = "SELECT devicelogsort.*, device.BH FROM device, devicelogsort WHERE " +
            "(device.IDDZMBH1 = ? OR device.IDDZMBH2 = ? OR device.IDDZMBH3 = ?) " +
            "AND devicelogsort.LBBH = device.GG";
    /**
     * 获取记录列表的内容（有哪些内容需要记录）
     * 通过二维码获取
     */
    public static final String Get_Record_List_By_SAOMA = "SELECT devicelogsort.*, device.BH FROM device, devicelogsort WHERE " +
            "(device.EWMBH = ?) " +
            "AND devicelogsort.LBBH = device.GG";
    /**
     * 获取记录列表的内容（有哪些内容需要记录）
     * 通过设备自编号获取
     */
    public static final String Get_Record_List_by_BH = "SELECT devicelogsort.*, device.BH FROM device, devicelogsort WHERE " +
            "device.BH = ? " +
            "AND devicelogsort.LBBH = device.GG";
    /**
     * 获取记录列表的内容（有哪些内容需要记录）
     * 通过设备自编号获取
     */
    public static final String Get_Record_List_by_GG = "SELECT * FROM devicelogsort WHERE devicelogsort.LBBH = ?";
    /**
     * 添加一条记录信息--现场添加记录信息
     */
    public static final String ADD_RECORD = "INSERT INTO devicelog (JLZLMC, SBBH, DH, JH, JLSJ, CZR) VALUES (?, ?, ?, ?, ?, ?)";
    /**
     * 更新记录信息
     */
    public static final String UPDATE_RECORD = "UPDATE devicelog SET DH = ?, JH =?, JLSJ = ?, CZR = ?," +
            " SHYJ = \"\", SHR = \"\", SHSJ = NULL, SHBZ = \"\" WHERE ID = ?";
    /**
     * 检查现场提交的记录的结果（列举所有没有通过审核的和没有审核的结果）
     */
    public static final String GET_RECORD_CHECK_LIST = "SELECT * FROM devicelog WHERE CZR = ? AND SHYJ = \"0\"";
    /**
     * 获取使用记录的历史记录内容, 通过设备自编码
     */
    public static final String GET_LOG_LIST_BY_BH = "SELECT devicelog.*, userinfo.USERNAME as czrname " +
            "FROM devicelog, userinfo WHERE " +
            "userinfo.USERID = devicelog.CZR";      //devicelog.JLZLMC = ? AND devicelog.SBBH = ? AND ";
    /**
     * 获取使用记录的历史记录内容
     */
    public static final String GET_LOG_LIST_BY_GG = "SELECT devicelog.*, userinfo.USERNAME as czrname " +
            "FROM devicelog, userinfo, device AS dv WHERE " +
            "dv.BH = devicelog.SBBH AND userinfo.USERID = devicelog.CZR";   // devicelog.JLZLMC = ? AND dv.GG = ? AND
    /**
     * 通过用户id获取用户的名字
     */
    public static final String GET_NAME_BY_ID = "SELECT userinfo.USERNAME as name FROM userinfo WHERE USERID = ?";
    /**
     * 获取转场记录
     */
    public static final String GET_MOVE_RECORD = "SELECT devicemove.*, userinfo.USERNAME as czrname FROM " +
            "devicemove, userinfo WHERE devicemove.SBBH = ? AND userinfo.USERID = devicemove.CZR";
    /**
     * 获取巡检记录
     */
    public static final String GET_INSPECTION_RECORD = "SELECT deviceinspection.*, userinfo.USERNAME as czrname FROM " +
            "deviceinspection, userinfo WHERE deviceinspection.SBBH = ? AND userinfo.USERID = deviceinspection.CZR";

    public static final String GetDeviceApplyINPage = "SELECT deviceapply.*, userinfo.USERNAME as czrname " +
            " FROM deviceapply, userinfo WHERE userinfo.USERID = deviceapply.SQR";
    /**
     * 查找设备入库记录，分页加载
     */
    public static final String GetDeviceINPage = "SELECT devicestore.*, userinfo.USERNAME as czrname " +
            "FROM devicestore, userinfo, device AS dv WHERE userinfo.USERID = devicestore.CZR AND dv.BH = devicestore.SBBH";
    /**
     * 查找设备出库记录，分页加载
     */
    public static final String GetDeviceOutPage = "SELECT devicestore.*, userinfo.USERNAME as czrname " +
            "FROM devicestore, userinfo, device AS dv WHERE userinfo.USERID = devicestore.CZR AND dv.BH = devicestore.SBBH";
    /**
     * 查找设备维修记录，分页加载， 类别3,4,5
     */
    public static final String GetDeviceRepairPage = "SELECT devicestore.*, userinfo.USERNAME as czrname " +
            "FROM devicestore, userinfo, device AS dv WHERE userinfo.USERID = devicestore.CZR AND dv.BH = devicestore.SBBH";
    /**
     * 查找报废设备记录，分页加载
     */
    public static final String GET_SCRAP_DEVICE_PAGE = "SELECT devicescrap.*, userinfo.USERNAME as bfrname " +
            "FROM devicescrap, userinfo, device AS dv WHERE userinfo.USERID = devicescrap.BFR AND dv.BH = devicescrap.SBBH";
    /**
     * 获取转场记录,加分页
     */
    public static final String GET_MOVE_RECORD_PAGE = "SELECT devicemove.*, userinfo.USERNAME as czrname " +
            "FROM devicemove, userinfo, device AS dv WHERE userinfo.USERID = devicemove.CZR AND dv.BH = devicemove.SBBH";
    /**
     * 获取巡检记录， 加分页
     */
    public static final String GET_INSPECTION_RECORD_PAGE = "SELECT deviceinspection.*, userinfo.USERNAME as czrname " +
            "FROM deviceinspection, userinfo, device AS dv WHERE userinfo.USERID = deviceinspection.CZR AND dv.BH = deviceinspection.SBBH";
    /**
     * 修改密码
     */
    public static final String CHANGE_PASSWORD = "UPDATE userinfo SET PASSWORD = ? WHERE USERID = ?";
}
