package scheduler;

public interface Delegate<DTO, R, E extends Exception> {
    R delegate(DTO dto) throws E;
}
