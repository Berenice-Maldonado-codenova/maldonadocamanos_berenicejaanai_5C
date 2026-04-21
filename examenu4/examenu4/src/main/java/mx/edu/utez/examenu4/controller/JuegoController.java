package mx.edu.utez.examenu4.controller;

import mx.edu.utez.examenu4.entidades.Partida;
import mx.edu.utez.examenu4.entidades.Tiro;
import mx.edu.utez.examenu4.service.JuegoService;

import java.util.List;

@RestController
@RequestMapping("/api/juego")
public class JuegoController {
    @Autowired
    private JuegoService juegoService;

    @PostMapping("/iniciar")
    public ResponseEntity<Partida> iniciar(@RequestBody IniciarPartidaDTO dto) {
        return ResponseEntity.ok(juegoService.iniciarPartida(dto.jugadorId(), dto.apuesta()));
    }

    @PostMapping("/tiro/{partidaId}")
    public ResponseEntity<Tiro> tirar(@PathVariable Long partidaId) {
        return ResponseEntity.ok(juegoService.realizarTiro(partidaId));
    }

    @GetMapping("/historial/{jugadorId}")
    public List<Partida> historial(@PathVariable Long jugadorId) {
        return partidaRepo.findByJugadorId(jugadorId);
    }
}
