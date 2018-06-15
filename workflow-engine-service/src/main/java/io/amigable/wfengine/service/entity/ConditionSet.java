package io.amigable.wfengine.service.entity;

import java.util.ArrayList;

public class ConditionSet {

    private int transitionConditionId;
    private int order;
    private int setId;
    private int parentSetId;
    private ArrayList<ConditionSet> childs;
    private Condition conditionEval;
    private String operator;

    public ConditionSet(){
        childs = new ArrayList<ConditionSet>();
    }


    public int getTransitionConditionId() {
        return transitionConditionId;
    }

    public void setTransitionConditionId(int transitionConditionId) {
        this.transitionConditionId = transitionConditionId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public ArrayList<ConditionSet> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<ConditionSet> childs) {
        this.childs = childs;
    }

    public Condition getConditionEval() {
        return conditionEval;
    }

    public void setConditionEval(Condition conditionEval) {
        this.conditionEval = conditionEval;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getParentSetId() {
        return parentSetId;
    }

    public void setParentSetId(int parentSetId) {
        this.parentSetId = parentSetId;
    }

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    private boolean evaluate(String operator, Object a, Object b){
        boolean resultValue = false;

        switch (operator.toUpperCase()){
            case "AND":
                resultValue = (boolean)a && (boolean)b;
                break;
            case "OR":
                resultValue = (boolean)a || (boolean)b;
        }
        return resultValue;
    }

    private String print(ConditionSet root){
        String returnSTR = "(";
        for (ConditionSet s : root.childs) {
            if(s.getConditionEval() != null){
                returnSTR+= "(" + s.getConditionEval().toString() + ")" +  (s.getOperator() != null ? " "+s.getOperator()+" ": "");
            }else{
                returnSTR += this.print(s);
            }
        }
        returnSTR +=")" + (root.getOperator() != null ? " "+root.getOperator()+" ": "");
        return returnSTR;
    }

    @Override
    public String toString(){
        return this.print(this);
    }


}
