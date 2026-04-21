package mx.edu.utez.examenu4.service;

import mx.edu.utez.examenu4.entidades.Jugador;
import mx.edu.utez.examenu4.entidades.Partida;
import mx.edu.utez.examenu4.entidades.Tiro;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class JuegoService {
    @Autowired private JugadorRepository jugadorRepo;
    @Autowired private PartidaRepository partidaRepo;
    @Autowired private TiroRepository tiroRepo;

    @Transactional
    public Partida iniciarPartida(Long jugadorId, double apuesta) {
        Jugador jugador = jugadorRepo.findById(jugadorId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        if (!jugador.isActivo()) throw new RuntimeException("Jugador inactivo");
        if (jugador.getSaldo() < apuesta) throw new RuntimeException("Saldo insuficiente");

        jugador.setSaldo(jugador.getSaldo() - apuesta);
        jugadorRepo.save(jugador);

        Partida partida = new Partida();
        partida.setJugador(jugador);
        partida.setApuesta(apuesta);
        partida.setFecha(LocalDateTime.now());
        partida.setEstado(EstadoPartida.EN_JUEGO);
        return partidaRepo.save(partida);
    }

    @Transactional
    public Tiro realizarTiro(Long partidaId) {
        Partida partida = partidaRepo.findById(partidaId)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        if (partida.getEstado() != EstadoPartida.EN_JUEGO)
            throw new RuntimeException("La partida ya ha finalizado");

        Random random = new Random();
        int d1 = random.nextInt(6) + 1;
        int d2 = random.nextInt(6) + 1;
        int suma = d1 + d2;

        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(d1);
        tiro.setValorDado2(d2);
        tiro.setSuma(suma);

        if (suma == 7 || suma == 11) {
            tiro.setEsGanador(true);
            partida.setEstado(EstadoPartida.FINALIZADA);
            Jugador j = partida.getJugador();
            j.setSaldo(j.getSaldo() + (partida.getApuesta() * 2));
            jugadorRepo.save(j);
        } else if (suma == 2 || suma == 3 || suma == 12) {
            tiro.setEsGanador(false);
            partida.setEstado(EstadoPartida.FINALIZADA);
        } else {
            tiro.setEsGanador(false);
        }

        partidaRepo.save(partida);
        return tiroRepo.save(tiro);
    }

    public void finalizarManual(Long partidaId) {
        Partida p = partidaRepo.findById(partidaId).get();
        p.setEstado(EstadoPartida.FINALIZADA);
        partidaRepo.save(p);
    }
}