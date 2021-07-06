FROM maven:3.8.1-openjdk-16
WORKDIR /app
RUN git clone https://github.com/philwhiles/frettler.git && \
    cd ./frettler/ && \
    ./build
ENTRYPOINT ["/app/frettler/frettler"]
CMD ["--help"]
