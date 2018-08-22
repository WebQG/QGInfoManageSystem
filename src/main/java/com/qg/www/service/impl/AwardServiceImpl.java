package com.qg.www.service.impl;

import com.qg.www.dao.AwardInfoDao;
import com.qg.www.dtos.RequestData;
import com.qg.www.dtos.ResponseData;
import com.qg.www.enums.Status;
import com.qg.www.models.AwardInfo;
import com.qg.www.models.UserInfo;
import com.qg.www.service.AwardService;
import com.qg.www.utils.ExcelTableUtil;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        String path = "";
        //查询全部信息
        List<AwardInfo> awardInfoList = awardInfoDao.queryAwardInfo();
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
        if (!file.isEmpty()) {
            File dir = new File(filePath + File.separator + "awardFile");
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
        RowBounds rowBounds = new RowBounds(data.getPage() * 8,8);
        // 在尾部增加年
        if(null != data.getAwardTime() && "" != data.getAwardTime()){
            data.setAwardTime(data.getAwardTime() + "年");
        }
        // 得到奖项信息列表
        List<AwardInfo> awardInfoList = awardInfoDao.queryAppointedAwardInfo(data,rowBounds);
        if(!awardInfoList.isEmpty()){
            responseData.setStatus(Status.NORMAL.getStatus());
            responseData.setAwardInfoList(awardInfoList);
        }else {
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
        if(null != awardInfo){
            responseData.setAwardInfo(awardInfo);
            responseData.setStatus(Status.NORMAL.getStatus());
        }else {
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
    public ResponseData addAwardInfoPicture(MultipartFile picture, String path,String awardId) {
        ResponseData responseData = new ResponseData();
        String fileName ;
        //上传的图片不为空,获取原始文件名。
        if (null != picture) {
            fileName = picture.getOriginalFilename();
            //图片符合格式，则正常上传；
            if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
                //创建文件夹
                File dir=new File(path+File.separator+"img");
                if (!dir.exists()){
                    dir.mkdirs();
                }
                File storeFile=new File(dir.getAbsolutePath()+File.separator+awardId+".jpg");
                try {
                    picture.transferTo(storeFile);
                    FileUtils.copyFile(storeFile,new File("E:\\JavaWeb\\QGInfoManager\\src\\main\\webapp\\img"+File.separator+awardId+".jpg"));
                   awardInfoDao.addAwardInfoPicture(Integer.valueOf(awardId),awardId+".jpg");
                   responseData.setStatus(Status.NORMAL.getStatus());
                } catch (IOException e) {
                    //存储失败
                    responseData.setStatus(Status.SERVER_HAPPEN_ERROR.getStatus());
                }
            } else {
                //返回状态码。
                responseData.setStatus(Status.DATA_FORMAT_ERROR.getStatus());
            }
        }else {
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
        if(null != data.getAwardTime() && !"".equals(data.getAwardTime())){
            data.setAwardTime(data.getAwardTime() + "年");
        }
        if(null != data.getName()){
            // 模糊搜索
            awardInfoList = awardInfoDao.queryAwardInfoByName(data);
        }else{
            // 精确搜索
            awardInfoList = awardInfoDao.queryAppointedAwardInfo(data);
        }

        if(!awardInfoList.isEmpty()){
            // 将成员列表放入参数返回
            responseData.setStatus(Status.NORMAL.getStatus());
            responseData.setAwardInfoList(awardInfoList);
        }else {
            responseData.setStatus(Status.INFO_LACK.getStatus());
            responseData.setAwardInfoList(new ArrayList<>());
        }
        return responseData;
    }
}
