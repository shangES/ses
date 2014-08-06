package com.mk.resume.action;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.grid.util.StringUtils;
import com.mk.resume.entity.ResumeAssess;
import com.mk.resume.entity.ResumeFile;
import com.mk.resume.service.ResumeFileService;

@Controller
public class ResumeFileAction {
	@Autowired
	private ResumeFileService resumeFileService;

	/**
	 * 添加
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/getResumeFileHtml.do")
	public ModelAndView getResumeFileHtml(ModelMap map, String webuserguid, Integer ordernum) throws Exception {
		if (StringUtils.isEmpty(webuserguid))
			return null;

		map.put("resumeguid", webuserguid);
		map.put("ordernum", ordernum);
		return new ModelAndView("/pages/resume/form_resumefile.jsp");
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/getResumeFileListHtml.do")
	public ModelAndView getResumeFileListHtml(ModelMap map, String webuserguid) throws Exception {
		if (webuserguid.isEmpty())
			return null;
		List<ResumeFile> list = resumeFileService.getResumeFileListByResumeGuid(webuserguid);
		map.put("resumefiles", list);

		return new ModelAndView("/pages/resume/themes/list_resumefile.jsp");
	}

	/**
	 * 保存or修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/resume/saveOrUpdateResumeFile.do")
	@ResponseBody
	public ResumeFile saveOrUpdateResumeFile(ResumeFile model) {
		resumeFileService.saveOrUpdateResumeFile(model);
		return model;
	}
	
	
	/**
	 * 保存邮件
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/resume/uploadResumeFile.do")
	public void uploadResumeEamil(HttpServletRequest request, HttpServletResponse response) throws Exception {

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

					ResumeFile model =resumeFileService.getResumeFileListByResumeFileId(item.getName());
					if (model != null && StringUtils.isNotEmpty(model.getResumefilepath())) {
						File file = new File(request.getRealPath(model.getResumefilepath()));
						item.write(file);// 写入服务器
					}
				}
			}
		}
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/resume/delResumeFileById.do")
	@ResponseBody
	public void delResumeFileById(HttpServletRequest request,String ids) throws Exception {
		resumeFileService.delResumeFileById(request,ids);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/resume/getAllResumeFileByResumeId.do")
	@ResponseBody
	public List<ResumeFile> getAllResumeFileByResumeId(String id){
		List<ResumeFile> data = resumeFileService.getAllResumeFileByResumeId(id);
		return data;
	}

}
