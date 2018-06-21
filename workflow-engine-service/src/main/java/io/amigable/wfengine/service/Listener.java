package io.amigable.wfengine.service;

import com.sun.jmx.snmp.internal.SnmpMsgProcessingSubSystem;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import io.amigable.wfengine.service.entity.*;
import javafx.concurrent.Task;
import jdk.nashorn.internal.runtime.regexp.RegExp;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.xml.transform.Result;


public class Listener {


    ConnectionManager dbAccess;

    public Listener(ConnectionManager inDBAccess) throws SQLException{
        this.dbAccess = inDBAccess;
    }

    public void activateTransitionStep(int processTransitionId, int processInstanceId, int processDefinitionId, int userId) throws SQLException, Exception{

        System.out.println("Activando Etapa");
        /*Assurance of transitionStep is not Activated*/
        if(this.isActiveTransitionStep(processTransitionId,processInstanceId,processDefinitionId)){
            System.out.println("Etapa ya Activa");
            return;
        }

        /*Obtain task metadata of transit*/
        TransitionStep transitMetaData = this.transitionStepMetaData(processTransitionId, processDefinitionId);

        /*If is end step type, do close de processInstance*/
        if(transitMetaData.getStepTypeId() == StepType.END){
            this.updateProcessInstanceStatus(processInstanceId, InstanceState.TERMINATED);
        }else{
            /*Insert task */
            /*TODO Revisar bien esto al parecer no tiene logica alguna*/
            TaskEntity taskTransit = new TaskEntity();
            taskTransit.setProcessInstanceId(processInstanceId);
            taskTransit.setProcessTransitionId(processTransitionId);
            taskTransit.setTaskStateId(TaskState.ACTIVATED);
            taskTransit.setResponsibleType(transitMetaData.getResponsibleType());
            taskTransit.setResponsibleId(transitMetaData.getResponsibleId());

            taskTransit.setId(this.addTransitionTask(taskTransit));

            /*Fire Event on Init*/

            /*Check Step Conditions*/
            ArrayList<ProcessTransitionConditionEntity> conditionList = this.listTransitionsConditions(processTransitionId, processDefinitionId);
            for(ProcessTransitionConditionEntity entity : conditionList){
                if(this.evaluateTransitionStepConditions(entity.getConditionSetId(),processDefinitionId,processInstanceId)){
                    this.activateTransitionStep(entity.getNextProcessTransitionId(), processInstanceId, processDefinitionId, userId);
                }
            }
        }
    }

    public ResultCode completeTransitionStep(int processTransitionId, int processInstanceId, int processDefinitionId, int taskId, int userId)throws SQLException, Exception{
        System.out.println("Completando Etapa");
        ResultCode retVal = ResultCode.Success;
        TransitionStep transitMetaData = this.transitionStepMetaData(processTransitionId, processDefinitionId);
        TaskEntity taskTransit = this.getTask(taskId);
        if(taskTransit.getProcessTransitionId() != processTransitionId){
            retVal = ResultCode.ErrorOnCompleteTask;
            throw new Exception("La tarea ingresada no concuerda con su asignacion de transito");
        }

        boolean isJoinStep = transitMetaData.getStepTypeId() == StepType.JUNCTION;

        /*todo generar codigo para evento completarEtapa here*/
        /*Check Step Conditions*/
        ArrayList<ProcessTransitionConditionEntity> conditionList = this.listTransitionsConditions(processTransitionId, processDefinitionId);
        int trueCount = 0;
        System.out.println(conditionList.size());
        for(ProcessTransitionConditionEntity entity : conditionList){
            System.out.println(entity.toString());
            boolean resultCondition = this.evaluateTransitionStepConditions(entity.getConditionSetId(),processDefinitionId,processInstanceId);
            if(resultCondition){
                trueCount++;
                if(entity.getNextProcessTransitionId() > 0){
                    this.activateTransitionStep(entity.getNextProcessTransitionId(), processInstanceId, processDefinitionId, userId);
                }
            }
        }
        if((isJoinStep && trueCount > 0) || (isJoinStep && conditionList.isEmpty())){
            this.updateTaskStatus(taskTransit.getId(), TaskState.TERMINATED);
        }else if(trueCount > 0 || conditionList.isEmpty()){
            this.updateTaskStatus(taskTransit.getId(), TaskState.TERMINATED, userId);
        }

        if(isJoinStep && !conditionList.isEmpty() && trueCount == 0){
            retVal  = ResultCode.TaskRulesWithoutAdvanceQualification;
        }
        return retVal;
    }

