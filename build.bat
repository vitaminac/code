set p=%1
javac -d out src/%p%.java
copy "src\%p%Input.txt" "out\%p%Input.txt"
java -classpath out/ %p% < out/%p%Input.txt