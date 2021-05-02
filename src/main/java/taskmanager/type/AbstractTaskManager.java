package taskmanager.type;

import taskmanager.Process;

import java.util.Collection;
import java.util.logging.Logger;

public abstract class AbstractTaskManager {

    final Logger logger = Logger.getLogger(AbstractTaskManager.class.getName());

    Collection<Process> processes;
    int maxSize;

    public AbstractTaskManager(int maxSize) {
        if (maxSize > 0) {
            this.maxSize = maxSize;
        } else {
            throw new IllegalArgumentException("maxSize cannot be less than 1");
        }
    }

    public abstract void add(Process process);

    public Collection<Process> list() {
        return processes;
    }

}
