package com.qg.www.service.impl;

import com.qg.www.dao.UserInfoDao;
import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import com.qg.www.enums.Status;
import com.qg.www.models.UserInfo;
import com.qg.www.service.UserInfoService;
import com.qg.www.utils.ExcelTableUtil;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author net
 * @version 1.0
 * 成员信息业务实现类
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    ResponseData responseData;
    @Resource
    private UserInfoDao userInfoDao;

    /**
     * 导出excel表格业务
     *
     * @return 文件路径
     */
    @Override
    public String exportExcel() {
        String path = "";
        List<UserInfo> userInfoList = userInfoDao.queryUserInfo();
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
        RowBounds rowBounds = new RowBounds(data.getPage(),8);
        // 得到成员信息列表
        List<UserInfo> userInfoList = userInfoDao.queryAppointedUserInfo(data,rowBounds);
        // 放入返回参数
        if(!userInfoList.isEmpty()){
            responseData.setStatus(Status.NORMAL.getStatus());
            responseData.setUserInfoList(userInfoList);
        }else {
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
        if(null != userInfo){
            responseData.setStatus(Status.NORMAL.getStatus());
            responseData.setUserInfo(userInfo);
        }else {
            responseData.setStatus(Status.INFO_LACK.getStatus());
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
    public ResponseData addUserInfoPicture(MultipartFile picture, String path, String userInfoId) {
        ResponseData responseData = new ResponseData();
        String fileName;
        //上传图片不为空，获取原始文件名
        if (null != picture) {
            fileName = picture.getOriginalFilename();
            //判断图片格式
            if (fileName.endsWith(".jpg")||fileName.endsWith(".png")){
                //创建文件夹
                File dir=new File(path+File.separator+"userImg");
                if (!dir.exists()){
                    dir.mkdirs();
                }
                File storeFile=new File(dir.getAbsolutePath()+File.separator+".jpg");
                try {
                    picture.transferTo(storeFile);
                    FileUtils.copyFile(storeFile,new File("D:\\QG\\InfoManageSystem\\src\\main\\webapp\\userImg"+File.separator+userInfoId+".jpg"));
                    userInfoDao.addUserInfoPicture(Integer.valueOf(userInfoId),userInfoId+".jpg");
                } catch (IOException e) {
                    //存储文件失败
                    responseData.setStatus(Status.SERVER_HAPPEN_ERROR.getStatus());
                }
            }else {
                responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
            }
        }else {
            responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
        }
        return responseData;
    }
}
