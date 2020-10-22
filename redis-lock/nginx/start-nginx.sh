#!/usr/bin/env bash


docker rm -f nginx && docker run --name nginx --volume "$PWD/conf":/etc/nginx/conf.d -p 9999:80 -d nginx