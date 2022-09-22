package snpc.generate.entity.hibernate.generator;

import snpc.generate.entity.hibernate.model.EntityStruct;

public class ContentIntefaceServiceGenerator implements IContentGenerator {
	/*
	package com.example.demo.services;

	import java.util.List;
	import java.util.Optional;

	import com.example.demo.entity.Actor;

	public interface IActorService {
	    public List<Actor> findAll();

	    public Optional<Actor> findById(Integer id);

	    public boolean exist(Integer id);

	    public Optional<Actor> insert(Actor saveActor);

	    public Optional<Actor> update(Integer actorId, Actor saveActor);
	}
	*/
	@Override
	public String gen(String basePackage, EntityStruct entity) {
		StringBuffer buffer = new StringBuffer();
		/**
		 * NOT SUPPORT SERVICE FOR TABLE 2 OR MORE KEY
		 */
		if(entity.isSingleKey() == false) 
			return "";
		buffer.append("package "+basePackage+".services;\n");
		buffer.append("\n");
		buffer.append("import java.util.List;\n");
		buffer.append("import java.util.Optional;\n");
		buffer.append("\n");
		buffer.append("import "+basePackage+".entity."+entity.getNameClass()+";\n");
		buffer.append("\n");
		buffer.append("public interface I"+entity.getNameClass()+"Service {\n");
		buffer.append("    public List<"+entity.getNameClass()+"> findAll();\n");
		buffer.append("\n");
		buffer.append("    public Optional<"+entity.getNameClass()+"> findById("+entity.findKeyIfSingleKey().getTypeProperty()+" id);\n");
		buffer.append("\n");
		buffer.append("    public boolean exist("+entity.findKeyIfSingleKey().getTypeProperty()+" id);\n");
		buffer.append("\n");
		buffer.append("    public Optional<"+entity.getNameClass()+"> insert("+entity.getNameClass()+" save"+entity.getNameClass()+");\n");
		buffer.append("\n");
		buffer.append("    public Optional<"+entity.getNameClass()+"> update("+entity.findKeyIfSingleKey().getTypeProperty()+" "+entity.getNameClassProperty()+"Id, "+entity.getNameClass()+" save"+entity.getNameClass()+");\n");
		buffer.append("}\n");
		buffer.append("\n");
		return buffer.toString();
	}
}
