package com.mk.system.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.grid.GridServerHandler;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.OptionType;
import com.mk.framework.tree.TreeNode;
import com.mk.system.service.OptionService;
import com.mk.system.tree.OptionListTree;
import com.mk.system.tree.OptiontypeTree;

@Controller
public class OptionAction {
	@Autowired
	private OptionService optionService;

	// ===============选项类型=================
	/**
	 * 得到全部的选项类型
	 * 
	 * @return
	 */
	@RequestMapping("/system/buildOptionTypesTree.do")
	@ResponseBody
	public Object[] buildOptionTypesTree() {
		List<OptionType> list = optionService.getAllOptiontype();
		OptiontypeTree tree = new OptiontypeTree();
		return new Object[] { tree.doBuild(list) };
	}

	/**
	 * 通过id得到选项类型的数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/system/getOptionTypeById.do")
	@ResponseBody
	public OptionType getOptionTypeById(String id) {
		OptionType model = optionService.getOptionTypeById(id);
		return model;
	}

	/**
	 * 保存or修改选项类型
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/system/saveOrUpdateOptionType.do")
	@ResponseBody
	public OptionType saveOrUpdateOptionType(OptionType model) {
		optionService.saveOrUpdateOptionType(model);
		return model;
	}

	// ===============选项列表=================
	
	/**
	 * 选项树
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/system/buildOptionListTree.do")
	@ResponseBody
	public List<TreeNode> buildOptionListTree(String code) throws Exception {
		OptionType type = optionService.getOptionTypeByCode(code);
		if (type == null)
			return null;
		List<OptionList> data = optionService.getOptionListByOptionTypeId(type.getOptiontypeguid());
		OptionListTree tree = new OptionListTree();
		return tree.doBuild(data, type);
	}

	
	
	/**
	 * 通过选项类型的id得到选项列表的数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/system/getOptionListByOptionTypeId.do")
	@ResponseBody
	public void getOptionListByTypeId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		String id = grid.getPageParameter("id");
		List<OptionList> list = optionService.getOptionListByOptionTypeId(id);
		grid.setData(list, OptionList.class);
		grid.printLoadResponseText();
	}

	/**
	 * 保存or修改选项列表的数据
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/system/saveOrUpdateOptionList.do")
	@ResponseBody
	public OptionList saveOrUpdateOptionList(OptionList model) {
		optionService.saveOrUpdateOptionList(model);
		return model;
	}

	/**
	 * 删除选项列表的数据
	 * 
	 * @param ids
	 */
	@RequestMapping("/system/delOptionListById.do")
	@ResponseBody
	public void delOptionListById(String ids) {
		optionService.delOptionListById(ids);
	}
	
	/**
	 * 通过选项列表的id得到选项列表的数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/system/getOptionListByListId.do")
	@ResponseBody
	public OptionList getOptionListByListId(String id) {
		OptionList model = optionService.getOptionListByListId(id);
		return model;
	}
	
	
	/**
	 * 验证代码
	 * @param optionid
	 * @param optiontypeguid
	 * @param code
	 * @return
	 */
	@RequestMapping("/system/checkOptionList.do")
	@ResponseBody
	public boolean checkOptionList(String optionid,String optiontypeguid,String  code){
		return optionService.checkOptionList(optionid,optiontypeguid,code);
	}
}
