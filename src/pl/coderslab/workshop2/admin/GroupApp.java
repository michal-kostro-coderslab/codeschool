package pl.coderslab.workshop2.admin;

import pl.coderslab.workshop2.dao.DBUtil;
import pl.coderslab.workshop2.dao.GroupDao;
import pl.coderslab.workshop2.model.Group;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GroupApp {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        String action = null;
        try(Connection conn = DBUtil.getConnection()) {
            do {
                action = printInvitation(conn);
                switch (action) {
                    case "add":
                        printAdd(conn);
                        break;
                    case "edit":
                        printEdit(conn);
                        break;
                    case "delete":
                        printDelete(conn);
                        break;
                }
            } while (!("quit").equals(action));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String printInvitation(Connection conn) throws SQLException {
        String action = null;
        System.out.println("Lista grup:");
        Group[] groups = GroupDao.findAll();
        for (Group group : groups)
            System.out.println("["+group.getId()+"] " + group.getName());

        do {
            System.out.println("Wybierz jedną z opcji: [a]dd, [e]dit, [d]elete, [q]uit :");
            String response = scan.nextLine();
            switch (response.charAt(0)) {
                case 'a':
                    action = "add";
                    break;
                case 'e':
                    action = "edit";
                    break;
                case 'd':
                    action = "delete";
                    break;
                case 'q':
                    action = "quit";
                    break;
                default:
                    System.out.println("błędna odpowiedz: " + response);
            }
        } while (action == null);
        return action;
    }

    private static void printAdd(Connection conn) {
        System.out.println("Podaj dane grupy");
        System.out.println("Podaj nazwę:");
        String name = scan.nextLine();
        Group group = new Group();
        group.setName(name);
        GroupDao.create(group);
        System.out.println("Dodano grupę!");
    }

    private static void printEdit(Connection conn) {
        String id = null;
        do {
            System.out.println("Którą grupę chesz edytować?");
            try {
                int response = scan.nextInt();
                id = Integer.toString(response);
                scan.nextLine();
                System.out.println("Podaj nazwę:");
                String name = scan.nextLine();
                Group g = GroupDao.read(response);
                g.setName(name);
                GroupDao.update(g);
                System.out.println("Zaktualizowano dane grupy!");
            } catch (InputMismatchException e) {
                System.out.println("Błędny numer");
                scan.nextLine();
            }
        } while (id == null);
    }

    private static void printDelete(Connection conn) {
        String id = null;
        do {
            System.out.println("Którę grupę chcesz usunąć?");
            try {
                int response = scan.nextInt();
                id = Integer.toString(response);
                scan.nextLine();
                GroupDao.delete(response);
                System.out.println("Usunięto grupę!");
            } catch (InputMismatchException e) {
                System.out.println("Błędny numer");
                scan.nextLine();
            }
        } while (id == null);
    }
}
