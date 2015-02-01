package ua.ishchenko.common.wsbeans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for WSResultCode complex type.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSResultCode", propOrder = { "code", "description", "summary",
		"action", "customCode" })
@XmlRootElement(name = "WSResultCode")
public class WSResultCode implements Serializable {

	private static final long serialVersionUID = -7803260325205414900L;
	protected int code;
	@XmlElement(required = true)
	protected String description;
	@XmlElement(required = true)
	protected String summary;
	@XmlElement(required = true)
	protected String action;
	protected int customCode;

	/**
	 * Gets the value of the code property.
	 * 
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Sets the value of the code property.
	 * 
	 */
	public void setCode(int value) {
		this.code = value;
	}

	/**
	 * Gets the value of the description property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the description property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * Gets the value of the summary property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the value of the summary property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSummary(String value) {
		this.summary = value;
	}

	/**
	 * Gets the value of the action property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Sets the value of the action property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAction(String value) {
		this.action = value;
	}

	/**
	 * Gets the value of the customCode property.
	 * 
	 */
	public int getCustomCode() {
		return customCode;
	}

	/**
	 * Sets the value of the customCode property.
	 * 
	 */
	public void setCustomCode(int value) {
		this.customCode = value;
	}

}
