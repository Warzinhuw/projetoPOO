package visao;
import java.util.Scanner;

import lib.Model.Disciplina.Disciplina;
import lib.Model.Usuario.Aluno;
import lib.Model.Usuario.Professor;
import lib.dao.AlunoDAO;
import lib.dao.DisciplinaDAO;
import lib.dao.ProfessorDAO;

import java.sql.*;  
import java.sql.DriverManager;

public class App {
    private static Scanner leitura = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        String opcaoMenu;
        int count = -1;
        do{
            if(++count != 0)
                System.out.println("Opção inválida!");
            System.out.print("Bem vindo(a) ao IFNET\n\nMenu - (a) Menu de cadastros  (b) Log in\nEntre com uma opção: ");
            opcaoMenu = leitura.nextLine();
        }while(!(opcaoMenu.equalsIgnoreCase("a") || opcaoMenu.equalsIgnoreCase("b") || opcaoMenu.equalsIgnoreCase("c")));

        if(opcaoMenu.equalsIgnoreCase("a"))
            loadMenuCadastros();
        else if(opcaoMenu.equalsIgnoreCase("b"))
            loadMenuLogIn();


    }

    public static void loadMenuCadastros(){
        System.out.print("Cadastrar (a) aluno (b) professor (c) disciplina: ");
        String opcaoMenu = leitura.nextLine();
    

        if(opcaoMenu.equalsIgnoreCase("a") || opcaoMenu.equalsIgnoreCase("b")){

            String nome, email;

            System.out.print("Digite o nome do(a) "+(opcaoMenu.equalsIgnoreCase("a") ? "aluno(a)" : "professor(a)")+": ");
            nome = leitura.nextLine();
            System.out.print("Digite o email do(a) "+(opcaoMenu.equalsIgnoreCase("a") ? "aluno(a)" : "professor(a)")+": ");
            email = leitura.nextLine();

            if(opcaoMenu.equalsIgnoreCase("a")){
                Aluno aluno = new Aluno();
                aluno.cadastrarUsuario(nome, email, 0);
                AlunoDAO.inserir(aluno);
                System.out.println("Aluno(a) adicionado(a)!");
            }
            else{
                System.out.print("Digite a área do professor (ex: Matemática): ");
                Professor professor = new Professor(leitura.nextLine());
                professor.cadastrarUsuario(nome, email, 0);
                ProfessorDAO.inserir(professor);
                System.out.println("Professor(a) adicionado(a)!");
            }

        }
        else if(opcaoMenu.equalsIgnoreCase("c")){
            String nomeDisciplina, nomeProfessor;
            int cargaHoraria;
            System.out.print("Nome da disciplina: ");
            nomeDisciplina = leitura.nextLine();
            System.out.print("Carga horária: ");
            cargaHoraria = Integer.parseInt(leitura.nextLine());
            System.out.print("Nome do professor responsável: ");
            nomeProfessor = leitura.nextLine();
            Disciplina disciplina = new Disciplina(cargaHoraria, nomeDisciplina, nomeProfessor);
            DisciplinaDAO.inserir(disciplina);
            System.out.println("Disciplina adicionada!");
        }
        else{
            System.out.println("Opção inválida!");
            loadMenuCadastros();
        }

    }

    public static void loadMenuLogIn(){

        Aluno aluno = new Aluno();
        Professor professor = new Professor();
        System.out.print("Digite o prontuário: ");
        String prontuario = leitura.nextLine();
        if(AlunoDAO.logarUsuario(prontuario, aluno)){
            System.out.println("Bem vindo(a) "+aluno.getNome()+"!");
            loadMenuAluno(aluno);
        }
        else if(ProfessorDAO.logarUsuario(prontuario, professor)){
            System.out.println("Bem vindo(a) "+professor.getNome()+"!");
            loadMenuProfessor(professor);
        }
        else
            System.out.println("Usuário não encontrado!");

    }

    public static void loadMenuAluno(Aluno aluno){//

    }

    public static void loadMenuProfessor(Professor professor){//

    }

}