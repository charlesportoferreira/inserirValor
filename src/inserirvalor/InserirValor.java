package inserirvalor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InserirValor {

    public static List<String> fileNames = new ArrayList<>();

    public static void main(String[] args) {
        String diretorio = System.getProperty("user.dir");
        fileNames = fileTreePrinter(new File(diretorio), 0);
        for (String fileName : fileNames) {
            lerArquivoData(fileName);
        }
    }

    public static List<String> fileTreePrinter(File initialPath, int initialDepth) {
        if (initialPath.exists()) {
            File[] contents = initialPath.listFiles();
            for (File content : contents) {
                if (content.isDirectory()) {
                    fileTreePrinter(content, initialDepth + 1);
                } else {
                    if (content.getName().contains(".txt")) {
                        fileNames.add(content.getName());
                    }
                }
            }
        }
        return fileNames;
    }

    public static void lerArquivoData(String nome) {
        StringBuilder linha = new StringBuilder();
        linha.append("g,geracao,p,peso,v,valor,f,fitness\n");
        File arquivo = new File(nome);
        String dadoLido;
        try {
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
            while (br.ready()) {
                dadoLido = br.readLine().replace(".0", "").replace("-", ",");
                if (dadoLido.contains("S:,1000")) {
                    break;
                }
                linha.append(dadoLido).append("\n");

            }
        } catch (IOException ex) {
        }
        salvarArquivo(linha.toString(), nome.replace(".txt", ".dat"));
    }

    public static void salvarArquivo(String texto, String nomeArquivo) {
        try {
            try (FileWriter fw = new FileWriter(nomeArquivo);
                    BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(texto);
                bw.close();
                fw.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException("Impossivel salvar arquivo: " + nomeArquivo);
        }
    }

}
