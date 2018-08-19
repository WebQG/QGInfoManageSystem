package com.qg.www.service.impl;

import com.qg.www.dao.UserInfoDao;
import com.qg.www.dtos.ResponseData;
import com.qg.www.models.UserInfo;
import com.qg.www.service.UserInfoService;
import com.qg.www.utils.ExcelTableUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author net
 * @version 1.0
 * 成员信息业务实现类
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
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
        }else {
            path=new File("ERROR.txt").getAbsolutePath();
        }
        return path;
    }

    /**
     * 导入excel文件
     *
     * @return 状态码
     */
    @Override
    public ResponseData importExcel() {
        return null;
    }
}
