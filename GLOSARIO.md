------------------------GLOSARIO--------------------------------------


Clase

1.	Es una plantilla o molde en programación que define los atributos y métodos que tendrán los objetos que se creen a partir de ella.
2.	Para mí, una clase es como un plano, ya que en base a esta se definen los objetos que irán dentro, como en una casa, la clase seria plano y los objetos las casas que se construyan en base a este mismo
3.	Aparece en java cada que colocamos “Class” ya sabemos que se refiere a clase, como podría ser “public class dulces”
4.	Public class dulces, y dentro de esta clase podría haber mentas, chocolates, gomitas, y toda aquella chuchería que cumpla con los atributos (características) necesarias para estar dentro de
5.	Nos ayuda a definir y organizar las características de los objetos que usaremos y al hacer esto también los métodos son más organizados


Objeto

1.	Es una instancia de una clase que representa una entidad concreta y contiene sus propios atributos y métodos.
2.	Es la galleta que se cocinó en base al molde, siendo el molde la clase y la galleta el objeto que por sus atributos pertenece a esa clase
3.	Aparece dentro de class, un ejemplo digamos que tenemos la clase libro, el atributo podría ser “string editorial”
4.	Por ejemplo, tenemos la clase comic, dentro podemos tener el objeto “String autor”
5.	Los objetos nos ayudan a trabajar en base a lo ordenado por las clases, son los que se encargan de cumplir ordenes en base a los métodos que contienen y a trabajar en el código, en este caso el método de autor, definiría el nombre del escritor del comic 
 
Encapsulamiento

1.	Es el principio de la programación orientada a objetos que consiste en proteger los datos de una clase y controlar el acceso a ellos mediante métodos.
2.	El encapsulamiento es una forma de proteger los datos dentro de una clase, controlando el acceso y modificación de estos para mantener la lógica y las reglas que debe cumplir la clase. Por ejemplo, puede evitar que un libro tenga un precio negativo.
3.	El encapsulamiento es una forma de cuidar los datos que tenemos dentro de una clase, se trata de hacer que la clase pueda controlar que cosas y como se cambian estas cosas dentro de ella
4.	Podemos encontrarlo dentro de las clases, un ejemplo podrían ser los getters y setters, podríamos encontrar un “if” es una condición
5.	Un ejemplo podría ser a la hora de tener nuestra clase libro, si ponemos if (precio >= 0), estaríamos diciendo que el precio no puede ser menor a 0, nos ayuda a llevar una estructura mas ordenada de nuestro código para que cada clase pueda proteger y controlar sus datos manteniendo sus limites, reglas y la lógica
Interfaz

1.	Es una estructura que define un conjunto de métodos que las clases que la implementan deben cumplir, funcionando como un contrato que establece qué comportamientos debe tener una clase.
2.	Una interfaz es una forma de mantener condicionada una clase obligándola a implementar un método
3.	La interfaz en POO, funciona como un contrato, en donde al ser invocada, le pone como condición a la clase que si acepta el contrato, debe utilizar o implementar dentro del código el método
4.	Lo encontramos dentro de las clases, un ejemplo podría ser dentro de nuestra librería en la parte de la factura en donde diría “implements imprimible” entonces para que podamos usar ese “imprimible” nuestro contrato nos pedirá que dentro del código tengamos el método “imprimir” y asi se cumplira la condición de la interfaz
5.	La interfaz nos ayuda a condicionar y a poder ejecutar de forma mas ordenada nuestro código poniendo limites y condiciones a la hora de usar metodos
Tipo primitivo

1.	Es un tipo de dato básico de Java utilizado para almacenar valores simples, como números, caracteres y valores booleanos. Java cuenta con ocho tipos primitivos: byte, short, int, long, float, double, char y boolean.
2.	es uno de los tipos de datos básicos que Java proporciona para guardar valores simples directamente.
3.	Los tipos primitivos son datos clásicos que se pueden manejar fácilmente, cosas básicas como números enteros, texto, números decimales, o verdadero y falso, eso es un tipo primitivo, términos y cosas básicas implementadas dentro de nuestro código.
4.	Lo encontramos definiendo el tipo de atributo de un objeto
5.	Nos ayuda a saber que tipo de atributo es el de objeto, ya sea de texto, de numero o si es una autenticación de verdero o falso


 Clase Wrapper
