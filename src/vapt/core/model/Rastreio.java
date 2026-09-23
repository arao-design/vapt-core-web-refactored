/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vapt.core.model;
import vapt.core.enums.TransportadoraHomologada;


public class Rastreio {
private TransportadoraHomologada transportadora;
    private String codigoRastreio;
    private boolean entregue;

    public Rastreio(TransportadoraHomologada transportadora, String codigoRastreio) {
        this.transportadora = transportadora;
        this.codigoRastreio = codigoRastreio;
        this.entregue = false;
    }

    public TransportadoraHomologada getTransportadora() { return transportadora; }
    public String getCodigoRastreio() { return codigoRastreio; }
    public boolean isEntregue() { return entregue; }
    public void setEntregue(boolean entregue) { this.entregue = entregue; }
}
