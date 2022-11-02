
/*********************************************************************
* @name InvalidPatientInfoException
* 
* @authors DJS - B2 - 03
* 
* @description Excepción lanzada cuando la información de los pacientes no es correcta.
***********************************************************************/
package Colas;

@SuppressWarnings("serial")
public class InvalidPatientInfoException extends Exception {

	public InvalidPatientInfoException() {
	}

	public InvalidPatientInfoException(String message) {
		super(message);
	}

	public InvalidPatientInfoException(Throwable cause) {
		super(cause);
	}

	public InvalidPatientInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPatientInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
