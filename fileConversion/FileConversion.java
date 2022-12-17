import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liyongqiang
 * @data 2022/12/17 20:36
 * @description excel转换sql脚本 支持xlsx、xls
 */
public class FileConversion {

    public static void main(String[] args) throws Exception {
        File file = new File(文件的存放路径);
        readExcel(file);
//        File file1 = new File(文件的存放路径);
//        readExcel(file1);
    }

    /**
     * 读取excel文件
     * @param file
     * @return
     * @throws IOException
     */
    public static List<Map<String, Object>> readExcel(File file) throws IOException {
        List<java.util.Map<String, Object>> allList = new ArrayList<>();
        // 获取excel工作簿对象
        String fileName = file.getName();
        String substring = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(substring)) {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
            for (Sheet sheet:workbook){
                List<String> headerList = new ArrayList<>();
                List<java.util.Map<String, Object>> dataList = new ArrayList<>();
                System.out.println(sheet.getSheetName());
                String sheetName = sheet.getSheetName();
                int i =0;
                for (Row row : sheet) {
                    if (i == 0) {
                        if (i == 0) {
                            for (Cell cell : row) {
                                headerList.add(cell.getStringCellValue());
                            }
                            if(headerList.size() > 0) {
                                String createTableSql = createTable(headerList, sheetName);
                                System.out.println("生成的创建表语句："+ createTableSql);
                            }
                        }
                    } else {
                        java.util.Map<String, Object> map = new HashMap<>();
                        int j = 0;
                        for (Cell cell : row) {
                            //设置单元格类型
                            cell.setCellType(CellType.STRING);
                            map.put(headerList.get(j), cell.getStringCellValue());
                            j++;
                        }

                        dataList.add(map);
                    }
                    i++;
                }
                java.util.Map<String, Object> map = new HashMap<>();
                map.put("sheetName",sheetName);
                map.put("data",dataList);
                insertFromMap(dataList, sheetName);
                allList.add(map);
            }
        } else if (".xlsx".equals(substring)) {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
            for (Sheet sheet:workbook){
                List<String> headerList = new ArrayList<>();
                List<java.util.Map<String, Object>> dataList = new ArrayList<>();
                System.out.println(sheet.getSheetName());
                String sheetName = sheet.getSheetName();
                int i =0;
                for (Row row : sheet) {
                    if (i == 0) {
                        if (i == 0) {
                            for (Cell cell : row) {
                                headerList.add(cell.getStringCellValue());
                            }
                            if(headerList.size() > 0) {
                                String createTableSql = createTable(headerList, sheetName);
                                System.out.println("生成的创建表语句："+ createTableSql);
                            }
                        }
                    } else {
                        java.util.Map<String, Object> map = new HashMap<>();
                        int j = 0;
                        for (Cell cell : row) {
                            //设置单元格类型
                            cell.setCellType(CellType.STRING);
                            map.put(headerList.get(j), cell.getStringCellValue());
                            j++;
                        }
                        dataList.add(map);
                    }
                    i++;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("sheetName",sheet.getSheetName());
                map.put("data",dataList);
                insertFromMap(dataList, sheetName);
                allList.add(map);
            }
        }
        System.out.println(allList);
        return allList;
    }

    /**
     * 读取所有sheet数据
     * @param workbooks
     * @param workbookXss
     * @return
     */
    public static List<Map<String, Object>> readSheet(HSSFWorkbook workbooks, XSSFWorkbook workbookXss){
        List<Map<String, Object>> allList = new ArrayList<>();
        for (Sheet sheet:workbooks != null ? workbooks : workbookXss){
            List<String> headerList = new ArrayList<>();
            List<Map<String, Object>> dataList = new ArrayList<>();
            String sheetName = sheet.getSheetName();
            // System.out.println(sheetName);
            int i =0;
            for (Row row : sheet) {
                if (i == 0) {
                    if (i == 0) {
                        for (Cell cell : row) {
                            headerList.add(cell.getStringCellValue());
                        }
                        if(headerList.size() >0) {
                            // 创建表结构
                            String createTabel = createTable(headerList, sheetName);
                        }else {
                            break;
                        }
                    }
                } else {
                    Map<String, Object> map = new HashMap<>();
                    int j = 0;
                    for (Cell cell : row) {
                        //设置单元格类型
                        cell.setCellType(CellType.STRING);
                        map.put(headerList.get(j), cell.getStringCellValue());
                        j++;
                    }
                    dataList.add(map);
                }
                i++;
            }
            String insertSql = insertFromMap(dataList, sheetName);
           /* Map<String, Object> map = new HashMap<>();
            map.put("sheetName",sheet.getSheetName());
            map.put("data",dataList);
            allList.add(map);*/
        }
        return allList;
    }


