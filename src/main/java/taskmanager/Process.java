package taskmanager;

import taskmanager.utils.Utils;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class Process {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private Long pid;
    private Prio prio;
    private LocalDateTime creationTime;

    @Override
    public String toString() {
        return "Process{" +
                "pid=" + pid +
                ", prio=" + prio +
                ", creationTime=" + creationTime +
                '}';
    }

    public Process(Prio prio) {
        this.pid = Utils.generatePid();
        this.prio = prio;
        this.creationTime = LocalDateTime.now();
    }

    public void kill() {
        logger.info("Killed process " + pid);
    }

    public Prio getPrio() {
        return prio;
    }

    public int getIntPrio() {
        return prio.getIntValue();
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }
}
