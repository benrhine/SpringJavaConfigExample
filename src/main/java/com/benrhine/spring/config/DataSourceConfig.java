package com.benrhine.spring.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.benrhine.spring.init.Initializer;

@Configuration
@PropertySource("classpath:application.properties")
public class DataSourceConfig {
	//private static final Logger logger = Logger.getLogger(DataSourceConfig.class);
	
	//private static final String PROPERTY_NAME_DATABASE_DRIVER 	= "db.driver";
    //private static final String PROPERTY_NAME_DATABASE_PASSWORD 	= "db.password";
    //private static final String PROPERTY_NAME_DATABASE_URL 		= "db.url";
    //private static final String PROPERTY_NAME_DATABASE_USERNAME 	= "db.username";
	
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT 			 = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL	 		 = "hibernate.show_sql";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
    
	@Resource
	private Environment env;
	
	/**
	 * Property Based Configuration:
	 * 
	 * DataSource will retrieve properties from the application.properties file and set them.
	 */
	/*@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(	env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(				env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(			env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(			env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

		return dataSource;
	}*/
	
	/**
	 * JNDI DataSource Configuration:
	 * 
	 * All data source properties come from the deployment container.
	 */
	@Bean
	public DataSource dataSource() throws Exception {
		Context initialContext = new InitialContext();
		
		if (Initializer.CONTAINER.equalsIgnoreCase("Apache Tomcat")) {
			return (DataSource) initialContext.lookup("java:comp/env/jdbc/test");
		} else if (Initializer.CONTAINER.equalsIgnoreCase("jetty")) {
			return (DataSource) initialContext.lookup("jdbc/test");
		} else {
			throw new Exception("Could not figure out JNDI name!");
		}
		
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		try {
			sessionFactoryBean.setDataSource(dataSource());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sessionFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
		sessionFactoryBean.setHibernateProperties(hibProperties());
		return sessionFactoryBean;
	}
	
	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		return properties;	
	}
	
	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
	
//	@Bean
//	public JndiTemplate jndiTemplate() {
//		JndiTemplate jt = new JndiTemplate();
//		Properties environment = new Properties();
//		environment.put("java.naming.factory.initial", "org.eclipse.naming.InitialContextFactory");
//		environment.put("java.naming.provider.url", "org.eclipse.naming");
//		
//		jt.setEnvironment(environment);
//		return jt;
//	}
//	
//	@Bean
//	public JndiObjectFactoryBean jndiDataSource() {
//		JndiObjectFactoryBean jofb = new JndiObjectFactoryBean();
//		jofb.setJndiTemplate(this.jndiTemplate());
//		jofb.setResourceRef(false);
//		jofb.setJndiName("jdbc/test");
//		return jofb;
//	}
	
//	<bean id="jndiTemplate" class="org.springframework.jndi.JndiTemplate">
//    <property name="environment">
//        <props>
//            <prop key="java.naming.factory.initial">org.mortbay.naming.InitialContextFactory</prop>
//            <prop key="java.naming.provider.url">org.mortbay.naming</prop>
//        </props>
//    </property>
//</bean>
//
//<bean id="jndiDataSource" class="org.springframework.jndi.JndiObjectFactoryBean">
//
//    <property name="jndiTemplate">
//      <ref bean="jndiTemplate"/>
//    </property>
//    <property name="jndiName">
//          <value>jdbc/MySQLDS</value>
//    </property>
//  </bean>
}
