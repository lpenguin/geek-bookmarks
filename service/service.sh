#!/bin/bash



function getVersion(){
  grep 'val Version' $rootDir/project/build.scala | sed 's/ *val Version = //' | sed 's/"//g'
}

function start(){
  echo $pidFile
  echo $serviceJar
  echo $$ > $pidFile
  nohup java -jar $serviceJar </dev/null </dev/null > $logFile 2>&1 &
}

function stop(){
  if [[ -f $pidFile ]] ; then
    kill -9 $(cat $pidFile)
    rm -rf $pidFile
  else
    echo "Pid file is not exists"
  fi
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


for option in "$@" ; do
  case $option in
    start)
    echo "Starting service"
    start
    ;;
    stop)
      echo "Stopping service"
      stop
    ;;
  esac
done
