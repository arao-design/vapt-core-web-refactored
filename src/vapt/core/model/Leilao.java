/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vapt.core.model;

import vapt.core.enums.StatusLeilao;

public class Leilao {

    private Long id;
    private String tituloItem;
    private double valorInicial;
    private double maiorLance;
    private Long vendedorId;
    private Long compradorVencedorId;
    private StatusLeilao status;
    private Rastreio rastreio;

    public Leilao() {
        this.status = StatusLeilao.ATIVO;
    }

    public Leilao(Long id, String tituloItem, double valorInicial, Long vendedorId) {
        this.id = id;
        this.tituloItem = tituloItem;
        this.valorInicial = valorInicial;
        this.maiorLance = valorInicial;
        this.vendedorId = vendedorId;
        this.status = StatusLeilao.ATIVO;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTituloItem() { return tituloItem; }
    public void setTituloItem(String tituloItem) { this.tituloItem = tituloItem; }

    public double getValorInicial() { return valorInicial; }
    public void setValorInicial(double valorInicial) { this.valorInicial = valorInicial; }

    public double getMaiorLance() { return maiorLance; }
    public void setMaiorLance(double maiorLance) { this.maiorLance = maiorLance; }

    public Long getVendedorId() { return vendedorId; }
    public void setVendedorId(Long vendedorId) { this.vendedorId = vendedorId; }

    public Long getCompradorVencedorId() { return compradorVencedorId; }
    public void setCompradorVencedorId(Long compradorVencedorId) { this.compradorVencedorId = compradorVencedorId; }

    public StatusLeilao getStatus() { return status; }
    public void setStatus(StatusLeilao status) { this.status = status; }

    public Rastreio getRastreio() { return rastreio; }
    public void setRastreio(Rastreio rastreio) { this.rastreio = rastreio; }
}