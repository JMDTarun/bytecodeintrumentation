package org.javabenchmark.instrumentation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "defaultCode")
@XmlAccessorType(XmlAccessType.FIELD)
public class DefaultCode {

	private After after;
	private Before before;

	public After getAfter() {
		return after;
	}

	public void setAfter(After after) {
		this.after = after;
	}

	public Before getBefore() {
		return before;
	}

	public void setBefore(Before before) {
		this.before = before;
	}

}
