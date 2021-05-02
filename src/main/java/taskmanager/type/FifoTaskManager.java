package taskmanager.type;

import taskmanager.Process;

import java.util.LinkedList;

public class FifoTaskManager extends AbstractTaskManager {

    public FifoTaskManager(int maxSize) {
        super(maxSize);
        this.processes = new LinkedList<>();
    }

    @Override
    public void add(Process process) {
        LinkedList<Process> fifoProcesses = (LinkedList<Process>) processes;

        if (processes.size() == maxSize) {
            Process firstProcess = fifoProcesses.getFirst();
            kill(firstProcess);
        }
        fifoProcesses.addLast(process);
    }


}
