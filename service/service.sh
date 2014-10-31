#!/bin/bash
function getVersion(){
  grep 'val Version' $rootDir/project/build.scala | sed 's/ *val Version = //' | sed 's/"//g'
}
project=geek-bookmarks
scalaVersion=scala-2.11

serviceDir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
rootDir=$serviceDir/..
targetDir=$rootDir/target/
pidFile=$serviceDir/servive.pid
logFile=$serviceDir/service.log
packageVersion=$(getVersion)
serviceJar=$targetDir/$scalaVersion/$project-assembly-$packageVersion.jar
echo $serviceJar

function start(){
  nohup java -Dpidfile=$pidFile -jar $serviceJar </dev/null > $logFile 2>&1
}

function stop(){
  kill -9 $(cat $pidFile)
}
