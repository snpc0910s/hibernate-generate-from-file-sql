package snpc.generate.entity.hibernate.generator.service;

import java.util.ArrayList;
import java.util.List;

import snpc.generate.entity.hibernate.generator.IContentGenerator;
import snpc.generate.entity.hibernate.model.EntityStruct;
import snpc.generate.entity.hibernate.model.PropertiesStruct;
import snpc.generate.entity.hibernate.util.StringUtil;

public class ContentServiceRelationshipGenerator  implements IContentGenerator  {
    @Override
	public String gen(String basePackage, EntityStruct entity) {
		/**
		 * NOT SUPPORT SERVICE FOR TABLE 2 OR MORE KEY
		 */
		if(entity.isSingleKey() == false) 
			return "";
		String NAME_CLASS = entity.getNameClass();
		String NAME_CLASS_PROPERTIES = entity.getNameClassProperty();
		
		StringBuffer buffer = new StringBuffer();
		// header
		buffer.append("package "+basePackage+".services.impl;\n");
		buffer.append("\n");
		buffer.append("import java.util.List;\n");
		buffer.append("import java.util.Optional;\n");
		buffer.append("\n");
		buffer.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		buffer.append("import org.springframework.stereotype.Service;\n");
		buffer.append("import org.springframework.transaction.annotation.Transactional;\n");
		buffer.append("\n");
		buffer.append("import "+basePackage+".entity."+NAME_CLASS+";\n");
		buffer.append("import "+basePackage+".repo."+NAME_CLASS+"Repository;\n");
        // import relationship repo
        for(PropertiesStruct property: entity.getProperties())
        if(property.isHaveRelationShip() == true){
            buffer.append("import "+basePackage+".entity."+StringUtil.nameToNameClass(property.getRelationshipWithTable())+";\n");
            buffer.append("import "+basePackage+".repo."+StringUtil.nameToNameClass(property.getRelationshipWithTable())+"Repository;\n");
        }
		buffer.append("import "+basePackage+".services.I"+NAME_CLASS+"Service;\n");
		buffer.append("\n");
		buffer.append("@Service\n");
		buffer.append("public class "+NAME_CLASS+"ServiceImpl implements I"+NAME_CLASS+"Service {\n");
		buffer.append("\n");
		// declare repo
		buffer.append("    @Autowired\n");
		buffer.append("    private "+NAME_CLASS+"Repository "+NAME_CLASS_PROPERTIES+"Repo;\n");
		buffer.append("\n");
        // declare repo have relationship
        int countPropertiesHaveRelationship = 0;
        for(PropertiesStruct property: entity.getProperties())
        if(property.isHaveRelationShip() == true){
            buffer.append("    @Autowired\n");
		    buffer.append("    private "+StringUtil.nameToNameClass(property.getRelationshipWithTable())+"Repository "+StringUtil.nameToNameProperties(property.getRelationshipWithTable())+"Repo;\n");
		    buffer.append("\n");
            countPropertiesHaveRelationship++;
        }
		// main function: find all
		buffer.append("    @Transactional(readOnly = true)\n");
		buffer.append("    @Override\n");
		buffer.append("    public List<"+NAME_CLASS+"> findAll() {\n");
		buffer.append("        return "+NAME_CLASS_PROPERTIES+"Repo.findAll();\n");
		buffer.append("    }\n");
		buffer.append("\n");
		// main function: find by id
		buffer.append("    @Transactional(readOnly = true)\n");
		buffer.append("    @Override\n");
		buffer.append("    public Optional<"+NAME_CLASS+"> findById("+entity.findKeyIfSingleKey().getTypeProperty()+" id) {\n");
		buffer.append("        return "+NAME_CLASS_PROPERTIES+"Repo.findById(id);\n");
		buffer.append("    }\n");
		buffer.append("\n");
		// main function: exist
		buffer.append("    @Transactional(readOnly = true)\n");
		buffer.append("    @Override\n");
		buffer.append("    public boolean exist(Integer id) {\n");
		buffer.append("        return "+NAME_CLASS_PROPERTIES+"Repo.existsById(id);\n");
		buffer.append("    }\n");
		buffer.append("\n");
		// main function: insert
		buffer.append("    @Transactional(readOnly = false)\n");
		buffer.append("    @Override\n");
		buffer.append("    public Optional<"+NAME_CLASS+"> insert("+NAME_CLASS+" save"+NAME_CLASS+") {\n");
        if(countPropertiesHaveRelationship == 0) {
            // dont have any relationship
            buffer.append("        "+NAME_CLASS+" "+NAME_CLASS_PROPERTIES+" = new "+NAME_CLASS+"();\n");
            for(PropertiesStruct property: entity.getProperties()) {
                if(property.isKey()) {
                    buffer.append("        "+NAME_CLASS_PROPERTIES+"."+property.getNameMethodSet()+"(null);\n");
                }else {
                    buffer.append("        "+NAME_CLASS_PROPERTIES+"."+property.getNameMethodSet()+"(save"+NAME_CLASS+"."+property.getNameMethodGet()+"());\n");
                }
            }
            buffer.append("        "+NAME_CLASS+" o = "+NAME_CLASS_PROPERTIES+"Repo.save("+NAME_CLASS_PROPERTIES+");\n");
            buffer.append("        return "+NAME_CLASS_PROPERTIES+"Repo.findById(o."+entity.findKeyIfSingleKey().getNameMethodGet()+"());\n");
        }else {
            // have relationship
            List<String> lstIsPresent = new ArrayList<>(); 
            for(PropertiesStruct property: entity.getProperties()) 
            if(property.isHaveRelationShip()== true){
                buffer.append("        "+"Optional<"+StringUtil.nameToNameClass(property.getRelationshipWithTable())+"> o"+StringUtil.nameToNameClass(property.getRelationshipWithTable())+" = "+StringUtil.nameToNameProperties(property.getRelationshipWithTable())+"Repo.findById(save"+NAME_CLASS+".get"+StringUtil.nameToNameClass(property.getRelationshipWithTable())+"().get"+StringUtil.nameToNameClass(property.getRelationshipWithTable())+"Id());");
                lstIsPresent.add("o"+StringUtil.nameToNameClass(property.getRelationshipWithTable())+ ".isPresent()");
            }
            buffer.append("\n        "+"if("+String.join(" && ", lstIsPresent)+") {");
            buffer.append("\n            "+NAME_CLASS+" "+NAME_CLASS_PROPERTIES+" = new "+NAME_CLASS+"();\n");
            for(PropertiesStruct property: entity.getProperties()) {
                if(property.isKey()) {
                    buffer.append("            "+NAME_CLASS_PROPERTIES+"."+property.getNameMethodSet()+"(null);\n");
                }else {
                    if(property.isHaveRelationShip() == false) {
                        // normal properties
                        buffer.append("            "+NAME_CLASS_PROPERTIES+"."+property.getNameMethodSet()+"(save"+NAME_CLASS+"."+property.getNameMethodGet()+"());\n");
                    }else {
                        buffer.append("            "+NAME_CLASS_PROPERTIES+ ".set"+StringUtil.nameToNameClass(property.getRelationshipWithTable())+"(o"+StringUtil.nameToNameClass(property.getRelationshipWithTable())+".get());\n");
                    }
                }
            }
            buffer.append("            "+NAME_CLASS+" o = "+NAME_CLASS_PROPERTIES+"Repo.save("+NAME_CLASS_PROPERTIES+");\n");
            buffer.append("            return "+NAME_CLASS_PROPERTIES+"Repo.findById(o."+entity.findKeyIfSingleKey().getNameMethodGet()+"());\n");
            buffer.append("        "+"}");
            buffer.append("\n        "+"throw new ObjectNotFoundException(\"Not exist Store or Address\");");
        }
		buffer.append("\n    }\n");
		buffer.append("\n");


		// main function: update
		buffer.append("    @Transactional(readOnly = false)\n");
		buffer.append("    @Override\n");
		buffer.append("    public Optional<"+NAME_CLASS+"> update("+entity.findKeyIfSingleKey().getTypeProperty()+" "+NAME_CLASS_PROPERTIES+"Id, "+NAME_CLASS+" save"+NAME_CLASS+") {\n");
		 if(countPropertiesHaveRelationship == 0) {
            // dont have relationship
            buffer.append("        save"+NAME_CLASS+"."+entity.findKeyIfSingleKey().getNameMethodSet()+"("+NAME_CLASS_PROPERTIES+"Id);\n");
            buffer.append("        Optional<"+NAME_CLASS+"> "+NAME_CLASS_PROPERTIES+"Optional = "+NAME_CLASS_PROPERTIES+"Repo.findById(save"+NAME_CLASS+"."+entity.findKeyIfSingleKey().getNameMethodGet()+"());\n");
            buffer.append("        if ("+NAME_CLASS_PROPERTIES+"Optional.isPresent()) {\n");
            buffer.append("            "+NAME_CLASS+" "+NAME_CLASS_PROPERTIES+" = "+NAME_CLASS_PROPERTIES+"Optional.get();\n");
            for(PropertiesStruct property: entity.getProperties()) {
                if(property.isKey()) {
                    continue;
                }else {
                    buffer.append("            "+NAME_CLASS_PROPERTIES+"."+property.getNameMethodSet()+"(save"+NAME_CLASS+"."+property.getNameMethodGet()+"());\n");
                }
            }
            buffer.append("            "+NAME_CLASS+" o = "+NAME_CLASS_PROPERTIES+"Repo.save("+NAME_CLASS_PROPERTIES+");\n");
            buffer.append("            return "+NAME_CLASS_PROPERTIES+"Repo.findById(o."+entity.findKeyIfSingleKey().getNameMethodGet()+"());\n");
            buffer.append("        }\n");
            buffer.append("        return Optional.empty();\n");
        }else {
            // have relationship
            List<String> lstIsPresent = new ArrayList<>(); 
            buffer.append("        save"+NAME_CLASS+"."+entity.findKeyIfSingleKey().getNameMethodSet()+"("+NAME_CLASS_PROPERTIES+"Id);\n");
            buffer.append("\n        Optional<"+NAME_CLASS+"> "+NAME_CLASS_PROPERTIES+"Optional = "+NAME_CLASS_PROPERTIES+"Repo.findById(save"+NAME_CLASS+"."+entity.findKeyIfSingleKey().getNameMethodGet()+"());\n");
            lstIsPresent.add(NAME_CLASS_PROPERTIES+"Optional.isPresent()");
            for(PropertiesStruct property: entity.getProperties()) 
            if(property.isHaveRelationShip()== true){
                buffer.append("        "+"Optional<"+StringUtil.nameToNameClass(property.getRelationshipWithTable())+"> o"+StringUtil.nameToNameClass(property.getRelationshipWithTable())+" = "+StringUtil.nameToNameProperties(property.getRelationshipWithTable())+"Repo.findById(save"+NAME_CLASS+".get"+StringUtil.nameToNameClass(property.getRelationshipWithTable())+"().get"+StringUtil.nameToNameClass(property.getRelationshipWithTable())+"Id());");
                lstIsPresent.add("o"+StringUtil.nameToNameClass(property.getRelationshipWithTable())+ ".isPresent()");
            }
            buffer.append("\n        "+"if("+String.join(" && ", lstIsPresent)+") {"); 
            buffer.append("\n            "+NAME_CLASS+" "+NAME_CLASS_PROPERTIES+" = "+NAME_CLASS_PROPERTIES+"Optional.get();\n");
            for(PropertiesStruct property: entity.getProperties()) {
                if(property.isKey()) {
                    continue;
                }else {
                    if(property.isHaveRelationShip() == false) {
                        // normal properties
                        buffer.append("            "+NAME_CLASS_PROPERTIES+"."+property.getNameMethodSet()+"(save"+NAME_CLASS+"."+property.getNameMethodGet()+"());\n");
                    }else {
                        buffer.append("            "+NAME_CLASS_PROPERTIES+ ".set"+StringUtil.nameToNameClass(property.getRelationshipWithTable())+"(o"+StringUtil.nameToNameClass(property.getRelationshipWithTable())+".get());\n");
                    }
                }
            }
            buffer.append("            "+NAME_CLASS+" o = "+NAME_CLASS_PROPERTIES+"Repo.save("+NAME_CLASS_PROPERTIES+");\n");
            buffer.append("            return "+NAME_CLASS_PROPERTIES+"Repo.findById(o."+entity.findKeyIfSingleKey().getNameMethodGet()+"());\n");
            buffer.append("        }\n");
            buffer.append("        return Optional.empty();\n");
        }
        
		buffer.append("    }\n");
		buffer.append("}\n");
		buffer.append("\n");
		return buffer.toString();
	}
}
