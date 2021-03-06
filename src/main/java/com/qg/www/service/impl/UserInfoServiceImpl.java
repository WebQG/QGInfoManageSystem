package com.qg.www.service.impl;

import com.qg.www.dao.AwardInfoDao;
import com.qg.www.dao.UserAndAwardDao;
import com.qg.www.dao.UserInfoDao;
import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import com.qg.www.enums.Status;
import com.qg.www.enums.UserOperate;
import com.qg.www.models.AwardInfo;
import com.qg.www.models.UserInfo;
import com.qg.www.service.UserInfoService;
import com.qg.www.utils.ExcelTableUtil;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author net
 * @version 1.0
 * 成员信息业务实现类
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private ResponseData responseData;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private AwardInfoDao awardInfoDao;
    @Resource
    private UserAndAwardDao userAndAwardDao;

    /**
     * 导出excel表格业务
     *
     * @param userInfoId 成员信息ID
     * @return 文件路径
     */
    @Override
    public String exportExcel(String userInfoId) {
        System.out.println(userInfoId);
        String path = "";
        List<UserInfo> userInfoList = new ArrayList<>();
        if (userInfoId == null || "".equals(userInfoId)) {
            //查询全部成员信息；
            userInfoList = userInfoDao.queryUserInfo();
        } else {
            RequestData data = new RequestData();
            //异常处理
            try {
                data.setUserInfoId(Integer.valueOf(userInfoId));
            } catch (NumberFormatException e) {
                System.out.println("页面数据被修改，传递的成员ID格式错误！");
                return new File("ERROR.txt").getAbsolutePath();
            }
            userInfoList.add(userInfoDao.getUserInfoById(data));
        }

        //如果不为空，填写excel表格
        if (!userInfoList.isEmpty()) {
            try {
                path = ExcelTableUtil.createUserExcel(userInfoList);
            } catch (IOException e) {
                //创建失败异常
                e.printStackTrace();
            }
        } else {
            path = new File("ERROR.txt").getAbsolutePath();
        }
        return path;
    }

    /**
     * 导入excel文件
     *
     * @param file     文件
     * @param filePath 文件存放路径
     * @return 状态码
     */
    @Override
    public ResponseData importExcel(MultipartFile file, String filePath) {
        if (!file.isEmpty()) {
            File dir = new File(filePath + File.separator + "tmpFile");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //存储文件
            File storeFile = new File(dir.getAbsolutePath() + File.separator + file.getName() + ".xlsx");
            try {
                file.transferTo(storeFile);
            } catch (IOException e) {
                responseData.setStatus(Status.SERVER_HAPPEN_ERROR.getStatus());
            }
            //获取文件中的成员信息；
            List<UserInfo> userInfoList = ExcelTableUtil.readUserInfoExcel(storeFile.getAbsolutePath());
            if (!userInfoList.isEmpty()) {
                Iterator<UserInfo> iterator = userInfoList.iterator();
                while (iterator.hasNext()) {
                    UserInfo userInfo = iterator.next();
                    userInfoDao.addUserInfo(userInfo);
                }
            }
            //正常
            responseData.setStatus(Status.NORMAL.getStatus());
        } else {
            //由于文件数据的缺失
            responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
        }
        return responseData;
    }

    /**
     * 根据关键字查询成员信息
     *
     * @param data 所属组别、所属年级
     * @return 编号、名字、组别、年级、图片地址
     */
    @Override
    public ResponseData queryUserInfo(RequestData data) {
        ResponseData responseData = new ResponseData();
        // 分页信息，8条为一页
        RowBounds rowBounds = new RowBounds(data.getPage() * 8, 8);
        // 得到成员信息列表
        List<UserInfo> userInfoList = userInfoDao.queryAppointedUserInfo(data, rowBounds);
        // 放入返回参数
        if (!userInfoList.isEmpty()) {
            responseData.setStatus(Status.NORMAL.getStatus());
            responseData.setUserInfoList(userInfoList);
        } else {
            responseData.setStatus(Status.INFO_LACK.getStatus());
        }
        return responseData;
    }

    /**
     * 根据ID查找成员信息
     *
     * @param data 成员ID
     * @return 成员详细信息
     */
    @Override
    public ResponseData getUserInfo(RequestData data) {
        ResponseData responseData = new ResponseData();
        // 根据ID得到成员信息
        UserInfo userInfo = userInfoDao.getUserInfoById(data);
        // 加入返回参数
        if (null != userInfo) {
            responseData.setStatus(Status.NORMAL.getStatus());
            responseData.setUserInfo(userInfo);
        } else {
            responseData.setStatus(Status.INFO_LACK.getStatus());
            responseData.setUserInfo(new UserInfo());
        }
        return responseData;
    }

    /**
     * 添加成员头像
     *
     * @param picture    照片
     * @param path       路径
     * @param userInfoId 用户信息ID
     * @return 状态码
     */
    @Override
    public synchronized ResponseData addUserInfoPicture(MultipartFile picture, String path, String userInfoId) {
        ResponseData responseData = new ResponseData();
        String fileName;
        System.out.println(picture == null);
        //上传图片不为空，获取原始文件名
        if (null != picture) {
            fileName = picture.getOriginalFilename();
            System.out.println(fileName);
            //判断图片格式
            if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
                //创建文件夹
                File dir = new File(path + File.separator + "userImg");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String uuid = UUID.randomUUID() + "";
                File storeFile = new File(dir.getAbsolutePath() + File.separator + uuid + userInfoId + ".jpg");
                try {
                    picture.transferTo(storeFile);
                    /* FileUtils.copyFile(storeFile,new File("D:\\QG\\InfoManageSystem\\src\\main\\webapp\\userImg"+File.separator+userInfoId+".jpg"));*/
                    userInfoDao.addUserInfoPicture(Integer.valueOf(userInfoId), uuid + userInfoId + ".jpg");
                    responseData.setStatus(Status.NORMAL.getStatus());
                } catch (IOException e) {
                    //存储文件失败
                    responseData.setStatus(Status.SERVER_HAPPEN_ERROR.getStatus());
                }
            } else {
                responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
            }
        } else {
            responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
        }
        return responseData;
    }

    /**
     * 提供给安卓的成员信息搜索接口
     *
     * @param data key 1为模糊搜索 2为精确搜索 、所属组别、所属年级
     * @return 编号、名字、组别、年级、图片地址
     */
    @Override
    public ResponseData queryUserInfoAndroid(RequestData data) {
        ResponseData responseData = new ResponseData();
        List<UserInfo> userInfoList;
        if (null != data.getName()) {
            // 模糊搜索
            userInfoList = userInfoDao.queryUserInfoByName(data);
        } else {
            // 精确搜索
            userInfoList = userInfoDao.queryAppointedUserInfo(data);
        }

        if (!userInfoList.isEmpty()) {
            // 将成员列表放入参数返回
            responseData.setStatus(Status.NORMAL.getStatus());
            responseData.setUserInfoList(userInfoList);
        } else {
            responseData.setUserInfo(new UserInfo());
            responseData.setStatus(Status.INFO_LACK.getStatus());
        }
        return responseData;
    }

    /**
     * 导出分类成员信息
     *
     * @param requestData 请求信息参数
     * @return 文件路径
     */
    @Override
    public String exportSomeOneInfoExcel(RequestData requestData) {
        String grade = requestData.getGrade();
        String group = requestData.getGroup();
        List<UserInfo> userInfoList;
        //当数据全为空的时候导出所有成员信息，否则，按照分类导出。
        if ((grade == null || "".equals(grade)) && (group == null || "".equals(group))) {
            userInfoList = userInfoDao.queryUserInfo();
        } else {
            userInfoList = userInfoDao.queryUserInfoByGroupAndGrade(requestData);
        }
        //定义路径
        String path;
        try {
            path = ExcelTableUtil.createUserExcel(userInfoList);
        } catch (IOException e) {
            //创建失败则返回错误提示文件的路径。
            path = new File("ERROR.txt").getAbsolutePath();
        }
        return path;
    }

    /**
     * 修改成员详细信息
     *
     * @param data 成员信息
     * @return 状态码
     */
    @Override
    public ResponseData updateUserInfo(RequestData data) {
        userInfoDao.updateUserInfo(data);
        ResponseData responseData = new ResponseData();
        responseData.setStatus(Status.NORMAL.getStatus());
        return responseData;
    }

    /**
     * 删除成员信息
     *
     * @param data      成员ID
     * @param privilege 权限
     * @return 状态码；
     */
    @Override
    public ResponseData deleteUserInfo(RequestData data, Integer privilege) {
        ResponseData responseData = new ResponseData();
        //判断有没有权限；
        if (!UserOperate.ADMIN_PRIVILEGE.getUserOperateCode().equals(privilege)) {
            responseData.setStatus(Status.NO_PROVILEGE.getStatus());
            return responseData;
        }
        //删除奖项；
        int solveNum = userInfoDao.deleteUserInfo(data);
        if (solveNum == 0) {
            responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
        } else {
            responseData.setStatus(Status.NORMAL.getStatus());
        }
        return responseData;
    }

    /**
     * 查看某位成员的获奖信息
     *
     * @param data      成员的ID
     * @param privilege 权限
     * @return 奖项列表和状态码
     */
    @Override
    public ResponseData showSomeOneAwards(RequestData data, String privilege) {
        ResponseData responseData = new ResponseData();
        //判断有没有登录权限；
        if ("".equals(privilege) || privilege == null) {
            responseData.setStatus(Status.NO_PROVILEGE.getStatus());
            return responseData;
        }
        List<Integer> awardIdList = userAndAwardDao.queryAwardInfoId(data);
        List<AwardInfo> awardInfoList = new LinkedList<>();
        if (!awardIdList.isEmpty()) {
            for (Integer i : awardIdList) {
                data.setAwardId(i);
                awardInfoList.add(awardInfoDao.getAwardInfoById(data));
            }
            responseData.setStatus(Status.NORMAL.getStatus());
            responseData.setAwardInfoList(awardInfoList);
        }
        return responseData;
    }
}
