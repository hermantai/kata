set -e

FILE1="$1"

javac -Xlint kata/"$1".java
java kata."$1"
