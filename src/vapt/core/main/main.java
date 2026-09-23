/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vapt.core.main;

import vapt.core.enums.StatusLeilao;
import vapt.core.enums.TransportadoraHomologada;
import vapt.core.model.Leilao;
import vapt.core.service.LeilaoService;

public class main {
public static void main(String[] args) {
        System.out.println("=== TESTE DE VALIDAÇÃO CORE VAPT ===");
        
        LeilaoService service = new LeilaoService();
        
        // 1. Criar Leilão
        Leilao leilao = new Leilao(101L, "Smartphone Vapt Pro", 500.0, 10L);
        System.out.println("1. Leilão Criado. Valor Inicial: R$ " + leilao.getMaiorLance());

        // 2. Comprador dá lance com aceite dos termos
        service.registrarLance(leilao, 20L, 650.0, true);
        System.out.println("2. Novo Maior Lance Registrado: R$ " + leilao.getMaiorLance() + " (Comprador ID: " + leilao.getCompradorVencedorId() + ")");

        // 3. Encerramento simulado do Leilão
        leilao.setStatus(StatusLeilao.EM_VALIDACAO_CHAT);
        System.out.println("3. Status do Leilão alterado para: " + leilao.getStatus());

        // 4. Comprador clica em "Já tirei minhas dúvidas!"
        service.confirmarValidacaoChat(leilao, 20L, true);
        System.out.println("4. Validação do Chat efetuada. Status atual: " + leilao.getStatus());

        // 5. Leiloador registra envio com transportadora homologada (Ex: Loggi)
        service.registrarEnvio(leilao, 10L, TransportadoraHomologada.LOGGI, "LOGGI-883921-BR");
        System.out.println("5. Envio Registrado via " + leilao.getRastreio().getTransportadora() + " - Código: " + leilao.getRastreio().getCodigoRastreio());
        System.out.println("   Status Final: " + leilao.getStatus());

        System.out.println("\n=== TODOS OS TESTES FORAM EXECUTADOS COM SUCESSO! ===");
    }
}
