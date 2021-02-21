__clean:
	./mvnw clean -f pom.xml

package: __clean
	./mvnw package -f pom.xml

#Requires chrome to run selenium integration tests
run-selenium-tests:
	./mvnw test -f pom.xml

run-local:
	./mvnw spring-boot:run

#Windows specific commands
__clean-win:
	.\mvnw.cmd clean -f pom.xml

package-win: __clean-win
	.\mvnw.cmd package -f pom.xml

run-selenium-tests-win:
	.\mvnw.cmd test -f pom.xml

run-local-win:
	.\mvnw.cmd spring-boot:run
