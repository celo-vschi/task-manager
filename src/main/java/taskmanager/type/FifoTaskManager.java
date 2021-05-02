package taskmanager.type;

import taskmanager.Process;

import java.util.LinkedList;
import java.util.logging.Logger;

public class FifoTaskManager extends AbstractTaskManager {

    final Logger logger = Logger.getLogger(FifoTaskManager.class.getName());

    public FifoTaskManager(int maxSize) {
        super(maxSize);
        this.processes = new LinkedList<>();
    }

    @Override
    public void add(Process process) {
        LinkedList<Process> fifoProcesses = (LinkedList<Process>) processes;

        if (processes.size() == maxSize) {
            Process last = fifoProcesses.removeFirst();
            last.kill();
        }
        fifoProcesses.addLast(process);
    }

}
