
/*********************************************************************
* @name InvalidPatientInfoException
* 
* @authors DJS - B2 - 03
* 
* @description Excepci�n lanzada cuando la informaci�n de los pacientes no es correcta.
***********************************************************************/
package B2_03_colas;

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