    /**
     * 生成创建表结构
     * @param headerList
     * @param sheetName
     * @return
     */
    public static String createTable(List<String> headerList, String sheetName){
        StringBuffer createTableSql = new StringBuffer();
        if(headerList.size() > 0) {
            createTableSql.append("SET NAMES utf8mb4;\n");
            createTableSql.append("SET FOREIGN_KEY_CHECKS = 0;\n");
            createTableSql.append("DROP TABLE IF EXISTS `"+sheetName+"`;\n");
            createTableSql.append("CREATE TABLE `"+sheetName+"`");
            createTableSql.append("(\n");
            createTableSql.append("`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',\n");
            int k = 0;
            for (String key: headerList){
                createTableSql.append("`"+key+"` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,\n");
                k++;
                if(k == headerList.size()){
                    createTableSql.append("PRIMARY KEY (`id`) USING BTREE)\n");
                    createTableSql.append("ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '' ROW_FORMAT = Dynamic;\n");
                    createTableSql.append("SET FOREIGN_KEY_CHECKS = 1;");
                }
            }
        }
        return createTableSql.toString();
    }


    /**
     * map对象生成insert插入语句
     * @param dataList
     * @param tableName
     */
    public static String insertFromMap(List<Map<String,Object>> dataList, String tableName){
        String sql = null;
        if(dataList.size() >0) {
            int i = 0;
            StringBuffer strKey = new StringBuffer();
            //插入sql语句
            StringBuffer insertSql = new StringBuffer().append("INSERT INTO " + "`" +tableName + "`");
            StringBuffer value = new StringBuffer();
            for (Map<String,Object> map : dataList) {
                //存入key的字符串数组
                ArrayList<Object> arrKey = new ArrayList<>();
                //存入value的字符串数组
                ArrayList<Object> arrValue = new ArrayList<>();
                //拼接sql
                for (String key : map.keySet()) {
                    arrKey.add(key);
                }
                for (String keys : map.keySet()) {
                    arrValue.add(map.get(keys));
                }
                if(i == 0) {
                    //遍历存的key字符串数组拼接sql
                    for (int j = 0; j < arrKey.size(); j++) {
                        strKey.append("`"+ arrKey.get(j) + "`");
                        if (j != arrKey.size() - 1) {//拼上","最后一个不拼
                            strKey.append(",");
                        }
                    }
                }
                i++;
                StringBuffer strVal = new StringBuffer();
                //遍历存的value字符串数组拼接sql
                for (int j = 0; j < arrValue.size(); j++) {
                    if (null != arrValue.get(j) && !"".equals(arrValue.get(j))) {
                        strVal.append("'" + arrValue.get(j) + "'");//拼接单引号
                    } else if ("".equals(arrValue.get(j))) {
                        strVal.append("" + null + "");
                    } else {
                        strVal.append(arrValue.get(j));
                    }
                    if (j != arrValue.size() - 1) {//拼上","最后一个不拼
                        strVal.append(",");
                    }
                }
                String stringEntryVal = strVal.toString();
                value.append("("+stringEntryVal+")");
                if(i< dataList.size()) {
                    value.append(",");
                }
            }
            insertSql.append("(" + strKey + ")");
            insertSql.append(" VALUES ");
            insertSql.append(value+";");
            System.out.println("生成插入数据sql:" + insertSql.toString());
            sql = insertSql.toString();
        }
        return sql;
    }
}
