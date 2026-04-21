package mx.edu.utez.examenu4.entidades;

@Entity
public class Jugador {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private double saldo;
    private boolean activo;
}

