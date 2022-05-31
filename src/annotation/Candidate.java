/**
 * 
 */
package annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This is a Java annotation. An annotation is an object that provides metadata. This metadata can
 * be applied at the class, method, field, parameter, constructor, or local variable level. An
 * annotation can even be applied as an annotation parameter value.
 * <p>
 * What is metadata? Whatever the annotation defines. See below for more details.
 * <p>
 * Metadata provided with annotations is not made available to classes by default. Instead, a class
 * must query another class to see if it has a specific annotation. You can get a list of all class
 * annotations, but there is no universal mechanism that provides metadata about the metadata. In
 * other words, you could get a list of all annotations, but you won't know what they mean unless
 * you know what it does in advance.
 * <p>
 * Annotations are declared with the @interface keyword. The name of the annotation class becomes
 * the annotation. Put an at sign in front of the annotation class name to add the annotation
 * metadata. For example, this annotation is declared as @interface Candidate. To add this
 * annotation, use @Candidate.
 * <p>
 * Annotations that are not in the same package as the class in which they are used must have an
 * import statement.
 * 
 * @author Promineo
 *
 */
/*
 * Annotations can specify a retention strategy. SOURCE means that the annotation is only available
 * in the source (text) file and is not placed into the .class file. An example of this
 * is @Override. This annotation is only used by the IDE to alert the programmer if a method
 * override is not being used correctly. CLASS is the default retention strategy. This annotation is
 * written to the .class file but is not available in the runtime environment. The final retention
 * strategy is RUNTIME. With this strategy, the annotation is available in the runtime environment,
 * which makes it accessible to classes in the running application.
 */
@Retention(RUNTIME)

/*
 * Annotations also have targets. A target is the level at which the annotation is valid. For
 * example, a METHOD target cannot be applied at the class or parameter level. There are many
 * targets but you probably won't use all of them. Here are the most common: TYPE means that the
 * annotation is applied at the class level. Spring Boot's @SpringBootApplication is an example of a
 * class-level annotation. FIELD is applied to instance variables. Spring's @Autowired annotation is
 * an example of a FIELD-level annotation. METHOD is applied to class methods.
 * Spring's @PostConstruct is an example of a method-level annotation. You may also encounter
 * PARAMETER-level annotations that apply to method parameters, CONSTRUCTOR-level annotations that
 * apply to class constructors or LOCAL_VARIABLE-level annotations that apply to local variables
 * within methods. There are other target types but they are seen less often.
 */
@Target({METHOD})

/*
 * Like interfaces, annotations can have access-level modifiers. It is most common to declare
 * annotations with public access but this is not required. The @interface keyword specified that
 * this construct is an annotation. Candidate is the name of the annotation. Use it in code by
 * specifying @Candidate.
 */
public @interface Candidate {
  /**
   * Like other Java types, enums can be declared internally. For readability, I have dispensed with
   * the Java convention of making enum values uppercase.
   */
  public enum Party {
    Republican, Democrat, Green, Libertarian
  }

  /**
   * It is not my wish to engender debate on the difference between gender and sex. I am using a
   * simplified genetic definition of sex to refer to specific combinations of X and Y chromosomes.
   * In this definition, I am using the most common forms: males have an X and a Y chromosome and
   * females have two X chromosomes. I recognize that DSDs exist but do not include them here. In
   * this definition, sex does not imply sexual orientation or gender identity. If you want to have
   * this discussion, please feel free to have it elsewhere.
   */
  public enum Sex {
    Male, Female
  }

  /**
   * Annotation parameters are specified using methods within the annotation. The party() method
   * means that the annotation can have a party = Party.xxx parameter. The parameter value supplies
   * the metadata for the target. So, you can declare: @Candidate(party = Party.Green). When the
   * annotation is queried by another class, the parameter type is Party and the value is
   * Party.Green. Note that annotation parameters may specify a default value. If no default value
   * is specified, as is the case here, the parameter is required.
   */
  public Party party();

  /**
   * This parameter specifies the first name of the candidate. Use it like
   * this: @Candidate(firstName = "Fred"). This parameter is required.
   */
  public String firstName();

  /**
   * This parameter specifies the last name of the candidate. Use it like this: @Candidate(lastName
   * = "Hondo"). This parameter is required.
   */
  public String lastName();

  /**
   * This parameter specifies the sex of the candidate. It is optional as a default value is
   * supplied (Female).
   */
  public Sex sex() default Sex.Female;
}
