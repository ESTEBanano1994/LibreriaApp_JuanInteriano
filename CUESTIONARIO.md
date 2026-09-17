Diferencia entre clase y objeto

-la diferencia es que la clase es la que define un grupo de objetos, y el objeto es el que por medio de parámetros y coincidencias se almacena en una clase en especifico
¿Que es un atributo?
-Un atributo es un agregado a un objeto, una característica o diferencia
-dentro de libreria app, los atributos podemos encontrarlos dentro de la clase de libros, donde nos definen cosas como que deben llevar nombre, o autor
¿Que es un metodo?
-un método es por asi decirlo una instrucción que se le da a una acción
-los metodos dentro de libreriaApp, podemos encontrarlos cuando vemos por ejemplo dentro de usuario "evniar correo" o "registrar usuario"
¿Para que sirve un constructor?
-no se
-no se donde puedo encontrarlo
Diferencia entre private, public y protected
-la diferencia principal son los accesos y permisos de cada una, uno esconde los archivos que no tienes permitido ver, el otro te permite verlos y el protected te permite acceder a solo ciertos archivos con la limitancia de que no pueden ser modificados si no se tiene un rol que permita hacerlo
-dentro de la libreria, podemos encontrarlo cuando hablamos de las clases, en donde principalmente tenemos las publicas y las privadas
¿para que sirve get y set?
-get sirve principalmente para obtener un dato, set sirve para asignarlo
-en libreria lo encontramos cuando solicitamos el nombre del usuario y set cuando colocamos un codigo unico a estos usuarios que ingresan
¿Que significa encapsulamiento?
-encapsulamiento es almacenar atributos
-dentro de libreria el encapsulamiento lo encontramos cuando una clase protege y dirige sus datos, en este caso en los libros cuando vemos un "if" referente al precio 
diferencia entre clase y interfaz
-Una clase define caracteristicas y atributos que debe tener un objeto y una interfaz es el contrato que define que metodos debe llevar una clase dentro para poder realizar cierta accion relacionada a la interfaz
-dentro de la libreria podemos encontrar por ejemplo la clase factura, y dentro puede llevar el metodo "imprimible" y para que esto se lleve acabo, la interfaz podria decir que debe llevar el metodo "imprimir"
¿Que significa implements?
-Implements significa implementar
-dentro de la libreria implements lo econtramos a la hora de colocar una contraseña
¿Que significan static y final?
-no lo se :(
-no se donde lo podemos encontrar
¿Qué es una tabla relacional?
-una tabla relacionar es una forma grafica de representar como varios objetos se conectan entre si, mostrando su relacion y conexion
-dentro de la libreria, la tabla relacional nos mostraria como cada parte del codigo se relaciona entre si, solicitando y enviando datos que permitan la ejecucion correcta del codigo, por ejemplo el controller solicitando datos del view y del dao para mostrar el registro de un usuario
¿Qué es una clave primaria?
-no lo se :(
-no se donde se puede encontrar
¿Qué es una clave foránea?
-la llave foranea es el conector entre un grupo de columnas para compartir datos que sean iguales en las columnas
-dentro de la tabla relacional, en donde vemos un datos que se conecta a muchos o uno que solo se conecta a otro
¿Qué significa CRUD?
-create, read, update, delete
¿Qué es JDBC?
-no lo se
-no se donde lo podemos encontrar
¿Qué es un procedimiento almacenado?
-es un conjunto de instrucciones SQL, guardadas, o almacenadas que solo pueden ser ejecutadas por una llamada/orden en especifico, creada para justo eso, que pueda ejecutar el procedimiento almacenado
-lo encontramos a la hora de usar la interfaz de bodega, en donde vemos todos los datos de cada libro y al agregar o quitar stock, veremos como los datos del libro se van agregando o quitando
¿Qué es DAO?
-no lo se
-el dao lo encontramos por ejemplo en "usuario.dao" o "usuariodao.impl"
¿Por qué separar SQL del Controller?
-SQL, es el almacen de los datos, el controller es quien controla estos datos, su trabajo son dos codigos diferentes pero para poder ser ejecutados al final, se hace una conexion en donde sql tiene los datos que son controlados por el controller
¿Qué es JavaFX?
-Es una parte de java que se concentra mas en trabajar con clases y objetos
-lo encontramos en todo nuestro codigo, desde los metodos, hasta las interfaces, los fxml y cada parte construida con la programacion orientada a objetos de JavaFX
¿Qué es FXML?
-no lo se
-no se donde lo podemos encontrar
¿Qué función cumple SceneBuilder?
-SceneBuilder nos ayuda a diseñar la interfaz de nuestro codigo en ejecucion
-Scenebuilder y su interfaz es lo que vemos cada que ejecutamos nuestro programa, en menus y inicios de sesion, desde los botones, hasta los espacios para escribir
¿Qué es un Controller?
-un controller es una java class que nos ayuda a manejar ciertas partes de la interfaz, por ejemplo un controller de menu principal, llamara, almacenara y ejecutara todos los codigos que pertenezan al menu principal y su interfaz
-los controllers los encontramos en las ramas principales, ya que son los dirigentes de las demas ramas
¿Qué significa @FXML?
-no lo se
-no se donde lo podemos encontrar
¿Qué significa MVC?
-no lo se
-no se donde lo podemos encontrar
¿Qué responsabilidad tiene el modelo?
-no lo se
-no se onde lo podemos encontrar
¿Qué responsabilidad tiene la vista?
-la vista, organiza el como se vera y que es lo que se mostrara a la hora de ejecutar nuestro codigo
-la vista la vemos al ejecutar el codigo, el view es quien dirige que y como se vera lo que esta en la interfaz grafica
¿Qué responsabilidad tiene el controlador?
-controlar el codigo
Dibuja el recorrido de un dato hasta MySQL.
investiga separación de responsabilidades, acoplamiento y cohesión

-La separación de responsabilidades consiste en separar el programas de manera que cada parte tenga una responsabilidad o trabajo en especifico

-La Coheción, se refiere a que tan relacionadas estan las cosas que hace una clase, por ejemplo si juntaramos dentro de una clase un libro, que se registre el libro, que se empaque y se venda tiene alta cohesión, todo tiene que ver con libro, pero si a libro le ponemos que registre un correo, que registre el usuario o que llame a conectarse con alguna otra parte de la libreria, tendria una baja cohesion, a parte de ser muchas responsabilidades, no estan relacionadas con lo principal que seria libro

-acoplamiento, a la hora de hablar de acoplamiento, hablamos de saber que tanto conoce y de que tanto depende una parte del programa de otra, por ejemplo si nuestro controller depende de muchas partes del programa y una no esta bien, esto afectara a la hora de ejecutar el programa, pero si hacemos que nuestro controller no depende de tantas cosas, sera mas facil el poder ejecutarlo

-cuando hacemos un proyecto siempre buscaremos: separar responsabilidades, aumentar la cohesión y reducir el acoplamiento.
