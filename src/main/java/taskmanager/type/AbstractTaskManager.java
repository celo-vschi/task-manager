package taskmanager.type;

import taskmanager.Process;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;

public abstract class AbstractTaskManager {

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
        return list(Sorting.ORIGINAL);
    }

    public Collection<Process> list(Sorting sorting) {
        Comparator<Process> processComparator;

        switch (sorting) {
            case CREATION_TIME:
                processComparator = Comparator.comparing(Process::getCreationTime);
                break;
            case PRIO:
                processComparator = Comparator.comparing(Process::getIntPrio, Comparator.reverseOrder());
                break;
            case ID:
                processComparator = Comparator.comparing(Process::getPid);
                break;
            case ORIGINAL:
            default:
                processComparator = null;
                break;
        }

        if (processComparator != null) {
            return processes.stream().sorted(processComparator).collect(Collectors.toList());
        } else {
            return processes;
        }

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

    public void killGroup(Process.Prio prio) {
        processes.removeIf(process -> prio == process.getPrio());
    }

    public enum Sorting {
        CREATION_TIME,
        PRIO,
        ID,
        ORIGINAL
    }
}
