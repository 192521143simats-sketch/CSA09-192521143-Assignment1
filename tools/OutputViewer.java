import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
public class OutputViewer {
    public static void main(String[] args) throws Exception {
        ProcessBuilder b = new ProcessBuilder(java.util.Arrays.copyOfRange(args, 1, args.length));
        b.redirectErrorStream(true);
        Process p = b.start();
        String output = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        int exit = p.waitFor();
        if (System.getProperty("evidence.output") != null) java.nio.file.Files.writeString(java.nio.file.Path.of(System.getProperty("evidence.output")), output);
        JFrame frame = new JFrame("Executed Java output - " + args[0]);
        JTextArea text = new JTextArea(args[0] + "\n" + String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length))
            + "\n\n" + output + "\nProcess exit code: " + exit);
        text.setEditable(false); text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, output.lines().count() > 30 ? 13 : 17));
        text.setMargin(new Insets(18, 18, 18, 18));
        frame.add(new JScrollPane(text)); frame.setSize(1100, 820);
        frame.setLocationRelativeTo(null); frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
