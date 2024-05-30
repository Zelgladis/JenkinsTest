#!/bin/bash
find -type f -regex "./target/mgtu-adapter-bank-[0-9]+\\.[0-9]+\\.[0-9]+\\.jar" -exec cp {} /home/jboss/mgtu-adapter-bank.jar \;