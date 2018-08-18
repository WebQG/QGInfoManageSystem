package com.qg.www.service.impl;

import com.qg.www.dao.AwardInfoDao;
import com.qg.www.models.AwardInfo;
import com.qg.www.service.AwardService;
import com.qg.www.utils.ExcelTableUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author net
 * @version 1.0
 * 奖项处理业务实现类
 */
@Service
public class AwardServiceImpl implements AwardService {
    @Resource
    private AwardInfoDao awardInfoDao;

    /**
     * 导出excel表格业务
     *
     * @return 文件路径
     */
    @Override
    public String exportExcel() {
        //定义路径变量；
        String path="";
        //查询全部信息
        List<AwardInfo> awardInfoList = awardInfoDao.queryAwardInfo(null, null, null);
        //奖项列表不为空，进行excel文件的创建；
        if (!awardInfoList.isEmpty()) {
            try {
                path=ExcelTableUtil.createAwardExcel(awardInfoList);
            } catch (IOException e) {
                //创建文件异常
                e.printStackTrace();
            }
        }else {
            //出现服务器端错误或者内容为空
            path=new File("ERROR.txt").getAbsolutePath();
        }
        return path;
    }
}
