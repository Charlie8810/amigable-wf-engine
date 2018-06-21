package io.amigable.wfengine.service.entity;

/**
 * Created by capra on 20-06-2018.
 */
public enum ResultCode {
    ErrorInvalidSchema (0),
    Success (1),
    ErrorEvaluatingConditions (2),
    ErrorProcessNotExistsOrNotEnabled (3),
    ErrorInstanceNotExistsOrNotEnabled (4),
    ErrorInstanceStepNotEnabled (5),
    ErrorOnStartInstance (6),
    ErrorOnCompleteTask (7),
    ErrorOnAbortInstance (8),
    ErrorEventNotValid (9),
    ErrorOnResumeInstance (10),
    ErrorOnActivateTaskInstance (11),
    ErrorInDataBaseConnection (12),
    TaskRulesWithoutAdvanceQualification (13),
    Exception (14);


    private final int resultCode;

    ResultCode(int resultCode){
        this.resultCode = resultCode;
    }
    private int resultCode(){
        return this.resultCode;
    }

    public String formatedResultCode(){
        return String.format("%03d", this.resultCode);
    }

    public String resultDescription(String location){
        String[] results = new String[15];
        if(location.contains("es")){
            results[0] = "Esquema Invalido";
            results[1] = "Proceso Ejecutado Correctamente";
            results[2] = "Error al Evaluar Condiciones";
            results[3] = "Proceso no Existe o no Esta Habilitado";
            results[4] = "Instancia no Existe o no Esta Habilitada";
            results[5] = "Etapa no Asociada a la Instancia o No Existe";
            results[6] = "Error al Iniciar la Instancia";
            results[7] = "Error al Completar la Etapa";
            results[8] = "Error al Abortar la Instancia";
            results[9] = "Evento de entrada no valido";
            results[10] = "Error al reactivar instancia";
            results[11] = "Error al activar tarea instancia";
            results[12] = "reglas asociadas a la tarea sin calificacion de avance";
            results[13] = "Error de conecccion a base de datos";
            results[14] = "Excepcion";
        }
        return results[this.resultCode];
    }



}
