package visao;
import java.util.ArrayList;
import java.util.Scanner;

import lib.Model.Disciplina.Disciplina;
import lib.Model.Material.Material;
import lib.Model.Usuario.Aluno;
import lib.Model.Usuario.Professor;
import lib.Model.Usuario.Usuario;
import lib.dao.AlunoDAO;
import lib.dao.DisciplinaDAO;
import lib.dao.MaterialDAO;
import lib.dao.ProfessorDAO;

import java.sql.*;  
import java.sql.DriverManager;

public class App {
    private static Scanner leitura = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        
        loadMenuIncial();

    }

    public static void loadMenuIncial(){
        String opcaoMenu;
        int count = -1;
        do{
            if(++count != 0)
                System.out.println("Opção inválida!");
            System.out.print("Bem vindo(a) ao IFNET\n\nMenu - (a) Menu de cadastros  (b) Log in  (c) Exclusão\nEntre com uma opção: ");
            opcaoMenu = leitura.nextLine();
        }while(!(opcaoMenu.equalsIgnoreCase("a") || opcaoMenu.equalsIgnoreCase("b") || opcaoMenu.equalsIgnoreCase("c")));

        if(opcaoMenu.equalsIgnoreCase("a"))
            loadMenuCadastros();
        else if(opcaoMenu.equalsIgnoreCase("b"))
            loadMenuLogIn();
        else if(opcaoMenu.equalsIgnoreCase("c"))
            excluir();
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
            loadMenuAluno(aluno);
        }
        else if(ProfessorDAO.logarUsuario(prontuario, professor)){
            loadMenuProfessor(professor);
        }
        else
            System.out.println("Usuário não encontrado!");

    }

    public static void loadMenuAluno(Aluno aluno){//
        Material material = new Material();

        System.out.print("Bem vindo(a) "+aluno.getNome()+"!\n\n"+
        "Cadastrar: "+"(a) disciplina, (b) livros, (c) apostilas (d) material da web (0 para menu inicial): ");

        switch(leitura.nextLine().toLowerCase()){

            case "a":{
                System.out.println("Disciplinas disponíveis: ");
                ArrayList<String> listDisciplinas = new ArrayList<>();
                listDisciplinas = DisciplinaDAO.getListDisciplinas(listDisciplinas);
                for(int i = 0 ; i < listDisciplinas.size() ; i++){
                    System.out.print(listDisciplinas.get(i)+(i==listDisciplinas.size()-1 ? "." : ", "));
                }
                System.out.print("\nQual disciplina deseja cadastrar?\nR: ");
                String disciplina = leitura.nextLine();
                if(DisciplinaDAO.checarExistenciaDisciplina(disciplina)){
                    if(AlunoDAO.adicionarDisciplina(aluno, disciplina))
                        System.out.println("\nDisciplina adicionada com sucesso!");
                    else
                        System.out.println("\nDisciplina já cadastrada anteriormente!");
                }
                else{
                    System.out.println("Disciplina não encontrada!");
                }
                loadMenuAluno(aluno);
                break;
            }

            case "b":{
                System.out.print("Digite o nome do material que deseja adicionar: ");
                String nome_material  = leitura.nextLine();
                System.out.print("Digite a area de conhecimento: ");
                String area_conhecimento  = leitura.nextLine();
                material.cadastrarMaterial(nome_material, area_conhecimento, "Livro");
                MaterialDAO.inserir(material);
                    
                break;
            }

            case "c":{
                
            }

            case "d":{
                
            }

           

            case "0":{
                loadMenuIncial();
                break;
            }

        }
    }

    public static void loadMenuProfessor(Professor professor){//
        System.out.print("Bem vindo(a) "+professor.getNome()+"!\n\n"+
        "(a) Inserir conteúdo, (b) Criar grupo de trabalho, (c) Criar grupo de pesquisa (0 para menu inicial)\nR: ");

        switch(leitura.nextLine().toLowerCase()){

            case "a":{
                System.out.println("Suas disciplinas disponíveis: ");
                ArrayList<String> listDisciplinas = new ArrayList<>();
                listDisciplinas = DisciplinaDAO.getListDisciplinas(listDisciplinas, professor.getNome());
                for(int i = 0 ; i < listDisciplinas.size() ; i++){
                    System.out.print(listDisciplinas.get(i)+(i==listDisciplinas.size()-1 ? "." : ", "));
                }
                break;
            }

            case "0":{
                loadMenuIncial();
                break;
            }
        }

    }

    

    public static void excluir(){

        Aluno aluno = new Aluno();
        Professor professor = new Professor();

        System.out.print("Deseja excluir: Aluno (a) , Professor (b), Disciplina (c) , Material/Conteudo (d) , Grupo (e):  ");
        String opcaoExclusao = leitura.nextLine();
        switch (opcaoExclusao){
            case "a":

                System.out.print("Digite o prontuario do aluno que deseja excluir: ");
                String pront = leitura.nextLine();
                    AlunoDAO.deletarAluno(pront);
                    break;
            
            case "b":
                
                System.out.print("Digite o prontuario do professor que deseja excluir: ");
                String prontt = leitura.nextLine();
                ProfessorDAO.deletarProfessor(prontt);
                break;
                

            case "c":
            System.out.print("Digite o nome da disciplina que deseja excluir: ");
            String nomeDisciplina = leitura.nextLine();
                DisciplinaDAO.deletarDisciplina(nomeDisciplina);
                break;
            }
        }


    }
                



