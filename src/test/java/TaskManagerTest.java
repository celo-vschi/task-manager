import org.junit.Test;
import taskmanager.Prio;
import taskmanager.Process;
import taskmanager.type.AbstractTaskManager;
import taskmanager.type.DefaultTaskManager;
import taskmanager.type.FifoTaskManager;
import taskmanager.type.PrioTaskManager;

import java.util.Collection;

import static org.junit.Assert.*;

public class TaskManagerTest {

    AbstractTaskManager underTest;

    @Test(expected = IllegalArgumentException.class)
    public void testMaxSizeLessThan1() {
        underTest = new DefaultTaskManager(0);
    }

    @Test
    public void testDefaultTaskManager() {
        underTest = new DefaultTaskManager(2);

        Process p1 = new Process(Prio.LOW);
        Process p2 = new Process(Prio.MEDIUM);
        Process p3 = new Process(Prio.HIGH);

        underTest.add(p1);
        Collection<Process> processes = underTest.list();
        assertTrue(processes.contains(p1));
        assertEquals(1, processes.size());

        underTest.add(p2);
        processes = underTest.list();
        assertTrue(processes.contains(p1));
        assertTrue(processes.contains(p2));
        assertEquals(2, processes.size());

        // p3 shouldn't be added because
        underTest.add(p3);
        processes = underTest.list();
        assertTrue(processes.contains(p1));
        assertTrue(processes.contains(p2));
        assertFalse(processes.contains(p3));
        assertEquals(2, processes.size());

        // killed p2
        underTest.kill(p2);
        processes = underTest.list();
        assertTrue(processes.contains(p1));
        assertFalse(processes.contains(p2));
        assertEquals(1, processes.size());

        // adding p3 again; this time it should work
        underTest.add(p3);
        processes = underTest.list();
        assertTrue(processes.contains(p1));
        assertTrue(processes.contains(p3));
        assertEquals(2, processes.size());
    }


    @Test
    public void testFifoTaskManager() {
        underTest = new FifoTaskManager(2);

        Process p1 = new Process(Prio.LOW);
        Process p2 = new Process(Prio.MEDIUM);
        Process p3 = new Process(Prio.HIGH);

        underTest.add(p1);
        Collection<Process> processes = underTest.list();
        assertTrue(processes.contains(p1));
        assertEquals(1, processes.size());

        underTest.add(p2);
        processes = underTest.list();
        assertTrue(processes.contains(p1));
        assertTrue(processes.contains(p2));
        assertEquals(2, processes.size());

        // p1 should be removed and p3 should be added
        underTest.add(p3);
        processes = underTest.list();
        assertTrue(processes.contains(p2));
        assertTrue(processes.contains(p3));
        assertFalse(processes.contains(p1));
        assertEquals(2, processes.size());
    }

    @Test
    public void testPrioTaskManager() throws InterruptedException {
        underTest = new PrioTaskManager(6);

        Process p1 = new Process(Prio.LOW);
        Process p2 = new Process(Prio.MEDIUM);
        Process p3 = new Process(Prio.HIGH);
        Process p4 = new Process(Prio.LOW);
        Process p5 = new Process(Prio.MEDIUM);
        Process p6 = new Process(Prio.HIGH);

        underTest.add(p1);
        underTest.add(p2);
        underTest.add(p3);
        underTest.add(p4);
        underTest.add(p5);
        underTest.add(p6);

        Collection<Process> processes = underTest.list();
        assertTrue(processes.contains(p1));
        assertTrue(processes.contains(p2));
        assertTrue(processes.contains(p3));
        assertTrue(processes.contains(p4));
        assertTrue(processes.contains(p5));
        assertTrue(processes.contains(p6));
        assertEquals(6, processes.size());

        // this process should not be added, no lower prio is in the list
        Process shouldNotBeAdded = new Process(Prio.LOW);
        underTest.add(shouldNotBeAdded);
        processes = underTest.list();
        assertFalse(processes.contains(shouldNotBeAdded));
        assertEquals(6, processes.size());

        // this process should be added and p1 (lowest prio and oldest) should be removed
        Process shouldBeAdded1 = new Process(Prio.MEDIUM);
        underTest.add(shouldBeAdded1);
        processes = underTest.list();
        assertFalse(processes.contains(shouldBeAdded1));
        assertTrue(processes.contains(p1));
        assertEquals(6, processes.size());

        // this process should be added and p4 (lowest prio and oldest) should be removed
        Process shouldBeAdded2 = new Process(Prio.HIGH);
        underTest.add(shouldBeAdded2);
        processes = underTest.list();
        assertFalse(processes.contains(shouldBeAdded2));
        assertTrue(processes.contains(p4));
        assertEquals(6, processes.size());
    }

    @Test
    public void testKillAll() {
        underTest = new DefaultTaskManager(3);

        Process p1 = new Process(Prio.LOW);
        Process p2 = new Process(Prio.MEDIUM);
        Process p3 = new Process(Prio.HIGH);

        underTest.add(p1);
        underTest.add(p2);
        underTest.add(p3);

        Collection<Process> processes = underTest.list();
        assertEquals(3, processes.size());

        underTest.killAll();
        processes = underTest.list();
        assertTrue(processes.isEmpty());
    }

    @Test
    public void testKillGroup() {
        underTest = new DefaultTaskManager(6);

        Process p1 = new Process(Prio.LOW);
        Process p2 = new Process(Prio.MEDIUM);
        Process p3 = new Process(Prio.HIGH);
        Process p4 = new Process(Prio.LOW);
        Process p5 = new Process(Prio.MEDIUM);
        Process p6 = new Process(Prio.HIGH);

        underTest.add(p1);
        underTest.add(p2);
        underTest.add(p3);
        underTest.add(p4);
        underTest.add(p5);
        underTest.add(p6);

        Collection<Process> processes = underTest.list();
        assertEquals(6, processes.size());

        underTest.killGroup(Prio.HIGH);
        processes = underTest.list();
        assertEquals(4, processes.size());
        assertFalse(processes.contains(p3));
        assertFalse(processes.contains(p6));
    }

}
