package com.qg.www.service.impl;

import com.qg.www.dao.AwardInfoDao;
import com.qg.www.dtos.ResponseData;
import com.qg.www.enums.Status;
import com.qg.www.models.AwardInfo;
import com.qg.www.service.AwardService;
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
 * 奖项处理业务实现类
 */
@Service
public class AwardServiceImpl implements AwardService {
    @Resource
    private AwardInfoDao awardInfoDao;
    @Resource
    private ResponseData responseData;

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
        List<AwardInfo> awardInfoList = awardInfoDao.queryAwardInfo();
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

    /**
     * 奖项信息导入业务
     *
     * @param file     文件
     * @param filePath 文件路径
     * @return 状态码
     */
    @Override
    public ResponseData importExcel(MultipartFile file, String filePath) {
        if (!file.isEmpty()) {
            File dir = new File(filePath + File.separator + "awardFile");
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
            List<AwardInfo> awardInfoList = ExcelTableUtil.readAwardInfoExcel(storeFile.getAbsolutePath());
            if (!awardInfoList.isEmpty()){
                Iterator<AwardInfo> iterator=awardInfoList.iterator();
                while(iterator.hasNext()){
                    AwardInfo awardInfo=iterator.next();
                    awardInfoDao.addAwardInfo(awardInfo);
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
