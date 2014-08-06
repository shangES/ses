package com.mk.person.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.constance.Constance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.spring.SpringContextHolder;
import com.mk.person.entity.Person;
import com.mk.person.service.PersonService;

@Controller
public class PersonAction {

	@Autowired
	PersonService personService;

	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/person/searchPerson.do")
	@ResponseBody
	public void searchPerson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		personService.searchPerson(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("待入职人才管理");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/person/getPersonById")
	@ResponseBody
	public Person getPersonById(HttpServletRequest request,String id) throws IOException {
		Person model = personService.getPersonById(id);
		String str = this.changePath(request, model.getPhoto());
		model.setPhoto(str);
		return model;
	}
	
	/**
	 * 选择图片下载路径，不在内网，到外网查找
	 * 
	 * @param request
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String changePath(HttpServletRequest request,String path) throws IOException{
		String tempPath = "";
		if(path==null)
			return null;
		//例子：http://localhost:port/project/filepath
		//  http://localhost:port
		String physicalpath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		String realphysicalpath = SpringContextHolder.getApplicationContext().getResource("/").getFile().getAbsolutePath() + "/";
		//file.toString===http://localhost:port/project/filepath
		File file = new File(realphysicalpath+path);
		if(file.exists()){
			//内网路径查找
			//项目名：/project/
			String filepath = physicalpath+"/"+request.getSession().getServletContext().getContextPath()+"/";
			tempPath = filepath+path;
		}else{
			String outerfilepath = realphysicalpath.replaceAll(request.getSession().getServletContext().getContextPath().substring(1, request.getSession().getServletContext().getContextPath().length()), Constance.PROJECTNAMEOUTER);
			File outerfile = new File(outerfilepath+path);
			if(outerfile.exists()){
				tempPath = physicalpath+"/"+Constance.PROJECTNAMEOUTER+"/"+path;
			}else
				tempPath=null;
		}
		
		return tempPath;
	}


	/**
	 * 保存或修改
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/person/saveOrUpdatePerson.do")
	@ResponseBody
	public Person saveOrUpdatePerson(HttpServletRequest request,Person model) throws Exception {
		personService.saveOrUpdatePerson(request,model);
		return model;

	}

	
	/**
	 * 保存
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/person/savePerson.do")
	@ResponseBody
	public Person savePerson(Person model) throws Exception {
		personService.savePerson(model);
		return model;

	}
	
	
	
	/**
	 * 删除
	 * 
	 * @param request
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/person/delPersonById.do")
	@ResponseBody
	public void delPersonById(HttpServletRequest request, String ids) throws Exception {
		personService.delPersonById(request, ids);
	}

}
