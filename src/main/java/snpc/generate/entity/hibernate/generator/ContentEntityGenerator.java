package snpc.generate.entity.hibernate.generator;

import java.util.ArrayList;
import java.util.List;

import snpc.generate.entity.hibernate.model.EntityStruct;
import snpc.generate.entity.hibernate.model.PropertiesStruct;

public class ContentEntityGenerator implements IContentGenerator {

	@Override
	public String gen(String basePackage, EntityStruct entity) {
		StringBuffer buffer = new StringBuffer();

		// 1. init header
		buffer.append("package " + basePackage + ".entity;\n");
		buffer.append("");
		buffer.append("import java.util.Date;\n");
		buffer.append("import javax.persistence.Column;\n");
		buffer.append("import javax.persistence.Entity;\n");
		buffer.append("import javax.persistence.Table;\n");
		if(entity.isSingleKey() == false) {
			buffer.append("import java.io.Serializable;\n");
			buffer.append("import javax.persistence.Embeddable;\n");
			buffer.append("import javax.persistence.EmbeddedId;\n");
		}else {
			buffer.append("import javax.persistence.GeneratedValue;\n");
			buffer.append("import javax.persistence.GenerationType;\n");
			buffer.append("import javax.persistence.Id;\n");
		}
		buffer.append("\n");
		buffer.append("@Entity\n");
		buffer.append("@Table(name = \"" + entity.getNameTable() + "\")\n");
		buffer.append("public class " + entity.getNameClass() + " {\n");
		buffer.append("");
		// 2. init properties
		boolean added = false;
		for (PropertiesStruct property : entity.getProperties()) {
			buffer.append("\n");
			if (property.isKey() == true && entity.isSingleKey() == true) {
				// generate only 1 id
				buffer.append("    @Id\n");
				if (property.getFullStringLine().contains("AUTO_INCREMENT")) {
					buffer.append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
				}
				buffer.append("    @Column(name = \"" + property.getNameColumn() + "\")\n");
				buffer.append("    private " + property.getTypeProperty() + " " + property.getNameProperty() + ";\n");
			} else if (property.isKey() == true && entity.isSingleKey() == false) {
				// generate 2 or more id
				if (added == false) {
					String embedded = this.createEmbeddedFromEntity(entity);
					buffer.append(embedded);
					buffer.append("    @EmbeddedId\n");
					buffer.append("    private " + entity.getNameClass() + "Id id;\n");

				}
				added = true;
			} else {
				// normal property
				buffer.append("    @Column(name = \"" + property.getNameColumn() + "\")\n");
				buffer.append("    private " + property.getTypeProperty() + " " + property.getNameProperty() + ";\n");
			}

		}
		// 3. init getter setter
		buffer.append("\n");
		buffer.append("	// getter and setter\n");
		for (PropertiesStruct property : entity.getProperties()) {
			if(entity.isSingleKey() == true) {
				buffer.append(property.genGetterAndSetter(1));				
			}else {
				if(property.isKey() == false) {
					buffer.append(property.genGetterAndSetter(1));
				}
			}
		}
		if (entity.isSingleKey() == false) {
			buffer.append("    public " + entity.getNameClass() + "Id getId() {\n");
			buffer.append("        return id;\n");
			buffer.append("    }\n");
			buffer.append("\n");
			buffer.append("    public void setId(" + entity.getNameClass() + "Id id) {\n");
			buffer.append("        this.id = id;\n");
			buffer.append("    }\n");
		}
		// 4. end and return
		buffer.append("}");
		return buffer.toString();
	}

	private String createEmbeddedFromEntity(EntityStruct entity) {
		// get only primary key
		List<PropertiesStruct> proIds = new ArrayList<>();
		for (PropertiesStruct pro : entity.getProperties()) {
			if (pro.isKey() == true)
				proIds.add(pro);
		}
		List<String> constructorFullParam = new ArrayList<>();
		for (PropertiesStruct property : proIds) {
			constructorFullParam.add(property.getTypeProperty() + " " + property.getNameProperty());
		}

		// process
		StringBuffer buffer = new StringBuffer();

		buffer.append("    // static class\n");
		buffer.append("    @Embeddable\n");
		buffer.append("    public static class " + entity.getNameClass() + "Id implements Serializable {\n");
		for (PropertiesStruct property : proIds) {
			buffer.append("        @Column(name = \"" + property.getNameColumn() + "\")\n");
			buffer.append("        private " + property.getTypeProperty() + " " + property.getNameProperty() + ";\n");
		}
		buffer.append("\n");
		buffer.append("\n");
		buffer.append("        public " + entity.getNameClass() + "Id(){}\n");
		buffer.append("\n");
		buffer.append(
				"        public " + entity.getNameClass() + "Id(" + String.join(",", constructorFullParam) + ") {\n");
		for (PropertiesStruct property : proIds) {
			buffer.append(
					"            this." + property.getNameProperty() + " = " + property.getNameProperty() + ";\n");
		}
		buffer.append("        }\n");
		buffer.append("        \n");
		buffer.append("\n");
		buffer.append("		// getter and setter 2 key\n");
		for (PropertiesStruct property : proIds) {
			buffer.append(property.genGetterAndSetter(2));
		}
		buffer.append("\n");
		buffer.append("\n");
		buffer.append("        @Override\n");
		buffer.append("        public int hashCode() {\n");
		buffer.append("            final int prime = 31;\n");
		buffer.append("            int result = 1;\n");
		for (PropertiesStruct property : proIds) {
			buffer.append("            result = prime * result + ((" + property.getNameProperty() + " == null) ? 0 : "
					+ property.getNameProperty() + ".hashCode());\n");
		}
		buffer.append("            return result;\n");
		buffer.append("        }\n");
		buffer.append("\n");
		buffer.append("        @Override\n");
		buffer.append("        public boolean equals(Object obj) {\n");
		buffer.append("            if (this == obj)\n");
		buffer.append("                return true;\n");
		buffer.append("            if (obj == null)\n");
		buffer.append("                return false;\n");
		buffer.append("            if (getClass() != obj.getClass())\n");
		buffer.append("                return false;\n");
		buffer.append("            " + entity.getNameClass() + "Id other = (" + entity.getNameClass() + "Id) obj;\n");
		for (PropertiesStruct property : proIds) {
			buffer.append("            if (" + property.getNameProperty() + " == null) {\n");
			buffer.append("                if (other." + property.getNameProperty() + " != null)\n");
			buffer.append("                    return false;\n");
			buffer.append("            } else if (!" + property.getNameProperty() + ".equals(other."
					+ property.getNameProperty() + "))\n");
			buffer.append("                return false;\n");
		}
		buffer.append("            return true;\n");
		buffer.append("        }\n");
		buffer.append("    }\n");

		// append ID to parent class

		return buffer.toString();
	}
}
