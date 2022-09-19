package snpc.generate.entity.hibernate.generator;

import snpc.generate.entity.hibernate.model.EntityStruct;
import snpc.generate.entity.hibernate.model.PropertiesStruct;

public class ContentRepoGenerator implements IContentGenerator {

	@Override
	public String gen(String basePackage, EntityStruct entity) {

		String typeId = "";
		if (entity.isSingleKey() == false) {
			typeId = entity.getNameClass() + "Id";
		} else {
			for(PropertiesStruct property : entity.getProperties()) {
				if(property.isKey()) {
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
		buffer.append("\n");
		buffer.append("@Repository\n");
		buffer.append("public interface " + entity.getNameClass() + "Repo extends JpaRepository<"
				+ entity.getNameClass() + "," + typeId + ">{\n");
		buffer.append("}\n");
		return buffer.toString();
	}

}
