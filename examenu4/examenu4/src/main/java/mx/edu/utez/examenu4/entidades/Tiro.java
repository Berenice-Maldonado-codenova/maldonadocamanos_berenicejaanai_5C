package mx.edu.utez.examenu4.entidades;

@Entity
public class Tiro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int valorDado1;
    private int valorDado2;
    private int suma;
    private boolean esGanador;

    @ManyToOne
    private Partida partida;

    public void setPartida(Partida partida) {
    }

    public void setValorDado1(int d1) {
    }

    public void setEsGanador(boolean b) {
        
    }
    // Getters y Setters
}