package com.blue.spring.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @description: Excel工具类
 * @params:
 * @author: lizhixin
 * @createDate: 14:59 2017/9/7
 */
public class ExcelUtil {

    /**
     * @description: 读取Excel
     * @params:
     * @author: lizhixin
     * @createDate: 14:59 2017/9/7
     */
    public static List<List<String>> readExcel(File file, int cMaxIndex) {
        List<List<String>> list = new ArrayList<List<String>>();
        try {
            InputStream inputStream = new FileInputStream(file);
            String fileName = file.getName();
            Workbook wb = null;

            if (fileName.endsWith(".xlsx")) {
                wb = new XSSFWorkbook(inputStream);//Excel 2007
            } else {
                wb = (Workbook) new HSSFWorkbook(inputStream);//Excel 2003
            }

            Sheet sheet = wb.getSheetAt(0);//第一个工作表  ，第二个则为1，以此类推...
            int firstRowIndex = sheet.getFirstRowNum();
            int lastRowIndex = sheet.getLastRowNum();
            for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
                Row row = sheet.getRow(rIndex);
                if (row != null) {
                    int firstCellIndex = row.getFirstCellNum();
                    // int lastCellIndex = row.getLastCellNum();
                    //此处参数cIndex决定可以取到excel的列数。
                    List<String> list2 = new ArrayList<String>();
                    for (int cIndex = firstCellIndex; cIndex < cMaxIndex; cIndex++) {
                        Cell cell = null;
                        String value = "";
                        try {
                            cell = row.getCell(cIndex);
                        } catch (Exception e) {
                            list2.add("");
                            e.printStackTrace();
                        }

//                        if (cell != null) {
//                            value = cell.toString();
//                            list2.add(value);
//                        }

                        if (cell != null && StringUtils.isNotBlank(cell.toString())) {
                            if (CellType.NUMERIC == cell.getCellTypeEnum()) {
                                DecimalFormat df = new DecimalFormat("0");
                                value = df.format(cell.getNumericCellValue());
                            } else {
                                value = cell.toString();
                            }
                            list2.add(value);
                        }else{
                        	list2.add("");
                        }

                    }
                    list.add(list2);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @description: 设置某些列的值只能输入预制的数据, 显示下拉框.
     * @params sheet    要设置的sheet.
     * @params textlist 下拉框显示的内容
     * @params firstRow 开始行
     * @params endRow   结束行
     * @params firstCol 开始列
     * @params endCol   结束列
     * @author: lizhixin
     * @createDate: 14:54 2017/9/7
     */
    public static XSSFSheet setHSSFValidation(XSSFSheet sheet,
                                              String[] textlist,
                                              int firstRow,
                                              int endRow,
                                              int firstCol,
                                              int endCol) {
//        // 加载下拉列表内容
//        DVConstraint constraint = DVConstraint
//                .createExplicitListConstraint(textlist);
//        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
//        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
//                endRow, firstCol, endCol);
//        // 数据有效性对象
//        HSSFDataValidation data_validation_list = new HSSFDataValidation(
//                regions, constraint);
//
//        //选中时显示信息
//        data_validation_list.setSuppressDropDownArrow(false);
//        data_validation_list.createPromptBox("输入提示", "请从下拉列表中选择");
//        data_validation_list.setShowPromptBox(true);
//
//        sheet.addValidationData(data_validation_list);


        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                .createExplicitListConstraint(textlist);
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
        //显示下拉框设置为true
        validation.setSuppressDropDownArrow(true);
        validation.setShowErrorBox(true);
        validation.setShowPromptBox(true);
        validation.createPromptBox("输入提示", "请从下拉列表中选择");
        sheet.addValidationData(validation);
        return sheet;
    }

    /**
     * @description: 设置输入长度限制
     * @params sheet    要设置的sheet.
     * @params min      最大长度
     * @params max      最小长度
     * @params firstRow 开始行
     * @params endRow   结束行
     * @params firstCol 开始列
     * @params endCol   结束列
     * @author: lizhixin
     * @createDate: 14:54 2017/9/7
     */
    public static XSSFSheet setHSSFNumber(XSSFSheet sheet, String min,
                                          String max, int firstRow, int endRow, int firstCol,
                                          int endCol) {
//        // 构造constraint对象，并设置输入的长度
//        DVConstraint constraint = DVConstraint.createNumericConstraint(
//                DVConstraint.ValidationType.TEXT_LENGTH,
//                DVConstraint.OperatorType.BETWEEN, min, max);
//        // 四个参数分别是：起始行、终止行、起始列、终止列
//        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
//                endRow, firstCol, endCol);
//        // 数据有效性对象
//        HSSFDataValidation data_validation_view = new HSSFDataValidation(
//                regions, constraint);
//
//        //选中时显示信息
//        data_validation_view.setSuppressDropDownArrow(false);
//        data_validation_view.createPromptBox("输入提示", "长度为" + max + "位");
//        data_validation_view.setShowPromptBox(true);
//
//        sheet.addValidationData(data_validation_view);

        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                .createNumericConstraint(XSSFDataValidationConstraint.ValidationType.TEXT_LENGTH, XSSFDataValidationConstraint.OperatorType.BETWEEN, min, max);
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
        validation.setShowErrorBox(true);
        validation.setShowPromptBox(true);
        validation.createPromptBox("输入提示", "长度为" + max + "位");
        sheet.addValidationData(validation);


        return sheet;
    }


    /**
     * @description: 设置输入时间格式限制
     * @params sheet    要设置的sheet.
     * @params minDate  最大长度
     * @params maxDate  最小长度
     * @params firstRow 开始行
     * @params endRow   结束行
     * @params firstCol 开始列
     * @params endCol   结束列
     * @author: lizhixin
     * @createDate: 14:54 2017/9/7
     */
    public static XSSFSheet setHSSFDate(XSSFSheet sheet,
                                        String minDate,
                                        String maxDate,
                                        String dateFormat,
                                        int firstRow,
                                        int endRow,
                                        int firstCol,
                                        int endCol) {
//        // 构造constraint对象，并设置
//        DVConstraint constraint = DVConstraint.createDateConstraint(
//                DVConstraint.OperatorType.BETWEEN,
//                minDate,
//                maxDate,
//                dateFormat);
//        // 四个参数分别是：起始行、终止行、起始列、终止列
//        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
//                endRow, firstCol, endCol);
//        // 数据有效性对象
//        HSSFDataValidation data_validation_view = new HSSFDataValidation(
//                regions, constraint);
//
//        //选中时显示信息
//        data_validation_view.setSuppressDropDownArrow(false);
//        data_validation_view.createPromptBox("输入提示", "输入格式为：yyyy-mm-dd");
//        data_validation_view.setShowPromptBox(true);
//
//        sheet.addValidationData(data_validation_view);

        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                .createDateConstraint(XSSFDataValidationConstraint.OperatorType.BETWEEN, minDate, maxDate, "yyyy-MM-dd");
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint,addressList);
        validation.setShowErrorBox(true);
        validation.setShowPromptBox(true);
        validation.createPromptBox("输入提示", "输入格式为：yyyy-MM-dd");
        sheet.addValidationData(validation);

        return sheet;
    }

    /**
     *@description: 设置省市区的级联
     *@params:
     *@author: lizhixin
     *@createDate: 9:34 2017/10/27
    */
    public static void setArea(XSSFSheet sheet,List<String> provNameList,Map<String, List<String>> siteMap){
        for (int k = 0; k < 500; k++) {
            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet)sheet);

            // 每一行的省市区要单独设置一次，不能一起设置
            // 省规则
            DataValidationConstraint provConstraint = dvHelper.createExplicitListConstraint(provNameList.toArray(new String[]{}));
            CellRangeAddressList provRangeAddressList = new CellRangeAddressList(k+2, k+2, 11, 11);
            DataValidation provinceDataValidation = dvHelper.createValidation(provConstraint, provRangeAddressList);
            provinceDataValidation.createErrorBox("error", "请选择正确的省份");
            provinceDataValidation.setShowErrorBox(true);
            provinceDataValidation.setSuppressDropDownArrow(true);
            sheet.addValidationData(provinceDataValidation);


            // 市以规则，此处仅作一个示例
            // "INDIRECT($A$" + 2 + ")" 表示规则数据会从名称管理器中获取key与单元格 A2 值相同的数据，如果A2是浙江省，那么此处就是
            // 浙江省下的区域信息。
            // A是省对应的位置（特别注意）
            int listFormSize = k+3;
            int cellRowSize = k+2;
            DataValidationConstraint formula = dvHelper.createFormulaListConstraint("INDIRECT($L$" + listFormSize + ")");
            CellRangeAddressList rangeAddressList = new CellRangeAddressList(cellRowSize,cellRowSize,12,12);
            DataValidation cacse = dvHelper.createValidation(formula, rangeAddressList);
            cacse.createErrorBox("error", "请选择正确的市");
            sheet.addValidationData(cacse);

            // 区规则
            formula = dvHelper.createFormulaListConstraint("INDIRECT($M$" + listFormSize + ")");
            rangeAddressList = new CellRangeAddressList(cellRowSize,cellRowSize,13,13);
            cacse = dvHelper.createValidation(formula, rangeAddressList);
            cacse.createErrorBox("error", "请选择正确的区");
            sheet.addValidationData(cacse);
        }
    }

