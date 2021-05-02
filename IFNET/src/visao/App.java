package visao;
import java.util.ArrayList;
import java.util.Scanner;

import lib.Model.Disciplina.Disciplina;
import lib.Model.Grupo.Grupo;
import lib.Model.Material.Material;
import lib.Model.Usuario.Aluno;
import lib.Model.Usuario.Professor;
import lib.Model.Usuario.Usuario;
import lib.dao.AlunoDAO;
import lib.dao.DisciplinaDAO;
import lib.dao.GrupoDAO;
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

        switch(opcaoMenu.toLowerCase()){

            case"a":
            case"b":{
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
                break;
            }

            case"c":{
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
                break;
            }

            default:{
                System.out.println("Opção inválida!");
                loadMenuCadastros();
                break;
            }
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
                ArrayList<String> listDisciplinas = DisciplinaDAO.getListDisciplinas();
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

    public static void loadMenuProfessor(Professor professor){
        System.out.print("Bem vindo(a) "+professor.getNome()+"!\n\n"+
        "(a) Inserir conteúdo, (b) Criar grupo de trabalho, (c) Criar grupo de pesquisa (d) Gerenciar grupos (0 para menu inicial)\nR: ");

        switch(leitura.nextLine().toLowerCase()){

            case "a":{
                System.out.println("Suas disciplinas disponíveis: ");
                ArrayList<String> listDisciplinas = new ArrayList<>();
                listDisciplinas = DisciplinaDAO.getListDisciplinas(professor.getNome());

                for(int i = 0 ; i < listDisciplinas.size() ; i++){
                    System.out.print(listDisciplinas.get(i)+(i==listDisciplinas.size()-1 ? "." : ", "));
                }
                System.out.print("\nEm qual disciplina deseja adicionar o conteudo?\nR: ");
                String disciplina = leitura.nextLine();

                if(!listDisciplinas.contains(disciplina)){
                    System.out.println("Disciplina invalida!");
                    loadMenuProfessor(professor);
                }

                System.out.print("Insira o conteúdo: ");
                DisciplinaDAO.inserirConteudo(disciplina, leitura.nextLine(), professor.getNome());
                System.out.println("Conteúdo inserido com sucesso!");

                break;
            }

            case "b":{
                System.out.print("Digite o nome do grupo de trabalho: ");
                String nomeGrupo = leitura.nextLine();
                String nomeDisciplina = escolherDisciplinaGrupo(professor);
                GrupoDAO.inserir(new Grupo(Grupo.GRUPO_TRABALHO, nomeGrupo, professor.getNome(), nomeDisciplina));
                System.out.println("Grupo adicionado com sucesso!");
                loadMenuProfessor(professor);
                break;
            }

            case "c":{
                System.out.print("Digite o nome do grupo de pesquisa: ");
                String nomeGrupo = leitura.nextLine();
                String nomeDisciplina = escolherDisciplinaGrupo(professor);
                GrupoDAO.inserir(new Grupo(Grupo.GRUPO_PESQUISA, nomeGrupo, professor.getNome(), nomeDisciplina));
                System.out.println("Grupo adicionado com sucesso!");
                loadMenuProfessor(professor);
                break;
            }

            case "d":{
                String nomeGrupo;
                boolean controle = false;
                do{
                    System.out.print("Qual grupo deseja gerenciar? (digite 0 para ver os grupos disponíveis)\nR: ");
                    nomeGrupo = leitura.nextLine();
                    if(nomeGrupo.equals("0")){
                        ArrayList<String> listGrupos = GrupoDAO.getListGrupos(professor.getNome());
                        System.out.println("Grupos disponíveis: ");
                        for(int i = 0 ; i < listGrupos.size() ; i++){
                            System.out.print(listGrupos.get(i)+(i==listGrupos.size()-1 ? ".\n" : ", "));
                        }
                    }
                    else if(GrupoDAO.checarExistenciaGrupo(nomeGrupo))
                        controle = true;
                    else
                        System.out.println("Grupo inválido!");
                }while(nomeGrupo.equals("0") || !controle);
                loadMenuGrupo(nomeGrupo, professor.getNome(), professor);
                break;
            }

            case "0":{
                loadMenuIncial();
                break;
            }
        }

    }

    public static void loadMenuGrupo(String nomeGrupo, String nomeProfessor, Professor professor){
        Grupo grupo = GrupoDAO.recuperarGrupo(nomeGrupo, nomeProfessor);
        System.out.print("Menu grupo -\n(a) Adicionar alunos\n(b) Remover alunos\n(c) Excluir grupo\n(0) Voltar ao menu anterior\nR: ");

        switch(leitura.nextLine().toLowerCase()){

            case "a":{
                boolean valido = false;
                String nomeAluno;
                do{
                    System.out.print("Digite o nome do aluno que deseja adicionar(digite 0 para ver os alunos disponíveis): ");
                    nomeAluno = leitura.nextLine();
                    ArrayList<String> listAlunos = GrupoDAO.getListAlunosSemGrupo(grupo);
                    if(nomeAluno.equals("0")){
                        if(listAlunos.isEmpty())
                            System.out.println("Nenhum aluno disponível.");
                        else
                            System.out.println("Alunos disponíveis: ");
                        for(int i = 0 ; i < listAlunos.size() ; i++){
                            System.out.print(listAlunos.get(i)+(i==listAlunos.size()-1 ? ".\n" : ", "));
                        }
                    }
                    else if(!listAlunos.contains(nomeAluno))
                        System.out.println(nomeAluno+" já está nesse grupo!");
                    else
                        valido = true;
                }while(nomeAluno.equals("0") || !valido);
                GrupoDAO.inserirAluno(grupo, nomeAluno);
                System.out.println("Aluno inserido com sucesso no grupo '"+grupo.getNomeGrupo()+"'!");
                loadMenuGrupo(nomeGrupo, nomeProfessor, professor);
                break;
            }

            case "b":{
                boolean valido = false;
                String nomeAluno;
                do{
                    System.out.print("Digite o nome do aluno que deseja remover(digite 0 para ver os alunos disponíveis): ");
                    nomeAluno = leitura.nextLine();
                    ArrayList<String> listAlunos = GrupoDAO.getListAlunosNoGrupo(grupo);
                    if(nomeAluno.equals("0")){
                        if(listAlunos.size()==0)
                            System.out.println("Nenhum aluno disponível.");
                        else
                            System.out.println("Alunos disponíveis: ");
                        for(int i = 0 ; i < listAlunos.size() ; i++){
                            System.out.print(listAlunos.get(i)+(i==listAlunos.size()-1 ? ".\n" : ", "));
                        }
                    }
                    else if(!listAlunos.contains(nomeAluno))
                        System.out.println(nomeAluno+" não está nesse grupo.");
                    else
                        valido = true;
                }while(nomeAluno.equals("0") || !valido);
                GrupoDAO.removerAluno(nomeAluno);
                System.out.println("Aluno removido com sucesso do grupo '"+grupo.getNomeGrupo()+"'!");
                loadMenuGrupo(nomeGrupo, nomeProfessor, professor);
                break;
            }

            case "c":{
                System.out.print("Deseja realmente excluir o grupo '"+grupo.getNomeGrupo()+"'? (s) para deletar: ");
                if(leitura.nextLine().equalsIgnoreCase("s")){
                    GrupoDAO.excluirGrupo(grupo);
                    System.out.println("Grupo excluído com sucesso!");
                }
                else
                    System.out.println("Exclusão cancelada.");
                loadMenuGrupo(nomeGrupo, nomeProfessor, professor);
                break;
            }

            case "0":{
                loadMenuProfessor(professor);
                break;
            }

            default:{
                System.out.println("Opção inválida");
                loadMenuGrupo(nomeGrupo, nomeProfessor, professor);
            }

        }

    }

    public static String escolherDisciplinaGrupo(Professor professor){
        String nomeDisciplina;
        boolean controle = false;
        do{
            System.out.print("A qual disciplina esse grupo será relacionado? (digite 0 para ver as disciplinas disponíveis)\nR: ");
            nomeDisciplina = leitura.nextLine();
            if(nomeDisciplina.equals("0")){
                ArrayList<String> listDisciplinas = DisciplinaDAO.getListDisciplinas(professor.getNome());
                System.out.println("Disciplinas disponíveis: ");
                for(int i = 0 ; i < listDisciplinas.size() ; i++){
                    System.out.print(listDisciplinas.get(i)+(i==listDisciplinas.size()-1 ? ".\n" : ", "));
                }
            }
            else if(DisciplinaDAO.checarExistenciaDisciplina(nomeDisciplina))
                controle = true;
            else
                System.out.println("Disciplina inválida!");
        }while(nomeDisciplina.equals("0") || !controle);
        return nomeDisciplina;
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
                



