package taskmanager;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.logging.Logger;

public class Process {

    private static final Random RANDOM = new Random();
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
        this.pid = RANDOM.nextLong();
        this.prio = prio;
        this.creationTime = LocalDateTime.now();
    }

    public void kill() {
        logger.info("Killed process " + pid);
    }

    public Long getPid() {
        return pid;
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

    public enum Prio {

        HIGH(0),
        MEDIUM(1),
        LOW(2);

        private final int intValue;

        Prio(int intValue) {
            this.intValue = intValue;
        }

        public int getIntValue() {
            return this.intValue;
        }

    }
}
