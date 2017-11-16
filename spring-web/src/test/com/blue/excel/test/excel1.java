package com.blue.excel.test;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @description:
 * @email:
 * @author: lizhixin
 * @createDate: 14:00 2017/10/23
 */
public class excel1 {

    public static void main(String[] args) {
        System.out.println(StringUtils.isNotBlank(" "));
        System.out.println(org.springframework.util.StringUtils.isEmpty(" "));
//        testCascade2007();
    }

    //对于直辖市要单独处理，保证省和市命名不一样，否则会覆盖导致四个直辖市无法显示区
    public static void testCascade2007() {
        // 查询所有的省名称
        List<String> provNameList = new ArrayList<String>();
        provNameList.add("安徽省");
        provNameList.add("浙江省");

        // 整理数据，放入一个Map中，mapkey存放父地点，value 存放该地点下的子区域
        Map<String, List<String>> siteMap = new HashMap<String, List<String>>();
        List<String> list1 = new ArrayList();
        list1.add("杭州市");
        list1.add("宁波市");
        siteMap.put("浙江省", list1);
        List<String> list2 = new ArrayList();
        list2.add("芜湖市");
        list2.add("滁州市");
        siteMap.put("安徽省", list2);
        List<String> list3 = new ArrayList();
        list3.add("戈江区");
        list3.add("三山区");
        siteMap.put("芜湖市", list3);
//        siteMap.put("滁州市", new ArrayList("来安县", "凤阳县"));

        // 创建一个excel
        Workbook book = new XSSFWorkbook();

        // 创建需要用户填写的数据页
        // 设计表头
        Sheet sheet1 = book.createSheet("test");
        Row row0 = sheet1.createRow(0);
        row0.createCell(0).setCellValue("省");
        row0.createCell(1).setCellValue("市");
        row0.createCell(2).setCellValue("区");

        //创建一个专门用来存放地区信息的隐藏sheet页
        //因此也不能在现实页之前创建，否则无法隐藏。
        Sheet hideSheet = book.createSheet("site");
//        book.setSheetHidden(book.getSheetIndex(hideSheet), true);

        int rowId = 0;
        // 设置第一行，存省的信息
        Row proviRow = hideSheet.createRow(rowId++);
        proviRow.createCell(0).setCellValue("省列表");
        for(int i = 0; i < provNameList.size(); i ++){
            Cell proviCell = proviRow.createCell(i + 1);
            proviCell.setCellValue(provNameList.get(i));
        }
        // 将具体的数据写入到每一行中，行开头为父级区域，后面是子区域。
        Iterator<String> keyIterator = siteMap.keySet().iterator();
        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            List<String> son = siteMap.get(key);

            Row row = hideSheet.createRow(rowId++);
            row.createCell(0).setCellValue(key);
            for(int i = 0; i < son.size(); i ++){
                Cell cell = row.createCell(i + 1);
                cell.setCellValue(son.get(i));
            }

            // 添加名称管理器
            String range = getRange(1, rowId, son.size());
            Name name = book.createName();
            name.setNameName(key);
            String formula1 = "site!" + range;
            name.setRefersToFormula(formula1);

            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet)sheet1);

            // 每一行的省市区要单独设置一次，不能一起设置
            // 省规则
            DataValidationConstraint provConstraint = dvHelper.createExplicitListConstraint(provNameList.toArray(new String[]{}));
            CellRangeAddressList provRangeAddressList = new CellRangeAddressList(1, 1, 0, 0);
            DataValidation provinceDataValidation = dvHelper.createValidation(provConstraint, provRangeAddressList);
            provinceDataValidation.createErrorBox("error", "请选择正确的省份");
            provinceDataValidation.setShowErrorBox(true);
            provinceDataValidation.setSuppressDropDownArrow(true);
            sheet1.addValidationData(provinceDataValidation);


            // 市以规则，此处仅作一个示例
            // "INDIRECT($A$" + 2 + ")" 表示规则数据会从名称管理器中获取key与单元格 A2 值相同的数据，如果A2是浙江省，那么此处就是
            // 浙江省下的区域信息。
            // A是省对应的位置（特别注意）
            DataValidationConstraint formula = dvHelper.createFormulaListConstraint("INDIRECT($A$" + 1 + ")");
            CellRangeAddressList rangeAddressList = new CellRangeAddressList(1,1,1,1);
            DataValidation cacse = dvHelper.createValidation(formula, rangeAddressList);
            cacse.createErrorBox("error", "请选择正确的市");
            sheet1.addValidationData(cacse);

            // 区规则
            formula = dvHelper.createFormulaListConstraint("INDIRECT($B$" + 1 + ")");
            rangeAddressList = new CellRangeAddressList(1,1,2,2);
            cacse = dvHelper.createValidation(formula, rangeAddressList);
            cacse.createErrorBox("error", "请选择正确的区");
            sheet1.addValidationData(cacse);
        }


        FileOutputStream os = null;
        try {
            os = new FileOutputStream("D:/testCascade2007.xlsx");
            book.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param offset   偏移量，如果给0，表示从A列开始，1，就是从B列
     * @param rowId    第几行
     * @param colCount 一共多少列
     * @return 如果给入参 1,1,10. 表示从B1-K1。最终返回 $B$1:$K$1
     * @author denggonghai 2016年8月31日 下午5:17:49
     */
    public static String getRange(int offset, int rowId, int colCount) {
        char start = (char) ('A' + offset);
        if (colCount <= 25) {
            char end = (char) (start + colCount - 1);
            return "$" + start + "$" + rowId + ":$" + end + "$" + rowId;
        } else {
            char endPrefix = 'A';
            char endSuffix = 'A';
            if ((colCount - 25) / 26 == 0 || colCount == 51) {// 26-51之间，包括边界（仅两次字母表计算）
                if ((colCount - 25) % 26 == 0) {// 边界值
                    endSuffix = (char) ('A' + 25);
                } else {
                    endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
                }
            } else {// 51以上
                if ((colCount - 25) % 26 == 0) {
                    endSuffix = (char) ('A' + 25);
                    endPrefix = (char) (endPrefix + (colCount - 25) / 26 - 1);
                } else {
                    endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
                    endPrefix = (char) (endPrefix + (colCount - 25) / 26);
                }
            }
            return "$" + start + "$" + rowId + ":$" + endPrefix + endSuffix + "$" + rowId;
        }

    }
}



