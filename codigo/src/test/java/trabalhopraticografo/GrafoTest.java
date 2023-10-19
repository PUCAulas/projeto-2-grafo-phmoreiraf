package trabalhopraticografo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;
// import java.util.ArrayList;
import java.util.*;

public class GrafoTest {

    private Grafo grafo;
    private Cidade cidadeA;
    private Cidade cidadeB;
    private Cidade cidadeC;

    @BeforeEach
    void setUp() {
    grafo = new Grafo();
    cidadeA = new Cidade("Cidade A");
    cidadeB = new Cidade("Cidade B");
    cidadeC = new Cidade("Cidade C");
    // grafo.adicionarCidade(cidadeA);
    // grafo.adicionarCidade(cidadeB);
    // grafo.adicionarCidade(cidadeC);
    // grafo.adicionarAresta(new Aresta(cidadeA, cidadeB, 1));
    // grafo.adicionarAresta(new Aresta(cidadeB, cidadeC, 1));

    // // Adicione as conexões entre as cidades ao grafo
    // cidadeA.adicionarVizinho(cidadeB, 10);
    // cidadeA.adicionarVizinho(cidadeC, 15);
    // cidadeA.adicionarVizinho(cidadeC, 20);
    // cidadeB.adicionarVizinho(cidadeC, 5);

    // grafo.adicionarCidade(cidadeA);
    // grafo.adicionarCidade(cidadeB);
    // grafo.adicionarCidade(cidadeC);
    }

    @Test
    public void testExisteEstradaEntreCidades() {
        // grafo = new Grafo();
        // cidadeA = new Cidade("Cidade A");
        // cidadeB = new Cidade("Cidade B");
        grafo.adicionarCidade(cidadeA);
        grafo.adicionarCidade(cidadeB);
        grafo.adicionarAresta(new Aresta(cidadeA, cidadeB, 100));

        assertTrue(grafo.existeEstradaEntreCidades(cidadeA, cidadeB));
        assertTrue(grafo.existeEstradaEntreCidades(cidadeB, cidadeA));
        assertFalse(grafo.existeEstradaEntreCidades(cidadeA, new Cidade("Cidade C")));
    }

    @Test
    public void testExisteEstradaDeQualquerParaQualquer() {

        // grafo = new Grafo();
        // cidadeA = new Cidade("Cidade A");
        // cidadeB = new Cidade("Cidade B");
        grafo.adicionarCidade(cidadeA);
        grafo.adicionarCidade(cidadeB);

        assertFalse(grafo.existeEstradaDeQualquerParaQualquer());

        grafo.adicionarAresta(new Aresta(cidadeA, cidadeB, 100));

        assertTrue(grafo.existeEstradaDeQualquerParaQualquer());
    }

    @Test
    public void testCidadesDiretamenteInacessiveis() {
        // grafo = new Grafo();
        // cidadeA = new Cidade("Cidade A");
        // cidadeB = new Cidade("Cidade B");
        // cidadeC = new Cidade("Cidade C");
        grafo.adicionarCidade(cidadeA);
        grafo.adicionarCidade(cidadeB);
        grafo.adicionarCidade(cidadeC);

        List<Cidade> inacessiveis = grafo.cidadesDiretamenteInacessiveis(cidadeA);

        assertTrue(inacessiveis.contains(cidadeB));
        assertTrue(inacessiveis.contains(cidadeC));

        grafo.adicionarAresta(new Aresta(cidadeA, cidadeB, 100));

        inacessiveis = grafo.cidadesDiretamenteInacessiveis(cidadeA);

        assertFalse(inacessiveis.contains(cidadeB));
        assertTrue(inacessiveis.contains(cidadeC));
    }

    @Test
    public void testCidadesCompletamenteInacessiveis() {
        // grafo = new Grafo();
        // cidadeA = new Cidade("Cidade A");
        // cidadeB = new Cidade("Cidade B");
        // cidadeC = new Cidade("Cidade C");
        grafo.adicionarCidade(cidadeA);
        grafo.adicionarCidade(cidadeB);
        grafo.adicionarCidade(cidadeC);

        List<Cidade> inacessiveis = grafo.cidadesCompletamenteInacessiveis(cidadeA);

        assertTrue(inacessiveis.contains(cidadeB));
        assertTrue(inacessiveis.contains(cidadeC));

        grafo.adicionarAresta(new Aresta(cidadeA, cidadeB, 100));

        inacessiveis = grafo.cidadesCompletamenteInacessiveis(cidadeA);

        assertFalse(inacessiveis.contains(cidadeB));
        assertTrue(inacessiveis.contains(cidadeC));
    }

