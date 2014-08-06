<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/META-INF/c.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title>简历管理</title>
<base href="${baseUrl }" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link rel="stylesheet" type="text/css" href="skins/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="skins/css/jquery-ui-1.8.15.custom.css" />

<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery.layout.js"></script>
<script type="text/javascript"
	src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>


<style>
.resumeBody {
	color: #555555;
	font-size: 12px;
	border-top: 0px solid #ddd;
	max-height: 600px;
}

.resumeBody strong,.resumeBody h5 {
	color: #555555;
	font-size: 14px;
	padding: 0px;
	margin: 0px;
}

.resumeBody .ver-line {
	color: #555555;
	font-size: 14px;
	padding: 0 5px;
}

.resumeBody h5,.resumeBody strong {
	font-weight: bold;
}

.resumeBody .details dt {
	background: url("skins/images/lookResumebg.jpg") no-repeat scroll 5px 0
		transparent;
}

.resumeBody .details dt h5 {
	color: #315AAA;
	font-size: 14px;
	height: 26px;
	line-height: 26px;
	text-indent: 20px;
}

.resumeBody .summary,.resumeBody .details dd {
	line-height: 25px;;
	margin: 10px;
	padding-left: 10px;
}
/* .resumeBody .training {border-bottom: 1px dotted #CCCCCC;margin-bottom: 10px;padding-bottom: 5px;} */
.bordorbuttom {
	border-bottom: 1px dotted #CCCCCC;
	margin-bottom: 10px;
	padding-bottom: 5px;
}

.resumeBody .training:last-child {
	border-bottom: 0 none;
}
</style>


<script type="text/javascript">
	//通讯录
	function goAddressList(addresslistguid) {
		window.open('${baseUrl }/resume.do?page=form_addresslist&id='
				+ addresslistguid);
	}
</script>
</head>
<body>


	<div class="resumeBody">

		<c:if test="${recommendslist!=null }">
			<dd>
				<c:choose>
					<c:when test="${fn:length(recommendslist)>0 }">
						<c:forEach items="${recommendslist }" var="item" begin="0" end="2">
						<dt>
							<h5>
								<strong>简历推荐</strong>
							</h5>
						</dt>
					<div class="training">
						<br>
						<p>建议面试时间：${item.auditiontime }</p>
						<p>推荐公司：${item.recommendcompanyname }</p>
						<p>推荐部门：${item.recommenddeptname }</p>
						<p>推荐岗位：${item.recommendpostname }</p>
						<p>
							推荐人： <a
								href="javascript:goAddressList('${item.addresslistguid }');">
								${item.username} </a>
						</p>
					</div>
					<br>
					<dt>
						<h5>面试记录</h5>
					</dt>
					<c:if test="${auditionRecordslist!=null }">
						<dd>
							<c:forEach items="${auditionRecordslist}" var="item" begin="0"
								end="2">
								<div class="training">
									<br>
									<p>面试时间：${item.auditiondate}</p>
									<p>面试地点：${item.auditionaddress}</p>
									<p>面试官：${item.maininterviewerguidname}</p>
									<p>面试结果：${item.resulttypename}</p>
									<p>面试评语：${item.resultcontent}</p>
								</div>
							</c:forEach>
						</dd>
					</c:if>

					<br>
					<dt>
						<h5>体检记录</h5>
					</dt>
					<c:if test="${examinationRecordslist!=null }">
						<dd class="bordorbuttom">
							<c:forEach items="${examinationRecordslist}" var="item" begin="0"
								end="2">
								<div class="training">
									<br>
									<p>体检机构：${item.thirdpartnerguidname}</p>
									<p>体检时间：${item.examinationdate}</p>
									<p>体检地点：${item.examinationaddress}</p>
									<p>体检结果：${item.examinationstatename}</p>
								</div>
							</c:forEach>
						</dd>
					</c:if>
				</c:forEach>
				</c:when>
				<c:otherwise>
				<dt>
					<h5>
						<strong>简历推荐</strong>
					</h5>
				</dt>
					<br>
					<dt>
						<h5>面试记录</h5>
					</dt>
					<br>
					<dt>
						<h5>体检记录</h5>
					</dt>
				</c:otherwise>
				</c:choose>
			</dd>
		</c:if>
	</div>


</body>
</html>