    private TaskEntity getTask(int processTransitionId, int processInstanceId,int processDefinitionId)throws SQLException{
        String query = String.format("CALL sp_getTaskByIds(%1$d, %2$d, %3$d);", processTransitionId, processInstanceId, processDefinitionId);
        ResultSet rs = this.dbAccess.executeQuery(query);
        TaskEntity retVal = new TaskEntity();
        if(rs.next()){
            retVal.setId(rs.getInt("id"));
            retVal.setProcessInstanceId(rs.getInt("processInstanceId"));
            retVal.setProcessTransitionId(rs.getInt("processTransitionId"));
            retVal.setTaskStateId(rs.getInt("taskStateId"));
            retVal.setResponsibleId(rs.getInt("responsibleId"));
            retVal.setResponsibleType(rs.getString("responsibleType"));
        }
        return retVal;
    }

    private TaskEntity getTask(int taskId)throws SQLException{
        String query = String.format("CALL sp_getTaskById(%1$d)", taskId);
        ResultSet rs = this.dbAccess.executeQuery(query);
        TaskEntity retVal = new TaskEntity();
        if(rs.next()){
            retVal.setId(rs.getInt("id"));
            retVal.setProcessInstanceId(rs.getInt("processInstanceId"));
            retVal.setProcessTransitionId(rs.getInt("processTransitionId"));
            retVal.setTaskStateId(rs.getInt("taskStateId"));
            retVal.setResponsibleId(rs.getInt("responsibleId"));
            retVal.setResponsibleType(rs.getString("responsibleType"));
        }
        return retVal;
    }

    public boolean isActiveTransitionStep(int processTransitionId, int processInstanceId,int processDefinitionId ) throws SQLException {
        String query = String.format("CALL sp_isActiveTransitionStep(%1$d, %2$d, %3$d)", processTransitionId, processInstanceId, processDefinitionId);
        ResultSet rs = this.dbAccess.executeQuery(query);
        boolean retVal = true;
        if(rs.next()){
            retVal = rs.getBoolean("isActiveTransitionStep");
        }
        rs.close();
        return retVal;
    }

    public void updateProcessInstanceStatus(int processInstanceId, int instanceStatusId) throws SQLException{
        /*TODO rename this procedure with the proper nomenclature: "sp_updateProcessInstanceStatus" */
        String query = String.format("CALL sp_UpdateProcessInstanceStatusById(%1$d, %2$d)", processInstanceId, instanceStatusId);
        this.dbAccess.executeQuery(query);
    }

    public void updateTaskStatus(int taskId, int taskStateId) throws SQLException{
        String query = String.format("CALL sp_updateTaskStatusById(%1$d, %2$d)", taskId, taskStateId);
        this.dbAccess.executeQuery(query);
    }

    public void updateTaskStatus(int taskId, int taskStateId, int userId) throws SQLException{
        String query = String.format("CALL sp_updateTaskStatusUserById(%1$d, %2$d, %3$d)", taskId, taskStateId, userId);
        this.dbAccess.executeQuery(query);
    }