//import java.io.FileOutputStream;
//
//import org.apache.poi.hssf.usermodel.DVConstraint;
//import org.apache.poi.hssf.usermodel.HSSFDataValidation;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.DataValidation;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.Name;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.util.CellRangeAddressList;
//import org.apache.poi.xssf.usermodel.XSSFDataValidation;
//
//public class excel1 {
//    private static String EXCEL_HIDE_SHEET_NAME = "poihide";
//    private static String HIDE_SHEET_NAME_YN = "yesOrNOList";
//    private static String HIDE_SHEET_NAME_PROVINCE = "provinceList";
//    //设置下拉列表的内容
//    private static String[] yesOrNOList = {"是", "否"};
//    private static String[] provinceList = {"广东省", "河南省"};
//    private static String[] gzProvinceList = {"广州", "深圳", "珠海"};
//    private static String[] hnProvinceList = {"郑州", "洛阳", "济源"};
//    public static void main(String[] args) {
//        Workbook wb = new HSSFWorkbook();
//        createExcel(wb);
//        creatExcelHidePage(wb);
//        setDataValidation(wb);
//        FileOutputStream fileOut;
//        try {
//            fileOut = new FileOutputStream("d://excel_danborusu.xls");
//            wb.write(fileOut);
//            fileOut.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public static void createExcel(Workbook wb) {
//        Sheet sheet = wb.createSheet("测试");
//        Row row = sheet.createRow(0);
//        Cell cell = row.createCell(0);
//        cell.setCellValue("是否转售");
////cell.setCellStyle(getTitleStyle(wb));
//        cell = row.createCell(1);
//        cell.setCellValue("省份");
////cell.setCellStyle(getTitleStyle(wb));
//        cell = row.createCell(2);
//        cell.setCellValue("本地网");
////cell.setCellStyle(getTitleStyle(wb));
//        cell = row.createCell(3);
//    }
//    private static CellStyle getTitleStyle(Workbook wb) {
//        CellStyle style = wb.createCellStyle();
////对齐方式设置
//        style.setAlignment(CellStyle.ALIGN_CENTER);
////边框颜色和宽度设置
//        style.setBorderBottom(CellStyle.BORDER_THIN);
//        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//        style.setBorderLeft(CellStyle.BORDER_THIN);
//        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//        style.setBorderRight(CellStyle.BORDER_THIN);
//        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
//        style.setBorderTop(CellStyle.BORDER_THIN);
//        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
//        style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
////设置背景颜色
//        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
////粗体字设置
//        Font font = wb.createFont();
//        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
//        style.setFont(font);
//        return style;
//    }
//    public static void creatExcelHidePage(Workbook workbook) {
//        Sheet hideInfoSheet = workbook.createSheet(EXCEL_HIDE_SHEET_NAME);//隐藏一些信息
////在隐藏页设置选择信息
////第一行设置性别信息
//        Row sexRow = hideInfoSheet.createRow(0);
//        creatRow(sexRow, yesOrNOList);
////第二行设置省份名称列表
//        Row provinceNameRow = hideInfoSheet.createRow(1);
//        creatRow(provinceNameRow, provinceList);
////以下行设置城市名称列表
//        Row cityNameRow = hideInfoSheet.createRow(2);
//        creatRow(cityNameRow, gzProvinceList);
//        cityNameRow = hideInfoSheet.createRow(3);
//        creatRow(cityNameRow, hnProvinceList);
////名称管理
////第一行设置性别信息
//        creatExcelNameList(workbook, HIDE_SHEET_NAME_YN, 1, yesOrNOList.length, false);
////第二行设置省份名称列表
//        creatExcelNameList(workbook, HIDE_SHEET_NAME_PROVINCE, 2, provinceList.length, false);
////以后动态大小设置省份对应的城市列表
//        creatExcelNameList(workbook, provinceList[0], 3, gzProvinceList.length, true);
//        creatExcelNameList(workbook, provinceList[1], 4, hnProvinceList.length, true);
////设置隐藏页标志
////        workbook.setSheetHidden(workbook.getSheetIndex(EXCEL_HIDE_SHEET_NAME), true);
//    }
//    private static void creatExcelNameList(Workbook workbook, String nameCode, int order, int size, boolean cascadeFlag) {
//        Name name;
//        name = workbook.createName();
//        name.setNameName(nameCode);
//        String formula = EXCEL_HIDE_SHEET_NAME + "!" + creatExcelNameList(order, size, cascadeFlag);
//        System.out.println(nameCode + " ==  " + formula);
//        name.setRefersToFormula(formula);
//    }
//    private static String creatExcelNameList(int order, int size, boolean cascadeFlag) {
//        char start = 'A';
//        if (cascadeFlag) {
//            if (size <= 25) {
//                char end = (char) (start + size - 1);
//                return "$" + start + "$" + order + ":$" + end + "$" + order;
//            } else {
//                char endPrefix = 'A';
//                char endSuffix = 'A';
//                if ((size - 25) / 26 == 0 || size == 51) {//26-51之间，包括边界（仅两次字母表计算）
//                    if ((size - 25) % 26 == 0) {//边界值
//                        endSuffix = (char) ('A' + 25);
//                    } else {
//                        endSuffix = (char) ('A' + (size - 25) % 26 - 1);
//                    }
//                } else {//51以上
//                    if ((size - 25) % 26 == 0) {
//                        endSuffix = (char) ('A' + 25);
//                        endPrefix = (char) (endPrefix + (size - 25) / 26 - 1);
//                    } else {
//                        endSuffix = (char) ('A' + (size - 25) % 26 - 1);
//                        endPrefix = (char) (endPrefix + (size - 25) / 26);
//                    }
//                }
//                return "$" + start + "$" + order + ":$" + endPrefix + endSuffix + "$" + order;
//            }
//        } else {
//            if (size <= 26) {
//                char end = (char) (start + size - 1);
//                return "$" + start + "$" + order + ":$" + end + "$" + order;
//            } else {
//                char endPrefix = 'A';
//                char endSuffix = 'A';
//                if (size % 26 == 0) {
//                    endSuffix = (char) ('A' + 25);
//                    if (size > 52 && size / 26 > 0) {
//                        endPrefix = (char) (endPrefix + size / 26 - 2);
//                    }
//                } else {
//                    endSuffix = (char) ('A' + size % 26 - 1);
//                    if (size > 52 && size / 26 > 0) {
//                        endPrefix = (char) (endPrefix + size / 26 - 1);
//                    }
//                }
//                return "$" + start + "$" + order + ":$" + endPrefix + endSuffix + "$" + order;
//            }
//        }
//    }
//    private static void creatRow(Row currentRow, String[] textList) {
//        if (textList != null && textList.length > 0) {
//            int i = 0;
//            for (String cellValue : textList) {
//                Cell userNameLableCell = currentRow.createCell(i++);
//                userNameLableCell.setCellValue(cellValue);
//            }
//        }
//    }
//    public static void setDataValidation(Workbook wb) {
//        int sheetIndex = wb.getNumberOfSheets();
//        if (sheetIndex > 0) {
//            for (int i = 0; i < sheetIndex; i++) {
//                Sheet sheet = wb.getSheetAt(i);
//                if (!EXCEL_HIDE_SHEET_NAME.equals(sheet.getSheetName())) {
////省份选项添加验证数据
//                    for (int a = 2; a < 5; a++) {
////性别添加验证数据
//                        DataValidation data_validation_list = getDataValidationByFormula(HIDE_SHEET_NAME_YN, a, 1);
//                        sheet.addValidationData(data_validation_list);
//                        DataValidation data_validation_list2 = getDataValidationByFormula(HIDE_SHEET_NAME_PROVINCE, a,
//                                2);
//                        sheet.addValidationData(data_validation_list2);
////城市选项添加验证数据
//                        DataValidation data_validation_list3 = getDataValidationByFormula("INDIRECT($B$" + a + ")", a,
//                                3);
//                        sheet.addValidationData(data_validation_list3);
//                    }
//                }
//            }
//        }
//    }
//    private static DataValidation getDataValidationByFormula(String formulaString, int naturalRowIndex,
//                                                             int naturalColumnIndex) {
//        System.out.println("formulaString  " + formulaString);
////加载下拉列表内容
//        DVConstraint constraint = DVConstraint.createFormulaListConstraint(formulaString);
////设置数据有效性加载在哪个单元格上。
////四个参数分别是：起始行、终止行、起始列、终止列
//        int firstRow = naturalRowIndex - 1;
//        int lastRow = naturalRowIndex - 1;
//        int firstCol = naturalColumnIndex - 1;
//        int lastCol = naturalColumnIndex - 1;
//        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
////数据有效性对象
//        DataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
//        data_validation_list.setEmptyCellAllowed(false);
//        if (data_validation_list instanceof XSSFDataValidation) {
//            data_validation_list.setSuppressDropDownArrow(true);
//            data_validation_list.setShowErrorBox(true);
//        } else {
//            data_validation_list.setSuppressDropDownArrow(false);
//        }
////设置输入信息提示信息
//        data_validation_list.createPromptBox("下拉选择提示", "请使用下拉方式选择合适的值！");
////设置输入错误提示信息
//        data_validation_list.createErrorBox("选择错误提示", "你输入的值未在备选列表中，请下拉选择合适的值！");
//        return data_validation_list;
//    }
//    private static DataValidation getDataValidationByDate(int naturalRowIndex, int naturalColumnIndex) {
////加载下拉列表内容
//        DVConstraint constraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.BETWEEN, "1900-01-01",
//                "5000-01-01", "yyyy-mm-dd");
////设置数据有效性加载在哪个单元格上。
////四个参数分别是：起始行、终止行、起始列、终止列
//        int firstRow = naturalRowIndex - 1;
//        int lastRow = naturalRowIndex - 1;
//        int firstCol = naturalColumnIndex - 1;
//        int lastCol = naturalColumnIndex - 1;
//        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
////数据有效性对象
//        DataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
////设置输入信息提示信息
//        data_validation_list.createPromptBox("日期格式提示", "请按照'yyyy-mm-dd'格式输入日期值！");
////设置输入错误提示信息
//        data_validation_list.createErrorBox("日期格式错误提示", "你输入的日期格式不符合'yyyy-mm-dd'格式规范，请重新输入！");
//        return data_validation_list;
//    }
//}