    /**
     *@description: 创建省市区隐藏的sheet
     *@params:
     *@author: lizhixin
     *@createDate: 9:34 2017/10/27
    */
    public static void createSiteSheet(XSSFWorkbook wb,List<String> provNameList,Map<String, List<String>> siteMap){
        XSSFSheet hideSheet = wb.createSheet("site");
        wb.setSheetHidden(wb.getSheetIndex(hideSheet), true);

        int rowId = 0;
        // 设置第一行，存省的信息
        Row proviRow = hideSheet.createRow(rowId++);
        proviRow.createCell(0).setCellValue("省列表");
        for(int n = 0; n < provNameList.size(); n ++){
            Cell proviCell = proviRow.createCell(n + 1);
            proviCell.setCellValue(provNameList.get(n));
        }

        // 将具体的数据写入到每一行中，行开头为父级区域，后面是子区域。
        Iterator<String> keyIterator = siteMap.keySet().iterator();
        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            List<String> son = siteMap.get(key);

            Row row = hideSheet.createRow(rowId++);
            row.createCell(0).setCellValue(key);
            for(int m = 0; m < son.size(); m ++){
                Cell cell = row.createCell(m + 1);
                cell.setCellValue(son.get(m));
            }

            // 添加名称管理器
            String range = getRange(1, rowId, son.size());
            Name name = wb.createName();
            name.setNameName(key);
            String formula1 = "site!" + range;
            name.setRefersToFormula(formula1);
        }
    }


    /**
     * @description: 动态生成Excel
     * @params: dataList     写入Excel的参数
     * @params: fileName     Excel名称
     * @params: groupList    需要设置下拉框的参数
     * @author: lizhixin
     * @createDate: 14:48 2017/9/7
     */
    public static XSSFWorkbook createExcel(List<List<String>> dataList, String fileName, List<Map<String, List<String>>> groupList, Map<String,Object> areaMap) {

        List<String> provNameList = (List<String>) areaMap.get("provNameList");
        Map<String, List<String>> siteMap = (Map<String, List<String>>) areaMap.get("siteMap");

        // excel文件对象
        XSSFWorkbook wb = new XSSFWorkbook();
        // 工作表对象
        XSSFSheet sheetlist = wb.createSheet(fileName);

        XSSFCellStyle textStyle = wb.createCellStyle();

        //设置自动换行
        textStyle.setWrapText(true);

        //设置为文本格式
        XSSFDataFormat format = wb.createDataFormat();
        textStyle.setDataFormat(format.getFormat("@"));

        //居中
        textStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //垂直
        textStyle.setAlignment(HorizontalAlignment.CENTER);

        //样式锁定
        textStyle.setLocked(true);

        int maxRow = dataList.size();
        int maxCol = dataList.get(1).size();
        int forSize = 0;
        for (int i = 0; i < dataList.size(); i++) {
            //第几行
            XSSFRow headerRow = sheetlist.createRow((short) i);
            for (int j = 0; j < dataList.get(i).size(); j++) {
                //此处有一个问题，在使用HSSF生成2003的时候，只要在每一个单元格出设置样式就可以，
                // 但是在使用XSSF生成2007的时候就不生效。在网上查询的时候有人说这是apache的一个bug，现在还没有解决，
                // 网上给出的解决方案是在每个单元格设置的时候既要设置每个单元格的样式，还要使用sheet设置默认的样式，要保证样式是一致的。
                //https://bz.apache.org/bugzilla/show_bug.cgi?id=51037
                //https://stackoverflow.com/questions/34463072/i-want-to-arrange-entire-cells-in-specific-column-instead-of-individual-cells?answertab=active#tab-top
                sheetlist.setDefaultColumnStyle(j,textStyle);

                //第几列
                XSSFCell headerCell = headerRow.createCell((short) j);
                headerCell.setCellStyle(textStyle);

                if (i == 0) {
                    //合并单元格
                    CellRangeAddress region1 = new CellRangeAddress(i, i, (short) 0, (short) maxCol - 1);
                    sheetlist.addMergedRegion(region1);
                    //设置值
                    headerCell.setCellValue(fileName);
                } else {
                    //设置值
                    headerCell.setCellValue(dataList.get(i).get(j));
                }

                if (i != 0) {
                    //动态设置下拉框
                    for (int n = 0; n < groupList.size(); n++) {
                        Map<String, List<String>> map = groupList.get(n);
                        //设置下拉框的列数
                        int setCol = Integer.parseInt(map.get("col").get(0));
                        //下拉框的值
                        List<String> groupData = map.get("data");

                        if (j == setCol) {
                            //设置下拉框
                            int size = groupData.size();
//                            String[] textArr = {"列表1", "列表2", "列表3", "列表4", "列表5"};
                            String[] textArr = new String[size];
                            for (int k = 0; k < size; k++) {
                                textArr[k] = groupData.get(k);
                            }
                            sheetlist = setHSSFValidation(sheetlist, textArr, 2, 500, j, j);// 第一列的前501行都设置为选择列表形式.
                        }
                    }

                    if (j == 2 || j == 15) {
                        //设置长度
                        sheetlist = setHSSFNumber(sheetlist, "11", "11", 0, 500, j, j);
                    }

                    if (j == 3) {
                        //设置长度
                        sheetlist = setHSSFNumber(sheetlist, "18", "18", 0, 500, j, j);
                    }

//                    if (j == 6) {
//                        //设置时间的格式
//                        sheetlist = setHSSFDate(sheetlist, "1900-01-01", "5000-01-01", "yyyy-MM-dd", 2, 500, j, j);
//                    }


                    if(j == 11){
                        createSiteSheet(wb,provNameList,siteMap);

                        if(forSize != 1){
                            forSize++;
                            setArea(sheetlist,provNameList,siteMap);
                        }
                    }
                }
            }
        }
        return wb;
    }

    /**
     * @param offset   偏移量，如果给0，表示从A列开始，1，就是从B列
     * @param rowId    第几行
     * @param colCount 一共多少列
     * @return 如果给入参 1,1,10. 表示从B1-K1。最终返回 $B$1:$K$1
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

//    /**
//     *@description: 导出报名信息excel
//     *@params:
//     *@author: lizhixin
//     *@createDate: 22:46 2017/9/21
//    */
//    public static XSSFWorkbook downloadApplyMessage(List<ApplyExcelVO> list, List<String> attribute, String excelName){
//        // excel文件对象
//        XSSFWorkbook wb = new XSSFWorkbook();
//        // 工作表对象
//        XSSFSheet sheetlist = wb.createSheet(excelName);
//
//        XSSFCellStyle textStyle = wb.createCellStyle();
//
//        //设置自动换行
//        textStyle.setWrapText(true);
//
//        //设置为文本格式
//        XSSFDataFormat format = wb.createDataFormat();
//        textStyle.setDataFormat(format.getFormat("@"));
//
//        //居中
//        textStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//
//        //垂直
//        textStyle.setAlignment(HorizontalAlignment.CENTER);
//
//        //样式锁定
//        textStyle.setLocked(true);
//
//        int maxRow = list.size()+2;
//        int maxCol = attribute.size();
//        for (int i = 0; i < maxRow; i++) {
//            //第几行
//            XSSFRow headerRow = sheetlist.createRow((short) i);
//            for (int j = 0; j < maxCol; j++) {
//                sheetlist.setDefaultColumnStyle(j,textStyle);
//
//                //第几列
//                XSSFCell headerCell = headerRow.createCell((short) j);
//                headerCell.setCellStyle(textStyle);
//
//                if (i == 0 ) {
//                    if(j == 0){
//                        //合并单元格
//                        CellRangeAddress region1 = new CellRangeAddress(i, i, (short) 0, (short) maxCol - 1);
//                        sheetlist.addMergedRegion(region1);
//                        //设置值
//                        headerCell.setCellValue(excelName);
//                    }
//                }else if(i == 1){
//                    headerCell.setCellValue(attribute.get(j));
//                }else{
//                    if (j == 0) {
//                        headerCell.setCellValue(list.get(i-2).getGroup());
//                    }
//                    if (j == 1) {
//                        headerCell.setCellValue(list.get(i-2).getApply_name());
//                    }
//                    if (j == 2) {
//                        headerCell.setCellValue(list.get(i-2).getApply_phone());
//                    }
//                    if (j == 3) {
//                        headerCell.setCellValue(list.get(i-2).getApply_idcard());
//                    }
//                    if (j == 4) {
//                        headerCell.setCellValue(list.get(i-2).getEmail());
//                    }
//                    if (j == 5) {
//                        headerCell.setCellValue(list.get(i-2).getSex());
//                    }
//                    if (j == 6) {
//                        headerCell.setCellValue(list.get(i-2).getBirthday());
//                    }
//                    if (j == 7) {
//                        headerCell.setCellValue(list.get(i-2).getAddress());
//                    }
//                    if (j == 8) {
//                        headerCell.setCellValue(list.get(i-2).getCloth_size());
//                    }
//                    if (j == 9) {
//                        headerCell.setCellValue(list.get(i-2).getBlood());
//                    }
//                    if (j == 10) {
//                        headerCell.setCellValue(list.get(i-2).getCountry());
//                    }
//                    if (j == 11) {
//                        headerCell.setCellValue(list.get(i-2).getU_province());
//                    }
//                    if (j == 12) {
//                        headerCell.setCellValue(list.get(i-2).getU_city());
//                    }
//                    if (j == 13) {
//                        headerCell.setCellValue(list.get(i-2).getU_region());
//                    }
//                    if (j == 14) {
//                        headerCell.setCellValue(list.get(i-2).getUrgent_person());
//                    }
//                    if (j == 15) {
//                        headerCell.setCellValue(list.get(i-2).getUrgent_person_phone());
//                    }
//                }
//            }
//        }
//        return wb;
//    }
//
//
//    /**
//     *@description: 导出成绩信息excel
//     *@params:
//     *@author: lizhixin
//     *@createDate: 22:46 2017/9/21
//     */
//    public static XSSFWorkbook downloadGradeMessage(List<ApplyExcelVO> list, List<String> attribute, String excelName){
//        // excel文件对象
//        XSSFWorkbook wb = new XSSFWorkbook();
//        // 工作表对象
//        XSSFSheet sheetlist = wb.createSheet(excelName);
//
//        XSSFCellStyle textStyle = wb.createCellStyle();
//
//        //设置自动换行
//        textStyle.setWrapText(true);
//
//        //设置为文本格式
//        XSSFDataFormat format = wb.createDataFormat();
//        textStyle.setDataFormat(format.getFormat("@"));
//
//        //居中
//        textStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//
//        //垂直
//        textStyle.setAlignment(HorizontalAlignment.CENTER);
//
//        //样式锁定
//        textStyle.setLocked(true);
//
//        int maxRow = list.size()+2;
//        int maxCol = attribute.size();
//        for (int i = 0; i < maxRow; i++) {
//            //第几行
//            XSSFRow headerRow = sheetlist.createRow((short) i);
//            for (int j = 0; j < maxCol; j++) {
//                sheetlist.setDefaultColumnStyle(j,textStyle);
//
//                //第几列
//                XSSFCell headerCell = headerRow.createCell((short) j);
//                headerCell.setCellStyle(textStyle);
//
//                if (i == 0 ) {
//                    if(j == 0){
//                        //合并单元格
//                        CellRangeAddress region1 = new CellRangeAddress(i, i, (short) 0, (short) maxCol - 1);
//                        sheetlist.addMergedRegion(region1);
//                        //设置值
//                        headerCell.setCellValue(excelName);
//                    }
//                }else if(i == 1){
//                    headerCell.setCellValue(attribute.get(j));
//                }else{
//                    if (j == 0) {
//                        headerCell.setCellValue(list.get(i-2).getGroup());
//                    }
//                    if (j == 1) {
//                        headerCell.setCellValue(list.get(i-2).getApply_name());
//                    }
//                    if (j == 2) {
//                        headerCell.setCellValue(list.get(i-2).getApply_phone());
//                    }
//                    if (j == 3) {
//                        headerCell.setCellValue(list.get(i-2).getApply_idcard());
//                    }
//                    if (j == 4) {
//                        headerCell.setCellValue(list.get(i-2).getEmail());
//                    }
//                    if (j == 5) {
//                        headerCell.setCellValue(list.get(i-2).getSex());
//                    }
//                    if (j == 6) {
//                        headerCell.setCellValue(list.get(i-2).getBirthday());
//                    }
//                    if (j == 7) {
//                        headerCell.setCellValue(list.get(i-2).getAddress());
//                    }
//                    if (j == 8) {
//                        headerCell.setCellValue(list.get(i-2).getCloth_size());
//                    }
//                    if (j == 9) {
//                        headerCell.setCellValue(list.get(i-2).getBlood());
//                    }
//                    if (j == 10) {
//                        headerCell.setCellValue(list.get(i-2).getCountry());
//                    }
//                    if (j == 11) {
//                        headerCell.setCellValue(list.get(i-2).getU_province());
//                    }
//                    if (j == 12) {
//                        headerCell.setCellValue(list.get(i-2).getU_city());
//                    }
//                    if (j == 13) {
//                        headerCell.setCellValue(list.get(i-2).getU_region());
//                    }
//                    if (j == 14) {
//                        headerCell.setCellValue(list.get(i-2).getUrgent_person());
//                    }
//                    if (j == 15) {
//                        headerCell.setCellValue(list.get(i-2).getUrgent_person_phone());
//                    }
//                }
//            }
//        }
//        return wb;
//    }

    public void main(String[] args) throws Exception {
//
////        List<List<String>> data = readExcel(new File("D:\\2017xxxxx马拉松赛团队报名表.xlsx"));
////        System.out.println(data);
//
//
//        HSSFWorkbook wb = new HSSFWorkbook();// excel文件对象
//        HSSFSheet sheetlist = wb.createSheet("sheetlist");// 工作表对象
//
//        //设置工作表保护密码
////        sheetlist.protectSheet("123");
//
//        HSSFHeader header = sheetlist.getHeader();
//        header.setCenter("测试的title");
//        HSSFRow headerRow = sheetlist.createRow((short) 0);
//        HSSFCell headerCell = headerRow.createCell((short) 0);
//        headerCell.setCellType(HSSFCell.CELL_TYPE_STRING);
//        HSSFCellStyle textStyle = wb.createCellStyle();
//
//        //设置自动换行
//        textStyle.setWrapText(true);
//
//        //设置为文本格式
//        HSSFDataFormat format = wb.createDataFormat();
//        textStyle.setDataFormat(format.getFormat("@"));
//        //居中
//        textStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        //垂直
//        textStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        textStyle.setLocked(true);
//        headerCell.setCellStyle(textStyle);
//
//        //合并单元格
//        CellRangeAddress region1 = new CellRangeAddress(0, 0, (short) 0, (short) 13);
//        sheetlist.addMergedRegion(region1);
//
//
//        headerCell.setCellValue("11111111");
//
//        FileOutputStream out = new FileOutputStream("d:\\生成的Excel.xlsx");
//
//        //设置下拉框
//        String[] textlist = {"列表1", "列表2", "列表3", "列表4", "列表5"};
//        sheetlist = setHSSFValidation(sheetlist, textlist, 1, 500, 0, 0);// 第一列的前501行都设置为选择列表形式.
//
////        //设置提示信息
////        sheetlist = setHSSFPrompt(sheetlist, "", "请输入11位的手机号码",
////                0, 500, 1, 1);// 第二列的前501行都设置提示.
//
//        //设置长度
//        sheetlist = setHSSFNumber(sheetlist,"11","11",0,500,1,1);
//
//        //设置时间的格式
//        sheetlist = setHSSFDate(sheetlist,"1900-01-01","5000-01-01","yyyy-mm-dd",1,500,2,2);
//
//        wb.write(out);
//        out.close();

    }
}
