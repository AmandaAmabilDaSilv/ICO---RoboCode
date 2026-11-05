# ICO – RoboCode

Projeto da disciplina de ICO (IFSC) em que o grupo programa robôs-tanque em Java para
lutar no [Robocode](https://robocode.sourceforge.io/), um jogo de programação em que
robôs controlados por código se enfrentam em uma arena.

O repositório contém uma instalação completa do **Robocode 1.11.1** (pastas `libs/`,
`compilers/`, `config/`, `javadoc/`, `battles/`, robôs de exemplo em `robots/sample*`
etc.). O código do grupo fica em:

| Caminho | Conteúdo |
|---|---|
| `robots/Tristeza/Samara.java` | Robô **Samara** |

## Robô Samara

Robô do pacote `Tristeza`, baseado no exemplo `sample.Corners`, nas cores roxo e rosa.

- **Procura de inimigos:** fica parada e gira o canhão (com o radar junto) 20° por vez,
  invertendo o sentido após 360 passos.
- **Ao ver um inimigo:** atira com força escolhida pela distância e pela energia
  (força 1 acima de 200 px ou com energia abaixo de 15; força 2 entre 50 e 200 px;
  força 3 até 50 px). Em seguida vira na perpendicular ao inimigo e anda 100 px para
  dificultar os tiros dele.
- **Ao ser atingida por uma bala:** atira, vira 30° e recua 100 px.
- **Ao bater na parede:** gira e dá ré 150 px.
- **Ao colidir com outro robô:** atira com força máxima e se afasta 80 px (recua se o
  robô está à frente, avança se está atrás).
- **Ao vencer a rodada:** gira o corpo e o canhão comemorando e imprime uma mensagem.

Os valores de distância, energia e giro estão como constantes no topo da classe.

## Como rodar uma batalha

É necessário ter o **Java** instalado.

### Com a interface gráfica

1. Na raiz do projeto, rode:
   ```sh
   ./robocode.sh
   ```
2. **Compile a Samara** antes de lutar, para usar a versão mais recente do código:
   menu **Robot → Source Editor**, abra `Tristeza/Samara.java` e use **Compiler → Compile**.
3. Menu **Battle → New**, selecione `Tristeza.Samara` e os adversários
   (por exemplo `sample.SittingDuck`, `sample.Walls`, `sample.SpinBot`), clique em
   **Add** e depois em **Start Battle**.

Também é possível abrir uma batalha pronta em **Battle → Open** (arquivos `.battle` da
pasta `battles/`).

### Sem interface gráfica

Uma batalha descrita em um arquivo `.battle` pode ser rodada pela linha de comando,
gravando o placar em um arquivo:

```sh
java -cp "libs/*" -Xmx512M \
  --add-opens=java.base/sun.net.www.protocol.jar=ALL-UNNAMED \
  --add-opens=java.base/java.lang.reflect=ALL-UNNAMED \
  robocode.Robocode -battle battles/sample.battle \
  -nodisplay -nosound -results resultados.txt
```

Para incluir a Samara, crie um arquivo `.battle` com a linha
`robocode.battle.selectedRobots=` listando `Tristeza.Samara` e os adversários
(confira o nome exato do robô na tela **Battle → New**).
