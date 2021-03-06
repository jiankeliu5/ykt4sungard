<?xml version="1.0"?>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" "http://www.springframework.org/dtd/spring-beans.dtd">

<beans>
	<bean id="liferayDataSourceTarget" class="com.liferay.util.spring.jndi.PortalDataSourceFactoryBean" lazy-init="true" />
	<bean id="liferayDataSource" class="org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy" lazy-init="true">
		<property name="targetDataSource">
			<ref bean="liferayDataSourceTarget" />
		</property>
	</bean>
	<bean id="liferaySessionFactory" class="${springHibernatePackage}.HibernateConfiguration" lazy-init="true">
		<property name="dataSource">
			<ref bean="liferayDataSource" />
		</property>
	</bean>
	<bean id="liferayTransactionManager" class="org.springframework.orm.hibernate3.HibernateTransactionManager" lazy-init="true">
		<property name="dataSource">
			<ref bean="liferayDataSource" />
		</property>
		<property name="sessionFactory">
			<ref bean="liferaySessionFactory" />
		</property>
	</bean>
</beans>