#!/usr/bin/env bash
mongosh "mongodb+srv://cluster0.1op9b.mongodb.net/logs" --username admin --password admin --eval "db.scalaapi.find().sort({_id:-1}).limit(1)"