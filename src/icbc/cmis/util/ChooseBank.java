package icbc.cmis.util;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import icbc.cmis.base.*;
import icbc.missign.Employee;
import icbc.cmis.util.Decode;
/**
 * Title: 选择银行.<br>
 * Description: Insert type's descript.<br>
 * Copyright: Copyright (c) 2003<br>
 * Creation date: (2003-11-10 9:16:18)<br>
 * @company: 中国工商银行杭州软件研发部<br>
 * @author: Yongcheng Shang<br>
 * @version: 1.0<br>
 */
public class ChooseBank extends HttpServlet {
    private static final int PAGE_LINES = 15;
    private static final int BUFFER_LINES = 10 * PAGE_LINES;
    private static final String CONTENT_TYPE = "text/xml; charset=GBK";
public void clear(
    CMisSessionMgr session,
    PrintWriter out,
    String bankcode,
    String shortname) {
    //清除SESSION中的相关数据
    try {
        session.removeSessionData("banksql3GB2U");
        session.removeSessionData("bankrecordCount3GB2U");
        session.removeSessionData("bankbufferBegin3GB2U");
        session.removeSessionData("bankbufferEnd3GB2U");
        session.removeSessionData("bankdata3GB2U");
        //点击返回不清掉session数据
        if (bankcode.equals("")) {

        } else {
            session.addSessionData("BankCodeInSession", bankcode);
            session.addSessionData("BankNameInSession", shortname);
        }
    } catch (Exception ex) {
    };
    out.println("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    out.println("<ok />");
}
  /**Clean up resources*/
  public void destroy() {
  }
/**Process the HTTP Get request*/
public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    doPost(request, response);
}
protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws javax.servlet.ServletException, java.io.IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    CMisSessionMgr session = new CMisSessionMgr(request);

