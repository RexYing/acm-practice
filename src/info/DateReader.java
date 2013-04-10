package info;

/**
 * 
 * @author Rex
 *
 */
public class DateReader {

	public static void main(String[] args) {
        DateAnnotationParser dateParser = new DateAnnotationParser();
        try {
			dateParser.parse(midatlantic.year2005.ContextFreeClock_F.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println(dateParser.getNumProblems());
        // you can use also Class.forName 
        // to load from file system directly!
	}

}
