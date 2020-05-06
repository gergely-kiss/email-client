package uk.gergely.kiss.emailclient.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.emailclient.service.repo.ProcessEntity;
import uk.gergely.kiss.emailclient.service.repo.ProcessEntityRepository;

import javax.persistence.NoResultException;
import java.util.Optional;

@Service
public class ProcessServiceImpl implements ProcessService {
    private final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

    private final ProcessEntityRepository repo;

    public ProcessServiceImpl(ProcessEntityRepository processEntityRepository) {
        this.repo = processEntityRepository;
    }


    @Override
    public Boolean checkIfProcessIsRunningAndCreateIfNotExist(String processId) {
        try{
            ProcessEntity processEntity = repo.findById(processId).orElseThrow(()-> new NoResultException());
            Boolean isRunning = processEntity.getIsRunning();
            logger.info("{} is running:  {}", processId, isRunning);
            return isRunning;
        }
        catch (Exception e){
            logger.info("{} not exist exist. Creating new process", processId);
            ProcessEntity processEntity = new ProcessEntity();
            processEntity.setId(processId);
            processEntity.setIsRunning(false);
            repo.save(processEntity);
            return false;
        }
    }

    @Override
    public void startProcess(String processId) {
        logger.info("{}  started", processId);
        changeValue(processId);
    }

    @Override
    public void stopProcess(String processId) {
        logger.info("{}  stopped", processId);
        changeValue(processId);
    }

    private void changeValue(String processId) {
        ProcessEntity process = repo.findById(processId).orElseThrow(() -> new NoResultException());
        process.setIsRunning(!process.getIsRunning());
        repo.save(process);
    }
}
