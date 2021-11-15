import webbrowser
import os



def main():

    print("Welcome to Big Data Processing Application\nPlease type the number that corresponds to which application you would like to run:\n1. Apache Hadoop\n2. Apache Spark\n3. Jupyter Notebook\n4. SonarQube and SonarScanner")
    val = int(input("Type the number here > "))
    if(val == 1):
        print("Apache Hadoop")
    elif(val == 2):
        print("Apache Spark")
    elif(val == 3):
        print("Jupyter Notebook")
        webbrowser.open('http://localhost:8888/?token=7284adc13ac9eca5c7f8fa6ac3f09cf7a2be7d7484613609')
        os.system("docker run -p 8888:8888 jupyter/base-notebook")
    elif(val == 4):
        print("Sonar Scanner")
    else:
        print("Invalid input")

main()
