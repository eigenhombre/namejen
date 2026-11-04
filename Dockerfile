FROM clojure:temurin-21-tools-deps

RUN apt-get -qq -y update
RUN apt-get -qq -y upgrade

RUN apt-get install -qq -y make nodejs

WORKDIR /home/janice
COPY . /home/janice
RUN make
RUN java -jar target/$(ls target | grep standalone)
