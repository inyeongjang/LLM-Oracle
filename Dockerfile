FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    openjdk-8-jdk \
    openjdk-17-jdk \
    openjdk-11-jdk \
    git \
    python3 \
    python3-pip \
    python3-venv \
    subversion \
    perl \
    wget \
    ca-certificates \
    curl \
    unzip \
    cpanminus \
    libdbi-perl \
    libstring-interpolate-perl \
    && rm -rf /var/lib/apt/lists/*

RUN update-alternatives --set java /usr/lib/jvm/java-17-openjdk-amd64/bin/java && \
    update-alternatives --set javac /usr/lib/jvm/java-17-openjdk-amd64/bin/javac
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

WORKDIR /home/ubuntu
RUN git clone https://github.com/facumolina/fixcheck
WORKDIR /home/ubuntu/fixcheck

RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

RUN ./gradlew shadowJar

RUN python3 -m venv /opt/venv && \
    /opt/venv/bin/pip install --upgrade pip setuptools wheel

RUN /opt/venv/bin/pip install -r experiments/requirements.txt
RUN /opt/venv/bin/pip install -r llms/requirements.txt

ENV PATH="/opt/venv/bin:${PATH}"

WORKDIR /home/ubuntu
RUN git clone https://github.com/rjust/defects4j
RUN git clone https://github.com/Ultimanecat/DefectRepairing