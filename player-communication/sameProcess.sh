# Ensure the script stops on the first error
set -e

# Print each command before executing (for debugging purposes)
set -x

# Compile the project using Maven
mvn clean compile > /dev/null

# Execute the main class
mvn exec:java -Dexec.mainClass="com.example.players.MainSameProcess"

# shellcheck disable=SC2162
read -p "Press enter to continue"
