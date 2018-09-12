package com.qg.www.service.impl;

import com.qg.www.dao.AwardInfoDao;
import com.qg.www.dao.UserAndAwardDao;
import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import com.qg.www.enums.Status;
import com.qg.www.enums.UserInfoHead;
import com.qg.www.enums.UserOperate;
import com.qg.www.models.AwardInfo;
import com.qg.www.models.UserAndAward;
import com.qg.www.models.UserInfo;
import com.qg.www.service.AwardService;
import com.qg.www.utils.ExcelTableUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author net
 * @version 1.0
 * 奖项处理业务实现类
 */
@Service
public class AwardServiceImpl implements AwardService {
    @Resource
    private AwardInfoDao awardInfoDao;
    @Resource
    private UserAndAwardDao userAndAwardDao;

    /**
     * 导出excel表格业务
     *
     * @param awardInfoId 奖项ID
     * @return 文件路径
     */
    @Override
    public String exportExcel(String awardInfoId) {
        //定义路径变量；
        String path = "";
        List<AwardInfo> awardInfoList = new ArrayList<>();
        if (awardInfoId == null || "".equals(awardInfoId)) {
            //查询全部信息
            awardInfoList = awardInfoDao.queryAwardInfo();
        } else {
            //导出单一信息；
            RequestData data = new RequestData();
            //处理异常；
            try {
                data.setAwardId(Integer.valueOf(awardInfoId));
            } catch (NumberFormatException e) {
                //出现
                System.out.println("页面数据被修改，传递的奖项ID格式错误！");
                return new File("ERROR.txt").getAbsolutePath();
            }
            awardInfoList.add(awardInfoDao.getAwardInfoById(data));
        }
        //奖项列表不为空，进行excel文件的创建；
        if (!awardInfoList.isEmpty()) {
            try {
                path = ExcelTableUtil.createAwardExcel(awardInfoList);
            } catch (IOException e) {
                //创建文件异常
                e.printStackTrace();
            }
        } else {
            //出现服务器端错误或者内容为空
            path = new File("ERROR.txt").getAbsolutePath();
        }
        return path;
    }

    /**
     * 奖项信息导入业务
     *
     * @param file     文件
     * @param filePath 文件路径
     * @return 状态码
     */
    @Override
    public ResponseData importExcel(MultipartFile file, String filePath) {
        ResponseData responseData = new ResponseData();
        if (!file.isEmpty()) {
            File dir = new File(filePath + File.separator + "awardFile");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = file.getOriginalFilename();
            if (null != fileName && (fileName.endsWith(".xlsx") || fileName.endsWith("xls"))) {
                //存储文件
                File storeFile = new File(dir.getAbsolutePath() + File.separator + file.getName() + ".xlsx");
                try {
                    file.transferTo(storeFile);
                } catch (IOException e) {
                    responseData.setStatus(Status.SERVER_HAPPEN_ERROR.getStatus());
                }
                //获取文件中的成员信息；
                List<AwardInfo> awardInfoList = ExcelTableUtil.readAwardInfoExcel(storeFile.getAbsolutePath());
                if (!awardInfoList.isEmpty()) {
                    Iterator<AwardInfo> iterator = awardInfoList.iterator();
                    while (iterator.hasNext()) {
                        AwardInfo awardInfo = iterator.next();
                        awardInfoDao.addAwardInfo(awardInfo);
                    }
                }
                //正常
                responseData.setStatus(Status.NORMAL.getStatus());
            } else {
                responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
            }

        } else {
            //由于文件数据的缺失
            responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
        }
        return responseData;
    }

    /**
     * 查询奖项列表
     *
     * @param data 页数、获奖年份、奖项级别、获奖等级
     * @return 奖项列表
     */
    @Override
    public ResponseData queryAwardInfo(RequestData data) {
        ResponseData responseData = new ResponseData();
        // 分页、每页8条
        RowBounds rowBounds = new RowBounds(data.getPage() * 8, 8);
        // 在尾部增加年
        if (null != data.getAwardTime() && !"".equals(data.getAwardTime())) {
            data.setAwardTime(data.getAwardTime());
        }
        // 得到奖项信息列表
        List<AwardInfo> awardInfoList = awardInfoDao.queryAppointedAwardInfo(data, rowBounds);
        if (!awardInfoList.isEmpty()) {
            responseData.setStatus(Status.NORMAL.getStatus());
            responseData.setAwardInfoList(awardInfoList);
        } else {
            // 返回信息缺少
            responseData.setStatus(Status.INFO_LACK.getStatus());
        }
        return responseData;
    }

    /**
     * 根据ID查询奖项详细信息
     *
     * @param data 奖项ID
     * @return 奖项详细信息
     */
    @Override
    public ResponseData getAwardInfoById(RequestData data) {
        ResponseData responseData = new ResponseData();
        // 通过ID得到奖项信息
        AwardInfo awardInfo = awardInfoDao.getAwardInfoById(data);
        // 加入返回参数
        if (null != awardInfo) {
            responseData.setAwardInfo(awardInfo);
            responseData.setStatus(Status.NORMAL.getStatus());
        } else {
            responseData.setAwardInfo(new AwardInfo());
            responseData.setStatus(Status.INFO_LACK.getStatus());
        }
        return responseData;
    }