    try {
        if (!CMisSessionMgr.checkSession(request)) {
            outTime(out, "  当前会话已失效，请重新登录");
            return;
        }

        String flag = request.getParameter("flag");
        String page = request.getParameter("page");

        //判断标志，根据标志转向不同处理
        if (flag.equals("newQuery")) {
            newQuery(request, session, out);
        } else
            if (flag.equals("query")) {
                query(session, out, page);
            } else
                if (flag.equals("done")) {
                    String bankcode = Decode.decode(request.getParameter("bankcode"));
                    String shortname = Decode.decode(request.getParameter("shortname"));
                    clear(session, out, bankcode, shortname);
                }

    } catch (TranFailException ex) {
        outTime(
            out,
            "数据提取失败！"
                + ex.getErrorCode()
                + ""
                + ex.getErrorLocation()
                + ""
                + ex.getDisplayMessage()
                + ""
                + ex.getErrorMsg()
                + "");
    } catch (Exception ex) {
        outTime(out, "数据提取失败！" + ex.getMessage());
    }

}
//  private static final String CONTENT_TYPE = "text/xml";
/**Initialize global variables*/
public void init() throws ServletException {
}
public void newQuery(
    HttpServletRequest request,
    CMisSessionMgr session,
    PrintWriter out)
    throws TranFailException {
    //新查询，取出查询参数，调用数据查询模块得到结果，将结果放入SESSION
    //返回结果集的第一页
    try {
        //取参数
        Employee employee = (Employee) session.getSessionData("Employee");
        String bankcode = request.getParameter("bankcode");
        if (bankcode == null)
            bankcode = "";
        String shortname = request.getParameter("shortname");
        if (shortname == null)
            shortname = "";

        String queryType = request.getParameter("queryType");

        Hashtable paras = new Hashtable();
        Enumeration pnames = request.getParameterNames();
        while (pnames.hasMoreElements()) {
            String tname = (String) pnames.nextElement();
            paras.put(tname, request.getParameter(tname));
        }

        //调用数据查询模块得到结果，将结果放入SESSION
        ChooseBankDAO dao =
            new ChooseBankDAO(new icbc.cmis.operation.DummyOperation());
        String sql[] = dao.genSQL(queryType, employee, bankcode, shortname, paras);
        session.updateSessionData("banksql3GB2U", sql);
        int rowCount = dao.getRecordCount(sql[0]);
        session.updateSessionData("bankrecordCount3GB2U", String.valueOf(rowCount));
        session.updateSessionData("bankbufferBegin3GB2U", null);
        session.updateSessionData("bankbufferEnd3GB2U", null);
        session.updateSessionData("bankdata3GB2U", null);

        //返回结果集的第一页
        query(session, out, "1");
    } catch (TranFailException ex) {
        throw ex;
    } catch (Exception ex) {
        throw new TranFailException(
            "cmisUTIL801",
            this.getClass().getName() + ".newQuery(HttpServletRequest, CMisSessionMgr, PrintWrite)",
            ex.getMessage(),
            ex.getMessage());
    }

}
public void outTime(PrintWriter out, String info) {
    out.println("<?xml version=\"1.0\"?>");
    out.println("<error>");
    out.println(info);
    out.println("</error>");
}
public void query(CMisSessionMgr session, PrintWriter out, String spage)
    throws TranFailException {
    //从SESSION中取回结果集，根据页号返回结果
    try {
        //从SESSION中取回数据
        String sql[] = (String[]) session.getSessionData("banksql3GB2U");
        int rowCount =
            Integer.parseInt((String) session.getSessionData("bankrecordCount3GB2U"));
        int bufferBegin = 0;
        int bufferEnd = 0;
        Vector datas = null;

        try {
            bufferBegin =
                Integer.parseInt((String) session.getSessionData("bankbufferBegin3GB2U"));
        } catch (Exception ex) {
        }
        try {
            bufferEnd =
                Integer.parseInt((String) session.getSessionData("bankbufferEnd3GB2U"));
        } catch (Exception ex) {
        }

        String warning = "";

        //取记录集大小
        int last = rowCount;
        int pages = (last - 1) / PAGE_LINES + 1;

        //计算应取回的记录起始和终止编号
        int page = Integer.parseInt(spage);
        int begin = (page - 1) * PAGE_LINES + 1;
        int end = (page) * PAGE_LINES;
        if (end > last) {
            end = last;
        }

        //检查是否已缓存
        if (begin < bufferBegin || end > bufferEnd) {
            if (begin < bufferBegin) {
                bufferBegin = begin / this.BUFFER_LINES * this.BUFFER_LINES + 1;
                bufferEnd = bufferBegin + this.BUFFER_LINES - 1;
            } else {
                bufferEnd = ((end - 1) / this.BUFFER_LINES + 1) * this.BUFFER_LINES;
                bufferBegin = bufferEnd - this.BUFFER_LINES + 1;
            }

            if (bufferBegin > begin) {
                bufferBegin = begin;
            }
            if (bufferEnd < end) {
                bufferEnd = end;
            }
            if (bufferEnd > last) {
                bufferEnd = last;
            }
            if (bufferBegin < 1) {
                bufferBegin = 1;
            }
            //updated by chenj 20030526
            //
            String newString = new String(sql[1]);
            newString += "ROWNUM <= " + bufferEnd + " ) WHERE rnum >= " + bufferBegin;

            ChooseBankDAO dao =
                new ChooseBankDAO(new icbc.cmis.operation.DummyOperation());
            datas = dao.query(newString, bufferBegin, bufferEnd);
            session.updateSessionData("bankbufferBegin3GB2U", String.valueOf(bufferBegin));
            session.updateSessionData("bankbufferEnd3GB2U", String.valueOf(bufferEnd));
            session.updateSessionData("bankdata3GB2U", datas);

            //System.out.println("new buffer:" + bufferBegin + " -> " + bufferEnd);
        } else {
            try {
                datas = (Vector) session.getSessionData("bankdata3GB2U");
            } catch (Exception ex) {
                throw new TranFailException(
                    "cmisUTIL803",
                    this.getClass().getName() + ".query(CMisSessionMgr, PrintWrite, String)",
                    ex.getMessage(),
                    "取缓存数据出错！");
            }
        }

        //返回结果
        out.println("<?xml version=\"1.0\" encoding=\"GBK\"?>");
        out.print(
            "<datas page='"
                + page
                + "' pages='"
                + pages
                + "' lines='"
                + last
                + "' warning='"
                + warning
                + "'>");
        if (datas != null && !datas.isEmpty()) {
            for (int i = 0; i <= end - begin; i++) {
                String[] row = (String[]) datas.get(begin - bufferBegin + i);
                /** bankvest, bankcode, shortname, fullname, institution, rownum, bankvestname, provincename, isicbc */
                out.print(
                    "<en o='"
                        + row[5]
                        + "' c='"
                        + row[0]
                        + "' v='"
                        + row[1]
                        + "' s='"
                        + row[2]
                        + "' f='"
                        + row[3]
                        + "' p='"
                        + row[4]
                        + "' vn='"
                        + row[6]
                        + "' pn='"
                        + row[7]
                        + "' isicbc='"
                        + row[8]
                        + "' />");
            }
        }
        out.print("</datas>");
    } catch (TranFailException ex) {
        throw ex;
    } catch (Exception ex) {
        throw new TranFailException(
            "cmisUTIL801",
            this.getClass().getName() + ".query(CMisSessionMgr, PrintWrite, String)",
            ex.getMessage(),
            "取数据出错！");
    }

}
}
