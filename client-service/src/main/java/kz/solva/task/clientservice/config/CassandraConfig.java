package kz.solva.task.clientservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {
    // антипаттерн magicNumbers
    public static final String KEYSPACE = "adik";
    public static final String LOCAL_DATA_CENTER = "datacenter1";
    @Bean
    public CqlSessionFactoryBean cassandraSession() {
        CqlSessionFactoryBean sessionFactoryBean = new CqlSessionFactoryBean();
        sessionFactoryBean.setKeyspaceName(KEYSPACE);
        sessionFactoryBean.setContactPoints("127.0.0.1");
        sessionFactoryBean.setPort(9042);
        sessionFactoryBean.setLocalDatacenter(LOCAL_DATA_CENTER);
        return sessionFactoryBean;
    }
    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(KEYSPACE)
                .with(KeyspaceOption.DURABLE_WRITES, true);
        return Arrays.asList(specification);
    }

    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        return Arrays.asList(DropKeyspaceSpecification.dropKeyspace(KEYSPACE));
    }

    @Override
    protected String getKeyspaceName() {
        return KEYSPACE;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {"kz.solva.task.clientservice.entity.currency"};
    }

}
