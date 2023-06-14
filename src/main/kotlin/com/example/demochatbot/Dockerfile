FROM openjdk:17.0.2-jdk
ADD ./build/ /code/chatbot
WORKDIR /code/chatbot
COPY ./build/libs/demo-chat-bot-0.0.1-SNAPSHOT.jar ./chat-bot.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "chat-bot.jar"]