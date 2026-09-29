/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vapt.core.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import vapt.core.model.Leilao;
import vapt.core.service.LeilaoService;

public class ServerApp {

    private static Leilao leilao = new Leilao(1L, "Notebook Gamer", 1000.0, 101L);
    private static LeilaoService service = new LeilaoService();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // Rota principal (exibe a tela)
        server.createContext("/leilao", new LeilaoHandler());
        
        // Rota para processar o lance enviado pelo formulário
        server.createContext("/leilao/lance", new LanceHandler());

        server.setExecutor(null);
        System.out.println("Servidor iniciado em: http://localhost:8080/leilao");
        server.start();
    }

    static class LeilaoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = renderHTML(null, null);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes(StandardCharsets.UTF_8));
            os.close();
        }
    }

    static class LanceHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String formData = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                Map<String, String> params = parseFormData(formData);

                String msgSucesso = null;
                String msgErro = null;

                try {
                    Long compradorId = Long.parseLong(params.getOrDefault("compradorId", "0"));
                    Double valorLance = Double.parseDouble(params.getOrDefault("valorLance", "0"));
                    boolean aceitaTermos = "true".equals(params.get("aceitaTermos"));

                    service.registrarLance(leilao, compradorId, valorLance, aceitaTermos);
                    msgSucesso = "Lance de R$ " + valorLance + " registrado com sucesso!";
                } catch (Exception e) {
                    msgErro = e.getMessage();
                }

                String response = renderHTML(msgSucesso, msgErro);
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
        }
    }

    private static Map<String, String> parseFormData(String formData) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length > 1) {
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                map.put(key, value);
            }
        }
        return map;
    }

    private static String renderHTML(String msgSucesso, String msgErro) {
    StringBuilder html = new StringBuilder();
    html.append("<!DOCTYPE html><html lang='pt-BR'><head><meta charset='UTF-8'>");
    html.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
    html.append("<title>VaptCore - Plataforma de Leilões</title>");
    html.append("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap' rel='stylesheet'>");
    
    // Estilização CSS Integrada
    html.append("<style>");
    html.append("* { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Inter', sans-serif; }");
    html.append("body { background-color: #0f172a; color: #f8fafc; min-height: 100vh; display: flex; flex-direction: column; align-items: center; padding: 30px 15px; }");
    html.append(".header { text-align: center; margin-bottom: 30px; }");
    html.append(".header h1 { font-size: 2.2rem; font-weight: 700; background: linear-gradient(90deg, #38bdf8, #818cf8); -webkit-background-clip: text; -webkit-text-fill-color: transparent; }");
    html.append(".header p { color: #94a3b8; font-size: 0.95rem; margin-top: 5px; }");
    
    html.append(".container { display: grid; grid-template-columns: 1fr 1fr; gap: 25px; max-width: 900px; width: 100%; }");
    html.append("@media (max-width: 768px) { .container { grid-template-columns: 1fr; } }");
    
    html.append(".card { background: #1e293b; border: 1fr solid #334155; border-radius: 16px; padding: 25px; box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.3); }");
    html.append(".card-title { font-size: 1.1rem; font-weight: 600; color: #cbd5e1; margin-bottom: 15px; display: flex; justify-content: space-between; align-items: center; }");
    html.append(".badge { background: #0284c7; color: #fff; padding: 4px 10px; border-radius: 20px; font-size: 0.75rem; font-weight: 600; }");
    
    html.append(".item-display { text-align: center; margin: 20px 0; }");
    html.append(".item-name { font-size: 1.5rem; color: #f1f5f9; font-weight: 700; margin-bottom: 10px; }");
    html.append(".price-box { background: #0f172a; border: 1px solid #334155; padding: 15px; border-radius: 12px; margin-top: 15px; }");
    html.append(".price-label { font-size: 0.85rem; color: #64748b; text-transform: uppercase; letter-spacing: 0.05em; }");
    html.append(".price-value { font-size: 2.2rem; color: #4ade80; font-weight: 700; margin-top: 5px; }");
    
    html.append(".form-group { margin-bottom: 18px; }");
    html.append("label { display: block; font-size: 0.85rem; color: #94a3b8; margin-bottom: 6px; font-weight: 500; }");
    html.append("input[type='number'] { width: 100%; background: #0f172a; border: 1px solid #334155; padding: 12px; border-radius: 8px; color: #fff; font-size: 1rem; outline: none; transition: 0.2s; }");
    html.append("input[type='number']:focus { border-color: #38bdf8; box-shadow: 0 0 0 2px rgba(56, 189, 248, 0.2); }");
    
    html.append(".checkbox-group { display: flex; align-items: center; gap: 10px; margin-bottom: 20px; }");
    html.append(".checkbox-group input { width: 18px; height: 18px; accent-color: #38bdf8; cursor: pointer; }");
    html.append(".checkbox-group label { margin: 0; cursor: pointer; }");
    
    html.append("button { width: 100%; background: linear-gradient(135deg, #2563eb, #1d4ed8); color: white; border: none; padding: 14px; border-radius: 8px; font-size: 1rem; font-weight: 600; cursor: pointer; transition: transform 0.1s, background 0.2s; }");
    html.append("button:hover { background: linear-gradient(135deg, #1d4ed8, #1e40af); transform: translateY(-1px); }");
    
    html.append(".alert { padding: 12px 15px; border-radius: 8px; font-size: 0.9rem; margin-top: 15px; text-align: center; }");
    html.append(".alert-success { background: rgba(74, 222, 128, 0.1); color: #4ade80; border: 1px solid rgba(74, 222, 128, 0.2); }");
    html.append(".alert-error { background: rgba(248, 113, 113, 0.1); color: #f87171; border: 1px solid rgba(248, 113, 113, 0.2); }");
    html.append("</style></head><body>");

    // Cabeçalho
    html.append("<div class='header'>");
    html.append("<h1>VaptCore Web</h1>");
    html.append("<p>Painel de Monitoramento de Lances em Tempo Real</p>");
    html.append("</div>");

    html.append("<div class='container'>");

    // Card do Item / Maior Lance
    html.append("<div class='card'>");
    html.append("<div class='card-title'><span>Item Ativo</span> <span class='badge'>EM ANDAMENTO</span></div>");
    html.append("<div class='item-display'>");
    html.append("<div class='item-name'>").append(leilao.getTituloItem()).append("</div>");
    html.append("<div class='price-box'>");
    html.append("<div class='price-label'>Maior Lance Atual</div>");
    html.append("<div class='price-value'>R$ ").append(String.format("%.2f", leilao.getMaiorLance())).append("</div>");
    html.append("</div></div></div>");

    // Card do Formulário de Envio
    html.append("<div class='card'>");
    html.append("<div class='card-title'><span>Dar um Lance</span></div>");
    html.append("<form action='/leilao/lance' method='POST'>");
    
    html.append("<div class='form-group'>");
    html.append("<label for='compradorId'>ID do Comprador</label>");
    html.append("<input type='number' id='compradorId' name='compradorId' placeholder='Ex: 101' required>");
    html.append("</div>");

    html.append("<div class='form-group'>");
    html.append("<label for='valorLance'>Valor do Lance (R$)</label>");
    html.append("<input type='number' step='0.01' id='valorLance' name='valorLance' placeholder='0,00' required>");
    html.append("</div>");

    html.append("<div class='checkbox-group'>");
    html.append("<input type='checkbox' id='aceitaTermos' name='aceitaTermos' value='true' required>");
    html.append("<label for='aceitaTermos'>Concordo com os termos do leilão</label>");
    html.append("</div>");

    html.append("<button type='submit'>Confirmar e Enviar Lance</button>");
    html.append("</form>");

    if (msgSucesso != null) {
        html.append("<div class='alert alert-success'>").append(msgSucesso).append("</div>");
    }
    if (msgErro != null) {
        html.append("<div class='alert alert-error'>").append(msgErro).append("</div>");
    }

    html.append("</div></div></body></html>");
    return html.toString();
    }
}