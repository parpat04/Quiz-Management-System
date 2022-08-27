import java.io.*;
import java.util.ArrayList;

/**
 * Project 5 - Grademic - Account
 * <p>
 * The Account class for our Grademic Program,
 * deals with account methods and data storage for student/teacher accounts
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version May 2, 2022
 */
public class Account {
    // creating account
    String username;
    String password;
    int accountNum;

    public Account() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNum(int accountNum) {
        this.accountNum = accountNum;
    }

    public static ArrayList<String> readFile(String fileName)
            throws FileNotFoundException, NullPointerException {
        File f = new File(fileName);
        ArrayList<String> list = new ArrayList<>();
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        try {
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bfr != null) {
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public boolean confirmLogIn(String username, String password, String fileName) throws FileNotFoundException {
        boolean regis = false;
        ArrayList<String> list = readFile(fileName);
        for (int i = 1; i < list.size(); i += 3) {
            if (list.get(i).equals(username) && list.get(i + 1).equals(password)) {
                logIn(username, password, Integer.valueOf(list.get(i - 1)));
                regis = true;
                break;
            }
        }
        if (regis == false) {
            System.out.println("Incorrect username or password");
        }
        return regis;
    }

    public boolean confirmCreateAccount(String username, String password, String fileName)
            throws FileNotFoundException {
        boolean regis = true;
        ArrayList<String> list = readFile(fileName);
        for (int i = 1; i < list.size(); i += 3) {
            if (list.get(i).equals(username)) {
                System.out.println("This username has already exists! Please enter a new username.");
                regis = false;
            }
        }
        if (regis == true) {
            createAccount(username, password, fileName);
        }
        return regis;
    }

    public void logIn(String username, String password, int accountNum) {
        setUsername(username);
        setPassword(password);
        setAccountNum(accountNum);
    }

    public void createAccount(String username, String password, String fileName) throws FileNotFoundException {
        ArrayList<String> list = readFile(fileName);
        File f = new File(fileName);
        String num = "";
        boolean append = true;
        boolean delete = false;
        for (int i = 0; i < list.size(); i += 3) {
            if (!Integer.toString(i / 3 + 1).equals(list.get(i))) {
                setUsername(username);
                setPassword(password);
                setAccountNum(i / 3 + 1);
                num = Integer.toString(accountNum);
                list.add(i, num);
                list.add(i + 1, username);
                list.add(i + 2, password);
                append = false;
                delete = true;
                break;
            }
        }
        if (delete == false) {
            setUsername(username);
            setPassword(password);
            setAccountNum(list.size() / 3 + 1);
            num = Integer.toString(accountNum);
        }
        FileOutputStream fos = new FileOutputStream(f, append);
        PrintWriter pw = new PrintWriter(fos);
        if (append == false) {
            for (int i = 0; i < list.size(); i++) {
                pw.println(list.get(i));
            }
        } else {
            pw.println(num);
            pw.println(username);
            pw.println(password);
        }
        pw.close();
    }

    public void createCourseSpace(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        FileOutputStream fos = new FileOutputStream(f, true);
        PrintWriter pw = new PrintWriter(fos);
        String num = Integer.toString(accountNum);
        pw.println(num);
        pw.println("");
        pw.println("");
        pw.close();
    }

    public void delete(String fileName) throws FileNotFoundException {
        ArrayList<String> list = readFile(fileName);
        File f = new File(fileName);
        for (int i = 0; i < list.size(); i += 3) {
            if (list.get(i).equals(Integer.toString(accountNum))) {
                list.remove(i + 2);
                list.remove(i + 1);
                list.remove(i);
                break;
            }
        }
        FileOutputStream fos = new FileOutputStream(f, false);
        PrintWriter pw = new PrintWriter(fos);
        for (int i = 0; i < list.size(); i++) {
            pw.println(list.get(i));
        }
        pw.close();
    }

    public void changePassword(String newPassword, String fileName) throws FileNotFoundException {
        password = newPassword;
        ArrayList<String> list = readFile(fileName);
        File f = new File(fileName);
        for (int i = 0; i < list.size(); i += 3) {
            if (list.get(i).equals(Integer.toString(accountNum))) {
                list.set(i + 2, newPassword);
                break;
            }
        }
        FileOutputStream fos = new FileOutputStream(f, false);
        PrintWriter pw = new PrintWriter(fos);
        for (int i = 0; i < list.size(); i++) {
            pw.println(list.get(i));
        }
        setPassword(newPassword);
        pw.close();
    }

    public void changeUsername(String newUsername, String fileName) throws FileNotFoundException {
        username = newUsername;
        ArrayList<String> list = readFile(fileName);
        File f = new File(fileName);
        for (int i = 0; i < list.size(); i += 3) {
            if (list.get(i).equals(Integer.toString(accountNum))) {
                list.set(i + 1, newUsername);
                break;
            }
        }
        FileOutputStream fos = new FileOutputStream(f, false);
        PrintWriter pw = new PrintWriter(fos);
        for (int i = 0; i < list.size(); i++) {
            pw.println(list.get(i));
        }
        setUsername(newUsername);
        pw.close();
    }
}
