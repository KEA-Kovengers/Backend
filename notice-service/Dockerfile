# Use the official gradle image to create a build artifact.
FROM gradle:8.6.0-jdk17 AS build

# 작업 디렉토리 설정
WORKDIR /app

# 소스 코드와 build.gradle 파일을 이미지로 복사
COPY src ./src
COPY build.gradle settings.gradle ./

# 애플리케이션 빌드
RUN gradle clean build -x test

# 빌드 단계 완료 후, 실행 단계를 위한 새로운 베이스 이미지 사용
FROM openjdk:17-jdk

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 단계에서 생성된 JAR 파일을 현재 디렉토리로 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 컨테이너 실행 시 JAR 파일 실행
ENTRYPOINT ["java","-jar","app.jar"]