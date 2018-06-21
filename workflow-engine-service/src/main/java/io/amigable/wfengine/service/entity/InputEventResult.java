package io.amigable.wfengine.service.entity;

/**
 * Created by capra on 19-06-2018.
 */
public class InputEventResult<T> {

    private String resultMessage;
    private String resultCode;
    private T resultObject;


    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public T getResultObject() {
        return resultObject;
    }

    public void setResultObject(T resultObject) {
        this.resultObject = resultObject;
    }

    @Override
    public String toString(){
        return resultCode + " - " + resultMessage;
    }

    public void parseResultCode(ResultCode input, String lang){
        this.setResultCode(input.formatedResultCode());
        this.setResultMessage(input.resultDescription(lang));
    }
}
