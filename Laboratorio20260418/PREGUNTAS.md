# Preguntas

- ¿Por que usamos HashMap para libros?
     - Porque es necesario que el registro de cada libro exista solo una vez y el acceso a un es muchomas rapido.

- ¿Por que la cola es ideal para la lista de espera?
    - Porque la lista de espera se necesita que conforme vayan solicitando libros asi se vaya procesando el prestamo, en orden de llegada.

- ¿Que ventaja tiene el BST para la busqueda?
    - permite busquedas mas rapida ya que empieza comparaando y descartando la mitad de las entradas como minimo. No es busqueda lineal.

- ¿Que pasaria si utilizamos ArrayList en lugar del HashMap?-
    - se corre el riesgo de aceptar duplicados y guardar valores Null, la busqueda por ISBN implica recorrer todo el arraylist

- ¿Cual es la complejidad de: busqueda en HashMap, insercion en BST, operaciones de pila y cola
    - la complejidad de busqueda de HashMap es constante , O(1)
    - la comlejidad de insercion en un arbol BST es logaritmica, O(log(n))
    - las operaciones de pila y cola tienen una complidad de O(1)

