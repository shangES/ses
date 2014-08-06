<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/META-INF/c.tld"%>
<!DOCTYPE html>
<html>
<head>
<title>简历打印</title>
<base href="${baseUrl }" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style>
strong,h1,h5,h6 {
	color: #555555;
	font-size: 14px;
	padding: 0px;
	margin: 0px;
	font-family:'KaiTi_GB2312';
}

h1 {
	font-size: 32px;
	height: 42px;
	line-height: 1em;
	font-family:'KaiTi_GB2312';
}

.static-print {
	width: 100%;
	border-right: 1px solid #000;
	font-family: '宋体';
}

.static-print td {
	border-left: 1px solid #000;
	border-bottom: 1px solid #000;
	padding: 10px 5px;
	line-height: 25px;
	font-size: 14px;
}

.static-print .firsttd {
	border-right: none;
}

.static-print .secondtd {
	border-left: none;
}
</style>
</head>
<body>
	<table border="0" cellspacing="0" cellpadding="0"
		style="border-top: 1px solid #000" class="static-print">
		<tr>
			<td colspan="20"><div align="center">
					<h1>华数个人简历</h1>
				</div></td>
		</tr>
		<tr>
			<td width="4%" class="firsttd">&nbsp;</td>
			<td width="85%" colspan="17" class="secondtd"  style="font-size: 20px; "><b>${resume.name}</b></td>
			<td colspan="2" rowspan="3" align="center"><img
				style="max-width: 200px; height: 125px;"
				src="${baseUrl }${resume.photo==null?'skins/images/nopic.jpg':resume.photo }" /></td>
		</tr>
		<tr>
			<td class="firsttd">&nbsp;</td>
			<td colspan="17" class="secondtd">${resume.sexname } |
				${resume.birthday }生 | 现居住于${resume.homeplace }</td>
		</tr>
		<tr>
			<td class="firsttd">&nbsp;</td>
			<td colspan="17" class="secondtd"><span class="secondtd">${resume.workagename
					}(工作经验)&nbsp;|&nbsp;最高学历${resume.culturename } </span></td>
		</tr>
		<tr>
			<td class="firsttd">&nbsp;</td>
			<td colspan="19" class="secondtd">${resume.mobile }(手机)</td>
		</tr>
		<tr>
			<td class="firsttd">&nbsp;</td>
			<td colspan="19" class="secondtd">E-mail：<a
				href="mailto:${resume.email }">${resume.email }</a>
			</td>
		</tr>

		<tr>
			<td colspan="20">自我评价</td>
		</tr>
		<tr>
			<td class="firsttd">&nbsp;</td>
			<td colspan="19" class="secondtd">${resume.valuation}</td>
		</tr>

		<tr>
			<td colspan="20">求职意向</td>
		</tr>
		<tr>
			<td class="firsttd">&nbsp;</td>
			<td width="20%" colspan="2" class="secondtd">期望从事职业：</td>
			<td colspan="17">${resume.occupation}</td>
		</tr>
		<tr>
			<td class="firsttd">&nbsp;</td>
			<td colspan="2" class="secondtd">行业：</td>
			<td colspan="17">${resume.industry}</td>
		</tr>
		<tr>
			<td class="firsttd">&nbsp;</td>
			<td colspan="2" class="secondtd">期望月薪：</td>
			<td colspan="17">${resume.salary}</td>
		</tr>
		<tr>
			<td class="firsttd">&nbsp;</td>
			<td colspan="2" class="secondtd">目前状况：</td>
			<td colspan="17">${resume.situation}</td>
		</tr>

		<tr>
			<td colspan="20">工作经历</td>
		</tr>
		<c:forEach items="${workexperiences }" var="item">
			<tr>
				<td class="firsttd">&nbsp;</td>
				<td colspan="19" class="secondtd">${item.startdate } --
					${item.enddate==null?'至今':item.enddate }</td>
			</tr>
			<tr>
				<td class="firsttd">&nbsp;</td>
				<td colspan="19" class="secondtd">${item.workunit } |
					${item.posation }</td>
			</tr>
			<tr>
				<td class="firsttd">&nbsp;</td>
				<td colspan="2" class="secondtd">职责描述：</td>
				<td colspan="17">${item.jobdescription }</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="20">项目经验</td>
		</tr>
		<c:forEach items="${projectexperiences }" var="item">
			<tr>
				<td class="firsttd">&nbsp;</td>
				<td colspan="19" class="secondtd">${item.startdate } --
					${item.enddate==null?'至今':item.enddate }</td>
			</tr>
			<tr>
				<td class="firsttd">&nbsp;</td>
				<td colspan="19" class="secondtd">${item.itemname }</td>
			</tr>
			<tr>
				<td class="firsttd">&nbsp;</td>
				<td colspan="2" class="secondtd">职责描述：</td>
				<td colspan="17">${item.jobdescription }</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="20">教育经历</td>
		</tr>
		<c:forEach items="${educationexperiences }" var="item">
			<tr>
				<td class="firsttd">&nbsp;</td>
				<td colspan="19" class="secondtd">${item.startdate } --
					${item.enddate==null?'至今':item.enddate }</td>
			</tr>
			<tr>
				<td class="firsttd">&nbsp;</td>
				<td colspan="19" class="secondtd">${item.school } |
					${item.specialty } | ${item.culturename }</td>
			</tr>
			<tr>
				<td class="firsttd">&nbsp;</td>
				<td colspan="2" class="secondtd">专业描述：</td>
				<td colspan="17">${item.majordescription }</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="20">培训经历</td>
		</tr>
		<c:forEach items="${trainingexperiences }" var="item">
			<tr>
				<td class="firsttd">&nbsp;</td>
				<td colspan="19" class="secondtd">${item.startdate } --
					${item.enddate==null?'至今':item.enddate }</td>
			</tr>
			<tr>
				<td class="firsttd">&nbsp;</td>
				<td colspan="19" class="secondtd">${item.traininginstitutions }</td>
			</tr>
			<tr>
				<td class="firsttd">&nbsp;</td>
				<td colspan="2" class="secondtd">所获证书：</td>
				<td colspan="17">${item.certificate }</td>
			</tr>
			<tr>
				<td class="firsttd">&nbsp;</td>
				<td colspan="2" class="secondtd">培训描述：</td>
				<td colspan="17">${item.trainingcontent }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>