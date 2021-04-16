package org.javabenchmark.instrumentation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.sun.xml.txw2.annotation.XmlCDATA;

@XmlRootElement(name = "method")
@XmlAccessorType(XmlAccessType.FIELD)
public class Method {

	private String name;
	private String code;
	private String codeInject;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	@XmlCDATA
	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeInject() {
		return codeInject;
	}

	public void setCodeInject(String codeInject) {
		this.codeInject = codeInject;
	}

}
