package com.mycompany.jpaassociations.config;

import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.Course;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.Student;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.UpdateCourseDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.UpdateStudentDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.model.Book;
import com.mycompany.jpaassociations.manytomany.simplerelationship.model.Writer;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.UpdateBookDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.UpdateWriterDto;
import com.mycompany.jpaassociations.onetomany.simplepk.model.Dish;
import com.mycompany.jpaassociations.onetomany.simplepk.model.Restaurant;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.UpdateDishDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.UpdateRestaurantDto;
import com.mycompany.jpaassociations.onetoone.simplepk.model.Team;
import com.mycompany.jpaassociations.onetoone.simplepk.model.TeamDetail;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamDetailDto;
import com.mycompany.jpaassociations.onetoone.simplepk.rest.dto.UpdateTeamDto;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    MapperFactory mapperFactory() {
        DefaultMapperFactory defaultMapperFactory = new DefaultMapperFactory.Builder().useAutoMapping(true).build();

        defaultMapperFactory.classMap(UpdateTeamDto.class, Team.class).mapNulls(false).byDefault().register();
        defaultMapperFactory.classMap(UpdateTeamDetailDto.class, TeamDetail.class).mapNulls(false).byDefault().register();

        defaultMapperFactory.classMap(UpdateRestaurantDto.class, Restaurant.class).mapNulls(false).byDefault().register();
        defaultMapperFactory.classMap(UpdateDishDto.class, Dish.class).mapNulls(false).byDefault().register();

        defaultMapperFactory.classMap(UpdateBookDto.class, Book.class).mapNulls(false).byDefault().register();
        defaultMapperFactory.classMap(UpdateWriterDto.class, Writer.class).mapNulls(false).byDefault().register();

        defaultMapperFactory.classMap(UpdateStudentDto.class, Student.class).mapNulls(false).byDefault().register();
        defaultMapperFactory.classMap(UpdateCourseDto.class, Course.class).mapNulls(false).byDefault().register();

        return defaultMapperFactory;
    }

    @Bean
    MapperFacade mapperFacade() {
        return mapperFactory().getMapperFacade();
    }
}

