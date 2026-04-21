package mx.edu.utez.examenu4.entidades;

import sun.jvm.hotspot.debugger.cdbg.EnumType;

import java.time.LocalDateTime;

@Entity
public class Partida {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fecha;
    @Enumerated(EnumType.STRING)
    private EstadoPartida estado;
    private double apuesta; // Atributo necesario para calcular premios

    @ManyToOne
    private Jugador jugador;

    public boolean getEstado() {
        return false;
    }
    // Getters y Setters
}