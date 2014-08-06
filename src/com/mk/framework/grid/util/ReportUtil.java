package com.mk.framework.grid.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.mk.framework.constance.Constance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.model.ColumnInfo;

public class ReportUtil {
	private static String sheetName = "data";
	private String title;
	private HSSFWorkbook wb;
	private HSSFRow row;
	private HSSFCell cell;
	private HSSFFont font;
	private HSSFCellStyle cellStyle;

	// 标题
	private void createSheetTitle(HSSFSheet sheet, int i) {

		HSSFFont font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 20);
		cellStyle = wb.createCellStyle();
		cellStyle.setFont(font1);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 标题
		row = sheet.createRow(0);
		row.setHeightInPoints(33);
		cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(title);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (i - 1)));

	}

	// 创建第一行
	private void createSheet(HSSFSheet sheet, List<ColumnInfo> columns) {
		row = sheet.createRow(1);
		// 行高
		row.setHeightInPoints(14);
		// font
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontHeightInPoints((short) 10);

		// css
		cellStyle = creatCellStyle(HSSFCellStyle.BORDER_THIN);
		cellStyle.setFont(font);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		ColumnInfo column = null;
		for (int i = 0; i < columns.size(); i++) {
			column = (columns.get(i));
			cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(column.getHeader());
		}
	}

	// 生成数据
	private void createBodyRow(HSSFSheet sheet, int sheetNum, List<ColumnInfo> columns, List<JSONObject> list) throws IllegalArgumentException, IllegalAccessException, IOException {
		JSONObject json;

		int startNum = sheetNum * Constance.EXCEL_SHEET_DATASIZE;
		int endNum = sheetNum * Constance.EXCEL_SHEET_DATASIZE + Constance.EXCEL_SHEET_DATASIZE;
		if (list.size() < endNum)
			endNum = list.size();

		int tmpNum = 0;
		for (int i = startNum; i < endNum; i++) {
			row = sheet.createRow(tmpNum + 2);
			// 行高
			row.setHeightInPoints(18);
			json = (JSONObject) list.get(i);
			this.createCell(row, columns, json);

			tmpNum++;
		}

	}

	private void createCell(HSSFRow row, List<ColumnInfo> columns, JSONObject obj) throws IllegalArgumentException, IllegalAccessException {
		Map<String, String> map = new HashMap<String, String>();
		for (Iterator<String> iterator = obj.keys(); iterator.hasNext();) {
			String key = (String) iterator.next();
			map.put(key, obj.getString(key));
		}
		ColumnInfo column = null;
		String val = null;

		for (int i = 0; i < columns.size(); i++) {
			column = columns.get(i);
			cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
			val = map.get(column.getId());

			if (StringUtils.isNotEmpty(val)) {
				if (column.isIsnumber()) {
					// 数字
					cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("##.##"));
					cell.setCellStyle(cellStyle);
					cell.setCellValue(Double.valueOf(val));
				} else
					cell.setCellValue(val);

			}

		}
	}

	// 导出excel
	public void reportGrid(GridServerHandler grid) throws Exception {
		HttpServletResponse response = grid.getResponse();
		String exportFileName = grid.getParameter("exportFileName");

		// 过滤隐藏列
		List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
		for (ColumnInfo colum : grid.getColumnInfo()) {
			if (!colum.isHidden() && !colum.getHeader().equals("选择") && !colum.getHeader().equals("状态"))
				columns.add(colum);
		}
		List data = grid.getData();

		// 公用的创建
		wb = new HSSFWorkbook();
		font = wb.createFont();

		// 头
		int sheetNum = Math.round(data.size() / Constance.EXCEL_SHEET_DATASIZE);
		for (int i = 0; i <= sheetNum; i++) {
			HSSFSheet sheet = wb.createSheet(sheetName + i);
			createSheetTitle(sheet, columns.size());

			// 标题
			createSheet(sheet, columns);
			// 体
			createBodyRow(sheet, i, columns, data);
		}

		response.reset();
		response.setContentType("application/x-msdownload"); //
		String s = "attachment; filename=" + new String(exportFileName.getBytes("GBK"), "ISO8859_1");
		response.setHeader("Content-Disposition", s); // 以上输出文件元信息
		OutputStream output = response.getOutputStream();
		wb.write(output);
		output.flush();

	}

	/**
	 * 创建统计的样式
	 * 
	 * @param type
	 * @return
	 */
	public HSSFCellStyle creatCellStyle(short type) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setBorderBottom(type);// 下边框
		style.setBorderLeft(type);// 左边框
		style.setBorderRight(type);// 右边框
		style.setBorderTop(type);// 上边框
		return style;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
