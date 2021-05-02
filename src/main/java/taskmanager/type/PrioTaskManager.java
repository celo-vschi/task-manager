package taskmanager.type;

import taskmanager.Process;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;

public class PrioTaskManager extends AbstractTaskManager {

    final Logger logger = Logger.getLogger(PrioTaskManager.class.getName());

    private static final Comparator<Process> leastImportantProcessComparator = Comparator
            .comparing(Process::getIntPrio, Comparator.reverseOrder())
            .thenComparing(Process::getCreationTime);

    public PrioTaskManager(int maxSize) {
        super(maxSize);
        this.processes = new ArrayList<>();
    }

    @Override
    public void add(Process process) {
        boolean shouldAdd = true;

        if (processes.size() == maxSize) {
            Process leastImportantProcess = getLeastImportantProcess();
            if (process.getIntPrio() > leastImportantProcess.getIntPrio()) {
                processes.remove(leastImportantProcess);
            } else {
                shouldAdd = false;
                logger.info("No process with a lower priority than " + process + " was found");
            }
        }

        if (shouldAdd) {
            processes.add(process);
        }
    }

    private Process getLeastImportantProcess() {
        return processes.stream().sorted(leastImportantProcessComparator).findFirst().get();
    }

}
