package org.javabenchmark.instrumentation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBExample {

	public static void main(String[] args) {
		
		try {
			File file = new File("F:/Hotels/myagent/configuration.xml");
			JAXBContext jaxbContext1 = JAXBContext.newInstance(Configurations.class);
	 
			Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
			Configurations configurationsObj = (Configurations) jaxbUnmarshaller.unmarshal(file);
			System.out.println(configurationsObj);
			
			
			Configurations configurations = new Configurations();
			
			Configuration configuration = new Configuration();
			configuration.setClassName("test.class.name");
			configuration.setInterfaceName("test.interface.name");
			configuration.setLocation("test.location.name");
			List<Configuration> configurationList = new ArrayList<Configuration>();
			configurationList.add(configuration);
			
			configurations.setConfiguration(configurationList);
			
//			MethodName methodName = new MethodName();
//			methodName.setMethodName("testMethod1");
			
			List<String> methodNames = new ArrayList<String>();
			methodNames.add("testMethod1");
			methodNames.add("testMethod2");
			
			configuration.setMethodName(methodNames);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(Configurations.class);
		    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		 
		    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		     
		    //Marshal the employees list in console
		    jaxbMarshaller.marshal(configurations, System.out);
//		     
//		    //Marshal the employees list in file
//		    jaxbMarshaller.marshal(configurations, new File("c:/temp/employees.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
