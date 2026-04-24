#!/bin/bash

echo "🚀 Lancement backend..."
cd backend
mvn spring-boot:run &
BACK_PID=$!

echo "🚀 Lancement frontend..."
cd ../frontend
npm start &
FRONT_PID=$!

echo "Backend PID: $BACK_PID"
echo "Frontend PID: $FRONT_PID"

wait