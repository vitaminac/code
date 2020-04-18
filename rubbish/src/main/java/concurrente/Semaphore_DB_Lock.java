package concurrente;

import java.util.concurrent.Semaphore;

public class Semaphore_DB_Lock {
    private int waiting_reader = 0;
    private Semaphore mutex_waiting_reader = new Semaphore(1); // exclusive access to waiting_reader

    private int working_reader = 0;
    private Semaphore mutex_working_reader = new Semaphore(1); // exclusive access to working_reader

    private int activate_writer = 0; // we can have multiple writer who wants access the db
    private Semaphore mutex_active_writer = new Semaphore(1); // exclusive access to activateWriter

    private Semaphore waiting_semaphore = new Semaphore(0);
    private Semaphore db_lock = new Semaphore(1); // controls access to the database, it is own by readers or writers

    public void acquireDBReadLock(final int id) throws InterruptedException {
        // if there is any waiting writer then give him priority
        mutex_active_writer.acquire();
        if (activate_writer > 0) {

            // add to the waiting list
            mutex_waiting_reader.acquire();
            waiting_reader += 1;
            System.out.println("Reader " + id + " is waiting. Now we have " + waiting_reader + " waiting readers.");
            mutex_waiting_reader.release();

            // after here we may have activate_writer zero,
            // but he will release exactly waiting_reader permit
            // so don't worry
            mutex_active_writer.release();

            waiting_semaphore.acquire();

            // add to the waiting list
            mutex_waiting_reader.acquire();
            waiting_reader -= 1;
            System.out.println("Reader " + id + " is done waiting. Now we have " + waiting_reader + " waiting readers.");
            mutex_waiting_reader.release();
        } else {
            mutex_active_writer.release();
        }

        mutex_working_reader.acquire();
        working_reader += 1;
        System.out.println("Reader " + id + " is reading. Now we have " + working_reader + " working readers");
        // If I am the first reader then lock the db access
        if (working_reader == 1) db_lock.acquire();
        mutex_working_reader.release();
    }

    public void releaseDBReadLock(final int id) throws InterruptedException {
        this.mutex_working_reader.acquire();
        this.working_reader -= 1;
        System.out.println("Reader " + id + " is done reading. Now we have " + working_reader + " working readers");
        if (this.working_reader == 0) db_lock.release();
        this.mutex_working_reader.release();
    }

    public void acquireDBWriteLock(final int id) throws InterruptedException {
        this.mutex_active_writer.acquire();
        this.activate_writer += 1;
        System.out.println("Writer " + id + " is waiting. Now we have " + activate_writer + " active writers");
        this.mutex_active_writer.release();
        this.db_lock.acquire();
    }

    public void releaseDBWriteLock(final int id) throws InterruptedException {
        this.mutex_active_writer.acquire();
        this.activate_writer -= 1;
        System.out.println("Writer " + id + " is done writing. Now we have " + activate_writer + " active writers");
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
