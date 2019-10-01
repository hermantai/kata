set -e

FILE1="$1"

javac kata/"$1".java
java kata."$1"
