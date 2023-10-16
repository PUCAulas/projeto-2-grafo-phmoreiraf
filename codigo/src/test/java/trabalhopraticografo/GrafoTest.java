package trabalhopraticografo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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
        grafo.adicionarCidade(cidadeA);
        grafo.adicionarCidade(cidadeB);
        grafo.adicionarCidade(cidadeC);
        grafo.adicionarAresta(new Aresta(cidadeA, cidadeB, 1));
        grafo.adicionarAresta(new Aresta(cidadeB, cidadeC, 1));

        // Adicione as conexões entre as cidades ao grafo
        cidadeA.adicionarVizinho(cidadeB, 10);
        cidadeA.adicionarVizinho(cidadeC, 15);
        cidadeA.adicionarVizinho(cidadeC, 20);
        cidadeB.adicionarVizinho(cidadeC, 5);

        grafo.adicionarCidade(cidadeA);
        grafo.adicionarCidade(cidadeB);
        grafo.adicionarCidade(cidadeC);
    }

    @Test
    public void testExisteEstradaEntreCidades() {
        assertTrue(grafo.existeEstradaEntreCidades(cidadeA, cidadeB));
        assertTrue(grafo.existeEstradaEntreCidades(cidadeB, cidadeC));
        assertTrue(grafo.existeEstradaEntreCidades(cidadeA, cidadeC));
    }

    @Test
    public void testCidadesInacessiveis() {
        List<Cidade> inacessiveis = grafo.cidadesInacessiveis(cidadeA);
        assertEquals(0, inacessiveis.size());
    }

    @Test
    public void testrecomendarVisitaTodasCidades() {
        List<Cidade> caminhoMinimo = grafo.recomendarVisitaTodasCidades(cidadeA);
        assertEquals(2, caminhoMinimo.size());
        assertEquals(cidadeC.getNome(), caminhoMinimo.get(0).getNome());
        assertEquals(cidadeB.getNome(), caminhoMinimo.get(1).getNome());
    }

    @Test
    public void testCicloHamiltoniano() {
        List<Cidade> cicloHamiltoniano = grafo.cicloHamiltoniano();
        assertNull(cicloHamiltoniano);

        grafo.adicionarAresta(new Aresta(cidadeA, cidadeC, 1));
        cicloHamiltoniano = grafo.cicloHamiltoniano();
        assertNotNull(cicloHamiltoniano);
        assertEquals(3, cicloHamiltoniano.size());
    }

    @Test
    void testEncontrarCidadeMaisProxima() {
        assertNull(grafo.encontrarCidadeMaisProxima(cidadeA));
        assertEquals(null, grafo.encontrarCidadeMaisProxima(cidadeB));
    }

    @Test
    void testIsHamiltoniano() {
        Grafo grafo = new Grafo();

        Cidade cidadeA = new Cidade("A");
        Cidade cidadeB = new Cidade("B");
        Cidade cidadeC = new Cidade("C");
        Cidade cidadeD = new Cidade("D");

        cidadeA.adicionarVizinho(cidadeB, 1);
        cidadeB.adicionarVizinho(cidadeC, 1);
        cidadeC.adicionarVizinho(cidadeD, 1);
        cidadeD.adicionarVizinho(cidadeA, 1);

        grafo.adicionarCidade(cidadeA);
        grafo.adicionarCidade(cidadeB);
        grafo.adicionarCidade(cidadeC);
        grafo.adicionarCidade(cidadeD);

        List<Cidade> rotaHamiltoniana = new ArrayList<>();
        rotaHamiltoniana.add(cidadeA);
        rotaHamiltoniana.add(cidadeB);
        rotaHamiltoniana.add(cidadeC);
        rotaHamiltoniana.add(cidadeD);
        rotaHamiltoniana.add(cidadeA);

        assertFalse(grafo.isHamiltoniano(rotaHamiltoniana));

        List<Cidade> rotaNaoHamiltoniana = new ArrayList<>();
        rotaNaoHamiltoniana.add(cidadeA);
        rotaNaoHamiltoniana.add(cidadeB);
        rotaNaoHamiltoniana.add(cidadeD);
        rotaNaoHamiltoniana.add(cidadeA);

        assertFalse(grafo.isHamiltoniano(rotaNaoHamiltoniana));
    }
}
