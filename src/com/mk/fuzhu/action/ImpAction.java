package com.mk.fuzhu.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mk.ImportDataUtil;
import com.mk.department.service.DepartmentService;
import com.mk.department.service.PostService;
import com.mk.framework.constance.Constance;
import com.mk.framework.upload.FileProperty;
import com.mk.fuzhu.service.NameConvertCodeService;

@Controller
public class ImpAction {
	public static String CONTENTTYPE = "text/html; charset=UTF-8";
	@Autowired
	private NameConvertCodeService nameConvertCodeService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PostService postService;

	@RequestMapping("/yewu/imp.do")
	public void imp(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 数据
		FileProperty f = new FileProperty();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<?> list = upload.parseRequest(request);// 解析传过来的内容 返回LIST
		Iterator<?> it = list.iterator();// 对LIST进行枚举

		// sheet是否选择
		String sheetname = null;
		for (Object item : list) {
			FileItem mod = (FileItem) item;
			if (mod.isFormField()) {
				sheetname = mod.getString("UTF-8");
			}
		}


		// 数据
		while (it.hasNext()) {
			FileItem item = (FileItem) it.next();
			// 判断是文本信息还是对象（文件）
			if (item.isFormField()) {
				// 如果是文本信息返回TRUE
				// System.out.println("参数" + item.getFieldName() + ":" +
				// item.getString("UTF-8"));
				// 注意输出值的时候要注意加上编码
			} else {
				if (item.getName() != null && !item.getName().equals("")) {
					// 解析excel数据
					if (sheetname == null || sheetname.equals("") || sheetname.equals("null"))
						f.setSheetNames(getSheetName(item.getInputStream()));
					else
						f.setData(Excel(item.getInputStream(), sheetname));

					// System.out.println("上传文件的大小：" + item.getSize());
					// System.out.println("上传文件的类型：" + item.getContentType());
					// System.out.println("上传文件的名称：" + item.getName());
					f.setName(item.getName());
				}
			}
		}
		ObjectMapper om = new ObjectMapper();
		response.setContentType(CONTENTTYPE);
		PrintWriter out = response.getWriter();
		out.print(om.writeValueAsString(f));
		out.flush();
		out.close();
	}

	/**
	 * 解析出sheet名称
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private List<String> getSheetName(InputStream file) throws IOException {
		List<String> list = new ArrayList<String>();
		// 读到
		HSSFWorkbook exl = new HSSFWorkbook(file);
		// 取第一个sheet
		HSSFSheet sheet = null;
		for (int i = 0; i < exl.getNumberOfSheets(); i++) {
			sheet = exl.getSheetAt(i);
			list.add(sheet.getSheetName());
		}
		return list;
	}

	/**
	 * 处理exls的模板数据
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private List<?> Excel(InputStream file, String talbename) throws IOException {
		// 读到
		HSSFWorkbook exl = new HSSFWorkbook(file);

		// 取sheet
		HSSFSheet sheet = null;
		for (int i = 0; i < exl.getNumberOfSheets(); i++) {
			HSSFSheet tmpsheet = exl.getSheetAt(i);
			if (tmpsheet.getSheetName().equals(talbename))
				sheet = tmpsheet;
		}
		// 必须选择一个sheet
		if (sheet == null)
			return null;

		// 格式转换
		String sheetName = null;
		if (talbename.indexOf("_") != -1)
			sheetName = talbename.substring(0, talbename.indexOf("_"));
		else
			sheetName = talbename;

		// 列头
		HSSFCell cell = null;
		HSSFRow row = sheet.getRow(1);
		Map<String, Integer> columnTitle = new HashMap<String, Integer>();
		for (int m = row.getFirstCellNum(); m <= row.getLastCellNum(); m++) {
			cell = row.getCell(m);
			if (cell != null) {
				columnTitle.put(Constance.getCellValue(cell), m);
			}
		}
		return ImportDataUtil.start(nameConvertCodeService, sheet, sheetName, columnTitle,departmentService,postService);
	}

	

}
