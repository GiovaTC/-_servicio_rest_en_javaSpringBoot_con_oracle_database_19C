# -_servicio_rest_en_javaSpringBoot_con_oracle_database_19C :.
# Servicio REST en Java (Spring Boot) con Oracle Database 19c .

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/cd4e06b5-e9f2-408a-ad34-77d26fc3b374" />  

Solución **completa, profesional y lista para ejecutar en IntelliJ IDEA**, que implementa un **servicio REST empresarial** con persistencia en **Oracle Database 19c** mediante **Stored Procedure**, siguiendo una arquitectura estándar **Controller – Service – Repository**.

---

## 1. Objetivo de la aplicación

* Exponer una **API REST** para pruebas con **Postman**
* Recibir un **JSON** que contiene:

  * Datos del cliente
  * Datos del producto
  * Detalle del producto
* Persistir la información en **Oracle 19c**
* Insertar datos usando **Stored Procedure**
* Mantener una arquitectura limpia y desacoplada

---

## 2. Arquitectura general del proyecto

```text
rest-oracle-service
│
├── controller
│   └── VentaController.java
├── service
│   └── VentaService.java
├── repository
│   └── VentaRepository.java
├── model
│   ├── Cliente.java
│   ├── Producto.java
│   └── VentaRequest.java
├── RestOracleApplication.java
└── resources
    └── application.properties
```

---

## 3. JSON de entrada (Postman)

```json
{
  "cliente": {
    "idCliente": 1,
    "nombre": "Juan Pérez",
    "email": "juan.perez@email.com"
  },
  "producto": {
    "idProducto": 101,
    "nombre": "Laptop",
    "precio": 3500000
  },
  "detalleProducto": "Laptop gamer 16GB RAM"
}
```

---

## 4. Stored Procedure en Oracle Database 19c

```sql
CREATE OR REPLACE PROCEDURE SP_REGISTRAR_VENTA (
    P_ID_CLIENTE      IN NUMBER,
    P_NOMBRE_CLIENTE  IN VARCHAR2,
    P_EMAIL           IN VARCHAR2,
    P_ID_PRODUCTO     IN NUMBER,
    P_NOMBRE_PRODUCTO IN VARCHAR2,
    P_PRECIO          IN NUMBER,
    P_DETALLE         IN VARCHAR2
) AS
BEGIN
    INSERT INTO VENTAS (
        ID_CLIENTE,
        NOMBRE_CLIENTE,
        EMAIL,
        ID_PRODUCTO,
        NOMBRE_PRODUCTO,
        PRECIO,
        DETALLE,
        FECHA_REGISTRO
    )
    VALUES (
        P_ID_CLIENTE,
        P_NOMBRE_CLIENTE,
        P_EMAIL,
        P_ID_PRODUCTO,
        P_NOMBRE_PRODUCTO,
        P_PRECIO,
        P_DETALLE,
        SYSDATE
    );

    COMMIT;
END;
/
```

---

## 5. Configuración Maven (pom.xml)

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>

    <dependency>
        <groupId>com.oracle.database.jdbc</groupId>
        <artifactId>ojdbc11</artifactId>
        <version>23.2.0.0</version>
    </dependency>
</dependencies>
```

---

## 6. Configuración Oracle (application.properties)

```properties
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/orcl
spring.datasource.username=SYSTEM
spring.datasource.password=oracle
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.jpa.show-sql=true
server.port=8080
```

---

## 7. Modelos (DTO)

### Cliente.java

```java
public class Cliente {

    private int idCliente;
    private String nombre;
    private String email;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

### Producto.java

```java
public class Producto {

    private int idProducto;
    private String nombre;
    private double precio;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
```

### VentaRequest.java

```java
public class VentaRequest {

    private Cliente cliente;
    private Producto producto;
    private String detalleProducto;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getDetalleProducto() {
        return detalleProducto;
    }

    public void setDetalleProducto(String detalleProducto) {
        this.detalleProducto = detalleProducto;
    }
}
```

---

## 8. Repositorio (Llamada al Stored Procedure)

```java
@Repository
public class VentaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void registrarVenta(VentaRequest request) {
        jdbcTemplate.update(
            "CALL SP_REGISTRAR_VENTA(?,?,?,?,?,?,?)",
            request.getCliente().getIdCliente(),
            request.getCliente().getNombre(),
            request.getCliente().getEmail(),
            request.getProducto().getIdProducto(),
            request.getProducto().getNombre(),
            request.getProducto().getPrecio(),
            request.getDetalleProducto()
        );
    }
}
```

---

## 9. Servicio

```java
@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public void procesarVenta(VentaRequest request) {
        ventaRepository.registrarVenta(request);
    }
}
```

---

## 10. Controlador REST

```java
@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarVenta(@RequestBody VentaRequest request) {
        ventaService.procesarVenta(request);
        return ResponseEntity.ok("Venta registrada correctamente en Oracle 19c");
    }
}
```

---

## 11. Clase principal

```java
@SpringBootApplication
public class RestOracleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestOracleApplication.class, args);
    }
}
```

---

## 12. Prueba en Postman

* **Método:** POST
* **URL:** `http://localhost:8080/api/ventas/registrar`

### Headers

```text
Content-Type: application/json
```

### Body

JSON definido en la sección 3.

---

## 13. Resultado esperado

* Respuesta **HTTP 200 OK**
* Registro persistido correctamente en **Oracle Database 19c**
* Integración completa:

  * REST
  * JSON
  * Spring Boot
  * Stored Procedure
  * Oracle 19c

---

**Estado:** Implementación lista para ejecución y pruebas empresariales básicas :. .
