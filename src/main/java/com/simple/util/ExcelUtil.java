package com.simple.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导入的方法
 * @author chenliuping
 * @date 2021-06-24 11:06
 */
public class ExcelUtil {

    /**
     * Excel web导出，直接在当前的web页面点击导出
     * @param response  HttpServletResponse
     * @param header 表头
     * @param keys map的key值
     * @param content 内容数据
     * @param title 表格名字
     * @param sheetName sheet名
     */
    public static void exportExcel(HttpServletResponse response, String[] header, String[] keys, List<Map<String, Object>> content, String title, String sheetName) throws Exception{
        title = title + ".xlsx";
        Workbook wb = getWB(header, keys, sheetName, content);
        title = new String(title.getBytes("UTF-8"), "ISO8859-1");
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Disposition", "attachment; filename=" + title);
        wb.write(response.getOutputStream());
        response.getOutputStream().close();
    }

    /**
     * Excel 导出获取OutputSteram流，为了下载使用的
     * @param header 表头
     * @param keys map的key值
     * @param content 内容数据
     * @param sheetName sheet名
     */
    public static void exportExcel(String[] header, String[] keys, List<Map<String, Object>> content, String sheetName, OutputStream outputStream) throws Exception{
        getWB(header, keys,sheetName,content).write(outputStream);
    }

    /**
     * Excel 导入
     * @param file 文件
     * @param keys 数据顺序
     */
    public static  List<Map<String, Object>>  importExcel(MultipartFile file, String[] keys) throws Exception{
        Workbook wb = null;
        String fileName = file.getOriginalFilename();
        if (fileName.endsWith("xls")) {
            POIFSFileSystem pois = new POIFSFileSystem(file.getInputStream());
            wb = new HSSFWorkbook(pois);
        } else if (fileName.endsWith("xlsx")) {
            wb = new XSSFWorkbook(file.getInputStream());
        }
        Sheet sheet = wb.getSheetAt(0);
        int rowCount = sheet.getPhysicalNumberOfRows();
//        如果每一个参数都必须不为空，则可以打开此验证
//        if (sheet.getRow( 1).getPhysicalNumberOfCells() != keys.length){
//            throw new RuntimeException("导入的Excel和模板的列不匹配");
//        }
        List<Map<String,Object>> result = new ArrayList<>();
        for (int i = 0; i < rowCount - 1; i++) {
            Row row = sheet.getRow(i + 1);
            Map<String,Object> tmp = new HashMap<>();
            for (int j = 0;j < keys.length; j++){
                Cell cell = row.getCell(j);
                if(cell == null) {
                    tmp.put(keys[j], null);
                }else {
                    // 把类型转行Spring
                    cell.setCellType(CellType.STRING);
                    tmp.put(keys[j], cell.getStringCellValue());
                }
            }
            result.add(tmp);
        }
        return result;
    }

    /**
     * 内部封装Workboot
     * @return
     */
    private static Workbook getWB( String[] header, String[] keys,String sheetName,List<Map<String, Object>> content){
        Workbook wb = new SXSSFWorkbook(1000);
        Sheet sheet = wb.createSheet(sheetName);
        Row row = sheet.createRow( 0);
        // 行高
        row.setHeight((short) 700);
        // 列宽
        for (int i = 0; i < header.length; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }
        for (int i = 0; i < header.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellStyle(HeaderStyle(wb));
        }
        for (int i = 0; i < content.size(); i++) {
            Map<String, Object> map = content.get(i);
            row = sheet.createRow((int) i + 1);
            row.setHeight((short) 500);
            for (int j = 0; j < keys.length; j++){
                Cell cell = row.createCell(j);
                cell.setCellValue(map.get(keys[j]) == null ? "" : map.get(keys[j]).toString());
                cell.setCellStyle(contentStyle(wb));
            }
        }
        return wb;
//        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
//            title = new String(title.getBytes("UTF-8"), "ISO8859-1"); // firefox浏览器
//        } else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
//            title = URLEncoder.encode(title, "UTF-8");// IE浏览器
//        } else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {
//            title = new String(title.getBytes("UTF-8"), "ISO8859-1");// 谷歌
//        }
    }

    /**
     * 表头样式
     */
    private static CellStyle HeaderStyle(Workbook wb){
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 11);
        CellStyle cellStyle = commonStyle(wb);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 内容样式
     */
    private static CellStyle contentStyle(Workbook wb){
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);
        CellStyle cellStyle = commonStyle(wb);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 公共样式
     */
    private static CellStyle commonStyle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);// 自动换行
        return style;
    }
}
