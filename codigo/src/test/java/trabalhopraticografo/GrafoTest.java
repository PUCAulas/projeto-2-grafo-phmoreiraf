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
        cidadeA.adicionarVizinho(cidadeD, 20);
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
        assertFalse(grafo.existeEstradaEntreCidades(cidadeC, cidadeB));
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

    /**
     * 
     */
    @Test
    void testRecomendarRotaPassageiro() {
        // Substitua as cidades de origem e destino pelos nomes corretos no seu grafo
        Cidade cidadeOrigem = grafo.buscarCidadePorNome("Cidade A");
        assertNotNull(cidadeOrigem);

        List<Cidade> rota = grafo.recomendarRotaPassageiro(cidadeOrigem);

        assertNotNull(rota);

        // Verifique se a rota é válida (deve visitar todas as cidades uma vez e voltar à origem)
        assertEquals(cidadeOrigem, rota.get(0));
        assertEquals(cidadeOrigem, rota.get(rota.size() - 1));

        assertEquals(1, rota.size()); // Deve ter o mesmo número de cidades

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
