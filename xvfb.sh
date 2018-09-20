#!/bin/bash

# This script will start an xvfb scren and then launch your application into it, using the command passed in as arguments.
# If you would like to attach a VNC session to this, run `XAUTHORITY=/tmp/Xauthority x11vnc -display :99` and then connect your preffered VNC client to port 5900

echo "Starting the xvfb server..."
XAUTHORITY=/tmp/Xauthority Xvfb ":99" -screen 0 1450x1450x16 &
export DISPLAY=:99

echo "Evaluating the command to start test run in docker container"
mvn test