package snpc.generate.entity.hibernate.generator.repository;

import snpc.generate.entity.hibernate.generator.IContentGenerator;
import snpc.generate.entity.hibernate.model.EntityStruct;
import snpc.generate.entity.hibernate.model.PropertiesStruct;

public class ContentRepoGenerator implements IContentGenerator {

	/*
	package com.example.demo.repo;

	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.stereotype.Repository;

	import com.example.demo.entity.Actor;

	@Repository
	public interface ActorRepo extends JpaRepository<Actor,Integer>{
	}
	*/
	/*
	package com.example.demo.repo;

	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.stereotype.Repository;

	import com.example.demo.entity.FilmCategory;
	import com.example.demo.entity.FilmCategory.FilmCategoryId;

	@Repository
	public interface FilmCategoryRepo extends JpaRepository<FilmCategory,FilmCategoryId>{
	}
	*/
	@Override
	public String gen(String basePackage, EntityStruct entity) {

		String typeId = "";
		if (entity.isSingleKey() == false) {
			typeId = entity.getNameClass() + "Id";
		} else {
			for (PropertiesStruct property : entity.getProperties()) {
				if (property.isKey()) {
					typeId = property.getTypeProperty();
				}
			}
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append("package " + basePackage + ".repo;\n");
		buffer.append("\n");
		buffer.append("import org.springframework.data.jpa.repository.JpaRepository;\n");
		buffer.append("import org.springframework.stereotype.Repository;\n");
		buffer.append("\n");
		buffer.append("import " + basePackage + ".entity." + entity.getNameClass() + ";\n");
		if (entity.isSingleKey() == false) {
			buffer.append("import " + basePackage + ".entity." + entity.getNameClass() + "." + entity.getNameClass()
					+ "Id;\n");
		}
		buffer.append("\n");
		buffer.append("@Repository\n");
		buffer.append("public interface " + entity.getNameClass() + "Repository extends JpaRepository<"
				+ entity.getNameClass() + "," + typeId + ">{\n");
		buffer.append("}\n");
		return buffer.toString();
	}

}
