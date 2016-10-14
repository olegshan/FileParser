# FileParser

This REST application receives files and parses them in parallel way.
The result of parsing is map with lines as the keys and number of their occurrences as the values.
Both source files and maps with result of parsing are stored into the database.

How to set up:

Create a MySQL database called "fileparser" with username "root" and password "root"
(If you need a database dump, it's located in folder ./src/main/resources/DBdump)

To upload some files please use the form.html file located in folder ./src/main/resources
You can test uploading using your own .txt files up to 30 MB or provided files in the folder ./src/main/resources/testfiles

To parse files after uploading please visit http://localhost:8080/parse
