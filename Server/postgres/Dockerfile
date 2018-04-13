FROM postgres:alpine
COPY . /init
RUN sed -i 's/\r$//' /init/init.sh
RUN cd /init
RUN cp /init/init.sh /docker-entrypoint-initdb.d