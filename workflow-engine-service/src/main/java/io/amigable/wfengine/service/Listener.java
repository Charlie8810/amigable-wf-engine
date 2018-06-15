package io.amigable.wfengine.service;

import io.amigable.wfengine.service.entity.*;
import javafx.concurrent.Task;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;


public class Listener {


    ConnectionManager dbAccess;

    public Listener(ConnectionManager inDBAccess) throws SQLException{
        this.dbAccess = inDBAccess;
    }

    public void activateTransitionStep(int processTransitionId, int processInstanceId, int processDefinitionId) throws SQLException, Exception{

        /*Assurance of transitionStep is not Activated*/
        if(this.isActiveTransitionStep(processTransitionId,processInstanceId,processDefinitionId)){
            return;
        }

        TransitionStep transitMetaData = this.transitionStepMetaData(processTransitionId, processDefinitionId);

        /*If is end step type, do close de processInstance*/
        if(transitMetaData.getStepTypeId() == StepType.END){
            this.updateProcessInstanceStatus(processInstanceId, InstanceState.TERMINATED);
        }else{
            /*Insert task */
            TaskEntity taskTransit = new TaskEntity();
            taskTransit.setProcessInstanceId(processInstanceId);
            taskTransit.setProcessTransitionId(processTransitionId);
            taskTransit.setTaskStateId(TaskState.ACTIVATED);
            taskTransit.setResponsibleType(transitMetaData.getResponsibleType());
            taskTransit.setResponsibleId(transitMetaData.getResponsibleId());
            this.addTransitionTask(taskTransit);

            /*Fire Event on Init*/

            /*Check Step Conditions hint:processTransitionCondition*/
            if(this.evaluateTransitionStepConditions(processTransitionId,processDefinitionId,processInstanceId)){
                TransitionStep nextTransitMetaData = this.findNextStep(processTransitionId, processDefinitionId);
                this.activateTransitionStep(nextTransitMetaData.getTransitionStepId(), processInstanceId, processDefinitionId);
            }
        }


    }

    public boolean isActiveTransitionStep(int processTransitionId, int processInstanceId,int processDefinitionId ) throws SQLException {
        String query = String.format("CALL sp_isActiveTransitionStep(%1$d, %2$d, %3$d)", processTransitionId, processInstanceId, processDefinitionId);
        ResultSet rs = this.dbAccess.executeQuery(query);
        return rs.getBoolean("isActiveTransitionStep");
    }

    public void updateProcessInstanceStatus(int processInstanceId, int instanceStatusId) throws SQLException{
        String query = String.format("CALL sp_UpdateProcessInstanceStatus(%1$d, %2$d)", processInstanceId, instanceStatusId);
        this.dbAccess.executeQuery(query);
    }

    public void addTransitionTask(TaskEntity inTask) throws SQLException{
        String query = String.format("CALL insertTransitionTask(%1$d, %2$d, %3$d, %4$d, '%5$s')",
                inTask.getProcessInstanceId(),
                inTask.getProcessTransitionId(),
                inTask.getTaskStateId(),
                inTask.getResponsibleId(),
                inTask.getResponsibleType()
        );
        this.dbAccess.executeQuery(query);
    }

    public TransitionStep transitionStepMetaData(int processTransitionId, int processDefinitionId) throws SQLException{
        String qryTransitionStepData = String.format("CALL sp_transitionStepMetaData(%1$d, %2$d)", processTransitionId, processDefinitionId);
        ResultSet rsTransitionStepData = this.dbAccess.executeQuery(qryTransitionStepData);

        TransitionStep ptran = new TransitionStep();
        ptran.setTransitionStepId(rsTransitionStepData.getInt("transitionStepId"));
        ptran.setProcessDefinitionId(rsTransitionStepData.getInt("processDefinitionId"));
        ptran.setStepTypeId(rsTransitionStepData.getInt("stepTypeId"));
        ptran.setStepTypeName(rsTransitionStepData.getString("stepTypeName"));
        ptran.setStepName(rsTransitionStepData.getString("stepName"));
        ptran.setResponsibleType(rsTransitionStepData.getString("responsibleType"));
        ptran.setResponsibleId(rsTransitionStepData.getInt("responsibleId"));
        ptran.setSlaTypeId(rsTransitionStepData.getInt("slaTypeId"));
        ptran.setSlaValue(rsTransitionStepData.getInt("slaValue"));

        ptran.setAssignedRoleId(rsTransitionStepData.getInt("assignedRoleId"));
        ptran.setAssignedRoleName(rsTransitionStepData.getString("assignedRoleName"));
        ptran.setAssignedUserId(rsTransitionStepData.getInt("assignedUserId"));
        ptran.setAssignedUserName(rsTransitionStepData.getString("assignedUserName"));
        return ptran;
    }

    public Object getAttributeItemValue(int processDefinitionId, int processInstanceId, String attributeSysName) throws SQLException {
        String query = String.format("CALL sp_getAttributeItemValueBySysName(%1$d, %2$d, '%3$s')", processDefinitionId, processInstanceId, attributeSysName);
        ResultSet rs = this.dbAccess.executeQuery(query);
        //'INTEGER','TEXT','DATE','DECIMAL','BOOLEAN'
        Class returnType = String.class;
        switch(rs.getString("rawValue"))
        {
            case "INTEGER":
                returnType = Integer.class;
                break;
            case "TEXT":
                returnType = String.class;
                break;
            case "DATE":
                returnType = java.sql.Date.class;
                break;
            case "DECIMAL":
                returnType = Double.class;
                break;
            case "BOOLEAN":
                returnType = Boolean.class;
                break;
        }
        return rs.getObject("rawValue", returnType);
    }