    /**
     * 添加图片
     *
     * @param picture 图片文件
     * @param path    存放路径
     * @return 状态码
     */
    @Override
    public synchronized ResponseData addAwardInfoPicture(MultipartFile picture, String path, String awardId) {
        ResponseData responseData = new ResponseData();
        String fileName;
        System.out.println("奖项图片是否空" + picture == null);
        //上传的图片不为空,获取原始文件名。
        if (null != picture) {
            fileName = picture.getOriginalFilename();
            //图片符合格式，则正常上传；
            if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
                //创建文件夹
                File dir = new File(path + File.separator + "img");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String uuid = UUID.randomUUID() + "";
                File storeFile = new File(dir.getAbsolutePath() + File.separator + uuid + awardId + ".jpg");
                try {
                    picture.transferTo(storeFile);
                    awardInfoDao.addAwardInfoPicture(Integer.valueOf(awardId), uuid + awardId + ".jpg");
                    responseData.setStatus(Status.NORMAL.getStatus());
                } catch (IOException e) {
                    //存储失败
                    responseData.setStatus(Status.SERVER_HAPPEN_ERROR.getStatus());
                }
            } else {
                //返回状态码。
                responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
            }
        } else {
            //上传图片缺失。
            responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
        }
        return responseData;
    }

    /**
     * 查询奖项列表
     *
     * @param data 获奖年份、奖项级别、获奖等级
     * @return 奖项列表
     */
    @Override
    public ResponseData queryAwardInfoAndroid(RequestData data) {
        ResponseData responseData = new ResponseData();
        List<AwardInfo> awardInfoList;
        if (null != data.getAwardTime() && !"".equals(data.getAwardTime())) {
            data.setAwardTime(data.getAwardTime());
        }
        if (null != data.getName()) {
            // 模糊搜索
            awardInfoList = awardInfoDao.queryAwardInfoByName(data);
        } else {
            // 精确搜索
            awardInfoList = awardInfoDao.queryAppointedAwardInfo(data);
        }

        if (!awardInfoList.isEmpty()) {
            // 将成员列表放入参数返回
            responseData.setStatus(Status.NORMAL.getStatus());
            responseData.setAwardInfoList(awardInfoList);
        } else {
            responseData.setStatus(Status.INFO_LACK.getStatus());
            responseData.setAwardInfoList(new ArrayList<AwardInfo>());
        }
        return responseData;
    }

    /**
     * 导出分类奖项信息；
     *
     * @param data 分类数据
     * @return 文件路径；
     */
    @Override
    public String exportSomeAwardInfo(RequestData data) {
        String awardTime = data.getAwardTime();
        String awardLevel = data.getAwardLevel();
        String rank = data.getRank();
        List<AwardInfo> awardInfoList;
        if ((awardTime == null || "".equals(awardTime)) && (rank == null || "".equals(rank)) && (awardLevel == null || "".equals(awardLevel))) {
            awardInfoList = awardInfoDao.queryAwardInfo();
        } else {
            awardInfoList = awardInfoDao.queryAwardInfoByTimeAndRank(data);
        }
        String path;
        try {
            path = ExcelTableUtil.createAwardExcel(awardInfoList);
        } catch (IOException e) {
            path = new File("ERROR.txt").getAbsolutePath();
        }
        return path;
    }

    /**
     * 修改奖项的详细信息
     *
     * @param data 修改后的奖项信息
     * @return 状态码
     */
    @Override
    public ResponseData updateAwardInfo(RequestData data) {
        System.out.println(awardInfoDao.updateAwardInfo(data));
        // 返回正常状态码
        ResponseData responseData = new ResponseData();
        responseData.setStatus(Status.NORMAL.getStatus());
        return responseData;
    }

    /**
     * 删除奖项详细信息
     *
     * @param data 包含奖项的ID
     * @return 状态码
     */
    @Override
    public ResponseData deleteAwardInfo(RequestData data, Integer privilege) {
        ResponseData responseData = new ResponseData();
        //判断有没有权限；
        if (!UserOperate.ADMIN_PRIVILEGE.getUserOperateCode().equals(privilege)) {
            responseData.setStatus(Status.NO_PROVILEGE.getStatus());
            return responseData;
        }
        //删除奖项；
        int solveNum = awardInfoDao.deleteAwardInfo(data);
        if (solveNum == 0) {
            responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
        } else {
            responseData.setStatus(Status.NORMAL.getStatus());
        }
        return responseData;
    }

    /**
     * 添加成员业务
     *
     * @param data      奖项ID 成员ID
     * @param privilege 判断是否具有权限
     * @return 状态码
     */
    @Override
    public synchronized ResponseData addUserForAward(RequestData data, Integer privilege) {
        ResponseData responseData = new ResponseData();
        if (UserOperate.ADMIN_PRIVILEGE.getUserOperateCode().equals(privilege)) {
            //没有权限
            responseData.setStatus(Status.NO_PROVILEGE.getStatus());
        }
        //TODO 根据奖项ID、成员ID，添加关联关系；
        List<UserInfo> userInfoList = data.getUserInfoList();
        StringBuilder users = new StringBuilder();
        if (userInfoList.size() == 0 || data.getAwardId() == null) {
            responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
        } else {
            for (UserInfo userInfo : userInfoList
                    ) {
                try {
                    userAndAwardDao.addUserAndAwardReaction(userInfo.getUserInfoId(), data.getAwardId());
                } catch (Exception e) {
                    //当为一个奖项添加重复的关联时，就会出现SQL异常，因此捕捉后进行处理。
                    System.out.println("用户尝试为一个奖项添加重复的关联关系...");
                }
                users.append(UserInfoHead.SPLIT_SIGN.getUserInfoHead() + userInfo.getName());
            }
            data.setJoinStudent(users.toString());
            awardInfoDao.updateAwardInfo(data);
            responseData.setStatus(Status.NORMAL.getStatus());
        }
        return responseData;
    }
}
