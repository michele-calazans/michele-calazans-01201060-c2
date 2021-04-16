package br.com.example.avaliacaocontinuadadois.controllers;

import br.com.example.avaliacaocontinuadadois.dominio.Lutador;
import br.com.example.avaliacaocontinuadadois.dominio.RequestGolpe;
import br.com.example.avaliacaocontinuadadois.repositories.LutadorRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lutadores")
public class LutadorController {
    @Autowired
    private LutadorRepository lutadorRepository;

    @PostMapping
    public ResponseEntity postLutador(@RequestBody Lutador lutador) {
        lutadorRepository.save(lutador);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity getLutadores() {
        return ResponseEntity.status(201).body(lutadorRepository.findByOrderByForcaGolpeDesc());
    }

    @GetMapping("/contagem-vivos")
    public ResponseEntity getLutadoresVivos() {
        int contagem = 0;
        List<Lutador> lutadores = lutadorRepository.findAll();

        for (int i = 0; i < lutadores.size(); i++) {
            Lutador lutador = lutadores.get(i);

            if (lutador.isVivo()) {
                contagem++;
            }
        }

        return ResponseEntity.status(201).body(contagem);
    }

    @PostMapping("/{id}/concentrar")
    public ResponseEntity concentrar(@PathVariable Integer id) {
        if (lutadorRepository.existsById(id)) {
            Lutador lutador = lutadorRepository.findById(id).get();
            int qtdConcentracoes = lutador.getConcentracoes();

            if (qtdConcentracoes < 3) {
                lutador.setConcentracoes(qtdConcentracoes + 1);
                lutador.setVida(lutador.getVida() * 1.15);
                lutadorRepository.save(lutador);
                return ResponseEntity.status(200).build();

            } else {
                return ResponseEntity.status(400).body("Lutador já se concentrou 3 vezes!");
            }
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/golpe")
    public ResponseEntity postGolpe(@RequestBody RequestGolpe golpe) {
        int idLutadorUm = golpe.getIdLutadorApanha();
        int idLutadorDois = golpe.getIdLutadorBate();

        if (lutadorRepository.existsById(idLutadorUm) && lutadorRepository.existsById(idLutadorDois)) {
            Lutador lutadorApanha = lutadorRepository.findById(idLutadorUm).get();
            Lutador lutadorBate = lutadorRepository.findById(idLutadorDois).get();

            if (lutadorApanha.isVivo() && lutadorBate.isVivo()) {

                lutadorApanha.setVida(lutadorApanha.getVida() - lutadorBate.getForcaGolpe());

                if (lutadorApanha.getVida() <= 0) {
                    lutadorApanha.setVivo(false);
                }

                lutadorRepository.save(lutadorApanha);

                List<Lutador> lutadorList = new ArrayList<>();

                lutadorList.add(lutadorApanha);
                lutadorList.add(lutadorBate);

                return ResponseEntity.status(201).body(lutadorList);
            } else {
                return ResponseEntity.status(400).body("Ambos os lutadores devem estar vivos!");
            }
        } else {
            return ResponseEntity.status(404).build();
        }
    }


    @GetMapping("/mortos")
    public ResponseEntity getMortos() {
        List<Lutador> lutadoresMortos = new ArrayList<>();
        List<Lutador> lutadores = lutadorRepository.findAll();

        for (int i = 0; i < lutadores.size(); i++) {
            Lutador lutador = lutadores.get(i);

            if (!lutador.isVivo()) {
                lutadoresMortos.add(lutador);
            }
        }

        if (lutadores.size() > 0) {
            return ResponseEntity.status(200).body(lutadoresMortos);
        } else {
            return ResponseEntity.status(204).body(lutadoresMortos);
        }
    }
}
