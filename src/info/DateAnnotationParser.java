package info;

/**
 * Parse date annotation
 * @author Rex
 *
 */
public class DateAnnotationParser {
	
	private int myNumProblems;
	
	public DateAnnotationParser() {
		myNumProblems = 0;
	}
	
    public void parse(Class<?> clazz) throws Exception {
    	if (clazz.isAnnotationPresent(Date.class)) {
    		myNumProblems++;
    		Date dateAnnotation = clazz.getAnnotation(Date.class);
    		String date = dateAnnotation.value();
    		//TODO: parse dates haha
    	}
    }
    
    public int getNumProblems() {
    	return myNumProblems;
    }
}
