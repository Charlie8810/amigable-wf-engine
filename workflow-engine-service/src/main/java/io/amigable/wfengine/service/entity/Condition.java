package io.amigable.wfengine.service.entity;

import java.sql.Date;

public class Condition {

    private String attributeName;
    private Object a;
    private Object b;
    private String operator;

    public Object getA() {
        return a;
    }

    public void setA(Object a) {
        this.a = a;
    }

    public Object getB() {
        return b;
    }

    public void setB(Object b) {
        this.b = b;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return a.toString() + " " + this.operator + " " + b.toString();
    }

    public String toString2() {
        return attributeName  + " " + this.operator + " " + b.toString();
    }

    public Boolean getResult() {
        return evaluate(this.getOperator(),this.getA(), this.getB());
    }

    private boolean evaluate(String operator, Object x, Object y){

        boolean returnValue = false;
        switch(operator){
            case "=":
                returnValue = (x==y);
                break;
            case ">":
                if(x instanceof Integer && y instanceof Integer){
                    returnValue = ((int)x > (int)y);
                }
                else if(x instanceof Character && y instanceof Character){
                    returnValue = ((char)x > (char)y);
                }
                else if(x instanceof Double && y instanceof Double){
                    returnValue = ((double)x > (double)y);
                }
                else if(x instanceof java.sql.Date && y instanceof java.sql.Date){
                    returnValue = ((java.sql.Date) x).compareTo((java.sql.Date)y) > 0;
                }
                break;
            case ">=":
                if(x instanceof Integer && y instanceof Integer){
                    returnValue = ((int)x >= (int)y);
                }
                else if(x instanceof Character && y instanceof Character){
                    returnValue = ((char)x >= (char)y);
                }
                else if(x instanceof Double && y instanceof Double){
                    returnValue = ((double)x >= (double)y);
                }
                else if(x instanceof java.sql.Date && y instanceof java.sql.Date){
                    returnValue = ((java.sql.Date) x).compareTo((java.sql.Date)y) >= 0;
                }
                break;
            case "<":
                if(x instanceof Integer && y instanceof Integer){
                    returnValue = ((int)x < (int)y);
                }
                else if(x instanceof Character && y instanceof Character){
                    returnValue = ((char)x < (char)y);
                }
                else if(x instanceof Double && y instanceof Double){
                    returnValue = ((double)x < (double)y);
                }
                else if(x instanceof java.sql.Date && y instanceof java.sql.Date){
                    returnValue = ((java.sql.Date) x).compareTo((java.sql.Date)y) < 0;
                }
                break;
            case "<=":
                if(x instanceof Integer && y instanceof Integer){
                    returnValue = ((int)x <= (int)y);
                }
                else if(x instanceof Character && y instanceof Character){
                    returnValue = ((char)x <= (char)y);
                }
                else if(x instanceof Double && y instanceof Double){
                    returnValue = ((double)x <= (double)y);
                }
                else if(x instanceof java.sql.Date && y instanceof java.sql.Date){
                    returnValue = ((java.sql.Date) x).compareTo((java.sql.Date)y) <= 0;
                }
                break;
            case "<>":
                returnValue = (x != y);
        }
        return returnValue;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
