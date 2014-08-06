package com.mk.system.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.context.ContextFacade;
import com.mk.framework.upload.FileProperty;

@Controller
public class CkeditUploadAction {
	public static String CONTENTTYPE = "text/html; charset=UTF-8";
	private String savePath;

	@RequestMapping("/uploadCkeditFile.do")
	public ModelAndView uploadCkeditFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		savePath = "upload/";
		FileProperty f = new FileProperty();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 解析传过来的内容 返回LIST
		List list = upload.parseRequest(request);

		// 解析物理地址
		String path = checkCkeditFilePath(request);

		// 解析文件
		Iterator it = list.iterator();// 对LIST进行枚举
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
					// System.out.println("上传文件的大小：" + item.getSize());
					// System.out.println("上传文件的类型：" + item.getContentType());
					// System.out.println("上传文件的名称：" + item.getName());
					// System.out.println("上传文件的路径: " + path);

					String fileName = item.getName();
					String name = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."), fileName.length());
					path += "/" + name;
					savePath += name;
					File file = new File(path);
					item.write(file);// 写入服务器
				}
			}
		}
		response.setContentType(CONTENTTYPE);
		PrintWriter out = response.getWriter();
		// request.getAttribute("baseUrl") +
		out.print("<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction(2, '/hrmweb/" + savePath + "', '');</script>");
		out.flush();
		out.close();

		return null;
	}

	private String checkCkeditFilePath(HttpServletRequest request) {
		// String path = request.getRealPath("/" + savePath);
		String path = request.getSession().getServletContext().getRealPath("/" + savePath);
		File file = new File(path);
		if (!file.isDirectory())
			file.mkdir();

		// 解析参数
		String directory = request.getParameter("directory");
		savePath += "/" + directory + "/";
		path += "/" + directory + "/";
		file = new File(path);
		if (!file.isDirectory())
			file.mkdir();

		// 加用户
		savePath += ContextFacade.getUserContext().getLoginname()+ "/";
		path += ContextFacade.getUserContext().getLoginname() + "/";

		file = new File(path);
		if (!file.isDirectory())
			file.mkdir();

		return path;
	}
}
