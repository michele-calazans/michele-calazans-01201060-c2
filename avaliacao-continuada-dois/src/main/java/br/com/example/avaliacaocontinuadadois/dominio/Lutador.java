package br.com.example.avaliacaocontinuadadois.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
public class Lutador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min = 3, max = 12)
    private String nome;

    @Positive
    private double forcaGolpe;

    @PositiveOrZero
    private double vida = 100.0;

    private int concentracoes = 0;

    private boolean vivo = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getForcaGolpe() {
        return forcaGolpe;
    }

    public void setForcaGolpe(double forcaGolpe) {
        this.forcaGolpe = forcaGolpe;
    }

    public double getVida() {
        return vida;
    }

    public void setVida(double vida) {
        this.vida = vida;
    }

    public int getConcentracoes() {
        return concentracoes;
    }

    public void setConcentracoes(int concentracoes) {
        this.concentracoes = concentracoes;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
}
