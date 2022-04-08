package commInfra;

abstract class Fifo_abstract1<T> {
    public abstract void add(Object letter);
    public abstract Object remove();
    public abstract Object size();
    public abstract void insert();
}