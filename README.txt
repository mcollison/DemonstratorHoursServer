This project was designed to support logging demonstrator hours at Newcastle University. 

To initialise the system a MySQL database should be running on the host machine. The login credentials should be stored in a the 'database.properties' file within the resources directory in the source code. 

Ideally to setup the system you should seed the database with a list of demonstrators and modules although they can be updated with new entries. 

****MySQL Setup****
sudo apt-get install mysql-server
mysql setup.sql


****Form submission inserts****
with java binding
SELECT ID FROM demonstrators WHERE LastName = FormDemoName;
INSERT INTO hours (ID, FormModuleCode, FormHours, FormDate);

****Monthly reports****
SELECT ID FROM demonstrators;
java loop 
SELECT * FROM hours where Demo_date >= 01/currentmonth/currentyear AND ID=IDfromabove;
export to file in java 
send mail with attached file
