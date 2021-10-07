#!/usr/bin/env bash
harbor_url=$1
harbor_project_name=$2
project_name=$3
tag=$4
port_out=$5
port_in=$6

image_name=$harbor_url/$harbor_project_name/$project_name:$tag

echo image_name: $image_name

# 查找是否已存在旧容器，存在则删除
container_id=$(docker ps -a | grep -w "$project_name":"$tag" | awk '{print $1}')
if [ "$container_id" != "" ]; then
	docker stop $container_id
	docker rm $container_id
	echo Delete container $container_id successfully!
fi

# 查找是否已存在旧镜像，存在则删除
image_id=$(docker images | grep -w "$project_name" | awk '{print $3}')
if [ "$image_id" != "" ]; then
	docker rmi -f $image_id
	echo Delete image $image_id successfully!
fi

# 登录 Harbor
docker login -u Irvingsoft -p Irvingsoft1130 $harbor_url

# 下载镜像
docker pull $image_name

# 启动容器
if [ "$port_in" != "" ]; then
	echo "View project!"
	docker run -di -p $port_out:$port_in --name $project_name --net=bridge $image_name
else
	echo "Backend project!"	
	docker run -di -p $port_out:$port_out -v /root/target:/target -v /etc/localtime:/etc/localtime --name $project_name --net=bridge $image_name --restart=on-failure
fi

docker logs $project_name

echo Start container successfully!