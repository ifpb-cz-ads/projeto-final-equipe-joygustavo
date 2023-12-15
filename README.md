O projeto desenvolvido é uma implementação do clássico jogo Campo Minado em Java, utilizando conceitos de Programação Orientada a Objetos (POO) e interface gráfica com o pacote javax.swing.

Visão Geral do Projeto:

Página Inicial:

*Tela inicial do jogo com o nome "Campo Minado" e instruções de como jogar.
*Opção para escolher o nível de dificuldade (Fácil, Médio, Difícil).
*Botão "Começar Jogo" para iniciar o jogo com a dificuldade escolhida.

Campo Minado:

*O jogo é representado por um campo de botões, onde cada botão corresponde a uma célula.
*O jogador pode clicar nos botões para revelar as células do campo.
*O objetivo é evitar clicar em bombas e revelar todas as células seguras.

Funcionalidades Implementadas:

*Geração aleatória de bombas no início do jogo.
*Contagem de bombas vizinhas para cada célula.
*Revelação automática de células sem bombas vizinhas.
*Interface gráfica usando imagens para representar bombas, células vazias e números de bombas vizinhas.
*Botões de controle na parte inferior para voltar à página inicial, escolher o modo ou reiniciar a fase.
*Página inicial em tela cheia, com opção para escolher o nível de dificuldade.

Persistência de Dados:

*Não foi implementada uma persistência de dados robusta nesse projeto. Entretanto, a estrutura permite fácil extensão para salvar e carregar o estado do jogo em arquivos.
