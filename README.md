# Annotations and reflection project

This project takes a look at annotations - what they are and how to use them. Along the way, it uses Java Reflection to examine classes and print a report.

## How to proceed

Start with the annotation: Candidate.java. Read through the comments. When you've gotten this annotation class figured out, turn to ElectionPredicter.java. This class shows how to use the Candidate annotation. There is also a functional interface (ElectionMethod.java) that has a part to play.

## How it works

The ElectionPredictor class has the main() method. It calls the instance method, predictResults(). predictResults() calls predictResult() several times, one for each result method.

### Result methods

The result method names must start with "predict". If the method name does not start with "predict", an exception is thrown by extractCandidate(), which all result methods must call. A result method also must have the @Candidate annotation. If it does not, an exception is thrown. These are the result methods:

* **predictPresident** - The method name is correct and it has an @Candidate annotation.
* **predictSecretary** - The method name is correct and it has an @Candidate annotation.
* **electTreasurer** - The method name is incorrect.
* **predictCoroner** - The method name is correct but there is no @Candidate annotation.

### The predictResult() method

This method requires a single parameter of type ElectionMethod. ElectionMethod is a functional interface with a single method: predict(). As such, a Lambda expression or method reference can be passed to predictResult. The only requirement is that the Lambda expression or referenced method must have no parameters and must return nothing. All of the result methods described above match these requirements.

### The predictResults() method

This method simply calls predictResult() once for each of the result methods. A mix of Lambda expressions and method references are used for the four result methods.

### The extractCandidate() method

The extractCandidate() method is called by each result method. It works as follows:

1. The caller method name is obtained from stack trace information.
1. If the caller method name does not start with "predict", throw an exception.
1. The candidate's office is obtained from the method name after "predict" (i.e., method predictPresident yields the office of president).
1. A Method object is obtained by using Java Reflection on the ElectionPredicter class.
1. The @Candidate annotation is obtained from the Method object. If the method does not have an @Candidate annotation an exception is thrown.
1. A message is formatted and printed using information in the @Candidate annotation.