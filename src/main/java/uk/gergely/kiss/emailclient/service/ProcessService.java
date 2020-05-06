package uk.gergely.kiss.emailclient.service;

public interface ProcessService {

    Boolean checkIfProcessIsRunningAndCreateIfNotExist(String processId);

    void startProcess(String processId);

    void stopProcess(String processId);

}
