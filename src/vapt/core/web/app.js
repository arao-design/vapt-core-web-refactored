/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


formLance.addEventListener("submit", async (e) => {
    e.preventDefault();
    const novoLance = parseFloat(inputLance.value);

    const response = await fetch('/api/leiloes/1/lance?valor=' + novoLance, {
        method: 'POST'
    });

    if (response.ok) {
        const data = await response.json();
        maiorLanceDisplay.textContent = `R$ ${data.valorAtual.toFixed(2)}`;
        alert("Lance registrado e salvo no banco de dados com sucesso!");
    } else {
        const erro = await response.text();
        alert("Erro: " + erro);
    }
});