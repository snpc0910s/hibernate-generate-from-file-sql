package snpc.generate.entity.hibernate.generator;

import snpc.generate.entity.hibernate.model.EntityStruct;

public class ContentBasicApiDTOGenerator implements IContentGenerator{

	@Override
	public String gen(String basePackage, EntityStruct entity) {
		StringBuffer buffer = new StringBuffer();
		/**
		 * NOT SUPPORT SERVICE FOR TABLE 2 OR MORE KEY
		 */
		if(entity.isSingleKey() == false) 
			return "";
		
		
		return buffer.toString();
	}

}
