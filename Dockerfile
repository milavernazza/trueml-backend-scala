FROM hseeberger/scala-sbt:11.0.6_1.3.10_2.13.1

WORKDIR /app

COPY . /app

RUN sbt clean compile

EXPOSE 8080

CMD ["sbt", "run"]
