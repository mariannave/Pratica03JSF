﻿@echo off
rem Modifique a variável abaixo para o local onde está a pasta do seu banco de dados.
set BD_HOME=C:\Users\marianna\Desktop\pratica03\bd
echo =========
echo Local do banco de dados: %BD_HOME%
echo =========

java -cp .\lib\hsqldb.jar org.hsqldb.server.Server --database.0 file:%BD_HOME%\turmasweb\turmasweb --dbname.0 tweb