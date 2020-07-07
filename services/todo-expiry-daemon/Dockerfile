FROM python:3-alpine3.11

RUN pip install pipenv

WORKDIR /app
COPY ["Pipfile", "Pipfile.lock", "./"]
RUN pipenv install

COPY ["main.py", "daemon.py", "logging-config.ini", "./"]

ENTRYPOINT ["pipenv", "run", "start"]
