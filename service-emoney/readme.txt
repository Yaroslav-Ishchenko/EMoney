to build and package the project you should acomplish next steps:
1)Create tables into your db
2) reconfigure jetty9.xml to connect your db (if current settings differ from your db or its local path)
3) reconfigure HibernateJpaVendorAdapter (specify the db dialect)
4) under test there are test which use web services and to make them pass either you need delete them if you use this project just for development etc.
	or check the user table and make sure at least one record is present, at root project's folder run next command to start jetty: mvn -D jetty.port=8888 jetty:run
	after succesfull start run: mvn clean package
