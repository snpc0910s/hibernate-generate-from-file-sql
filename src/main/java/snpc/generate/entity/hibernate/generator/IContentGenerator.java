package snpc.generate.entity.hibernate.generator;

import snpc.generate.entity.hibernate.model.EntityStruct;

public interface IContentGenerator {
	public String gen(String basePackage, EntityStruct entity);
}
