FROM openjdk:8-alpine
LABEL maintainer="acarbenay@adhuc.fr"

ENV TZ=Europe/Paris
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /srv/cena
COPY ./menu-generation.jar menu-generation.jar
CMD java -jar menu-generation.jar