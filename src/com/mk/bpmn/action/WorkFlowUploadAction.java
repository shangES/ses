package com.mk.bpmn.action;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mk.bpmn.service.WorkflowProcessDefinitionService;

@Controller
public class WorkFlowUploadAction {

	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;

	@RequestMapping("/uploadWorkflowFile.do")
	public ModelAndView uploadWorkflowFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 解析传过来的内容 返回LIST
		List list = upload.parseRequest(request);

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

					// 上传过来的文件
					ZipInputStream inputStream = new ZipInputStream(item.getInputStream());

					workflowProcessDefinitionService.deployment(name, inputStream);
				}
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(mapper.writeValueAsString(true));
		out.flush();
		out.close();
		return null;
	}

}
