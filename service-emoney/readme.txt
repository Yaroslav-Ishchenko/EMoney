to build and package the project you should acomplish next steps:
1)Create tables into your db
2) reconfigure jetty9.xml to connect your db (if current settings differ from your db or its local path)
3) reconfigure HibernateJpaVendorAdapter (specify the db dialect)
4) to run jetty get to the root project's folder and type: mvn jett:run This will build the war and deploy it to the jetty
4) to run integration test you should start a jetty instance with deployed war and than run command: mvn failsafe:integration-test
