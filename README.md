## UC San Diego Assignments
 Assignments from Object Oriented Programming in Java course of UC San Diego
 
## About the repository
 This repository contains a menu to select the assignment to visualize. The list of assignments is:
 1. **Adjacent maps:** Displays two maps using `MicrosoftHybridProvider`, one map displays the location of UC San Diego,
 the other one displays the location of my hometown (Miranda - Venezuela)
 
## Built with
1. [Java with JDK 14](https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html)

## Resources
1. [Processing 2.x](http://download.processing.org/processing-2.2.1-windows64.zip)
2. [Unfolding Maps](https://github.com/tillnagel/unfolding/releases/download/v0.9.6/Unfolding_for_processing_0.9.6.zip)
3. [Apache Logging Service](https://logging.apache.org/log4j/1.2/download.html)
4. [IntelliJ IDEA Ultimate](https://www.jetbrains.com/es-es/idea/download/#section=windows)

## Run the program
1. Clone the repo
2. Open the project's folder with IntelliJ IDEA or Eclipse IDE
3. Include the JARs that are in the `/jar` directory

## Issues
1. **Black margin at the top and right:** This is a bug due to a problem with the graphic card, if you are using an NVIDIA
graphic card adjust the resolution in the control panel.

## Notes
 1. The program displays the maps using OPENGL renderer, it was built with a computer that uses NVIDIA GTX1650 graphic card
 if you have a weak graphic card I'd recommend using Java 2D Renderer, you can tell the program to use this by removing
 the third parameter in `size(this.width, this.height, OPENGL)`, without third parameter it will use Java 2D Renderer by
 default.
 
 2. The program is using `MicrosfotHybridProvider` as map provider instead of Google because the Google provider
 accepts a very small amount of requests per day and the rest of providers do not work at the moment
 
## Test
 This program was tested in the following JDK versions
 
 | Version  |  Result      |
 |----------|--------------|
 |  JDK 15  | Doesn't work |
 |  JDK 14  |    Works     |
 |  JDK 8   |    Works     |