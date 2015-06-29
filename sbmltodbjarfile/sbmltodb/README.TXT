
run following command on command line after u are inside the jar folder
java -jar "sbmltodb.jar" dbserver dbuser dbpass dbname directorypath

e.g
java -jar "sbmltodb.jar" "localhost" "root" "root" "sbmldb2" "F:/Files/new"

Note: Diretory path should not end with / so if we need diretory F:Files it should be F:/Files only ( It will read all models from that directory and insert in dba)

Notes:

* If two JAR files on the project classpath have the same name, only the first
JAR file is copied to the lib folder.
* Only JAR files are copied to the lib folder.
If the classpath contains other types of files or folders, these files (folders)
are not copied.
* If a library on the projects classpath also has a Class-Path element
specified in the manifest,the content of the Class-Path element has to be on
the projects runtime path.
* To set a main class in a standard Java project, right-click the project node
in the Projects window and choose Properties. Then click Run and enter the
class name in the Main Class field. Alternatively, you can manually type the
class name in the manifest Main-Class element.
