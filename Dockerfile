FROM eclipse-temurin:17-jdk-jammy

RUN groupadd work \
    && useradd -g work work \
    && echo 'work:password' | chpasswd

USER work

WORKDIR /home/work

ENTRYPOINT [ "tail", "-f", "/dev/null" ]