package com.qg.www.service.impl;

import com.qg.www.dao.UserInfoDao;
import com.qg.www.dtos.ResponseData;
import com.qg.www.enums.Status;
import com.qg.www.models.UserInfo;
import com.qg.www.service.UserInfoService;
import com.qg.www.utils.ExcelTableUtil;
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
            File storeFile = new File(dir.getAbsolutePath() + File.separator + file.getName()+".xlsx");
            try {
                file.transferTo(storeFile);
            } catch (IOException e) {
                responseData.setStatus(Status.SERVER_HAPPEN_ERROR.getStatus());
            }
            //获取文件中的成员信息；
            List<UserInfo> userInfoList = ExcelTableUtil.readUserInfoExcel(storeFile.getAbsolutePath());
            if (!userInfoList.isEmpty()){
                Iterator<UserInfo> iterator=userInfoList.iterator();
                while(iterator.hasNext()){
                    UserInfo userInfo=iterator.next();
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
}
