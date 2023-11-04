FROM postgres:15-bullseye

LABEL author="Bogoslovskiy Alexandr"
LABEL description="Postgres Image for Bank"
LABEL version="1.0"

COPY ./infrastructure/db/*.sql /docker-entrypoint-initdb.d/