Overview

This Java program reads a CSV file (38811103.csv), creates a MySQL database with three tables (Manufacturer, CPU, and Phone), populates them with data from the CSV, executes some queries, and finally deletes the database.

The program demonstrates:

Reading CSV files in Java

Creating and manipulating MySQL databases and tables

Populating tables with dynamic SQL

Executing queries including DELETE and GROUP BY-HAVING

Handling foreign key constraints

Prerequisites

Before running the program, make sure you have:

Java JDK installed

MySQL installed and running

A CSV file named 38811103.csv in the project directory

Usage

Clone the repository or download the project.

Place your CSV file 38811103.csv in the project folder.

Compile the Java program:

javac code.java


Run the program:

java code


The program will:

Read the CSV file

Create a database AlvinSCC201 and three tables

Populate the tables with data

Execute delete and group queries

Print the query results to the console

Delete the database
