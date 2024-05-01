package snpc.generate.entity.hibernate.generator.repository;

import snpc.generate.entity.hibernate.generator.IContentGenerator;
import snpc.generate.entity.hibernate.generator.OptionalConfig;
import snpc.generate.entity.hibernate.model.EntityStruct;
import snpc.generate.entity.hibernate.model.PropertiesStruct;
import snpc.generate.entity.hibernate.util.StringUtil;

public class ContentRepoCustomGenerator implements IContentGenerator {

//    package com.example.demo.repo.custom;
//    import java.util.List;
//    import org.springframework.data.domain.Pageable;
//    import com.example.demo.entity.Country;
//
//    public interface ICountryRepoCustom {
//        public List<Country> getDynamicWhereCondition(Country country, Pageable page);
//    }


    @Override
    public String gen(String basePackage, EntityStruct entity, OptionalConfig config) {
        /**
         * NOT SUPPORT SERVICE FOR TABLE 2 OR MORE KEY
         */
        if(entity.isSingleKey() == false)
            return "";
        StringBuffer buffer = new StringBuffer();
        buffer.append("package " + basePackage + ".repo.custom;\n");
        buffer.append("import java.util.List;\n");
        buffer.append("import org.springframework.data.domain.Pageable;\n");
        buffer.append("import com.example.demo.entity."+entity.getNameClass()+";\n");
        buffer.append("\n");
        buffer.append("public interface "+entity.getNameClass()+"RepoCustom {\n");
        buffer.append("    public List<"+entity.getNameClass()+"> dynamicSearch("+entity.getNameClass()+" "+ StringUtil.lowerFirstLetter(entity.getNameClass()) +", Pageable page);\n");
        buffer.append("}\n");
        return buffer.toString();
    }
}
