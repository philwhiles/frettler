@echo off
mvnw.cmd clean install assembly:single && ^
echo @echo off > frettler.bat && ^
echo chcp 65001 > nul && ^
echo java -Dfile.encoding=UTF8 -jar ./target/frettler-0.1.0-jar-with-dependencies.jar %%* >> frettler.bat