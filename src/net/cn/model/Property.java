package net.cn.model;

/**
 * @author sunfan314
 *	value		属性值
 *	text		属性文本内容
 *	group		属性所属组
 */
public class Property {
	
	private String value;
	
	private String text;
	
	private String group;
	
	public Property(){
		super();
	}
	public Property(String value,String text){
		super();
		this.value=value;
		this.text=text;
	}
	
	public Property(String value,String text,String group){
		super();
		this.value=value;
		this.text=text;
		this.group=group;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	

}