1.	Es una clase de Java que permite representar un tipo de dato primitivo como un objeto, proporcionando métodos y características adicionales para trabajar con ese valor.
2.	es una clase de Java que sirve para representar un tipo primitivo como un objeto.
3.	La clase wrapper, nos ayuda a transformar un tipo primitivo a un objeto, aveces en el código realizamos comandos que requieren de un objeto y esto nos permite poder usar el tipo primitivo como ese objeto
4.	Lo encontramos a la hora de hacer métodos, en donde estos pueden llamar una clase wrapper ya convertida para poder ejecutarse
5.	Nos ayuda realizar acciones dentro del código que de otra manera sin la clase wrapper no podrían realizarse


DAO (Data Access Object)
1.Es un patrón de diseño que proporciona una interfaz abstracta para interactuar con la base de datos, separando la lógica de negocio de los detalles de acceso a los datos.
2. Para mí, el DAO es como el mesero de un restaurante; nosotros (la lógica de la aplicación) le pedimos lo que queremos comer y él va a la cocina (la base de datos) a traérnoslo, sin que tengamos que meternos nosotros mismos a cocinar ni a buscar los ingredientes.
3.Aparece como una clase o interfaz dedicada a la base de datos, con métodos como insertar(), eliminar() o obtenerTodos(). Por ejemplo, podríamos tener una clase UsuarioDAO con un método public List<Usuario> listarUsuarios().
4.Nos ayuda a mantener el código ordenado y desacoplado. Si el día de mañana cambiamos de base de datos (por ejemplo, de MySQL a PostgreSQL), solo modificamos las clases DAO y no toda nuestra aplicación.

JDBC (Java Database Connectivity)
1. Es una API estándar de Java que permite a las aplicaciones conectarse a bases de datos relacionales, ejecutar consultas SQL y procesar los resultados.
2. Es como el cable o puente de comunicación que conecta nuestro programa Java con la base de datos. Sin este conector, Java y la base de datos hablarían idiomas distintos y no podrían entenderse.
3. Lo encontramos al usar paquetes como java.sql.*, cargando el driver de la base de datos y abriendo conexiones con comandos como DriverManager.getConnection("jdbc:mysql://localhost:3306/mi_db", "root", "1234").
4. Nos ayuda a enviar instrucciones en lenguaje SQL directamente a la base de datos desde nuestras clases de Java y recibir la información devuelta para poder manipularla dentro del sistema.

Singleton
1. Es un patrón de diseño creacional que garantiza que una clase tenga únicamente una instancia en toda la aplicación y proporciona un punto de acceso global a ella.
2.El Singleton es como el director de un colegio; dentro de la institución solo puede existir un único director a la vez. No podemos andar creando varios directores, todos deben acudir al mismo cuando se requiera su función.
3. Se reconoce porque la clase tiene un constructor privado y un método estático como public static Conexion getInstance(). De esta manera, si la instancia ya existe, devuelve la misma en lugar de crear una nueva con new.
4. Nos ayuda a optimizar el uso de recursos y memoria en el sistema, evitando crear objetos repetidos cuando con uno solo es suficiente para realizar la tarea (como manejar una sola conexión a la base de datos).

PreparedStatement
1. Es una interfaz de JDBC que representa una instrucción SQL precompilada, la cual permite pasar parámetros de forma segura y eficiente antes de ejecutarse.
2. Es como un formulario impreso con espacios en blanco (campos por rellenar). La estructura de la oración SQL ya está fija y lista, y nosotros solo nos encargamos de escribir los valores específicos dentro de las casillas en blanco.
3. Lo encontramos usando el signo de interrogación ? dentro de la consulta SQL y llamando a la interfaz desde la conexión: PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM producto WHERE id = ?");, para luego asignar el valor con stmt.setInt(1, 10);.
4. Nos ayuda a ejecutar consultas a la base de datos de manera mucho más rápida (porque la sentencia ya está procesada) y previene fallos de seguridad graves como los ataques de inyección SQL.

Inyección SQL
1. Es una vulnerabilidad de seguridad en la que un atacante inserta o "inyecta" código SQL malicioso dentro de los campos de entrada de una aplicación para alterar las consultas a la base de datos.
2. Es como si en una hoja de firma donde debes poner tu nombre, escribas una instrucción engañosa como: "Firme aquí y además regáleme todo su dinero". Si la persona lee la hoja sin verificar, termina ejecutando la orden falsa junto con la original.
3. Ocurre cuando concatenamos texto directamente en las consultas en lugar de usar parámetros seguros. Por ejemplo, al escribir "SELECT * FROM usuario WHERE nombre = '" + txtNombre.getText() + "'" y el usuario ingresa ' OR '1'='1, lo cual salta los filtros de autenticación.
4. Conocer la inyección SQL nos ayuda a crear programas seguros, protegernos contra robos o borrados masivos de información y recordarnos la importancia de validar las entradas del usuario usando herramientas como PreparedStatement.