    public int addTransitionTask(TaskEntity inTask) throws SQLException{
        /*TODO rename this procedure with the proper nomenclature: "sp_insertTransitionTask" */
        String query = String.format("CALL sp_InsertTransitionTask(%1$d, %2$d, %3$d, %4$d, '%5$s')",
                inTask.getProcessInstanceId(),
                inTask.getProcessTransitionId(),
                inTask.getTaskStateId(),
                inTask.getResponsibleId(),
                inTask.getResponsibleType()
        );
        ResultSet rs = this.dbAccess.executeQuery(query);
        int retVal = 0;
        if(rs.next()) {
            retVal = rs.getInt("id");
        }
        return retVal;
    }

    public int addProcessInstance(ProcessInstanceEntity entry)throws SQLException{
        String query = String.format("CALL sp_insertProcessInstance(%1$d, %2$d, %3$d, '%4$s', '%5$s')",
                entry.getProcessDefinitionId(),
                entry.getProcessInstanceStateId(),
                entry.getInstanceNumber(),
                entry.getDescription(),
                entry.getCreatedBy()
        );
        ResultSet rs = this.dbAccess.executeQuery(query);
        int retVal = 0;
        if(rs.next()) {
            retVal = rs.getInt("id");
        }
        return retVal;
    }

    public TransitionStep transitionStepMetaData(int processTransitionId, int processDefinitionId) throws SQLException{
        String qryTransitionStepData = String.format("CALL sp_getTransitionStepMetaData(%1$d, %2$d)", processTransitionId, processDefinitionId);
        ResultSet rsTransitionStepData = this.dbAccess.executeQuery(qryTransitionStepData);
        TransitionStep ptran = new TransitionStep();
        if(rsTransitionStepData.next()) {
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
        }
        return ptran;
    }

    public Object getAttributeItemValue(int processDefinitionId, int processInstanceId, String attributeSysName) throws SQLException {
        String query = String.format("CALL sp_getAttributeItemValueBySysName(%1$d, %2$d, '%3$s');", processDefinitionId, processInstanceId, attributeSysName);
        ResultSet rs = this.dbAccess.executeQuery(query);
        Object retVal = null;
        if(rs.next()){
            Class returnType = this.setDataType(rs.getString("dataType"));
            retVal = rs.getObject("rawValue", returnType);
        }
        return retVal;
    }

    public Object getAttributeItemValue(int processDefinitionId, int processInstanceId, int attributeId) throws SQLException {
        String query = String.format("CALL sp_getAttributeItemValueById(%1$d, %2$d, %3$d);", processDefinitionId, processInstanceId, attributeId);
        ResultSet rs = this.dbAccess.executeQuery(query);
        Object retVal = null;
        if(rs.next()){
            Class returnType = this.setDataType(rs.getString("dataType"));
            retVal = rs.getObject("rawValue", returnType);
        }
        return retVal;

    }

