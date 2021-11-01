def main():

    print("Welcome to Big Data Processing Application\nPlease type the number that corresponds to which application you would like to run:\n1. Apache Hadoop\n2. Apache Spark\n3. Jupyter Notebook\n4. SonarQube and SonarScanner")
    val = int(input("Type the number here > "))
    if(val == 1):
        print("Apache Hadoop")
    elif(val == 2):
        print("Apache Spark")
    elif(val == 3):
        print("Jupyter Notebook")
    elif(val == 4):
        print("Sonar Scanner")
    else:
        print("Invalid input")

main()