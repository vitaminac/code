package core.concurrent;

import java.util.concurrent.Semaphore;

public class Semaphore_DB_Lock {
    private int waiting_reader = 0;
    // exclusive access to waiting_reader
    private Semaphore mutex_waiting_reader = new Semaphore(1);

    private int working_reader = 0;
    // exclusive access to working_reader
    private Semaphore mutex_working_reader = new Semaphore(1);

    // we can have multiple writer who wants access the db
    private int activate_writer = 0;
    // exclusive access to activate_writer
    private Semaphore mutex_active_writer = new Semaphore(1);

    private Semaphore waiting_semaphore = new Semaphore(0);
    // controls access to the database, it is own by readers or writers
    private Semaphore db_lock = new Semaphore(1);

    public void acquireDBReadLock(final int id) throws InterruptedException {
        // if there are some active writers then give them priority
        mutex_active_writer.acquire();
        if (activate_writer > 0) {

            // add to the waiting list
            mutex_waiting_reader.acquire();
            waiting_reader += 1;
            mutex_waiting_reader.release();

            // after here we may have activate_writer zero,
            // but he will release exactly waiting_reader permits
            // so don't worry
            mutex_active_writer.release();

            waiting_semaphore.acquire();

            // remove from the waiting list
            mutex_waiting_reader.acquire();
            waiting_reader -= 1;
            mutex_waiting_reader.release();
        } else {
            mutex_active_writer.release();
        }

        mutex_working_reader.acquire();
        working_reader += 1;
        // If I am the first reader then lock the db access
        if (working_reader == 1) db_lock.acquire();
        mutex_working_reader.release();
    }

    public void releaseDBReadLock(final int id) throws InterruptedException {
        this.mutex_working_reader.acquire();
        this.working_reader -= 1;
        if (this.working_reader == 0) db_lock.release();
        this.mutex_working_reader.release();
    }

    public void acquireDBWriteLock(final int id) throws InterruptedException {
        this.mutex_active_writer.acquire();
        this.activate_writer += 1;
        this.mutex_active_writer.release();
        this.db_lock.acquire();
    }

    public void releaseDBWriteLock(final int id) throws InterruptedException {
        this.mutex_active_writer.acquire();
        this.activate_writer -= 1;
        // when there is no more writer wants to access db
        // then we give the turn to readers
        if (this.activate_writer == 0) {
            this.mutex_waiting_reader.acquire();
            this.waiting_semaphore.release(this.waiting_reader);
            this.mutex_waiting_reader.release();
        }
        this.mutex_active_writer.release();
        this.db_lock.release();
    }
}
