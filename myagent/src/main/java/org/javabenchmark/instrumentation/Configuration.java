package org.javabenchmark.instrumentation;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "configuration")
public class Configuration {

	private String className;
	private String interfaceName;
	private String location;
	private List<String> methodName;
	private List<Method> method;

	public List<String> getMethodName() {
		return methodName;
	}

	@XmlElement(name = "methodName")
	public void setMethodName(List<String> methodName) {
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	@XmlAttribute
	public void setClassName(String className) {
		this.className = className;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	@XmlAttribute(name = "interfaceName")
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getLocation() {
		return location;
	}

	@XmlAttribute(name = "location")
	public void setLocation(String location) {
		this.location = location;
	}

	public List<Method> getMethod() {
		return method;
	}

	@XmlElement(name = "method")
	public void setMethod(List<Method> method) {
		this.method = method;
	}

}
