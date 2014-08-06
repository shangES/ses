package com.mk.recruitment.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.tree.TreePageGrid;
import com.mk.recruitment.entity.WebUser;
import com.mk.recruitment.service.WebUserService;
import com.mk.recruitment.tree.EmailTree;

@Controller
public class WebUserAction {

	@Autowired
	private WebUserService webUserService;
	
	
	/**
	 * 邮箱用户树
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/searchEmailTree.do")
	@ResponseBody
	public TreePageGrid searchEmailTree(@RequestBody TreePageGrid grid) {
		List<WebUser> data = webUserService.searchEmailTree(grid);
		EmailTree tree = new EmailTree();
		List<TreeNode> nodes = tree.doBuild(data);
		grid.setDataList(nodes);
		return grid;
	}
	
	

	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/searchWebUser.do")
	public void searchWebUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		webUserService.searchWebUser(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil reportUtil = new ReportUtil();
			reportUtil.setTitle("外网人员");
			reportUtil.reportGrid(grid);
		} else {
			grid.printLoadResponseText();
		}
	}

	/**
	 * 保存修改
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/saveOrUpdateWebUser.do")
	@ResponseBody
	public WebUser saveOrUpdateWebUser(WebUser model) throws Exception {
		webUserService.saveOrUpdateWebUser(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param Id
	 * @return
	 */
	@RequestMapping("/recruitment/getWebUserById.do")
	@ResponseBody
	public WebUser getWebUserById(String id) {
		WebUser model = webUserService.getWebUserById(id);
		return model;
	}

	/**
	 * 失效
	 * 
	 * @param ids
	 * @param state
	 */
	@RequestMapping("/recruitment/validWebUserById.do")
	@ResponseBody
	public void validWebUserById(String ids, Integer state) {
		webUserService.validWebUserById(ids, state);
	}

	/**
	 * 删除
	 * 
	 * @param Ids
	 */
	@RequestMapping("/recruitment/delWebUserById.do")
	@ResponseBody
	public void delWebUserById(String ids) {
		webUserService.delWebUserById(ids);
	}
	
	/**
	 * 校验登录名
	 * @param name
	 * @return
	 */
	@RequestMapping("/recruitment/checkWebUserByEmail.do")
	@ResponseBody
	public String checkWebUserByEmail(String webuserguid,String name){
		return webUserService.checkWebUserByEmail(webuserguid,name);
	}
}
