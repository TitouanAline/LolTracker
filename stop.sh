#!/bin/bash

echo "🛑 Arrêt des services..."

kill $(lsof -t -i:8080)   # backend Spring Boot
kill $(lsof -t -i:4200)   # Angular