    public boolean evaluateTransitionStepConditions(int processTransitionId, int processDefinitionId, int processInstanceId) throws SQLException, Exception{
        /*TODO rename this procedure with the proper nomenclature: "sp_listTransitionConditions" */
        String qry = String.format("CALL sp_transitionStepConditionList(%1$d, %2$d, %3$d)", processTransitionId, processInstanceId, processDefinitionId);
        ResultSet rs = this.dbAccess.executeQuery(qry);
        ConditionSet rootSet = new ConditionSet();
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
                    ConditionSet parent = this.findWrapperInChilds(rootSet,parentWrapper);
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
                    ConditionSet parent = this.findWrapperInChilds(rootSet,cluster);
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

    private ConditionSet findWrapperInChilds(ConditionSet rootSet, ConditionSet findSet){
        ConditionSet returnSet = null;
        for (ConditionSet set : rootSet.getChilds()) {
            if (set.getSetId() == findSet.getParentSetId()) {
                returnSet = set;
                break;
            }else{
                findWrapperInChilds(set, findSet);
            }
        }
        return returnSet;
    }

    private ConditionSet findWrapperInChilds(ConditionSet rootSet, int findParentSetId){
        ConditionSet returnSet = null;
        for (ConditionSet set : rootSet.getChilds()) {
            if (set.getSetId() == findParentSetId) {
                returnSet = set;
                break;
            }else{
                findWrapperInChilds(set, findParentSetId);
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

    private ArrayList<ProcessTransitionConditionEntity> listTransitionsConditions(int processTransitionId, int processDefinitionId)throws SQLException{
        String qry = String.format("CALL sp_listProcessTransitionCondition(%1$d, %2$d)", processTransitionId, processDefinitionId);
        ResultSet rs = this.dbAccess.executeQuery(qry);
        ArrayList<ProcessTransitionConditionEntity> out = new ArrayList<ProcessTransitionConditionEntity>();
        while(rs.next()){
            ProcessTransitionConditionEntity item = new ProcessTransitionConditionEntity();
            item.setId(rs.getInt("id"));
            item.setConditionSetId(rs.getInt("conditionSetId"));
            item.setProcessTransitionId(rs.getInt("processTransitionId"));
            item.setTransitionEvent(rs.getString("transitionEvent"));
            item.setNextProcessTransitionId(rs.getInt("nextProcessTransitionId"));
            out.add(item);
        }
        return out;
    }

    public ProcessInstanceEntity getProcessInstance(long instanceNumber) throws SQLException{

        //System.out.println("getProcessInstance("+ instanceNumber +")");
        String qry = String.format("CALL sp_getProcessInstanceByNumber(%1$d)", instanceNumber);
        ResultSet rs = this.dbAccess.executeQuery(qry);
        ProcessInstanceEntity retVal = null;
        if(rs.next()) {
            retVal = new ProcessInstanceEntity(
                    rs.getInt("id"),
                    rs.getInt("processDefinitionId"),
                    rs.getInt("processInstanceStateId"),
                    rs.getLong("instanceNumber"),
                    rs.getString("descripction"),
                    rs.getString("createdBy"),
                    rs.getDate("createdAt"),
                    rs.getDate("closedAt")
            );
        }
        return retVal;
    }

    public ProcessInstanceEntity getProcessInstance(int id) throws SQLException{
        String qry = String.format("CALL sp_getProcessInstanceById(%1$d);", id);
        ResultSet rs = this.dbAccess.executeQuery(qry);
        ProcessInstanceEntity retVal = null;
        if(rs.next()) {
            retVal = new ProcessInstanceEntity(
                    rs.getInt("id"),
                    rs.getInt("processDefinitionId"),
                    rs.getInt("processInstanceStateId"),
                    rs.getLong("instanceNumber"),
                    rs.getString("description"),
                    rs.getString("createdBy"),
                    rs.getDate("createdAt"),
                    rs.getDate("finalizedAt")
            );
        }
        return retVal;
    }

    public ProcessTransitionEntity getPrcessTransition(int taskId, int processInstanceId, int processDefinitionId)throws SQLException{
        String qry = String.format("CALL sp_getProcessTransitionByTaskId(%1$d, %2$d, %3$d);", taskId, processInstanceId, processDefinitionId);
        ResultSet rs = this.dbAccess.executeQuery(qry);
        ProcessTransitionEntity resVal = null;
        if(rs.next()){
            resVal = new ProcessTransitionEntity(
                    rs.getInt("id"),
                    rs.getInt("processDefinitionId"),
                    rs.getInt("stepId"),
                    rs.getInt("responsibleId"),
                    rs.getString("responsibleType"),
                    rs.getInt("slaTypeId"),
                    rs.getInt("slaValue"),
                    rs.getInt("sequence"),
                    rs.getBoolean("hasDelay"),
                    rs.getInt("delaySlaTypeId"),
                    rs.getInt("delayValue"),
                    rs.getBoolean("active")
            );
        }
        return resVal;
    }

    public ProcessTransitionEntity getInitialTransitionStep(int processDefinitionId)throws SQLException{
        String qry = String.format("CALL sp_getProcessTransitionByProcess(%1$d);", processDefinitionId);
        ResultSet rs = this.dbAccess.executeQuery(qry);
        ProcessTransitionEntity resVal = null;
        if(rs.next()){
            resVal = new ProcessTransitionEntity(
                    rs.getInt("id"),
                    rs.getInt("processDefinitionId"),
                    rs.getInt("stepId"),
                    rs.getInt("responsibleId"),
                    rs.getString("responsibleType"),
                    rs.getInt("slaTypeId"),
                    rs.getInt("slaValue"),
                    rs.getInt("sequence"),
                    rs.getBoolean("hasDelay"),
                    rs.getInt("delaySlaTypeId"),
                    rs.getInt("delayValue"),
                    rs.getBoolean("active")
            );
        }
        return resVal;
    }

    public InputEventResult<EntryInstance> processInputEvent(EntryInstance instance) throws Exception {
        InputEventResult<EntryInstance> retVal = new InputEventResult<>();

        /*Obtain the number of event*/
        int eventTypeId = Integer.parseInt(instance.getEvent().replaceAll("[^0-9]+",""));
        retVal.setResultObject(instance);

        /*TODO validate if the user input exists in de database*/


        ProcessInstanceEntity processInstance;
        ProcessTransitionEntity taskTransition;
        TaskEntity task;

        switch(eventTypeId){
            case InputEventType.ACTIVATE_INSTANCE:
                System.out.println("Activating Instance");
                try {
                    processInstance = new ProcessInstanceEntity(
                            instance.getProcessId(),
                            InstanceState.ACTIVATED,
                            instance.getInstanceNumber(),
                            instance.getDescription(),
                            String.valueOf(instance.getUserId())
                    );
                    processInstance.setId(this.addProcessInstance(processInstance));
                    taskTransition = this.getInitialTransitionStep(instance.getProcessId());
                    if (taskTransition != null) {
                        this.activateTransitionStep(taskTransition.getId(), processInstance.getId(), processInstance.getProcessDefinitionId(), instance.getUserId());
                    }
                    retVal.parseResultCode(ResultCode.Success,"es-cl");
                }catch(Exception e){
                    e.printStackTrace();
                    retVal.parseResultCode(ResultCode.Exception,"es-cl");
                    retVal.setResultMessage(e.getMessage());
                }
                break;

            case InputEventType.ACTIVATE_TASK:
                System.out.println("Activating Task");
                try {
                    processInstance = this.getProcessInstance(instance.getInstanceNumber());
                    taskTransition = this.getPrcessTransition(instance.getStepId(), processInstance.getId(), processInstance.getProcessDefinitionId());
                    if (taskTransition != null) {
                        this.activateTransitionStep(taskTransition.getId(), processInstance.getId(), processInstance.getProcessDefinitionId(), instance.getUserId());
                    }
                    retVal.parseResultCode(ResultCode.Success,"es-cl");
                }catch(Exception e){
                    e.printStackTrace();
                    retVal.parseResultCode(ResultCode.Exception,"es-cl");
                    retVal.setResultMessage(e.getMessage());
                }

                break;

            case InputEventType.COMPLETE_TASK:
                System.out.println("Completing Task");
                try {
                    processInstance = this.getProcessInstance(instance.getInstanceNumber());
                    taskTransition = this.getPrcessTransition(instance.getStepId(), processInstance.getId(), processInstance.getProcessDefinitionId());

                    if (taskTransition != null) {
                        retVal.parseResultCode(this.completeTransitionStep(taskTransition.getId(), processInstance.getId(), processInstance.getProcessDefinitionId(), instance.getStepId(), instance.getUserId()), "es-es");
                    }else{
                        throw new Exception("Dejando la mea caga");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    retVal.parseResultCode(ResultCode.Exception,"es-cl");
                    retVal.setResultMessage(e.getMessage());
                }
                break;

            case InputEventType.ABORT_INSTANCE:
                System.out.println("Aborting Instance");
                break;

            case InputEventType.REACTIVATE_INSTANCE:
                System.out.println("Reactivating Instance");
                break;
        }


        return retVal;
    }
}
