FROM ubuntu:latest
LABEL authors="alexwu"

ENTRYPOINT ["top", "-b"]