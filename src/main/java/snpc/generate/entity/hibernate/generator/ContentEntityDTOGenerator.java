package snpc.generate.entity.hibernate.generator;

import snpc.generate.entity.hibernate.model.EntityStruct;
import snpc.generate.entity.hibernate.model.PropertiesStruct;

public class ContentEntityDTOGenerator implements IContentGenerator {

	/*
	package com.example.demo.dto;
	import java.util.Date;

	public class ActorDTO {
	    private Integer actorId;

	    private String firstName;

	    private String lastName;

	    private Date lastUpdate;

		// getter and setter
	    public Integer getActorId() {
	        return actorId;
	    }
	    public void setActorId(Integer actorId) {
	        this.actorId = actorId;
	    }
	    public String getFirstName() {
	        return firstName;
	    }
	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	    }
	    public String getLastName() {
	        return lastName;
	    }
	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	    }
	    public Date getLastUpdate() {
	        return lastUpdate;
	    }
	    public void setLastUpdate(Date lastUpdate) {
	        this.lastUpdate = lastUpdate;
	    }
	    
	}
	*/
	
	@Override
	public String gen(String basePackage, EntityStruct entity) {
		if(entity.isSingleKey() == false)
			return "";
		StringBuffer buffer = new StringBuffer();
		buffer.append("package " + basePackage + ".dto;\n");
		buffer.append("import java.util.Date;\n");
		buffer.append("\n");
		buffer.append("public class " + entity.getNameClass() + "DTO {\n");
		for (PropertiesStruct property : entity.getProperties()) {
			buffer.append("    private " + property.getTypeProperty() + " " + property.getNameProperty() + ";\n");
			buffer.append("\n");
		}
		buffer.append("	// getter and setter\n");
		for (PropertiesStruct property : entity.getProperties()) {
			buffer.append(property.genGetterAndSetter(1));
		}
		buffer.append("    \n");
		buffer.append("}\n");
		return buffer.toString();
	}

}
