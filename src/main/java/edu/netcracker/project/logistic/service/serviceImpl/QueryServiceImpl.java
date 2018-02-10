package edu.netcracker.project.logistic.service.serviceImpl;

import edu.netcracker.project.logistic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


@PropertySource("classpath:queries.properties")
public class QueryServiceImpl implements QueryService {

    @Autowired
    Environment environment;

    @Override
    public String getQuery(String query_name) {
        return environment.getRequiredProperty(query_name);
    }
}