# visioners
Proyecto final de Algoritmos y Estructura de Datos - Sistema de Gestión de Inventario mediante Grafos y Árboles B+.


El sistema que hemos desarrollado es una aplicación para gestionar un almacén de forma más organizada.

Lo que permite es registrar ubicaciones físicas dentro del almacén, como por ejemplo estanterías, pasillos, zonas de carga, etc.

Estas ubicaciones se representan como vértices en un grafo dirigido, y entre ellas podemos crear rutas (o caminos), que se representan como aristas con peso, lo cual podría representar distancia o tiempo.

Así, el grafo modela el mapa interno del almacén, y permite, por ejemplo, encontrar caminos o analizar la conectividad entre zonas.

Pero además de eso, cada ubicación tiene su propio árbol B+ interno, que usamos para almacenar productos de forma ordenada.

Por ejemplo, si queremos guardar un producto en una zona específica, lo insertamos dentro del árbol B+ asociado a esa ubicación.

Este árbol nos permite buscar, ordenar e insertar productos de manera eficiente, ya que los árboles B+ están diseñados para operaciones rápidas en estructuras grandes.
