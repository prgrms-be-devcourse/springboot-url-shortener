# Dockerfile

# MySQL 이미지를 기반으로 이미지 생성
FROM mysql:latest
ADD schema.sql /docker-entrypoint-initdb.d/

# MySQL 설정
ENV MYSQL_ROOT_PASSWORD=my-secret-pw
ENV MYSQL_DATABASE=url
ENV MYSQL_USER=myuser
ENV MYSQL_PASSWORD=mypassword

# 포트 설정 (기본 MySQL 포트는 3306)
EXPOSE 3306
