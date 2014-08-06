package com.mk.webservice;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.mk.webservice.service.HrService;

public class Client {

	// public static void main(String args[]) throws Exception {
	// JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
	// org.apache.cxf.endpoint.Client client =
	// dcf.createClient("http://localhost:8080/hrmweb_v2/ws/OrgService?wsdl");
	// // sayHello 为接口中定义的方法名称 张三为传递的参数 返回一个Object数组
	// // 输出调用结果
	// System.out.println(client.invoke("getWSEmployeeList"));
	// }

	public static void main(String args[]) throws Exception {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		// 注册WebService接口
		factory.setServiceClass(HrService.class);
		// 设置WebService地址
		factory.setAddress("http://localhost:8080/hrmweb_v2/ws/OrgService");
		HrService dfw = (HrService) factory.create();
		System.out.println("invoke webservice...");
		//System.out.println(dfw.getWSEmployeeList("12003"));
		System.out.println("---------");
		//System.out.println(dfw.getWSEmployeeListByPostId("958"));
		//System.out.println(dfw.getWSDepartmentByPostId("958"));
		//System.out.println(dfw.getWSEmployeeListByDepartment("0050"));
		//System.out.println(dfw.getNumByPostCode("916"));
		//System.out.println(dfw.getPostCodeByDeptCode("dept11"));
	}

}
