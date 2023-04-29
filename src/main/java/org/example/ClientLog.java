package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    private List<String[]> log = new ArrayList();

    public ClientLog() {
    }

    public void log(int productNum, int amount) throws IOException {
        this.log.add(new String[]{"" + productNum, "" + amount});
    }

    public void exportAsCSV(File txtFile) {
        if (!txtFile.exists()) {
            this.log.add(0, new String[]{"productNum,amount\n"});
        }

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true));

            try {
                writer.writeAll(this.log);
            } catch (Throwable var6) {
                try {
                    writer.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            writer.close();
        } catch (IOException var7) {
            throw new RuntimeException(var7);
        }
    }

    private class CSVWriter {
        public CSVWriter(FileWriter fileWriter) {
        }

        public void writeAll(List<String[]> log) {

        }

        public void close() {
        }
    }
}

