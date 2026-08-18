# IMPORTANT



1.the lib file is very important, it's a bridge between the java with the MySQL code. in VS Studio Code do CTRL+SHIFT+P then type Java: Configure Classpath -> libraries -> add library -> click on the MySQL connector located in the lib folder.



2.In dbeaver, create a db name employeedata(Only name it differently if you know what you're doing) copy and paste the whole MYSQL.txt into the script section there.





3\. in DBconnect, type in your username USER = "TYPE HERE";, and password if you have it PASSWORD = "TYPE HERE"; or if you don't have a password, PASSWORD ="";  then in the URL section, after localhost:3306/**employeeData** should be it unless you changed the DB name in #2(Change it if your db name is not employeedata)



4\. You run it using Main.java, there are only 3 admin and that is Snoopy, Charlie, and Lucy with the password admin1, admin2 and admin3 in that order, the reaining employee and everyone after that is employee, for now the admin and employee have a **password consist of their role+their emp id**. you can check this in dbvear by use employeedata; select \* from employees;



5\. Login using username and password from above and start using, thank you.

