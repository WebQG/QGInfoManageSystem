package com.qg.www.utils;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.qg.www.enums.AwardHead;
import com.qg.www.models.AwardInfo;
import com.qg.www.models.UserInfo;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
        try (OutputStream out = new FileOutputStream("奖项.xlsx")) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
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
                headCoulumn1.add(AwardHead.AWARD_NAME.getAwardHead());
                headCoulumn2.add(AwardHead.AWARD_GET_TIME.getAwardHead());
                headCoulumn3.add(AwardHead.AWARD_LEVEL.getAwardHead());
                headCoulumn4.add(AwardHead.AWARD_RANK.getAwardHead());
                headCoulumn5.add(AwardHead.AWARD_DEPARTMENT.getAwardHead());
                headCoulumn6.add(AwardHead.LEAD_TEACHER.getAwardHead());
                headCoulumn7.add(AwardHead.JOIN_STUDENT.getAwardHead());
                headCoulumn8.add(AwardHead.AWARD_DESCRIPTION.getAwardHead());
                head.add(headCoulumn1);
                head.add(headCoulumn2);
                head.add(headCoulumn3);
                head.add(headCoulumn4);
                head.add(headCoulumn5);
                head.add(headCoulumn6);
                head.add(headCoulumn7);
                head.add(headCoulumn8);
                Table table = new Table(1);
                table.setHead(head);
                writer.write0(data, sheet1, table);
                writer.finish();
            }
        }
        File file = new File("奖项.xlsx");
        path = file.getAbsolutePath();
        return path;
    }

    /**
     * 创建用户信息EXCEL表格
     *
     * @param list 成员信息表格
     * @return 该文件的路径；
     */
    public static String createUserExcel(List<UserInfo> list) {
        return null;
    }
}
