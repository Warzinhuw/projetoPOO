package visao;
import java.util.Scanner;
import lib.Model.Usuario.Aluno;
import lib.Model.Usuario.Professor;
import lib.dao.AlunoDAO;
import lib.dao.ProfessorDAO;

import java.sql.*;  

public class App {
    private static Scanner leitura = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        String opcaoMenu;
        int count = -1;
        do{
            if(++count != 0)
                System.out.println("Opção inválida!");
            System.out.print("Bem vindo(a) ao IFNET\n\nMenu - (a) Menu de cadastros  (b) XXXX\nEntre com uma opção: ");
            opcaoMenu = leitura.nextLine();
        }while(!(opcaoMenu.equalsIgnoreCase("a") || opcaoMenu.equalsIgnoreCase("b")));

        if(opcaoMenu.equalsIgnoreCase("a"))
            loadMenuCadastros();

    }

    public static void loadMenuCadastros(){
        System.out.print("Cadastrar (a) aluno (b) profressor: ");
        String opcaoMenu = leitura.nextLine();
    

        if(opcaoMenu.equalsIgnoreCase("a") || opcaoMenu.equalsIgnoreCase("b")){

            String nome, email;
            int min = 1000000, max = 9999999;
            String prontuario = "BP"+Math.floor(Math.random()*(max-min+1)+min);

            System.out.print("Digite o nome do(a) "+(opcaoMenu.equalsIgnoreCase("a") ? "aluno(a)" : "professor(a)"));
            nome = leitura.nextLine();
            System.out.print("Digite o email do(a) "+(opcaoMenu.equalsIgnoreCase("a") ? "aluno(a)" : "professor(a)"));
            email = leitura.nextLine();

            if(opcaoMenu.equalsIgnoreCase("a")){
                Aluno aluno = new Aluno();
                aluno.cadastrarUsuario(nome, prontuario, email, 0);
                AlunoDAO.inserir(aluno);
            }
            else if(opcaoMenu.equalsIgnoreCase("b")){
                System.out.print("Digite a área do professor (ex: Matemática): ");
                Professor professor = new Professor(leitura.nextLine());
                professor.cadastrarUsuario(nome, prontuario, email, 0);
                ProfessorDAO.inserir(professor);
            }   

        }
        else{
            System.out.println("Opção inválida!");
            loadMenuCadastros();
        }

    }

    public static void logarUsuario(String login, String senha){//

    }

}
