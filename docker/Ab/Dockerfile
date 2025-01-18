FROM alpine:latest

# Обновляем систему
RUN apk update && \
    apk upgrade
WORKDIR /usr/local/bin/ab
# Устанавливаем необходимые пакеты
RUN apk add --no-cache apache2-utils

EXPOSE 8888
ENTRYPOINT ["tail", "-f", "/dev/null"]


# Копируем файл ab в директорию bin
#ADD ab /usr/local/bin/ab

# Делаем файл исполняемым
#RUN chmod +x ab

# Указываем команду по умолчанию при запуске контейнера
#CMD ["ab", "-n", "1000", "-c", "10", "http://social_net_otus:8080/"]