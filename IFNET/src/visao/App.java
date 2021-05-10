package visao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;

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
import lib.dao.RelacionamentoDAO;
import lib.dao.UsuarioDAO;
import lib.Excecao.*;

import java.sql.*;  
import java.sql.DriverManager;

public class App {
    private static Scanner leitura = new Scanner(System.in);
    public static void main(String[] args) {
        
        loadMenuIncial();
    }

    public static void loadMenuIncial(){
        String opcaoMenu;
        System.out.println("\n-----Bem vindo(a) ao IFNET-----");
        System.out.print("\nMenu - \n(a) Cadastro  \n(b) Log in  \n(c) Exclusão \n(d) Consultas");
        System.out.print("\nEntre com uma opção:");
        opcaoMenu = leitura.nextLine();

        switch(opcaoMenu.toLowerCase()){
            case "a":{
                loadMenuCadastros();
                break;
            }

            case "b":{
                loadMenuLogIn();
                break;
            }

            case "c":{
        
                loadMenuExcluir() ;
                break;
            
            }

            case "d":{
                loadMenuConsultas();
                break;
            }

            default:{
                System.out.println("Opção Invalida!");
                loadMenuIncial();
                break;
            }
        }
    }

    public static void loadMenuCadastros(){
        System.out.print("\n\nCadastrar: \n(a) aluno \n(b) professor \n(c) disciplina \n(0 para voltar ao menu inicial)");
        System.out.print("\nEntre com uma opção:");
        String opcaoMenu = leitura.nextLine();

        switch(opcaoMenu.toLowerCase()){

            case "a":
            case "b":{
                String nome, email;
                System.out.print("Digite o nome do(a) "+(opcaoMenu.equalsIgnoreCase("a") ? "aluno(a)" : "professor(a)")+": ");
                nome = leitura.nextLine();
                System.out.print("Digite o email do(a) "+(opcaoMenu.equalsIgnoreCase("a") ? "aluno(a)" : "professor(a)")+": ");
                email = leitura.nextLine();

                if(opcaoMenu.equalsIgnoreCase("a")){

                    Aluno aluno = new Aluno();
                    System.out.print("Digite o nome do Curso: ");
                    String nomeC = leitura.nextLine();
                    aluno.setNomeCurso(nomeC);
                    aluno.cadastrarUsuario(nome, email);
                    AlunoDAO.inserir(aluno);
                    System.out.println("\nAluno(a) adicionado(a)!");
                    System.out.println("Prontuario: "+aluno.getProntuario());
                    loadMenuAluno(aluno);
                }
                else{
                System.out.print("Digite a area de estudo do professor: ");
                String area = leitura.nextLine();
                ArrayList<String> listDisciplinas = DisciplinaDAO.getListDisciplinasSemProf();
                if(!listDisciplinas.isEmpty())
                    System.out.println("\nDisciplinas disponíveis: ");
                else{
                    System.out.print("\nNenhuma disciplina disponível, adicione uma primeiro.");
                    loadMenuCadastros();
                    break;
                }
                for(int i = 0 ; i < listDisciplinas.size() ; i++){
                    System.out.print(listDisciplinas.get(i)+(i==listDisciplinas.size()-1 ? "." : ", "));
                }
                System.out.print("\nQual disciplina esse professor irá ministrar?\nR: ");
                String disciplina = leitura.nextLine();
                Professor professor; 
                professor = new Professor();
                professor.setArea(area);
                professor.setDisciplina(disciplina);
                professor.cadastrarUsuario(nome, email);
                try {
                        DisciplinaDAO.checarExistenciaDisciplina(disciplina);
                        ProfessorDAO.inserir(professor);
                        if(DisciplinaDAO.adicionarDisciplinaProfessor(professor, disciplina)){
                        System.out.println("\nProfessor(a) adicionado(a)!");
                        System.out.println("\nProntuario: "+professor.getProntuario());
                        loadMenuProfessor(professor);
                        }else 
                            System.out.println("\nDisciplina já cadastrada anteriormente!");
                            loadMenuCadastros();
                } catch (NaoEncontrado e) {
                    System.out.println(e.getMessage());
                }
                break;
                }
            }

            case"c":{
                String nomeDisciplina;
                String cargaHoraria;
                System.out.print("Nome da disciplina: ");
                nomeDisciplina = leitura.nextLine();
                System.out.print("Carga horária: ");
                cargaHoraria = leitura.nextLine();
                Disciplina disciplina = new Disciplina(cargaHoraria, nomeDisciplina);
                try{
                    DisciplinaDAO.inserir(disciplina);
                    System.out.println("Disciplina adicionada!");
                }catch(FormatoInvalido f){
                    System.out.println(f.getMessage());
                }
                loadMenuCadastros();
                break;
            }

            case "0":{
                loadMenuIncial();
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
        else{
            System.out.println("Usuário não encontrado!");
            loadMenuLogIn();
        }

    }

    public static void loadMenuAluno(Aluno aluno){
        Material material = new Material();

        System.out.print("\nBem vindo(a) "+aluno.getNome()+"!\n\n"+ "Entre com a opção desejada: "+"\n(a) Cadastrar disciplina \n(b) Cadastrar livros \n(c) Cadastrar apostilas \n(d) Cadastrar material da web \n(e) Gerenciar relacionamentos\n(0 para sair) ");
        System.out.print("\nEntre com uma opção:");
        switch(leitura.nextLine().toLowerCase()){

            case "a":{
                System.out.println("Disciplinas disponíveis: ");
                ArrayList<String> listDisciplinas = DisciplinaDAO.getListDisciplinas();
                for(int i = 0 ; i < listDisciplinas.size() ; i++){
                    System.out.print(listDisciplinas.get(i)+(i==listDisciplinas.size()-1 ? "." : ", "));
                }
                System.out.print("\nQual disciplina deseja cadastrar?\nR: ");
                String disciplina = leitura.nextLine();
                try{
                    DisciplinaDAO.checarExistenciaDisciplina(disciplina);
                    if(AlunoDAO.adicionarDisciplina(aluno, disciplina))
                        System.out.println("\nDisciplina adicionada com sucesso!");
                    else
                        System.out.println("\nDisciplina já cadastrada anteriormente!");
                }catch(NaoEncontrado e){
                    System.out.println(e.getMessage());
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
                MaterialDAO.inserir(material, aluno.getProntuario());
                System.out.println("Material adicionado com sucesso!");
                loadMenuAluno(aluno);
                break;
            }

            case "c":{
                System.out.print("Digite o nome do material que deseja adicionar: ");
                String nome_material  = leitura.nextLine();
                System.out.print("Digite a area de conhecimento: ");
                String area_conhecimento  = leitura.nextLine();
                material.cadastrarMaterial(nome_material, area_conhecimento, "Apostila");
                MaterialDAO.inserir(material, aluno.getProntuario());
                System.out.println("Material adicionado com sucesso!");
                loadMenuAluno(aluno);
                break;
            }

            case "d":{
                System.out.print("Digite o nome do material que deseja adicionar: ");
                String nome_material  = leitura.nextLine();
                System.out.print("Digite a area de conhecimento: ");
                String area_conhecimento  = leitura.nextLine();
                material.cadastrarMaterial(nome_material, area_conhecimento, "Material da WEB");
                MaterialDAO.inserir(material,aluno.getProntuario());
                System.out.println("Material adicionado com sucesso!");
                loadMenuAluno(aluno);
                break;
            }

            case "e":{
                loadMenuRelacionamento(aluno);
                break;
            }

            case "0":{
                loadMenuIncial();
                break;
            }

            default:{
                System.out.println("Opção inválida!");
                loadMenuAluno(aluno);
                break;
            }

        }
    }

    public static void loadMenuProfessor(Professor professor){
        System.out.print("Bem vindo(a) "+professor.getNome()+"!\n\n"+
        "\n(a) Inserir conteúdo \n(b) Criar grupo de trabalho \n(c) Criar grupo de pesquisa \n(d) Gerenciar grupos \n(e) Gerenciar relacionamentos \n(0 para menu inicial)\n ");
        System.out.print("\nEntre com uma opção:");
        String opcaoMenu=leitura.nextLine().toLowerCase();
        switch(opcaoMenu){

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
                loadMenuProfessor(professor);
                break;
            }

            case "b":
            case "c":{
                String tipoGrupo = opcaoMenu.equals("b") ? "trabalho" : "pesquisa";
                System.out.print("Digite o nome do grupo de "+tipoGrupo+": ");
                String nomeGrupo = leitura.nextLine();
                String nomeDisciplina = escolherDisciplinaGrupo(professor);
                Grupo grupo = new Grupo((tipoGrupo.equals("trabalho") ? Grupo.GRUPO_TRABALHO : Grupo.GRUPO_PESQUISA), nomeGrupo, professor.getNome(), nomeDisciplina);
                GrupoDAO.inserir(grupo);
                System.out.println("Grupo adicionado com sucesso!\nID do grupo: "+grupo.getIdGrupo());
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
                        if(listGrupos.isEmpty()){
                            System.out.println("Nenhum grupo disponível.");
                            loadMenuProfessor(professor);
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

            case "e":{
                loadMenuRelacionamento(professor);
                break;
            }

            case "0":{
                loadMenuIncial();
                break;
            }

            default:{
                System.out.println("\nOpção inválida!");
                loadMenuProfessor(professor);
            }
        }

    }

    public static void loadMenuGrupo(String nomeGrupo, String nomeProfessor, Professor professor){
        Grupo grupo = GrupoDAO.recuperarGrupo(nomeGrupo, nomeProfessor);
        System.out.print("\nMenu grupo -\n(a) Adicionar alunos\n(b) Remover alunos\n(c) Excluir grupo\n(0) Voltar ao menu anterior\nR: ");

        switch(leitura.nextLine().toLowerCase()){

            case "a":{
                boolean valido = false;
                String nomeAluno;
                do{
                    System.out.print("Digite o nome do aluno que deseja adicionar(digite 0 para ver os alunos disponíveis): ");
                    nomeAluno = leitura.nextLine();
                    ArrayList<String> listAlunos = GrupoDAO.getListAlunosSemGrupo(grupo);
                    if(nomeAluno.equals("0")){
                        if(listAlunos.isEmpty()){
                            System.out.println("Nenhum aluno disponível.");
                            loadMenuGrupo(nomeGrupo, nomeProfessor, professor);
                        }
                        else
                            System.out.println("Alunos disponíveis: ");
                        for(int i = 0 ; i < listAlunos.size() ; i++){
                            System.out.print(listAlunos.get(i)+(i==listAlunos.size()-1 ? ".\n" : ", "));
                        }
                    }
                    else if(!listAlunos.contains(nomeAluno)){
                        System.out.println(nomeAluno+" já está nesse grupo ou não existe!");
                        loadMenuGrupo(nomeGrupo, nomeProfessor, professor);
                    }
                    else
                        valido = true;
                }while(nomeAluno.equals("0") || !valido);
                Aluno aluno = (Aluno) UsuarioDAO.recuperarUsuario(nomeAluno);
                GrupoDAO.inserirAluno(grupo, aluno);
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
                        if(listAlunos.isEmpty()){
                            System.out.println("Nenhum aluno disponível.");
                            loadMenuGrupo(nomeGrupo, nomeProfessor, professor);
                        }
                        else
                            System.out.println("Alunos disponíveis: ");
                        for(int i = 0 ; i < listAlunos.size() ; i++){
                            System.out.print(listAlunos.get(i)+(i==listAlunos.size()-1 ? ".\n" : ", "));
                        }
                    }
                    else if(!listAlunos.contains(nomeAluno)){
                        System.out.println(nomeAluno+" não está nesse grupo.");
                        loadMenuGrupo(nomeGrupo, nomeProfessor, professor);
                    }
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
                    loadMenuProfessor(professor);
                    break;
                }
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

    public static void loadMenuConsultas(){
        System.out.print("\n-- Menu -- \n(a) Usuários com mais relacionamentos \n(b) Grupos com mais usuários \n(c) Grupos de pesquisa por disciplina \n(0) voltar ao menu anterior \nR: ");

        switch(leitura.nextLine().toLowerCase()){

            case "a":{
                SortedMap<Integer, String> mapRelacionamentos = RelacionamentoDAO.getMapGrupoMaisUsuarios();
                if(mapRelacionamentos.isEmpty()){
                    System.out.println("Nenhum relacionamento encontrado.");
                }
                else{
                    System.out.println((mapRelacionamentos.size()==1 ? "O" : "Os ")+
                    (mapRelacionamentos.size()==1 ? " usuário com mais relacionamento: " : (mapRelacionamentos.size()+" primeiros usuários com mais relacionamentos: ")));
                    for(Map.Entry<Integer, String> entry : mapRelacionamentos.entrySet()){
                        System.out.println(entry.getValue()+" - "+(entry.getKey() == 1 ? "1 relacionamento" : (entry.getKey()+" relacionamentos")));
                    }
                }
                loadMenuConsultas();
                break;
            }

            case "b":{
                SortedMap<Integer, String> mapGrupos = GrupoDAO.getMapGrupoMaisUsuarios();
                if(mapGrupos.isEmpty()){
                    System.out.println("Nenhum grupo encontrado.");
                    loadMenuConsultas();
                }
                else{
                    System.out.println((mapGrupos.size()==1 ? "O" : "Os ")+
                    (mapGrupos.size()==1 ? " grupo com mais usuários:" : (mapGrupos.size()+" primeiros grupos com mais usuários: ")));
                    for(Map.Entry<Integer, String> entry : mapGrupos.entrySet()){
                        System.out.println(entry.getValue()+" - "+(entry.getKey() == 1 ? "1 aluno" : (entry.getKey()+" alunos")));
                    }
                }
                loadMenuConsultas();
                break;
            }

            case "c":{
                System.out.print("Entre com a disciplina: ");
                String disciplina = leitura.nextLine();
                ArrayList<String> listDisciplinas = GrupoDAO.getListGruposPorDisciplina(disciplina);
                if(listDisciplinas.isEmpty()){
                    System.out.println("Nenhum grupo encontrado relacionado a essa disciplina.");
                    loadMenuConsultas();
                }
                  
                else{
                    System.out.println("Grupos relacionados a essa disciplina: ");
                    for(int i = 0 ; i<listDisciplinas.size() ; i ++){
                        System.out.print(listDisciplinas.get(i)+(i==listDisciplinas.size()-1 ? ".\n" : ", "));
                    }
                }
                loadMenuConsultas();
                break;
            }

            case "0":
                loadMenuIncial();
                break;

            default:{
                System.out.println("Opção inválida!");
                loadMenuConsultas();
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
            else {
                try{
                    DisciplinaDAO.checarExistenciaDisciplina(nomeDisciplina);
                    controle = true;
                }catch(NaoEncontrado e){
                    System.out.println(e.getMessage());
                }
            }  
        }while(nomeDisciplina.equals("0") || !controle);
        return nomeDisciplina;
    }

    public static void loadMenuExcluir(){

        Aluno aluno = new Aluno();
        Professor professor = new Professor();

        System.out.print("\n\nDeseja excluir: \n(a) Aluno \n(b) Professor \n(c) Disciplina \n(d) Material \n(0 para voltar ao menu anterior)");
        System.out.print("\nEscolha uma opção: ");
        String opcaoExclusao = leitura.nextLine();
        switch (opcaoExclusao){
            case "a":{

                
                    System.out.print("Digite o nome do aluno que deseja excluir: ");
                    String nome = leitura.nextLine();
                    try{
                        UsuarioDAO.checarExistenciaUsuario(nome);
                        AlunoDAO.deletarAluno(nome);
                        System.out.print("Aluno removido com sucesso!");
                    }catch(NaoEncontrado e){
                        System.out.println(e.getMessage());
                    }
                   
                    loadMenuExcluir();
                    break;
                
            }
            
            case "b":{
                System.out.print("Digite o nome do professor que deseja excluir: ");
                String nome = leitura.nextLine();
            try{
                UsuarioDAO.checarExistenciaUsuario(nome);
                ProfessorDAO.deletarProfessor(nome);
                System.out.print("Professor removido com sucesso!");
            }catch(NaoEncontrado e){
                System.out.println(e.getMessage());
            }
                loadMenuExcluir();
                break;
            }
                

            case "c":{
                System.out.print("Digite o nome da disciplina que deseja excluir: ");
                String nomeDisciplina = leitura.nextLine();
                try{
                    DisciplinaDAO.checarExistenciaDisciplina(nomeDisciplina);
                    DisciplinaDAO.deletarDisciplina(nomeDisciplina);
                    System.out.print("Disciplina removida com sucesso!");
                }catch(NaoEncontrado e){
                    System.out.println(e.getMessage());
                }
                
                loadMenuExcluir();
                break;
            }

            case "d":{
                System.out.print("Digite o nome do material que deseja excluir: ");
                String nomeMaterial = leitura.nextLine();

                try{
                    MaterialDAO.checarExistenciaMaterial(nomeMaterial);
                    MaterialDAO.deletarMaterial(nomeMaterial);
                    System.out.print("Material removido com sucesso!");
                }catch(NaoEncontrado e){
                    System.out.println(e.getMessage());
                }
               
                loadMenuExcluir();
                break;
            }

            case "0":{
                loadMenuIncial();
                break;
            }

            default: {
                System.out.println("Opção Invalida!");
                loadMenuExcluir();
            }
        }
    }

    public static void loadMenuRelacionamento(Usuario usuario){
        System.out.print("\nMenu - \n(a) Adicionar usuário \n(b) Excluir usuário \n(0) para voltar ao menu anterior\nR: ");

        switch(leitura.nextLine().toLowerCase()){
            case "a":{
                System.out.print("\nDigite o nome do usuário que deseja adicionar: ");
                String nomeUsuario = leitura.nextLine();
                Usuario outroUsuario;
                try {
                    outroUsuario = UsuarioDAO.checarExistenciaUsuario(nomeUsuario);
                    System.out.print("Qual o grau de confiabilidade desse usuário?\nR: ");
                    RelacionamentoDAO.criarRelacionamento(usuario, outroUsuario, leitura.nextLine());
                    System.out.println("Relacionamento criado com sucesso!");
                    
                } catch (NaoEncontrado e) {
                    System.out.println(e.getMessage());
                }

                loadMenuRelacionamento(usuario);
                break;
            }

            case "b":{
                System.out.print("\nDigite o nome do usuário que deseja remover: ");
                String nomeUsuario = leitura.nextLine();
                Usuario outroUsuario;
                try {
                    outroUsuario = UsuarioDAO.checarExistenciaUsuario(nomeUsuario);
                    RelacionamentoDAO.excluirRelacionamento(usuario, outroUsuario);
                    System.out.println("Relação com "+outroUsuario.getNome()+" excluída.");
                } catch (NaoEncontrado e) {
                   System.out.println(e.getMessage());
                }

                loadMenuRelacionamento(usuario);
                break;
            }

            case "0":{
                if(usuario.getClass() == Aluno.class)
                    loadMenuAluno((Aluno) usuario);
                else
                    loadMenuProfessor((Professor) usuario);
                break;
            }

            default:{
                System.out.println("Opção inválida!");
                loadMenuRelacionamento(usuario);
                break;
            }

        }
    }

}
                



