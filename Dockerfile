FROM eclipse-temurin:21-jdk-jammy

RUN groupadd work \
    && useradd -g work work \
    && echo 'work:password' | chpasswd

USER work

WORKDIR /home/work

ENTRYPOINT [ "tail", "-f", "/dev/null" ]