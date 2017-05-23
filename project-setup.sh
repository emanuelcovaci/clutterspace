#!/bin/bash

IDE="$1"
gradle build

if [[ "$IDE" = "eclipse" ]]; then
  gradle afterEclipseImport
fi

