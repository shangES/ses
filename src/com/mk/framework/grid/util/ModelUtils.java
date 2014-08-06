package com.mk.framework.grid.util;

import com.mk.framework.grid.common.Const;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.model.ColumnInfo;
import com.mk.framework.grid.model.FilterInfo;
import com.mk.framework.grid.model.GridInfo;
import com.mk.framework.grid.model.PageInfo;
import com.mk.framework.grid.model.SortInfo;

public class ModelUtils {
	public static GridInfo createGridInfo(JSONObject modelJS) {
		GridInfo info = new GridInfo();
		info.setId(modelJS.getString("id"));
		return info;
	}

	public static ColumnInfo createColumnInfo(JSONObject modelJS) {
		ColumnInfo info = new ColumnInfo();
		info.setId(modelJS.getString("id"));
		info.setHeader(modelJS.getString("header"));
		info.setFieldIndex(modelJS.getString("fieldIndex"));
		info.setSortOrder(modelJS.getString("sortOrder"));

		info.setIsnumber(modelJS.getBoolean("number"));
		info.setHidden(modelJS.getBoolean("hidden"));
		info.setExportable(modelJS.getBoolean("exportable"));
		info.setPrintable(modelJS.getBoolean("printable"));

		return info;
	}

	public static PageInfo createPageInfo(JSONObject modelJS) {
		PageInfo info = new PageInfo();

		info.setEndRowNum(modelJS.getInt("endRowNum", Const.nullInt));
		info.setPageNum(modelJS.getInt("pageNum", Const.nullInt));
		info.setPageSize(modelJS.getInt("pageSize", Const.nullInt));
		info.setStartRowNum(modelJS.getInt("startRowNum", Const.nullInt));
		info.setTotalPageNum(modelJS.getInt("totalPageNum", Const.nullInt));
		info.setTotalRowNum(modelJS.getInt("totalRowNum", Const.nullInt));

		return info;
	}

	public static JSONObject generatePageInfoJSON(PageInfo pageInfo) {
		JSONObject pageInfoJS = new JSONObject();
		pageInfoJS.put("endRowNum", pageInfo.getEndRowNum());
		pageInfoJS.put("pageNum", pageInfo.getPageNum());
		pageInfoJS.put("pageSize", pageInfo.getPageSize());
		pageInfoJS.put("startRowNum", pageInfo.getStartRowNum());
		pageInfoJS.put("totalPageNum", pageInfo.getTotalPageNum());
		pageInfoJS.put("totalRowNum", pageInfo.getTotalRowNum());
		return pageInfoJS;
	}

	public static SortInfo createSortInfo(JSONObject modelJS) {
		SortInfo info = new SortInfo();

		info.setColumnId(modelJS.getString("columnId"));
		info.setFieldName(modelJS.getString("fieldName"));
		info.setSortOrder(modelJS.getString("sortOrder"));

		return info;
	}

	public static FilterInfo createFilterInfo(JSONObject modelJS) {
		FilterInfo info = new FilterInfo();

		info.setColumnId(modelJS.getString("columnId"));
		info.setFieldName(modelJS.getString("fieldName"));
		info.setLogic(modelJS.getString("logic"));
		info.setValue(modelJS.getString("value"));

		return info;
	}

}
