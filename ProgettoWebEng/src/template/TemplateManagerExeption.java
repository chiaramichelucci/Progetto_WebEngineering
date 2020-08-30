package template;

public class TemplateManagerExeption extends Exception {

	public TemplateManagerExeption(String message) {
        super(message);
    }

    public TemplateManagerExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public TemplateManagerExeption(Throwable cause) {
        super(cause);
    }
	
}
