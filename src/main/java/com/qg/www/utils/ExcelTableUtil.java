package com.qg.www.utils;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.qg.www.enums.AwardHead;
import com.qg.www.enums.UserInfoHead;
import com.qg.www.models.AwardInfo;
import com.qg.www.models.UserInfo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author net
 * @version 1.0
 * excel表格处理工具；
 */
public class ExcelTableUtil {
    /**
     * 创建奖项EXCEL表格
     *
     * @param awardInfos 奖项列表
     * @return 该文件的路径；
     */
    public static String createAwardExcel(List<AwardInfo> awardInfos) throws IOException {
        //记录路径
        String path;
        try (OutputStream out = new FileOutputStream("奖项.xls")) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS);
            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName("sheet1");
            List<List<String>> data = new ArrayList<>();
            //列表不为空才创建迭代进行遍历创建excel文件；
            if (!awardInfos.isEmpty()) {
                Iterator<AwardInfo> iterator = awardInfos.iterator();
                while (iterator.hasNext()) {
                    List<String> list = new ArrayList<>();
                    AwardInfo awardInfo1 = iterator.next();
                    list.add(awardInfo1.getAwardName());
                    list.add(awardInfo1.getAwardTime());
                    list.add(awardInfo1.getAwardLevel());
                    list.add(awardInfo1.getRank());
                    list.add(awardInfo1.getDepartment());
                    list.add(awardInfo1.getLeadTeacher());
                    list.add(awardInfo1.getJoinStudent());
                    list.add(awardInfo1.getAwardDescription());
                    list.add(awardInfo1.getAwardProject());
                    data.add(list);
                }
                List<List<String>> head = new ArrayList<>();
                List<String> headCoulumn1 = new ArrayList<>();
                List<String> headCoulumn2 = new ArrayList<>();
                List<String> headCoulumn3 = new ArrayList<>();
                List<String> headCoulumn4 = new ArrayList<>();
                List<String> headCoulumn5 = new ArrayList<>();
                List<String> headCoulumn6 = new ArrayList<>();
                List<String> headCoulumn7 = new ArrayList<>();
                List<String> headCoulumn8 = new ArrayList<>();
                List<String> headCoulumn9 = new ArrayList<>();
                headCoulumn1.add(AwardHead.AWARD_NAME.getAwardHead());
                headCoulumn2.add(AwardHead.AWARD_GET_TIME.getAwardHead());
                headCoulumn3.add(AwardHead.AWARD_LEVEL.getAwardHead());
                headCoulumn4.add(AwardHead.AWARD_RANK.getAwardHead());
                headCoulumn5.add(AwardHead.AWARD_DEPARTMENT.getAwardHead());
                headCoulumn6.add(AwardHead.LEAD_TEACHER.getAwardHead());
                headCoulumn7.add(AwardHead.JOIN_STUDENT.getAwardHead());
                headCoulumn8.add(AwardHead.AWARD_DESCRIPTION.getAwardHead());
                headCoulumn9.add(AwardHead.AWARD_PROJECT.getAwardHead());
                head.add(headCoulumn1);
                head.add(headCoulumn2);
                head.add(headCoulumn3);
                head.add(headCoulumn4);
                head.add(headCoulumn5);
                head.add(headCoulumn6);
                head.add(headCoulumn7);
                head.add(headCoulumn8);
                head.add(headCoulumn9);
                Table table = new Table(1);
                table.setHead(head);
                writer.write0(data, sheet1, table);
                writer.finish();
            }
        }
        File file = new File("奖项.xls");
        path = file.getAbsolutePath();
        return path;
    }

    /**
     * 创建用户信息EXCEL表格
     *
     * @param userInfoList 成员信息表格
     * @return 该文件的路径；
     */
    public static String createUserExcel(List<UserInfo> userInfoList) throws IOException {
        String path;
        try (OutputStream out = new FileOutputStream("成员信息.xls")) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS);
            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName("sheet1");
            List<List<String>> data = new ArrayList<>();
            //列表不为空才创建迭代进行遍历创建excel文件；
            if (!userInfoList.isEmpty()) {
                Iterator<UserInfo> iterator = userInfoList.iterator();
                while (iterator.hasNext()) {
                    List<String> list = new ArrayList<>();
                    UserInfo userInfo = iterator.next();
                    list.add(userInfo.getName());
                    list.add(userInfo.getGroup());
                    list.add(userInfo.getCollege());
                    list.add(userInfo.getGrade());
                    list.add(userInfo.getTel());
                    list.add(userInfo.getBirthplace());
                    list.add(userInfo.getQq());
                    list.add(userInfo.getEmail());
                    list.add(userInfo.getDescription());
                    data.add(list);
                }
                List<List<String>> head = new ArrayList<>();
                List<String> headCoulumn1 = new ArrayList<>();
                List<String> headCoulumn2 = new ArrayList<>();
                List<String> headCoulumn3 = new ArrayList<>();
                List<String> headCoulumn4 = new ArrayList<>();
                List<String> headCoulumn5 = new ArrayList<>();
                List<String> headCoulumn6 = new ArrayList<>();
                List<String> headCoulumn7 = new ArrayList<>();
                List<String> headCoulumn8 = new ArrayList<>();
                List<String> headCoulumn9 = new ArrayList<>();
                headCoulumn1.add(UserInfoHead.USERNAME.getUserInfoHead());
                headCoulumn2.add(UserInfoHead.USER_GROUP.getUserInfoHead());
                headCoulumn3.add(UserInfoHead.COLLEGE.getUserInfoHead());
                headCoulumn4.add(UserInfoHead.STUDENT_NUMBER.getUserInfoHead());
                headCoulumn5.add(UserInfoHead.TELEPHONE.getUserInfoHead());
                headCoulumn6.add(UserInfoHead.BIRTHPLACE.getUserInfoHead());
                headCoulumn7.add(UserInfoHead.QQ.getUserInfoHead());
                headCoulumn8.add(UserInfoHead.EMAIL.getUserInfoHead());
                headCoulumn9.add(UserInfoHead.DESCRIPTION.getUserInfoHead());
                head.add(headCoulumn1);
                head.add(headCoulumn2);
                head.add(headCoulumn3);
                head.add(headCoulumn4);
                head.add(headCoulumn5);
                head.add(headCoulumn6);
                head.add(headCoulumn7);
                head.add(headCoulumn8);
                head.add(headCoulumn9);
                Table table = new Table(1);
                table.setHead(head);
                writer.write0(data, sheet1, table);
                writer.finish();
            }
        }
        File file = new File("成员信息.xls");
        path = file.getAbsolutePath();
        return path;
    }

    /**
     * 读取excel
     *
     * @param filePath 文件路径
     * @return 成员信息列表
     */
    public static List<UserInfo> readUserInfoExcel(String filePath) {
        final List<UserInfo> userInfoList = new ArrayList<>();
        try {
            try (InputStream in = new FileInputStream(filePath)) {
                AnalysisEventListener<List<String>> listener = new AnalysisEventListener<List<String>>() {
                    @Override
                    public void invoke(List<String> object, AnalysisContext context) {
                        System.out.println("Row:" + context.getCurrentRowNum() + " Data:" + object);
                        //读取第一行真实数据；
                        if (context.getCurrentRowNum() > 0) {
                            UserInfo userInfo = new UserInfo();
                            if (object.size() >= 1) {
                                userInfo.setName(object.get(0));
                            }
                            if (object.size() >= 2) {
                                userInfo.setGroup(object.get(1));
                            }
                            if (object.size() >= 3) {
                                userInfo.setCollege(object.get(2));
                            }
                            if (object.size() >= 4) {
                                userInfo.setGrade(object.get(3));
                            }
                            if (object.size() >= 5) {
                                userInfo.setTel(object.get(4));
                            }
                            if (object.size() >= 6) {
                                userInfo.setBirthplace(object.get(5));
                            }
                            if (object.size() >= 7) {
                                userInfo.setQq(object.get(6));
                            }
                            if (object.size() >= 8) {
                                userInfo.setEmail(object.get(7));
                            }
                            if (object.size() >= 9) {
                                userInfo.setDescription(object.get(8));
                            }
                            userInfoList.add(userInfo);
                        }
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                        System.err.println("doAfterAllAnalysed...");
                    }
                };
                ExcelReader excelReader = null;
                try {
                    excelReader = ExcelReaderFactory.getExcelReader(in, null, listener);
                } catch (InvalidFormatException e) {
                    System.out.println("文件格式无效，读取失败。");
                }
                if (excelReader != null) {
                    excelReader.read();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfoList;
    }

    /**
     * 读取excel文件
     *
     * @param filePath 文件存储路径
     * @return 奖项信息列表
     */
    public static List<AwardInfo> readAwardInfoExcel(String filePath) {
        final List<AwardInfo> awardInfoList = new ArrayList<>();
        try {
            try (InputStream in = new FileInputStream(filePath)) {
                AnalysisEventListener<List<String>> listener = new AnalysisEventListener<List<String>>() {
                    @Override
                    public void invoke(List<String> object, AnalysisContext context) {
                        System.out.println("Row:" + context.getCurrentRowNum() + " Data:" + object);
                        //读取第一行真实数据；
                        if (context.getCurrentRowNum() > 0) {
                            AwardInfo awardInfo = new AwardInfo();
                            if (object.size() >= 1) {
                                awardInfo.setAwardName(object.get(0));
                            }
                            if (object.size() >= 2) {
                                awardInfo.setAwardTime(object.get(1));
                            }
                            if (object.size() >= 3) {
                                awardInfo.setAwardLevel(object.get(2));
                            }
                            if (object.size() >= 4) {
                                awardInfo.setRank(object.get(3));
                            }
                            if (object.size() >= 5) {
                                awardInfo.setDepartment(object.get(4));
                            }
                            if (object.size() >= 6) {
                                awardInfo.setLeadTeacher(object.get(5));
                            }
                          /*  if (object.size() >= 7) {
                                awardInfo.setJoinStudent(object.get(6));
                            }*/
                            if (object.size() >= 7) {
                                awardInfo.setAwardDescription(object.get(6));
                            }
                            if (object.size() >= 8) {
                                awardInfo.setAwardProject(object.get(7));
                            }
                            awardInfoList.add(awardInfo);
                        }
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                        System.err.println("doAfterAllAnalysed...");
                    }
                };
                ExcelReader excelReader = null;
                try {
                    excelReader = ExcelReaderFactory.getExcelReader(in, null, listener);
                } catch (InvalidFormatException e) {
                    System.out.println("文件格式无效，读取失败。");
                }
                if (excelReader != null) {
                    excelReader.read();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return awardInfoList;
    }
}
