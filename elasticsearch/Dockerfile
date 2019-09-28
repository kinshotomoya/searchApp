FROM docker.elastic.co/elasticsearch/elasticsearch:6.8.3

# 日本語解析用のプラグインをinstall
RUN elasticsearch-plugin install analysis-kuromoji

ADD ./elasticsearch.yml /usr/share/elasticsearch/config/elasticsearch.yml