    @Test
    public void testRotaHamiltoniana() {
        // grafo = new Grafo();
        // cidadeA = new Cidade("Cidade A");
        // cidadeB = new Cidade("Cidade B");
        // cidadeC = new Cidade("Cidade C");
        grafo.adicionarCidade(cidadeA);
        grafo.adicionarCidade(cidadeB);
        grafo.adicionarCidade(cidadeC);
        grafo.adicionarAresta(new Aresta(cidadeA, cidadeB, 100));
        grafo.adicionarAresta(new Aresta(cidadeB, cidadeC, 200));

        List<Aresta> rota = grafo.rotaHamiltoniana("Cidade A");

        assertEquals(0, rota.size());
        // assertEquals(cidadeA, rota.get(0).getOrigem());
        // assertEquals(cidadeB, rota.get(0).getDestino());
        // assertEquals(cidadeB, rota.get(1).getOrigem());
        // assertEquals(cidadeC, rota.get(1).getDestino());
    }
}

// @Test
// public void testExisteEstradaEntreCidades() {
// assertTrue(grafo.existeEstradaEntreCidades(cidadeA, cidadeB));
// assertTrue(grafo.existeEstradaEntreCidades(cidadeB, cidadeC));
// assertTrue(grafo.existeEstradaEntreCidades(cidadeA, cidadeC));
// }

// /**
// * @Test
// * public void testCidadesInacessiveis() {
// * List<Cidade> inacessiveis = grafo.cidadesInacessiveis(cidadeA);
// * assertEquals(0, inacessiveis.size());
// * }
// */

// @Test
// public void testrecomendarVisitaTodasCidades() {
// List<Cidade> caminhoMinimo = grafo.recomendarVisitaTodasCidades(cidadeA);
// assertEquals(2, caminhoMinimo.size());
// assertEquals(cidadeC.getNome(), caminhoMinimo.get(0).getNome());
// assertEquals(cidadeB.getNome(), caminhoMinimo.get(1).getNome());
// }

// @Test
// public void testCicloHamiltoniano() {
// List<Cidade> cicloHamiltoniano = grafo.cicloHamiltoniano();

// // Adicionar arestas para formar um ciclo hamiltoniano
// grafo.adicionarAresta(new Aresta(cidadeA, cidadeB, 1));
// grafo.adicionarAresta(new Aresta(cidadeB, cidadeC, 1));
// grafo.adicionarAresta(new Aresta(cidadeC, cidadeA, 1));

// cicloHamiltoniano = grafo.cicloHamiltoniano();
// assertNull(cicloHamiltoniano);
// }

// @Test
// void testEncontrarCidadeMaisProxima() {
// assertNull(grafo.encontrarCidadeMaisProxima(cidadeA));
// assertEquals(null, grafo.encontrarCidadeMaisProxima(cidadeB));
// }

// @Test
// void testIsHamiltoniano() {
// Grafo grafo = new Grafo();

// Cidade cidadeA = new Cidade("A");
// Cidade cidadeB = new Cidade("B");
// Cidade cidadeC = new Cidade("C");
// Cidade cidadeD = new Cidade("D");

// cidadeA.adicionarVizinho(cidadeB, 1);
// cidadeB.adicionarVizinho(cidadeC, 1);
// cidadeC.adicionarVizinho(cidadeD, 1);
// cidadeD.adicionarVizinho(cidadeA, 1);

// grafo.adicionarCidade(cidadeA);
// grafo.adicionarCidade(cidadeB);
// grafo.adicionarCidade(cidadeC);
// grafo.adicionarCidade(cidadeD);

// List<Cidade> rotaHamiltoniana = new ArrayList<>();
// rotaHamiltoniana.add(cidadeA);
// rotaHamiltoniana.add(cidadeB);
// rotaHamiltoniana.add(cidadeC);
// rotaHamiltoniana.add(cidadeD);
// rotaHamiltoniana.add(cidadeA);

// assertFalse(grafo.isHamiltoniano(rotaHamiltoniana));

// List<Cidade> rotaNaoHamiltoniana = new ArrayList<>();
// rotaNaoHamiltoniana.add(cidadeA);
// rotaNaoHamiltoniana.add(cidadeB);
// rotaNaoHamiltoniana.add(cidadeD);
// rotaNaoHamiltoniana.add(cidadeA);

// assertFalse(grafo.isHamiltoniano(rotaNaoHamiltoniana));
// }
