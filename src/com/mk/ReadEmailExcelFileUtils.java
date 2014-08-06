package com.mk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.grid.util.DateUtil;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.NameConvertCodeService;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.recruitment.dao.RecruitmentDao;
import com.mk.recruitment.dao.WebUserDao;
import com.mk.recruitment.entity.RecruitPost;
import com.mk.recruitment.entity.WebUser;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.EducationExperience;
import com.mk.resume.entity.ProjectExperience;
import com.mk.resume.entity.Resume;
import com.mk.resume.entity.TrainingExperience;
import com.mk.resume.entity.WorkExperience;

public class ReadEmailExcelFileUtils {

	/**
	 * 解析excel
	 * 
	 * @param file
	 * @throws IOException 
	 */
	@Transactional
	public static String analysisExcel(File file,SqlSession sqlSession,NameConvertCodeService nameConvertCodeService) throws IOException{
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);

		FileInputStream fileinputstream = new FileInputStream(file);
		String suffixname = "";
		suffixname = file.toString().substring(file.toString().lastIndexOf(".")+1, file.toString().length());
		//System.out.println("suffixname===="+suffixname);
		if(suffixname.equals("xls")){
			//excel2003
			HSSFWorkbook workbook = new HSSFWorkbook(fileinputstream);
			//System.out.println("=====getNumberOfSheets====="+workbook.getNumberOfSheets());
			for(int i = 0; i < workbook.getNumberOfSheets();i++){
				//System.out.println("===getNumberOfSheets_"+i+"==="+workbook.getSheetAt(i));
				if(workbook.getSheetAt(i) != null){
					//System.out.println("===getLastRowNum_"+i+"===="+workbook.getSheetAt(i).getLastRowNum() );
					String candidatesposation = "";
					String candidatescompany = "";
					String candidatesdate = "";
					String name = "";
					String sexname = "";
					String birthday = "";
					String homeplace = "";
					String workage = "";
					String email = "";
					String phone = "";
					String valuation = "";
					String industry = "";
					String salary = "";
					String occupation = "";
					String situation = "";
					Resume resume = new Resume();
					List<WorkExperience> worklist = new ArrayList<WorkExperience>();
					List<ProjectExperience> projectlist = new ArrayList<ProjectExperience>();
					List<EducationExperience> educationlist = new ArrayList<EducationExperience>();
					List<TrainingExperience> traininglist = new ArrayList<TrainingExperience>();
					HSSFSheet hssfsheet = workbook.getSheetAt(i);
					boolean state = false;
					if(hssfsheet.getRow(1).getLastCellNum() > 15)
						continue;
					//String cell = hssfsheet.getRow(0).getCell(0).getStringCellValue().replaceAll(" ", "");
					boolean statejob = hssfsheet.getRow(0).getCell(0)==null?true:false;
					//System.out.println("=====cell====="+cell);
					if(!statejob){
						//System.out.println("======智联招聘======");
						analysisZLZPExcel(hssfsheet,null,nameConvertCodeService,resumeDao,webUserDao,myCandidatesDao,recruitmentDao);
						//break;
					}else{
						//System.out.println("======___111111111111111___======");
						//遍历该sheet中的数据，遍历excel中有效的行数
						//忽略前面两行
						for(int rownum = 2; rownum < hssfsheet.getLastRowNum(); rownum++){
							//第roownum行
							HSSFRow row = hssfsheet.getRow(rownum);
							//忽略第一列
							for(int cellnum = 1; cellnum < row.getLastCellNum();cellnum++){
								String content = row.getCell(cellnum).getStringCellValue().replaceAll(" ", "");
								//try {
									if(content.equals("应聘职位")){
										candidatesposation = row.getCell(cellnum+1).getStringCellValue();
										break;
									}else if(content.equals("应聘公司/部门")){
										candidatescompany = row.getCell(cellnum+1).getStringCellValue();
										break;
									}else if(content.equals("更新日期")){
										candidatesdate = row.getCell(cellnum+1).getStringCellValue();
										break;
									}else if(content.equals("姓名")){
										name = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("姓名===="+name);
										break;
									}else if(content.equals("性别")){
										sexname = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("性别===="+sexname);
										break;
									}else if(content.equals("出生日期")){
										birthday = row.getCell(cellnum+1).getStringCellValue().replaceAll(" ", "")+"-01";
										//Date date = DateUtil.parse(birthday);
										//System.out.println("出生日期===="+birthday);
										break;
									}else if(content.equals("目前居住地")){
										homeplace = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("目前居住地===="+homeplace);
										break;
									}else if(content.equals("工作年限")){
										workage = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("工作年限===="+workage);
										break;
									}else if(content.equals("电子邮件")){
										email = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("电子邮件===="+email);
										break;
									}else if(content.equals("移动电话")){
										phone = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("移动电话===="+phone);
										break;
									}else if(content.equals("自我评价")){
										if(!state){
											state = true;
											break;
										}else{
											state = false;
											valuation = row.getCell(cellnum+1).getStringCellValue();
										}
										//System.out.println("自我评价===="+valuation);
										if(StringUtils.isEmpty(valuation))
											valuation = "无";
										break;
									}else if(content.equals("希望行业")){
										industry = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("希望行业===="+industry);
										if(StringUtils.isEmpty(industry))
											industry = "无";
										break;
									}else if(content.equals("期望工资")){
										salary = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("期望工资===="+salary);
										break;
									}else if(content.equals("目标职能")){
										occupation = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("目标只能===="+occupation);
										if(StringUtils.isEmpty(occupation))
											occupation = "无";
										break;
									}else if(content.equals("求职状态")){
										situation = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("求职状态===="+situation);
										break;
									}else if(content.equals("工作经验")){
										/*System.out.println("state=="+state);*/
										if(!state){
											/*System.out.println("---------"+row.getCell(cellnum+1).getStringCellValue());*/
											state = true;
											break;
										}else{
											//System.out.println("====工作经验====");
											state = false;
											String projectcontent = row.getCell(cellnum+1).getStringCellValue();
											if(StringUtils.isNotEmpty(projectcontent)){
												/*System.out.println("----projectcontent-----"+row.getCell(cellnum+1).getStringCellValue());*/
												String[] arrproject = projectcontent.split("\\*");
												String[] arrcontents = Arrays.toString(arrproject).substring(1, Arrays.toString(arrproject).length()-1).split("\n");
												String string = "";
												for(String str : arrcontents){
													string += str;
												}
												/*System.out.println("===string==="+string);*/
												String[] tmp = string.split(", , , , , , , , , , , , , , , , , , , , ");
												/*System.out.println("oooooooooo==="+tmp.length);
												for(String str : tmp){
													System.out.println(">>>>>>>>>>>"+str);
												}*/
													if(tmp.length > 0){
														for(int arrynum = 0; arrynum < tmp.length;arrynum++){
															String[] arrdate = tmp[arrynum].replaceAll(" ", "").split("——");
															//开始时间
															String startdate = "";
															//结束时间
															String enddate = "";
															//工作单位
															String workunit = "";
															//职位
															String posation = "";
															//职责描述
															String jobdescription = "";
															//部门
															String department = "";
															
															if(arrdate.length > 0){
//																System.out.println("sssssssssss=="+arrdate[0]);
																int index = arrdate[0].lastIndexOf("至");
																startdate = arrdate[0].substring(index-7, index)+"-01";
																enddate = arrdate[0].subSequence(index+1, arrdate[0].length())+"-01";
																/*System.out.println("开始时间===="+startdate);
																System.out.println("结束时间===="+enddate);*/
															}
															if(arrdate.length > 1){
																String[] arrcontent = arrdate[1].split(":");
																if(arrcontent.length > 0){
																	for(int worknum = 0; worknum < arrcontent.length; worknum++){
																		//换行
																		//System.out.println("===worknum====="+arrcontent[worknum]);
																		String[] arrydelempty = arrcontent[worknum].split("\\s");
																		if(arrydelempty.length > 0){
																			for(int worknum_1 = 0;worknum_1 < arrydelempty.length;worknum_1++){
																				//System.out.println("=====arrcontent[worknum]======"+arrydelempty[worknum_1]);
																				String forwork = arrydelempty[worknum_1].replaceAll(" ", "");
																				//System.out.println("====forwork===="+forwork);
																				String[] temp = forwork.split("：");
																				for(int k = 0; k < temp.length; k++){
																					String worktmp = temp[k];
																					if(worktmp.equals("公司")){
																						String[] arrycom = arrcontent[worknum+1].split(" ");
																						if(arrycom.length > 0)
																							workunit = arrycom[0];
																						//System.out.println("工作单位===="+workunit);
																					}else if(worktmp.equals("职位")){
																						//System.out.println("====worktmp职位===="+arrcontent[worknum+1]);
																						posation = arrcontent[worknum+1];
																						//System.out.println("职位===="+posation);
																					}else if(worktmp.equals("工作描述")){
																						jobdescription = temp[k+1];
																						//System.out.println("工作描述===="+jobdescription);
																						//String[] arrydescription = arrcontent[worknum+1].split(" ");
																						//System.out.println("====worktmp工作描述===="+arrcontent[worknum+1]);
																						/*if(arrydescription.length > 0){
																							for(int j = 0; j < arrydescription.length-1; j++)
																								jobdescription += arrydescription[j];
																						}*/
																						//System.out.println("工作描述===="+jobdescription);
																					}else if(worktmp.equals("部门")){
																						department = arrcontent[worknum+1];
																						//System.out.println("部门===="+department);
																					}
																				}
																				
																			}
																		}
																	}
																}
															}
															
															if(StringUtils.isEmpty(workunit))
																workunit = "无";
															if(StringUtils.isEmpty(posation))
																posation = "无";
															if(StringUtils.isEmpty(jobdescription))
																jobdescription = "无";
															if(workunit.length() > 50)
																workunit = workunit.substring(0, 50);
															if(posation.length() > 50)
																posation = posation.substring(0, 5);
															if(jobdescription.length() > 500)
																jobdescription = jobdescription.substring(0, 500);
															
															WorkExperience model = new WorkExperience();
															model.setWorkexperienceguid(UUIDGenerator.randomUUID());
															model.setWorkunit(workunit);
															model.setPosation(posation);
															model.setJobdescription(jobdescription);
															model.setStartdate(DateUtil.parse(startdate));
															model.setEnddate(DateUtil.parse(enddate));
															model.setModtime(new Timestamp(System.currentTimeMillis()));
															worklist.add(model);
														}
													}
													//System.out.println("====worklist===="+worklist.size());
											}
											break;
											}
									}else if(content.equals("项目经验")){
										if(!state){
											state = true;
											break;
										}else{
											//System.out.println("====项目经验====");
											state = false;
											String projectcontent = row.getCell(cellnum+1).getStringCellValue();
											if(StringUtils.isNotEmpty(projectcontent)){
												String[] arrproject = projectcontent.split("\\*");
												String[] arrcontent = Arrays.toString(arrproject).substring(1, Arrays.toString(arrproject).length()-1).split("\\s");
												String string = "";
												for(String str : arrcontent){
													string += str;
												}
												//System.out.println("====str===="+string);
												String[] tmp = string.split(",,,,,,,,,,,,,,,,,,,,");
												//System.out.println("==项目经验个数=="+tmp.length);
												if(tmp.length > 0){
													for(int arrytnum = 0; arrytnum < tmp.length;arrytnum++){
														//	System.out.println("sssssssssss===="+tmp[arrytnum]);
															//开始时间
															String startdate = "";
															//结束时间
															String enddate = "";
															//项目名称
															String itemname = "";
															//职责描述
															String jobdescription = "";
															String[] arrdate = tmp[arrytnum].split("——");
															if(arrdate.length > 0){
																//System.out.println("====arrdate[0]==="+arrdate[0]);
																int index = arrdate[0].lastIndexOf("至");
																startdate = arrdate[0].substring(index-7, index)+"-01";
																enddate = arrdate[0].subSequence(index+1, arrdate[0].length())+"-01";
																/*System.out.println("开始时间===="+startdate);
																System.out.println("结束时间===="+enddate);*/
															}
															if(arrdate.length > 1){
																String tmpcontent = "";
																for(String str : arrdate){
																	//System.out.println("XXXXXXXXXx==="+str);
																	tmpcontent += str;
																}
																//System.out.println("yyyyyyyyyy==="+tmpcontent);
																//System.out.println("arrdate===="+arrdate[1]);
																String[] arrycontent = tmpcontent.split("：");
																/*for(String str : arrycontent)
																System.out.println("oooooooo==="+str);*/
																if(arrycontent.length > 0){
																	String[] arryitemname = arrycontent[0].split("\\s");
																	
																	if(arryitemname.length > 0)
																		itemname = arryitemname[0].substring(0, arryitemname[0].length()-4);
																	//System.out.println("项目名称===="+itemname);
																	for(int projectnum = 0; projectnum < arrycontent.length;projectnum++){
																		//换行
																		String[] arrydelempty = arrycontent[projectnum].split("\\s");
																	/*	for(String str : arrydelempty)
																		System.out.println("===项目内容===="+str);*/
																		if(arrydelempty.length > 0){
																			for(int projectnum_1 = 0; projectnum_1 < arrydelempty.length; projectnum_1++){
																				if(arrydelempty[projectnum_1].length() > 4){

																					String projecttmp = arrydelempty[projectnum_1].replaceAll(" ","").substring(arrydelempty[projectnum_1].length()-4, arrydelempty[projectnum_1].length());
																					//System.out.println("====projecttmp===="+projecttmp);
																					if(projecttmp.equals("项目描述")){
																						jobdescription = arrycontent[projectnum+1];
																						//System.out.println("项目描述===="+jobdescription);
																					}
																				
																				}
																			}
																		}
																	}
																}
															}
																if(StringUtils.isEmpty(itemname))
																	itemname = "无";
																if(StringUtils.isEmpty(jobdescription))
																	jobdescription = "无";
																if(itemname.length() > 50)
																	itemname = itemname.substring(0, 50);
																if(jobdescription.length() > 500)
																	jobdescription = jobdescription.substring(0, 500);
																ProjectExperience model = new ProjectExperience();
																model.setProjectexperienceguid(UUIDGenerator.randomUUID());
																model.setItemname(itemname);
																model.setJobdescription(jobdescription);
																model.setStartdate(DateUtil.parse(startdate));
																model.setEnddate(DateUtil.parse(enddate));
																model.setModtime(new Timestamp(System.currentTimeMillis()));
																projectlist.add(model);
															}
												}
											}
											break;
										}
									}else if(content.equals("教育经历")){
										if(!state){
											state = true;
											break;
										}else{
											//System.out.println("====教育经历====");
											state = false;
											String projectcontent = row.getCell(cellnum+1).getStringCellValue();
											if(StringUtils.isNotEmpty(projectcontent)){
												/*System.out.println("===projectcontent==="+projectcontent);*/
												String[] arrproject = projectcontent.split("\\*");
												//System.out.println("===="+Arrays.toString(arrproject).substring(1, Arrays.toString(arrproject).length()-1));
												String[] arrcontent = Arrays.toString(arrproject).substring(1, Arrays.toString(arrproject).length()-1).split("\n");
												String string = "";
												for(String str : arrcontent){
													string += str;
												}
												String[] tmp = string.split(", , , , , , , , , , , , , , , , , , , , ");
												if(tmp.length > 0){
													for(int arrnum = 0; arrnum < tmp.length; arrnum++){
														String[] arrdate = tmp[arrnum].split("——");
														//开始时间
														String startdate = "";
														//结束时间
														String enddate = "";
														//学校
														String school = "";
														//专业
														String specialty = "";
														//专业描述
														String majordescription = "";
														//学历
														String culturename = "";
														if(arrdate.length > 0){
															int index = arrdate[0].lastIndexOf("至");
															startdate = arrdate[0].substring(index-7, index)+"-01";
															enddate = arrdate[0].subSequence(index+1, arrdate[0].length())+"-01";
															////System.out.println("开始时间===="+startdate);
															//System.out.println("结束时间===="+enddate);
															if(arrdate.length > 1){
																String[] arrycontent = arrdate[1].split("\\s");
																if(arrycontent.length > 0)
																	school = arrycontent[0];
																if(arrycontent.length > 1){
																	specialty = arrycontent[1].substring(0, arrycontent[1].length()-2);
																//	System.out.println("专业===="+specialty);
																	culturename = arrycontent[1].substring(arrycontent[1].length()-2, arrycontent[1].length()).replaceAll(" ", "");
																//	System.out.println("学历===="+culturename);
																}
																if(arrycontent.length > 2){
																	majordescription = arrycontent[2];
																	//System.out.println("专业描述===="+majordescription);
																}
															}
														}
														if(StringUtils.isEmpty(school))
															school = "无";
														if(StringUtils.isEmpty(specialty))
															specialty = "无";
														if(StringUtils.isEmpty(majordescription))
															majordescription = "无";
														if(school.length() > 50)
															school = school.substring(0, 50);
														if(specialty.length() > 50)
															specialty = specialty.substring(0, 50);
														if(majordescription.length() > 500)
															majordescription = majordescription.substring(0, 500);
														// 数据库翻译
														Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturename);
														if (culture == null)
															culture = Constance.VALID_YES;
														EducationExperience model =new EducationExperience();
														model.setEducationexperienceguid(UUIDGenerator.randomUUID());
														model.setSchool(school);
														model.setSpecialty(specialty);
														model.setCulture(culture);
														model.setCulturename(culturename);
														model.setMajordescription(majordescription);
														model.setModtime(new Timestamp(System.currentTimeMillis()));
														model.setStartdate(DateUtil.parse(startdate));
														model.setEnddate(DateUtil.parse(enddate));
														educationlist.add(model);
													}
												}
											}
											break;
										}
									}else if(content.equals("培训经历")){
										if(!state){
											state = true;
											break;
										}else{
											//System.out.println("====培训经历====");
											state = false;
											String projectcontent = row.getCell(cellnum+1).getStringCellValue();
											if(StringUtils.isNotEmpty(projectcontent)){
												/*System.out.println("----projectcontent-----"+row.getCell(cellnum+1).getStringCellValue());*/
												String[] arrproject = projectcontent.split("\\*");
												String[] arrcontents = Arrays.toString(arrproject).substring(1, Arrays.toString(arrproject).length()-1).split("\n");
												String string = "";
												for(String str : arrcontents){
													string += str;
												}
												/*System.out.println("===string==="+string);*/
												String[] tmp = string.split(", , , , , , , , , , , , , , , , , , , , ");
													if(tmp.length > 0){
														for(int arrynum = 0; arrynum < tmp.length;arrynum++){
															String startdate = "";
															String enddate = "";
															//培训机构
															String traininginstitutions = "";
															//证书
															String certificate = "";
															//内容
															String trainingcontent = "";
															String[] arrdate = tmp[arrynum].split("——");
															if(arrdate.length > 0){
																int index = arrdate[0].lastIndexOf("至");
																startdate = arrdate[0].substring(index-7, index)+"-01";
																enddate = arrdate[0].subSequence(index+1, arrdate[0].length())+"-01";
																//System.out.println("开始时间===="+startdate);
																//System.out.println("结束时间===="+enddate);
																if(arrdate.length > 1){
																	String[] contents = arrdate[1].split("\\s");
																	if(contents.length > 0){
																		String[] training = contents[0].split(" ");
																		if(training.length > 0)
																			traininginstitutions = training[0];
																	}
																	if(contents.length > 3)
																		for(int j = 3; j < contents.length;j++){
																			if(j==contents.length-1){
																				trainingcontent += contents[j];	
																			}else
																				trainingcontent += contents[j]+"。";
																		}
																}
															}
															if(StringUtils.isEmpty(certificate))
																certificate = "无";
															if(StringUtils.isEmpty(traininginstitutions))
																traininginstitutions = "无";
															if(StringUtils.isEmpty(trainingcontent))
																trainingcontent = "无";
															if(certificate.length() > 50)
																certificate = certificate.substring(0, 50);
															if(traininginstitutions.length() > 50)
																traininginstitutions = traininginstitutions.substring(0, 50);
															if(trainingcontent.length() > 500)
																trainingcontent = trainingcontent.substring(0, 50);
															
															TrainingExperience model = new TrainingExperience();
															model.setTrainingexperienceguid(UUIDGenerator.randomUUID());
															model.setTrainingcontent(trainingcontent);
															model.setCertificate(certificate);
															model.setTraininginstitutions(traininginstitutions);
															model.setStartdate(DateUtil.parse(startdate));
															model.setEnddate(DateUtil.parse(enddate));
															model.setModtime(new Timestamp(System.currentTimeMillis()));
															traininglist.add(model);
														}
													}
											}
											break;
										}
									}
								/*} catch (Exception e) {
									return "模板错误！";
								}*/
							}
						}
						
						// 外網用戶
						WebUser user = null;
						// 保存到本地
						//System.out.println("===email==="+email);
						user = webUserDao.checkWebUserByEmail(null, email);
						if (user == null) {
							// 保存外网用户
							user = new WebUser(email, name);
							user.setWebuserguid(UUIDGenerator.randomUUID());
							user.setMobile(phone);
							// System.out.println(user.toString());
							webUserDao.insertWebUser(user);
						}else
							user.setMobile(phone);
						
						// 简历信息
						resume = resumeDao.getResumeById(user.getWebuserguid());
						
						// 如果簡歷存在以最新的為準
						boolean isnew = false;
						if (resume == null) {
							isnew = true;
							resume = new Resume();
							resume.setWebuserguid(user.getWebuserguid());
							resume.setCreatetime(new Timestamp(System.currentTimeMillis()));
						}
						resume.setModtime(new Timestamp(System.currentTimeMillis()));
						// 姓名
						if (user.getUsername().length() > 25)
							resume.setName(user.getUsername().substring(0, 25));
						else
							resume.setName(user.getUsername());
						// 电话
						resume.setMobile(user.getMobile());
						// 邮件
						resume.setEmail(user.getEmail());
						// 数据翻译
						Integer sex = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.SEX, sexname);
						if (sex != null) {
							resume.setSexname(sexname);
							resume.setSex(sex);
						} else {
							resume.setSex(Constance.VALID_NO);
						}
						//System.out.println("===birthday===="+birthday);
						resume.setBirthday(DateUtil.parse(birthday));
						//System.out.println("==homeplace===="+homeplace);
						if(homeplace.length() > 50)
							homeplace = homeplace.substring(0, 50);
						resume.setHomeplace(homeplace);
						resume.setMark(Constance.VALID_NO);
						// 数据库翻译
						String cultruename = "无";
						if(educationlist.size() > 0)
							cultruename = educationlist.get(0).getCulturename();
						Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, cultruename);
						if (culture != null) {
							resume.setCulturename(cultruename);
							resume.setCulture(culture);
						} else
							resume.setCulture(Constance.VALID_NO);
						if(situation.length() > 50)
							situation = situation.substring(0, 50);
						if(salary.length() > 50)
							salary = salary.substring(0, 50);
						if(occupation.length() > 100)
							occupation = occupation.substring(0, 100);
						if(valuation.length() > 500)
							valuation = valuation.substring(0, 500);
						if(industry.length() > 50)
							industry = industry.substring(0, 50);
						resume.setValuation(valuation);
						resume.setSalary(salary);
						resume.setSituation(situation);
						resume.setOccupation(occupation);
						resume.setIndustry(industry);
						
						if (isnew)
							resumeDao.insertResume(resume);
						else
							resumeDao.updateResume(resume);
						
						//工作经验
						resumeDao.delWorkExperienceByWebuserId(resume.getWebuserguid());
						for(WorkExperience model : worklist){
							model.setWebuserguid(resume.getWebuserguid());
							resumeDao.insertWorkExperience(model);
						}
						//项目经验
						resumeDao.delProjectExperienceByWebuserId(resume.getWebuserguid());
						for(ProjectExperience model : projectlist){
							model.setWebuserguid(resume.getWebuserguid());
							resumeDao.insertProjectExperience(model);
						}
						//教育经历
						resumeDao.delEducationExperienceByWebuserId(resume.getWebuserguid());
						for(EducationExperience model : educationlist){
							model.setWebuserguid(resume.getWebuserguid());
							resumeDao.insertEducationExperience(model);
						}
						//培训经历
						resumeDao.delTrainingExperienceByWebuserId(resume.getWebuserguid());
						for(TrainingExperience model : traininglist){
							model.setWebuserguid(resume.getWebuserguid());
							resumeDao.insertTrainingExperience(model);
						}
						
						List<MyCandidates> tmpList = myCandidatesDao.getMyCandidatesByWebUserGuidAndState(user.getWebuserguid(), Constance.CandidatesState_Blacklist);
						// 实例化
						MyCandidates model = new MyCandidates();
						model.setMycandidatesguid(UUIDGenerator.randomUUID());
						model.setWebuserguid(user.getWebuserguid());
						model.setCandidatesstate(tmpList.isEmpty() ? Constance.CandidatesState_One : Constance.CandidatesState_Blacklist);
						model.setCandidatestype(Constance.User5);
						model.setProgress(Constance.VALID_NO);
						model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
						model.setReadtype(Constance.VALID_NO);
						// 職位
						List<RecruitPost> list = recruitmentDao.getRecruitPostByRecruitPostName(candidatesposation);
						model.setPostname(candidatesposation);
						if (!list.isEmpty()) {
							RecruitPost recruitPost = list.get(0);
							model.setRecruitpostguid(recruitPost.getRecruitpostguid());
						} else {
							model.setRecruitpostname(candidatesposation);
						}
						//过滤掉一周以内投递过的相同简历
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						model.setTempdate(format.format(System.currentTimeMillis()));
						List<MyCandidates> candidateslist = myCandidatesDao.checkResume(model);
						if(candidateslist != null&&!candidateslist.isEmpty()){
							//System.out.println("===一周内以投递===");
							continue;
						}
						
						model.setCandidatestime(DateUtil.parse(candidatesdate));
						myCandidatesDao.insertMyCandidates(model);
					}
				}
			}
		}else if(suffixname.equals("xlsx")){

			//excel2003
			XSSFWorkbook workbook = new XSSFWorkbook(fileinputstream);
			for(int i = 0; i < workbook.getNumberOfSheets();i++){
				//System.out.println("===getNumberOfSheets_"+i+"==="+workbook.getSheetAt(i));
				if(workbook.getSheetAt(i) != null){
					//System.out.println("===getLastRowNum_"+i+"===="+workbook.getSheetAt(i).getLastRowNum() );
					String candidatesposation = "";
					String candidatescompany = "";
					String candidatesdate = "";
					String name = "";
					String sexname = "";
					String birthday = "";
					String homeplace = "";
					String workage = "";
					String email = "";
					String phone = "";
					String valuation = "";
					String industry = "";
					String salary = "";
					String occupation = "";
					String situation = "";
					Resume resume = new Resume();
					List<WorkExperience> worklist = new ArrayList<WorkExperience>();
					List<ProjectExperience> projectlist = new ArrayList<ProjectExperience>();
					List<EducationExperience> educationlist = new ArrayList<EducationExperience>();
					List<TrainingExperience> traininglist = new ArrayList<TrainingExperience>();
					XSSFSheet xssfsheet = workbook.getSheetAt(i);
					boolean state = false;
					if(xssfsheet.getRow(1).getLastCellNum() > 20)
						continue;
					String cell = xssfsheet.getRow(1).getCell(1).getStringCellValue().replaceAll(" ", "");
					String celltitle = xssfsheet.getRow(1).getCell(2).getStringCellValue().replaceAll(" ", "");
					if(StringUtils.isNotEmpty(cell) && !celltitle.equals("个人简历")){
						analysisZLZPExcel(null,xssfsheet,nameConvertCodeService,resumeDao,webUserDao,myCandidatesDao,recruitmentDao);
						break;
					}else{
						//遍历该sheet中的数据，遍历excel中有效的行数
						//忽略前面两行
						for(int rownum = 2; rownum < xssfsheet.getLastRowNum(); rownum++){
							//第roownum行
							XSSFRow row = xssfsheet.getRow(rownum);
							//忽略第一列
							for(int cellnum = 1; cellnum < row.getLastCellNum();cellnum++){
								String content = row.getCell(cellnum).getStringCellValue().replaceAll(" ", "");
								//try {
									if(content.equals("应聘职位")){
										candidatesposation = row.getCell(cellnum+1).getStringCellValue();
										break;
									}else if(content.equals("应聘公司/部门")){
										candidatescompany = row.getCell(cellnum+1).getStringCellValue();
										break;
									}else if(content.equals("更新日期")){
										candidatesdate = row.getCell(cellnum+1).getStringCellValue();
										break;
									}else if(content.equals("姓名")){
										name = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("姓名===="+name);
										break;
									}else if(content.equals("性别")){
										sexname = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("性别===="+sexname);
										break;
									}else if(content.equals("出生日期")){
										birthday = row.getCell(cellnum+1).getStringCellValue().replaceAll(" ", "")+"-01";
										//Date date = DateUtil.parse(birthday);
										System.out.println("出生日期===="+birthday);
										break;
									}else if(content.equals("目前居住地")){
										homeplace = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("目前居住地===="+homeplace);
										break;
									}else if(content.equals("工作年限")){
										workage = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("工作年限===="+workage);
										break;
									}else if(content.equals("电子邮件")){
										email = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("电子邮件===="+email);
										break;
									}else if(content.equals("移动电话")){
										phone = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("移动电话===="+phone);
										break;
									}else if(content.equals("自我评价")){
										if(!state){
											state = true;
											break;
										}else{
											state = false;
											valuation = row.getCell(cellnum+1).getStringCellValue();
										}
										//System.out.println("自我评价===="+valuation);
										if(StringUtils.isEmpty(valuation))
											valuation = "无";
										break;
									}else if(content.equals("希望行业")){
										industry = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("希望行业===="+industry);
										if(StringUtils.isEmpty(industry))
											industry = "无";
										break;
									}else if(content.equals("期望工资")){
										salary = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("期望工资===="+salary);
										break;
									}else if(content.equals("目标职能")){
										occupation = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("目标只能===="+occupation);
										if(StringUtils.isEmpty(occupation))
											occupation = "无";
										break;
									}else if(content.equals("求职状态")){
										situation = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("求职状态===="+situation);
										break;
									}else if(content.equals("工作经验")){
										/*System.out.println("state=="+state);*/
										if(!state){
											/*System.out.println("---------"+row.getCell(cellnum+1).getStringCellValue());*/
											state = true;
											break;
										}else{
											//System.out.println("====工作经验====");
											state = false;
											String projectcontent = row.getCell(cellnum+1).getStringCellValue();
											if(StringUtils.isNotEmpty(projectcontent)){
												/*System.out.println("----projectcontent-----"+row.getCell(cellnum+1).getStringCellValue());*/
												String[] arrproject = projectcontent.split("\\*");
												String[] arrcontents = Arrays.toString(arrproject).substring(1, Arrays.toString(arrproject).length()-1).split("\n");
												String string = "";
												for(String str : arrcontents){
													string += str;
												}
												/*System.out.println("===string==="+string);*/
												String[] tmp = string.split(", , , , , , , , , , , , , , , , , , , , ");
												/*System.out.println("oooooooooo==="+tmp.length);
												for(String str : tmp){
													System.out.println(">>>>>>>>>>>"+str);
												}*/
													if(tmp.length > 0){
														for(int arrynum = 0; arrynum < tmp.length;arrynum++){
															String[] arrdate = tmp[arrynum].replaceAll(" ", "").split("——");
															//开始时间
															String startdate = "";
															//结束时间
															String enddate = "";
															//工作单位
															String workunit = "";
															//职位
															String posation = "";
															//职责描述
															String jobdescription = "";
															//部门
															String department = "";
															if(arrdate.length > 0){
//																System.out.println("sssssssssss=="+arrdate[0]);
																int index = arrdate[0].lastIndexOf("至");
																startdate = arrdate[0].substring(index-7, index)+"-01";
																enddate = arrdate[0].subSequence(index+1, arrdate[0].length())+"-01";
																//System.out.println("开始时间===="+startdate);
																//System.out.println("结束时间===="+enddate);
															}
															if(arrdate.length > 1){
																String[] arrcontent = arrdate[1].split(":");
																if(arrcontent.length > 0){
																	for(int worknum = 0; worknum < arrcontent.length; worknum++){
																		//换行
																		//System.out.println("===worknum====="+arrcontent[worknum]);
																		String[] arrydelempty = arrcontent[worknum].split("\\s");
																		if(arrydelempty.length > 0){
																			for(int worknum_1 = 0;worknum_1 < arrydelempty.length;worknum_1++){
																				//System.out.println("=====arrcontent[worknum]======"+arrydelempty[worknum_1]);
																				String forwork = arrydelempty[worknum_1].replaceAll(" ", "");
																				//System.out.println("====forwork===="+forwork);
																				String[] temp = forwork.split("：");
																				for(int k = 0; k < temp.length; k++){
																					String worktmp = temp[k];
																					if(worktmp.equals("公司")){
																						String[] arrycom = arrcontent[worknum+1].split(" ");
																						if(arrycom.length > 0)
																							workunit = arrycom[0];
																						//System.out.println("工作单位===="+workunit);
																					}else if(worktmp.equals("职位")){
																						//System.out.println("====worktmp职位===="+arrcontent[worknum+1]);
																						posation = arrcontent[worknum+1];
																						//System.out.println("职位===="+posation);
																					}else if(worktmp.equals("工作描述")){
																						jobdescription = temp[k+1];
																						//System.out.println("工作描述===="+jobdescription);
																						//String[] arrydescription = arrcontent[worknum+1].split(" ");
																						//System.out.println("====worktmp工作描述===="+arrcontent[worknum+1]);
																						/*if(arrydescription.length > 0){
																							for(int j = 0; j < arrydescription.length-1; j++)
																								jobdescription += arrydescription[j];
																						}*/
																						//System.out.println("工作描述===="+jobdescription);
																					}else if(worktmp.equals("部门")){
																						department = arrcontent[worknum+1];
																						//System.out.println("部门===="+department);
																					}
																				}
																				
																			}
																		}
																	}
																}
															}
															
															if(StringUtils.isEmpty(workunit))
																workunit = "无";
															if(StringUtils.isEmpty(posation))
																posation = "无";
															if(StringUtils.isEmpty(jobdescription))
																jobdescription = "无";
															if(workunit.length() > 50)
																workunit = workunit.substring(0, 50);
															if(posation.length() > 50)
																posation = posation.substring(0, 5);
															if(jobdescription.length() > 500)
																jobdescription = jobdescription.substring(0, 500);
															
															WorkExperience model = new WorkExperience();
															model.setWorkexperienceguid(UUIDGenerator.randomUUID());
															model.setWorkunit(workunit);
															model.setPosation(posation);
															model.setJobdescription(jobdescription);
															model.setStartdate(DateUtil.parse(startdate));
															model.setEnddate(DateUtil.parse(enddate));
															model.setModtime(new Timestamp(System.currentTimeMillis()));
															worklist.add(model);
														}
													}
													//System.out.println("====worklist===="+worklist.size());
											}
											break;
											}
									}else if(content.equals("项目经验")){
										if(!state){
											state = true;
											break;
										}else{
											//System.out.println("====项目经验====");
											state = false;
											String projectcontent = row.getCell(cellnum+1).getStringCellValue();
											if(StringUtils.isNotEmpty(projectcontent)){
												String[] arrproject = projectcontent.split("\\*");
												String[] arrcontent = Arrays.toString(arrproject).substring(1, Arrays.toString(arrproject).length()-1).split("\\s");
												String string = "";
												for(String str : arrcontent){
													string += str;
												}
												//System.out.println("====str===="+string);
												String[] tmp = string.split(",,,,,,,,,,,,,,,,,,,,");
												//System.out.println("==项目经验个数=="+tmp.length);
												if(tmp.length > 0){
													for(int arrytnum = 0; arrytnum < tmp.length;arrytnum++){
														//	System.out.println("sssssssssss===="+tmp[arrytnum]);
															//开始时间
															String startdate = "";
															//结束时间
															String enddate = "";
															//项目名称
															String itemname = "";
															//职责描述
															String jobdescription = "";
															String[] arrdate = tmp[arrytnum].split("——");
															if(arrdate.length > 0){
																//System.out.println("====arrdate[0]==="+arrdate[0]);
																int index = arrdate[0].lastIndexOf("至");
																startdate = arrdate[0].substring(index-7, index)+"-01";
																enddate = arrdate[0].subSequence(index+1, arrdate[0].length())+"-01";
																/*System.out.println("开始时间===="+startdate);
																System.out.println("结束时间===="+enddate);*/
															}
															if(arrdate.length > 1){
																String tmpcontent = "";
																for(String str : arrdate){
																	//System.out.println("XXXXXXXXXx==="+str);
																	tmpcontent += str;
																}
																//System.out.println("yyyyyyyyyy==="+tmpcontent);
																//System.out.println("arrdate===="+arrdate[1]);
																String[] arrycontent = tmpcontent.split("：");
																/*for(String str : arrycontent)
																System.out.println("oooooooo==="+str);*/
																if(arrycontent.length > 0){
																	String[] arryitemname = arrycontent[0].split("\\s");
																	/*for(String str : arryitemname)
																		System.out.println("......................"+str);*/
																	if(arryitemname.length > 0)
																		itemname = arryitemname[0].substring(0, arryitemname[0].length()-4);
																	//System.out.println("项目名称===="+itemname);
																	for(int projectnum = 0; projectnum < arrycontent.length;projectnum++){
																		//换行
																		String[] arrydelempty = arrycontent[projectnum].split("\\s");
																	/*	for(String str : arrydelempty)
																		System.out.println("===项目内容===="+str);*/
																		if(arrydelempty.length > 0){
																			for(int projectnum_1 = 0; projectnum_1 < arrydelempty.length; projectnum_1++){
																				if(arrydelempty[projectnum_1].length() > 4){

																					String projecttmp = arrydelempty[projectnum_1].replaceAll(" ","").substring(arrydelempty[projectnum_1].length()-4, arrydelempty[projectnum_1].length());
																					//System.out.println("====projecttmp===="+projecttmp);
																					if(projecttmp.equals("项目描述")){
																						jobdescription = arrycontent[projectnum+1];
																						//System.out.println("项目描述===="+jobdescription);
																					}
																				
																				}
																			}
																		}
																	}
																}
															}
																if(StringUtils.isEmpty(itemname))
																	itemname = "无";
																if(StringUtils.isEmpty(jobdescription))
																	jobdescription = "无";
																if(itemname.length() > 50)
																	itemname = itemname.substring(0, 50);
																if(jobdescription.length() > 500)
																	jobdescription = jobdescription.substring(0, 500);
																ProjectExperience model = new ProjectExperience();
																model.setProjectexperienceguid(UUIDGenerator.randomUUID());
																model.setItemname(itemname);
																model.setJobdescription(jobdescription);
																model.setStartdate(DateUtil.parse(startdate));
																model.setEnddate(DateUtil.parse(enddate));
																model.setModtime(new Timestamp(System.currentTimeMillis()));
																projectlist.add(model);
															}
												}
											}
											break;
										}
									}else if(content.equals("教育经历")){
										if(!state){
											state = true;
											break;
										}else{
											//System.out.println("====教育经历====");
											state = false;
											String projectcontent = row.getCell(cellnum+1).getStringCellValue();
											if(StringUtils.isNotEmpty(projectcontent)){
												/*System.out.println("===projectcontent==="+projectcontent);*/
												String[] arrproject = projectcontent.split("\\*");
												//System.out.println("===="+Arrays.toString(arrproject).substring(1, Arrays.toString(arrproject).length()-1));
												String[] arrcontent = Arrays.toString(arrproject).substring(1, Arrays.toString(arrproject).length()-1).split("\n");
												String string = "";
												for(String str : arrcontent){
													string += str;
												}
												String[] tmp = string.split(", , , , , , , , , , , , , , , , , , , , ");
												if(tmp.length > 0){
													for(int arrnum = 0; arrnum < tmp.length; arrnum++){
														String[] arrdate = tmp[arrnum].split("——");
														//开始时间
														String startdate = "";
														//结束时间
														String enddate = "";
														//学校
														String school = "";
														//专业
														String specialty = "";
														//专业描述
														String majordescription = "";
														//学历
														String culturename = "";
														if(arrdate.length > 0){
															int index = arrdate[0].lastIndexOf("至");
															startdate = arrdate[0].substring(index-7, index)+"-01";
															enddate = arrdate[0].subSequence(index+1, arrdate[0].length())+"-01";
															////System.out.println("开始时间===="+startdate);
															//System.out.println("结束时间===="+enddate);
															if(arrdate.length > 1){
																String[] arrycontent = arrdate[1].split("\\s");
																if(arrycontent.length > 0)
																	school = arrycontent[0];
																if(arrycontent.length > 1){
																	specialty = arrycontent[1].substring(0, arrycontent[1].length()-2);
																//	System.out.println("专业===="+specialty);
																	culturename = arrycontent[1].substring(arrycontent[1].length()-2, arrycontent[1].length()).replaceAll(" ", "");
																//	System.out.println("学历===="+culturename);
																}
																if(arrycontent.length > 2){
																	majordescription = arrycontent[2];
																	//System.out.println("专业描述===="+majordescription);
																}
															}
														}
														if(StringUtils.isEmpty(school))
															school = "无";
														if(StringUtils.isEmpty(specialty))
															specialty = "无";
														if(StringUtils.isEmpty(majordescription))
															majordescription = "无";
														if(school.length() > 50)
															school = school.substring(0, 50);
														if(specialty.length() > 50)
															specialty = specialty.substring(0, 50);
														if(majordescription.length() > 500)
															majordescription = majordescription.substring(0, 500);
														// 数据库翻译
														Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturename);
														if (culture == null)
															culture = Constance.VALID_YES;
														EducationExperience model =new EducationExperience();
														model.setEducationexperienceguid(UUIDGenerator.randomUUID());
														model.setSchool(school);
														model.setSpecialty(specialty);
														model.setCulture(culture);
														model.setCulturename(culturename);
														model.setMajordescription(majordescription);
														model.setModtime(new Timestamp(System.currentTimeMillis()));
														model.setStartdate(DateUtil.parse(startdate));
														model.setEnddate(DateUtil.parse(enddate));
														educationlist.add(model);
													}
												}
											}
											break;
										}
									}else if(content.equals("培训经历")){
										if(!state){
											state = true;
											break;
										}else{
											//System.out.println("====培训经历====");
											state = false;
											String projectcontent = row.getCell(cellnum+1).getStringCellValue();
											if(StringUtils.isNotEmpty(projectcontent)){
												/*System.out.println("----projectcontent-----"+row.getCell(cellnum+1).getStringCellValue());*/
												String[] arrproject = projectcontent.split("\\*");
												String[] arrcontents = Arrays.toString(arrproject).substring(1, Arrays.toString(arrproject).length()-1).split("\n");
												String string = "";
												for(String str : arrcontents){
													string += str;
												}
												/*System.out.println("===string==="+string);*/
												String[] tmp = string.split(", , , , , , , , , , , , , , , , , , , , ");
													if(tmp.length > 0){
														for(int arrynum = 0; arrynum < tmp.length;arrynum++){
															String startdate = "";
															String enddate = "";
															//培训机构
															String traininginstitutions = "";
															//证书
															String certificate = "";
															//内容
															String trainingcontent = "";
															String[] arrdate = tmp[arrynum].split("——");
															if(arrdate.length > 0){
																int index = arrdate[0].lastIndexOf("至");
																startdate = arrdate[0].substring(index-7, index)+"-01";
																enddate = arrdate[0].subSequence(index+1, arrdate[0].length())+"-01";
																//System.out.println("开始时间===="+startdate);
																//System.out.println("结束时间===="+enddate);
																if(arrdate.length > 1){
																	String[] contents = arrdate[1].split("\\s");
																	if(contents.length > 0){
																		String[] training = contents[0].split(" ");
																		if(training.length > 0)
																			traininginstitutions = training[0];
																	}
																	if(contents.length > 3)
																		for(int j = 3; j < contents.length;j++){
																			if(j==contents.length-1){
																				trainingcontent += contents[j];	
																			}else
																				trainingcontent += contents[j]+"。";
																		}
																}
															}
															if(StringUtils.isEmpty(certificate))
																certificate = "无";
															if(StringUtils.isEmpty(traininginstitutions))
																traininginstitutions = "无";
															if(StringUtils.isEmpty(trainingcontent))
																trainingcontent = "无";
															if(certificate.length() > 50)
																certificate = certificate.substring(0, 50);
															if(traininginstitutions.length() > 50)
																traininginstitutions = traininginstitutions.substring(0, 50);
															if(trainingcontent.length() > 500)
																trainingcontent = trainingcontent.substring(0, 50);
															
															TrainingExperience model = new TrainingExperience();
															model.setTrainingexperienceguid(UUIDGenerator.randomUUID());
															model.setTrainingcontent(trainingcontent);
															model.setCertificate(certificate);
															model.setTraininginstitutions(traininginstitutions);
															model.setStartdate(DateUtil.parse(startdate));
															model.setEnddate(DateUtil.parse(enddate));
															model.setModtime(new Timestamp(System.currentTimeMillis()));
															traininglist.add(model);
														}
													}
											}
											break;
										}
									}
								/*} catch (Exception e) {
									return "模板错误！";
								}*/
							}
						}
						
						// 外網用戶
						WebUser user = null;
						// 保存到本地
						//System.out.println("===email==="+email);
						user = webUserDao.checkWebUserByEmail(null, email);
						if (user == null) {
							// 保存外网用户
							user = new WebUser(email, name);
							user.setWebuserguid(UUIDGenerator.randomUUID());
							user.setMobile(phone);
							// System.out.println(user.toString());
							webUserDao.insertWebUser(user);
						}else
							user.setMobile(phone);
						
						// 简历信息
						resume = resumeDao.getResumeById(user.getWebuserguid());
						
						// 如果簡歷存在以最新的為準
						boolean isnew = false;
						if (resume == null) {
							isnew = true;
							resume = new Resume();
							resume.setWebuserguid(user.getWebuserguid());
							resume.setCreatetime(new Timestamp(System.currentTimeMillis()));
						}
						resume.setModtime(new Timestamp(System.currentTimeMillis()));
						// 姓名
						if (user.getUsername().length() > 25)
							resume.setName(user.getUsername().substring(0, 25));
						else
							resume.setName(user.getUsername());
						// 电话
						resume.setMobile(user.getMobile());
						// 邮件
						resume.setEmail(user.getEmail());
						// 数据翻译
						Integer sex = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.SEX, sexname);
						if (sex != null) {
							resume.setSexname(sexname);
							resume.setSex(sex);
						} else {
							resume.setSex(Constance.VALID_NO);
						}
						//System.out.println("===birthday===="+birthday);
						resume.setBirthday(DateUtil.parse(birthday));
						//System.out.println("==homeplace===="+homeplace);
						if(homeplace.length() > 50)
							homeplace = homeplace.substring(0, 50);
						resume.setHomeplace(homeplace);
						resume.setMark(Constance.VALID_NO);
						// 数据库翻译
						String cultruename = "无";
						if(educationlist.size() > 0)
							cultruename = educationlist.get(0).getCulturename();
						Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, cultruename);
						if (culture != null) {
							resume.setCulturename(cultruename);
							resume.setCulture(culture);
						} else
							resume.setCulture(Constance.VALID_NO);
						if(situation.length() > 50)
							situation = situation.substring(0, 50);
						if(salary.length() > 50)
							salary = salary.substring(0, 50);
						if(occupation.length() > 100)
							occupation = occupation.substring(0, 100);
						if(valuation.length() > 500)
							valuation = valuation.substring(0, 500);
						if(industry.length() > 50)
							industry = industry.substring(0, 50);
						resume.setValuation(valuation);
						resume.setSalary(salary);
						resume.setSituation(situation);
						resume.setOccupation(occupation);
						resume.setIndustry(industry);
						
						if (isnew)
							resumeDao.insertResume(resume);
						else
							resumeDao.updateResume(resume);
						
						//工作经验
						resumeDao.delWorkExperienceByWebuserId(resume.getWebuserguid());
						for(WorkExperience model : worklist){
							model.setWebuserguid(resume.getWebuserguid());
							resumeDao.insertWorkExperience(model);
						}
						//项目经验
						resumeDao.delProjectExperienceByWebuserId(resume.getWebuserguid());
						for(ProjectExperience model : projectlist){
							model.setWebuserguid(resume.getWebuserguid());
							resumeDao.insertProjectExperience(model);
						}
						//教育经历
						resumeDao.delEducationExperienceByWebuserId(resume.getWebuserguid());
						for(EducationExperience model : educationlist){
							model.setWebuserguid(resume.getWebuserguid());
							resumeDao.insertEducationExperience(model);
						}
						//培训经历
						resumeDao.delTrainingExperienceByWebuserId(resume.getWebuserguid());
						for(TrainingExperience model : traininglist){
							model.setWebuserguid(resume.getWebuserguid());
							resumeDao.insertTrainingExperience(model);
						}
						
						List<MyCandidates> tmpList = myCandidatesDao.getMyCandidatesByWebUserGuidAndState(user.getWebuserguid(), Constance.CandidatesState_Blacklist);
						// 实例化
						MyCandidates model = new MyCandidates();
						model.setMycandidatesguid(UUIDGenerator.randomUUID());
						model.setWebuserguid(user.getWebuserguid());
						model.setCandidatesstate(tmpList.isEmpty() ? Constance.CandidatesState_One : Constance.CandidatesState_Blacklist);
						model.setCandidatestype(Constance.User5);
						model.setProgress(Constance.VALID_NO);
						model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
						model.setReadtype(Constance.VALID_NO);
						// 職位
						List<RecruitPost> list = recruitmentDao.getRecruitPostByRecruitPostName(candidatesposation);
						model.setPostname(candidatesposation);
						if (!list.isEmpty()) {
							RecruitPost recruitPost = list.get(0);
							model.setRecruitpostguid(recruitPost.getRecruitpostguid());
						} else {
							model.setRecruitpostname(candidatesposation);
						}
						//过滤掉一周以内投递过的相同简历
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						model.setTempdate(format.format(System.currentTimeMillis()));
						List<MyCandidates> candidateslist = myCandidatesDao.checkResume(model);
						if(candidateslist != null&&!candidateslist.isEmpty()){
							//System.out.println("===一周内以投递===");
							continue;
						}
						
						model.setCandidatestime(DateUtil.parse(candidatesdate));
						myCandidatesDao.insertMyCandidates(model);
					}
				}
			}
		
		}
	return "完成解析！";
	}
	
	/**
	 * 智联excel解析
	 *  
	 * @param hssfsheet  xls后缀型
	 * @param xssfsheet xlsx后缀型
	 */
	@Transactional
	public static void analysisZLZPExcel(HSSFSheet hssfsheet,XSSFSheet xssfsheet,NameConvertCodeService nameConvertCodeService,ResumeDao resumeDao,WebUserDao webUserDao,MyCandidatesDao myCandidatesDao,RecruitmentDao recruitmentDao){
		String candidatesposation = "";
		String candidatescompany = "";
		String candidatesdate = "";
		String name = "";
		String sexname = "";
		String birthday = "";
		String homeplace = "";
		String workage = "";
		String email = "";
		String phone = "";
		String valuation = "";
		String industry = "";
		String salary = "";
		String occupation = "";
		String situation = "";
		Resume resume = new Resume();
		List<WorkExperience> worklist = new ArrayList<WorkExperience>();
		List<ProjectExperience> projectlist = new ArrayList<ProjectExperience>();
		List<EducationExperience> educationlist = new ArrayList<EducationExperience>();
		List<TrainingExperience> traininglist = new ArrayList<TrainingExperience>();
		if(hssfsheet != null){
			//xls后缀解析
			//遍历该sheet中的数据，遍历excel中有效的行数
			//忽略前面两行
		if(hssfsheet.getLastRowNum() > 0){
			//System.out.println("getLastRowNum========="+hssfsheet.getLastRowNum());
			String personinfo = "";
			if(hssfsheet.getRow(1).getLastCellNum() > 0){
				personinfo =  hssfsheet.getRow(0).getCell(0).getStringCellValue().replaceAll(" ", "");
			}
			//System.out.println("======personinfo========="+personinfo);
			if(personinfo.equals("个人基本信息")){
				for(int rownum = 0; rownum < hssfsheet.getLastRowNum(); rownum++){
					//第roownum行
					HSSFRow row = hssfsheet.getRow(rownum);
					//System.out.println("=====rownum======"+rownum);
					//列
					if(row.getLastCellNum() > 0){
						boolean statend = false;
						for(int cellnum = 0; cellnum < row.getLastCellNum();cellnum++){
							//System.out.println("===cellnum===="+cellnum);
							if(row.getCell(cellnum) != null){
								if(StringUtils.isNotEmpty(row.getCell(cellnum).getStringCellValue())){
									String content = row.getCell(cellnum).getStringCellValue().replaceAll(" ", "");
									//System.out.println("===content===="+content);
									if(content.equals("在校学习情况") || content.equals("证书")){
										statend = true;
										break;
									}else if(content.equals("姓名")){
										name = row.getCell(cellnum+1).getStringCellValue();
									//	System.out.println("姓名===="+name);
										break;
									}else if(content.equals("性别")){
										sexname = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("性别===="+sexname);
										break;
									}else if(content.equals("出生年月")){
										String birthday_1 = row.getCell(cellnum+1).getStringCellValue();
										int year = birthday_1.lastIndexOf("年");
										int month = birthday_1.lastIndexOf("月");
										birthday = birthday_1.substring(0, year)+"-"+birthday_1.substring(year+1, month)+"-01";
										//System.out.println("出生年月===="+birthday);
										break;
									}else if(content.equals("现居住地")){
										homeplace = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("现居住地===="+homeplace);
										break;
									}else if(content.equals("工作经验")){
										if(row.getCell(cellnum+1) !=null ){
											if(StringUtils.isNotEmpty(row.getCell(cellnum+1).getStringCellValue())){
												if(row.getCell(cellnum+1).getStringCellValue().endsWith("工作经验")){
													workage = row.getCell(cellnum+1).getStringCellValue().substring(0, row.getCell(cellnum+1).getStringCellValue().length()-4)+"以上";
												}else if(row.getCell(cellnum+1).getStringCellValue().equals("应届毕业生"))
													workage = "应届毕业生";
												else if(row.getCell(cellnum+1).getStringCellValue().equals("在读学生"))
													workage = "在读学生";
											}
										}
										//System.out.println("工作经验===="+workage);
										break;
									}else if(content.equals("联系电话")){
										phone = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("联系电话===="+phone);
										break;
									}else if(content.equals("E-mail")){
										email = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("E-mail===="+email);
										break;
									}else if(content.equals("期望从事职业：")){
										occupation = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("期望从事职业===="+occupation);
										break;
									}else if(content.equals("期望从事行业：")){
										industry = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("期望从事行业===="+industry);
										break;
									}else if(content.equals("期望月薪：")){
										salary = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("期望月薪===="+salary);
										break;
									}else if(content.equals("目前状况：")){
										situation = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("目前状况===="+situation);
										break;
									}else if(content.equals("自我评价")){
										HSSFRow hssfrow = hssfsheet.getRow(rownum+1);
										valuation = hssfrow.getCell(cellnum+1).getStringCellValue();
										//System.out.println("自我评价===="+situation);
										rownum = rownum+1;
										break;
									}else if(content.equals("工作经历")){
										//System.out.println("======工作经历======");
										int nextnum = 0;
										int worknum = rownum+1;
										boolean state = false;//工作经历条数是否是最后一行判断
//										rownum = rownum+1;
//										String cont = hssfsheet.getRow(rownum).getCell(cellnum).getStringCellValue();
//										System.out.println("=====cont======"+cont);
											//try {
												//取到所有工作经历的行数
												for(int k = worknum; k < hssfsheet.getLastRowNum(); k++){
													//第rownum行
													HSSFRow rownext = hssfsheet.getRow(k);
													//System.out.println("****************************-=="+k);
													for(int g = 0; g < rownext.getLastCellNum();g++){
														HSSFCell hssfcell = rownext.getCell(g);
														//System.out.println("--------------------"+hssfcell);
														if(hssfcell != null){
															String contents = rownext.getCell(g).getStringCellValue().replaceAll(" ", "");
															if(contents.equals("项目经验")){
																nextnum = k;
																rownum = k-1;
																//System.out.println("======kkkkkkkkkk======="+k);
																state = true;
															}
														}
														break;
													}
													if(state)
														break;
												}
												if(worknum < nextnum){
													for(int i = worknum; i < nextnum; i+=4){
														//开始时间
														String startdate = "";
														//结束时间
														String enddate = "";
														//工作单位
														String workunit = "";
														//职位
														String posation = "";
														//职责描述
														String jobdescription = "";
														//部门
														String department = "";
														//设死一个工作经历占4行
														for(int h = 0;h < 4; h++){
															row = hssfsheet.getRow(i+h);
															//根据h循环分行拿数据
															if(h==0){
																for(int cellnum_1 = 0; cellnum_1 < row.getLastCellNum();cellnum_1++){
																	String nextcont = row.getCell(cellnum_1+1).getStringCellValue().replaceAll(" ", "");
																	//System.out.println("===nextcont==="+nextcont);
																	if(StringUtils.isNotEmpty(nextcont)){
																		String[] arrdate = row.getCell(cellnum+1).getStringCellValue().split(" - ");
																		if(arrdate.length > 0)
																			startdate = arrdate[0];
																		/*System.out.println("开始时间====="+startdate);
																		for(String str : arrdate)
																			System.out.println("=====str====="+str);*/
																		if(arrdate.length > 1){
																			String[] arrwork = arrdate[1].split("\\s");
																			if(arrwork.length > 0)
																				enddate = arrwork[0];
																			for(int g = 1; g < arrwork.length;g++){
																				if(StringUtils.isNotEmpty(arrwork[g])){
																					workunit += arrwork[g]+" ";
																				}
																			}
//																			System.out.println("结束时间====="+enddate);
//																			System.out.println("工作单位====="+workunit);
																		}
																		
																	}/*else
																		rownum = i;*/
																	break;
																}
															}else if(h==1){
																for(int cellnum_1 = 0; cellnum_1 < row.getLastCellNum();cellnum_1++){
																	String[] arrposation = row.getCell(cellnum+1).getStringCellValue().split(" | ");
																	List<String> strlist = new ArrayList<String>();
																	for(int index = 0; index < arrposation.length; index++){
																		if(StringUtils.isNotEmpty(arrposation[index]) && !arrposation[index].trim().equals("|"))
																			strlist.add(arrposation[index]);
																	}
																	if(strlist.size() > 0){
																		String tmpsalay = strlist.get(strlist.size()-1);
																		int idx = tmpsalay.lastIndexOf("元/月");
																		if(idx >= 0){
																			if(strlist.size() > 1)
																				posation = strlist.get(strlist.size()-2);
																			if(strlist.size() > 2)
																				department = strlist.get(strlist.size()-3);
																		}else{
																			posation = tmpsalay;
																			if(strlist.size() > 1)
																				department = strlist.get(strlist.size()-1);
																		}
//																		System.out.println("职位========"+posation);
//																		System.out.println("部门========"+department);
																	}
																	break;
																}
															}else if(h==3){
																String strposation = row.getCell(cellnum+1).getStringCellValue();
																jobdescription = strposation;
																int num = jobdescription.indexOf("工作职责 :");
																if(num >= 0)
																	jobdescription = jobdescription.substring(6, jobdescription.length());
//																System.out.println("职责描述======="+jobdescription);
															}
														
															if(StringUtils.isEmpty(workunit))
																workunit = "无";
															if(StringUtils.isEmpty(posation))
																posation = "无";
															if(StringUtils.isEmpty(jobdescription))
																jobdescription = "无";
															if(workunit.length() > 50)
																workunit = workunit.substring(0, 50);
															if(posation.length() > 50)
																posation = posation.substring(0, 5);
															if(jobdescription.length() > 500)
																jobdescription = jobdescription.substring(0, 500);
															
														}
														
														WorkExperience model = new WorkExperience();
														model.setWorkexperienceguid(UUIDGenerator.randomUUID());
														model.setWorkunit(workunit);
														model.setPosation(posation);
														model.setJobdescription(jobdescription);
														Date sqlstartdate = DateUtil.parse(startdate);
														if(sqlstartdate == null)
															sqlstartdate =  new Date(System.currentTimeMillis());
														model.setStartdate(sqlstartdate);
														Date sqlenddate = DateUtil.parse(enddate);
														if(sqlenddate == null)
															sqlenddate =  new Date(System.currentTimeMillis());
														model.setEnddate(sqlenddate);
														model.setModtime(new Timestamp(System.currentTimeMillis()));
														worklist.add(model);
														//System.out.println(model.toString());
													}
													break;
												}
												break;
											/*} catch (Exception e) {
												System.out.println("========工作经历模板错误！=========");
												continue;
											}*/
									}else if(content.equals("项目经验")){
										//System.out.println("======项目经验======");
										int nextnum = 0;
										int projectnum = rownum+1;
										boolean state = false;//项目经验条数是否是最后一行判断
										//取到所有项目经验的行数
										for(int k = projectnum; k < hssfsheet.getLastRowNum(); k++){
											//第rownum行
											HSSFRow rownext = hssfsheet.getRow(k);
											//System.out.println("****************************-=="+k);
											for(int g = 0; g < rownext.getLastCellNum();g++){

												HSSFCell hssfcell = rownext.getCell(g);
												//System.out.println("--------------------"+hssfcell);
												if(hssfcell != null){
													String contents = rownext.getCell(g).getStringCellValue().replaceAll(" ", "");
													if(contents.equals("教育经历")){
														nextnum = k;
														rownum = k-1;
														//System.out.println("======k_1k_1k_1k_1k_1k_1k_1k_1k_1======="+k);
														state = true;
													}
												}
												break;
											}
											if(state)
												break;
										}
										if(projectnum < nextnum){
											//开始时间
											String startdate = "";
											//结束时间
											String enddate = "";
											//项目名称
											String itemname = "";
											//职责描述
											String jobdescription = "";
											for(int i = projectnum; i < nextnum;i++){
												row = hssfsheet.getRow(i);
												if(row.getLastCellNum() > 0){
													for(int projectcellnum = 0;projectcellnum < row.getLastCellNum();projectcellnum++){
														String[] arrjobdescription = row.getCell(projectcellnum).getStringCellValue().split("：");
														//System.out.println("=========arrjobdescription.size==========="+arrjobdescription[0]);
														if(arrjobdescription.length > 0){
															for(String str : arrjobdescription){
																if(str.replaceAll(" ", "").equals("责任描述")){
																	jobdescription = row.getCell(projectcellnum+1).getStringCellValue();
																	if(hssfsheet.getRow(i+1)!=null && hssfsheet.getRow(i+1).getRowNum() < nextnum){
																		String nextcont = hssfsheet.getRow(i+1).getCell(projectcellnum).getStringCellValue().replaceAll(" ", "");
																	//	System.out.println("nextcont=============="+nextcont);
																		if(StringUtils.isNotEmpty(nextcont) && !nextcont.equals("教育经历")){
																			if(nextcont.replaceAll(" ", "").equals("项目描述：")){
																				jobdescription +=  hssfsheet.getRow(i+1).getCell(projectcellnum+1).getStringCellValue();
																				//System.out.println("项目描述======"+jobdescription);
																				if(StringUtils.isEmpty(itemname))
																					itemname = "无";
																				if(StringUtils.isEmpty(jobdescription))
																					jobdescription = "无";
																				if(itemname.length() > 50)
																					itemname = itemname.substring(0, 50);
																				if(jobdescription.length() > 500)
																					jobdescription = jobdescription.substring(0, 500);
																				ProjectExperience model = new ProjectExperience();
																				model.setProjectexperienceguid(UUIDGenerator.randomUUID());
																				model.setItemname(itemname);
																				model.setJobdescription(jobdescription);
																				Date sqlstartdate = DateUtil.parse(startdate);
																				if(sqlstartdate == null)
																					sqlstartdate =  new Date(System.currentTimeMillis());
																				model.setStartdate(sqlstartdate);
																				Date sqlenddate = DateUtil.parse(enddate);
																				if(sqlenddate == null)
																					sqlenddate =  new Date(System.currentTimeMillis());
																				model.setEnddate(sqlenddate);
																				model.setModtime(new Timestamp(System.currentTimeMillis()));
																				projectlist.add(model);
																				//System.out.println(model.toString());
																			}
																		}
																	}
																}
																break;
															}
														}
														break;
													}
												}
											}
										}
										break;
									}else if(content.equals("教育经历")){
										//System.out.println("======教育经历======");
										int nextnum = 0;
										int educationnum = rownum+1;
										boolean state = false;//教育经历条数是否是最后一行判断
										//取到所有教育经历的行数
										for(int k = educationnum; k < hssfsheet.getLastRowNum(); k++){
											//第rownum行
											HSSFRow rownext = hssfsheet.getRow(k);
											//System.out.println("****************************-=="+k);
											for(int g = 0; g < rownext.getLastCellNum();g++){

												HSSFCell hssfcell = rownext.getCell(g);
												//System.out.println("--------------------"+hssfcell);
												if(hssfcell != null){
													String contents = rownext.getCell(g).getStringCellValue().replaceAll(" ", "");
													if(contents.equals("培训经历")){
														nextnum = k;
														rownum = k-1;
														//System.out.println("======k_2k_2k_2k_2k_2k_2k_2k_2k_2======="+k);
														state = true;
													}
												}
												break;
											}
											if(state)
												break;
										}
										if(educationnum < nextnum){
											for(int i = educationnum; i < nextnum;i++){
												//开始时间
												String startdate = "";
												//结束时间
												String enddate = "";
												//学校
												String school = "";
												//专业
												String specialty = "";
												//专业描述
												String majordescription = "";
												//学历
												String culturename = "";
												row = hssfsheet.getRow(i);
												List<String> strlist = new ArrayList<String>();
												for(int educationcellnum = 0; educationcellnum < row.getLastCellNum();educationcellnum++){
													String[] arreducation = row.getCell(educationcellnum+1).getStringCellValue().split(" ");
													for(String str : arreducation){
														//System.out.println("str==========="+str);
														if(StringUtils.isNotEmpty(str))
															strlist.add(str);
													}
													break;
												}
												if(!strlist.isEmpty()){
													if(strlist.size() > 0)
														startdate = strlist.get(0);
													if(strlist.size() > 2)
														enddate = strlist.get(2);
													if(strlist.size() > 3)
														school = strlist.get(3);
													if(strlist.size() > 6){
														specialty = strlist.get(5);
														culturename = strlist.get(6);
													}
													if(strlist.size() <= 6){
														specialty = strlist.get(4);
														culturename = strlist.get(5);
													}
													/*System.out.println("开始时间========"+startdate);
													System.out.println("结束时间========"+enddate);
													System.out.println("学校========"+school);
													System.out.println("专业========"+specialty);
													System.out.println("学历========"+culturename);*/
												}
												
												if(StringUtils.isEmpty(school))
													school = "无";
												if(StringUtils.isEmpty(specialty))
													specialty = "无";
												if(StringUtils.isEmpty(majordescription))
													majordescription = "无";
												if(school.length() > 50)
													school = school.substring(0, 50);
												if(specialty.length() > 50)
													specialty = specialty.substring(0, 50);
												if(majordescription.length() > 500)
													majordescription = majordescription.substring(0, 500);
												// 数据库翻译
												Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturename);
												if (culture == null)
													culture = Constance.VALID_YES;
												EducationExperience model =new EducationExperience();
												model.setEducationexperienceguid(UUIDGenerator.randomUUID());
												model.setSchool(school);
												model.setSpecialty(specialty);
												model.setCulture(culture);
												model.setCulturename(culturename);
												model.setMajordescription(majordescription);
												model.setModtime(new Timestamp(System.currentTimeMillis()));
												Date sqlstartdate = DateUtil.parse(startdate);
												if(sqlstartdate == null)
													sqlstartdate =  new Date(System.currentTimeMillis());
												model.setStartdate(sqlstartdate);
												Date sqlenddate = DateUtil.parse(enddate);
												if(sqlenddate == null)
													sqlenddate =  new Date(System.currentTimeMillis());
												model.setEnddate(sqlenddate);
												educationlist.add(model);
												//System.out.println(model.toString());
											}
										}
										break;
									}else if(content.equals("培训经历")){
										//System.out.println("======培训经历======");
										statend = true;
										int nextnum = 0;
										int trainingnum = rownum+1;
										boolean state = false;//培训经历条数是否是最后一行判断
										//取到所有培训经历的行数
										for(int k = trainingnum; k < hssfsheet.getLastRowNum(); k++){
											//第rownum行
											HSSFRow rownext = hssfsheet.getRow(k);
											//System.out.println("****************************-=="+k);
											for(int g = 0; g < rownext.getLastCellNum();g++){

												HSSFCell hssfcell = rownext.getCell(g);
												//System.out.println("--------------------"+hssfcell);
												if(hssfcell != null){
													String contents = rownext.getCell(g).getStringCellValue().replaceAll(" ", "");
													if(contents.equals("证书") || contents.equals("在校学习情况")){
														nextnum = k;
														rownum = k-1;
														//System.out.println("======k_2k_2k_2k_2k_2k_2k_2k_2k_2======="+rownum);
														state = true;
													}
												}
												break;
											}
											if(state)
												break;
										}
										
										if(trainingnum < nextnum){
											for(int i = trainingnum; i < nextnum;i++){
												//boolean statetmp = false;
												String startdate = "";
												String enddate = "";
												//培训机构
												String traininginstitutions = "";
												//证书
												String certificate = "";
												//内容
												String trainingcontent = "";
												row = hssfsheet.getRow(i);
												if(row.getLastCellNum() > 0){
													for(int trainingcellnum = 0; trainingcellnum < row.getLastCellNum();trainingcellnum++){
														boolean statetraining = false;
														if(row.getCell(trainingcellnum) != null){
															if(StringUtils.isNotEmpty(row.getCell(trainingcellnum).getStringCellValue())){
																String traintitle = row.getCell(trainingcellnum).getStringCellValue().replaceAll(" ", "");
																//System.out.println(i+"__------------traintitle====="+traintitle);
																if(traintitle.equals("培训机构：")){
																	if(row.getCell(trainingcellnum+1) != null){
																		if(StringUtils.isNotEmpty(row.getCell(trainingcellnum+1).getStringCellValue())){
																			traininginstitutions = row.getCell(trainingcellnum+1).getStringCellValue();
//																			System.out.println("培训机构========"+traininginstitutions);
																		}
																	}
																	//下一行的值，直到再次等于培训机构，则终止
																	for(int tmpnum = i+1; tmpnum < nextnum;tmpnum++){
																		HSSFRow trainrow = hssfsheet.getRow(tmpnum);
																		//System.out.println("trainrow========="+trainrow.getCell(0).getStringCellValue());
																		for(int tmpcell = 0; tmpcell < trainrow.getLastCellNum();tmpcell++){
																			String tmptraintitle = trainrow.getCell(tmpcell).getStringCellValue();
																			if(tmptraintitle.equals("所获证书：")){
																				if(trainrow.getCell(tmpcell+1) != null){
																					if(StringUtils.isNotEmpty(trainrow.getCell(tmpcell+1).getStringCellValue())){
																						certificate = trainrow.getCell(trainingcellnum+1).getStringCellValue();
//																						System.out.println("所获证书========"+certificate);
																					}
																				}
																			}else if(tmptraintitle.equals("培训描述：")){
																				//System.out.println("-----------------------------"+trainrow.getCell(tmpcell+1).getStringCellValue());
																				if(trainrow.getCell(tmpcell+1) != null){
																					if(StringUtils.isNotEmpty(trainrow.getCell(tmpcell+1).getStringCellValue())){
																						trainingcontent = trainrow.getCell(tmpcell+1).getStringCellValue();
//																						System.out.println("培训描述========"+trainingcontent);
																					}
																				}
																			}else if(tmptraintitle.equals("培训机构：")){
																				statetraining = true;
																				i = tmpnum-1;
																				//statetmp = true;
																				//System.out.println("...........................................i........."+i);
																			}
																				break;
																		}
																		if(statetraining)
																			break;
																	}
																	if(StringUtils.isEmpty(certificate))
																		certificate = "无";
																	if(StringUtils.isEmpty(traininginstitutions))
																		traininginstitutions = "无";
																	if(StringUtils.isEmpty(trainingcontent))
																		trainingcontent = "无";
																	if(certificate.length() > 50)
																		certificate = certificate.substring(0, 50);
																	if(traininginstitutions.length() > 50)
																		traininginstitutions = traininginstitutions.substring(0, 50);
																	if(trainingcontent.length() > 500)
																		trainingcontent = trainingcontent.substring(0, 50);
																	
																	TrainingExperience model = new TrainingExperience();
																	model.setTrainingexperienceguid(UUIDGenerator.randomUUID());
																	model.setTrainingcontent(trainingcontent);
																	model.setCertificate(certificate);
																	model.setTraininginstitutions(traininginstitutions);
																	Date sqlstartdate = DateUtil.parse(startdate);
																	if(sqlstartdate == null)
																		sqlstartdate =  new Date(System.currentTimeMillis());
																	model.setStartdate(sqlstartdate);
																	Date sqledndate = DateUtil.parse(enddate);
																	if(sqledndate == null)
																		sqledndate =  new Date(System.currentTimeMillis());
																	model.setEnddate(sqledndate);
																	model.setModtime(new Timestamp(System.currentTimeMillis()));
																	traininglist.add(model);
//																	System.out.println(model.toString());
																}
															}
														}
														break;
													}
												}
											/*	if(statetmp)
													break;*/
											}
										}
										break;
									}
										break;
								
								}
							}
							if(statend)
								break;
						}
					}
					
				/*	continue;*/
				}
			}
		}
		}else{
			//xlsx后缀解析

			//xls后缀解析
			//遍历该sheet中的数据，遍历excel中有效的行数
			//忽略前面两行
		if(xssfsheet.getLastRowNum() > 0){
			//System.out.println("getLastRowNum========="+xssfsheet.getLastRowNum());
			String personinfo = "";
			if(xssfsheet.getRow(1).getLastCellNum() > 0){
				personinfo =  xssfsheet.getRow(0).getCell(0).getStringCellValue().replaceAll(" ", "");
			}
			//System.out.println("======personinfo========="+personinfo);
			if(personinfo.equals("个人基本信息")){
				for(int rownum = 0; rownum < xssfsheet.getLastRowNum(); rownum++){
					//第roownum行
					XSSFRow row = xssfsheet.getRow(rownum);
					//System.out.println("=====rownum======"+rownum);
					//列
					if(row.getLastCellNum() > 0){
						boolean statend = false;
						for(int cellnum = 0; cellnum < row.getLastCellNum();cellnum++){
							//System.out.println("===cellnum===="+cellnum);
							if(row.getCell(cellnum) != null){
								if(StringUtils.isNotEmpty(row.getCell(cellnum).getStringCellValue())){
									String content = row.getCell(cellnum).getStringCellValue().replaceAll(" ", "");
									//System.out.println("===content===="+content);
									if(content.equals("在校学习情况") || content.equals("证书")){
										statend = true;
										break;
									}else if(content.equals("姓名")){
										name = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("姓名===="+name);
										break;
									}else if(content.equals("性别")){
										sexname = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("性别===="+sexname);
										break;
									}else if(content.equals("出生年月")){
										String birthday_1 = row.getCell(cellnum+1).getStringCellValue();
										int year = birthday_1.lastIndexOf("年");
										int month = birthday_1.lastIndexOf("月");
										birthday = birthday_1.substring(0, year)+"-"+birthday_1.substring(year+1, month)+"-01";
										//System.out.println("出生年月===="+birthday);
										break;
									}else if(content.equals("现居住地")){
										homeplace = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("现居住地===="+homeplace);
										break;
									}else if(content.equals("工作经验")){
										if(row.getCell(cellnum+1) !=null ){
											if(StringUtils.isNotEmpty(row.getCell(cellnum+1).getStringCellValue())){
												if(row.getCell(cellnum+1).getStringCellValue().endsWith("工作经验")){
													workage = row.getCell(cellnum+1).getStringCellValue().substring(0, row.getCell(cellnum+1).getStringCellValue().length()-4)+"以上";
												}else if(row.getCell(cellnum+1).getStringCellValue().equals("应届毕业生"))
													workage = "应届毕业生";
												else if(row.getCell(cellnum+1).getStringCellValue().equals("在读学生"))
													workage = "在读学生";
											}
										}
										//System.out.println("工作经验===="+workage);
										break;
									}else if(content.equals("联系电话")){
										phone = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("联系电话===="+phone);
										break;
									}else if(content.equals("E-mail")){
										email = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("E-mail===="+email);
										break;
									}else if(content.equals("期望从事职业：")){
										occupation = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("期望从事职业===="+occupation);
										break;
									}else if(content.equals("期望从事行业：")){
										industry = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("期望从事行业===="+industry);
										break;
									}else if(content.equals("期望月薪：")){
										salary = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("期望月薪===="+salary);
										break;
									}else if(content.equals("目前状况：")){
										situation = row.getCell(cellnum+1).getStringCellValue();
										//System.out.println("目前状况===="+situation);
										break;
									}else if(content.equals("自我评价")){
										HSSFRow hssfrow = hssfsheet.getRow(rownum+1);
										valuation = hssfrow.getCell(cellnum+1).getStringCellValue();
										//System.out.println("自我评价===="+situation);
										rownum = rownum+1;
										break;
									}else if(content.equals("工作经历")){
										//System.out.println("======工作经历======");
										int nextnum = 0;
										int worknum = rownum+1;
										boolean state = false;//工作经历条数是否是最后一行判断
//										rownum = rownum+1;
//										String cont = hssfsheet.getRow(rownum).getCell(cellnum).getStringCellValue();
//										System.out.println("=====cont======"+cont);
											//try {
												//取到所有工作经历的行数
												for(int k = worknum; k < hssfsheet.getLastRowNum(); k++){
													//第rownum行
													HSSFRow rownext = hssfsheet.getRow(k);
													//System.out.println("****************************-=="+k);
													for(int g = 0; g < rownext.getLastCellNum();g++){
														HSSFCell hssfcell = rownext.getCell(g);
														//System.out.println("--------------------"+hssfcell);
														if(hssfcell != null){
															String contents = rownext.getCell(g).getStringCellValue().replaceAll(" ", "");
															if(contents.equals("项目经验")){
																nextnum = k;
																rownum = k-1;
																//System.out.println("======kkkkkkkkkk======="+k);
																state = true;
															}
														}
														break;
													}
													if(state)
														break;
												}
												if(worknum < nextnum){
													for(int i = worknum; i < nextnum; i+=4){
														//开始时间
														String startdate = "";
														//结束时间
														String enddate = "";
														//工作单位
														String workunit = "";
														//职位
														String posation = "";
														//职责描述
														String jobdescription = "";
														//部门
														String department = "";
														//设死一个工作经历占4行
														for(int h = 0;h < 4; h++){
															row = xssfsheet.getRow(i+h);
															//根据h循环分行拿数据
															if(h==0){
																for(int cellnum_1 = 0; cellnum_1 < row.getLastCellNum();cellnum_1++){
																	String nextcont = row.getCell(cellnum_1+1).getStringCellValue().replaceAll(" ", "");
																	//System.out.println("===nextcont==="+nextcont);
																	if(StringUtils.isNotEmpty(nextcont)){
																		String[] arrdate = row.getCell(cellnum+1).getStringCellValue().split(" - ");
																		if(arrdate.length > 0)
																			startdate = arrdate[0];
																		/*System.out.println("开始时间====="+startdate);
																		for(String str : arrdate)
																			System.out.println("=====str====="+str);*/
																		if(arrdate.length > 1){
																			String[] arrwork = arrdate[1].split("\\s");
																			if(arrwork.length > 0)
																				enddate = arrwork[0];
																			for(int g = 1; g < arrwork.length;g++){
																				if(StringUtils.isNotEmpty(arrwork[g])){
																					workunit += arrwork[g]+" ";
																				}
																			}
//																			System.out.println("结束时间====="+enddate);
//																			System.out.println("工作单位====="+workunit);
																		}
																		
																	}/*else
																		rownum = i;*/
																	break;
																}
															}else if(h==1){
																for(int cellnum_1 = 0; cellnum_1 < row.getLastCellNum();cellnum_1++){
																	String[] arrposation = row.getCell(cellnum+1).getStringCellValue().split(" | ");
																	List<String> strlist = new ArrayList<String>();
																	for(int index = 0; index < arrposation.length; index++){
																		if(StringUtils.isNotEmpty(arrposation[index]) && !arrposation[index].trim().equals("|"))
																			strlist.add(arrposation[index]);
																	}
																	if(strlist.size() > 0){
																		String tmpsalay = strlist.get(strlist.size()-1);
																		int idx = tmpsalay.lastIndexOf("元/月");
																		if(idx >= 0){
																			if(strlist.size() > 1)
																				posation = strlist.get(strlist.size()-2);
																			if(strlist.size() > 2)
																				department = strlist.get(strlist.size()-3);
																		}else{
																			posation = tmpsalay;
																			if(strlist.size() > 1)
																				department = strlist.get(strlist.size()-1);
																		}
//																		System.out.println("职位========"+posation);
//																		System.out.println("部门========"+department);
																	}
																	break;
																}
															}else if(h==3){
																String strposation = row.getCell(cellnum+1).getStringCellValue();
																jobdescription = strposation;
																int num = jobdescription.indexOf("工作职责 :");
																if(num >= 0)
																	jobdescription = jobdescription.substring(6, jobdescription.length());
//																System.out.println("职责描述======="+jobdescription);
															}
														
															if(StringUtils.isEmpty(workunit))
																workunit = "无";
															if(StringUtils.isEmpty(posation))
																posation = "无";
															if(StringUtils.isEmpty(jobdescription))
																jobdescription = "无";
															if(workunit.length() > 50)
																workunit = workunit.substring(0, 50);
															if(posation.length() > 50)
																posation = posation.substring(0, 5);
															if(jobdescription.length() > 500)
																jobdescription = jobdescription.substring(0, 500);
															
														}
														
														WorkExperience model = new WorkExperience();
														model.setWorkexperienceguid(UUIDGenerator.randomUUID());
														model.setWorkunit(workunit);
														model.setPosation(posation);
														model.setJobdescription(jobdescription);
														Date sqlstartdate = DateUtil.parse(startdate);
														if(sqlstartdate == null)
															sqlstartdate =  new Date(System.currentTimeMillis());
														model.setStartdate(sqlstartdate);
														Date sqlenddate = DateUtil.parse(enddate);
														if(sqlenddate == null)
															sqlenddate =  new Date(System.currentTimeMillis());
														model.setEnddate(sqlenddate);
														model.setModtime(new Timestamp(System.currentTimeMillis()));
														worklist.add(model);
														//System.out.println(model.toString());
													}
													break;
												}
												break;
											/*} catch (Exception e) {
												System.out.println("========工作经历模板错误！=========");
												continue;
											}*/
									}else if(content.equals("项目经验")){
										//System.out.println("======项目经验======");
										int nextnum = 0;
										int projectnum = rownum+1;
										boolean state = false;//项目经验条数是否是最后一行判断
										//取到所有项目经验的行数
										for(int k = projectnum; k < hssfsheet.getLastRowNum(); k++){
											//第rownum行
											HSSFRow rownext = hssfsheet.getRow(k);
											//System.out.println("****************************-=="+k);
											for(int g = 0; g < rownext.getLastCellNum();g++){

												HSSFCell hssfcell = rownext.getCell(g);
												//System.out.println("--------------------"+hssfcell);
												if(hssfcell != null){
													String contents = rownext.getCell(g).getStringCellValue().replaceAll(" ", "");
													if(contents.equals("教育经历")){
														nextnum = k;
														rownum = k-1;
														//System.out.println("======k_1k_1k_1k_1k_1k_1k_1k_1k_1======="+k);
														state = true;
													}
												}
												break;
											}
											if(state)
												break;
										}
										if(projectnum < nextnum){
											//开始时间
											String startdate = "";
											//结束时间
											String enddate = "";
											//项目名称
											String itemname = "";
											//职责描述
											String jobdescription = "";
											for(int i = projectnum; i < nextnum;i++){
												row = xssfsheet.getRow(i);
												if(row.getLastCellNum() > 0){
													for(int projectcellnum = 0;projectcellnum < row.getLastCellNum();projectcellnum++){
														String[] arrjobdescription = row.getCell(projectcellnum).getStringCellValue().split("：");
														//System.out.println("=========arrjobdescription.size==========="+arrjobdescription[0]);
														if(arrjobdescription.length > 0){
															for(String str : arrjobdescription){
																if(str.replaceAll(" ", "").equals("责任描述")){
																	jobdescription = row.getCell(projectcellnum+1).getStringCellValue();
																	if(xssfsheet.getRow(i+1)!=null && xssfsheet.getRow(i+1).getRowNum() < nextnum){
																		String nextcont = xssfsheet.getRow(i+1).getCell(projectcellnum).getStringCellValue().replaceAll(" ", "");
																	//	System.out.println("nextcont=============="+nextcont);
																		if(StringUtils.isNotEmpty(nextcont) && !nextcont.equals("教育经历")){
																			if(nextcont.replaceAll(" ", "").equals("项目描述：")){
																				jobdescription +=  xssfsheet.getRow(i+1).getCell(projectcellnum+1).getStringCellValue();
																				//System.out.println("项目描述======"+jobdescription);
																				if(StringUtils.isEmpty(itemname))
																					itemname = "无";
																				if(StringUtils.isEmpty(jobdescription))
																					jobdescription = "无";
																				if(itemname.length() > 50)
																					itemname = itemname.substring(0, 50);
																				if(jobdescription.length() > 500)
																					jobdescription = jobdescription.substring(0, 500);
																				ProjectExperience model = new ProjectExperience();
																				model.setProjectexperienceguid(UUIDGenerator.randomUUID());
																				model.setItemname(itemname);
																				model.setJobdescription(jobdescription);
																				Date sqlstartdate = DateUtil.parse(startdate);
																				if(sqlstartdate == null)
																					sqlstartdate =  new Date(System.currentTimeMillis());
																				model.setStartdate(sqlstartdate);
																				Date sqlenddate = DateUtil.parse(enddate);
																				if(sqlenddate == null)
																					sqlenddate =  new Date(System.currentTimeMillis());
																				model.setEnddate(sqlenddate);
																				model.setModtime(new Timestamp(System.currentTimeMillis()));
																				projectlist.add(model);
																				//System.out.println(model.toString());
																			}
																		}
																	}
																}
																break;
															}
														}
														break;
													}
												}
											}
										}
										break;
									}else if(content.equals("教育经历")){
									//	System.out.println("======教育经历======");
										int nextnum = 0;
										int educationnum = rownum+1;
										boolean state = false;//教育经历条数是否是最后一行判断
										//取到所有教育经历的行数
										for(int k = educationnum; k < xssfsheet.getLastRowNum(); k++){
											//第rownum行
											XSSFRow rownext = xssfsheet.getRow(k);
											//System.out.println("****************************-=="+k);
											for(int g = 0; g < rownext.getLastCellNum();g++){

												XSSFCell hssfcell = rownext.getCell(g);
												//System.out.println("--------------------"+hssfcell);
												if(hssfcell != null){
													String contents = rownext.getCell(g).getStringCellValue().replaceAll(" ", "");
													if(contents.equals("培训经历")){
														nextnum = k;
														rownum = k-1;
														//System.out.println("======k_2k_2k_2k_2k_2k_2k_2k_2k_2======="+k);
														state = true;
													}
												}
												break;
											}
											if(state)
												break;
										}
										if(educationnum < nextnum){
											for(int i = educationnum; i < nextnum;i++){
												//开始时间
												String startdate = "";
												//结束时间
												String enddate = "";
												//学校
												String school = "";
												//专业
												String specialty = "";
												//专业描述
												String majordescription = "";
												//学历
												String culturename = "";
												row = xssfsheet.getRow(i);
												List<String> strlist = new ArrayList<String>();
												for(int educationcellnum = 0; educationcellnum < row.getLastCellNum();educationcellnum++){
													String[] arreducation = row.getCell(educationcellnum+1).getStringCellValue().split(" ");
													for(String str : arreducation){
														//System.out.println("str==========="+str);
														if(StringUtils.isNotEmpty(str))
															strlist.add(str);
													}
													break;
												}
												if(!strlist.isEmpty()){
													if(strlist.size() > 0)
														startdate = strlist.get(0);
													if(strlist.size() > 2)
														enddate = strlist.get(2);
													if(strlist.size() > 3)
														school = strlist.get(3);
													if(strlist.size() > 6){
														specialty = strlist.get(5);
														culturename = strlist.get(6);
													}
													if(strlist.size() <= 6){
														specialty = strlist.get(4);
														culturename = strlist.get(5);
													}
													/*System.out.println("开始时间========"+startdate);
													System.out.println("结束时间========"+enddate);
													System.out.println("学校========"+school);
													System.out.println("专业========"+specialty);
													System.out.println("学历========"+culturename);*/
												}
												
												if(StringUtils.isEmpty(school))
													school = "无";
												if(StringUtils.isEmpty(specialty))
													specialty = "无";
												if(StringUtils.isEmpty(majordescription))
													majordescription = "无";
												if(school.length() > 50)
													school = school.substring(0, 50);
												if(specialty.length() > 50)
													specialty = specialty.substring(0, 50);
												if(majordescription.length() > 500)
													majordescription = majordescription.substring(0, 500);
												// 数据库翻译
												Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturename);
												if (culture == null)
													culture = Constance.VALID_YES;
												EducationExperience model =new EducationExperience();
												model.setEducationexperienceguid(UUIDGenerator.randomUUID());
												model.setSchool(school);
												model.setSpecialty(specialty);
												model.setCulture(culture);
												model.setCulturename(culturename);
												model.setMajordescription(majordescription);
												model.setModtime(new Timestamp(System.currentTimeMillis()));
												Date sqlstartdate = DateUtil.parse(startdate);
												if(sqlstartdate == null)
													sqlstartdate =  new Date(System.currentTimeMillis());
												model.setStartdate(sqlstartdate);
												Date sqlenddate = DateUtil.parse(enddate);
												if(sqlenddate == null)
													sqlenddate =  new Date(System.currentTimeMillis());
												model.setEnddate(sqlenddate);
												educationlist.add(model);
												System.out.println(model.toString());
											}
										}
										break;
									}else if(content.equals("培训经历")){
										//System.out.println("======培训经历======");
										statend = true;
										int nextnum = 0;
										int trainingnum = rownum+1;
										boolean state = false;//培训经历条数是否是最后一行判断
										//取到所有培训经历的行数
										for(int k = trainingnum; k < xssfsheet.getLastRowNum(); k++){
											//第rownum行
											XSSFRow rownext = xssfsheet.getRow(k);
											//System.out.println("****************************-=="+k);
											for(int g = 0; g < rownext.getLastCellNum();g++){

												XSSFCell hssfcell = rownext.getCell(g);
												//System.out.println("--------------------"+hssfcell);
												if(hssfcell != null){
													String contents = rownext.getCell(g).getStringCellValue().replaceAll(" ", "");
													if(contents.equals("证书") || contents.equals("在校学习情况")){
														nextnum = k;
														rownum = k-1;
														//System.out.println("======k_2k_2k_2k_2k_2k_2k_2k_2k_2======="+rownum);
														state = true;
													}
												}
												break;
											}
											if(state)
												break;
										}
										
										if(trainingnum < nextnum){
											for(int i = trainingnum; i < nextnum;i++){
												//boolean statetmp = false;
												String startdate = "";
												String enddate = "";
												//培训机构
												String traininginstitutions = "";
												//证书
												String certificate = "";
												//内容
												String trainingcontent = "";
												row = xssfsheet.getRow(i);
												if(row.getLastCellNum() > 0){
													for(int trainingcellnum = 0; trainingcellnum < row.getLastCellNum();trainingcellnum++){
														boolean statetraining = false;
														if(row.getCell(trainingcellnum) != null){
															if(StringUtils.isNotEmpty(row.getCell(trainingcellnum).getStringCellValue())){
																String traintitle = row.getCell(trainingcellnum).getStringCellValue().replaceAll(" ", "");
																//System.out.println(i+"__------------traintitle====="+traintitle);
																if(traintitle.equals("培训机构：")){
																	if(row.getCell(trainingcellnum+1) != null){
																		if(StringUtils.isNotEmpty(row.getCell(trainingcellnum+1).getStringCellValue())){
																			traininginstitutions = row.getCell(trainingcellnum+1).getStringCellValue();
//																			System.out.println("培训机构========"+traininginstitutions);
																		}
																	}
																	//下一行的值，直到再次等于培训机构，则终止
																	for(int tmpnum = i+1; tmpnum < nextnum;tmpnum++){
																		HSSFRow trainrow = hssfsheet.getRow(tmpnum);
																		//System.out.println("trainrow========="+trainrow.getCell(0).getStringCellValue());
																		for(int tmpcell = 0; tmpcell < trainrow.getLastCellNum();tmpcell++){
																			String tmptraintitle = trainrow.getCell(tmpcell).getStringCellValue();
																			if(tmptraintitle.equals("所获证书：")){
																				if(trainrow.getCell(tmpcell+1) != null){
																					if(StringUtils.isNotEmpty(trainrow.getCell(tmpcell+1).getStringCellValue())){
																						certificate = trainrow.getCell(trainingcellnum+1).getStringCellValue();
//																						System.out.println("所获证书========"+certificate);
																					}
																				}
																			}else if(tmptraintitle.equals("培训描述：")){
																				//System.out.println("-----------------------------"+trainrow.getCell(tmpcell+1).getStringCellValue());
																				if(trainrow.getCell(tmpcell+1) != null){
																					if(StringUtils.isNotEmpty(trainrow.getCell(tmpcell+1).getStringCellValue())){
																						trainingcontent = trainrow.getCell(tmpcell+1).getStringCellValue();
//																						System.out.println("培训描述========"+trainingcontent);
																					}
																				}
																			}else if(tmptraintitle.equals("培训机构：")){
																				statetraining = true;
																				i = tmpnum-1;
																				//statetmp = true;
																				//System.out.println("...........................................i........."+i);
																			}
																				break;
																		}
																		if(statetraining)
																			break;
																	}
																	if(StringUtils.isEmpty(certificate))
																		certificate = "无";
																	if(StringUtils.isEmpty(traininginstitutions))
																		traininginstitutions = "无";
																	if(StringUtils.isEmpty(trainingcontent))
																		trainingcontent = "无";
																	if(certificate.length() > 50)
																		certificate = certificate.substring(0, 50);
																	if(traininginstitutions.length() > 50)
																		traininginstitutions = traininginstitutions.substring(0, 50);
																	if(trainingcontent.length() > 500)
																		trainingcontent = trainingcontent.substring(0, 50);
																	
																	TrainingExperience model = new TrainingExperience();
																	model.setTrainingexperienceguid(UUIDGenerator.randomUUID());
																	model.setTrainingcontent(trainingcontent);
																	model.setCertificate(certificate);
																	model.setTraininginstitutions(traininginstitutions);
																	Date sqlstartdate = DateUtil.parse(startdate);
																	if(sqlstartdate == null)
																		sqlstartdate =  new Date(System.currentTimeMillis());
																	model.setStartdate(sqlstartdate);
																	Date sqledndate = DateUtil.parse(enddate);
																	if(sqledndate == null)
																		sqledndate =  new Date(System.currentTimeMillis());
																	model.setEnddate(sqledndate);
																	model.setModtime(new Timestamp(System.currentTimeMillis()));
																	traininglist.add(model);
//																	System.out.println(model.toString());
																}
															}
														}
														break;
													}
												}
											/*	if(statetmp)
													break;*/
											}
										}
										break;
									}
										break;
								
								}
							}
							if(statend)
								break;
						}
					}
					
				/*	continue;*/
				}
			}
		}
		
		}
		
		
		// 外網用戶
		WebUser user = null;
		// 保存到本地
		//System.out.println("===email==="+email);
		user = webUserDao.checkWebUserByEmail(null, email);
		if (user == null) {
			// 保存外网用户
			user = new WebUser(email, name);
			user.setWebuserguid(UUIDGenerator.randomUUID());
			user.setMobile(phone);
			// System.out.println(user.toString());
			webUserDao.insertWebUser(user);
		}else
			user.setMobile(phone);
		
		// 简历信息
		resume = resumeDao.getResumeById(user.getWebuserguid());
		
		// 如果簡歷存在以最新的為準
		boolean isnew = false;
		if (resume == null) {
			isnew = true;
			resume = new Resume();
			resume.setWebuserguid(user.getWebuserguid());
			resume.setCreatetime(new Timestamp(System.currentTimeMillis()));
		}
		resume.setModtime(new Timestamp(System.currentTimeMillis()));
		// 姓名
		if (user.getUsername().length() > 25)
			resume.setName(user.getUsername().substring(0, 25));
		else
			resume.setName(user.getUsername());
		// 电话
		resume.setMobile(user.getMobile());
		// 邮件
		resume.setEmail(user.getEmail());
		// 数据翻译
		Integer sex = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.SEX, sexname);
		if (sex != null) {
			resume.setSexname(sexname);
			resume.setSex(sex);
		} else {
			resume.setSex(Constance.VALID_NO);
		}
		//System.out.println("===birthday===="+birthday);
		resume.setBirthday(DateUtil.parse(birthday));
		//System.out.println("==homeplace===="+homeplace);
		if(homeplace.length() > 50)
			homeplace = homeplace.substring(0, 50);
		resume.setHomeplace(homeplace);
		resume.setMark(Constance.VALID_NO);
		//System.out.println("=======workage============="+workage);
		if(StringUtils.isNotEmpty(workage)){
			Integer workagenum = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.WORKAGE, workage);
			if (workagenum != null) {
				//System.out.println("====workagenum======"+workagenum);
				resume.setWorkagename(workage);
				resume.setWorkage(workagenum);
			} 
		}
		// 数据库翻译
		String cultruename = "无";
		if(educationlist.size() > 0)
			cultruename = educationlist.get(0).getCulturename();
		Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, cultruename);
		if (culture != null) {
			resume.setCulturename(cultruename);
			resume.setCulture(culture);
		} else
			resume.setCulture(Constance.VALID_NO);
		if(situation.length() > 50)
			situation = situation.substring(0, 50);
		if(salary.length() > 50)
			salary = salary.substring(0, 50);
		if(occupation.length() > 100)
			occupation = occupation.substring(0, 100);
		if(valuation.length() > 500)
			valuation = valuation.substring(0, 500);
		if(industry.length() > 50)
			industry = industry.substring(0, 50);
		resume.setValuation(valuation);
		resume.setSalary(salary);
		resume.setSituation(situation);
		resume.setOccupation(occupation);
		resume.setIndustry(industry);
		
		if (isnew)
			resumeDao.insertResume(resume);
		else
			resumeDao.updateResume(resume);
		
		//工作经验
		resumeDao.delWorkExperienceByWebuserId(resume.getWebuserguid());
		for(WorkExperience model : worklist){
			model.setWebuserguid(resume.getWebuserguid());
			resumeDao.insertWorkExperience(model);
		}
		//项目经验
		resumeDao.delProjectExperienceByWebuserId(resume.getWebuserguid());
		for(ProjectExperience model : projectlist){
			model.setWebuserguid(resume.getWebuserguid());
			resumeDao.insertProjectExperience(model);
		}
		//教育经历
		resumeDao.delEducationExperienceByWebuserId(resume.getWebuserguid());
		for(EducationExperience model : educationlist){
			model.setWebuserguid(resume.getWebuserguid());
			resumeDao.insertEducationExperience(model);
		}
		//培训经历
		resumeDao.delTrainingExperienceByWebuserId(resume.getWebuserguid());
		for(TrainingExperience model : traininglist){
			model.setWebuserguid(resume.getWebuserguid());
			resumeDao.insertTrainingExperience(model);
		}
		
		
		List<MyCandidates> tmpList = myCandidatesDao.getMyCandidatesByWebUserGuidAndState(user.getWebuserguid(), Constance.CandidatesState_Blacklist);
		// 实例化
		MyCandidates model = new MyCandidates();
		model.setMycandidatesguid(UUIDGenerator.randomUUID());
		model.setWebuserguid(user.getWebuserguid());
		model.setCandidatesstate(tmpList.isEmpty() ? Constance.CandidatesState_One : Constance.CandidatesState_Blacklist);
		model.setCandidatestype(Constance.User4);
		model.setProgress(Constance.VALID_NO);
		model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		model.setReadtype(Constance.VALID_NO);
		// 職位
		List<RecruitPost> list = recruitmentDao.getRecruitPostByRecruitPostName(candidatesposation);
		model.setPostname(candidatesposation);
		if (!list.isEmpty()) {
			RecruitPost recruitPost = list.get(0);
			model.setRecruitpostguid(recruitPost.getRecruitpostguid());
		} else {
			model.setRecruitpostname(candidatesposation);
		}
		//过滤掉一周以内投递过的相同简历
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		model.setTempdate(format.format(System.currentTimeMillis()));
		/*MyCandidates candidates = myCandidatesDao.checkResume(model);
		if(candidates != null){
			System.out.println("===一周内以投递==="+candidates.toString());
			//continue;
		}*/
		Date caddate= DateUtil.parse(candidatesdate);
		if(caddate == null)
			caddate = new Date(System.currentTimeMillis());
		model.setCandidatestime(caddate);
		myCandidatesDao.insertMyCandidates(model);
	}
}
