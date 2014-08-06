package com.mk.resume.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.context.ContextFacade;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.spring.SpringContextHolder;
import com.mk.framework.upload.FileProperty;
import com.mk.resume.entity.ResumeEamil;
import com.mk.resume.entity.ResumeEamilFile;
import com.mk.resume.entity.ResumeEmailFile;
import com.mk.resume.service.ResumeEamilService;

@Controller
public class ResumeEamilAction {
	@Autowired
	private ResumeEamilService resumeEamilService;
	public static String CONTENTTYPE = "text/html; charset=UTF-8";
	private String savePath;
	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/searchResumeEamil.do")
	@ResponseBody
	public void searchResumeEamil(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		resumeEamilService.searchResumeEamil(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("简历邮箱信息");
			report.reportGrid(grid);
		} else
		  grid.printLoadResponseText();

	}

	/**
	 * 保存or修改选项类型
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/resume/saveOrUpdateResumeEamil.do")
	@ResponseBody
	public ResumeEamil saveOrUpdateResumeEamil(ResumeEamil model) {
		resumeEamilService.saveOrUpdateResumeEamil(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/resume/getResumeEamilById.do")
	@ResponseBody
	public ResumeEamil getResumeEamilById(String id) {
		ResumeEamil model = resumeEamilService.getResumeEamilById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/delResumeEamilById.do")
	@ResponseBody
	public void delResumeEamilById(String ids) throws Exception {
		resumeEamilService.delResumeEamilById(ids);
	}

	/**
	 * 同步邮件
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/resume/refreshResumeEamil.do")
	@ResponseBody
	public void refreshResumeEamil() throws Exception {
		resumeEamilService.refreshResumeEamil();
	}

	/**
	 * 得到
	 * 
	 * @param mail
	 * @return
	 */
	@RequestMapping("/resume/getResumeEamilByEmail.do")
	@ResponseBody
	public String getResumeEamilByEmail(String email, String id) {
		return resumeEamilService.getResumeEamilByEmail(email, id);
	}

	/**
	 * 标记为已读
	 * 
	 * @param ids
	 */
	@RequestMapping("/resume/updateByReadtype.do")
	@ResponseBody
	public void updateByReadtype(String ids, Integer readtype) {
		resumeEamilService.updateByReadtype(ids, readtype);
	}
	
	/**
	 * 简历导入
	 * 
	 * @param file
	 * @throws Exception 
	 */
	@RequestMapping("/resume/resumeUpload.do")
	@ResponseBody
	public void resumeUpload(HttpServletRequest request,HttpServletResponse response) throws Exception{
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

					resumeEamilService.resumeUpload(item);
					
				}
			}
		}
	}
	
	@RequestMapping("/resume/resumeImport.do")
	@ResponseBody
	public void resumeImport(@RequestBody ResumeEmailFile list) throws IOException{
		List<FileProperty> listProperty = list.getList();
		for(FileProperty model : listProperty){
			File file = new File(model.getFilepath());
			resumeEamilService.resumeImport(file);
		}
	}
	
	/**
	 * excel解析
	 * 
	 * @param list
	 * @throws Exception 
	 */
	@RequestMapping("/resume/analysisExcel.do")
	public ModelAndView analysisExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		savePath = "upload/";
		FileProperty f = new FileProperty();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 解析传过来的内容 返回LIST
		List list = upload.parseRequest(request);

		// 解析物理地址
		String path = uploadFilePath(request, list);
		
		// 解析文件
		Iterator it = list.iterator();// 对LIST进行枚举
		while (it.hasNext()) {
			FileItem item = (FileItem) it.next();
			// 判断是文本信息还是对象（文件）
			if (item.isFormField()) {
			} else {
				if (item.getName() != null && !item.getName().equals("")) {

					String fileName = item.getName();
					String name = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."), fileName.length());
					path += "/" + name;
					savePath += name;
					File file = new File(path);
					item.write(file);// 写入服务器
					// 返回结果

					f.setName(item.getName());
					f.setFilesize(item.getSize());
					f.setFilepath(savePath);
					f.setSuccessed(true);
					f.setFilepath(file.toString());
					String physicalpath = SpringContextHolder.getApplicationContext().getResource("/").getFile().getAbsolutePath() + "/";
					//System.out.println("========"+physicalpath+savePath);
					File excelfile = new File(physicalpath+savePath);
					resumeEamilService.analysisExcel(excelfile);
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 简历多个导入
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadFileList.do")
	public ModelAndView fileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		savePath = "upload";
		FileProperty f = new FileProperty();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 解析传过来的内容 返回LIST
		List list = upload.parseRequest(request);

		// 解析物理地址
		String path = uploadFilePath(request, list);
		
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
					// 返回结果

					f.setName(item.getName());
					f.setFilesize(item.getSize());
					f.setFilepath(savePath);
					f.setSuccessed(true);
					f.setFilepath(file.toString());
				}
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType(CONTENTTYPE);
		PrintWriter out = response.getWriter();
		out.print(mapper.writeValueAsString(f));
		out.flush();
		out.close();
		return null;
	}

	public String uploadFilePath(HttpServletRequest request, List list) throws Exception {
		String path = request.getRealPath("/" + savePath);
		File file = new File(path);
		if (!file.isDirectory())
			file.mkdir();

		// 解析参数
		String sheetname = null;
		for (Object item : list) {
			FileItem mod = (FileItem) item;
			if (mod.isFormField()) {
				savePath += "/" + mod.getString("UTF-8") + "/";
				path += "/" + mod.getString("UTF-8") + "/";
			}
		}
		// 加表名
		file = new File(path);
		String pathbefor = path.substring(0, path.indexOf("upload")+6);
		path = "";
		path = pathbefor +"/";
		if (!file.isDirectory())
			file.mkdir();
		

		// 加用户
		savePath += ContextFacade.getUserContext().getLoginname() + "/";
		path += ContextFacade.getUserContext().getLoginname() + "/";
		
		file = new File(path);
		if (!file.isDirectory())
			file.mkdir();

		return path;
	}

}
