package aplicaciones.gpsedit.util;

import java.util.Stack;

public class Pila<T> extends Stack<T> {

	private static final long serialVersionUID = -1806985665032027210L;
	private int maxSize;

    public Pila(int size) {
        super();
        this.maxSize = size;
    }

    @Override
    public T push(T object) {
        //elimina elementos si la pila es demasiado grande
        while (this.size() >= maxSize) {
            this.remove(0);
        }
        return super.push(object);
	}
}
