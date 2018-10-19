package es.demo.privaliamobilechallenge.commons;

public class MVPContract {
    public interface View{}
    public interface Presenter<T>{
        void onAttach(T view);
        void onDetach();
    }
    public interface Model{}
}
