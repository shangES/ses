package com.mk.system.action;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;
import org.codehaus.jackson.map.ObjectMapper;
import org.htmlparser.beans.StringBean;
import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.constance.Constance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.spring.SpringContextHolder;
import com.mk.framework.upload.FileProperty;
import com.mk.person.entity.ExaminationRecord;
import com.mk.person.service.ExaminationRecordService;
import com.mk.resume.entity.ResumeEamilFile;
import com.mk.resume.entity.ResumeFile;
import com.mk.resume.service.ResumeEamilService;
import com.mk.resume.service.ResumeFileService;

@Controller
public class FileUploadAction {
	@Autowired
	private ResumeEamilService resumeEamilService;
	@Autowired
	private ResumeFileService resumeFileService;
	@Autowired
	private ExaminationRecordService examinationRecordService;

	public static String CONTENTTYPE = "text/html; charset=UTF-8";
	private String savePath;

	@RequestMapping("/uploadFile.do")
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

	/**
	 * 简历的下载
	 * 
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/resume/downloadResumeEmail.do")
	public void downloadResumeEmail(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResumeEamilFile model = resumeEamilService.getResumeEamilFileById(id);
		if (model == null) {
			response.sendError(404, "File not found!");
			return;
		}
		ServletContext sc = request.getSession().getServletContext();
		String sourceFile = sc.getRealPath(model.getFilepath());

		File f = new File(sourceFile);
		if (!f.exists()) {
			response.sendError(404, "File not found!");
			return;
		}

		// 设置输出的格式
		response.reset();
		response.setContentType("bin");
		// 后輟名
		String fileName = model.getFilename();
		String bmfileName = new String(fileName.getBytes("GBK"), "ISO8859_1");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + bmfileName + "\"");
		// 循环取出流中的数据
		byte[] b = new byte[102400];
		int len;
		FileInputStream in = new FileInputStream(f);
		while ((len = in.read(b)) > 0) {
			response.getOutputStream().write(b, 0, len);
		}
		in.close();
	}

	/**
	 * 附件下载
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/downloadDocument.do")
	public void downloadDocument(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResumeFile model = resumeFileService.getResumeFileListByResumeFileId(id);
		if (model == null) {
			response.sendError(404, "File not found!");
			return;
		}

		String physicalpath = SpringContextHolder.getApplicationContext().getResource("/").getFile().getAbsolutePath() + "/";
		File file = new File(physicalpath + model.getResumefilepath());
		String sourceFile = "";
		//外网路径查找
		if(!file.exists()){
			String filepath = physicalpath.replaceAll(request.getSession().getServletContext().getContextPath().substring(1, request.getSession().getServletContext().getContextPath().length()), Constance.PROJECTNAMEOUTER);
			model.setResumefilepath(filepath+model.getResumefilepath());
			sourceFile = model.getResumefilepath();
		}else{
			//内网
			ServletContext sc = request.getSession().getServletContext();
			sourceFile = sc.getRealPath(model.getResumefilepath());
		}

		File f = new File(sourceFile);
		if (!f.exists()) {
			response.sendError(404, "File not found!");
			return;
		}

		// 设置输出的格式
		response.reset();
		response.setContentType("bin");

		String fileName = model.getResumefilename();
		String bmfileName = new String(fileName.getBytes("GBK"), "ISO8859_1");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + bmfileName + "\"");
		// 循环取出流中的数据
		byte[] b = new byte[102400];
		int len;
		FileInputStream in = new FileInputStream(f);
		while ((len = in.read(b)) > 0) {
			response.getOutputStream().write(b, 0, len);
		}
		in.close();
	}
	
	/**
	 * 体检报告下载(自检的应聘情况)
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/downloadExaminationRecord.do")
	public void downloadExaminationRecord(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExaminationRecord model = examinationRecordService.getExaminationRecordById(id);
		if (model == null) {
			response.sendError(404, "File not found!");
			return;
		}
		String suffix = null;
		if(StringUtils.isNotEmpty(model.getFilepath())){
			String[] suffixs = model.getFilepath().split("\\.");
			if(suffixs.length>0)
				suffix = "."+suffixs[suffixs.length-(suffixs.length-1)];
		}else
			suffix=".doc";
		ServletContext sc = request.getSession().getServletContext();
		String sourceFile = sc.getRealPath(model.getFilepath());

		File f = new File(sourceFile);
		if (!f.exists()) {
			response.sendError(404, "File not found!");
			return;
		}

		// 设置输出的格式
		response.reset();
		response.setContentType("bin");

		String fileName = "体检报告";
		String bmfileName = new String(fileName.getBytes("GBK"), "ISO8859_1");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + bmfileName + suffix+"\"");
		// 循环取出流中的数据
		byte[] b = new byte[102400];
		int len;
		FileInputStream in = new FileInputStream(f);
		while ((len = in.read(b)) > 0) {
			response.getOutputStream().write(b, 0, len);
		}
		in.close();

		
	}
	
	
	/**
	 * 获取文档内容
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getWordContent.do")
	public ModelAndView getWordContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
					String fileName = item.getName();
					String name = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."), fileName.length());
					String temppath = path;
					path += "/" + name;
					savePath += name;
					File file = new File(path);
					item.write(file);// 写入服务器
					// 返回结果
					
					FileInputStream fileinputstream = new FileInputStream(file);
					String content = analysisContent(fileinputstream, file, temppath);
					f.setName(item.getName());
					f.setFilesize(item.getSize());
					f.setFilepath(savePath);
					f.setWordContent(content);
					
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
	
	/**
	 * 文件内容解析
	 * 
	 * @param fileinputstream
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws OpenXML4JException 
	 * @throws XmlException 
	 * @throws ParserException 
	 * @throws MessagingException 
	 */
	public String analysisContent(FileInputStream fileinputstream, File file, String path) throws IOException, XmlException, OpenXML4JException, ParserException, MessagingException{
		String content = "";
		String str = file.toString();
		int index = str.lastIndexOf(".");
		String suffixname = str.substring(index+1, str.length());
		if(suffixname.equals("doc")){
			try {
				//word 2003解析
				WordExtractor word = new WordExtractor(fileinputstream);
				content = word.getText();
			} catch (Exception e) {
				suffixname = str.substring(0, index)+".mht";
				if(!file.renameTo(new File(suffixname)))
					return null;
				content = resolveMHTFile(new File(suffixname), path);
			}
		}else if(suffixname.equals("docx")){
			//word 2007解析
			OPCPackage opcpackage = POIXMLDocument.openPackage(file.toString());
			POIXMLTextExtractor poixmltextextractor = new XWPFWordExtractor(opcpackage);
			content = poixmltextextractor.getText();
		}else if(suffixname.equals("xls")){
			//excel 2003解析
			HSSFWorkbook wordbook = new HSSFWorkbook(fileinputstream);
			StringBuffer stringbuffer = new StringBuffer();
			//遍历sheet
			for (int i = 0; i < wordbook.getNumberOfSheets(); i++) {
				if (null != wordbook.getSheetAt(i)) {
					//得到sheet
					HSSFSheet sheet = wordbook.getSheetAt(i);
					//遍历该sheet中的数据                    
					for (int j = 0; j < sheet.getLastRowNum(); j++) {
						HSSFRow row = sheet.getRow(j);
						//循环遍历cell
						for (int k = 0; k < row.getLastCellNum(); k++) {
							if (null != row.getCell(k)) {
								//获取单元格的值
								HSSFCell cell = row.getCell(k);
								if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
									stringbuffer.append(cell.getNumericCellValue());
								else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN)
									stringbuffer.append(cell.getBooleanCellValue());
								else
									stringbuffer.append(cell.getStringCellValue());
							}
						}
					}
				}
			}
			content = stringbuffer.toString();
		}else if(suffixname.equals("xlsx")){
			//excel 2007解析
			XSSFWorkbook wb = new XSSFWorkbook(fileinputstream);
			StringBuffer stringbuffer = new StringBuffer();
			//遍历sheet
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				XSSFSheet sheet = wb.getSheetAt(i);
				if (sheet == null)
					continue;
				//遍历行
				for (int j = 0; j < sheet.getLastRowNum(); j++) {
					 XSSFRow row = sheet.getRow(j);
					 if (row == null)
						 continue;
					 //遍历单元格
					 for (int k = 0; k < row.getLastCellNum(); k++) {
						 XSSFCell cell = row.getCell(k);
						 if (cell == null)
							 continue;
						 if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN)
							 stringbuffer.append(cell.getBooleanCellValue());
						 else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
							 stringbuffer.append(cell.getNumericCellValue());
						 else
							 stringbuffer.append(cell.getStringCellValue());
					 }
				}
			}
			content = stringbuffer.toString();
		}else if(suffixname.equals("txt")){
			//txt解析
			InputStreamReader read = new InputStreamReader(fileinputstream);
			BufferedReader bufferedReader = new BufferedReader(read);
			String text = null;
			while((text = bufferedReader.readLine())!=null){
				content = content + text;
			}
			bufferedReader.close();
			read.close();
		}else if(suffixname.equals("html") || suffixname.equals("htm")||suffixname.equals("mht")){
			if(suffixname.equals("mht")){
				content = resolveMHTFile(file, path);
			}else{
				StringBean stringbean = new StringBean();
				stringbean.setLinks(false);
				stringbean.setCollapse(true); 
				stringbean.setReplaceNonBreakingSpaces(true);
				stringbean.setURL(file.toString());
	            content = stringbean.getStrings();
			}
			}
		fileinputstream.close();
		return content;
	}
	
	/**
	 * 解析mht文件，获取内容
	 * 
	 * @param file
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public String resolveMHTFile(File file, String path) throws MessagingException, IOException{
		InputStream inputstream = new FileInputStream(file.toString());
		Session mailsession = Session.getDefaultInstance(System.getProperties(), null);
		MimeMessage mimemessage = new MimeMessage(mailsession, inputstream);
		Object content = mimemessage.getContent();
		String str = "";
		if (content instanceof Multipart){
			MimeMultipart mimemultipart = (MimeMultipart)content;    
            MimeBodyPart mimebodypart = (MimeBodyPart)mimemultipart.getBodyPart(0);
            //获取mht文件的内容  
            str = getHtmlText(mimebodypart);
            str = str.replaceAll("<[^>]*>","");
		}
		return str;
	}
	
	
	/** 
	 * 获取mht文件中的内容代码 
	 * @param mimebodypart 
	 * @return 
	 */  
	private static String getHtmlText(MimeBodyPart mimebodypart) {    
	    InputStream textStream = null;    
	    BufferedInputStream bufferedinputstream = null;    
	    BufferedReader bufferedreader = null;    
	    Reader reader = null;    
	    try {    
	        textStream = mimebodypart.getInputStream();    
	        bufferedinputstream = new BufferedInputStream(textStream);    
	        reader = new InputStreamReader(bufferedinputstream,"GBK");   
	        bufferedreader = new BufferedReader(reader);    
	        StringBuffer strHtml = new StringBuffer("");    
	        String strLine = null;    
	        while ((strLine = bufferedreader.readLine()) != null) {    
	            strHtml.append(strLine + "\r\n");    
	        }    
	        bufferedreader.close();    
	        reader.close();    
	        textStream.close();    
	        return new String(strHtml);    
	    } catch (Exception e) {    
	    e.printStackTrace();    
	    } finally{  
	        try{    
	            if (bufferedreader != null)    
	            	bufferedreader.close();    
	            if (bufferedinputstream != null)    
	            	bufferedinputstream.close();    
	            if (textStream != null)    
	            textStream.close();    
	        }catch(Exception e){    
	        }    
	    }    
	    return null;    
	}

}
