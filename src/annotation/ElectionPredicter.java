/**
 * 
 */
package annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Stream;
import annotation.Candidate.Party;
import annotation.Candidate.Sex;

/**
 * This class contains a main method (so is runnable). It demonstrates how to use the @Candidate
 * annotation. The way to query a class for an annotation is by using Java Reflection. Each method
 * that uses the @Candidate annotation must have a name in the form "predict" + Office name. So,
 * {@link #predictSecretary()} and {@link #predictPresident()}.
 * 
 * @author Promineo
 *
 */
public class ElectionPredicter {

  /**
   * This method uses the @Candidate annotation to specify the candidate. The
   * {@link #extractCandidate()} method finds the annotation and the method name and prints the
   * candidate and office information. The method therefore must be named "predict" + the
   * candidate's office name. So, for method predictSecretary, the candidate's office is
   * "secretary".
   */
  @Candidate(firstName = "Barney", lastName = "Fitzgerald", sex = Sex.Male,
      party = Party.Libertarian)
  private void predictSecretary() {
    extractCandidate();
  }

  /**
   * This method uses the @Candidate annotation to specify the candidate. The
   * {@link #extractCandidate()} method finds the annotation and the method name and prints the
   * candidate and office information.
   */
  @Candidate(firstName = "Margie", lastName = "Young", party = Party.Green)
  private void predictPresident() {
    extractCandidate();
  }

  /**
   * This method uses the @Candidate annotation to specify the candidate. The
   * {@link #extractCandidate()} method finds the annotation and the method name. Since the method
   * name does not start with "predict", the extractCandidate() method throws an exception. This is
   * caught by the exception handler in {@link #predictResult(ElectionMethod)}.
   */
  @Candidate(firstName = "Bartholemew", lastName = "Bad", party = Party.Republican, sex = Sex.Male)
  private void electTreasurer() {
    extractCandidate();
  }

  /**
   * This method finds the method name using an exception stack trace. In this, it assumes that the
   * method that calls this method calls it directly with no intervening methods. From the method
   * name, the candidate's office is extracted. The @Candidate annotation is then queried for the
   * candidate information.
   * 
   * @throws IllegalStateException Thrown if the caller's method name does not start with "predict".
   */
  private void extractCandidate() {
    /*
     * Create an exception and obtain the stack trace. The first element (position 0) in the stack
     * trace array is the method at which the exception is created (this method). The next element
     * (position 1) is the method that called this method. The stack trace elements contain the
     * method name, which gives this method the name of its caller.
     */
    Exception ex = new Exception();
    String methodName = ex.getStackTrace()[1].getMethodName();

    /* If the caller method name does not start with "predict", throw an exception. */
    if(!methodName.startsWith("predict")) {
      throw new IllegalStateException("Method name " + methodName + " must start with 'predict'!");
    }

    /*
     * The method name must be "predict" + office name. Pull the candidate's office out of the
     * method name.
     */
    String office = methodName.substring(7).toLowerCase();

    /*
     * Use Java Reflection to get the method object that describes the method with the given name.
     */
    try {
      Method method = this.getClass().getDeclaredMethod(methodName);

      /* Extract the @Candidate annotation from the method object. */
      Candidate candidate = method.getAnnotation(Candidate.class);

      if(Objects.isNull(candidate)) {
        throw new IllegalStateException(
            "Method " + methodName + " does not have the @Candidate annotation.");
      }

      /* Format a message with the candidate's information and party affiliation. */
      String msg = String.format("%s: The %s is %s %s (%s) of the %s party!", methodName, office,
          candidate.firstName(), candidate.lastName(), candidate.sex(), candidate.party());

      System.out.println(msg);
    }
    catch (NoSuchMethodException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * This method calls the method that is passed as the parameter.
   * 
   * @param method {@link ElectionMethod} is a functional interface. As such, it has a single
   *        method: {@link ElectionMethod#predict()}. The predict() method takes no parameters and
   *        returns nothing. When the predict() interface method is called, it calls the method
   *        passed by a Lambda expression or a method reference, as long as the method in the
   *        parameter takes no parameters and returns nothing.
   */
  private void predictResult(ElectionMethod method) {
    try {
      /*
       * This calls the method on the functional interface, which results in the method passed as a
       * Lambda expression is invoked. This is a little confusing but if predictPresident is passed
       * as a Lambda expression to this method, the predictPresident method is actually called here.
       */
      method.predict();
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * This method uses Java Reflection to retrieve all the methods in this (ElectionPredicter) class.
   * It then creates a Stream of Method objects and filters out any methods that do not have the
   * Candidate annotation. It filters out methods that have parameters and return a non-void result.
   * It then sorts the methods by method name and converts the result to a List of Method. Finally,
   * for each method, {@link #predictResult(ElectionMethod)} is called. It is passed the method as
   * an {@link ElectionMethod} functional interface.
   */
  private void predictResults() {
    /*
     * getClass().getDeclaredMethods() returns an array of Method objects via Java Reflection that
     * represent all the methods of this class.
     */
    // @formatter:off
    Stream.of(getClass().getDeclaredMethods()) // <-- convert the array of Method to a Stream
        /* Filter out any methods that do not have the @Candidate annotation. */
        .filter(m -> Objects.nonNull(m.getAnnotation(Candidate.class)))

        /* Filter out any methods that takes one or more parameters. */
        .filter(m -> m.getParameterCount() == 0)

        /* Filter out any methods that do not return null. */
        .filter(m -> m.getReturnType().getName().equals("void"))

        /* Sort the Stream by method name, ascending. */
        .sorted((m1, m2) -> m1.getName().compareTo(m2.getName()))

        /* Convert the Stream of Method objects to a List of sorted Method objects. */
        .toList()
            /*
             * For each Method in the list, call predictResult() and pass a Lambda expression that 
             * takes no parameters and returns nothing. This meets the requirements of the 
             * ElectionMethod functional interface method predict(). Since invokeMethod is passed
             * as a Lambda expression, invoke method isn't actually called until predictResult calls
             * it by invoking the functional interface method.
             */
            .forEach(m -> predictResult(() -> invokeMethod(m)));
    // @formatter:on
  }

  /**
   * This method invokes the Method that is passed as a parameter.
   * 
   * @param m The Java Reflection Method object, which is called by this method. Each method called
   *        does two things: it is annotated by the Candidate annotation, and it calls
   *        {@link #extractCandidate()}.
   */
  private void invokeMethod(Method m) {
    try {
      /*
       * Call the method using this object. So, for example, if the Method parameter represents the
       * predictSecretary method, predictSecretary is called.
       */
      m.invoke(this, (Object[]) null);
    }
    catch (Exception e) {
      logException(m.getName(), e);
    }
  }

  /**
   * This method logs any thrown exception.
   */
  private void logException(String methodName, Exception e) {
    if(e instanceof InvocationTargetException ite
        && ite.getCause() instanceof IllegalStateException ise) {
      System.out.println(methodName + ": " + ise.getMessage());
    }
    else {
      System.out.println(e.toString());
    }
  }

  /**
   * This is the entry point to the Java application. It creates an ElectionPredicter object and
   * calls the {@link #predictResults()} on it.
   * 
   * @param args Unused.
   */
  public static void main(String[] args) {
    new ElectionPredicter().predictResults();
  }

}
