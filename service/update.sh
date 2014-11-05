#!/bin/bash
serviceDir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $serviceDir
git checkout master
git pull origin master
cd $serviceDir/../src/main/webapp/
bower install
bower update
