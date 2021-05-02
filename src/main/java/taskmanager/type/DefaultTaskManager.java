package taskmanager.type;

import taskmanager.Process;

import java.util.ArrayList;
import java.util.logging.Logger;

public class DefaultTaskManager extends AbstractTaskManager {

    final Logger logger = Logger.getLogger(DefaultTaskManager.class.getName());

    public DefaultTaskManager(int maxSize) {
        super(maxSize);
        this.processes = new ArrayList<>();
    }

    @Override
    public void add(Process process) {
        if (processes.size() < maxSize) {
            processes.add(process);
        } else {
            logger.warning("Cannot add " + process + "!");
        }
    }

}
