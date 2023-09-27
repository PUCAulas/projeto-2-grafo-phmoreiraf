package trabalhopraticografo;

import org.junit.*;

class AppTest {

    private Cidade cidadeA;
    private Cidade cidadeB;
    private Cidade cidadeC;
    private Cidade cidadeD;
    private Cidade cidadeE;

    @Before
    public void setUp() {
        // Crie algumas cidades e adicione estradas entre elas para testar
        cidadeA = new Cidade("A");
        cidadeB = new Cidade("B");
        cidadeC = new Cidade("C");
        cidadeD = new Cidade("D");
        cidadeE = new Cidade("E");

        cidadeA.adicionarVizinho(cidadeB, 10);
        cidadeB.adicionarVizinho(cidadeC, 5);
        cidadeC.adicionarVizinho(cidadeD, 8);
    }
    @Test
    public void testExisteEstradaEntreCidades() {
        assertTrue(cidadeA.existeEstradaEntreCidades(cidadeA, cidadeB)); // Deve existir uma estrada
        assertTrue(cidadeA.existeEstradaEntreCidades(cidadeA, cidadeC)); // Deve existir uma estrada
        assertTrue(cidadeA.existeEstradaEntreCidades(cidadeA, cidadeD)); // Deve existir uma estrada
        assertFalse(cidadeA.existeEstradaEntreCidades(cidadeA, cidadeE)); // Não deve existir uma estrada
    }

    @Test
    public void testIdentificarCidadesInacessiveis() {
        // A cidade A é a cidade sede, então todas as outras devem ser acessíveis
        assertEquals(0, cidadeA.identificarCidadesInacessiveis(cidadeA).size());

        // Se a cidade B for removida das vizinhas de A, ela deve ser inacessível
        cidadeA.getVizinhos().remove(cidadeB);
        assertEquals(1, cidadeA.identificarCidadesInacessiveis(cidadeA).size());
        assertTrue(cidadeA.identificarCidadesInacessiveis(cidadeA).contains(cidadeB));
    }
}
