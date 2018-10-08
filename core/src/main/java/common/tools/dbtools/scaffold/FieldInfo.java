package common.tools.dbtools.scaffold;

public class FieldInfo {
	private String type;
	private String name;
	private String comment;
	
	public FieldInfo(String type,String name,String comment){
		this.type=type;
		this.name=name;
		this.comment = comment;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
