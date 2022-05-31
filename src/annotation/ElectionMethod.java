/**
 * 
 */
package annotation;

/**
 * This defines a functional interface (with a single method that is not static, default, or
 * extended from Object).
 * 
 * @author Promineo
 *
 */
@FunctionalInterface
public interface ElectionMethod {
  /**
   * This declares a single method that takes no parameters and returns nothing. When a Lambda
   * expression or method reference is passed as a parameter of type ElectionMethod, it must take no
   * parameters and return nothing. Then, the predict() method may be called, which will call the
   * method reference or Lambda expression. This is functional programming. Note that the parameter
   * value (Lambda or method reference) does not need to <em>have</em> a predict() method. But the
   * Lambda must reference a method that takes no parameters and returns nothing. Here's an example:
   * 
   * <pre>
   * <code>
   * private void doSomething() { // Takes no parameters and returns nothing.
   *   System.out.println("Doing something.");
   * }
   * 
   * private void doNothing() {   // Takes no parameters and returns nothing.
   *   System.out.println("Doing nothing.");
   * }
   * 
   * private void doIt(ElectionMethod meth) {
   *   meth.predict(); // When predict() is called, the method in the parameter list is called.
   * }
   * 
   * private void doItAll() {
   *   doIt(() -> doSomething()); // Pass the Lambda to doIt() - prints "Doing something."
   *   doIt(this::doNothing);     // Pass the reference to doIt() - prints "Doing nothing."
   * }
   * </code>
   * </pre>
   */
  void predict();
}
