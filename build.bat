@echo off
mvnw.cmd clean install assembly:single && ^
echo @echo off > frettler.bat && ^
echo java -jar ./target/frettler-0.1.0-jar-with-dependencies.jar >> frettler.bat