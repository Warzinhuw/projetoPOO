import java.util.Scanner;
import lib.Usuario.Aluno;
import lib.Usuario.Professor;

public class App {
    private static Scanner leitura = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        String opcaoMenu;
        int count = -1;
        do{
            if(++count != 0)
                System.out.println("Opção inválida!");
            System.out.print("Bem vindo(a) ao IFNET\n\nMenu - (a) Log in  (b) Cadastrar\nEntre com uma opção: ");
            opcaoMenu = leitura.nextLine();
        }while(!(opcaoMenu.equalsIgnoreCase("a") || opcaoMenu.equalsIgnoreCase("b")));

        if(opcaoMenu.equalsIgnoreCase("a"))
            loadMenuLogIn();
        else
            loadMenuCadastro();

    }

    public static void loadMenuLogIn(){
        System.out.println("\nEntre com seus dados para realizar o log in");
        String login, senha;
        System.out.print("Digite seu login: ");
        login = leitura.nextLine();
        System.out.print("Digite sua senha: ");
        senha = leitura.nextLine();
        logarUsuario(login, senha);
    }

    public static void loadMenuCadastro(){
        System.out.println("\nEntre com seus dados para realizar o cadastro");
        String login, senha, repetirSenha, tipoUsuario;
        System.out.print("Digite (a) para aluno e (b) para profressor: ");
        tipoUsuario = leitura.nextLine();
        System.out.print("Digite seu login: ");
        login = leitura.nextLine();
        System.out.print("Digite sua senha: ");
        senha = leitura.nextLine();
        System.out.print("Digite sua senha novamente: ");
        repetirSenha = leitura.nextLine();
        if(!(senha.equals(repetirSenha)))
            System.out.println("Senhas não conferem!");
        else if(!(tipoUsuario.equalsIgnoreCase("a") || tipoUsuario.equalsIgnoreCase("b")))
            System.out.println("Tipo de usuário inválido!");
        else{
            String nome, email;
            int min = 1000000, max = 9999999;
            String prontuario = "BP"+Math.floor(Math.random()*(max-min+1)+min);
            System.out.print("Digite seu nome: ");
            nome = leitura.nextLine();
            System.out.print("Digite seu email: ");
            email = leitura.nextLine();
            if(tipoUsuario.equalsIgnoreCase("a"))
                new Aluno().cadastrarUsuario(nome, prontuario, email, 0);
            else{
                System.out.print("Digite sua área (ex: Matemática): ");
                new Professor(leitura.nextLine()).cadastrarUsuario(nome, prontuario, email, 0);
            }
            logarUsuario(login, senha);
        }

    }

    public static void logarUsuario(String login, String senha){

    }

}
