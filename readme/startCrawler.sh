#!/bin/bash
if [ -z $1];then
echo "crawler source is null,execute all crawler source"
nohup java -Xmx1024m -jar crawler.jar &
else
echo "execute crawler source: $1"
nohup java -Xmx1024m -jar crawler.jar $1 &
fi
