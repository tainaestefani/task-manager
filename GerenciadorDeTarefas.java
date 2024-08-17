import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class GerenciadorDeTarefas {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Tarefa> tarefas = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Sistema de Gestão de Tarefas!");
        
        boolean continuar = true;
        while (continuar) { // estrutura de repetição que irá reiniciar o sistema sempre que acabar de executar uma função
            System.out.println("\n------ Menu: ------");
            System.out.println("1. Criar nova tarefa");
            System.out.println("2. Listar todas as tarefas");
            System.out.println("3. Excluir uma tarefa");
            System.out.println("4. Sair");
            System.out.println("\nEscolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();  // Consumir a nova linha pendente

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

    public static void criarTarefa() {
        System.out.println("\nDigite o título da tarefa:");
        String tituloTarefa = scanner.nextLine();

        System.out.println("Digite a descrição da tarefa:");
        String descricaoTarefa = scanner.nextLine();

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

    public static void excluirTarefa() {
        listarTarefas();
        if (!tarefas.isEmpty()) {
            System.out.println("\nDigite o código da tarefa que deseja excluir:");
            int index = scanner.nextInt() - 1;
            scanner.nextLine();  // Consumir a nova linha pendente

            if (index >= 0 && index < tarefas.size()) {
                tarefas.remove(index);
                System.out.println("Tarefa excluída com sucesso!");
            } else {
                System.out.println("Código de tarefa inválido.");
            }
        }
    }
}

class Tarefa {
    private String titulo;
    private String descricao;
    private String dataVencimento;
    private String status;

    public Tarefa(String titulo, String descricao, String dataVencimento, String status) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public String getStatus() {
        return status;
    }
}