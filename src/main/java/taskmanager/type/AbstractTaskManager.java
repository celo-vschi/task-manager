package taskmanager.type;

import taskmanager.Prio;
import taskmanager.Process;

import java.util.Collection;
import java.util.Iterator;
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

    public void kill(Process process) {
        processes.remove(process);
        process.kill();
    }

    public void killAll() {
        Iterator<Process> iterator = processes.iterator();
        while (iterator.hasNext()) {
            Process process = iterator.next();
            process.kill();
            iterator.remove();
        }
    }

    public void killGroup(Prio prio) {
        processes.removeIf(process -> prio == process.getPrio());
    }

}
