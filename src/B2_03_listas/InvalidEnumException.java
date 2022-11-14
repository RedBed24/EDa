
/*********************************************************************
* @name InvalidEnumException
* 
* @authors DJS - B2 - 03
* 
* @description Excepción lanzada cuando se selecciona el ranking de gustos personales y
* 			   el usuario introduce una provincia o tipo de establecimiento inválidos.
***********************************************************************/
package B2_03_listas;

public class InvalidEnumException extends Exception {

	public InvalidEnumException() {
	}

	public InvalidEnumException(String message) {
		super(message);
	}

	public InvalidEnumException(Throwable cause) {
		super(cause);
	}

	public InvalidEnumException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidEnumException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
