package snpc.generate.entity.hibernate.generator.repository.custom;

import snpc.generate.entity.hibernate.generator.IContentGenerator;
import snpc.generate.entity.hibernate.generator.OptionalConfig;
import snpc.generate.entity.hibernate.model.EntityStruct;
import snpc.generate.entity.hibernate.model.PropertiesStruct;

public class ContentRepoCustomImplGenerator implements IContentGenerator {
    /*
        package com.example.demo.repo.custom.impl;
        import com.example.demo.entity.City;
        import com.example.demo.entity.QCity;
        import com.example.demo.repo.custom.CityRepoCustom;
        import com.querydsl.jpa.impl.JPAQuery;
        import com.querydsl.jpa.impl.JPAQueryFactory;
        import javax.persistence.EntityManager;
        import org.springframework.data.domain.Pageable;
        import org.springframework.stereotype.Component;
        import java.util.List;

        @Component
        public class CityRepoCustomImpl implements CityRepoCustom {
            private final JPAQueryFactory queryFactory ;
            private static final QCity qCity = QCity.city1;

            public CityRepoCustomImpl(EntityManager entityManager) {
                this.queryFactory = new JPAQueryFactory(entityManager);
            }

            @Override
            public List<City> dynamic(City city, Pageable page) {
                JPAQuery<City> query =  queryFactory.selectFrom(qCity);
                if(city.getCityId() != null ) {
                    query.where(qCity.cityId.eq(city.getCityId()));
                }
                if(city.getCity() != null ) {
                    query.where(qCity.city.eq(city.getCity()));
                }
                if(city.getLastUpdate() != null ) {
                    query.where(qCity.lastUpdate.eq(city.getLastUpdate()));
                }
                return query.fetch();
            }
        }
        } */


    @Override
    public String gen(String basePackage, EntityStruct entity, OptionalConfig config) {

        /**
         * NOT SUPPORT SERVICE FOR TABLE 2 OR MORE KEY
         */
        if(entity.isSingleKey() == false)
            return "";

        String NAME_CLASS = entity.getNameClass();
        String NAME_CLASS_PROPERTIES = entity.getNameClassProperty();
        String extendNumberDSL = entity.existNamePropertiesEqualNameTable() == true ? "1" : "";

        StringBuffer buffer = new StringBuffer();
        buffer.append("package " + basePackage + ".repo.custom.impl;\n");
        buffer.append("import " + basePackage + ".entity."+NAME_CLASS+";\n");
        buffer.append("import " + basePackage + ".entity.Q"+NAME_CLASS+";\n");
        buffer.append("import " + basePackage + ".repo.custom."+NAME_CLASS+"RepoCustom;\n");
        buffer.append("import com.querydsl.jpa.impl.JPAQuery;\n");
        buffer.append("import com.querydsl.jpa.impl.JPAQueryFactory;\n");
        buffer.append("import javax.persistence.EntityManager;\n");
        buffer.append("import org.springframework.data.domain.Pageable;\n");
        buffer.append("import org.springframework.stereotype.Component;\n");
        buffer.append("import java.util.List;\n");
        buffer.append("\n");
        buffer.append("@Component\n");
        buffer.append("public class "+NAME_CLASS+"RepoCustomImpl implements "+NAME_CLASS+"RepoCustom {\n");

        buffer.append("    private final JPAQueryFactory queryFactory ;\n");

        buffer.append("    private static final Q"+NAME_CLASS+" q"+NAME_CLASS+" = Q"+NAME_CLASS+"."+NAME_CLASS_PROPERTIES + extendNumberDSL+";\n");
        buffer.append("\n");
        buffer.append("    public "+NAME_CLASS+"RepoCustomImpl(EntityManager entityManager) {\n");
        buffer.append("        this.queryFactory = new JPAQueryFactory(entityManager);\n");
        buffer.append("    }\n");
        buffer.append("\n");
        buffer.append("    @Override\n");
        buffer.append("    public List<"+NAME_CLASS+"> dynamicSearch("+NAME_CLASS+" "+NAME_CLASS_PROPERTIES+", Pageable page) {\n");
        buffer.append("        JPAQuery<"+NAME_CLASS+"> query =  queryFactory.selectFrom(q"+NAME_CLASS+");\n");
        for(PropertiesStruct property: entity.getProperties()) {
            if(config.isHaveTaskRelationShip() == true && property.isHaveRelationShip() == true) {
                // current not support search have relationship
            }else {
                // normal properties
                buffer.append("        if("+NAME_CLASS_PROPERTIES+ "." +property.getNameMethodGet()+"() != null ) {\n");
                buffer.append("            query.where(q"+NAME_CLASS+"."+property.getNameProperty()+".eq("+NAME_CLASS_PROPERTIES+"."+property.getNameMethodGet()+"()));\n");
                buffer.append("        }\n");
            }
        }
        buffer.append("        if(page != null) {\n");
        buffer.append("            query.offset(page.getPageSize() * page.getPageNumber());\n");
        buffer.append("            query.limit(page.getPageSize());\n");
        buffer.append("        }\n");
        buffer.append("        return query.fetch();\n");
        buffer.append("    }\n");
        buffer.append("}\n");

        return buffer.toString();
    }

}
