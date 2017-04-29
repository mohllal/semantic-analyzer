package semantic;

public enum ErrorType {
	//Undeclared variable error
	NO_DECLARATION,
	
	//Multiple declaration of variable in a scope error
	MULTIPLE_DECLARATION,
	
	//Type mismatch errors
	FLOAT_INT_CASTING, //float to int error
	FLOAT_CHAR_CASTING, //float to char error
	FLOAT_BOOLEAN_CASTING, //float to boolean error
	BOOLEAN_INT_CASTING, //boolean to int error
	BOOLEAN_FLOAT_CASTING, //boolean to float error
	BOOLEAN_CHAR_CASTING, //boolean to char error
	INT_BOOLEAN_CASTING, //int to boolean error
	INT_CHAR_CASTING, //int to char error
	CHAR_FLOAT_CASTING, //char to float error
	CHAR_BOOLEAN_CASTING, //char to boolean error
	
	SINGLE_TO_ARRAY, //single variable to array error
	ARRAY_TO_SINGLE, //array to single variable error
	INVALID_CONDITION //invalid condition error
}
