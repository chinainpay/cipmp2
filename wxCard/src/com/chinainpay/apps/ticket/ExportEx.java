package com.chinainpay.apps.ticket;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExportEx {
	//ExCorrespondingPro中key是EXCEL中显示列名   value对应的result内属性名称
	public static HttpServletResponse exportExcel( List<Map<String, Object>> result, 
						HttpServletResponse response, LinkedHashMap<String,String> ExCorrespondingPro, String ExcelTitle){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		if(result!=null && result.size()!=0 && ExCorrespondingPro!=null && ExCorrespondingPro.size()!=0){
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(ExcelTitle);
			sheet.setDefaultColumnWidth(25);
			//生成一个样式(用于单元格)  
	        HSSFCellStyle style = workbook.createCellStyle();
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        //设置合并行  数参数：起始行号，终止行号， 起始列号，终止列号
	        CellRangeAddress cra=new CellRangeAddress(0, 1, 0, ExCorrespondingPro.size()-1);
	        sheet.addMergedRegion(cra);
	        HSSFRow TitleRow = sheet.createRow(0);
	        HSSFCell TitleCell = TitleRow.createCell(0);
	        //设置标题插入内容和样式
	        TitleCell.setCellValue(ExcelTitle);  
	        TitleCell.setCellStyle(style);
	        sheet.addMergedRegion(cra); //增加一行
	        String[] titles=new String[ExCorrespondingPro.size()];
	        int k=0;
//	        for (String key : ExCorrespondingPro.keySet()) {
//	        	titles[k]=key;
//	        	System.out.println("key= "+ key + " and value= " + ExCorrespondingPro.get(key));
//	        	k++;
//	        }
	        for (Map.Entry<String, String> entry : ExCorrespondingPro.entrySet()) {
	        	   titles[k]=entry.getKey().toString();
	        	   System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
	        	   k++;
	        	  }
	     // 产生表格标题行  
	        HSSFRow row = sheet.createRow(2);  
	        for(int i = 0; i < titles.length; i++){  
	            HSSFCell cell = row.createCell(i);  
	            //cell.setCellStyle(style);//设置单元格样式(包含字体)  
	            HSSFRichTextString text = new HSSFRichTextString(titles[i]);  
	            cell.setCellValue(text);//把数据放到单元格中
	            cell.setCellStyle(style);
	        }
	        int rowNum=3;
	        for (Map<String, Object> resultMap : result) {
	        	row = sheet.createRow(rowNum);// 第四行开始数据
	        	rowNum++;
	        	for(int i = 0; i < titles.length; i++){  
	        		HSSFCell cell = row.createCell(i);
	        		cell.setCellValue(resultMap.get(ExCorrespondingPro.get(titles[i].toString()))==null?"":resultMap.get(ExCorrespondingPro.get(titles[i].toString())).toString());
	        		cell.setCellStyle(style);
		        }
			}
	        try {
	        	response.setContentType("application/vnd.ms-excel");
            	response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(ExcelTitle+"_", "UTF-8")+dateFormat.format(new Date())+".xls"); 
				workbook.write(response.getOutputStream());
				return response;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return response;
	}
}

