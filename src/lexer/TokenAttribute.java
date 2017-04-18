package lexer;

public class TokenAttribute {
	private int intVal; // int value of the token
	private float floatVal; // float value of the token
	private char charVal; // char value of the token
	private boolean booleanVal; // boolean value of the token
	private String idVal; // id of the token

	public TokenAttribute() {}

	// construct TokenAttribute with an int value
	public TokenAttribute(int intVal){
		this.intVal = intVal;
	}

	// construct TokenAttribute with a float value
	public TokenAttribute(float floatVal){
		this.floatVal = floatVal;
	}

	// construct TokenAttribute with a char value
	public TokenAttribute(char charVal){
		this.charVal = charVal;
	}

	// construct TokenAttribute with a boolean value
	public TokenAttribute(boolean booleanVal){
		this.booleanVal = booleanVal;
	}

	// construct TokenAttribute with an id
	public TokenAttribute(String idVal){
		this.idVal = idVal;
	}

	public int getIntVal() {
		return intVal;
	}

	public void setIntVal(int intVal) {
		this.intVal = intVal;
	}

	public float getFloatVal() {
		return floatVal;
	}

	public void setFloatVal(float floatVal) {
		this.floatVal = floatVal;
	}

	public char getCharVal() {
		return charVal;
	}

	public void setCharVal(char charVal) {
		this.charVal = charVal;
	}

	public boolean getBooleanVal() {
		return booleanVal;
	}
	
	public void setBooleanVal(boolean booleanVal) {
		this.booleanVal = booleanVal;
	}

	public String getIdVal() {
		return idVal;
	}
	
	public void setIdVal(String idVal) {
		this.idVal = idVal;
	}
}
