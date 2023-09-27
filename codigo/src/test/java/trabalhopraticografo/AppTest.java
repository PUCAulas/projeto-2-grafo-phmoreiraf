package trabalhopraticografo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.*;
import org.junit.jupiter.api.BeforeEach;

public class AppTest {

    private Grafo grafo;
    private Cidade cidadeA;
    private Cidade cidadeB;
    private Cidade cidadeC;
    private Cidade cidadeD;

    @BeforeEach
    void setUp() {
        grafo = new Grafo();
        cidadeA = new Cidade("Cidade A");
        cidadeB = new Cidade("Cidade B");
        cidadeC = new Cidade("Cidade C");
        cidadeD = new Cidade("Cidade D");

        // Adicione as conexões entre as cidades ao grafo
        cidadeA.adicionarVizinho(cidadeB, 10);
        cidadeA.adicionarVizinho(cidadeC, 15);
        cidadeA.adicionarVizinho(cidadeC, 20);
        cidadeB.adicionarVizinho(cidadeC, 5);

        grafo.adicionarCidade(cidadeA);
        grafo.adicionarCidade(cidadeB);
        grafo.adicionarCidade(cidadeC);
        grafo.adicionarCidade(cidadeD);
    }

    @Test
    void testExisteEstradaEntreCidades() {
        assertTrue(grafo.existeEstradaEntreCidades(cidadeA, cidadeB));
        assertFalse(grafo.existeEstradaEntreCidades(cidadeA, cidadeD));
    }

    @Test
    void testIdentificarCidadesInacessiveis() {
        assertEquals(0, grafo.identificarCidadesInacessiveis(cidadeA).size());
        assertEquals(3, grafo.identificarCidadesInacessiveis(cidadeD).size());
    }

    @Test
    void testRecomendarVisitaTodasCidades() {
        assertEquals(grafo.getCidades().size(), grafo.recomendarVisitaTodasCidades(cidadeA).size());
    }

    @Test
    void testRecomendarRotaPassageiro() {
        assertTrue(grafo.recomendarRotaPassageiro(cidadeA).size() >= 1);
        assertTrue(grafo.recomendarRotaPassageiro(cidadeC).size() >= 1);
    }

    @Test
    void testEncontrarCidadeMaisProxima() {
        assertNull(grafo.encontrarCidadeMaisProxima(cidadeA));
        assertEquals(null, grafo.encontrarCidadeMaisProxima(cidadeB));
    }
}
