package collections.map;

interface Rehasher {
    Rehasher LINEAR_PROBE_REHASHER = (hash, key, trial) -> hash + trial;
    Rehasher QUADRATIC_PROBE_REHASHER = (hash, key, trial) -> hash + trial * trial;

    int rehash(int hash, int key, int trial);
}
