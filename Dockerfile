FROM maven:3.9.5-eclipse-temurin-21-alpine as build
#
WORKDIR /is-my-burguer-controle-pedido
#
COPY ./ ./
RUN mvn clean
RUN mvn install

FROM eclipse-temurin:21-jdk-alpine as main
EXPOSE 8843
EXPOSE 5007

COPY --from=build /is-my-burguer-controle-pedido/api-main-build/src/main/resources/springboot.crt springboot.crt
RUN keytool -importcert -file springboot.crt -alias springboot -keystore $JDK_HOME/jre/lib/security/cacerts
COPY --from=build /is-my-burguer-controle-pedido/api-main-build/target/is-my-burguer-controle-pedido.jar is-my-burguer-controle-pedido.jar

ENTRYPOINT ["java","-jar","is-my-burguer-controle-pedido.jar","--spring.profiles.active=production","-Dserver.port=8543"]
#CMD ["sleep","infinity"] Only for testing