    public Object getAttributeItemValue(int processDefinitionId, int processInstanceId, int attributeId) throws SQLException {
        String query = String.format("CALL sp_getAttributeItemValueById(%1$d, %2$d, %3$d)", processDefinitionId, processInstanceId, attributeId);
        ResultSet rs = this.dbAccess.executeQuery(query);
        Class returnType = this.setDataType(rs.getString("dataType"));
        return rs.getObject("rawValue", returnType);

    }

    public boolean evaluateTransitionStepConditions(int processTransitionId, int processDefinitionId, int processInstanceId) throws SQLException, Exception{
        String qry = String.format("CALL sp_transitionStepConditionList(%1$d, %2$d, %3$d)", processTransitionId, processInstanceId, processDefinitionId);
        ResultSet rs = this.dbAccess.executeQuery(qry);
        ConditionSet rootSet = new ConditionSet();
        rootSet.setSetId(4);
        while(rs.next()){
            String kind = rs.getString("kind");
            String parentKind = rs.getString("parentKind");
            if(kind.equals("expression")){
                int parentWrapper = rs.getInt("parentSetId");
                Condition conditional = new Condition();
                conditional.setA(rs.getObject("attributeValue", this.setDataType(rs.getString("dataType"))));
                conditional.setB(rs.getObject("conditionEvalValue", this.setDataType(rs.getString("dataType"))));
                conditional.setAttributeName(rs.getString("attributName"));
                conditional.setOperator(rs.getString("conditionOperator"));

                ConditionSet cluster = new ConditionSet();
                cluster.setSetId(rs.getInt("id"));
                cluster.setParentSetId(rs.getInt("parentSetId"));
                cluster.setOperator(rs.getString("setOperator"));
                cluster.setConditionEval(conditional);

                if(parentKind.equals("wrapper")){
                    ConditionSet parent = this.findWrapperInChils(rootSet,parentWrapper);
                    parent.getChilds().add(cluster);
                }else if(parentKind.equals("root")){
                    rootSet.getChilds().add(cluster);
                }


            }else if(kind.equals("wrapper")){
                ConditionSet cluster = new ConditionSet();
                cluster.setSetId(rs.getInt("id"));
                cluster.setParentSetId(rs.getInt("parentSetId"));
                cluster.setOperator(rs.getString("setOperator"));


                if(parentKind.equals("wrapper")){
                    ConditionSet parent = this.findWrapperInChils(rootSet,cluster);
                    parent.getChilds().add(cluster);
                }else if(parentKind.equals("root")){
                    rootSet.getChilds().add(cluster);
                }

            }else{
                /*fire error*/
                throw new Exception("Error charly-500: te entry not in expression or wrapper");
            }
        }
        System.out.println("|FORMULA======================================" );
        System.out.println(this.parseJavaScriptLang(rootSet.toString()));

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        boolean result = false;
        try{

            result = (boolean) engine.eval(this.parseJavaScriptLang(rootSet.toString()));
        }catch (ScriptException sEx){
            result = false;
            //System.out.println("Error: " + sEx.getMessage());
        }
        System.out.println("|RESULTADO======================================" );
        System.out.println(result);


        return result;
    }

    private TransitionStep findNextStep(int processTransitionId, int processDefinitionId) throws SQLException{
        String qry = String.format("CALL sp_transitionStepFindNext(%1$d, %2$d)", processTransitionId, processDefinitionId);
        ResultSet rs = this.dbAccess.executeQuery(qry);
        int nextProcessTransitionId = rs.getInt("processTransitionId");
        return  this.transitionStepMetaData(nextProcessTransitionId, processDefinitionId);
    }

    private ConditionSet findWrapperInChils(ConditionSet rootSet, ConditionSet findSet){
        ConditionSet returnSet = null;
        for (ConditionSet set : rootSet.getChilds()) {
            if (set.getSetId() == findSet.getParentSetId()) {
                returnSet = set;
                break;
            }else{
                findWrapperInChils(set, findSet);
            }
        }
        return returnSet;
    }

    private ConditionSet findWrapperInChils(ConditionSet rootSet, int findParentSetId){
        ConditionSet returnSet = null;
        for (ConditionSet set : rootSet.getChilds()) {
            if (set.getSetId() == findParentSetId) {
                returnSet = set;
                break;
            }else{
                findWrapperInChils(set, findParentSetId);
            }
        }
        return returnSet;
    }

    private Class setDataType(String dataType){
        Class returnType = String.class;
        switch(dataType)
        {
            case "INTEGER":
                returnType = Integer.class;
                break;
            case "TEXT":
                returnType = String.class;
                break;
            case "DATE":
                returnType = java.sql.Date.class;
                break;
            case "DECIMAL":
                returnType = Double.class;
                break;
            case "BOOLEAN":
                returnType = Boolean.class;
                break;
        }
        return returnType;
    }

    private String parseJavaScriptLang(String input){
        return input.replace("AND","&&").replace("OR","||").replace(" = "," === ");
    }

}
