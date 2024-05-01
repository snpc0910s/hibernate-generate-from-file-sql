package snpc.generate.entity.hibernate.generator.service;

import snpc.generate.entity.hibernate.generator.IContentGenerator;
import snpc.generate.entity.hibernate.generator.OptionalConfig;
import snpc.generate.entity.hibernate.model.EntityStruct;

public class ContentIntefaceServiceDTOGenerator implements IContentGenerator {

	@Override
	public String gen(String basePackage, EntityStruct entity, OptionalConfig config) {
		StringBuffer buffer = new StringBuffer();
		/**
		 * NOT SUPPORT SERVICE FOR TABLE 2 OR MORE KEY
		 */
		if(entity.isSingleKey() == false) 
			return "";
		
		
		
		return buffer.toString();
	}

}
