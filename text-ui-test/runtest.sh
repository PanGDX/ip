#!/usr/bin/env bash



# Define variables
JAR_FILE="ip.jar"
MAIN_CLASS="Sigmund.Sigmund" # Package.Class

# 1. CLEANUP
# Remove the bin directory and previous output files to ensure a fresh build
if [ -d "bin" ]; then
    rm -rf bin
fi

rm -f "$JAR_FILE" ACTUAL.TXT

# Create bin directory
mkdir bin

# 2. COMPILE
# -d bin: Output compiled classes to the bin folder
# -sourcepath src/main/java: Tells javac where to look for other packages (like 'data') if Sigmund imports them
# src/main/java/Sigmund/*.java: Only target the files in the Sigmund folder for compilation (dependencies will be pulled in automatically)
if ! javac -d bin -sourcepath src/main/java src/main/java/Sigmund/*.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# 3. CREATE JAR
# c: create, f: file, e: entry-point
# -C bin .: Go into the 'bin' folder and include everything ('.') inside it
if ! jar cfe "$JAR_FILE" "$MAIN_CLASS" -C bin .
then
    echo "********** JAR CREATION FAILURE **********"
    exit 1
fi

pwd
cd ./text-ui-test
pwd

if [ -d "data" ]; then
    rm -rf data
fi

# 4. RUN TEST
# Run the program using the generated jar
java -jar "../$JAR_FILE" < input.txt > ACTUAL.TXT

# --- VERIFICATION (Standard diff logic) ---

# Remove ANSI colors
sed -i 's/\x1b\[[0-9;]*m//g' ACTUAL.TXT

# Compare output
cp EXPECTED.TXT EXPECTED-UNIX.TXT
dos2unix -n ACTUAL.TXT ACTUAL-UNIX.TXT
dos2unix -n EXPECTED-UNIX.TXT EXPECTED-UNIX.TXT

if diff ACTUAL-UNIX.TXT EXPECTED-UNIX.TXT
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi
