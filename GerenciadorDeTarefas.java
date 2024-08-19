import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe principal que gerencia o sistema de tarefas e interações com o usuário.
 * Permite ao usuário criar, listar e excluir tarefas, mantendo todas as tarefas em uma lista.
 */
public class GerenciadorDeTarefas {
    // Scanner utilizado para ler a entrada do usuário
    static Scanner scanner = new Scanner(System.in);
    
    // Lista que armazena todas as tarefas criadas
    static ArrayList<Tarefa> tarefas = new ArrayList<>();

    /**
     * Método principal que inicia o sistema de gerenciamento de tarefas.
     * Exibe o menu principal e permite ao usuário escolher as operações.
     */
    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Sistema de Gestão de Tarefas!");
        
        boolean continuar = true;
        // Loop que mantém o sistema em execução até o usuário optar por sair
        while (continuar) {
            System.out.println("\n------ Menu: ------");
            System.out.println("1. Criar nova tarefa");
            System.out.println("2. Listar todas as tarefas");
            System.out.println("3. Excluir uma tarefa");
            System.out.println("4. Sair");
            System.out.println("\nEscolha uma opção: ");

            // Lê a opção escolhida pelo usuário
            int escolha = scanner.nextInt();
            scanner.nextLine();  // Consome a nova linha pendente

            // Executa a operação correspondente à escolha do usuário
            switch (escolha) {
                case 1:
                    criarTarefa();
                    break;
                case 2:
                    listarTarefas();
                    break;
                case 3:
                    excluirTarefa();
                    break;
                case 4:
                    continuar = false;
                    System.out.println("\nSaindo do sistema. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    /**
     * Método que permite ao usuário criar uma nova tarefa.
     * Realiza a validação dos dados de entrada, como o limite de caracteres no título e a data de vencimento.
     */
    public static void criarTarefa() {
        System.out.println("\nDigite o título da tarefa:");
        String tituloTarefa = scanner.nextLine();
        // Verifica se o título não ultrapassa 50 caracteres
        while (true) {
            if (tituloTarefa.length() <= 50) {
                break; // Sai do loop se o título for válido
            } else {
                System.out.println("Título muito longo. Insira um título com no máximo 50 caracteres.");
                tituloTarefa = scanner.nextLine();
            }
        }
        
        // Lê a descrição da tarefa
        System.out.println("Digite a descrição da tarefa:");
        String descricaoTarefa = scanner.nextLine();

        // Lê e valida a data de vencimento da tarefa
        System.out.println("Digite a data de vencimento (Formato: YYYY-MM-DD):");
        String dataVencimento = scanner.nextLine();
        while (true) {
            if (dataVencimento.matches("\\d{4}-\\d{2}-\\d{2}")) {
                LocalDate data = LocalDate.parse(dataVencimento);
                if (data.isAfter(LocalDate.now())) {
                    break; // Sai do loop se a data for válida e futura
                } else {
                    System.out.println("A data de vencimento deve ser uma data futura.");
                    dataVencimento = scanner.nextLine();
                }
            } else {
                System.out.println("Formato de data inválido. Use o formato: YYYY-MM-DD.");
                dataVencimento = scanner.nextLine();
            }
        }

        // Lê e valida o status da tarefa
        System.out.println("Digite o status da tarefa (Pendente, Em andamento, Concluída):");
        String statusTarefa = scanner.nextLine();
        while (true) {
            if (statusTarefa.equalsIgnoreCase("Pendente") ||
                statusTarefa.equalsIgnoreCase("Em andamento") ||
                statusTarefa.equalsIgnoreCase("Concluída")) {
                break; // Sai do loop se o status for válido.
            } else {
                System.out.println("Status inválido. Tente um dos seguintes: Pendente, Em andamento, Concluída.");
                statusTarefa = scanner.nextLine();
            }
        }

        // Cria uma nova tarefa e a adiciona ao ArrayList
        Tarefa novaTarefa = new Tarefa(tituloTarefa, descricaoTarefa, dataVencimento, statusTarefa);
        tarefas.add(novaTarefa);

        System.out.println("\nTarefa criada com sucesso!");
    }

    /**
     * Método que exibe a lista de todas as tarefas criadas.
     * Caso não existam tarefas cadastradas, informa ao usuário.
     */
    public static void listarTarefas() {
        System.out.println("\nLista de Tarefas:");
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
        } else {
            for (int i = 0; i < tarefas.size(); i++) {
                Tarefa tarefa = tarefas.get(i);
                System.out.println((i + 1) + ". Título: " + tarefa.getTitulo() +
                                   ", Descrição: " + tarefa.getDescricao() +
                                   ", Vencimento: " + tarefa.getDataVencimento() +
                                   ", Status: " + tarefa.getStatus());
            }
        }
    }

    /**
     * Método que permite ao usuário excluir uma tarefa existente.
     * Exibe a lista de tarefas e solicita que o usuário escolha o código da tarefa a ser excluída.
     */
    public static void excluirTarefa() {
        listarTarefas();
        if (!tarefas.isEmpty()) {
            System.out.println("\nDigite o código da tarefa que deseja excluir:");
            int index = scanner.nextInt() - 1;
            scanner.nextLine();  // Consome a nova linha pendente

            if (index >= 0 && index < tarefas.size()) {
                tarefas.remove(index);
                System.out.println("Tarefa excluída com sucesso!");
            } else {
                System.out.println("Código de tarefa inválido.");
            }
        }
    }
}

/**
 * Classe que representa uma tarefa, contendo os atributos título, descrição, data de vencimento e status.
 */
class Tarefa {
    private String titulo;
    private String descricao;
    private String dataVencimento;
    private String status;

    /**
     * Construtor da classe Tarefa.
     * @param titulo Título da tarefa
     * @param descricao Descrição da tarefa
     * @param dataVencimento Data de vencimento da tarefa
     * @param status Status da tarefa (Pendente, Em andamento, Concluída)
     */
    public Tarefa(String titulo, String descricao, String dataVencimento, String status) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.status = status;
    }

    /**
     * Retorna o título da tarefa.
     * @return Título da tarefa
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Retorna a descrição da tarefa.
     * @return Descrição da tarefa
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna a data de vencimento da tarefa.
     * @return Data de vencimento da tarefa
     */
    public String getDataVencimento() {
        return dataVencimento;
    }

    /**
     * Retorna o status da tarefa.
     * @return Status da tarefa
     */
    public String getStatus() {
        return status;
    }
}
