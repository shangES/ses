package icbc.cmis.operation;

import java.util.Hashtable;
import icbc.cmis.base.*;
/**
 * Insert the type's description here.
 * Creation date: (2001-12-25 14:55:07)
 * @author: Administrator
 */
public class CmisTestOperation extends CmisOperation {
/**
 * CmisTestOperation constructor comment.
 */
public CmisTestOperation() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-25 14:55:07)
 */
public void execute() throws Exception{
	try{
		//业务逻辑
		System.out.println("写你的业务逻辑处理...");
		IndexedDataCollection family = new IndexedDataCollection();
		KeyedDataCollection child = new KeyedDataCollection();
		DataElement name = new DataElement();
		name.setName("userName");
		name.setValue("张三");
		child.addElement(name);

		DataElement age = new DataElement();
		age.setName("userAge");
		age.setValue("18");
		child.addElement(age);

		DataElement job = new DataElement();
		job.setName("userWork");
		job.setValue("student");
		child.addElement(job);
		//etc....,你可以添加关于此人的其他属性
		family.addElement(child);

		KeyedDataCollection father = new KeyedDataCollection();
		DataElement name1 = new DataElement();
		name1.setName("userName");
		name1.setValue("张三父亲");
		father.addElement(name1);

		DataElement age1 = new DataElement();
		age1.setName("userAge");
		age1.setValue("48");
		father.addElement(age1);

		DataElement job1 = new DataElement();
		job1.setName("userWork");
		job1.setValue("医生");
		father.addElement(job1);
		//etc....,你可以添加关于此人的其他属性
		
		family.addElement(father);

		//display family info

		for(int i = 0; i < family.getSize(); i++){
			KeyedDataCollection aMember = (KeyedDataCollection)family.getElement(i);
			System.out.println("姓名:"+(String)aMember.getValueAt("userName"));
			System.out.println("年龄:"+(String)aMember.getValueAt("userAge"));
			System.out.println("职业:"+(String)aMember.getValueAt("userWork"));
		}

		
		//业务处理完毕
		
		addSessionData("familyInfo",family);//如果交易数据需要在下一次请求中使用,保存到session
		//当该数据不在有用时，从session中清除数据
		removeSessionData("familyInfo");

		//如果有动态数据要保存
		addDataField("opDynData",family);
		setOperationDataToSession();

		//设置返回页面
		setReplyPage("//icbc//cmis//frame.jsp");
		
		
	}catch(Exception e){
		setReplyPage("//icbc//cmis//error.jsp");
	}
	
	
}
}
