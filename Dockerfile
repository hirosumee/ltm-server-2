# use MSSQL 2017 image on Ubuntu 16.04
FROM microsoft/mssql-server-linux:2017-latest

# create directory within SQL container for database files
RUN mkdir -p /opt/mssql-scripts

# copy the database files from host to container
COPY script.sql /opt/mssql-scripts

# set environment variables
ENV MSSQL_SA_PASSWORD=P@ssw0rd
ENV ACCEPT_EULA=Y

# run initial scripts
RUN /opt/mssql/bin/sqlservr --accept-eula & sleep 10 \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P 'P@ssw0rd' -i /opt/mssql-scripts/script.sql \
    && pkill sqlservr
EXPOSE 1433