package org.javabenchmark.instrumentation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.txw2.annotation.XmlCDATA;

@XmlRootElement(name = "after")
@XmlAccessorType(XmlAccessType.FIELD)
public class After {

	private String code;

	public String getCode() {
		return code;
	}

	@XmlCDATA
	public void setCode(String code) {
		this.code = code;
	}
	
}
