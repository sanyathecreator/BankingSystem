#!/bin/bash
cd "$(dirname "$0")"
./gradlew installDist -q && ./app/build/install/app/bin/app
